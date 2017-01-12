-- Add columns to save ccct user name
alter table document drop column  ccct_user_id;
alter table document add column ccct_user_name character varying(2000);
    
