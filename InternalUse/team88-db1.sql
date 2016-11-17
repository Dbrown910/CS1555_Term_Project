-- schemas 1-4
DROP TABLE Airline CASCADE CONSTRAINTS;
DROP TABLE Plane CASCADE CONSTRAINTS;
DROP TABLE Flight CASCADE CONSTRAINTS;
DROP TABLE Price CASCADE CONSTRAINTS;

create table Airline(
	airline_id varchar(5),
	airline_name varchar(50),
	airline_abbreviation(10),
	year_founded int,
	CONSTRAINT Airline_PK
		PRIMARY KEY (airline_id)
);

create table Plane(
	plane_type char(4),
	manufacture varchar(10),
	plane_capacity int,
	last_service date,
	year int,
	owner_id varchar(5),
	CONSTRAINT Plane_PK
		PRIMARY KEY (plane_type),
	CONSTRAINT Plane_to_Airline_FK
		FOREIGN KEY (owner_id) REFERENCES Airline(airline_id)
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
		PRIMARY KEY (flight_number),
	CONSTRAINT Flight_to_Plane_FK
		FOREIGN KEY (plane_type) REFERENCES PLANE(plane_type),
	CONSTRAINT Flight_to_Airline_FK
		FOREIGN KEY (airline_id) REFERENCES Airline(airline_id)
);

create table Price(
	departure_city varchar(3),
	arrival_city varchar(3),
	airline_id varchar(5),
	high_price int,
	low_price int,
	CONSTRAINT Price_PK
		PRIMARY KEY (departure_city, arrival_city),
	CONSTRAINT Price_to_Airline_FK
		FOREIGN KEY (airline_id) REFERENCES Airline(airline_id)
);