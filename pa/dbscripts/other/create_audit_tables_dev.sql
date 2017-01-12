CREATE TABLE csm_remote_group_sync_record
(
   record_id bigint PRIMARY KEY NOT NULL,
   group_id bigint NOT NULL,
   result varchar(10) NOT NULL,
   message varchar(1000),
   sync_date timestamp NOT NULL
)
;



CREATE TABLE auditlogdetail
(
   id bigint PRIMARY KEY NOT NULL,
   message varchar(256),
   oldvalue text,
   newvalue text,
   attribute varchar(100) NOT NULL,
   record_id bigint NOT NULL
)
;
CREATE TABLE auditlogrecord
(
   id bigint PRIMARY KEY NOT NULL,
   username varchar(100) NOT NULL,
   entityname varchar(254) NOT NULL,
   entityid bigint NOT NULL,
   createddate timestamp NOT NULL,
   transactionid bigint NOT NULL,
   type varchar(255) NOT NULL
)
;


CREATE TABLE prs_sync_history
(
   identifier int PRIMARY KEY NOT NULL,
   sync_date timestamp NOT NULL,
   data text NOT NULL
);