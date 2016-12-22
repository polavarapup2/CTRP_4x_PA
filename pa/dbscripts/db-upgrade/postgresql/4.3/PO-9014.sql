-- PO-9014 Implement "Results Cover Sheet" screen 

CREATE TABLE study_notes
(
  identifier serial NOT NULL,
  discrepancy_type character varying(2000),
  change_type character varying(2000),
  action_taken character varying(2000),
  action_completion_date timestamp without time zone,
  study_note_type character varying(100),
  study_protocol_identifier bigint NOT NULL,
  date_last_created timestamp without time zone,
  date_last_updated timestamp without time zone,
  user_last_created_id integer,
  user_last_updated_id integer
  
  );

  ALTER TABLE study_notes ADD CONSTRAINT FK_DOCUMENT_STUDY_PROTOCOL
FOREIGN KEY (study_protocol_identifier) REFERENCES STUDY_PROTOCOL (IDENTIFIER)
ON DELETE CASCADE;

alter table study_protocol add column use_standard_language boolean;
alter table study_protocol add column date_entered_in_prs boolean;
alter table study_protocol add column designee_acess_revoked boolean;
alter table study_protocol add column designee_acess_revoked_date timestamp;
alter table study_protocol add column changes_in_ctrp_ctgov boolean;
alter table study_protocol add column changes_in_ctrp_ctgov_date timestamp;
alter table study_protocol add column send_to_ctgov_updated boolean;


delete from pa_properties where name='ctro.coversheet.email.subject';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctro.coversheet.email.subject',
'Trials Result Coversheet : ${nciId} ');

delete from pa_properties where name='ctro.coversheet.email.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctro.coversheet.email.body',
'<h2>Final Record Clean-up & Release</h2>
<table border="1" style="width:100%">
<tr>
<td>Certain Agreements Use Standard Language?</td>
<td>${useStandardLanguage}</td>
</tr>
<tr>
<td>If Completed, Terminated, Withdrawn - Completion Date Entered in PRS?</td>
<td>${dateEnteredInPrs}</td>
</tr>
<tr>
<td>Results Designee Access Revoked?</td>
<td>${designeeAccessRevoked} ${designeeAccessRevokedDate} </td>
</tr>
<tr>
<td>All Changes Made in CTRP and ClinicalTrials.gov?</td>
<td>${changesInCtrpCtGov} ${changesInCtrpCtGovDate} </td>
</tr>
<tr>
<td>If Completed, Terminated, Withdrawn -Confirm "Send trial information to ClinicalTrials.gov?" is set to "No"</td>
<td>${sendToCtGovUpdated} </td>
</tr>
</table>
');

 