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
            <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
            <jsp:include page="/WEB-INF/jsp/common/paheader.jsp"/>
            <div id="main">
                <div id="contentwrapper">
                    <div id="content">
                       <a href="javascript:void(0)" id="pageHelpid" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
                        <decorator:body/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div id="leftnav">
                    <ul class="menu">
                        <c:if test="${sessionScope.disclaimerAccepted}">
                            <jsp:include page="/WEB-INF/jsp/common/pamenu.jsp"/>
                        </c:if>
                        <jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/>
                    </ul>
                </div>
            </div>
            <div class="clear"><br /></div>
            <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
            <jsp:include page="/WEB-INF/jsp/common/misc.jsp"/>
        </div>
        <s:form id="xsrfForm"><s:token/></s:form>
    </body>
</html>
