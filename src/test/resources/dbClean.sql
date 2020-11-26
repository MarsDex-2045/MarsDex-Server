create schema if not exists marsdex;

set schema marsdex;

Drop table if exists colonies_companies;
DROP TABLE IF EXISTS companies_resources;
DROP TABLE IF EXISTS shipments_resources;
DROP TABLE IF EXISTS shipments;
DROP TABLE IF EXISTS resources;
DROP TABLE IF EXISTS colonies;
DROP TABLE IF EXISTS companies;