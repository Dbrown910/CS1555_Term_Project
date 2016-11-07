DROP TABLE Customer CASCADE CONSTRAINTS;
DROP TABLE Reservation CASCADE CONSTRAINTS;
DROP TABLE Reservation_detail CASCADE CONSTRAINTS;
DROP TABLE Date CASCADE CONSTRAINTS;

create table Customer(
	cid varchar(9),
	salutation varchar(3),
	first_name varchar(30),
	last_name varchar(30),
	credit_card_num varchar(16),
	credit_card_expire date,
	street varchar(30),
	city varchar(30),
	state varchar(2),
	phone varchar(10),
	email varchar(30),
	frequent_miles varchar(5),
	CONSTRAINT Customer_PK PRIMARY KEY (cid) DEFERRABLE
);

create table Reservation(
	reservation_number varchar(5),
	cid varchar(9),
	cost int,
	credit_card_num varchar(16),
	reservation_date date,
	ticketed varchar(1),
	CONSTRAINT res_PK PRIMARY KEY (reservation_number) DEFERRABLE,
	CONSTRAINT res_FK FOREIGN KEY (cid) references Customer(cid) INITIALLY DEFERRED DEFERRABLE
);

create table Reservation_detail(
	reservation_number varchar(5),
	flight_number varchar(3),
	flight_date date,
	leg int,
	CONSTRAINT res_detail_PK PRIMARY KEY (reservation_number, leg) DEFERRABLE,
	CONSTRAINT res_detail_FK1 FOREIGN KEY (reservation_number) references Reservation(reservation_number) INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT res_detail_FK2 FOREIGN KEY (flight_number) references Flight(flight_number) INITIALLY DEFERRED DEFERRABLE
);

create table Date(
	c_date date,
	CONSTRAINT Date_PK PRIMARY KEY (c_date) DEFERRABLE
);                                  