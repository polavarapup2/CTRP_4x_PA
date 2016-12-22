drop table if exists ctgov_org_map;

create table ctgov_org_map
(
    identifier serial not null,
    role varchar(20),
    ctgov_name varchar(256),    
    ctgov_city varchar(64),
    ctgov_state varchar(64),
    ctgov_zip varchar(32),
    ctgov_country varchar(64),
    cdr_id varchar(32),
    pdq_name varchar(128), 
    pdq_city varchar(64),
    pdq_state varchar(64),
    pdq_country varchar(64),
    po_id varchar(64),
    ctep_id varchar(64),
    primary key (identifier) 
) ;

drop table if exists ctgov_person_map;

create table ctgov_person_map
(
    identifier serial not null,   
    role varchar(20),
    ctgov_affiliation varchar(256),
    ctgov_fullname varchar(128),
    ctgov_firstname varchar(64),
    ctgov_middlename varchar(64),
    ctgov_lastname varchar(64),
    ctgov_prefix varchar(64),
    ctgov_suffix varchar(64),
    cdr_id varchar(16),
    pdq_fullname varchar(128),
    pdq_firstname varchar(64),
    pdq_middlename varchar(64),
    pdq_lastname varchar(64),
    pdq_city varchar(64),
    pdq_state varchar(64),
    pdq_country varchar(32),
    po_id varchar(16),
    ctep_id varchar(16),
    primary key (identifier) 
) ;


