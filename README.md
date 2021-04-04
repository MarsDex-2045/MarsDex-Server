# MarsDex Server Repository (MD-SR)
Welcome to the Server Repository of the MarsDex. This repository contains the code needed to run a local version of the MarsDex Server.

|Version|Maintenance|
|---|---|
|[![Generic badge](https://img.shields.io/badge/Version-Live-blue.svg)](https://shields.io/)|![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)|
## Index
- [Documentation](https://github.com/BT-Creator/MarsDex-Documentation)
- [Client](https://github.com/BT-Creator/MarsDex-Client)
- [Server](https://github.com/BT-Creator/MarsDex-Server)
  
## Bugs
At the moment of writing (17/12/20), there are no known bugs.

At the time of writing, the following features are implemented:
### API Calls

[All the calls found in `openapi-group-23.yaml`](https://github.com/BT-Creator/MarsDex-Server/blob/master/src/main/resources/openapi-group-23.yaml) will return data, pulled from the H2 Database.

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
|POST `/api/saveSubcription`|Implemented|
|GET `/api/push/{pushId}/{companyId}`|Implemented|

### DB Interaction
All endpoints that have been implemented interact through the database with the classes that can be found in `logic/data`.
#### H2 Statements
The `H2Statements` class serves as a common repository for SQL calls. Sometimes multiple calls are needed in one function and in order to evade repetition, this class was introduced.

*Note: The scope of these statements are only limited to the `data` folder*
#### Repository Structure
##### MarsRepository
MarsRepository is used as a utility class. It manages the connections and also some data conversions.
##### Other Repository
The calls are divided by the object they are working with. 

*e.g. `ResourceRepository` works with data & methods that mainly resolves around resources*

### Domains
All Classes are added that will be needed for the project. You can find them in the `classes` directory. 

### H2 Database
- **`colonies`** -> Contains all info about the colonies on Mars
- **`companies`** -> Contains all info about the companies per colonies
- **`shipments`** -> Details the transports between companies.
- **`resources`** -> Contains the resources known in the MarsDex system.
- **`push`** -> A table reserved for endpoints for push notifications.

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
6. Fill the DB with mock data with the [`dbConstruction.sql`](https://github.com/BT-Creator/MarsDex-Server/blob/master/src/test/resources/dbConstruction.sql) script. You can find the script in `src/test/resources/`. Copy it into the web console and execute it.
    - :exclamation: If you're having troubles loading the data in, you might have a database that is filled. You can clean it with the [`dbClean.sql`](https://github.com/BT-Creator/MarsDex-Server/blob/master/src/test/resources/dbClean.sql) script.

Congrats, now you have a MarsDex Server up and running. You'll still need [The MarsDex Client](https://github.com/BT-Creator/MarsDex-Client) in order to interact with it through a website.
