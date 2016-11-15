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
--compiles necessary information about the reservation
create or replace view seatingInfo
	as rd.reservation_number, rd.flight_number, rd.flight_date, f.airline_id, f.plane_type, p.plane_capacity, r.ticketed
	from Reservation_detail as rd, Flight as f, Plane as p, Reservation as r
	where rd.reservation_numer = r.reservation_number 
	  and rd.flight_number = f.flight_number 
	  and f.plane_type = p.plane_type;

--gets the number of ticketed reservations for each plane
create or replace view seatsReserved
	as flight_number, count(flight_number) as seat_count
	from seatingInfo, System_time
	having ((flight_date - c_date) * 24) <= 12 and ticketed = 'Y';

create or replace trigger cancelReservation 
before update of c_date on System_time
referencing new as newVal old as oldVal
for each row
declare 
	num_Passengers int;
begin
  Select Count(flight_number) into num_Passengers
  From seatsReserved 
  Having ((flight_date - :newVal.c_date) * 24) <= 12 and ticketed = 'Y') <  
  
 
 --deletes all the reservations 12 hours from the flight 
 Delete From Reservation
 Where Exists ( Select *
 				From Reservation_detail Join Reservation On Reservation_detail.reservation_number = Reservation.reservation_number
 				Where ((flight_date - :newVal.c_date) * 24) <= 12 				
 				And Reservation.ticketed = 'N');
 
 IF

 END IF;
end;
/
