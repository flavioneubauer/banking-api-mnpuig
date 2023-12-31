# Flavio Neubauer Task Log

1. after reading README file I started to set the basic requirements for the task:

1.1 Project using Java and Spring

Decided to create a Spring boot project using https://start.spring.io/. Selected Web, lombok, Data Jpa, Postgres, actuator and dev tools as dependencies.

Also selected jdk 17 for this project.

After that, got a docker-compose with a simple postgres to spin up the database for testing and configured application.properties. 

Now, I just ran the application and checked the it is up and running.

Also added a .gitignore to avoid commiting build files.

1.2 Entity mapping and project package structure

In this first step I'm just mapping packages and dividing the features required by the task.

```
BankAccount
    controller
        BankAccountController
    business
        create(deposit amount)
        getBalances(accountId)
    model
        BankAccountEntity
            id
            owner
            balance
Customer
    model
        CustomerEntity
            id
            name
Transfer
    controller
        TransferController
    business
        create(source, target, amount)
        history(accountId)
    model
        TransferEntity
            id
            source
            target
            amount
```


In this first step I will create it as a monolith cause we don't actually have any real numbers on the amount of requests, but this service can be scaled up vertically or horizontally if needed.

1.3 Business and Controller setup

After creating the main classes and packages I started to implemnt the first part: the bank account creation.

Had some issues with the testing framework. Wanted to use mockit but for some reason some of the annotations and mocking was not working.

So, since we have a restricted timebox will implement all the features and get back to it with a little bit more time.

1.4 - Main APIs done

One thing I forgot to log before is that the authentication / authorization part is not included in this task right now.
With more time I would implement a Spring Security and validate if the bank accounts belong to the owner.

1.5 Ending the time

Just to log that I tried to solve the issue with spring and mockito but it doesn't seem to work.

In the beginnig of this task I thought about using a clean architecture to separate the business logic from the infraestructure, but for a small project I thought it would be too much and should be straight forward to mock repository layer.

1.6 After ending time

So, I couldn't get over the fact that the tests were not working for me and started digging into it. 
In the last couple of years I've been using Quarkus Framework and a tipical test class goes like this: 

```java
@QuarkusTest
class ServiceTest {
	
	@InjectMock
    SomeRepository someRepository;

	@InjectMock
	SomeOtherService someService;
	
	@Inject
    Service service;
}
```

Looking into some guides was a lot of types using version 2 of Spring ( I've chosen version 3 ). 
After more digging and testing and found out what was the problem. 

I was adding @SpringBootTest, as I thought it was like QuarkusTest. 
Also, I thought InjectMocks annotation was supposed to be the same behavior as the InjectMock from Quarkus, but is the other way round.

So, after it worked and the comparative is: 

@QuarkusTest ~= @ExtendWith(MockitoExtension.class)

@InjectMock ~= @Mock

@Inject     ~= @InjectMocks

With this, the mocking part just worked and I could finish my tests.

2. Final

- docker compose up to get the db up
- access http://localhost:8080/swagger-ui/index.html#/