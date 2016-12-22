<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="disWebList != null">
    <s:set name="disWebList" value="disWebList" scope="request"/>
    <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row"
        name="disWebList" export="false">
        <display:column escapeXml="true" title="Parent Name" property="parentPreferredName"  headerClass="sortable"/>
        <display:column escapeXml="true" title="Name" property="preferredName"  headerClass="sortable"/>
        <display:column escapeXml="true" title="Code" property="code"  headerClass="sortable"/>
        <display:column escapeXml="true" title="NCI Thesaurus Concept ID" property="conceptId"  headerClass="sortable"/>
        <display:column escapeXml="true" title="Menu Display Name" property="menuDisplayName"  headerClass="sortable"/>
        <display:column title="Select" headerClass="centered" sortable="false">
            <c:choose>
            <c:when test="${!row.selected && row.menuDisplayName != null && row.menuDisplayName != ''}">
                <a href="javascript:void(0)" class="btn" onclick="addDisease('${row.diseaseIdentifier}')">
                    <span class="btn_img"><span class="add">Add</span></span>
                </a>
            </c:when>
            <c:when test="${row.selected}">
                <a href="javascript:void(0)" class="btn" onclick="removeDisease('${row.studyDiseaseIdentifier}')">
                    <span class="btn_img"><span class="add">Remove</span></span>
                </a>
            </c:when>
            <c:otherwise>
               not suitable for reporting
            </c:otherwise>
            </c:choose>
        </display:column>
        <display:column title="Include in Xml" headerClass="centered" sortable="false">
            <c:if test="${row.selected}">
                <c:out value="${row.ctGovXmlIndicator}"/>
            </c:if>
        </display:column>
    </display:table>
</s:if>