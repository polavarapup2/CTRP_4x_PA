UPDATE PA_PROPERTIES SET VALUE = 'The Clinical Trials Reporting Office (CTRO) has identified a need for a new permissible value to help with the marker abstraction for Protocol ${trialIdentifier}.
The concept is ${markerName}.
Found in HUGO? ${foundInHugo}
${hugoCodeClause}
Submitter: ${submitterName}

${markerTextClause}

If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.

Thank you,
The CTRO Staff' WHERE NAME = 'CDE_MARKER_REQUEST_BODY';