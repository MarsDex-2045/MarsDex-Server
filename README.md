# MarsDex Server Repository (MD-SR)
Welcome to the Server Repository of the MarsDex. This repository contains the code needed to run a local version of the MarsDex Server.

|Version|Maintenance|
|---|---|
|[![Generic badge](https://img.shields.io/badge/Version-Live-blue.svg)](https://shields.io/)|![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)|
## Index
* [Server](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server)
* [Client](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/client)
* [Documentation](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/documentation)
## Bugs
At the moment of writing (17/12/20), there are no known bugs.
## Features
**Note: Authentication is moved from MVP to Nice-To-have.**

At the time of writing, the following features are implemented:
### API Calls

[All the calls found in `openapi-group-23.yaml`](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server/-/blob/master/src/main/resources/openapi-group-23.yaml) will return data, pulled from the H2 Database.

|Endpoint|Mock or Implemented?|
|---|---|
|GET `/api/colony`|Implemented|
|GET `/api/colony/{colonyId}`|Implemented|
|GET `/api/company/{companyId}`|Implemented|
|GET `/api/company/{companyId}/transport`|Implemented|
|GET `/api/company/{companyId}/resource`|Implemented|
|PUT `/api/company/{companyId}/resource`|Implemented|
|PATCH `/api/company/{companyId}/resource`|Implemented|
|DELETE `/api/company/{companyId}/resource/{resourceId}`|Implemented|
|PUT `/api/company`|Implemented|
|POST `/api/company`|Implemented|
### DB Interaction
All endpoints that have been implemented interact through the database with the classes that can be found in `logic/data`.
#### H2 Statements
The `H2Statements` class serves as a common repository for SQL calls. Sometimes multiple calls are needed in one function and in order to evade repetition, this class was introduced.

*Note: The scope of these statements are only limited to the `data` folder*
#### Repository Structure
#### MarsRepository
MarsRepository is used as a utility class. It manages the connections and also some data conversions.
#### Other Repository
The calls are divided by the object they are working with. 

*e.g. `ResourceRepository` works with data & methods that mainly resolves around resources*

### Domains
All Classes are added that will be needed for the project. You can find them in the `classes` directory. You can also use the [Back-end Server Structure](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server/-/wikis/Server-Structure) as a guide through the `class` directory.

### H2 Database
You can find more information about the DB with the [DBD](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server/-/wikis/Database/DB-Diagram), but in short:
- **`colonies`** -> Contains all info about the colonies on Mars
- **`companies`** -> Contains all info about the companies per colonies
- **`shipments`** -> Details the transports between companies.
- **`resources`** -> Contains the resources known in the MarsDex system.

## How to start
In order to get the server up & running, you'll need to do the following things:
1. Clone the project to your machine.
2. Open the project in your IDE of choice.
3. Make sure that the IDE uses [the Zulu 11 JDK](https://www.azul.com/downloads/zulu-community/?package=jdk) to run the server.
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
5. Run the sever and connect to the webclient [`http://localhost:9000`](http://localhost:9000) and connect to the database. Don't fill in the credentials.
6. Fill the DB with mock data with the [`dbConstruction.sql`](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server/-/blob/master/src/test/resources/dbConstruction.sql) script. You can find the script in `src/test/resources/`. Copy it into the web console and execute it.
    - :exclamation: If you're having troubles loading the data in, you might have a database that is filled. You can clean it with the [`dbClean.sql`](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/server/-/blob/master/src/test/resources/dbClean.sql) script.

Congrats, now you have a MarsDex Server up and running. You'll still need [The MarsDex Client](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-23/client) in order to interact with it through a website.

## Contributing
You've noticed something a fault in code or have a feature request? Take first a look at the issues so that you don't make a duplicate issue. 

If there isn't an issue for it, send a message to Bo Robbrecht, and we'll suggest it to the team or alert them of the bug.
