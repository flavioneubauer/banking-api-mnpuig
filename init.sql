-- create customers

insert into customer values(gen_random_uuid(),'Arisha Barron');
insert into customer values(gen_random_uuid(),'Branden Gibson');
insert into customer values(gen_random_uuid(),'Rhonda Church');
insert into customer values(gen_random_uuid(),'Georgina Hazel');

-- helpfull query
select b.id as account, c.id as customerId, c.name as customerName, b.balance from bank_account b join customer c on b.customer_id = c.id;

-- example of customers after creation
                  id                  |      name      
--------------------------------------+----------------
 33628ec9-30e4-47e1-82a1-722ee9cdd636 | Arisha Barron
 550f9ccb-9b68-4b06-be14-42c8956fcdd7 | Branden Gibson
 ab001847-e986-497c-b56b-15c02852c13a | Rhonda Church
 bd03b336-c25f-4a93-8e42-ded4484b389d | Georgina Hazel
