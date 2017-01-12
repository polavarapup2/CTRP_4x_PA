update pa_properties set value=
'The CTRO has identified a need for an additional permissible value to help with the abstraction of biomarkers for Protocol ${trialIdentifier}.
 The concept is ${markerName}.
 Found in HUGO? ${foundInHugo}
 ${hugoCodeClause}
 ${markerTextClause}

 Please let us know if you need any further information to add to this permissible value

 Thanks,
 CTRO' 
WHERE name='CDE_MARKER_REQUEST_BODY';
 
update pa_properties set value=
'Additional Permissible Value Request: ${markerName} for ${trialIdentifier}' 
where name='CDE_MARKER_REQUEST_SUBJECT';