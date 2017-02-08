CREATE OR REPLACE FUNCTION biomarker_evaluation()
  RETURNS void AS
$BODY$
DECLARE
    v_page_id integer := 0;
    v_study_protocol integer :=0;
    v_status_code text := '' ;
    
    curs_protocol_id CURSOR IS
    SELECT so.study_protocol_id FROM study_otheridentifiers AS so 
    JOIN study_protocol AS sp ON sp.identifier=so.study_protocol_id WHERE sp.status_code ='ACTIVE'
    AND so.study_protocol_id IN 
    (SELECT pa.study_protocol_identifier FROM planned_activity AS pa WHERE pa.identifier IN 
    (SELECT pm.identifier FROM planned_marker AS pm )) 
    AND so.root = '2.16.840.1.113883.3.26.4.3' ORDER BY so.root ;

BEGIN
        OPEN curs_protocol_id;
        LOOP
        FETCH curs_protocol_id INTO v_study_protocol;
            EXIT WHEN NOT FOUND;
            
            SELECT dws.status_code INTO v_status_code FROM 
            document_workflow_status as dws WHERE dws.study_protocol_identifier = v_study_protocol
            ORDER BY dws.identifier desc LIMIT 1 ;
            IF v_status_code != 'REJECTED' THEN
                UPDATE planned_marker SET evaluation_type_code='Level/Quantity' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) and evaluation_type_code IS NULL;
            END IF; 
            
        END LOOP;
        CLOSE curs_protocol_id;
END;
$BODY$
  LANGUAGE plpgsql;

SELECT bioMarker_evaluation();


CREATE OR REPLACE FUNCTION biomarker_assay_type()
  RETURNS void AS
$BODY$
DECLARE
    v_page_id integer := 0;
    v_study_protocol integer :=0;
    v_status_code text := '' ;
    
    curs_protocol_id CURSOR IS
    SELECT so.study_protocol_id FROM study_otheridentifiers AS so 
    JOIN study_protocol AS sp ON sp.identifier=so.study_protocol_id WHERE sp.status_code ='ACTIVE'
    AND so.study_protocol_id IN 
    (SELECT pa.study_protocol_identifier FROM planned_activity AS pa WHERE pa.identifier IN 
    (SELECT pm.identifier FROM planned_marker AS pm )) 
    AND so.root = '2.16.840.1.113883.3.26.4.3' ORDER BY so.root ;

BEGIN
        OPEN curs_protocol_id;
        LOOP
        FETCH curs_protocol_id INTO v_study_protocol;
            EXIT WHEN NOT FOUND;
            
            SELECT dws.status_code INTO v_status_code FROM 
            document_workflow_status as dws WHERE dws.study_protocol_identifier = v_study_protocol
            ORDER BY dws.identifier desc LIMIT 1 ;
            IF v_status_code != 'REJECTED' THEN
                UPDATE planned_marker SET assay_type_code='PCR' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code IN ('RT_PCR','RT-PCR');
                UPDATE planned_marker SET assay_type_code='In Situ Hybridization' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code IN ('IN_SITU_HYBRIDIZATION','Fluorescence in situ hybridization','FISH');
                UPDATE planned_marker SET assay_type_code='Microarray' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code IN ('MICROARRAY','CGH');
                UPDATE planned_marker SET assay_type_code='Immunohistochemistry (IHC)' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code = 'IMMUNOHISTOCHEMISTRY';
                UPDATE planned_marker SET assay_type_code='Unspecified' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code = 'UNSPECIFIED';    
                UPDATE planned_marker SET assay_type_code='Other' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code = 'OTHER';
                UPDATE planned_marker SET assay_type_code='Other', assay_type_other_text='Serum ECD test' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code = 'SERUM_ECD_TEST';
                UPDATE planned_marker SET assay_type_code='Other', assay_type_other_text='LOH' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code = 'LOH';
                UPDATE planned_marker SET assay_type_code='Other', assay_type_other_text='Ligand binding assay' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_type_code = 'LIGAND_BINDING_ASSAY';
            END IF;             
        END LOOP;
        CLOSE curs_protocol_id;
