## Running the Project in Development Mode

`mvn spring-boot:run`

Wait for the application to start

Open http://localhost:8080/ to view the application.

Also, Intellij can detect this as a Spring Boot io.sapiens.retail.Application and setup everything to the io.sapiens.retail.Application.java. So, if we use Intellij, we should run the io.sapiens.retail.Application.java to use the debugger


## Project structure

- Awesome - The framework that wraps up everything and also provides the template to start the project
- Retail - The actual project that we need to implement

## Running the Project in Production Mode

`mvn spring-boot:run-exploded -Dvaadin.productionMode`

The default mode when the application is built or started is 'development'. The 'production' mode is turned on by setting the `vaadin.productionMode` system property when building or starting the app.

Note that if you switch between running in production mode and development mode, you need to do
```
mvn clean
```
before running in the other mode.

