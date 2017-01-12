<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title><decorator:title default="Protocol Abstraction (PA)"/></title>
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <script type="text/javascript">
            Help.url = '<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getPaHelpUrl()" />';
            var contextPath = '<%=request.getContextPath()%>';
            function mainOnLoadHandler() {
                if (window.callOnloadFunctions) {
                    callOnloadFunctions();
                }
            }
            function submitXsrfForm(action){
                document.xsrfForm.action=action;
                document.xsrfForm.submit();
            }
        </script>
        <fmt:setBundle basename="AuditTrailResources" var="auditTrailResources" scope="session"/>
        <!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
        <decorator:head/>
    </head>
    <body onload="mainOnLoadHandler();">
        <div id="wrapper" class="curate">
            <div id="main" style="float:none !important;">
                <div id="contentwrapper">
                    <div id="content" style="margin-left: 10px !important;">                       
                        <decorator:body/>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="clear"><br /></div>            
            <jsp:include page="/WEB-INF/jsp/common/misc.jsp"/>
        </div>
        <s:form id="xsrfForm"></s:form>
    </body>
</html>