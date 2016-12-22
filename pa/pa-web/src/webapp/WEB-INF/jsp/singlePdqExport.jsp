<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="pagePrefix" value="pdqExport."/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
    </head>
    <body>
        <s:form action="pdqgetSingleExport" method="POST" enctype="multipart/form-data">
            <s:actionerror/>
            <s:text name="Study Protocol ID"></s:text><s:textfield name="studyProtocolId"></s:textfield>
            <s:submit/>
        </s:form> 
    </body>
</html>