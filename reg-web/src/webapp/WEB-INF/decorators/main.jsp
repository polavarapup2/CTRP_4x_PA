<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="author" content="">
        <title><decorator:title default="NCI Registry"/></title>    
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
        <decorator:head/>
        <style type="text/css">
            BODY { width:100%; }
        </style>
        <script>
          function submitXsrfForm(action){
            document.xsrfForm.action=action;
            document.xsrfForm.submit();
          }
          
        </script>
    </head>
    <body>
        <c:choose>
	        <c:when test="${sessionScope.disclaimerAccepted}">
	        	<div id="wrap">
	        </c:when>
	        <c:otherwise>
	        	<div id="wrap" class="login">
	        </c:otherwise>
        </c:choose>
            <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
            <jsp:include page="/WEB-INF/jsp/common/registryheader.jsp"/>
            <c:if test="${sessionScope.disclaimerAccepted}">
                <jsp:include page="/WEB-INF/jsp/common/registrynavbar.jsp"/> 
            </c:if>
            <div class="container">
            	<decorator:body/>
            </div> 
        </div>
        <!-- TODO : move this file to no decoreate folder -->
        <jsp:include page="/WEB-INF/jsp/nodecorate/categoryDefinitions.jsp"/>
        <jsp:include page="/WEB-INF/jsp/nodecorate/contactus.jsp"/>
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        <jsp:include page="/WEB-INF/jsp/common/misc.jsp"/>
        <s:form id="xsrfForm"><s:token/></s:form>
    </body>
</html>
