<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="paOrgs != null">
    <s:set name="paOrgs" value="paOrgs" scope="request"/>
    <display:table class="data table table-striped table-bordered" summary="This table contains Organization search results. Please use column headers to sort results"  
                   sort="list" pagesize="10" uid="row" name="paOrgs" export="false" requestURI="popupdisplayOrgListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list" value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column title="PO-ID" property="id"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="false"/>
        <display:column escapeXml="true" title="Organization Name" property="name"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="false" title="Family Name" sortable="false">
            <c:forEach items="${row.families}" var="family">
                <c:out value="${family.value}"/><br/>
            </c:forEach>
        </display:column>
        <display:column escapeXml="true" title="City" property="city"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="State" property="state"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Country" property="country"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Zip" property="zip"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column title="Action" class="action" sortable="false">
         <c:choose>
          <c:when test="${fn:contains(row.name, '\\'')}">
              <button type="button" class="btn btn-icon btn-primary" onclick="submitform('${row.id}','${func:escapeJavaScript(row.name)}','${func:escapeJavaScript(row.p30GrantSerialNumber)}')"> <i class="fa-check"></i>Select</button>
           </c:when>
            <c:otherwise>
           <button type="button" class="btn btn-icon btn-primary" onclick='submitform("${row.id}","${func:escapeJavaScript(row.name)}","${func:escapeJavaScript(row.p30GrantSerialNumber)}")'> <i class="fa-check"></i>Select</button> </c:otherwise>
        </c:choose>
           
        </display:column>
    </display:table>
</s:if>
