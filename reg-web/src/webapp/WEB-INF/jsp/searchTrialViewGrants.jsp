<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row form-horizontal details" id="grantsDiv">
<display:table class=" table table-striped table-bordered data sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
    	name="${requestScope.trialFundingList}" requestURI="searchTrialview.action" export="false">
	<display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanismCode"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.nihInstitutionCode" property="nihInstitutionCode"   sortable="true" headerClass="sortable"/>
	<display:column escapeXml="true" titleKey="search.trial.view.serialNumber" property="serialNumber"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode"   sortable="true" headerClass="sortable"/>
</display:table>
</div>