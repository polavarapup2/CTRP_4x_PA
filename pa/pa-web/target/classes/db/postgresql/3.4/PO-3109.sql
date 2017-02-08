DROP TABLE IF EXISTS STUDY_ANATOMIC_SITE;

CREATE TABLE STUDY_ANATOMIC_SITE (
    STUDY_PROTOCOL_IDENTIFIER BIGINT NOT NULL,
    ANATOMIC_SITES_IDENTIFIER BIGINT NOT NULL,
    UNIQUE (STUDY_PROTOCOL_IDENTIFIER, ANATOMIC_SITES_IDENTIFIER)
);

ALTER TABLE STUDY_ANATOMIC_SITE ADD CONSTRAINT FK_STUDY_ANATOMIC_SITE_STUDY_PROTOCOL
FOREIGN KEY (STUDY_PROTOCOL_IDENTIFIER) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

ALTER TABLE STUDY_ANATOMIC_SITE ADD CONSTRAINT FK_STUDY_ANATOMIC_SITE_ANATOMIC_SITES
FOREIGN KEY (ANATOMIC_SITES_IDENTIFIER) REFERENCES ANATOMIC_SITES (IDENTIFIER)
ON DELETE RESTRICT;

delete from anatomic_sites;

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'lip, oral cavity and pharynx', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'esophagus', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'stomach', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'small intestine', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'colon', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'rectum', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'anus', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'liver', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'pancreas', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other digestive organs', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'larynx', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'lung', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other respiratory and intrathoracic organs', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'bones and joints', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'soft tissue', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'melanoma, skin', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'kaposi''s sarcoma', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'mycosis fungoides', ''); 

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other skin', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'breast - female', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'breast - male', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'cervix', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'corpus uteri', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'ovary', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other female genital', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'prostate', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other male genital', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'urinary bladder', '');     

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'kidney', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other urinary', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'eye and orbit', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'brain and nervous system', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'thyroid', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'other endocrine system', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'non-hodgkin''s lymphoma', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'hodgkin''s lymphoma', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'multiple myeloma', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'lymphoid leukemia', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'myeloid and moncytic leukemia', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'Leukemia, other', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'Leukemia, not otherwise specified', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'Other Hematopoietic', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'Unknown Sites', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'Ill-Defined Sites', '');

INSERT INTO anatomic_sites(identifier, code, display_name) VALUES (nextval('anatomic_sites_identifier_seq'), 'multiple', ''); 

update anatomic_sites set display_name = code;