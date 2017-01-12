<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="inboxTrial.title"/></title>
    <s:head/>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions() {
    setFocusToFirstControl();
}
</SCRIPT>
<body>
    <!-- main content begins-->
    <c:set var="topic" scope="request" value="inboxaccess"/>
    <jsp:include page="/WEB-INF/jsp/pendingAdminUserResults.jsp"/>
</body>
</html>
