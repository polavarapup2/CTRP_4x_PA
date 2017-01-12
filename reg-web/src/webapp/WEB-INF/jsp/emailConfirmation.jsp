<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="register.user.page.title"/></title>
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
</SCRIPT>

<body>
<!-- main content begins-->
    <c:set var="topic" scope="request" value="register"/>
    <h2><fmt:message key="register.user.email.confirm"/></h2>
    <div class="box" id="filters">
        <p>An e-mail has been sent to the e-mail address, <strong><c:out value="${requestScope.emailAddress}"/></strong>.
           Please click on the link provided in the e-mail to activate your account.</p>

    </div>
</body>
</html>
