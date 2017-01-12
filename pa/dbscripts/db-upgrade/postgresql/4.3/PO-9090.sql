insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'twitter.enabled','false');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'twitter.api.url','https://api.twitter.com/1.1/');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'twitter.account','twitter.default');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'twitter.queue.process.schedule','0 0 * * * ?');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),'twitter.timeout.read','60000');

INSERT INTO accounts (account_name,external_system,username,encrypted_password) 
    VALUES ('twitter.default.consumerkey' ,'TWITTER' ,'Iwl54IThlIunRdrMr9A5wyr2z' ,'' );
INSERT INTO accounts (account_name,external_system,username,encrypted_password) 
    VALUES ('twitter.default.accesstoken' ,'TWITTER' ,'3389288896-BlHfFTnlL1nZtLRhwYHftcJaSi5Ag1J76l96ALz' ,'' );
    
CREATE TABLE tweets
(
  identifier serial NOT NULL,
  tweet_text varchar(140) NOT NULL,
  status varchar NOT NULL,
  study_protocol_identifier bigint,
  create_date timestamp without time zone NOT NULL,
  sent_date timestamp without time zone,
  account_name varchar,
  errors varchar,  
  CONSTRAINT tweets_pk PRIMARY KEY (identifier),
  CONSTRAINT fk_study_protocol_identifier FOREIGN KEY (study_protocol_identifier)
      REFERENCES study_protocol (identifier) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE SET NULL
);


