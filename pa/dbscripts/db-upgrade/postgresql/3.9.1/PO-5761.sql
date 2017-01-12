--Author   : Reshma Koganti
--Date     : 10/05/2012        
--Jira#    : PO-5761Biomarker - Start capturing caDSR public IDs in CTRP db 
--Comments : creating new table for storing permissible values of biomarker. 

CREATE TABLE PLANNED_MARKER_SYNC_CADSR
(
   IDENTIFIER SERIAL NOT NULL,
   NAME varchar(2000) NOT NULL,
   MEANING varchar(2000),
   DESCRIPTION varchar(4000),
   caDSRId integer,
   STATUS_CODE varchar(200) NOT NULL,
   date_last_created timestamp without time zone,
   date_last_updated timestamp without time zone,
   user_last_created_id integer,
   user_last_updated_id integer,
   PRIMARY KEY (IDENTIFIER),
   CONSTRAINT name_sync_unique UNIQUE (NAME),
   CONSTRAINT CAdsrid_sync_unique UNIQUE (caDSRId),
   CONSTRAINT fk_planned_sync_created_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_planned_sync_updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


