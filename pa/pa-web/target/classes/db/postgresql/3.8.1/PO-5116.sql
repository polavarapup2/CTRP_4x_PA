INSERT INTO pa_properties values 
((select max(identifier) + 1 from pa_properties),'CDE_REQ_TO_EMAIL_SUB_PERMISSIBLE','New Permissible Value Request');
