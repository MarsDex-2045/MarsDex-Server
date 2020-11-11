//CREATE COMPANIES
insert into companies(name, email, phone, storage)
VALUES ('MarsDex', 'marsdex@mars.com', '+6623145878', null);
insert into companies(name, email, phone, storage)
VALUES ('MaMiCo', 'mamico@mars.com', '+3422893567', '150000');
insert into companies(name, email, phone, storage)
VALUES ('Generic Company', 'generic@earth.com', '+3246777245', '1000000');
insert into companies(name, email, phone, storage)
VALUES ('Geminorum Blue Vison Partners', 'gbvp@mars.com', '+552434221', '150000');
insert into companies(name, email, phone, storage)
VALUES ('Hydrae Noblement Services', 'hydraenoble@mars.com', '+454553234', '250000');
insert into companies(name, email, phone, storage)
VALUES ('104th Phoenix Discovery Group', 'pdg104@mars.com', '+56778987', '1500');
insert into companies(name, email, phone, storage)
VALUES ('Mother Gaia', 'gaia@mars.com', '+564556234', '400000');

//CREATE COLONIES
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Haberlandt Survey', 0.00000, 0.00000, 0.000 );
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Durrance Camp', 40.22451, -80.56218, 160.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Ehrlich City', 33.21322, -33.2132, 300.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Silves Claim', 22.21773, 24.33564, -200.232);

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
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (2, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');

insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (3, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'Processing');
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
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (4, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');

insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 6,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (5, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');

insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 2,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3, parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  parsedatetime('2052-02-23 22:22', 'yyyy-MM-dd hh:mm'), 'Delivered');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'Payed');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 5,  null, 'Processing');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 4,  null, 'In Transit');
insert into shipments(sender_id, send_time, receiver_id, receive_time, status)
VALUES (6, parsedatetime('2052-02-22 22:22', 'yyyy-MM-dd hh:mm'), 3,  null, 'In Transit');

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