 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialDocument.title"/></title>
	<s:head />
	<!-- DataTables -->
	<script type="text/javascript" charset="utf8"
	 src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
	<!-- DataTables CSS -->
	<link rel="stylesheet" type="text/css"
	    href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">
</head>
 <body>
 <h1><fmt:message key="actionsTaken.title" /></h1>
    <c:set var="topic" scope="request" value="resultsxml"/>
    <div class="box">
	    <s:if test="studyProtocolId != ''">
	        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
	    </s:if>
	    <jsp:include page="actionsTakenRecords.jsp"/>
	    <jsp:include page="editActionsTaken.jsp"/>
   </div>
  </body>
 </html>
