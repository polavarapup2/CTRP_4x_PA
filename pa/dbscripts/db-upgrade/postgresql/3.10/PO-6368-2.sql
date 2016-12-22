CREATE INDEX ctgov_org_map_idx01 ON ctgov_org_map  USING btree (ctgov_name);

CREATE INDEX ctgov_person_map_idx01 ON ctgov_person_map  USING btree (ctgov_fullname);

CREATE INDEX ctgov_person_map_idx02 ON ctgov_person_map  USING btree (ctgov_firstname, ctgov_lastname);

CREATE INDEX ctgov_person_map_idx03 ON ctgov_person_map  USING btree (pdq_firstname, pdq_lastname);