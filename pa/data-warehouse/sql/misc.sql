--Put here instead of user.groovy because organization table not yet populated when user.groovy run
UPDATE STG_DW_USER USR 
  SET AFFILIATED_ORGANIZATION = (SELECT DISTINCT NAME 
                                 FROM STG_DW_ORGANIZATION ORG 
                                 WHERE ORG.INTERNAL_ID = USR.AFFILIATED_ORGANIZATION_ID);

--Put here instead of disease.groovy because of java heap issue
CREATE INDEX sdtmp_index ON stg_dw_study_disease(nci_id);
DROP TABLE IF EXISTS stg_dw_study_disease_tree;
CREATE TABLE stg_dw_study_disease_tree AS
    SELECT DISTINCT
           CAST('NO' AS character varying(3)) AS ct_gov_xml_indicator,
           CAST('TREE' AS character varying(5)) AS inclusion_indicator, 
           sd.date_last_created, 
           sd.date_last_updated, 
           dp.disease_code,
           dp.preferred_name AS disease_preferred_name, 
           dp.menu_display_name AS disease_menu_display_name, 
           sd.internal_system_id,
           dp.parent_disease_identifier AS internal_system_id2,
           CAST(NULL AS character varying(3)) AS lead_disease_indicator, 
           sd.nci_id, 
           dp.nt_term_identifier AS nci_thesaurus_concept_id, 
           sd.user_last_created, 
           sd.user_last_updated
      FROM stg_dw_study_disease sd
      JOIN stg_dw_disease_parents dp ON (sd.internal_system_id2 = dp.disease_identifier);

ALTER TABLE stg_dw_study_disease_tree ADD PRIMARY KEY (internal_system_id, internal_system_id2);

DROP TABLE IF EXISTS stg_dw_study_disease_tree_keep;
CREATE TABLE stg_dw_study_disease_tree_keep AS
    SELECT nci_id, MIN(internal_system_id) AS internal_system_id, internal_system_id2
    FROM stg_dw_study_disease_tree
    GROUP BY nci_id, internal_system_id2;

ALTER TABLE stg_dw_study_disease_tree_keep ADD PRIMARY KEY (internal_system_id, internal_system_id2);

INSERT INTO stg_dw_study_disease 
    SELECT * 
    FROM stg_dw_study_disease_tree 
    WHERE (internal_system_id, internal_system_id2) 
          IN (SELECT internal_system_id, internal_system_id2 FROM stg_dw_study_disease_tree_keep);

DELETE FROM stg_dw_study_disease WHERE INCLUSION_INDICATOR = 'TREE'
  AND (nci_id, internal_system_id2) IN (SELECT nci_id, internal_system_id2 FROM stg_dw_study_disease WHERE INCLUSION_INDICATOR = 'TRIAL');

DROP TABLE stg_dw_study_disease_tree_keep;
DROP TABLE stg_dw_study_disease_tree;
DROP TABLE stg_dw_disease_parents;
DROP INDEX IF EXISTS sdtmp_index;
