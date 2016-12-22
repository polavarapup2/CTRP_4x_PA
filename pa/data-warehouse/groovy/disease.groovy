import groovy.sql.Sql


def sql = """SELECT
                CASE WHEN sd.ctgovxml_indicator THEN 'YES'
                     ELSE 'NO'
                END as ct_indicator,
                'TRIAL' as inclusion_indicator,
                sd.date_last_created, sd.date_last_updated, disease.disease_code, disease.preferred_name, disease.menu_display_name,
                sd.identifier,
                disease.identifier AS internal_system_id2,
                NULL as lead_indicator,
                nci_id.extension, disease.nt_term_identifier,
                CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                    WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                    ELSE split_part(creator.login_name, 'CN=', 2)
                END as creator,
                CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                    WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                    ELSE split_part(updater.login_name, 'CN=', 2)
                END as updater
                FROM STUDY_DISEASE sd
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = sd.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'
                left outer join pdq_disease as disease on disease.identifier = sd.disease_identifier
                left outer join study_protocol as sp on sp.identifier = sd.study_protocol_identifier
                left outer join csm_user as creator on sd.user_last_created_id = creator.user_id
                left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
                left outer join csm_user as updater on sd.user_last_created_id = updater.user_id
                left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id
                where sp.status_code = 'ACTIVE'"""

def sql_parents = """SELECT dp.disease_identifier, dp.parent_disease_identifier, d.disease_code, d.nt_term_identifier, d.preferred_name, d.menu_display_name 
                       FROM pdq_disease_parent dp
                       JOIN pdq_disease d ON (dp.parent_disease_identifier = d.identifier)"""


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def parents = destinationConnection.dataSet("STG_DW_DISEASE_PARENTS"); 
def diseases = destinationConnection.dataSet("STG_DW_STUDY_DISEASE");
sourceConnection.eachRow(sql_parents) { row ->
    parents.add(disease_identifier: row.disease_identifier, parent_disease_identifier: row.parent_disease_identifier,
        disease_code: row.disease_code, nt_term_identifier: row.nt_term_identifier, preferred_name: row.preferred_name, menu_display_name: row.menu_display_name)
}


System.out.println("Creating disease parents table...");
def rowsAffected = -1;
while (rowsAffected != 0) {
    def inserted = destinationConnection.executeInsert("""INSERT INTO stg_dw_disease_parents (
                                   SELECT DISTINCT dp1.disease_identifier, dp2.parent_disease_identifier, dp2.disease_code, dp2.nt_term_identifier, dp2.preferred_name, dp2.menu_display_name
                                   FROM stg_dw_disease_parents dp1
                                   JOIN stg_dw_disease_parents dp2 ON (dp1.parent_disease_identifier = dp2.disease_identifier)
                                   WHERE 
                                   	(SELECT count(dp3.disease_identifier) FROM stg_dw_disease_parents dp3 where dp1.disease_identifier=dp3.disease_identifier AND dp2.parent_disease_identifier=dp3.parent_disease_identifier)=0 
 
                              )""");
    rowsAffected = inserted.size;
}


System.out.println("Populating data warehouse report table with study diseases..."); 

sourceConnection.close();
sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
	properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])

sourceConnection.eachRow(sql) { row ->
    diseases.add(ct_gov_xml_indicator: row.ct_indicator, date_last_created: row.date_last_created, date_last_updated: row.date_last_updated,
            disease_code: row.disease_code, disease_preferred_name: row.preferred_name, disease_menu_display_name: row.menu_display_name,
            internal_system_id: row.identifier, lead_disease_indicator: row.lead_indicator, nci_id: row.extension, 
            nci_thesaurus_concept_id: row.nt_term_identifier, user_last_created: row.creator, user_last_updated: row.updater,
            internal_system_id2: row.internal_system_id2, inclusion_indicator: row.inclusion_indicator)
}

sourceConnection.close()
destinationConnection.close()
