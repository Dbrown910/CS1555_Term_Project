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
 
 --add the new price of the new leg to the cost
 update Reservation 
 set cost = cost + upd_price 
 where ticketed = 'N' and :newVal.reservation_number = Reservation.reservation_number;
end;
/
--2)

--3)