


delete from pa_properties where name='ctro.comparision.email.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ctro.comparision.email.body',
'<hr>
<table>
<tr>
<td><b>NCT ID:</b></td>
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

 
  





delete from pa_properties where name='ccct.comparision.email.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'ccct.comparision.email.body',
'<hr>
<table>
<tr>
<td><b>NCT ID:</b></td>
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

    



