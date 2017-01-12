<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>My Account</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<s:head/>
</head>
<body>
<!-- main content begins-->
    <h2><fmt:message key="batch.upload.confirm"/></h2>
    <div class="box" id="filters">
      <p>The batch trial data you submitted is being processed. You will receive an e-mail notification after the processing is complete. </p>
      Please <a href="${pageContext.request.contextPath}/admin/batchUpload.action">click here</a> to submit another file. 
 	</div>
</body>
</html>