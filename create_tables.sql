drop table RentReturn;
drop table Rental;
drop table Reservation;
drop table Customer;
drop table Vehicle;
drop table VehicleType;
commit;

create table VehicleType (
    vtname varchar(15) PRIMARY KEY,
    features varchar(30),
    wrate int,
    drate int,
    hrate int,
    wirate int,
    dirate int,
    hirate int,
    krate int
);

create table Vehicle (
    vlicense varchar(10) PRIMARY KEY,
    vid int,
    make varchar(15),
    model varchar(20),
    year int,
    vtname varchar(15),
    location varchar(20),
    city varchar(20),
    foreign key (vtname) references VehicleType(vtname)
);

create table Customer (
    dlicense int PRIMARY KEY,
    name varchar(30),
    address varchar(30),
    phonenumber int
);

create table Reservation (
    confNo int PRIMARY KEY,
    vtname varchar(15),
    address varchar(30),
    dlicense int,
    fromDate date,
    fromTime date,
    toDate date,
    toTime date,
    foreign key (vtname) references VehicleType(vtname),
    foreign key (dlicense) references Customer(dlicense)
);

create table Rental (
    rid int PRIMARY KEY,
    vlicense varchar(10),
    dlicense int,
    confNo int,
    fromDate date,
    fromTime date,
    toDate date,
    toTime date,
    cardName varchar(30),
    cardNo int,
    expDate date,
    foreign key (vlicense) references Vehicle(vlicense),
    foreign key (dlicense) references Customer(dlicense),
    foreign key (confNo) references Reservation(confNo)
);

create table RentReturn (
    rid int PRIMARY KEY,
    returnDate date,
    returnTime date,
    odometer int,
    fullTank char(1),
    foreign key (rid) references Rental(rid)
);