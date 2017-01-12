<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="amend.trial.page.title"/></title>
    <s:head/>
</head>
<body>
    <c:set var="topic" scope="request" value="amendtrial"/>
    <h2>Trial Cannot be Amended</h2>
    <div class="box" id="filters">
        <s:if test="hasActionErrors()">
            <div class="alert alert-danger"><s:actionerror/></div>
        </s:if>
    </div>
</body>
</html>
