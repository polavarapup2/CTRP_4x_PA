--Author   : Anubhav Das
--Date     : 04/16/2014        
--Jira#    : PO-6887 - PA: Enhance search for Updates of, and Acknowledged Updates for, trials from ClinicalTrials.gov 
--Comments : Add ADMIN_ACK_USER_ID/SCIENTIFIC_ACK_USER_ID columns to study_inbox table to track users who acknowledge admin/scientific updates. 
--Comments : Populates admin/scientific acknowledge user information in existing study inbox entries by querying audit logs. 

ALTER TABLE STUDY_INBOX ADD COLUMN ADMIN_ACK_USER_ID BIGINT;
ALTER TABLE STUDY_INBOX ADD COLUMN SCIENTIFIC_ACK_USER_ID BIGINT;

ALTER TABLE STUDY_INBOX ADD CONSTRAINT FK_STUDY_INBOX_ADMIN_ACK_CSM_USER 
FOREIGN KEY (ADMIN_ACK_USER_ID) REFERENCES CSM_USER (USER_ID) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE STUDY_INBOX ADD CONSTRAINT FK_STUDY_INBOX_SCI_ACK_CSM_USER
FOREIGN KEY (SCIENTIFIC_ACK_USER_ID) REFERENCES CSM_USER (USER_ID) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE SET NULL;

CREATE TEMPORARY TABLE auditlogrecord_temp (LIKE auditlogrecord INCLUDING DEFAULTS);
CREATE TEMPORARY TABLE auditlogdetail_temp (LIKE auditlogdetail INCLUDING DEFAULTS);

insert into auditlogrecord_temp 
	select * from auditlogrecord 
		where entityname = 'STUDY_INBOX';
		
insert into auditlogdetail_temp 
	select * from auditlogdetail 
		where attribute = 'adminCloseDate' OR attribute = 'scientificCloseDate';		
	  


update study_inbox a set admin_ack_user_id = 
(select b.user_id 
from csm_user b, auditlogrecord_temp c, auditlogdetail_temp d 
where a.type_code = 'UPDATE' 
and a.admin_close_date IS NOT NULL
and a.identifier = c.entityid
and c.entityname = 'STUDY_INBOX'
and c.id = d.record_id
and d.attribute = 'adminCloseDate'
and to_timestamp(d.newvalue, 'YYYY-MM-DD HH24:MI:SS.MS') = a.admin_close_date
and b.login_name = c.username);

update study_inbox a set scientific_ack_user_id = 
(select b.user_id 
from csm_user b, auditlogrecord_temp c, auditlogdetail_temp d 
where a.type_code = 'UPDATE' 
and a.scientific_close_date IS NOT NULL
and a.identifier = c.entityid
and c.entityname = 'STUDY_INBOX'
and c.id = d.record_id
and d.attribute = 'scientificCloseDate'
and to_timestamp(d.newvalue, 'YYYY-MM-DD HH24:MI:SS.MS') = a.scientific_close_date
and b.login_name = c.username);
