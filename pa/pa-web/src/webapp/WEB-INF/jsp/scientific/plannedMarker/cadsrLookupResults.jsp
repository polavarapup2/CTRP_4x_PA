<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<pa:failureMessage />
<s:if test="markers != null">
    <s:set name="markers" value="markers" scope="request" />
    <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" name="markers" export="false">
        <display:column escapeXml="false" titleKey="plannedMarker.lookup.permissibleValue" property="vmName" headerClass="sortable" />
        <display:column escapeXml="false" titleKey="plannedMarker.lookup.meaning" property="vmMeaning" headerClass="sortable" />
        <display:column escapeXml="false" titleKey="plannedMarker.lookup.synonym">
             <c:forEach var="item" items="${row.altNames}">
                  <c:out escapeXml="false" value="${item}"/>
             <br>
             </c:forEach>
        </display:column>
        <display:column escapeXml="false" titleKey="plannedMarker.lookup.description" property="vmDescription" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.publicId" property="publicId" headerClass="sortable" />
        
        <s:if test="showActionColumn == null">
        <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <a href="javascript:void(0)" class="btn" onclick="loadTopDiv('${row.id}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
        </display:column>
        </s:if>
    </display:table>
</s:if>