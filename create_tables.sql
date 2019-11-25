drop table RentReturn;
drop table Rental;
drop table Reservation;
drop table Customer;
drop table Vehicle;
drop table VehicleType;
drop table Branch;
commit;

drop sequence res_seq;
drop sequence rent_seq;
commit;

create sequence res_seq start with 1;
create sequence rent_seq start with 1;
commit;

create table Branch (
    location varchar(20),
    city varchar(20),
    primary key(location, city)
);
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
commit;

create table Vehicle (
    vlicense int PRIMARY KEY,
    vid int,
    make varchar(15),
    model varchar(20),
    year int,
    color varchar(15),
    odometer int,
    status varchar(20),
    vtname varchar(15),
    location varchar(20),
    city varchar(20),
    foreign key (vtname) references VehicleType(vtname),
    foreign key (location, city) references Branch(location, city)
);
commit;

create table Customer (
    dlicense int PRIMARY KEY,
    name varchar(30),
    address varchar(30),
    phonenumber int
);
commit;

create table Reservation (
    confNo int DEFAULT res_seq.nextval NOT NULL,
    vtname varchar(15),
    dlicense int,
    fromDate date,
    toDate date,
    primary key (confNo),
    foreign key (vtname) references VehicleType(vtname),
    foreign key (dlicense) references Customer(dlicense)
);
commit;

create table Rental (
    rid int DEFAULT rent_seq.nextval NOT NULL,
    vlicense int,
    dlicense int,
    confNo int,
    fromDate date,
    toDate date,
    odometer int,
    cardName varchar(30),
    cardNo int,
    expDate date,
    primary key (rid),
    foreign key (vlicense) references Vehicle(vlicense),
    foreign key (dlicense) references Customer(dlicense),
    foreign key (confNo) references Reservation(confNo)
);
commit;

create table RentReturn (
    rid int PRIMARY KEY,
    returnDate date,
    odometer int,
    fullTank char(1),
    value int,
    foreign key (rid) references Rental(rid)
);
commit;

insert into branch(location,city) values ('Blundell and No. 3', 'Richmond');
insert into branch(location,city) values ('Steveston', 'Richmond');
insert into branch(location,city) values ('Waterfront', 'Vancouver');
insert into branch(location,city) values ('Lougheed', 'Coquitlam');
insert into branch(location,city) values ('Metrotown', 'Burnaby');

insert into VehicleType (vtname, features, wrate, drate, hrate,wirate,dirate,hirate,krate) values ('Economy', '', 6, 3, 1, 14, 11, 8, 18);
insert into VehicleType (vtname, features, wrate, drate, hrate,wirate,dirate,hirate,krate) values ('SUV', '', 9, 7, 4, 19, 16, 9, 25);
insert into VehicleType (vtname, features, wrate, drate, hrate,wirate,dirate,hirate,krate) values ('Mid-size', '', 20, 15, 10, 30, 25, 22, 40);
insert into VehicleType (vtname, features, wrate, drate, hrate,wirate,dirate,hirate,krate) values ('Truck', '', 7, 6, 5, 9, 8, 7, 12);

insert into Customer (dlicense, name, address, phonenumber) values (1729328, 'Raymond Ng', '696 Ontario St.', 5671123);
insert into Customer (dlicense, name, address, phonenumber) values (1729311, 'Raymond Nog', '69 Ontario St.', 5671124);
insert into Customer (dlicense, name, address, phonenumber) values (1123328, 'Raymond Ng', '696 Ontario St.', 5671123);
insert into Customer (dlicense, name, address, phonenumber) values (0981726, 'Jessica Wong', '66-993 Ontar St.', 5671123);

insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (133313333, 1, 'Honda', 'Civic', 2006, 'red', 1080, 'rented', 'Economy', 'Blundell and No. 3', 'Richmond');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (244424444, 2, 'Nissan', 'Murano', 2016, 'red', 80, 'maintenance', 'SUV', 'Waterfront', 'Vancouver');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (453423129, 3, 'Nissan', 'Murano', 2013, 'blue', 14400, 'rented', 'SUV', 'Waterfront', 'Vancouver');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (453423128, 4, 'Nissan', 'Murano', 2016, 'blue', 1, 'available', 'SUV', 'Waterfront', 'Vancouver');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (453423126, 5, 'Nissan', 'Murano', 2003, 'blue', 140, 'available', 'SUV', 'Waterfront', 'Vancouver');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (888888888, 6, 'Ford', 'Mustang', 2003, 'white', 10012, 'available', 'Mid-size', 'Lougheed', 'Coquitlam');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (133313332, 7, 'Honda', 'Civic', 2005, 'red', 0, 'available', 'Economy', 'Blundell and No. 3', 'Richmond');
insert into Vehicle (vlicense, vid, make, model, year, color, odometer, status, vtname, location, city) values (696969420, 8, 'Tesla', 'Cybertruck', 2019, 'black', 0, 'available', 'Truck', 'Steveston', 'Richmond');

insert into Reservation (vtname, dlicense, fromDate, toDate) values ('SUV', 1729311, TO_DATE('2019/11/29', 'YYYY/MM/DD'), TO_DATE('2019/11/30', 'YYYY/MM/DD'));

insert into Rental (vlicense, dlicense, confNo, fromDate, toDate, odometer, cardName, cardNo, expDate) values (133313333, 1729328, null, TO_DATE('2019/11/29', 'YYYY/MM/DD'), TO_DATE('2019/11/30', 'YYYY/MM/DD'), 12347, 'Raymond Ng', 123456, TO_DATE('2023/11/19', 'YYYY/MM/DD'));

commit;
