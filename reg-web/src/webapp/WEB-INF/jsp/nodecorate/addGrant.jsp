<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(grants) > 0}">
<display:table class="table table-striped table-bordered sortable" sort="list"  uid="row"  name="grants" requestURI="submitTrialdisplayIndIde.action">
	<display:column title="Funding Mechanism Type" property="fundingMechanism"  headerClass="sortable"/>
	<display:column title="Institute Code" property="instituteCode"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable"/>
	<display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable"/>
	<display:column title="Action" class="action" sortable="false">
			<input type="button" value="Delete" onclick="deleteGrantRow('${row.rowId}')"/>
	</display:column>
</display:table>
</c:when>
<c:when test="${sessionScope.grantList != null}">
<display:table class="table table-striped table-bordered sortable" sort="list"  uid="row"  name="${sessionScope.grantList}" requestURI="submitTrialdisplayIndIde.action">
	<display:column title="Funding Mechanism Type" property="fundingMechanism"  headerClass="sortable"/>
	<display:column title="Institute Code" property="instituteCode"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable"/>
	<display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable"/>
	<display:column title="Action" class="action" sortable="false">
			<input type="button" value="Delete" onclick="deleteGrantRow('${row.rowId}')"/>
	</display:column>
</display:table>
</c:when>
</c:choose>
