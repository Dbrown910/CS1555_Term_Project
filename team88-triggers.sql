--1)
create or replace trigger adjustTicket
before update of leg on Reservation_detail
referencing new as newVal old as oldVal
for each row
declare 
 old_price int;
 upd_price int;
begin
--get the price of the old leg
 Select high_price into old_price 
 From Price Join Flight on Flight.airline_id = Price.airline_id 
 Where flight_number = :oldVal.flight_number;

--get the price of the new leg
 Select high_price into upd_price 
 From Price Join Flight on Flight.airline_id = Price.airline_id 
 Where flight_number = :newVal.flight_number;

  --subtract the old price from the total cost
 update Reservation 
 set cost = cost - old_price 
 where ticketed = 'N' and :oldVal.reservation_number = Reservation.reservation_number;
 
 --add the price of the new leg to the cost
 update Reservation 
 set cost = cost + upd_price 
 where ticketed = 'N' and :newVal.reservation_number = Reservation.reservation_number;
end;
/

--2)
CREATE OR REPLACE FUNCTION get_plane_capacity (flightNum in varchar) RETURN int
AS
capacity float;
BEGIN
	-- Get the capacity of that plane
	SELECT plane_capacity into capacity
	FROM Plane
	WHERE plane_type = (-- Get the plane type
						SELECT plane_type
						FROM Flight
						WHERE flight_number = flightNum
						);

	return (capacity);
END;
/

CREATE OR REPLACE FUNCTION get_num_flight_reservations (flightNum in varchar) RETURN int
AS
number_of_reservations int;
BEGIN
	-- Get the number of reservations for this flight
	SELECT COUNT(*) into number_of_reservations
	FROM Flight
	WHERE flight_number = flightNum;

	return (number_of_reservations);
END;
/

CREATE OR REPLACE FUNCTION get_new_plane (cap in int) RETURN char
AS
p_type char;
BEGIN
	-- Select the plane with the next highest capacity
	SELECT plane_type into p_type
	FROM (SELECT *
			FROM Plane
			WHERE plane_capacity > cap
			ORDER BY plane_capacity ASC)
	WHERE ROWNUM = 1;
	
	return (p_type);
END;
/

CREATE OR REPLACE TRIGGER planeUpgrade
AFTER INSERT ON Reservation_detail
FOR EACH ROW
BEGIN
	-- If the number of reservations on the flight is equal to the plane's capacity
	IF get_num_flight_reservations(:new.flight_number) >= get_plane_capacity(:new.flight_number) THEN
		-- Find a new plane
		UPDATE Flight
		SET plane_type = get_new_plane(get_num_flight_reservations(:new.flight_number))
		WHERE flight_number = :new.flight_number;
	END IF;		
END;
/

--3)
--finds a new plane to accomadate a smaller group of reservations
CREATE OR REPLACE FUNCTION downsize_plane (cap in int) RETURN char
AS
p_type char;
BEGIN
	-- Select the plane with the next highest capacity
	SELECT plane_type into p_type
	FROM (SELECT *
			FROM Plane
			WHERE plane_capacity > cap
			ORDER BY plane_capacity DESC)
	WHERE ROWNUM = 1;
	
	return (p_type);
END;
/

--compiles necessary information about the reservation
create or replace view seatingInfo
	as select Reservation_detail.reservation_number, Reservation_detail.flight_number, Reservation_detail.flight_date, Flight.airline_id, Flight.plane_type, Plane.plane_capacity, Reservation.ticketed
	from Reservation_detail, Flight, Plane, Reservation
	where Reservation_detail.reservation_number = Reservation.reservation_number 
	and Reservation_detail.flight_number = Flight.flight_number 
	and Flight.plane_type = Plane.plane_type
	and Reservation.ticketed = 'Y';

--gets the number of ticketed reservations for each plane
create or replace view seatsReserved(flight_number, seat_count)
	as select flight_number, count(flight_number)
	from seatingInfo, System_time
	where ((flight_date - c_date) * 24) <= 12 
	group by flight_number;

create or replace trigger cancelReservation 
before update of c_date on System_time
referencing new as newVal old as oldVal
for each row
Declare
	seats_used int;
	seats_total int;
Begin
 --deletes all the reservations 12 hours from the flight 
 Delete From Reservation
 Where Exists ( Select *
 				From Reservation_detail Join Reservation On Reservation_detail.reservation_number = Reservation.reservation_number
 				Where ((flight_date - :newVal.c_date) * 24) <= 12 				
 				And Reservation.ticketed = 'N');

 Select seat_count Into seats_used 
 From seatsReserved, seatingInfo 
 Where seatsReserved.flight_number = seatingInfo.flight_number;

 Select plane_capacity into seats_total
 From seatingInfo, seatsReserved
 Where seatsReserved.flight_number = seatingInfo.flight_number;

 If seats_used < seats_total Then
 	Update Flight
 	Set plane_type = downsize_plane(seats_used);
 End If;
End;
/
