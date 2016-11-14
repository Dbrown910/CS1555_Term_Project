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

CREATE OR REPLACE TRIGGER cancelReservation
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