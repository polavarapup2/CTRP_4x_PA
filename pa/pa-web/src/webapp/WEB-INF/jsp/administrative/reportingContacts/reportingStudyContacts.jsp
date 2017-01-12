 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="reportStudyContacts.title"/></title>
    
    <link href="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.css"
    rel="stylesheet" media="all" type="text/css" />
    <!-- DataTables CSS -->
   <link rel="stylesheet" type="text/css"
    href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">  
    
<!-- DataTables -->
<script type="text/javascript" charset="utf8"
    src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
    
<script type="text/javascript" language="javascript" src="${scriptPath}/js/ajaxHelper.js"></script>

</head>

<body>

 <h1><fmt:message key="reportStudyContacts.title"/></h1>
 <c:set var="topic" scope="request" value="resultscontact"/>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div id="studycontacts">
    <jsp:include page="reportingStudyContactsContent.jsp"/>
 </div>
 </body>
 
</html>