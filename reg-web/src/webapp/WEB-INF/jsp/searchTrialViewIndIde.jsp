<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row form-horizontal details" id="indideDiv">
<display:table class="data table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
    				name="${requestScope.studyIndIde}" requestURI="searchTrialview.action" export="false">
	<display:column titleKey="search.trial.view.indldeTypeCode" property="indIde"   sortable="true" headerClass="sortable"/>
	<display:column escapeXml="true" titleKey="search.trial.view.indldeNumber" property="number"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.grantorCode" property="grantor"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.holderTypeCode" property="holderType"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.nciDivProgHolderCode" property="programCode"   sortable="true" headerClass="sortable"/>

	<display:column titleKey="search.trial.view.expandedAccessIndicator" property="expandedAccess"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.expandedAccessStatusCode" property="expandedAccessType"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.view.exemptIndicator" property="exemptIndicator"   sortable="true" headerClass="sortable"/>
</display:table>
</div>


