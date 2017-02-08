--Author   : Reshma Koganti
--Date     : 06/25/2013    
--Jira#    : PO-4877 Track users' semi-annual reviews and updates to their trials 
--Comments : creating new table for storing trial verification data

CREATE TABLE TRIAL_DATA_VERIFICATION
(
   identifier SERIAL NOT NULL,
   verification_method varchar(200) NOT NULL,
   date_last_created timestamp without time zone,
   date_last_updated timestamp without time zone,
   user_last_created_id integer,
   user_last_updated_id integer,
   study_protocol_identifier bigint NOT NULL,
   CONSTRAINT trialData_pkey PRIMARY KEY (identifier),
   CONSTRAINT fk_trial_data_study_protocol FOREIGN KEY (study_protocol_identifier)
      REFERENCES study_protocol (identifier) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
   CONSTRAINT fk_trial_data_created_csm_user FOREIGN KEY (user_last_created_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_trial_data_updated_csm_user FOREIGN KEY (user_last_updated_id)
      REFERENCES csm_user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


