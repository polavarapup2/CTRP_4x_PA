CREATE TABLE accounts
(
   account_name varchar NOT NULL,   
   external_system varchar NOT NULL,
   username varchar NOT NULL,
   encrypted_password varchar NOT NULL,
   PRIMARY KEY (account_name)
);
