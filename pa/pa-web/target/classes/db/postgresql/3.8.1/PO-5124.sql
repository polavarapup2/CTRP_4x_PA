-- CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE
update pa_properties
set value = 'Here is the marker text as written in the protocol:
${markerText}'
where name = 'CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE';


-- CDE_MARKER_REQUEST_BODY
update pa_properties
set value = 'The Clinical Trials Reporting Office (CTRO) has identified a need for a new permissible value to help with the marker abstraction for Protocol ${trialIdentifier}.
The concept is ${markerName}.
Found in HUGO? ${foundInHugo}
HUGO Marker Code:  ${hugoCodeClause}

${markerTextClause}

If you need any further information to create the new permissible value, contact the CTRO at ncictro@mail.nih.gov.

Thank you,
The CTRO Staff'
where name = 'CDE_MARKER_REQUEST_BODY';
