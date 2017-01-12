--Inserting property for allowable regulatory authorities with no country name

INSERT INTO pa_properties values((select max(identifier) + 1 from pa_properties),'allowed.regulatory.authorities.no.country.name', 'IDMC');

--Inserting regulatory authority IDMC with country United States

INSERT INTO REGULATORY_AUTHORITY (identifier,authority_name,country_identifier) values(nextval('ra_sequence'),'IDMC',1026);
