--Author   : Reshma Koganti
--Date     : 7/18/2014        
--Jira#    : PO-6893 Biomarkers - Allow capture and display of biomarker synonyms
--            in CTRP separately from the root marker name 

CREATE TABLE PLANNED_MARKER_SYNONYMS
(
   IDENTIFIER SERIAL NOT NULL,
   ALTERNATE_NAME varchar(2000) NOT NULL,
   PM_SYNC_IDENTIFIER BIGINT,
   STATUS_CODE varchar(200) NOT NULL,
   date_last_created timestamp without time zone,
   date_last_updated timestamp without time zone,
   user_last_created_id integer,
   user_last_updated_id integer,
   PRIMARY KEY (IDENTIFIER),
   CONSTRAINT fk_pm_synonym_sync_id FOREIGN KEY (PM_SYNC_IDENTIFIER)
      REFERENCES PLANNED_MARKER_SYNC_CADSR (IDENTIFIER) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_pm_synonym_created_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_pm_synonym__updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


ALTER TABLE PLANNED_MARKER_SYNC_CADSR ADD COLUMN nt_term_identifier varchar(200);