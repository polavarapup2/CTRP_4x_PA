CREATE TABLE email_log
(
   identifier serial NOT NULL,
   date_sent timestamp NOT NULL,
   outcome varchar NOT NULL,
   errors varchar,
   sender varchar NOT NULL,
   recipient varchar NOT NULL,
   cc varchar,
   bcc varchar,
   subject varchar,
   body text,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);

CREATE TABLE email_attachment
(
   identifier serial NOT NULL,
   email_id int8,
   filename varchar,
   data bytea,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);

ALTER TABLE email_attachment ADD CONSTRAINT email_attachment_email_id FOREIGN KEY (email_id) 
    REFERENCES email_log (identifier) ON DELETE CASCADE;
