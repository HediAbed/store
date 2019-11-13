# Introdection

This Project is an example of an eCommerce for selling books: where there are two roles:
* ROLE_ADMIN: can manage books (In Back-office).
* ROLE_CLIENT: buy books on the store

# Remarque

* I did'n create a FrontEnd application yet

* I made just some tests for some user services

![](images/test.PNG)

# RDB Conception

![](images/br.png)

# Tech

* [SpringBoot](https://spring.io/projects/spring-boot) - Framwork that reduce boilerplate configuration of Spring application and running with the smallest amount of code.
* [JPA] - A specification that defines the management of relational data in a Java application. 
* [Hibernate] - ORM and JPA implementation.
* [postgresql](https://www.postgresql.org) - free and open-source relational database 
* [JavaMail] - Java API used to send and receive email via SMTP
* [thymeleaf](https://www.thymeleaf.org) - A template engine (I used it to generate Order template before sending it by email)
* [MapStruct](https://mapstruct.org)- A code generation library that generates object mappers
* [Swagger2](https://swagger.io/) - HTML to Markdown converter
* [Mockito] - is an open source testing framework 


# Start project 

Use maven to run the following command 

```sh
$ mvn spring-boot:run
```

Navigate to the url below to browse the Rest Api documentation.

```sh
http://localhost:8080/swagger-ui.html
```


These endpoints they don't need authentication

book-resource:
  *  /books/active
  *  /books/details/{id}
  *  /search/{keyword}
  
user-resource
  * /users/create
  * /users/forgetPassword
 
Also only ROLE_ADMIN can create update delete a Book
 
![](images/sw1.PNG)
## OAUTH

Token URL: http://localhost:8080/oauth/token
Flow: password
username : admin
password : admin
client : client
secret : secret
scope : trust

#### Swagger Steps
##### 

 1 . Click on authorize button

![](images/sw2.PNG)

##### 
 2 . copy-paste | insert credentials 

![](images/sw4.PNG)

#### 
 3 . quit authorization view after success authentication 
 4 . access services

![](images/sw5.PNG)

#### POSTMAN Steps

1. select authorization
2. select oauth2 type
3. select get new access token
4. type credentials
5. select request token
![](images/posm1.PNG)
![](images/posm2.PNG)
![](images/posm3.PNG)
#### Manual Integration via postman Steps
##### Setting Method Type | URL

URL: http://localhost:9000/oauth/token
Method Type : POST
Content-Type : application/x-www-form-urlencoded
![](images/posm4.PNG)

##### Generate basic token

For example : https://www.blitter.se/utils/basic-authentication-header-generator/
* username => client
* password => secret

![](images/boauth2.PNG)

##### Setting Basic token in http header

Authorization: Basic Y2xpZW50OnN6Y3JldA==

![](images/posm5.PNG)

##### Setting the body request and send it

grant_type password

username admin

password admin

scope trust

![](images/posm6.PNG)

##### Response contains:
#
```
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3RvcmUtcmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJ0cnVzdCJdLCJleHAiOjE1NzM1Mjc4MjEsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiNzk5ZWQzN2QtOWQzNy00NDczLThiZjgtZTBjMzliNTg0YWY4IiwiY2xpZW50X2lkIjoiY2xpZW50In0.w6pYuNpJXGonrhHYUnxtlQLd8JeFqfqFFE_De0lS7SE",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic3RvcmUtcmVzb3VyY2UiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJ0cnVzdCJdLCJhdGkiOiI3OTllZDM3ZC05ZDM3LTQ0NzMtOGJmOC1lMGMzOWI1ODRhZjgiLCJleHAiOjE1NzYxMTk3NjEsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiOTdiNmVkNjAtNjk3MS00MTg5LThhNTktMTI2MGIwYjVkODYxIiwiY2xpZW50X2lkIjoiY2xpZW50In0.mhCj4W7KaI4yEAIBMv-IzoARZhrZQ1iJDL3YOt2zG0A",
    "expires_in": 59,
    "scope": "trust",
    "jti": "799ed37d-9d37-4473-8bf8-e0c39b584af8"
}
```
