<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:set name="rolecode" value="@gov.nih.nci.pa.enums.StudySiteContactRoleCode@getRoleCodes()" />
<s:if test="persons != null">
<s:set name="persons" value="persons" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"
               name="persons" export="false" requestURI="/pa/protected/popupdisplayPersonsListDisplayTag.action">
    <display:setProperty name="basic.msg.empty_list" value="No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
    <display:column escapeXml="true" title="PO-ID" property="id"  headerClass="sortable"/>
    <display:column escapeXml="true" title="CTEP-ID" property="ctepId"  headerClass="sortable"/>
    <display:column escapeXml="true" title="First Name" property="firstName"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Middle Name" property="middleName"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Last Name" property="lastName"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Address" property="address"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Email" property="email"/>
    <display:column title="Role Code">
             <s:select id="%{#attr.row.id}"  list="#rolecode"/>
    </display:column>
    <display:column title="Action" class="action" sortable="false">
        <a href="javascript:void(0)" class="btn" onclick="callCreatePerson('${row.id}',
            document.getElementById('${row.id}').value,'${func:escapeJavaScript(row.firstName)}','${func:escapeJavaScript(row.lastName)}', 
            '${func:escapeJavaScript(row.email)}', '${func:escapeJavaScript(row.phone)}',
            '${func:escapeJavaScript(row.country)}')">
            <span class="btn_img"><span class="add">Select</span></span>
        </a>
    </display:column>
</display:table>
</s:if>



