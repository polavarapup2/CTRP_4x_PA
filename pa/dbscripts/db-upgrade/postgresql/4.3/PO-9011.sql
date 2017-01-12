-- Add columns to capture date time for ctro and ccct review
alter table document add column  ctro_user_date_created timestamp without time zone;
alter table document add column  ccct_user_date_created timestamp without time zone;
alter table document add column   ctro_user_id int8 ;
alter table document add column   ccct_user_id int8 ;

ALTER TABLE document ADD CONSTRAINT ctro_user_id FOREIGN KEY (ctro_user_id) 
    REFERENCES csm_user (user_id);
    
ALTER TABLE document ADD CONSTRAINT ccct_user_id FOREIGN KEY (ccct_user_id) 
    REFERENCES csm_user (user_id);  
    
    
-- roles to add to query to be displayed in users dropdown    
 delete from pa_properties where name='pa.ccct.ctro.roles.list';
 
 insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties),
'pa.ccct.ctro.roles.list','''Abstractor'',''AdminAbstractor'',''ScientificAbstractor'',''SuAbstractor'',''Submitter''');

--ctro email subject and body and to list



delete from pa_properties where name='ctro.comparision.email.subject';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctro.comparision.email.subject',
'Results Reporting & Tracking: ${nciId} Trial Comparison Document Review/Update');


delete from pa_properties where name='ctro.comparision.email.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctro.comparision.email.body',
'<hr>
<table>
<tr>
<td><b>NCIt ID:</b></td>
<td>${nctId}</td>
</tr>
</table>
<hr>
<p>
Date: ${currentDate}
<p>
Dear Scientific Team,
<p>
Can you review the attached comparison document for  ${nciId}? Please let me know if you have any questions or when updates have been completed.
<p>
<p>
<b>
Thanks,
<br>
Kathryn
</b>
<p>
In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov. 

');

 
  
--ccct email subject and body
delete from pa_properties where name='ccct.comparision.email.tolist';

--insert real email in prod
insert into pa_properties
   select(select max(identifier) + 1 from pa_properties), 'ccct.comparision.email.tolist',
'kathryn.dusold@nih.gov,michael.izbicki@nih.gov,andrea.jackson@nih.gov,david.loose@nih.gov
,riverskt@nih.gov,gisele.sarosy@nih.gov,stephanie.whitney@nih.gov
' where exists (
       select * from csm_remote_group where grid_grouper_url like '%cagrid-gridgrouper.nci.nih.gov%'
   );


--insert fake email in other tiers
insert into pa_properties
   select(select max(identifier) + 1 from pa_properties), 'ccct.comparision.email.tolist',
'sample@example.com' where not exists (
       select * from csm_remote_group where grid_grouper_url like '%cagrid-gridgrouper.nci.nih.gov%'
   );


delete from pa_properties where name='ccct.comparision.email.subject';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ccct.comparision.email.subject',
'Results Reporting & Tracking: ${nciId} Trial Comparison Document Review/Update');


delete from pa_properties where name='ccct.comparision.email.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ccct.comparision.email.body',
'<hr>
<table>
<tr>
<td><b>NCIt ID:</b></td>
<td>${nctId}</td>
</tr>
</table>
<hr>
<p>
Date: ${currentDate}
<p>
Hello,
<p>
Attached is the comparison document for   ${nciId} for your review. A Cover Sheet is being sent in a separate email. Please let me know if you have any questions or when your review/updates have been completed.
<p>
<p>
<b>
Thanks,
<br>
Kathryn
</b>
<p>
In the meantime, if you have questions about this or other CTRP topics, please contact us at ncictro@mail.nih.gov. 

');

    



