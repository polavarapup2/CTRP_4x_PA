CREATE TABLE prs_sync_history
(
   identifier serial NOT NULL,   
   sync_date timestamp NOT NULL,
   data text NOT NULL,
   PRIMARY KEY (identifier)
) WITH (OIDS=FALSE);