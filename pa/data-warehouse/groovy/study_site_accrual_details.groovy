import groovy.sql.Sql
def sql = """
      SELECT
       soi.extension AS nci_id,
       co.name AS country,
       pat.birth_date AS birth_date,
       ssub.date_last_created AS date_last_created,
       ssub.date_last_updated AS date_last_updated,
       delete_reason AS deletion_reason,
       pat.ethnic_code AS ethnicity,
       pat.sex_code AS gender,
       ssub.payment_method_code AS payment_method,
       pat.race_code AS race,
       pact.registration_date AS registration_date,
       registration_group_id AS registration_group,
       ssub.study_site_identifier AS site_id,
       ssub.status_code AS status,
       ssub.assigned_identifier AS study_subject_id,
       ssub.user_last_created_id,
       ssub.user_last_updated_id,
       dis.disease_code,
       sp.accrual_disease_code_system as code_system,
       dis.preferred_name,
       ssub.disease_identifier,
       ssub.site_disease_identifier,
       ssub.submission_type,
       pat.zip,
       ssub.identifier as subject_identifier
    FROM study_subject ssub
    JOIN Study_site ss ON (ssub.study_site_identifier = ss.identifier)
    JOIN study_protocol sp ON (ss.study_protocol_identifier = sp.identifier)
    JOIN study_otheridentifiers soi ON (sp.identifier = soi.study_protocol_id)
    JOIN performed_activity pact ON (ssub.identifier = pact.study_subject_identifier)
    JOIN patient pat ON (ssub.patient_identifier = pat.identifier)
    JOIN country co ON (pat.country_identifier = co.identifier)
    LEFT OUTER JOIN accrual_disease dis ON (ssub.disease_identifier = dis.identifier)
    WHERE soi.root = '2.16.840.1.113883.3.26.4.3'
      AND sp.status_code = 'ACTIVE' and ssub.status_code !='NULLIFIED'
"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def ssad = destinationConnection.dataSet("stg_dw_study_site_accrual_details");

sourceConnection.eachRow(sql) { row ->
    ssad.add(
        nci_id: row.nci_id,
        country : row.country,        
        date_last_created : row.date_last_created,
        date_last_updated : row.date_last_updated,
        deletion_reason : row.deletion_reason,
        ethnicity : row.ethnicity,
        gender : row.gender,
        payment_method : row.payment_method,
        race : row.race,
        registration_date : row.registration_date,
        registration_group : row.registration_group,
        site_id : row.site_id,
        status : row.status,
        study_subject_id : row.study_subject_id,
        user_last_created_id : row.user_last_created_id,
        user_last_updated_id : row.user_last_updated_id,
        disease_code : row.disease_code,
        code_system : row.code_system,
        preferred_name : row.preferred_name,
        disease_identifier : row.disease_identifier,
        site_disease_identifier : row.site_disease_identifier,
        submission_type : row.submission_type,        
        subject_identifier : row.subject_identifier
    )};
    
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET user_name_last_created = us.name,  user_email_last_created = us.email 
                                 FROM stg_dw_user us where ssad.user_last_created_id = us.csm_user_id""");
   
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET user_name_last_updated = us.name, user_email_last_updated = us.email 
                                 FROM stg_dw_user us where ssad.user_last_updated_id = us.csm_user_id""");
   
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET org_name = ps.org_name, 
                                     org_org_family = ps.org_org_family,
                                     site_org_id = ps.org_po_id
                                 FROM stg_dw_study_participating_site ps where ssad.site_id = ps.internal_system_id""");

destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET icd9_disease_code = disease_code,
                                     icd9_disease_term = preferred_name 
                                 WHERE code_system = 'ICD9'""");
                             
destinationConnection.execute("""UPDATE stg_dw_study_site_accrual_details ssad
                                 SET sdc_disease_code = disease_code,
                                     sdc_disease_term = preferred_name 
                                 WHERE code_system = 'SDC'""");
							 
							 sourceConnection.close()
							 destinationConnection.close()
                             