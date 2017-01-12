import groovy.sql.Sql

def sql = """
select ss.identifier, contact.role_code, person.first_name,person.middle_name, person.last_name, organization.name, so.extension
       , organization.assigned_identifier::integer, person.assigned_identifier as per_POID
from study_site ss
join study_site_contact as contact on (contact.study_site_identifier = ss.identifier)
join clinical_research_staff crs on (contact.clinical_research_staff_identifier = crs.identifier)
join person on (person.identifier = crs.person_identifier)
join healthcare_facility hcf on (ss.healthcare_facility_identifier = hcf.identifier)
join organization on (hcf.organization_identifier = organization.identifier)
join study_otheridentifiers so on (ss.study_protocol_identifier = so.study_protocol_id)
join study_protocol sp on (so.study_protocol_id = sp.identifier)
where functional_code = 'TREATING_SITE'
  and contact.role_code IN ('PRINCIPAL_INVESTIGATOR','SUB_INVESTIGATOR')
  and so.root = '2.16.840.1.113883.3.26.4.3'
  and sp.status_code = 'ACTIVE'
"""
  
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def ps_investigators = destinationConnection.dataSet("STG_DW_STUDY_PARTICIPATING_SITE_INVESTIGATORS")

sourceConnection.eachRow(sql) { row ->
  ps_investigators.add(
    INTERNAL_SYSTEM_ID: row.identifier,
    NCI_ID: row.extension,
    INVESTIGATOR_FIRST_NAME: row.first_name,
    INVESTIGATOR_MIDDLE_NAME: row.middle_name,
    INVESTIGATOR_LAST_NAME: row.last_name,
    INVESTIGATOR_ROLE: row.role_code,
    PARTICIPATING_SITE_ORG_NAME: row.name,
    ORG_PO_ID: row.assigned_identifier,
    PERSON_PO_ID: row.per_POID
  )
}

sourceConnection.close()
destinationConnection.close()
