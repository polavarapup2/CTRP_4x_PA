DELETE FROM study_protocol
;
DELETE FROM organization
;
DELETE FROM person
;
DELETE FROM patient
;

-- provisioning
DELETE FROM CSM_USER WHERE LOGIN_NAME = 'outcomes';
DELETE FROM CSM_GROUP WHERE GROUP_NAME = 'Outcomes';
INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) VALUES ('Outcomes', 'Outcome Submitter group - security role', (select application_id from csm_application where application_name = 'pa'),current_date);
-- Password is pass
INSERT INTO CSM_USER(LOGIN_NAME, FIRST_NAME, LAST_NAME, PASSWORD, UPDATE_DATE) VALUES ('outcomes', 'Test', 'Outcomes','BtM2GNbiAxg=',current_date);
INSERT INTO CSM_USER_GROUP (USER_ID, GROUP_ID) VALUES ((select user_id from csm_user where login_name = 'outcomes'), (select group_id from csm_group where group_name = 'Outcomes'));


-- studies
INSERT INTO study_protocol (identifier, assigned_identifier, official_title , study_protocol_type, PRI_COMPL_DATE, PRI_COMPL_DATE_TYPE_CODE, START_DATE, START_DATE_TYPE_CODE, status_code, submission_number)  
    values ( 1, 'NCI-2008-00001','Interventional-NCI-2008-00001 test test test test test test test test test test test test test test test test test test test test test test test test',
    'InterventionalStudyProtocol', '2010-12-31', 'ANTICIPATED', '2010-12-31', 'ANTICIPATED', 'ACTIVE', 1);
INSERT INTO study_protocol (identifier, assigned_identifier, official_title , study_protocol_type, PRI_COMPL_DATE, PRI_COMPL_DATE_TYPE_CODE, START_DATE, START_DATE_TYPE_CODE, status_code, submission_number)  
    values ( 2, 'NCI-2008-00002','Observation-NCI-2008-00001','ObservationalStudyProtocol', '2010-12-31', 'ANTICIPATED', '2010-12-31', 'ANTICIPATED', 'ACTIVE', 1);



--- insert into document workflow status

insert into document_workflow_status (identifier,status_code,comment_text,status_date_range_low, study_protocol_identifier, date_last_updated, user_last_updated) 
values (1,'ACCEPTED', NULL, '2008-01-11', 1, current_timestamp, 'abstractor' ) 
;

insert into document_workflow_status (identifier,status_code,comment_text,status_date_range_low, study_protocol_identifier, date_last_updated, user_last_updated) 
values (2,'ACCEPTED', NULL, '2008-02-21', 2, current_timestamp, 'abstractor' ) 
;



-- insert into study_overall_status 

insert into study_overall_status (identifier, status_code, status_date, study_protocol_identifier, date_last_updated, user_last_updated) 
values (1, 'APPROVED', '2008-03-11', 1, current_timestamp, 'abstractor' ) 
;
insert into study_overall_status (identifier, status_code, status_date, study_protocol_identifier, date_last_updated, user_last_updated) 
values (2, 'APPROVED', '2007-07-21', 2, current_timestamp, 'abstractor' ) 
;

-- eligibility criterion
insert into planned_activity (category_code, study_protocol_identifier, planned_activity_type, inclusion_indicator, criterion_name, eligible_gender_code)
  values ('ELIGIBILITY_CRITERION', 1, 'PlannedEligibilityCriterion', true, 'GENDER', 'MALE');

--- organization insert
insert into organization (identifier, name, assigned_identifier, date_last_updated, user_last_updated ,status_code) 
values (1, 'Oraganization A', '1', current_timestamp, 'po' ,'ACTIVE') 
;

insert into organization (identifier, name, assigned_identifier, date_last_updated, user_last_updated ,status_code) 
values (2, 'Oraganization B', '2', current_timestamp, 'po' ,'ACTIVE') 
;

insert 
into organization (identifier, name, assigned_identifier, date_last_updated, user_last_updated  ,status_code) 
values (3, 'Oraganization C', '3', current_timestamp, 'po' ,'ACTIVE') 
;

