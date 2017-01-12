<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value=""/> 
<head>
    <title><fmt:message key="contact.page.title"/></title>
    <s:head />
</head>


<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
 <h1><fmt:message key="contact.page.header" /></h1>
    <!--Box-->

    <div class="box" id="filters">
          
            <p class="intro">Thank you for using the NCI&#39;s Clinical Trials Reporting Program. </p>
          
            <p>
               If you are experiencing problems with the application, you can email NCI CBIIT Applications Support at 
               <a href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a> or call <strong>240-276-5541</strong> or toll free: <strong>888-478-4423</strong>.  
            </p>
            <p>
                Alternatively, you can submit a request for help at the following URL: 
            </p>
            <p>
                <a href="http://cbiit.nci.nih.gov/support" target="_blank"><font size="3" ><u>http://cbiit.nci.nih.gov/support</u></font></a>
            </p>

            <p>NCI CBIIT Application Support is available Mon-Fri 8am-8pm EST, excluding Federal Government Holidays. </p>

    </div>

    <!--/Box-->
</body>
</html>