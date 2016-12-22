import groovy.sql.Sql
def sql = """
            select indide.date_last_created, indide.date_last_updated,
            CASE WHEN indide.expanded_access_indicator THEN 'YES'
                 ELSE 'NO'
            END as access_indicator,
            indide.expanded_access_status_code, indide.grantor_code, indide.holder_type_code, indide.indlde_number,
            indide.indlde_type_code, indide.identifier, indide.nci_div_prog_holder_code, nci_id.extension,
            indide.nih_inst_holder_code,
            indide.exempt_indicator,
            CASE WHEN NULLIF(ru_creator.first_name, '') is not null THEN ru_creator.first_name || ' ' || ru_creator.last_name
                 WHEN NULLIF(split_part(creator.login_name, 'CN=', 2), '') is null THEN creator.login_name
                 ELSE split_part(creator.login_name, 'CN=', 2)
            END as creator,
            CASE WHEN NULLIF(ru_updater.first_name, '') is not null THEN ru_updater.first_name || ' ' || ru_updater.last_name
                 WHEN NULLIF(split_part(updater.login_name, 'CN=', 2), '') is null THEN updater.login_name
                 ELSE split_part(updater.login_name, 'CN=', 2)
            END as updater
            from STUDY_INDLDE indide
            inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = indide.study_protocol_identifier
            and nci_id.root = '2.16.840.1.113883.3.26.4.3'
            left outer join csm_user as creator on indide.user_last_created_id = creator.user_id
            left outer join registry_user as ru_creator on ru_creator.csm_user_id = creator.user_id
            left outer join csm_user as updater on indide.user_last_created_id = updater.user_id
            left outer join registry_user as ru_updater on ru_updater.csm_user_id = updater.user_id;"""
def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def indides = destinationConnection.dataSet("STG_DW_STUDY_IND_IDE")
sourceConnection.eachRow(sql) { row ->
    indides.add(date_last_created: row.date_last_created, date_last_updated: row.date_last_updated, expanded_access_indicator: row.access_indicator,
            exempt_indicator: row.exempt_indicator,
            expanded_access_status_code: row.expanded_access_status_code, grantor_code: row.grantor_code, holder_type_code: row.holder_type_code,
            ind_ide_number: row.indlde_number, ind_ide_type_code: row.indlde_type_code, internal_system_id: row.identifier,
            nci_div_prog_holder_code: row.nci_div_prog_holder_code, nci_id: row.extension, nih_instholder_code: row.nih_inst_holder_code,
            user_last_created: row.creator, user_last_updated: row.updater)
}

sourceConnection.close()
destinationConnection.close()