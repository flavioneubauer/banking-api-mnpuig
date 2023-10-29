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

In this first step I will create it as a monolith cause we don't actually have any real numbers on the amount of requests, but this service can be scaled up vertically or horizontally if needed.