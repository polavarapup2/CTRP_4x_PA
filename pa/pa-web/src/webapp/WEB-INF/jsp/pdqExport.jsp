<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="pagePrefix" value="pdqExport."/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
    </head>
    <body>
        <s:form name="pdq">
            <s:if test="hasActionErrors()">
                <div class="error_msg">
                    <s:actionerror/>
                </div>
            </s:if>
            <s:actionmessage/>
            <display:table class="data" sort="list" pagesize="200" id="row" name="${requestScope.listOfFileNames}" export="false" requestURI="pdqgetAvailableFiles.action">
                <display:column class="title" title="File Name" sortable="true" headerScope="col" scope="row">
                    <a href="pdqgetFileByDate.action?date=${row}">${row}</a>
                </display:column>
            </display:table>
        </s:form> 
    </body>
</html>