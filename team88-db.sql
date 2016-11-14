DROP TABLE Airline CASCADE CONSTRAINTS;
DROP TABLE Plane CASCADE CONSTRAINTS;
DROP TABLE Flight CASCADE CONSTRAINTS;
DROP TABLE Price CASCADE CONSTRAINTS;
DROP TABLE Customer CASCADE CONSTRAINTS;
DROP TABLE Reservation CASCADE CONSTRAINTS;
DROP TABLE Reservation_detail CASCADE CONSTRAINTS;
DROP TABLE System_time CASCADE CONSTRAINTS;

create table Airline(
	airline_id varchar(5),
	airline_name varchar(50),
	airline_abbreviation varchar(10),
	year_founded int,
	CONSTRAINT Airline_PK
		PRIMARY KEY (airline_id) DEFERRABLE
);

create table Plane(
	plane_type char(4),
	manufacture varchar(10),
	plane_capacity int,
	last_service date,
	year int,
	owner_id varchar(5),
	CONSTRAINT Plane_PK
		PRIMARY KEY (plane_type) DEFERRABLE,
	CONSTRAINT Plane_to_Airline_FK
		FOREIGN KEY (owner_id) REFERENCES Airline(airline_id) INITIALLY DEFERRED DEFERRABLE
);

create table Flight(
	flight_number varchar(3),
	airline_id varchar(5),
	plane_type char(4),
	departure_city varchar(3),
	arrival_city varchar(3),
	departure_time varchar(4),
	arrival_time varchar(4),
	weekly_schedule varchar(7),
	CONSTRAINT Flight_PK
		PRIMARY KEY (flight_number) DEFERRABLE,
	CONSTRAINT Flight_to_Plane_FK
		FOREIGN KEY (plane_type) REFERENCES PLANE(plane_type) INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Flight_to_Airline_FK
		FOREIGN KEY (airline_id) REFERENCES Airline(airline_id) INITIALLY DEFERRED DEFERRABLE
);

create table Price(
	departure_city varchar(3),
	arrival_city varchar(3),
	airline_id varchar(5),
	high_price int,
	low_price int,
	CONSTRAINT Price_PK
		PRIMARY KEY (departure_city, arrival_city) DEFERRABLE,
	CONSTRAINT Price_to_Airline_FK
		FOREIGN KEY (airline_id) REFERENCES Airline(airline_id) INITIALLY DEFERRED DEFERRABLE
);

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
	CONSTRAINT Customer_PK 
		PRIMARY KEY (cid) DEFERRABLE,
	CONSTRAINT Customer_to_Airline_FK
		FOREIGN KEY (frequent_miles) REFERENCES Airline(airline_id) INITIALLY DEFERRED DEFERRABLE

);

create table Reservation(
	reservation_number varchar(5),
	cid varchar(9),
	cost int,
	credit_card_num varchar(16),
	reservation_date date,
	ticketed varchar(1),
	CONSTRAINT Reservation_PK 
		PRIMARY KEY (reservation_number) DEFERRABLE,
	CONSTRAINT Reservation_to_Customer_FK 
		FOREIGN KEY (cid) REFERENCES Customer(cid) INITIALLY DEFERRED DEFERRABLE
);

create table Reservation_detail(
	reservation_number varchar(5),
	flight_number varchar(3),
	flight_date date,
	leg int,
	CONSTRAINT Reservation_detail_PK 
		PRIMARY KEY (reservation_number, leg) DEFERRABLE,
	CONSTRAINT Reservation_detail_to_Reservation_FK1 
		FOREIGN KEY (reservation_number) REFERENCES Reservation(reservation_number) INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Reservation_detail_to_Flight_FK2 
		FOREIGN KEY (flight_number) REFERENCES Flight(flight_number) INITIALLY DEFERRED DEFERRABLE
);

create table System_time(
	c_date date,
	CONSTRAINT Date_PK 
		PRIMARY KEY (c_date) DEFERRABLE
);                                  