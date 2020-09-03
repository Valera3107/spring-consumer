GET

http://localhost:8080/company/  - get all companies

http://localhost:8080/company/{symbol}  - get company by symbol

http://localhost:8080/company/price/{symbol} - get company price by symbol and time period

Running the application locally: 

There are several ways to run a Spring Boot application on your local machine.
One way is to execute the main method in the ua.com.todo.Application class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

mvn spring-boot:run


With Docker image v4lera/spring-consumer and command:
 
docker run -it v4lera/spring-consumer

Also you have to configure spring application properties.

What can be improved:
- Add spring application properties to dockerfile ENV
- Find out better endpoint of Iextrading for faster reading data
- Make code refactoring for service layer (create interface)