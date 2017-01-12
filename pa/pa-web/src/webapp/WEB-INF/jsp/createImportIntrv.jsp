<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="disclaimer." />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><s:if test="%{!importTerm}">
		<fmt:message key="manageTerms.createIntrv.page.title" />
	</s:if><s:else>
		<fmt:message key="manageTerms.importIntrv.page.title" />
	</s:else></title>
</head>
<body>
	<h1>
		<s:if test="%{!importTerm}">
			<fmt:message key="manageTerms.createIntrv.page.title" />
            <c:set var="topic" scope="request" value="enterintervention" />
		</s:if>
		<s:else>
			<fmt:message key="manageTerms.importIntrv.page.title" />
            <c:set var="topic" scope="request" value="importintervention" />
		</s:else>
	</h1>
	<jsp:include page="/WEB-INF/jsp/nodecorate/interventionForm.jsp" />
</body>
</html>