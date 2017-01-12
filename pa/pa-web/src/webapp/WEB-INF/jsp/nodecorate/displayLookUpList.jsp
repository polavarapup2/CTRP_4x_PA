<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="lookupDtos != null">

<s:set name="lookupDtos" value="lookupDtos" scope="request"/>
<display:table class="data" sort="list" pagesize="10" uid="row"
	name="lookupDtos" export="false" requestURI="popupTypeInterventiondisplayLookUpListDisplayTag.action">
	<display:column escapeXml="true" title="Code" property="code"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Display Name" property="displayName"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Public Id" property="publicId"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Description" property="description"  headerClass="sortable"/>
	<display:column title="Action" class="action" sortable="false">
		<a href="javascript:void(0)" class="btn" onclick="submitLookUp('${row.id}','${row.divName}','${row.type}')">
		<span class="btn_img"><span class="add">Select</span></span></a>
	</display:column>
</display:table>

</s:if>