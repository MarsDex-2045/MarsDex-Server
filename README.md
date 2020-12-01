# MarsDex Server Repository (MD-SR)
Welcome to the Server Repository of the MarsDex. This repository contains the code needed to run a local version of the MarsDex Server.

|Version|Maintance|SonarQube|
|---|---|---|
|[![Generic badge](https://img.shields.io/badge/Version-Alpha-red.svg)](https://shields.io/)|![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)|![SonarQube](https://sonar.ti.howest.be/sonar/api/project_badges/measure?project=2020.project-ii%3Amars-server-23&metric=coverage)<br>*Based on the latest push to any branch in this repo.*|
## Features
At the time of writing, the following features are implemented:
### API Calls
[All the calls found in `openapi-group-23.yaml`](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server/-/blob/master/src/main/resources/openapi-group-23.yaml) will return data. Not all data is real data directly pulled from the H2 Database:

|Endpoint|Mock or Implemented?|
|---|---|
|GET `/api/colony`|Implemented|
|GET `/api/colony/{colonyId}`|Implemented|
|GET `/api/company/{companyId}`|Mock|
|GET `/api/company/{companyId}/transport`|Mock|
|GET `/api/company/{companyId}/resource`|Implemented|
|PUT `/api/company/{companyId}/resource`|Mock|
|PATCH `/api/company/{companyId}/resource`|Mock|
|DELETE `/api/company/{companyId}/resource/{resourceId}|Mock|
|PUT `/api/company`|Mock|

## How to start
In order to get the server up & running, you'll need to do the following things:
1. Clone the project to your machine.
2. Open the project in your IDE of choice
3. Add a new directory `conf` to your project root and add the following JSON code as a `config.json` file:
```json
{
  "http": {
    "port": 8080
  },
  "db" : {
    "url": "jdbc:h2:~/mars-db",
    "username": "",
    "password": "",
    "webconsole.port": 9000
  }
}
```
3. Build the Gradle project
4. Before running the server, make sure that the 8080 & 9000 ports are available on your machine and not used by any other services that run on `localhost`
5. Run the sever and connect to the webclient `http://localhost:9000` and connect to the database. Don't fill in the credentials.
6. Fill the DB with mock data with the `dbConstruction.sql` script. You can find the script in `src/test/resources/`. Copy it into the web console and execute it.
    - :exclamation: If you're having troubles loading the data in, you might have a database that is filled. You can clean it with the `dbClean.sql` script

Congrats, now you have a MarsDex Server up and running. You'll still need [The MarsDex Client](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/client) in order to interact with it through a website.
