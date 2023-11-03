# Home task solution

## About the API project

It's a Spring Boot 3 project using JDK 17. The dependencies are: 
- spring-starter-web
- spring-data-jpa
- postgres driver
- springdoc-openapi-starter-webmvc-ui ( swagger-ui )
- lombok

## How to spin-up for dev

First you should have a posgtres database up and running. 
There is a docker-compose.yaml that creates the postgres instance, and it's configured in application.properties.
Next step is to run the project on any IDE using the main method on BankingApiApplication.

After running, check http://localhost:8080/swagger-ui/index.html#/ and see swagger-ui.

For creating the customers, run this script into the database: 

```sql
insert into customer values(gen_random_uuid(),'Arisha Barron');
insert into customer values(gen_random_uuid(),'Branden Gibson');
insert into customer values(gen_random_uuid(),'Rhonda Church');
insert into customer values(gen_random_uuid(),'Georgina Hazel');
```

if using the provided docker compose, you can run this one line to create them all: 
```bash
docker exec -t -u postgres banking-api-db psql -d banking-mnpuig -c "insert into customer values(gen_random_uuid(),'Arisha Barron');insert into customer values(gen_random_uuid(),'Branden Gibson');insert into customer values(gen_random_uuid(),'Rhonda Church');insert into customer values(gen_random_uuid(),'Georgina Hazel');"
```

Also, a helpful query to check bank accounts and users
```sql
select b.id as account, b.name as accountName, c.id as customerId, c.name as customerName, b.balance from bank_account b join customer c on b.customer_id = c.id;
```

And using docker
```bash
docker exec -t -u postgres banking-api-db psql -d banking-mnpuig -c "select b.id as account, b.name as accountName, c.id as customerId, c.name as customerName, b.balance from bank_account b join customer c on b.customer_id = c.id" 
```

# Home task requirements

### Objective

Your assignment is to build an internal API for a fake financial institution using Java and Spring.

### Brief

While modern banks have evolved to serve a plethora of functions, at their core, banks must provide certain basic features. Today, your task is to build the basic HTTP API for one of those banks! Imagine you are designing a backend API for bank employees. It could ultimately be consumed by multiple frontends (web, iOS, Android etc).

### Tasks

- Implement assignment using:
  - Language: **Java**
  - Framework: **Spring**
- There should be API routes that allow them to:
  - Create a new bank account for a customer, with an initial deposit amount. A
    single customer may have multiple bank accounts.
  - Transfer amounts between any two accounts, including those owned by
    different customers.
  - Retrieve balances for a given account.
  - Retrieve transfer history for a given account.
- Write tests for your business logic

Feel free to pre-populate your customers with the following:

```json
[
  {
    "id": 1,
    "name": "Arisha Barron"
  },
  {
    "id": 2,
    "name": "Branden Gibson"
  },
  {
    "id": 3,
    "name": "Rhonda Church"
  },
  {
    "id": 4,
    "name": "Georgina Hazel"
  }
]
```

You are expected to design any other required models and routes for your API.

### Evaluation Criteria

- **Java** best practices
- Completeness: did you complete the features?
- Correctness: does the functionality act in sensible, thought-out ways?
- Maintainability: is it written in a clean, maintainable way?
- Testing: is the system adequately tested?
- Documentation: is the API well-documented?

### CodeSubmit

Please organize, design, test and document your code as if it were going into production - then push your changes to the master branch. After you have pushed your code, you may submit the assignment on the assignment page.

All the best and happy coding,

The Builder Prime Team
