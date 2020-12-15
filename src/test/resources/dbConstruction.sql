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
    email nvarchar(100) not null unique,
    phone nvarchar(20) not null,
    password nvarchar(100) not null
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
    added_timestamp date not null,
    foreign key (shipment_id) references shipments(id),
    foreign key (resource_id) references resources(id)
);

//CREATE COLONIES
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Haberlandt Survey', -22.42744, 162.18224, 0.000 );
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Durrance Camp', -80.60405, 80.04179, 160.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Ehrlich City', 44.32803, 103.71858, 300.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Silves Claim', 22.21773, 24.33564, -200.232);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('SPF-LF1', -27.81715, 83.36856, -10.234);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Galouye Relay', -67.06746, 102.12979, 200.232);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Red Jasmine', 37.24281, -179.06788, 10.231);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Wang City', -28.45090, 57.46124, 30.334);

//CREATE COMPANIES
insert into COMPANIES(name, email, phone, password)
VALUES ('MarsDex', 'marsdex@mars.com', '+6623145878', 'DataH0arder');
insert into COMPANIES(name, email, phone, password)
VALUES ('MaMiCo', 'mamico@mars.com', '+3422893567', 'B1g1r0n');
insert into COMPANIES(name, email, phone, password)
VALUES ('Generic Company', 'generic@earth.com', '+3246777245', 'G3n3r1c');
insert into COMPANIES(name, email, phone, password)
VALUES ('Geminorum Blue Vison Partners', 'gbvp@mars.com', '+552434221', 'V1s10na1r');
insert into COMPANIES(name, email, phone, password)
VALUES ('Hydrae Noblement Services', 'hydraenoble@mars.com', '+454553234', '8ydr0n');
insert into COMPANIES(name, email, phone, password)
VALUES ('104th Phoenix Discovery Group', 'pdg104@mars.com', '+56778987', 'D1sc0n1x');
insert into COMPANIES(name, email, phone, password)
VALUES ('Mother Gaia', 'gaia@mars.com', '+564556234', 'H0wT0N0tSt0rePAssW0rd101');
insert into COMPANIES(name, email, phone, password)
VALUES ('Delta Squadron', 'deltasquad@mars.com', '+6673241524', 'D0wnUnder');
insert into COMPANIES(name, email, phone, password)
VALUES ('Strategic Homeland Division', 'shd@mars.com', '+8853240987', 'Extrem1s_Mal1s_Extrema_Remed1a');
insert into COMPANIES(name, email, phone, password)
VALUES ('SSL Interstellar PLC', 'sslinter@mars.com', '+0253664587', 'Beh1ndTh3V10D');
insert into COMPANIES(name, email, phone, password)
VALUES ('G.O.M. Collective', 'gom@mars.com', '+3222156287', 'Z3lano');

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
insert into resources(price, name)
VALUES (69.098, 'Hafnium 178');
insert into resources(price, name)
VALUES (5.651, 'Indium');
insert into resources(price, name)
VALUES (2.865, 'Uraninite');
insert into resources(price, name)
VALUES (52.077, 'Taaffeite');
insert into resources(price, name)
VALUES (172.629, 'Serendibite');
insert into resources(price, name)
VALUES (1.997, 'Rutile');

//ASSIGN COLONIES TO COMPANIES
    //COLONY 1
    insert into colonies_companies(colony_id, company_id)
    VALUES (1, 1);
    //COLONY 2
    insert into colonies_companies(colony_id, company_id)
    VALUES (2, 2);
    insert into colonies_companies(colony_id, company_id)
    VALUES (2, 3);
    //COLONY 3
    insert into colonies_companies(colony_id, company_id)
    VALUES (3, 4);
    insert into colonies_companies(colony_id, company_id)
    VALUES (3, 5);
    //COLONY 4
    insert into colonies_companies(colony_id, company_id)
    VALUES (4, 6);
    insert into colonies_companies(colony_id, company_id)
    VALUES (4, 7);
    //COLONY 5
    insert into colonies_companies(colony_id, company_id)
    VALUES (5, 8);
    //COLONY 6
    insert into colonies_companies(colony_id, company_id)
    VALUES (6, 9);
    //COLONY 7
    insert into colonies_companies (colony_id, company_id)
    values (7, 10);
    //COLONY 8
    insert into colonies_companies(colony_id, company_id)
    VALUES (8, 11);

//GIVE COMPANY RESOURCES
    //Company 2
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

    //COMPANY 3
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

    //COMPANY 4
    INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (4, 1, 223.000, '2050-11-30');
    INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (4, 2, 350.000, '2050-6-22');

    //COMPANY 5
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

    //COMPANY 6
    VALUES (6, 1, 25.000, '2050-11-1');
    INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (6, 2, 3500.000, '2050-2-22');
    INSERT INTO  companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (6, 3, 20.000, '2050-2-16');

    //COMPANY 7
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (7, 9, 12.300, '2049-3-12');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (7, 14, 12.300, '2049-3-12');

    //COMPANY 8
    insert into companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (8, 10, 6000.234, '2051-3-12');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (8, 15, 12.300, '2049-3-12');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (8, 6, 12.300, '2049-3-12');


    //COMPANY 9
    insert into companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (9, 11, 234.221, '2050-7-11');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (9, 16, 12.300, '2049-3-12');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (9, 7, 70.221, '2049-3-12');


    //COMPANY 10
    insert into companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (10, 12, 221.123, '2030-9-22');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (10, 17, 44.215, '2049-3-12');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (10, 8, 50.221, '2049-3-12');


    //COMPANY 11
    insert into companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (11, 13, 124.221, '2033-11-1');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (11, 5, 50.22, '2049-3-12');
    insert INTO companies_resources(company_id, resource_id, weight, added_timestamp)
    VALUES (11, 9, 12.300, '2049-3-12');


//SHIPMENTS
    //COMPANY 2
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, 'Payed');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'Processing');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, 'In Transit');

    //Company 3
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,'Payed');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2, 'Processing');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, 'In Transit');

    //Company 5
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'Payed');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2, 'Processing');
    insert into shipments(sender_id, send_time, receiver_id,  status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id, status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2, 'In Transit');
    insert into shipments(sender_id, send_time, receiver_id,  status)
    VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, 'In Transit');

//CONNECT SHIPMENTS & RESOURCES
    // COMPANY 1
    insert into shipments_resources(shipment_id, resource_id, weight,added_timestamp)
    VALUES (1, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (2, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (3, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (4, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (5, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (6, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (7, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (8, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (9, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (10, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (11, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (12, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (13, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (14, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (15, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (16, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (17, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (18, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (19, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (20, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (21, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (22, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (23, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (24, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (25, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (26, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (27, 1, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));

    // Company 2
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (2,2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (4, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (6, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (8, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (10, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (12, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (14, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (16, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (18, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (20, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (22, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (24, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));
    insert into shipments_resources(shipment_id, resource_id, weight, added_timestamp)
    VALUES (26, 2, 200, parsedatetime('2052-03-22 22:22', 'yyyy-MM-dd hh:mm'));