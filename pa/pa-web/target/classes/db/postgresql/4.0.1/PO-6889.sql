CREATE TABLE ACCRUAL_OUT_OF_SCOPE_TRIAL (
    IDENTIFIER BIGINT NOT NULL,
    CTEP_ID VARCHAR(50) NOT NULL,
    FAILURE_REASON VARCHAR(255),   
    SUBMISSION_DATE TIMESTAMP,     
    USER_ID BIGINT,
    CTRO_ACTION VARCHAR(255),   
    PRIMARY KEY (IDENTIFIER)
);


ALTER TABLE ACCRUAL_OUT_OF_SCOPE_TRIAL ADD CONSTRAINT FK_001
    FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID) 
    ON UPDATE NO ACTION ON DELETE CASCADE;
    
CREATE UNIQUE INDEX accrual_out_of_scope_trial_idx01 ON accrual_out_of_scope_trial (ctep_id);

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'accrual.outofscope.actions','Rejected;Out of Scope');