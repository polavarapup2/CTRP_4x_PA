<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="persons != null">
    <s:set name="persons" value="persons" scope="request"/>
    <display:table class="data table table-striped table-bordered" summary="This table contains Person search results. Please use column headers to sort results"
                   sort="list" pagesize="10" uid="row"  name="persons" export="false" requestURI="popupdisplayPersonsListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list" value="No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column title="PO-ID" property="id"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="false"/>
        <display:column escapeXml="true" title="First Name" property="firstName"  sortable="false"  headerClass="sortable"  headerScope="col" maxLength="15"/>
        <display:column escapeXml="true" title="Middle Name" property="middleName"  sortable="false"  headerClass="sortable"  headerScope="col" maxLength="10"/>
        <display:column escapeXml="true" title="Last Name" property="lastName"  sortable="false"  headerClass="sortable"  headerScope="col" maxLength="15"/>
        <display:column escapeXml="true" title="Email" property="email"  sortable="false"  headerClass="sortable"  headerScope="col" maxLength="20"/>
        <display:column escapeXml="true" title="Address" property="address"  sortable="false"  headerClass="sortable"  headerScope="col"/>
        <display:column title="Action" class="action" sortable="false"  headerScope="col">
            <button type="button" class="btn btn-icon btn-primary" onclick="submitform('${row.id}','${func:escapeJavaScript(row.lastName)}' + ',' + '${func:escapeJavaScript(row.firstName)}')"> <i class="fa-check"></i>Select</button>
        </display:column>
    </display:table>
    <script type="text/javascript" language="javascript">
    
	    if (Prototype.Browser.IE) {    	
	    	showProgressIndicatorForPaginationLinks();
	        Event.observe(document, "dom:loaded", showProgressIndicatorForPaginationLinks);
	    }
    
    </script> 
</s:if>
