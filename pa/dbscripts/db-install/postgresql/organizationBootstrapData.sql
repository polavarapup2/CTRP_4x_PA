INSERT INTO organization (identifier, assigned_identifier, name, city, postal_code, country_name, state, status_code)
    VALUES (1, '1', 'ClinicalTrials.gov', 'Rockville', '20852', 'USA', 'MD', 'ACTIVE');
INSERT INTO organization (identifier, assigned_identifier, name, city, postal_code, country_name, state, status_code)
    VALUES (2, '2', 'Cancer Therapy Evaluation Program', 'Rockville', '20852', 'USA', 'MD', 'ACTIVE');
INSERT INTO organization (identifier, assigned_identifier, name, city, postal_code, country_name, state, status_code)
    VALUES (3, '3', 'National Cancer Institute Division of Cancer Prevention', 'Rockville', '20852', 'USA', 'MD', 'ACTIVE');
INSERT INTO organization (identifier, assigned_identifier, name, city, postal_code, country_name, state, status_code)
    VALUES (4, '4', 'National Cancer Institute', 'Rockville', '20852', 'USA', 'MD', 'ACTIVE');
INSERT INTO organization (identifier, assigned_identifier, name, city, postal_code, country_name, state, status_code)
    VALUES (5, '5', 'NCI - Center for Cancer Research', 'Rockville', '20852', 'USA', 'MD', 'ACTIVE');

INSERT INTO research_organization (identifier, assigned_identifier, organization_identifier, status_code) values (1, '1', 1, 'PENDING');
INSERT INTO research_organization (identifier, assigned_identifier, organization_identifier, status_code) values (2, '2', 2, 'PENDING');
INSERT INTO research_organization (identifier, assigned_identifier, organization_identifier, status_code) values (3, '3', 3, 'PENDING');
INSERT INTO research_organization (identifier, assigned_identifier, organization_identifier, status_code) values (4, '4', 4, 'PENDING');
INSERT INTO research_organization (identifier, assigned_identifier, organization_identifier, status_code) values (5, '5', 5, 'PENDING');