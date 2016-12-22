<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>

<s:if test="paOrgs != null">
    <s:set name="paOrgs" value="paOrgs" scope="request"/>
    <display:table class="table table-striped table-bordered sortable" summary="This table contains Organization search results. Please use column headers to sort results"  
                   sort="list" pagesize="10" uid="row" name="paOrgs" export="false" requestURI="orgPopdisplayOrgListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list" value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column title="PO-ID" property="id"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="true"/>
        <display:column escapeXml="true" title="Organization Name" property="name"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="City" property="city"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="State" property="state"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Country" property="country"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Zip" property="zip"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column title="Action" class="action" sortable="false">
        	<button type="button" class="btn btn-icon btn-primary" onclick="submitform('${row.id}','${func:escapeJavaScript(row.name)}')"> <i class="fa-check"></i>Select</button>
        </display:column>
    </display:table>
</s:if>