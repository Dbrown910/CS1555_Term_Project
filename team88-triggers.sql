--1)
create or replace trigger adjustTicket
before update of leg on Reservation_detail
referencing new as newVal
for each row
declare 
 upd_price number;
begin
 Select high_price into upd_price 
 From Price Join Flight on Flight.airline_id = Price.airline_id 
 Order By ASC Where flight_id = :newVal.flight_number;
 
 update Reservation 
 set cost = upd_price 
 where ticketed = 0 and :newVal.reservation_number = Reservation.reservation_number;
end;

--2)

--3)