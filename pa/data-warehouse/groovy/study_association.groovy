import groovy.sql.Sql

def sql =
    """SELECT
  soia.extension AS study_a,
  soib.extension AS study_b,
  assoc.study_identifier,
  assoc.identifier_type,
  assoc.study_protocol_type,
  assoc.study_subtype_code,
  assoc.official_title, 
  spb.study_protocol_type AS study_protocol_type_b,
  spb.study_subtype_code AS study_subtype_code_b,
  spb.official_title AS official_title_b
FROM study_protocol_association assoc
JOIN study_otheridentifiers soia ON (assoc.study_a_id = soia.study_protocol_id)
JOIN study_protocol spa ON (assoc.study_a_id = spa.identifier)
LEFT JOIN study_otheridentifiers soib ON (assoc.study_b_id = soib.study_protocol_id)
LEFT JOIN study_protocol spb ON (assoc.study_b_id = spb.identifier)
WHERE soia.root = '2.16.840.1.113883.3.26.4.3'
  AND spa.status_code = 'ACTIVE'
  AND (soib.root IS NULL OR soib.root = '2.16.840.1.113883.3.26.4.3')
"""


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
        properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
        properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def associations = destinationConnection.dataSet("STG_DW_STUDY_ASSOCIATION")

sourceConnection.eachRow(sql) { row ->
    destinationConnection.withTransaction {
        try {
            associations.add(
                study_a : row.study_a,
                study_b : row.study_b == null ? row.study_identifier : row.study_b,
                identifier_type : row.study_b == null ? row.identifier_type : 'NCI',
                study_protocol_type : row.study_b == null ? row.study_protocol_type : row.study_protocol_type_b,
                study_subtype_code : row.study_b == null ? row.study_subtype_code : row.study_subtype_code_b,
                official_title : row.study_b == null ? row.official_title : row.official_title_b
                    )
        } catch (Exception e) {
            println "Error adding row : " + row
        }
    }
}


destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET identifier_type='Other Identifier'
                                 WHERE identifier_type='OTHER_IDENTIFIER'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET identifier_type='Lead Organization'
                                 WHERE identifier_type='LEAD_ORG'""")

destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_protocol_type='Interventional'
                                 WHERE study_protocol_type='INTERVENTIONAL'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_protocol_type='NonInterventional'
                                 WHERE study_protocol_type='NON_INTERVENTIONAL'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_protocol_type='Interventional'
                                 WHERE study_protocol_type='InterventionalStudyProtocol'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_protocol_type='NonInterventional'
                                 WHERE study_protocol_type='NonInterventionalStudyProtocol'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_protocol_type='Interventional'
                                 WHERE study_protocol_type IS NULL""")

destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_subtype_code='Observational'
                                 WHERE study_subtype_code='OBSERVATIONAL'""")
destinationConnection.execute("""UPDATE STG_DW_STUDY_ASSOCIATION SET study_subtype_code='Ancillary-Correlative'
                                 WHERE study_subtype_code='ANCILLARY_CORRELATIVE'""")

sourceConnection.close()
destinationConnection.close()