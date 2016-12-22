import groovy.sql.Sql

import org.apache.commons.lang.StringUtils

def sql = """SELECT sp.identifier spid, soi.extension nci_id, sp.study_protocol_type, sp.study_subtype_code,
                    sr.type_code study_source,
                    (select count(*) from study_anatomic_site where sp.identifier = study_protocol_identifier) asite_count,
                    anat.code AS asite, pi.last_name, pi.first_name, pi.middle_name,
                    sp.start_date, sos_close.status_date close_date,
                    sp.phase_code, sp.primary_purpose_code, sp.official_title, sp.min_target_accrual_num,
                    sp.identifier sp_id, lead_org.assigned_identifier::integer lead_org_po_id, sp.proprietary_trial_indicator,
                    sp.consortia_trial_category
             FROM study_protocol sp
             JOIN document_workflow_status dwf_curr ON (sp.identifier = dwf_curr.study_protocol_identifier
                         AND dwf_curr.identifier IN (SELECT max(dwf2.identifier)
                         FROM document_workflow_status dwf2 WHERE dwf_curr.study_protocol_identifier = dwf2.study_protocol_identifier))
             JOIN study_otheridentifiers soi ON (sp.identifier = soi.study_protocol_id AND soi.root = '2.16.840.1.113883.3.26.4.3')
             LEFT JOIN study_resourcing sr ON (sp.identifier = sr.study_protocol_identifier AND sr.summ_4_rept_indicator = TRUE)
             LEFT JOIN organization org ON (org.identifier = sr.organization_identifier::integer)
             LEFT JOIN study_anatomic_site sas1 ON (sp.identifier = sas1.study_protocol_identifier
                         AND sas1.anatomic_sites_identifier IN (SELECT MIN(anatomic_sites_identifier)
                         FROM study_anatomic_site sas2 WHERE sas2.study_protocol_identifier = sp.identifier))
             LEFT JOIN anatomic_sites anat ON (sas1.anatomic_sites_identifier = anat.identifier)
             LEFT JOIN study_contact AS sc ON (sc.study_protocol_identifier = sp.identifier AND sc.role_code = 'STUDY_PRINCIPAL_INVESTIGATOR')
             LEFT JOIN clinical_research_staff AS crs ON (crs.identifier = sc.clinical_research_staff_identifier)
             LEFT JOIN person AS pi ON (pi.identifier = crs.person_identifier)
             LEFT JOIN study_overall_status sos_active ON (sp.identifier = sos_active.study_protocol_identifier
                         AND sos_active.identifier IN (SELECT max(sos2.identifier)
                         FROM study_overall_status sos2 WHERE sos_active.study_protocol_identifier = sos2.study_protocol_identifier
                         AND sos2.status_code = 'ACTIVE'))
             LEFT JOIN study_overall_status sos_close ON (sp.identifier = sos_close.study_protocol_identifier
                         AND sos_close.identifier IN (SELECT max(sos3.identifier)
                         FROM study_overall_status sos3 WHERE sos_close.study_protocol_identifier = sos3.study_protocol_identifier
                         AND sos3.status_code IN ('CLOSED_TO_ACCRUAL','CLOSED_TO_ACCRUAL_AND_INTERVENTION')
                         AND (sos3.identifier > sos_active.identifier OR sos_active.identifier IS NULL)))
             LEFT JOIN study_site lo on lo.study_protocol_identifier = sp.identifier and lo.functional_code = 'LEAD_ORGANIZATION'
             LEFT JOIN research_organization ro_lead_org on ro_lead_org.identifier = lo.research_organization_identifier
             LEFT JOIN organization lead_org on lead_org.identifier = ro_lead_org.organization_identifier
             WHERE dwf_curr.status_code != 'REJECTED'
               AND sp.identifier IN ( SELECT MAX(identifier)
                                      FROM study_protocol sp
                                      JOIN study_otheridentifiers soi ON (sp.identifier = soi.study_protocol_id)
                                      WHERE soi.root = '2.16.840.1.113883.3.26.4.3'
                                        AND sp.status_code = 'ACTIVE'
                                      GROUP BY soi.extension )
               AND sr.identifier IN ( SELECT MIN(identifier)
                                      FROM study_resourcing
                                      WHERE summ_4_rept_indicator = TRUE
                                      GROUP BY study_protocol_identifier)
         """

def sqlFunding = """SELECT DISTINCT nci_id, sponsor
                    FROM stg_dw_summary_4_funding
                    WHERE nci_id IN (SELECT nci_id FROM stg_dw_data_table_4)
                 """

def sqlOtherIds = """SELECT DISTINCT nci_id, value
                  FROM stg_dw_study_other_identifier
                  WHERE nci_id IN (SELECT nci_id FROM stg_dw_data_table_4)
               """


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def data_4 = destinationConnection.dataSet("stg_dw_data_table_4");

