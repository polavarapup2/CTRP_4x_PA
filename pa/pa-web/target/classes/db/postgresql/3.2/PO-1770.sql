-- Add NCI as default collaborator for all non-proprietary trials.
CREATE OR REPLACE FUNCTION insert_nci_collaborator(v_org_name varchar, v_status varchar, v_functional_code varchar) RETURNS void AS '
  DECLARE
    v_study_site_id bigint;
    v_org_id bigint;
    v_research_org_id bigint;
    row_data study_protocol%ROWTYPE;
    v_study_protocol_already_exists bigint;
  BEGIN
    -- Get org representing NCI
    SELECT INTO v_org_id identifier FROM organization
        WHERE name = v_org_name AND status_code != ''NULLIFIED'' order by identifier limit 1;

    -- Get research org that represents NCI
    SELECT INTO v_research_org_id identifier FROM research_organization
        WHERE organization_identifier = v_org_id AND status_code != ''NULLIFIED'' order by identifier limit 1;

    -- Iterate through the results of a query.
    FOR row_data IN SELECT sp.* FROM study_protocol sp WHERE sp.proprietary_trial_indicator=false LOOP
        -- check to see if this study site identifer already has a nci study site with functional code = FUNDING_SOURCE
        SELECT INTO v_study_protocol_already_exists identifier FROM study_site
            WHERE   functional_code = v_functional_code AND
                    research_organization_identifier = v_research_org_id AND
                    study_protocol_identifier = row_data.identifier order by row_data.identifier limit 1;

        IF v_study_protocol_already_exists IS NULL THEN
            v_study_site_id := nextval(''hibernate_sequence'');
            INSERT INTO study_site (identifier, functional_code, study_protocol_identifier, research_organization_identifier, status_code,
                status_date_range_low, date_last_created, user_last_created, date_last_updated, user_last_updated)
            VALUES (v_study_site_id, v_functional_code, row_data.identifier, v_research_org_id, v_status, now(), now(), ''ejbclient'', now(), ''ejbclient'');
        END IF;
    END LOOP;
  END;
  ' LANGUAGE plpgsql;

-- Execute the function with appropriate arguments
  select insert_nci_collaborator('National Cancer Institute', 'ACTIVE', 'FUNDING_SOURCE');