DROP SEQUENCE IF EXISTS pa_properties_identifier_seq CASCADE;
ALTER TABLE pa_properties ADD CONSTRAINT pa_properties_name_uq UNIQUE ("name");
insert into pa_properties (identifier,"name","value") values ((select max(identifier) from pa_properties) + 1,'log.email.address','log@example.com');