sourceConnection.eachRow(sql) { row ->
    data_4.add(
                nci_id: row.nci_id,
                clinical_research_cat: (row.study_protocol_type == "InterventionalStudyProtocol" ? "INT"
                                         : (row.study_subtype_code == "ANCILLARY_CORRELATIVE" ? "ANC/COR" : "OBS")),
                study_source: row.study_source,
                is_multiinstitutional : "N",
                site: row.asite_count > 1 ? "multiple" : row.asite,
                pi_last_name: row.last_name,
                pi_first_name: row.first_name,
                pi_middle_initial: StringUtils.left(row.middle_name, 1),
                open_date: row.start_date,
                close_date: row.close_date,
                phase: row.phase_code,
                primary_purpose: row.primary_purpose_code,
                official_title: StringUtils.substring(row.official_title, 0, 600),
                entire_study: row.min_target_accrual_num,
                sp_id: row.sp_id,
                lead_org_po_id: row.lead_org_po_id,
                is_industrial: row.proprietary_trial_indicator,
                summary_level_accrual: row.proprietary_trial_indicator,
                consortia_trial_category: row.consortia_trial_category
            )};

destinationConnection.eachRow(sqlFunding) { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_data_table_4
                                     SET specific_funding_source = specific_funding_source || ', ' || ?
                                     WHERE nci_id = ?
                                       AND specific_funding_source IS NOT NULL
                                  """, [row.sponsor, row.nci_id]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_data_table_4
                                     SET specific_funding_source = ?
                                     WHERE nci_id = ?
                                       AND specific_funding_source IS NULL
                                  """, [row.sponsor, row.nci_id]);
}

destinationConnection.execute("""UPDATE stg_dw_data_table_4 dt4
                                 SET nct_id = st.nct_id, ctep_dcp_id = COALESCE(st.ctep_id, st.dcp_id), lead_org_id = st.lead_org_id
                                 FROM stg_dw_study st
                                   WHERE dt4.nci_id = st.nci_id""");

destinationConnection.eachRow(sqlOtherIds) { row ->
    destinationConnection.executeUpdate("""UPDATE stg_dw_data_table_4
                                     SET other_ids = other_ids || chr(13) || ?
                                     WHERE nci_id = ?
                                       AND other_ids IS NOT NULL
                                       AND ? NOT IN (nct_id, ctep_dcp_id, lead_org_id)
                                  """, [row.value, row.nci_id, row.value]);
    destinationConnection.executeUpdate("""UPDATE stg_dw_data_table_4
                                     SET other_ids = ?
                                     WHERE nci_id = ?
                                       AND other_ids IS NULL
                                       AND ? NOT IN (nct_id, ctep_dcp_id, lead_org_id)
                                  """, [row.value, row.nci_id, row.value]);
}


destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'N' WHERE study_source = 'NATIONAL'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'E' WHERE study_source = 'EXTERNALLY_PEER_REVIEWED'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'I' WHERE study_source = 'INSTITUTIONAL'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'D' WHERE study_source = 'INDUSTRIAL' AND consortia_trial_category IS NULL""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'N' WHERE study_source = 'INDUSTRIAL' AND consortia_trial_category = 'NATIONAL'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'E' WHERE study_source = 'INDUSTRIAL' AND consortia_trial_category = 'EXTERNALLY_PEER_REVIEWED'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET study_source = 'I' WHERE study_source = 'INDUSTRIAL' AND consortia_trial_category IS NOT NULL""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET is_multiinstitutional = 'Y' WHERE nci_id IN
                                  ( SELECT nci_id FROM
                                    ( SELECT DISTINCT nci_id, family_name
                                      FROM stg_dw_study_participating_site sps
                                      JOIN stg_dw_family_organization famorg ON (famorg.organization_id = sps.org_po_id AND famorg.functionaltype = 'ORGANIZATIONAL')
                                    ) AS fams
                                    GROUP BY nci_id
                                    HAVING count(*) > 1
                                  )""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Tre' WHERE primary_purpose = 'TREATMENT'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Pre' WHERE primary_purpose = 'PREVENTION'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Sup' WHERE primary_purpose = 'SUPPORTIVE_CARE'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Scr' WHERE primary_purpose = 'SCREENING'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Dia' WHERE primary_purpose = 'DIAGNOSTIC'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Hsr' WHERE primary_purpose = 'HEALTH_SERVICES_RESEARCH'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Bas' WHERE primary_purpose = 'BASIC_SCIENCE'""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET primary_purpose = 'Oth' WHERE primary_purpose = 'OTHER'""");

destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET summary_level_accrual = FALSE WHERE nci_id IN (
                                   SELECT nci_id FROM stg_dw_study_site_accrual_details WHERE status = 'ACTIVE')""");
destinationConnection.execute("""UPDATE stg_dw_data_table_4 SET summary_level_accrual = TRUE WHERE nci_id IN (
                                   SELECT nci_id FROM stg_dw_study_accrual_count WHERE count_type = 'SITE_TOTAL' AND accrual_count > 0)""");
							   
							   sourceConnection.close()
							   destinationConnection.close()
