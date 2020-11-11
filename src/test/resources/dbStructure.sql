create schema if not exists marsdex;

set schema marsdex;

DROP TABLE IF EXISTS colonies;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS resources;
Drop table if exists colonies_companies;
DROP TABLE IF EXISTS companies_resources;
DROP TABLE IF EXISTS shipments_resources;
DROP TABLE IF EXISTS shipments;

create table if not exists colonies(
    id int primary key auto_increment not null ,
    name nvarchar(50) not null,
    latitude double not null ,
    longitude double not null ,
    altitude double not null
);

CREATE TABLE IF NOT EXISTS companies(
    id int primary key auto_increment not null,
    name nvarchar(70) not null,
    email nvarchar(100) not null,
    phone nvarchar(20) not null,
    storage int
);

CREATE TABLE IF NOT EXISTS resources (
    id int primary key auto_increment not null,
    price double not null
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
    id int not null primary key,
    sender_id int not null,
    send_time datetime not null,
    receiver_id int not null,
    receive_time datetime not null,
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