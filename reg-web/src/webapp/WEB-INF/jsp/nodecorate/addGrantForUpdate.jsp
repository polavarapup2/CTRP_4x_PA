<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(grants) > 0}">
<display:table class="table table-striped table-bordered sortable" sort="list"  uid="row"  name="grants" requestURI="submitTrialdisplayIndIde.action">
	<display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable"/>
	<display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable"/>
	<display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable"/>
	<display:column title="Action" class="action" sortable="false">
		<button type="button" class="btn btn-icon btn-default" onclick="deleteGrantRow('${row.rowId}');"><i class="fa-minus"></i>Delete</button></td>
	</display:column>
</display:table>
</c:when>
<c:when test="${sessionScope.grantAddList != null}">
<display:table class="table table-striped table-bordered sortable" sort="list"  uid="row"  name="${sessionScope.grantAddList}" requestURI="submitTrialdisplayIndIde.action">
	<display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable"/>
	<display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable"/>
	<display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable"/>
	<display:column title="Action" class="action" sortable="false">
		<button type="button" class="btn btn-icon btn-default" onclick="deleteGrantRow('${row.rowId}');"><i class="fa-minus"></i>Delete</button></td>
	</display:column>
</display:table>
</c:when>
</c:choose>
