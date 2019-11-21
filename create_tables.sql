DROP TABLE if exists Vehicle cascade;
DROP TABLE if exists VehicleTypes cascade;
DROP TABLE if exists Customer cascade;
DROP TABLE if exists Reservation cascade;
DROP TABLE if exists Rental cascade;
DROP TABLE if exists Return cascade;

CREATE TABLE Vehicle(
	vlicense    int(9),
    vid         int(15),
	make        varchar(30),
	model       varchar(25),
	year        int(4),
    vtname      varchar(30),
    location    varchar(20),
    city        varchar(20),
    primary key (vlicense),
    foreign key (vtname) references VehicleTypes(vtname)
);

CREATE TABLE VehicleTypes(
    vtname      varchar(10),
	features    varchar(30),
	wrate       int(5),
    drate       int(5),
    hrate       int(5),
    wirate      int(5),
    dirate      int(5),
    hirate      int(5),
    krate       int(5),
    primary key (vtname)
);

CREATE TABLE Customer(
    dlicense    int(15),
	name        varchar(20),
	address     varchar(30),
	phonenumber int(10),
    primary key (dlicence)
);

CREATE TABLE Reservation(
	confNo      int(6),
	vtname      varchar(20),
	address     varchar(30),
	dlicense    int(10),
    fromDate    date,
    fromTime    date,
    toDate      date,
    toTime      date,
    primary key (confNo),
    foreign key (vtname) references vehicleTypes(vtname),
    foreign key (dlicense) references customer(dlicense)
);

CREATE TABLE Rental(
	rid         int(15),
    vlicense    int(9),
    dlicense    int(15),
    fromDate    int(10),
    confNo      int(6),
    fromTime    date,
    toDate      date,
    toTime      date,
    cardName    varchar(25),
    cardNo      int(16),
    expDate     date,
    primary key (rid),
    foreign key (vlicense) references Vehicle(vlicense),
    foreign key (dlicense) references Customer(dlicense),
    foreign key (confNo) references Reservation(confNo)
);

CREATE TABLE Return(
	rid         int(15),
	returnDate  date,
    returnTime  time,
    odometer    int(10),
    fulltank    boolean,
    value       int(6),
    primary key (rid),
    foreign key (rid) references Rental(rid)
);

INSERT INTO Customer values (78065941, "John Doe", "1345 Main St.", 5561239);
INSERT INTO Customer values (12345678, "Donald Trump", "778 Broadway Ave.", 4206969);
INSERT INTO Customer values (08632853, "Reid Holmes", "9-473 Burarrd St.", 4462371);
INSERT INTO Customer values (88888888, "Raymond Ng", "333 E Hastings Ave.", 4434444);