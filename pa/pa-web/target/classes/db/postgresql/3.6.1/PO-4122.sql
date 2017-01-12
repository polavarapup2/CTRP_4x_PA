CREATE INDEX arm_study_protocol_idx  ON arm  (study_protocol_identifier);

CREATE INDEX document_study_protocol_idx  ON document (study_protocol_identifier);

CREATE INDEX  mapping_identifier_study_protocol_idx  ON  mapping_identifier (study_protocol_identifier);

CREATE INDEX  messages_log_study_protocol_idx  ON  messages_log  USING btree (study_protocol_identifier);

CREATE INDEX  messages_log_audit_study_protocol_idx  ON  messages_log_audit (study_protocol_identifier);

CREATE INDEX  performed_activity_study_protocol_idx  ON  performed_activity (study_protocol_identifier);

CREATE INDEX  performed_observation_result_study_protocol_idx  ON performed_observation_result (study_protocol_identifier);

CREATE INDEX  planned_activity_study_protocol_idx  ON  planned_activity (study_protocol_identifier);

CREATE INDEX  stratum_group_study_protocol_idx  ON  stratum_group (study_protocol_identifier);

CREATE INDEX  study_disease_study_protocol_idx  ON  study_disease (study_protocol_identifier);

CREATE INDEX  study_indlde_study_protocol_idx  ON  study_indlde (study_protocol_identifier);

CREATE INDEX  study_objective_study_protocol_idx  ON  study_objective  (study_protocol_identifier);

CREATE INDEX  study_onhold_study_protocol_idx  ON  study_onhold  (study_protocol_identifier);

CREATE INDEX  study_otheridentifiers_pkey  ON  study_otheridentifiers (study_protocol_id);

CREATE INDEX  study_otheridentifiers_stage_pkey  ON  study_otheridentifiers_stage  (study_protocol_id);

CREATE INDEX  study_outcome_measure_study_protocol_idx  ON  study_outcome_measure (study_protocol_identifier);

CREATE INDEX  study_recruitment_status_study_protocol_idx  ON  study_recruitment_status (study_protocol_identifier);

CREATE INDEX  study_regulatory_authority_study_protocol_idx  ON  study_regulatory_authority (study_protocol_identifier);

CREATE INDEX  study_relationship_source_study_protocol_idx  ON  study_relationship (source_study_protocol_identifier);

CREATE INDEX  study_relationship_target_study_protocol_idx  ON  study_relationship (target_study_protocol_identifier);

CREATE INDEX  study_resourcing_study_protocol_idx  ON  study_resourcing (study_protocol_identifier);

CREATE INDEX  study_site_accrual_access_study_site_idx  ON  study_site_accrual_access (study_site_identifier);

CREATE INDEX  study_site_accrual_status_study_site_idx  ON  study_site_accrual_status (study_site_identifier);

CREATE INDEX  study_site_contact_study_site_idx  ON  study_site_contact (study_site_identifier);

CREATE INDEX  study_site_overall_status_study_site_idx  ON  study_site_overall_status (study_site_identifier);

CREATE INDEX  study_site_subject_accrual_count_study_site_idx  ON  study_site_subject_accrual_count (study_site_identifier);

CREATE INDEX  study_site_subject_accrual_count_study_protocol_idx  ON  study_site_subject_accrual_count (study_protocol_identifier);

CREATE INDEX  study_subject_audit_study_site_idx  ON study_subject_audit (study_site_identifier);

CREATE INDEX  study_subject_audit_study_protocol_idx  ON  study_subject_audit (study_protocol_identifier);

CREATE INDEX  study_subject_study_site_idx  ON study_subject (study_site_identifier);

CREATE INDEX  study_subject_study_protocol_idx  ON  study_subject (study_protocol_identifier);

CREATE INDEX  study_subject_patient_idx  ON  study_subject (patient_identifier);