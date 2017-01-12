<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(ideInd) > 0}">
	<display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="ideInd" requestURI="submitTrialdisplayIndIde.action">
		<display:column title="IND/IDE Type" property="indIde"  headerClass="sortable"/>
		<display:column title="Number" property="number"  headerClass="sortable"/>
		<display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable"/>
		<display:column title="Holder" property="holderType"  headerClass="sortable"/>
		<display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable"/>
		<display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable"/>
		<display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable"/>
		<display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable"/>
		<display:column title="Action" class="action" sortable="false">
			<c:choose>
            <c:when test="${row.indIdeId != null}">
            </c:when>
            <c:otherwise>
             <input type="button" value="Delete" onclick="deleteIndIde('${row.rowId}')"/>
            </c:otherwise>
            </c:choose>
		</display:column>
	</display:table>
</c:when>
<c:when test="${sessionScope.indIdeList != null}">
    <c:if test="${fn:length(sessionScope.indIdeList) > 0}">
	<display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${sessionScope.indIdeList}" requestURI="submitTrialdisplayIndIde.action">
		<display:column title="IND/IDE Type" property="indIde"  headerClass="sortable"/>
		<display:column title="Number" property="number"  headerClass="sortable"/>
		<display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable"/>
		<display:column title="Holder" property="holderType"  headerClass="sortable"/>
		<display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable"/>
		<display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable"/>
		<display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable"/>
		<display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable"/>
		<display:column title="Action" class="action" sortable="false">
	        <c:choose>
	        <c:when test="${row.indIdeId != null}">
			</c:when>
			<c:otherwise>
			 <input type="button" value="Delete" onclick="deleteIndIde('${row.rowId}')"/>
			</c:otherwise>
			</c:choose>
        </display:column>

	</display:table>
	</c:if>
</c:when>
<c:when test="${trialDTO.indIdeDtos != null && fn:length(trialDTO.indIdeDtos) >0}">
    <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.indIdeDtos}" requestURI="submitTrialdisplayIndIde.action">
        <display:column title="IND/IDE Type" property="indIde"  headerClass="sortable"/>
        <display:column title="Number" property="number"  headerClass="sortable"/>
        <display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable"/>
        <display:column title="Holder" property="holderType"  headerClass="sortable"/>
        <display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable"/>
        <display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable"/>
        <display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable"/>
        <display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable"/>
    </display:table>
</c:when>

</c:choose>

