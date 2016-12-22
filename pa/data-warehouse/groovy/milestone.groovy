import groovy.sql.Sql
def sql = """SELECT
				sm.milestone_date,
				sm.milestone_code,
				sm.user_last_created_id,
				sm.user_last_updated_id,
				sm.date_last_created, 
				sm.date_last_updated,
                sm.identifier,
                sm.comment_text,
                sp.submission_number,
                nci_id.extension
                FROM STUDY_MILESTONE sm
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sm.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                join study_protocol sp on sp.identifier = sm.study_protocol_identifier
                """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def milestones = destinationConnection.dataSet("STG_DW_STUDY_MILESTONE");

sourceConnection.eachRow(sql) { row ->
    milestones.add(
    		comments: row.comment_text,
    		name: row.milestone_code,
    		date: row.milestone_date,
    		date_created: row.date_last_created,
    		date_last_updated: row.date_last_updated,
            internal_system_id: row.identifier,
            nci_id: row.extension,
            submission_number: row.submission_number,
            user_last_created_id: row.user_last_created_id,
            user_last_updated_id: row.user_last_updated_id
            )};

destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE tbl
                                 SET user_name_created = us.name, first_name_created = us.first_name, last_name_created = us.last_name  
                                 FROM stg_dw_user us where tbl.user_last_created_id = us.csm_user_id""");

destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE tbl
                                 SET user_name_last_updated = us.name, first_name_last_updated = us.first_name, last_name_last_updated = us.last_name 
                                 FROM stg_dw_user us where tbl.user_last_updated_id = us.csm_user_id""");

destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Received Date'    
	where NAME='SUBMISSION_RECEIVED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Acceptance Date'    
	where NAME='SUBMISSION_ACCEPTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Rejection Date'    
	where NAME='SUBMISSION_REJECTED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Terminated Date'
	where NAME='SUBMISSION_TERMINATED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submission Reactivated Date'
	where NAME='SUBMISSION_REACTIVATED'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative Processing Start Date'    
	where NAME='ADMINISTRATIVE_PROCESSING_START_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative Processing Completed Date'    
	where NAME='ADMINISTRATIVE_PROCESSING_COMPLETED_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Ready for Administrative QC Date'    
	where NAME='ADMINISTRATIVE_READY_FOR_QC'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative QC Start Date'    
	where NAME='ADMINISTRATIVE_QC_START'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Administrative QC Completed Date'    
	where NAME='ADMINISTRATIVE_QC_COMPLETE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific Processing Start Date'    
	where NAME='SCIENTIFIC_PROCESSING_START_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific Processing Completed Date'    
	where NAME='SCIENTIFIC_PROCESSING_COMPLETED_DATE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Ready for Scientific QC Date'    
	where NAME='SCIENTIFIC_READY_FOR_QC'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific QC Start Date'    
	where NAME='SCIENTIFIC_QC_START'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Scientific QC Completed Date'    
	where NAME='SCIENTIFIC_QC_COMPLETE'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Ready for Trial Summary Report Date'    
	where NAME='READY_FOR_TSR'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Trial Summary Report Date'    
	where NAME='TRIAL_SUMMARY_REPORT'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Submitter Trial Summary Report Feedback Date'    
	where NAME='TRIAL_SUMMARY_FEEDBACK'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Initial Abstraction Verified Date'    
	where NAME='INITIAL_ABSTRACTION_VERIFY'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='On-going Abstraction Verified Date'    
	where NAME='ONGOING_ABSTRACTION_VERIFICATION'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_MILESTONE SET NAME='Late Rejection Date'    
	where NAME='LATE_REJECTION_DATE'""")

sourceConnection.close()
destinationConnection.close()