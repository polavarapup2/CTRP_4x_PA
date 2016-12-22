CREATE INDEX study_site_study_protocol_idx  ON study_site  USING btree  (study_protocol_identifier);

CREATE INDEX study_checkout_study_protocol_idx  ON study_checkout  USING btree  (study_protocol_identifier);

CREATE INDEX study_inbox_study_protocol_idx  ON study_inbox  USING btree  (study_protocol_identifier);


