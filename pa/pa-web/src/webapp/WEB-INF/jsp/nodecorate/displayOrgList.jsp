<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<pa:failureMessage />
<s:if test="orgs != null">

    <s:set name="orgs" value="orgs" scope="request" />
    <display:table class="data" sort="list" pagesize="10" uid="row" name="orgs" export="false"
        requestURI="popupOrgdisplayOrgListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list"
            value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column escapeXml="true" title="PO-ID" property="id" headerClass="sortable" />
        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="true"/>
        <display:column title="Organization Name" headerClass="sortable">
            <c:out value="${row.name}"/>
        </display:column>
        <display:column escapeXml="false" title="Family Name" sortable="false">
            <c:forEach items="${row.families}" var="family">
                <c:out value="${family.value}" />
                <br />
            </c:forEach>
        </display:column>
        <display:column escapeXml="true" title="City" property="city" headerClass="sortable" />
        <display:column escapeXml="true" title="State" property="state" headerClass="sortable" />
        <display:column escapeXml="true" title="Country" property="country" headerClass="sortable" />
        <display:column escapeXml="true" title="Zip" property="zip" headerClass="sortable" />
        
         <c:set var="orgName" value="${row.name}"/>
        
        <display:column title="Action" class="action" sortable="false">
        
        <c:choose>
          <c:when test="${fn:contains(orgName, '\\'')}">
              <a href="javascript:void(0)" class="btn" onclick="submitform('${row.id}','${func:escapeJavaScript(row.name)}')"> <span class="btn_img"><span
                class="add">Select</span></span></a>
           </c:when>
            <c:otherwise>
           <a href="javascript:void(0)" class="btn" onclick='submitform("${row.id}","${func:escapeJavaScript(row.name)}")'> <span class="btn_img"><span
                class="add">Select</span></span></a>
            </c:otherwise>
        </c:choose>
           
        </display:column>
        
    </display:table>

</s:if>