END;
$BODY$
  LANGUAGE plpgsql;

SELECT biomarker_assay_type();
 

CREATE OR REPLACE FUNCTION biomarker_assay_use()
  RETURNS void AS
$BODY$
DECLARE
    v_page_id integer := 0;
    v_study_protocol integer :=0;
    v_status_code text := '' ;
    
    curs_protocol_id CURSOR IS
    SELECT so.study_protocol_id FROM study_otheridentifiers AS so 
    JOIN study_protocol AS sp ON sp.identifier=so.study_protocol_id WHERE sp.status_code ='ACTIVE'
    AND so.study_protocol_id IN 
    (SELECT pa.study_protocol_identifier FROM planned_activity AS pa WHERE pa.identifier IN 
    (SELECT pm.identifier FROM planned_marker AS pm )) 
    AND so.root = '2.16.840.1.113883.3.26.4.3' ORDER BY so.root ;

BEGIN
        OPEN curs_protocol_id;
        LOOP
        FETCH curs_protocol_id INTO v_study_protocol;
            EXIT WHEN NOT FOUND;
            
            SELECT dws.status_code INTO v_status_code FROM 
            document_workflow_status as dws WHERE dws.study_protocol_identifier = v_study_protocol
            ORDER BY dws.identifier desc LIMIT 1 ;
            IF v_status_code != 'REJECTED' THEN
                UPDATE planned_marker SET assay_use_code='Integrated' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_use_code IN ('CORRELATIVE','INTEGRATED','RESEARCH','UNCLEAR','UNSPECIFIED');
                UPDATE planned_marker SET assay_use_code='Integral' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_use_code = 'INTEGRAL';
            END IF; 
            
        END LOOP;
        CLOSE curs_protocol_id;
END;
$BODY$
  LANGUAGE plpgsql;

SELECT biomarker_assay_use();



CREATE OR REPLACE FUNCTION biomarker_assay_purpose()
  RETURNS void AS
$BODY$
DECLARE
    v_page_id integer := 0;
    v_study_protocol integer :=0;
    v_status_code text := '' ;
    
    curs_protocol_id CURSOR IS
    SELECT so.study_protocol_id FROM study_otheridentifiers AS so 
    JOIN study_protocol AS sp ON sp.identifier=so.study_protocol_id WHERE sp.status_code ='ACTIVE'
    AND so.study_protocol_id IN 
    (SELECT pa.study_protocol_identifier FROM planned_activity AS pa WHERE pa.identifier IN 
    (SELECT pm.identifier FROM planned_marker AS pm )) 
    AND so.root = '2.16.840.1.113883.3.26.4.3' ORDER BY so.root ;

BEGIN
        OPEN curs_protocol_id;
        LOOP
        FETCH curs_protocol_id INTO v_study_protocol;
            EXIT WHEN NOT FOUND;
            
            SELECT dws.status_code INTO v_status_code FROM 
            document_workflow_status as dws WHERE dws.study_protocol_identifier = v_study_protocol
            ORDER BY dws.identifier desc LIMIT 1 ;
            IF v_status_code != 'REJECTED' THEN
                UPDATE planned_marker SET assay_purpose_code='Eligibility Criterion' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_purpose_code = 'ELIGIBILITY_CRITERION';
                UPDATE planned_marker SET assay_purpose_code='Research' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_purpose_code IN ('OTHER','RESEARCH','Unspecified','UNSPECIFIED');
                UPDATE planned_marker SET assay_purpose_code='Response Assessment' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_purpose_code = 'PD';
                UPDATE planned_marker SET assay_purpose_code='Stratification Factor' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_purpose_code = 'STRATIFICATION_FACTOR';
                UPDATE planned_marker SET assay_purpose_code='Treatment Assignment' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and assay_purpose_code = 'TREATMENT_ASSIGNMENT';
            END IF;             
        END LOOP;
        CLOSE curs_protocol_id;
END;
$BODY$
  LANGUAGE plpgsql;

SELECT biomarker_assay_purpose();
 
 


CREATE OR REPLACE FUNCTION biomarker_specimen_type()
  RETURNS void AS
