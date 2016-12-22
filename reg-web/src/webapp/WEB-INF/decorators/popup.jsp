<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><decorator:title default="Registration"/></title>     
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
        <link href="${pageContext.request.contextPath}/styles/displayStyles.css" rel="stylesheet" type="text/css" media="all"/>
        <link href="${pageContext.request.contextPath}/struts/niftycorners/niftyCorners.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/struts/niftycorners/niftyPrint.css" rel="stylesheet" type="text/css" media="print"/>
        <script type="text/javascript">
           jQuery(function(){
        	   $.timeoutDialog();
                if(!NiftyCheck()) {
                    return;
                }
                // perform niftycorners rounding
                // eg.
                // Rounded("blockquote","tr bl","#ECF1F9","#CDFFAA","smooth border #88D84F");
            });
        </script>
        <decorator:head/>
    </head>
    <body> 
       <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
       <decorator:body/>
    </body>   
</html>
