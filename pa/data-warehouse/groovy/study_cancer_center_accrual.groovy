import groovy.sql.Sql

def sql = """select scca.identifier, f.po_id, sp.nci_id, scca.targeted_accrual from study_cancer_center_accrual scca, family f, study_protocol sp 
        where scca.family_id=f.identifier and scca.study_protocol_id=sp.identifier"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

def studycctargetAcc = destinationConnection.dataSet("STG_DW_STUDY_CANCER_CENTER_ACCRUAL")

sourceConnection.eachRow(sql) { row ->
    studycctargetAcc.add(
          internal_system_id: row.identifier,
          family_po_id: row.po_id,
          nci_id: row.nci_id,
          targeted_accrual: row.targeted_accrual)
}

sourceConnection.close()
destinationConnection.close()