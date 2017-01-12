import groovy.sql.Sql

def sql = """SELECT sr.identifier, soi.extension, sr.type_code, org.name, CAST(org.assigned_identifier AS bigint)
                    , sp.identifier sp_identifier
             FROM study_resourcing sr
             JOIN study_protocol sp ON (sp.identifier = sr.study_protocol_identifier)
             JOIN study_otheridentifiers soi ON (soi.study_protocol_id = sp.identifier)
             LEFT JOIN organization org ON (CAST(sr.organization_identifier AS bigint) = org.identifier)
             WHERE sr.summ_4_rept_indicator = TRUE
               AND sp.status_code = 'ACTIVE'
               AND soi.root = '2.16.840.1.113883.3.26.4.3'
          """

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def sponsors = destinationConnection.dataSet("stg_dw_summary_4_funding")

sourceConnection.eachRow(sql) { row ->
    if (row.name != null) {
      sponsors.add(
        internal_system_id: row.identifier,
        nci_id: row.extension,
        sponsor_id: row.assigned_identifier,
        sponsor: row.name
      );
	}
	destinationConnection.executeUpdate("""UPDATE stg_dw_study study
                                           SET summary_4_funding_category = ?
                                           WHERE internal_system_id = ?
	                                    """, [row.type_code, row.sp_identifier]);
}

destinationConnection.execute("""UPDATE stg_dw_summary_4_funding s4
                                 SET sponsor = fo.organization_name,
                                     family_id = fo.family_id,
                                     family = fo.family_name
                                 FROM stg_dw_family_organization fo
                                 WHERE fo.organization_id = s4.sponsor_id""");
							 
							 
							 sourceConnection.close()
							 destinationConnection.close()
