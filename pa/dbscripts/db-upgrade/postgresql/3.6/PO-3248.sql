INSERT INTO PA_PROPERTIES (identifier, name, value) values ((select max(identifier)+1 from PA_PROPERTIES), 'ctrp.support.email', 'ncictrp-techsupport@mail.nih.gov');