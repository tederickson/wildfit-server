# wildfit-server

REST server for WILDFIT application

The WILDFIT app is an innovative solution to support
individuals on their health and wellness journey.

# Documentation

Swagger provides the API documentation.  
Run the application and point a browser to http://localhost:8080/swagger-ui.html

# Run the Application

Run the following command in a terminal window:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

# Config

Only the application.properties file is stored in Git.  
The rest are local files to prevent leaking sensitive information.

* src/main/resources/application.properties
    * Common configuration values
* src/main/resources/application-dev.properties
    * Development configuration values
* flyway.conf
    * Flyway configuration

# Wildfit Server uses https://www.nutritionix.com/business/api

Required HEADERS when accessing Nutritionix V2 API endpoints:

* x-app-id: Your app ID issued from developer.nutritionix.com
* x-app-key: Your app key issued from developer.nutritionix.com
* x-remote-user-id:  A unique identifier to represent the end-user who is accessing the Nutritionix API. If in
  development mode, set this to 0. This is used for billing purposes to determine the number of active users your app
  has.

Please note, when authenticating with the API, you must send the x-app-id and x-app-key params as headers, and not as
query string parameters.

