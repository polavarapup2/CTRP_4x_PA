DELETE FROM pa_properties where name='studysite.statuschange.email.subject';
DELETE FROM pa_properties where name='studysite.statuschange.email.body';
/*
 * 1 - <participating_site_org_name>
 * 2 - <nci_id>
 */
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'studysite.statuschange.email.subject',
'Participating Site Status was changed for %1s, on trial: %2s');
/*
 * 1 - <trial_title>
 * 2 - <nci_id>
 * 3 - <lead_org>
 * 4 - <participating_site_org_name>
 * 5 - <current_date>
 * 
 * 6 - <first_name>
 * 7 - <last_name>
 * 8 - <current_date>
 * 
 * 9 - <participating_site>
 * 10 - <old_site_status>
 * 11 - <new_site_status>
 */
INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'studysite.statuschange.email.body',
'<hr>
<table border="0">
    <tr>
        <td><b>Title:</b></td>
        <td>%1s</td>
    </tr>
    <tr>
        <td><b>NCI Trial ID:</b></td>
        <td>%2s</td>
    </tr>
    <tr>
        <td><b>Lead Organization:</b></td>
        <td>%3s</td>
    </tr>
    <tr>
        <td><b>Participating Site:</b></td>
        <td>%4s</td>
    </tr>

</table>
<hr>
<p>Date: %5s</p>
<p>Dear %6s %7s,</p>
<p>The ClinicalTrials.gov record shows that the trial listed above
    was closed as of %8s. For consistency, the CTRP system has changed the
    trials status at your participating site:</p>
<table border="0">
    <tr>
        <td><b>Participating Site:</b></td>
        <td>%9s</td>
    </tr>
    <tr>
        <td><b>Old Site Status:</b></td>
        <td>%10s</td>
    </tr>
    <tr>
        <td><b>New Site Status:</b></td>
        <td>%11s</td>
    </tr>
</table>


<p>If you believe this is an error, or if you have additional
    questions about this or other CTRP topics, please contact the CTRO at
    ncictro@mail.nih.gov.</p>
<p>Thank you for participating in the NCI Clinical Trials Reporting
    Program.</p>
');