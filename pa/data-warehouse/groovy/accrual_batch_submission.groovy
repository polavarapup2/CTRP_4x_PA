import groovy.sql.Sql

def sql = """select batch_file_identifier, change_code, nci_number, date_last_created, date_last_updated, passed_validation,
                    results, user_last_created_id, user_last_updated_id, total_imports
             from accrual_collections
          """

def sourceConnectionPa = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def accBat = destinationConnection.dataSet("STG_DW_ACCRUAL_BATCH_SUBMISSION")

sourceConnectionPa.eachRow(sql) { row ->
    def cleaned_nci_id = row.nci_number
    if ((cleaned_nci_id != null) && !cleaned_nci_id.contains('NCI-')) {
        cleaned_nci_id = null;
    }
    accBat.add(
        batch_file_identifier: row.batch_file_identifier,
        change_code: row.change_code,
        corresponding_nci_id: cleaned_nci_id,
        date_last_created: row.date_last_created,
        date_last_updated: row.date_last_updated,
        passed_validation: row.passed_validation,
		successful_imports: row.total_imports,
        result_comments: row.results,
        study_id_submitted: row.nci_number,
        user_last_created_id: row.user_last_created_id,
        user_last_updated_id: row.user_last_updated_id
      
    )
}

destinationConnection.execute("""UPDATE stg_dw_accrual_batch_submission absu
                                 SET user_name_last_created = us.name 
                                 FROM stg_dw_user us where absu.user_last_created_id = us.csm_user_id""");

destinationConnection.execute("""UPDATE stg_dw_accrual_batch_submission absu
                                 SET user_name_last_updated = us.name 
                                 FROM stg_dw_user us where absu.user_last_updated_id = us.csm_user_id""");
							 
sourceConnectionPa.close()
destinationConnection.close()

