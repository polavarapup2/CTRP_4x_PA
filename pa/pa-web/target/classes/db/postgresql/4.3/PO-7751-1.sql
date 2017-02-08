INSERT INTO pa_properties values((select max(identifier) + 1 from pa_properties),
    'trial.identifiers.header','<table border="0">
<tr><td><b>NCI Trial ID:</b></td><td>${spDTO.nciIdentifier}</td></tr>
<tr><td><b>Lead Organization Trial ID:</b></td><td>${spDTO.localStudyProtocolIdentifier}</td></tr>
<tr><td><b>Lead Organization:</b></td><td>${spDTO.leadOrganizationName}</td></tr>
<tr><td><b>CTRP-assigned Lead Organization ID:</b></td><td>${spDTO.leadOrganizationPOId?c}</td></tr>
<#if spDTO.ctepId?has_content>
<tr><td><b>CTEP ID:</b></td><td>${spDTO.ctepId}</td></tr>
</#if>
<#if spDTO.dcpId?has_content>
<tr><td><b>DCP ID:</b></td><td>${spDTO.dcpId}</td></tr>
</#if>
<#if spDTO.nctNumber?has_content>
<tr><td><b>ClinicalTrials.gov ID:</b></td><td>${spDTO.nctNumber}</td></tr>
</#if>
</table>');