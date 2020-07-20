# team-sava SDK for user management in the mongo database: team-sava-sdk
1. install docker
2. pull mongo official image from docker from docker hub
     docker pull mongo:bionic
3. create and start docker container
     docker run -p 27017:27017 --name mongo -d mongo:bionic
4. start the mongo client to create the database
     - docker exec -it mongo bash
     - mongo
     - use TeamSavaUsers to create the database
5. pull the code from the repository, clean it and build
     mvn clean install
6. run it, check the swagger api and test it thru it
     - mvn spring-boot:run
     - in the browser navigate to http://localhost:10112/swagger-ui.html