insert into organization (identifier, name, assigned_identifier, date_last_updated, user_last_updated  ,status_code) 
values (4, 'Oraganization D', '4', current_timestamp, 'po' ,'ACTIVE') 
;
insert into organization (identifier, name, assigned_identifier, date_last_updated, user_last_updated  ,status_code) 
values (5, 'Oraganization E', '5', current_timestamp, 'po' ,'ACTIVE') 
;

---- healthcare_facility

insert into healthcare_facility (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (1, 'PO001', 1, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated,status_code) 
values (2, 'PO002', 2, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (3, 'PO003', 3, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (4, 'PO004', 3, current_timestamp, 'po' ,'ACTIVE') 
;

insert into healthcare_facility (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (5, 'PO005', 2, current_timestamp, 'po' ,'ACTIVE') 
;
insert into healthcare_facility (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (6, 'PO006', 1, current_timestamp, 'po' ,'ACTIVE') 
;

---- research_organization

insert into research_organization (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (1, 'PO001', 1, current_timestamp, 'po' ,'ACTIVE') 
;

insert into research_organization (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated,status_code) 
values (2, 'PO002', 2, current_timestamp, 'po' ,'ACTIVE') 
;

insert into research_organization (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (3, 'PO003', 3, current_timestamp, 'po' ,'ACTIVE') 
;

insert into research_organization (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (4, 'PO004', 3, current_timestamp, 'po' ,'ACTIVE') 
;

insert into research_organization (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (5, 'PO005', 2, current_timestamp, 'po' ,'ACTIVE') 
;
insert into research_organization (identifier, assigned_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (6, 'PO006', 1, current_timestamp, 'po' ,'ACTIVE') 
;



insert into study_site 
       (identifier, functional_code, local_sp_indentifier, study_protocol_identifier, research_organization_identifier ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (1,'LEAD_ORGANIZATION','CALGB-88612-A443',1, 1, 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;
 
insert into study_site 
       (identifier, functional_code, local_sp_indentifier, study_protocol_identifier, research_organization_identifier , status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (2,'LEAD_ORGANIZATION','CCG-09032-A443',2 ,2,'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;

insert into study_site 
       (identifier, functional_code, local_sp_indentifier, study_protocol_identifier, healthcare_facility_identifier ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (3,'TREATING_SITE','CALGB-88612-A443',1, 1, 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;
 
insert into study_site 
       (identifier, functional_code, local_sp_indentifier, study_protocol_identifier, healthcare_facility_identifier , status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (4,'TREATING_SITE','CCG-09032-A443',2 ,2,'ACTIVE', current_timestamp, current_timestamp , 'abstractor' )
; 

insert into study_site 
       (identifier, functional_code, local_sp_indentifier, study_protocol_identifier, healthcare_facility_identifier ,status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (5,'TREATING_SITE','CALGB-88612-A443',1, 2, 'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;
 
insert into study_site 
       (identifier, functional_code, local_sp_indentifier, study_protocol_identifier, healthcare_facility_identifier , status_code, status_date_range_low, date_last_updated, user_last_updated ) 
values (6,'TREATING_SITE','CCG-09032-A443',2 ,1,'ACTIVE', current_timestamp, current_timestamp , 'abstractor' ) 
;


------ person -----------------

insert into person (identifier, assigned_identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (1, '1', 'John','D', 'Doe', current_timestamp, 'po' ,'ACTIVE')  
;
insert into person (identifier, assigned_identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (2, '2', 'Mark','f', 'Smith', current_timestamp, 'po' ,'ACTIVE')  
;
insert into person (identifier, assigned_identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (3, '3', 'Mary','Ann', 'Jones', current_timestamp, 'po' ,'ACTIVE') 
;
insert into person (identifier, assigned_identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (4, '4', 'Jack',null, 'Nick', current_timestamp, 'po' ,'ACTIVE')  
;
insert into person (identifier, assigned_identifier , first_name, middle_name, last_name,   date_last_updated, user_last_updated , status_code)
values (5, '5', 'Samuel','k', 'Smith', current_timestamp, 'po' ,'ACTIVE')  
;

------- heacltcare provider ------

insert into healthcare_provider (identifier, person_identifier, organization_identifier, assigned_identifier , date_last_updated, user_last_updated, status_code) 
 values (1, 1,  1, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (identifier, person_identifier,  organization_identifier, assigned_identifier , date_last_updated, user_last_updated, status_code) 
 values (2, 2,  2, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (identifier, person_identifier,  organization_identifier, assigned_identifier , date_last_updated, user_last_updated, status_code) 
 values (3, 3,  3, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (identifier, person_identifier,  organization_identifier, assigned_identifier , date_last_updated, user_last_updated, status_code) 
 values (4, 4,  1, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (identifier, person_identifier,  organization_identifier, assigned_identifier , date_last_updated, user_last_updated , status_code)
 values (5, 1,  2, 'po', current_timestamp, 'po' ,'ACTIVE')  
;
insert into healthcare_provider (identifier, person_identifier,  organization_identifier, assigned_identifier , date_last_updated, user_last_updated, status_code) 
 values (6, 2,  3, 'po', current_timestamp, 'po' ,'ACTIVE')  
;



insert into CLINICAL_RESEARCH_STAFF (identifier, assigned_identifier, person_identifier, organization_identifier, date_last_updated, user_last_updated ,status_code) 
values (1, 'PO001', 1, 1, current_timestamp, 'po' ,'ACTIVE') 
;

--------------- study contact -----------

insert into study_contact (identifier,role_code,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_identifier, 
    healthcare_provider_identifier,study_protocol_identifier, status_code, status_date_range_low, date_last_updated,user_last_updated ,clinical_research_staff_identifier) 
values(1, 'STUDY_PRINCIPAL_INVESTIGATOR','true','2432','terra cot','herdon','de','11111',null,1,1,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ,1) 
;
 
insert into study_contact (identifier,role_code,primary_indicator,address_line, delivery_address_line, city,state, postal_code,country_identifier, 
    healthcare_provider_identifier,study_protocol_identifier, status_code, status_date_range_low, date_last_updated,user_last_updated ,clinical_research_staff_identifier)  
values(2, 'STUDY_PRINCIPAL_INVESTIGATOR','true','2432','terra cot','herdon','de','11111',null,2,2,'ACTIVE','2008-09-01', current_timestamp , 'abstractor' ,1) 
;


----------------- study site accrual access --------------------------------
insert into study_site_accrual_access (csm_user_id, study_site_identifier, request_details, status_code, status_date_range_low)
values ((select user_id from csm_user where login_name = 'submitter'), 3, 'details', 'ACTIVE', '1/1/2009');

insert into study_site_accrual_access (csm_user_id, study_site_identifier, request_details, status_code, status_date_range_low)
values ((select user_id from csm_user where login_name = 'submitter'), 5, 'details', 'ACTIVE', '1/1/2009');


----------------- submission -------------------------------------------------
insert into submission (label, description, cut_off_date, status_code, status_date_range_low, status_date_range_high, study_protocol_identifier)
  values ('01', 'description', '9/30/2009', 'OPENED', '1/1/2009', null, 1);
insert into patient (identifier, assigned_identifier, race_code, sex_code, ethnic_code, birth_date, status_code, status_date_range_low)
	values (1,'1', 'HAWAIIAN', 'FEMALE', 'NOT_HISPANIC', '1/1/1988', 'ACTIVE', '6/1/2009');
insert into study_subject (identifier, patient_identifier, study_protocol_identifier, study_site_identifier, payment_method_code, 
            status_code, status_date_range_low, assigned_identifier)
	values (1, 1, 1, 3, 'PRIVATE', 'ACTIVE', '6/1/2009', 'Patient 001');
insert into performed_activity (identifier, study_protocol_identifier, performed_activity_type, study_subject_identifier, registration_date)
        values (1, 1, 'PerformedSubjectMilestone', 1,'6/1/2009');

  