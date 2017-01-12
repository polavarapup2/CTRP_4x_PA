<%@ tag display-name="actionErrorsAndMessages"
	description="Struts action errors and messages"
	body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="hasActionMessages()">
	<div class="alert alert-info">
		<s:actionmessage />
	</div>
</s:if>
<s:if test="hasActionErrors()">
	<div class="alert alert-danger">
		<s:actionerror />
	</div>
</s:if>
<s:else>
	<s:actionerror />
</s:else>
