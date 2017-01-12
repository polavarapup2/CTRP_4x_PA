import groovy.sql.Sql

def sql = """SELECT *
             FROM patient_stage
"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def inbox = destinationConnection.dataSet("stg_dw_failed_patient_accruals");

sourceConnection.eachRow(sql) { row ->
    inbox.add(
		study_identifier: row.study_identifier,
		study_protocol_identifier: row.study_protocol_identifier,
		assigned_identifier: row.assigned_identifier,
		race_code: row.race_code,
		sex_code: row.sex_code,
		ethnic_code: row.ethnic_code,		
		country_code: row.country_code,		
		payment_method_code: row.payment_method_code,
		study_site: row.study_site,
		disease_code: row.disease_code,
		registration_date: row.registration_date,
		registration_group_id: row.registration_group_id,
		accrual_count: row.accrual_count,
		file_name: row.file_name,
		submission_status: row.submission_status,
		date_last_created: row.date_last_created,
		date_last_updated: row.date_last_updated,
		user_last_created_id: row.user_last_created_id,
		user_last_updated_id: row.user_last_updated_id,
		site_disease_code: row.site_disease_code
       
    )
}

sourceConnection.close()
destinationConnection.close()
