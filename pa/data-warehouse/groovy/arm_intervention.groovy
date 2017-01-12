import groovy.sql.Sql
def sql = """SELECT
				arm.description_text as arm_desc,
				arm.name as arm_name,
				arm.type_code as arm_code,
				arm.date_last_Created as arm_created_date,
				arm.date_last_updated as arm_updated_date,
				int.description_text as int_desc,
				int.name as int_name,
				altname,
				int.identifier as int_id,
                pa.subcategory_code as int_code,
                int.nt_term_identifier as intervention_evs_c_code,
				pa.date_last_Created as int_created_date,
				pa.date_last_updated as int_updated_date,
                nci_id.extension,
                CASE WHEN NULLIF(arm_ru_creator.first_name, '') is not null THEN arm_ru_creator.first_name || ' ' || arm_ru_creator.last_name
                     WHEN NULLIF(split_part(arm_csm_creator.login_name, 'CN=', 2), '') is null THEN arm_csm_creator.login_name
                     ELSE split_part(arm_csm_creator.login_name, 'CN=', 2)
                END as arm_creator,
                CASE WHEN NULLIF(arm_ru_updater.first_name, '') is not null THEN arm_ru_updater.first_name || ' ' || arm_ru_updater.last_name
                     WHEN NULLIF(split_part(arm_csm_updater.login_name, 'CN=', 2), '') is null THEN arm_csm_updater.login_name
                     ELSE split_part(arm_csm_updater.login_name, 'CN=', 2)
                END as arm_updater,
                CASE WHEN NULLIF(int_ru_creator.first_name, '') is not null THEN int_ru_creator.first_name || ' ' || int_ru_creator.last_name
                     WHEN NULLIF(split_part(int_csm_creator.login_name, 'CN=', 2), '') is null THEN int_csm_creator.login_name
                     ELSE split_part(int_csm_creator.login_name, 'CN=', 2)
                END as int_creator,
                CASE WHEN NULLIF(int_ru_updater.first_name, '') is not null THEN int_ru_updater.first_name || ' ' || int_ru_updater.last_name
                     WHEN NULLIF(split_part(int_csm_updater.login_name, 'CN=', 2), '') is null THEN int_csm_updater.login_name
                     ELSE split_part(int_csm_updater.login_name, 'CN=', 2)
                END as int_updater,
                arm_ru_creator.first_name as arm_creator_first,
                arm_ru_creator.last_name as arm_creator_last,
                arm_ru_updater.first_name as arm_updater_first,
                arm_ru_updater.last_name as arm_updater_last,
                int_ru_creator.first_name as int_creator_first,
                int_ru_creator.last_name as int_creator_last,
                int_ru_updater.first_name as int_updater_first,
                int_ru_updater.last_name as int_updater_last
                FROM ARM
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = arm.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3'   
				inner join study_protocol sp on nci_id.study_protocol_id=sp.identifier and sp.status_code = 'ACTIVE'
                join arm_intervention a_i on a_i.arm_identifier = arm.identifier   
                join planned_activity pa on pa.identifier = a_i.planned_activity_identifier   
                join intervention int on int.identifier = pa.intervention_identifier   
                left outer join (select intervention_identifier,array_to_string(array_agg(name),' , ') as altname from intervention_alternate_name group by intervention_identifier) AS INTERVENTION
                     on INTERVENTION.intervention_identifier = pa.intervention_identifier
                left outer join csm_user as arm_csm_creator on arm_csm_creator.user_id = arm.user_last_Created_id
                left outer join registry_user as arm_ru_creator on arm_ru_creator.csm_user_id = arm_csm_creator.user_id
                left outer join csm_user as arm_csm_updater on arm_csm_updater.user_id = arm.user_last_updated_id
                left outer join registry_user as arm_ru_updater on arm_ru_updater.csm_user_id = arm_csm_updater.user_id
                left outer join csm_user as int_csm_creator on int_csm_creator.user_id = pa.user_last_Created_id
                left outer join registry_user as int_ru_creator on int_ru_creator.csm_user_id = int_csm_creator.user_id
                left outer join csm_user as int_csm_updater on int_csm_updater.user_id = pa.user_last_updated_id
                left outer join registry_user as int_ru_updater on int_ru_updater.csm_user_id = int_csm_updater.user_id
                UNION
                SELECT arm.description_text as arm_desc, 
                       arm.name as arm_name, 
                       arm.type_code as arm_code, 
                       arm.date_last_Created as arm_created_date,
                       arm.date_last_updated as arm_updated_date, 
                       null as int_desc, 
                       null as int_name, 
                       null as altname, 
                       null as int_id, 
                       null as int_code,
                       null as intervention_evs_c_code,
                       null as int_created_date, 
                       null as int_updated_date, 
                       so.extension,
                       CASE WHEN NULLIF(arm_ru_creator.first_name, '') is not null THEN arm_ru_creator.first_name || ' ' || arm_ru_creator.last_name
                         WHEN NULLIF(split_part(arm_csm_creator.login_name, 'CN=', 2), '') is null THEN arm_csm_creator.login_name
                         ELSE split_part(arm_csm_creator.login_name, 'CN=', 2)
                         END as arm_creator, 
                       CASE WHEN NULLIF(arm_ru_updater.first_name, '') is not null THEN arm_ru_updater.first_name || ' ' || arm_ru_updater.last_name
                         WHEN NULLIF(split_part(arm_csm_updater.login_name, 'CN=', 2), '') is null THEN arm_csm_updater.login_name
                         ELSE split_part(arm_csm_updater.login_name, 'CN=', 2)
                         END as arm_updater,null as int_creator, null as int_updater,
                       arm_ru_creator.first_name as arm_creator_first,
                       arm_ru_creator.last_name as arm_creator_last,
                       arm_ru_updater.first_name as arm_updater_first,
                       arm_ru_updater.last_name as arm_updater_last, 
                       null as int_creator_first, 
                       null as int_creator_last, 
                       null as int_updater_first, 
                       null as int_updater_last
                from study_protocol  sp join study_otheridentifiers so on sp.identifier=so.study_protocol_id 
                join arm arm on arm.study_protocol_identifier=sp.identifier
                left join   arm_intervention ai on arm.identifier=ai.arm_identifier
                left outer join csm_user as arm_csm_creator on arm_csm_creator.user_id = arm.user_last_Created_id
                left outer join registry_user as arm_ru_creator on arm_ru_creator.csm_user_id = arm_csm_creator.user_id
                left outer join csm_user as arm_csm_updater on arm_csm_updater.user_id = arm.user_last_updated_id
                left outer join registry_user as arm_ru_updater on arm_ru_updater.csm_user_id = arm_csm_updater.user_id
                WHERE sp.status_code = 'ACTIVE' AND sp.proprietary_trial_indicator=true and so.root='2.16.840.1.113883.3.26.4.3' and ai.arm_identifier is null"""


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def arms = destinationConnection.dataSet("STG_DW_STUDY_ARM_AND_INTERVENTION");


