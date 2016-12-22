<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="regIdAuthOrgList != null">
<s:select list="regIdAuthOrgList" name="selectedAuthOrg" listKey="id" listValue="name"></s:select>
</s:if>