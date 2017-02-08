-- Function: clone_planned_activity_4_amend()

-- DROP FUNCTION clone_planned_activity_4_amend();

CREATE OR REPLACE FUNCTION clone_planned_activity_4_amend()
  RETURNS void AS
$$
DECLARE  
pa_data planned_activity%ROWTYPE;
v_planned_activity_id bigint;
tr RECORD;
BEGIN
     FOR tr in select ai.identifier as ai_row, ai.arm_identifier as arm_identifier, ai.planned_activity_identifier as planned_act_identifier,
    a.study_protocol_identifier as new_trial_id
    from arm_intervention ai, planned_activity pa, arm a 
    where ai.arm_identifier = a.identifier 
    and pa.identifier = ai.planned_activity_identifier
    and a.study_protocol_identifier != pa.study_protocol_identifier LOOP
    
        v_planned_activity_id := nextval('hibernate_sequence');
    
    SELECT INTO pa_data * FROM planned_activity where identifier = tr.planned_act_identifier;
    pa_data.study_protocol_identifier = tr.new_trial_id;
    pa_data.identifier = v_planned_activity_id;
    insert into planned_activity values (pa_data.identifier,
          pa_data.category_code,
          pa_data.subcategory_code,
          pa_data.lead_product_indicator,
          pa_data.text_description,
          pa_data.study_protocol_identifier,
          pa_data.intervention_identifier,
          pa_data.planned_activity_type,
          pa_data.inclusion_indicator,
          pa_data.criterion_name,
          pa_data.operator,
          pa_data.min_value,
          pa_data.unit,
          pa_data.eligible_gender_code,
          pa_data.date_last_created,
          pa_data.date_last_updated,
          pa_data.display_order,
          pa_data.dose_min_value,
          pa_data.dose_min_unit,
          pa_data.dose_max_value,
          pa_data.dose_max_unit,
          pa_data.dose_description,
          pa_data.dose_form_code,
          pa_data.dose_frequency_code,
          pa_data.dose_regimen,
          pa_data.dose_total_min_value,
          pa_data.dose_total_min_unit,
          pa_data.dose_total_max_value,
          pa_data.dose_total_max_unit,
          pa_data.route_of_administration_code,
          pa_data.dose_duration_value,
          pa_data.dose_duration_unit,
          pa_data.approach_site_code,
          pa_data.target_site_code,
          pa_data.method_code,
          pa_data.structured_indicator,
          pa_data.text_value,
          pa_data.min_unit,
          pa_data.max_value,
          pa_data.max_unit,
          pa_data.cde_public_identifier,
          pa_data.cde_version_number,
          pa_data.user_last_created_id,
          pa_data.user_last_updated_id);
        update arm_intervention set planned_activity_identifier = v_planned_activity_id where identifier = tr.ai_row; 
     END LOOP;
END;
$$
  LANGUAGE 'plpgsql';
  
  select clone_planned_activity_4_amend();

