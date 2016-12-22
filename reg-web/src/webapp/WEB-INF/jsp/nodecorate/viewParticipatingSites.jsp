<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<div class="container">
	<s:token />
	<s:set name="organizationList" value="organizationList" scope="request" /> 
	<display:table name="organizationList" id="row" class="table table-striped table-bordered sortable" pagesize="20" requestURI="participatingSitespopup.action">
		<display:column escapeXml="true" property="nciNumber" titleKey="participatingOrganizations.nciNumber" class="sortable" />
		<display:column escapeXml="true" property="name" titleKey="participatingOrganizations.name" class="sortable" />
   		<display:column property="investigator" titleKey="participatingOrganizations.investigators"/>
	</display:table>
</div>
<div class="align-center button-row">
   <button type="button" class="btn btn-icon btn-primary" onclick="window.top.hidePopWin();"><i class="fa-floppy-o"></i>Close</button>
</div>