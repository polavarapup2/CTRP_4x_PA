import groovy.sql.Sql

def sql = """select sec.name,sp.secondary_purpose_other_text,
             nci_id.extension 
             from study_protocol_sec_purpose spsp
             inner join secondary_purpose sec on sec.identifier = spsp.secondary_purpose_id
             inner join rv_trial_id_nci nci_id on nci_id.study_protocol_id = spsp.study_protocol_id
             inner join study_protocol sp on sp.identifier = spsp.study_protocol_id
             where sp.status_code = 'ACTIVE'"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'], 
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def secondarypurpose = destinationConnection.dataSet("STG_DW_STUDY_SECONDARY_PURPOSE")

sourceConnection.eachRow(sql) { row ->
    secondarypurpose.add(
        secondary_purpose_name: row.name,
        secondary_purpose_other_text:row.secondary_purpose_other_text,
        nci_id: row.extension)
}

sourceConnection.close()
destinationConnection.close()