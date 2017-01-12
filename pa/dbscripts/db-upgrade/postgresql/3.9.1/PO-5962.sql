alter table csm_user add column automated_curation boolean;
update csm_user set automated_curation=true where login_name='/O=caBIG/OU=caGrid/OU=Stage LOA1/OU=NCI STAGE/CN=CTEPRSSTest';
update csm_user set automated_curation=true where login_name='/O=caBIG/OU=caGrid/OU=LOA1/OU=NCI/CN=CTEPRSSTest';