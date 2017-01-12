update pa_properties set value =
'<hr>
<table border="0">
    <tr>
        <td><b>NCI Trial ID:</b></td>
        <td>${nciTrialIdentifier}</td>
    </tr>
    <tr>
        <td><b>Batch File Name:</b></td>
        <td>${fileName}</td>
    </tr>
    <tr>
        <td><b>Submission Date:</b></td>
        <td>${submissionDate}</td>
    </tr>
</table>
<hr>

<p>Date: ${CurrentDate}</p>
<p>Dear ${SubmitterName},</p>
<p>Thank you for submitting your accruals to the NCI Clinical Trials
    Reporting Program (CTRP).</p>
<p>The CTRP processed your file successfully.</p>
<p>
    <b>Number of Subjects Registered: </b> ${count}
</p>
<p>${errorsDesc}</p>
<ul>${errors}</ul>

${preprocessingErrors}

<p>If you have questions about this or other CTRP topics, please
    contact us at ncictro@mail.nih.gov.</p>
<p>Thank you for submitting your trial for registration in the
    Clinical Trials Reporting Program.</p>'
where name = 'accrual.confirmation.body';
