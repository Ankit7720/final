# Assignement
Steps to run
---------------------------------------------
-Build the project using:     mvn clean install
-Run using :                  mvn spring-boot:run
---OR------
-Directly Run AssignementApplication.java From IDE.

-The web application is accessible via localhost:9090
-Need Application like postman(api testing) to access api

FOR APIs - (localhost:9090/assignement/admin/**) 
-in postman enable Authorization -->Basic Auth-->
-Use username="admin"
     password="admin123"
     

FOR APIs - (localhost:9090/assignement/admin/**) 
-in postman enable Authorization -->Basic Auth-->
-Use username="jerry"
     password="jerry123"

     
For POST and PUT request
body:application/json
{
    "username": "ankit",
    "password": "ankit123",
    "roles": "USER",
    "active": true
}


Database
It uses a H2 in memory database (for now), can be changed easily in the application.properties for any other database.