sourceConnection.eachRow(sql) { row ->
    arms.add(
    		   ARM_DESCRIPTION: row.arm_desc,
 			   ARM_NAME: row.arm_name,
 			   ARM_TYPE: row.arm_code,
    		   DATE_CREATED_ARM: row.arm_created_date,
		       DATE_UPDATED_ARM: row.arm_updated_date,
		       DATE_CREATED_INTERVENTION: row.int_created_date,
		       DATE_UPDATED_INTERVENTION: row.int_updated_date,
		       INTERVENTION_DESCRIPTION:  row.int_desc,
		       INTERVENTION_NAME: row.int_name,
		       INTERVENTION_TYPE: row.int_code,
		       INTERVENTION_EVS_C_CODE: row.intervention_evs_c_code,
		       INTERVENTION_ID: row.int_id,
			   NCI_ID: row.extension,
		   	   USER_NAME_CREATED_ARM: row.arm_creator,
	 	   	   USER_NAME_UPDATED_ARM: row.arm_updater,
		       USER_NAME_CREATED_INTERVENTION: row.int_creator,
			   USER_NAME_UPDATED_INTERVENTION: row.int_updater,
            FIRST_NAME_CREATED_ARM: row.arm_creator_first,
            LAST_NAME_CREATED_ARM: row.arm_creator_last,
            FIRST_NAME_UPDATED_ARM: row.arm_updater_first,
            LAST_NAME_UPDATED_ARM: row.arm_updater_last,
            FIRST_NAME_CREATED_INTERVENTION: row.int_creator_first,
            LAST_NAME_CREATED_INTERVENTION: row.int_creator_last,
            FIRST_NAME_UPDATED_INTERVENTION: row.int_updater_first,
            LAST_NAME_UPDATED_INTERVENTION: row.int_updater_last,
			   INTERVENTION_OTHER_NAME: row.altname
    		)
            }
     
            
sourceConnection.close()
destinationConnection.close()