<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="contact.page.title"/></title>
    <s:head />
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="contact.page.header" /></h1>
 <c:set var="topic" scope="request" value=""/> 
    <!--Box-->

    <div class="box" id="filters">
        <h5>CTRP Application Support</h5>
        <p>If you are experiencing technical issues with the CTRP web application, please contact us at <a href="mailto:ctrp_support@nih.gov">ctrp_support@nih.gov</a>.</p>
        <p>When submitting a support request, please include: </p>
            <li>Your contact information, including your telephone number</li>
            <li>The name of the application/tool you are using</li>
            <li>The URL if it is a Web-based application</li>
            <li>A description of the problem and steps to recreate it</li>
            <li>The text of any error messages you have received</li>
        <h5>Contacting the Clinical Trials Reporting Office</h5>
        <p>For other CTRP topics, contact the Clinical Trials Reporting Office (CTRO) at <a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov</a>.</p>
    </div>

    <!--/Box-->
</body>
</html>