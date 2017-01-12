


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
<td>If Completed, Terminated, Withdrawn -Confirm "Send trial information to ClinicalTrials.gov?" is set to "No"</td>
<td>${sendToCtGovUpdated} </td>
</tr>
<tr>
<td>Results Designee Access Revoked?</td>
<td>${designeeAccessRevoked} ${designeeAccessRevokedDate} </td>
</tr>
<tr>
<td>All Changes Made in CTRP and ClinicalTrials.gov?</td>
<td>${changesInCtrpCtGov} ${changesInCtrpCtGovDate} </td>
</tr>

</table>
');

alter table study_notes drop column discrepancy_type;
alter table study_notes drop column study_note_type ;
alter table study_notes rename to study_record_change;


 