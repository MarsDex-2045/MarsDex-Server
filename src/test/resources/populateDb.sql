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

insert into colonies(name, latitude, longitude, altitude)
VALUES ('Haberlandt Survey', 0.00000, 0.00000, 0.000 );
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Durrance Camp', 40.22451, -80.56218, 160.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Ehrlich City', 33.21322, -33.2132, 300.000);
insert into colonies(name, latitude, longitude, altitude)
VALUES ('Silves Claim', 22.21773, 24.33564, -200.232);

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