delete from pa_properties where name='participating.site.not.closed.email.subject';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'participating.site.not.closed.email.subject',
'CTRP was unable to close some participating sites for trial ${nciId}');


delete from pa_properties where name='participating.site.not.closed.email.body';

INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'participating.site.not.closed.email.body',
'<br>
 Dear CTRO Staff,
<br>
<br>
<table width="100%" border="1">
<tr>
    <td style="font-weight:bold" width="20%" valign="top">Date:</td>
    <td width="80%" valign="top">${currentDateTime}</td>
</tr>
<tr>
    <td style="font-weight:bold" width="20%" valign="top">Trial Identification block</td>
    <td width="80%" valign="top">
    <table width="100%" border="1">
        <tr>
            <td style="font-weight:bold" width="25%" valign="top">Title:</td>
            <td width="75%" valign="top">${title}</td>
        </tr>
        <tr>
            <td style="font-weight:bold" width="25%" valign="top">NCI ID:</td>
            <td width="75%" valign="top">${nciId}</td>
        </tr>
        <tr>
            <td style="font-weight:bold" width="25%">NCT ID:</td>
            <td width="75%" valign="top">${nctId}</td>
        </tr>
        <tr>
            <td style="font-weight:bold" width="25%">DCP ID:</td>
            <td width="75%" valign="top">${dcpId}</td>
        </tr>
        <tr>
            <td style="font-weight:bold" width="25%">CTEP ID:</td>
            <td width="75%" valign="top">${ctepId}</td>
        </tr>
    </table>
    </td>
</tr>
<tr>
<td style="font-weight:bold" width="20%" valign="top">Current Status:</td>
<td width="80%" valign="top">${currentStatus}</td>
</tr>
<tr>
<td style="font-weight:bold" width="20%" valign="top">Current Status Date:</td>
<td width="80%" valign="top">${currentStatusDate}</td>
</tr>
</table>
<br>
<br>
When this trial was closed, the CTRP application was unable to automatically add a closure status to the following participating site(s) due to inconsistent data found when evaluating the trial''s closure status and the participating site status history. This occurs if any of the following conditions are met:
<br>
<ul>
<li>
The participating site already has a closure status, but it is not its current status
</li>
<li>
The trial''s closure status date is before the site''s current status date
</li>
</ul>
<br>
<br>
Unclosed Sites
<br>
<br>
${unclosedSites}
<br>
Thank you
<br>
This is a system-generated message. Please do not reply.
');