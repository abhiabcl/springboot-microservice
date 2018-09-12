# springboot-microservice

This project is demo project of micro service using spring boot. 

Project has three service. 
1. edge-service
2. eurekaService
3. item-Catlog

eurekaService
This service work as naming service. It's like service register, every other service should be register with this service. That mean all other service information is available to this service.

Spring Boot Project dependencies
Eureka Server - Server for service lookup.

item-Catlog
This service has a actual implementation of service. All item API implementation is available with this service. And it should register with eurekaService.

Spring Boot Project dependencies

Actuator - To monitor and maintain 
Eureka Discovery - To register service with Eureka Server
JPA - To Data processing
H2 - To H2 in memory database.
RestRepositories - DAO api as REST 
Web - In build tomcat server, MVC
Dev Tool - To auto update changes
Lombok - To avoid boiler plate codes.



edge-service
This service is edge service, it is also register with eurekaService. This service is ready to interact with consumer and it's also using the fall back technique, it should response back valid data in case of non availability of any other service.

Spring Boot Project dependencies

Actuator - To monitor and maintain 
Eureka Discovery - To register service with Eureka Server
Feign - Rest client.
Zuul - As Proxy and dynamic routing to the api calls.
RestRepositories - DAO api as REST 
Web - In build tomcat server, MVC
Hystrix - Circuit breaker, for fall back. 
Lombok - To avoid boiler plate codes.

How to create project:
Import as maven project to eclipse.

How to run:
Run eurekaService first, so it will be available to register other service and then run other service.


