INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctgov.api.getByNct', 'http://clinicaltrials.gov/show/${nctid}?displayxml=true');
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctgov.api.searchByTerm', 'http://clinicaltrials.gov/search?term=${term}&studyxml=true');

INSERT INTO csm_user (login_name,first_name,last_name,organization,department,title,phone_number,password,email_id,start_date,end_date,update_date,migrated_flag,premgrt_login_name,automated_curation) 
    VALUES ('ctgovimport','CT.Gov Import','',null,null,null,null,'',null,null,null,current_date,0,null,null);
    

INSERT INTO registry_user (identifier, first_name,middle_name,last_name,address_line,city,state,postal_code,country,phone,affiliate_org,csm_user_id,date_last_created,date_last_updated,prs_org_name,po_organization_id,po_person_id,email_address,affiliated_org_id,affiliated_org_user_type,user_last_created_id,user_last_updated_id,enable_emails,site_accrual_submitter,family_accrual_submitter) 
    VALUES (nextval('hibernate_sequence'), 'CT.Gov Import',null,'','','','','','','','ClinicalTrials.gov',
        (select user_id from csm_user where csm_user.login_name='ctgovimport' limit 1),
        current_date,null,'ClinicalTrials.gov',null,null,'ctgovimport@example.com',
        (select cast(assigned_identifier as int8) from organization where organization.name='ClinicalTrials.gov' limit 1),
        'MEMBER',null,null,false,false,false);
    
