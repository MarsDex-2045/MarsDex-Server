create schema if not exists marsdex;

set schema marsdex;

create table if not exists colonies(
    id int primary key auto_increment not null ,
    name nvarchar(50) not null,
    latitude double not null ,
    longitude double not null ,
    altitude double not null
);

CREATE TABLE IF NOT EXISTS COMPANIES(
    id int primary key auto_increment not null,
    name nvarchar(70) not null,
    email nvarchar(100) not null,
    phone nvarchar(20) not null,
    password nvarchar(100) not null,
    storage int
);

CREATE TABLE IF NOT EXISTS resources (
    id int primary key auto_increment not null,
    price double not null,
    name nvarchar(30) not null
);

CREATE TABLE IF NOT EXISTS colonies_companies(
    colony_id int not null,
    company_id int not null,
    foreign key (colony_id) references colonies(id),
    foreign key (company_id) references companies(id)
);

create table if not exists companies_resources(
    company_id int not null,
    resource_id int not null,
    weight double not null,
    added_timestamp date not null,
    foreign key(company_id) references companies(id),
    foreign key(resource_id) references resources(id)
);

create table if not exists shipments(
    id int not null primary key auto_increment,
    sender_id int not null,
    send_time datetime not null,
    receiver_id int not null,
    receive_time datetime,
    status enum('Payed', 'Processing', 'In Transit', 'Delivered'),
    foreign key(sender_id) references companies(id),
    foreign key (receiver_id) references companies(id)
);

create table if not exists shipments_resources(
    shipment_id int not null,
    resource_id int not null,
    weight double not null,
    foreign key (shipment_id) references shipments(id),
    foreign key (resource_id) references resources(id)
);

//CREATE COLONIES
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Haberlandt Survey', 0.00000, 0.00000, 0.000 );
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Durrance Camp', 40.22451, -80.56218, 160.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Ehrlich City', 33.21322, -33.2132, 300.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Silves Claim', 22.21773, 24.33564, -200.232);


//CREATE COMPANIES
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('MarsDex', 'marsdex@mars.com', '+6623145878', 'DataH0arder', null);
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('MaMiCo', 'mamico@mars.com', '+3422893567', 'B1g1r0n', 150000);
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('Generic Company', 'generic@earth.com', '+3246777245', 'G3n3r1c', 1000000);
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('Geminorum Blue Vison Partners', 'gbvp@mars.com', '+552434221', 'V1s10na1r',150000);
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('Hydrae Noblement Services', 'hydraenoble@mars.com', '+454553234', '8ydr0n', 250000);
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('104th Phoenix Discovery Group', 'pdg104@mars.com', '+56778987', 'D1sc0n1x', 1500);
insert into COMPANIES(name, email, phone, password, storage)
VALUES ('Mother Gaia', 'gaia@mars.com', '+564556234', 'H0wT0N0tSt0rePAssW0rd101', 400000);

//CREATE RESOURCES
insert into resources(price, name)
VALUES (71.596, 'Painite');
insert into resources(price, name)
VALUES (271.192, 'Alexandrite');
insert into resources(price, name)
VALUES (1.077, 'Bauxite');
insert into resources(price, name)
VALUES (232.553, 'Low Temperature Diamonds');
insert into resources(price, name)
VALUES (135.218, 'Void Opals');
insert into resources(price, name)
VALUES (149.218, 'Benitoite');
insert into resources(price, name)
VALUES (139.218, 'Bertrandite');
insert into resources(price, name)
VALUES (5.343, 'Coltan');
insert into resources(price, name)
VALUES (10.453, 'Cryolite');
insert into resources (price, name)
values (2.694, 'Gallite');
insert into resources(price, name)
VALUES (24.998, 'Goslarite');
insert into resources(price, name)
VALUES (234.223,'Lithium Hydroxide');

//ASSIGN COLONIES TO COMPANIES
insert into colonies_companies(colony_id, company_id)
VALUES (1, 1);
insert into colonies_companies(colony_id, company_id)
VALUES (2, 2);
insert into colonies_companies(colony_id, company_id)
VALUES (2, 3);
insert into colonies_companies(colony_id, company_id)
VALUES (3, 4);
insert into colonies_companies(colony_id, company_id)
VALUES (3, 5);
insert into colonies_companies(colony_id, company_id)
VALUES (4, 6);
insert into colonies_companies(colony_id, company_id)
VALUES (4, 7);

//GIVE COMPANY RESOURCES
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 1, 2500.000, '2050-11-1');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 2, 350000.000, '2050-2-22');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 3, 2500.000, '2050-2-16');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 4, 12451.000, '2050-4-20');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 5, 67850.000, '2050-4-20');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 6, 445251.241, '2050-4-21');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 7, 42531.000, '2050-4-22');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (2, 8, 5216.535, '2050-4-23');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (3, 1, 2500.000, '2050-11-12');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (3, 2, 350.000, '2050-2-24');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (3, 3, 2520.000, '2050-2-20');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (3, 4, 123411.000, '2050-4-21');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (3, 5, 65560.000, '2050-4-20');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (4, 1, 223.000, '2050-11-30');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (4, 2, 35000.000, '2050-6-22');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 1, 250.000, '2050-11-1');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 2, 35000.000, '2050-2-22');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 3, 200.000, '2050-2-16');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 4, 1251.000, '2050-4-20');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 5, 6780.000, '2050-4-20');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 6, 44251.241, '2050-4-21');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 7, 4251.000, '2050-4-22');
INSERT INTO companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (5, 8, 5000.535, '2050-4-23');
VALUES (6, 1, 25.000, '2050-11-1');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (6, 2, 3500.000, '2050-2-22');
INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
VALUES (6, 3, 20.000, '2050-2-16');

//SHIPMENTS
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');

insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');

insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');

//CONNECT SHIPMENTS & RESOURCES
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (1, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (2, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (3, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (4, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (5, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (6, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (7, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (8, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (9, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (10, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (11, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (12, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (13, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (14, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (15, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (16, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (17, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (18, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (19, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (20, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (21, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (22, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (23, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (24, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (25, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (26, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (27, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (28, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (29, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (30, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (31, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (32, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (33, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (34, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (35, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (36, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (37, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (38, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (39, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (40, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (41, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (42, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (43, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (44, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (45, 1, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (2,2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (4, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (6, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (8, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (10, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (12, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (14, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (16, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (18, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (20, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (22, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (24, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (26, 2, 200 );
insert into shipments_resources(shipment_id, resource_id, weight)
VALUES (28, 2, 200 );