import groovy.sql.Sql
def sql = """select
            CASE WHEN g.active_indicator THEN 'YES'
                 ELSE 'NO'
            END as active,
            g.funding_mechanism_code, g.identifier, g.nci_division_program_code,
            nci_id.extension, g.nih_institute_code, g.serial_number,
            g.inactive_comment_text, g.date_last_updated, g.user_last_updated_id
            from STUDY_RESOURCING g
               inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = g.study_protocol_identifier
                   and nci_id.root = '2.16.840.1.113883.3.26.4.3'
            JOIN study_protocol sp ON (g.study_protocol_identifier = sp.identifier AND sp.status_code = 'ACTIVE')
            where g.summ_4_rept_indicator is false"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def grants = destinationConnection.dataSet("STG_DW_STUDY_GRANT")

sourceConnection.eachRow(sql) { row ->
    grants.add(active_indicator: row.active, funding_mechanism_code: row.funding_mechanism_code, internal_system_id: row.identifier,
            nci_division_or_program: row.nci_division_program_code, nci_id: row.extension, nih_institution_code: row.nih_institute_code,
            serial_number: row.serial_number, 
            deletion_date: (row.active == 'NO' ? row.date_last_updated : null),
            reason_for_delete: row.inactive_comment_text, user_last_updated_id: row.user_last_updated_id)
};

destinationConnection.execute("""UPDATE stg_dw_study_grant g
                                 SET deleted_by = us.name 
                                 FROM stg_dw_user us 
                                 WHERE g.user_last_updated_id = us.csm_user_id
                                   AND g.active_indicator = 'NO'
                              """);
						  
						  sourceConnection.close()
						  destinationConnection.close()
