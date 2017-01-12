DROP INDEX IF EXISTS study_checkout_user_identifier;
create index study_checkout_user_identifier on study_checkout (user_identifier);

DROP INDEX IF EXISTS study_checkout_checkout_type_checkin_date;
create index study_checkout_checkout_type_checkin_date on study_checkout (checkout_type, checkin_date);

DROP INDEX IF EXISTS study_overall_status_status_date;
create index study_overall_status_status_date on study_overall_status (status_date);

DROP INDEX IF EXISTS clinical_research_staff_person_identifier;
create index clinical_research_staff_person_identifier on clinical_research_staff (person_identifier);

DROP INDEX IF EXISTS study_contact_clinical_research_staff_identifier;
create index study_contact_clinical_research_staff_identifier on study_contact (clinical_research_staff_identifier);

DROP INDEX IF EXISTS study_contact_healthcare_provider_identifier;
create index study_contact_healthcare_provider_identifier on study_contact (healthcare_provider_identifier);

DROP INDEX IF EXISTS study_contact_role_code;
create index study_contact_role_code on study_contact (role_code);

DROP INDEX IF EXISTS study_resourcing_summ_4_rept_indicator;
create index study_resourcing_summ_4_rept_indicator on study_resourcing (summ_4_rept_indicator);

DROP INDEX IF EXISTS study_otheridentifiers_root;
create index study_otheridentifiers_root on study_otheridentifiers (root);

DROP INDEX IF EXISTS organization_name;
create index organization_name on organization (name);

DROP INDEX IF EXISTS research_organization_organization_identifier;
create index research_organization_organization_identifier on research_organization (organization_identifier);

DROP INDEX IF EXISTS study_site_research_organization_identifier;
create index study_site_research_organization_identifier on study_site (research_organization_identifier);

DROP INDEX IF EXISTS study_inbox_close_date;
create index study_inbox_close_date on study_inbox (close_date);

DROP INDEX IF EXISTS study_protocol_user_last_created_id;
create index study_protocol_user_last_created_id on study_protocol (user_last_created_id);

DROP INDEX IF EXISTS study_protocol_submitting_organization_id;
create index study_protocol_submitting_organization_id on study_protocol (submitting_organization_id);

DROP INDEX IF EXISTS study_site_healthcare_facility_identifier;
create index study_site_healthcare_facility_identifier ON study_site (healthcare_facility_identifier);

DROP INDEX IF EXISTS healthcare_provider_person_identifier;
create index healthcare_provider_person_identifier ON healthcare_provider (person_identifier);

DROP INDEX IF EXISTS hcf_organization_identifier;
create index hcf_organization_identifier ON healthcare_facility (organization_identifier);
   
DROP INDEX IF EXISTS sm_rv_admin_milestone01;
            
DROP INDEX IF EXISTS sm_rv_admin_milestone02;

DROP INDEX IF EXISTS sm_rv_admin_milestone03;
     
DROP INDEX IF EXISTS sm_rv_scientific_milestone01;
            
DROP INDEX IF EXISTS sm_rv_scientific_milestone02;