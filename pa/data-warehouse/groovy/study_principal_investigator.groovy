import groovy.sql.Sql

def sql = """
SELECT sc.identifier, so.extension, per.first_name, per.last_name,sc.role_code, per.assigned_identifier
   FROM study_contact sc
   JOIN study_protocol sp ON sp.identifier=sc.study_protocol_identifier
   JOIN study_otheridentifiers so ON so.study_protocol_id=sp.identifier
   JOIN clinical_research_staff crs ON crs.identifier = sc.clinical_research_staff_identifier
   JOIN person per ON crs.person_identifier = per.identifier
  WHERE so.root='2.16.840.1.113883.3.26.4.3' and sp.status_code='ACTIVE' and sc.role_code::text = 'STUDY_PRINCIPAL_INVESTIGATOR'::text 
"""
  
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def trial_pi = destinationConnection.dataSet("STG_DW_STUDY_PRINCIPAL_INVESTIGATOR")

sourceConnection.eachRow(sql) { row ->
  trial_pi.add(
    INTERNAL_SYSTEM_ID: row.identifier,
    NCI_ID: row.extension,
    PI_FIRST_NAME: row.first_name,
    PI_LAST_NAME: row.last_name,
    PI_ROLE: row.role_code,
    PERSON_PO_ID: row.assigned_identifier
  )
}

sourceConnection.close()
destinationConnection.close()
