DELETE FROM pa_properties where name in ('site.status.change.notification.subject', 'site.status.change.notification.body');

INSERT INTO pa_properties values((select max(identifier) + 1 from pa_properties),
    'site.status.change.notification.subject', 'NCI CTRP: SITE STATUS CHANGED ON TRIAL ${trial.nciIdentifier} AS A RESULT OF A TRIAL STATUS CHANGE');


INSERT INTO pa_properties values((select max(identifier) + 1 from pa_properties),
    'site.status.change.notification.body', '<hr>
<table border="0">
    <tr>
        <td><b>Trial Title:</b></td>
        <td>${trial.officialTitle}</td>
    </tr>
    <tr>
        <td><b>Lead Organization:</b></td>
        <td>${trial.leadOrganizationName}</td>
    </tr>
    <tr>
        <td><b>Previous Trial Status:</b></td>
        <td>${data.previousTrialStatus.statusCode.code}</td>
    </tr>
    <tr>
        <td><b>New Trial Status:</b></td>
        <td>${data.newTrialStatus.statusCode.code}</td>
    </tr>
</table>
<hr>
<p>Date: ${date}</p>
<p>Dear ${recipient.fullName},</p>
<p>The Status on the above trial has been changed and as a result
    the following Open Participating Sites have been closed:</p>

<table border="0">
    <#list data.siteData as site>
    <tr>
        <td align="right">Site Name:</td>
        <td align="left">${site.name}</td>
    </tr>
    <tr>
        <td align="right">Previous Site Status:</td>
        <td align="left">${site.previousTrialStatus.code}</td>
    </tr>

    <tr>
        <td align="right">New Site Status:</td>
        <td align="left">${site.newStatus.code}</td>
    </tr>
    <tr>
        <td align="right">Missing Status(es) or Errors:</td>
        <td align="left">${site.errors}</td>
    </tr>
    <tr>
        <td colspan="2">&nbsp;</td>
    </tr>
    </#list>
</table>

<p>
    <b>NEXT STEPS:</b><br> Please login to the Clinical Trials
    Reporting Program Registry application and provide any missing site
    status information listed above.
</p>
<p>If you have any questions or concerns regarding this change,
    please contact the Clinical Trials Reporting Office (CTRO) staff at
    ncictro@mail.nih.gov.</p>
<p>Thank you for ensuring accurate Trial and Participating Site
    Statuses and Dates in the Clinical Trials Reporting Program.</p>');

