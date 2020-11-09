# Project II - Mars Server
This is the **server side start-project** for Project II. 

You can change the whole code to the wishes of your team.

The start project provides the basic scaffolding for an openapi webserver.

## Before you start:
- Choose Zulu jdk version 11 (Configure through this through intelij)
- Install the sonarlint plugin through intelij plugins.
- **Assign your group number** to the following locations (change XX by your group number, 2 digits, zero-padding):
  - `settings.gradle`: `rootProject.name = "2020.project-ii.mars-server-XX"`
  - `gradle.properties`: `systemProp.sonar.projectName=2020.project-ii.mars-server-XX`
  - `openapi-group-XX.yaml`: `https://project-ii.ti.howest.be/mars-XX`
  - `WebServer.java`: `OPEN_API_SPEC = "openapi-group-XX.yaml";`
  - `replace XX in file name openapi-group-XX.yaml`
  - Search the string XX in all files (ctr shift f)
    - There should only be readme entries

- **Attach the TI sonarqube server to your project in intelij.**
    - Go to Intelij settings/other settings/SonarLint General Settings
        - Create a new connection by clicking on the plus sign.
        - Choose a configuration name.
        - Choose sonarcube.
        - Enter URL: https://sonar.ti.howest.be/sonar
        - Use token: `a71c618ef467e72256e59bbbb48a8eb441cf3629`
        - Save configuration.
    - Go to Intelij settings/other settings/SonarLint Project Settings
        - Choose your newly created connection.
        - Choose your project by clicking **search in list.**
    - Go to Intelij settings/other settings/SonarLint General Settings
        - Click update binding (no error messages should pop up).
- At the bottom of your screen a sonarlint tab should be available.
    - Code smells will be available at this location.

## How to run the start project
In Intelij choose gradle task run.

## What is included
  - a very basic openapi spec
    - localhost:8080/api/message
  - H2 database web console
  - The setup of a vert.x web api (WebServer.java)
    - It's allowed to change this file.
  
## Local locations
 - H2 web client
   - localhost:9000
   - url: ~/mars-db
   - no credentials
 - Web api
   - localhost:8080/api/message
   - map openapi paths to the MarsOpenApiBridge in the WebServer.java
     - function: addRouteWithCtxFunction
  
## Public locations
 - H2 web client
   - https://project-ii.ti.howest.be/db-XX
   - url: jdbc:h2:/opt/group-XX/db-XX
   - username:group-XX
   - password: see leho
 - Web api
   - https://project-ii.ti.howest.be/mars-XX/api/
 - Web client
   - https://project-ii.ti.howest.be/mars-XX
 - Sonar
   - https://sonar.ti.howest.be/sonar/dashboard?id=2020.project-ii%3Amars-server-XX
   - https://sonar.ti.howest.be/sonar/dashboard?id=2020.project-ii%3Amars-client-XX