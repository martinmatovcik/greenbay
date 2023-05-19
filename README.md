# Greenbay - buy&sell app
Greenbay is a simplified eBay clone.  
The project was developed in Java and Spring Boot. As a build tool, it is using Gradle and it has only a back end.  
_This is only example project do demonstrate my skills._
### Here is how it works:
As a user, you can easily register and log in using Spring Validation. After successful login, you can receive generated JWT token. This process is made possible with Spring Security. Every endpoint apart from login and register is secured and requires authentication and authorization. There are two types of user roles - USER and ADMIN. User with USER role is able to list products for sale and buy or bid on products. On the other hand, user with an ADMIN role can also update or delete products. When creating the product using a dedicated endpoint, you need to provide a valid @RequestBody in JSON format. If successful the product will be stored in the MySQL database using Spring Data JPA. The recently-created product has some predefined values e.g. empty list of Bids. The database in this project was made and maintained using Flyway. Testing of endpoints was made in Postman. The code has unit tests as well as integration tests. It was done using jUnit 5, Mockito, and MockMVC. Version control of this project was done using Git and GitHub. The development environment was IntelliJ IDEA.  
### Technology used:  
+ Java, Spring Boot
+ Gradle
+ Spring Security
+ JWT token
+ Spring Validation
+ JSON
+ Spring Data JPA, MySQL, Flyway
+ Postman, jUnit 5, Mockito, Mock MVC
+ Git, GitHub
+ IntelliJ IDEA
