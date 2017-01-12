<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="disclaimer." />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><s:if test="%{!importTerm}">
		<fmt:message key="manageTerms.createDisease.page.title" />
	</s:if><s:else>
		<fmt:message key="manageTerms.importDisease.page.title" />
	</s:else></title>
<s:head />
</head>
<body>
	<h1>
		<s:if test="%{!importTerm}">
			<fmt:message key="manageTerms.createDisease.page.title" />
			<c:set var="topic" scope="request" value="enterdisease" />
		</s:if>
		<s:else>
			<fmt:message key="manageTerms.importDisease.page.title" />
			<c:set var="topic" scope="request" value="importdisease" />
		</s:else>
	</h1>
	
	<jsp:include page="/WEB-INF/jsp/nodecorate/diseaseForm.jsp" />
</body>
</html>