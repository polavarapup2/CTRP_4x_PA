<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:set name="orgContactList" value="orgContactList" scope="request"/>
<display:table class="data" summary="This table contains Person search results. Please use column headers to sort results"
               sort="list" pagesize="10" uid="row"  name="orgContactList" export="false" requestURI="popupdisplayTitlesListDisplayTag.action">
    <display:column title="PO-ID" property="identifier.extension"  sortable="true"  headerClass="sortable"  headerScope="col"/>
    <display:column escapeXml="true" title="Title" property="title"  sortable="true"  headerClass="sortable"  headerScope="col"/>
    <display:column title="Email">
         <s:select name="emails" id="%{#attr.row.identifier.extension}email"  list="%{#attr.row.emails}"/>
    </display:column>
    <display:column title="Phone">
         <s:select name="phones" id="%{#attr.row.identifier.extension}phone"  list="%{#attr.row.phones}"/>
    </display:column>
    <display:column title="Action" class="action" sortable="false"  headerScope="col">
        <a href="javascript:void(0)" class="btn" onclick="submitform('${row.identifier.extension}','${func:escapeJavaScript(row.title)}')">
            <span class="btn_img"><span class="confirm">Select</span></span>
        </a>
    </display:column>
</display:table>
