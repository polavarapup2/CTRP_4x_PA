insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'go.usa.gov.api.shorten.url', 'http://go.usa.gov/api/shorten.json?login={user_name}&apiKey={api_key}&longUrl={longUrl}');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'go.usa.gov.api.timeout', '60000');    
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
    'go.usa.gov.api.shorten.json.path', 'response.data.entry.[0].short_url');    

INSERT INTO accounts (account_name,external_system,username,encrypted_password) 
    VALUES ('go.usa.gov' ,'GO_USA_GOV' ,'nci-ctrp' ,'' );