$BODY$
DECLARE
    v_page_id integer := 0;
    v_study_protocol integer :=0;
    v_status_code text := '' ;
    
    curs_protocol_id CURSOR IS
    SELECT so.study_protocol_id FROM study_otheridentifiers AS so 
    JOIN study_protocol AS sp ON sp.identifier=so.study_protocol_id WHERE sp.status_code ='ACTIVE'
    AND so.study_protocol_id IN 
    (SELECT pa.study_protocol_identifier FROM planned_activity AS pa WHERE pa.identifier IN 
    (SELECT pm.identifier FROM planned_marker AS pm )) 
    AND so.root = '2.16.840.1.113883.3.26.4.3' ORDER BY so.root ;

BEGIN
        OPEN curs_protocol_id;
        LOOP
        FETCH curs_protocol_id INTO v_study_protocol;
            EXIT WHEN NOT FOUND;
            
            SELECT dws.status_code INTO v_status_code FROM 
            document_workflow_status as dws WHERE dws.study_protocol_identifier = v_study_protocol
            ORDER BY dws.identifier desc LIMIT 1 ;
            IF v_status_code != 'REJECTED' THEN
                UPDATE planned_marker SET tissue_specimen_type_code='Plasma' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_specimen_type_code IN ('CITRATED_PLASMA','PLASMA');
                UPDATE planned_marker SET tissue_specimen_type_code='Serum' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_specimen_type_code = 'SERUM';
                UPDATE planned_marker SET tissue_specimen_type_code='Tissue' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_specimen_type_code = 'TISSUE';
                UPDATE planned_marker SET tissue_specimen_type_code='Unspecified' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_specimen_type_code = 'UNSPECIFIED';
                UPDATE planned_marker SET tissue_specimen_type_code='Whole Blood' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_specimen_type_code = 'WHOLE_BLOOD';
                UPDATE planned_marker SET tissue_specimen_type_code='Other', specimen_type_other_text='Other fluid' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_specimen_type_code = 'OTHER_FLUID';
            END IF; 
            
        END LOOP;
        CLOSE curs_protocol_id;
END;
$BODY$
  LANGUAGE plpgsql;

SELECT biomarker_specimen_type();


CREATE OR REPLACE FUNCTION biomarker_specimen_collection()
  RETURNS void AS
$BODY$
DECLARE
    v_page_id integer := 0;
    v_study_protocol integer :=0;
    v_status_code text := '' ;
    
    curs_protocol_id CURSOR IS
    SELECT so.study_protocol_id FROM study_otheridentifiers AS so 
    JOIN study_protocol AS sp ON sp.identifier=so.study_protocol_id WHERE sp.status_code ='ACTIVE'
    AND so.study_protocol_id IN 
    (SELECT pa.study_protocol_identifier FROM planned_activity AS pa WHERE pa.identifier IN 
    (SELECT pm.identifier FROM planned_marker AS pm )) 
    AND so.root = '2.16.840.1.113883.3.26.4.3' ORDER BY so.root ;

BEGIN
        OPEN curs_protocol_id;
        LOOP
        FETCH curs_protocol_id INTO v_study_protocol;
            EXIT WHEN NOT FOUND;
            
            SELECT dws.status_code INTO v_status_code FROM 
            document_workflow_status as dws WHERE dws.study_protocol_identifier = v_study_protocol
            ORDER BY dws.identifier desc LIMIT 1 ;
            IF v_status_code != 'REJECTED' THEN
                UPDATE planned_marker SET tissue_collection_method_code='Mandatory' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_collection_method_code IN ('MANDATORY','UNSPECIFIED','MANDATORY_ON_CONSENT');
                UPDATE planned_marker SET tissue_collection_method_code='Voluntary' where identifier IN
                (SELECT pa.identifier FROM planned_activity AS pa WHERE pa.study_protocol_identifier = v_study_protocol) 
                and tissue_collection_method_code = 'VOLUNTARY';
                
            END IF; 
            
        END LOOP;
        CLOSE curs_protocol_id;
END;
$BODY$
  LANGUAGE plpgsql ;

SELECT biomarker_specimen_collection();