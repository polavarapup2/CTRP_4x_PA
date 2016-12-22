<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
    <c:when test="${fn:length(sessionScope.secondaryIdentifiersList) >0}">
        <display:table class="data table table-striped table-bordered" decorator="" sort="list" size="false" id="row"
                       name="${sessionScope.secondaryIdentifiersList}" requestURI="" export="false">
            <display:column escapeXml="true" titleKey="submit.trial.otherIdentifier" property="extension" sortable="false" headerClass="sortable"/>
            <display:column title="Action" class="action" sortable="false">
                <button type="button" class="btn btn-icon btn-default" onclick="deleteOtherIdentifierRow('${row_rowNum}');"><i class="fa-minus"></i>Delete</button>
            </display:column>
        </display:table>
    </c:when>
    <c:when test="${trialDTO.secondaryIdentifierList != null && fn:length(trialDTO.secondaryIdentifierList) >0}">   
        <display:table class="data table table-striped table-bordered" decorator="" sort="list" size="false" id="row"
                       name="${trialDTO.secondaryIdentifierList}" requestURI="" export="false">
            <display:column escapeXml="true" titleKey="submit.trial.otherIdentifier" property="extension" sortable="false" headerClass="sortable"/>
        </display:table>
    </c:when>
</c:choose>
