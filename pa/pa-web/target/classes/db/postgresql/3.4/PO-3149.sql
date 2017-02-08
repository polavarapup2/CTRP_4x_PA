INSERT INTO PA_PROPERTIES (identifier, name, value) values (59, 'CDE_MARKER_REQUEST_FROM_EMAIL', 'ncictro@mail.nih.gov');
INSERT INTO PA_PROPERTIES (identifier, name, value) values (60, 'CDE_MARKER_REQUEST_SUBJECT', 'CDE Marker Request');
INSERT INTO PA_PROPERTIES (identifier, name, value) values (61, 'CDE_MARKER_REQUEST_BODY', 
'The CTRO has identified a need for a new CDE to help with the marker abstraction for Protocol ${trialIdentifier}.
The concept is ${markerName}.
Found in HUGO? ${foundInHugo}
${hugoCodeClause}
${markerTextClause}
Please let us know if you need any further information to build the new CDE.

Thanks,
CTRO');
INSERT INTO PA_PROPERTIES (identifier, name, value) values (62, 'CDE_MARKER_REQUEST_HUGO_CLAUSE', 'HUGO Marker Code: ${hugoCode}');
INSERT INTO PA_PROPERTIES (identifier, name, value) values (63, 'CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE', 
'Here is the text of the marker as written in the protocol:
${markerText}');