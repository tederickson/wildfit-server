# wildfit-server

REST server for WILDFIT application

The WILDFIT app is an innovative solution to support
individuals on their health and wellness journey.

# Documentation

Swagger provides the API documentation of the 28+ REST endpoints.  
Run the application and point a browser to http://localhost:8080/swagger-ui.html

# Run the Application

Run the following command in a terminal window:

```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
```

# Config

Only the src/main/resources/application.yml file is stored in Git.  
The rest are local files to prevent leaking sensitive information.

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

# Test

run `mvn clean test -Dspring.profiles.active=dev` to run all JUnit tests.

The current test environment utilizes SpringBootTest, JsonTest and Mockito to achieve a 97% test coverage.

The JaCoCo reports are available at [wildfit-server](http://localhost:63342/wildfit-server/server/target/site/jacoco/index.html#up-c).

# Database

The original design had a table for recipe instructions and another table for ingredients.

* 9 ingredients
* 4 instructions

Query for the ingredients and the instructions based on recipe id resulted in the cartesian product (4 * 9) of 36 rows.

The way to solve this is by a joined table strategy:

1. Use an abstract parent class
2. Add an enum stored in the database as text depicting the child class type
3. Use PrimaryKeyJoinColumn annotation and provide the same name in child classes

Separate tables are created for the parent and child classes.

The primary id of the child classes is the primary key of the parent class.  
This ensures unique key values between the child classes.  
Otherwise would need a compound index to ensure uniqueness.

Storing the enum name is better than the default enum number because if someone inserts a
new enum value the database does not have to be updated with the new number.

Query for the parent class returns 13 rows.  
Accessing the data returns 9 ingredients and 4 instructions.
The enum provides efficient mapping of the database row to a Java class.

![WildFit database](WildFitSchema.png "WildFit Schema")

# Code
The code is broken up into:
* config - almost all the stuff needed to run the app
* deserialization - custom deserialization code that converts JSON to Java
* model - the DTO (Data Transfer Objects) that talk to the database
* exception - the application specific Exceptions
* manager - some folks call this package rest - it contains the RestControllers which process the URLs that interact with the application.
* domain - the REST request/response domain objects that are converted to JSON
* mapper - the glue coded needed to convert JSON to Java or Database rows to Java
* repository - surprisingly simple way to create a SQL command. Turn on Spring Boot parameters to view the generated SQL.
* service - the methods called by the managers/controllers.
* handlers - the only purpose of these classes called by the service is to mitigate merge hell and provide unique test 
classes for the handlers. Otherwise you end up with 10,000+ lines of unit test cases for each service that are difficult to refactor.  

The current stats are that every line of code generates 10 lines of test code.  
That is because you have to mock out services and mimic return values.
