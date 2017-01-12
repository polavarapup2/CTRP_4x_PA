<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<html lang="en">
    <head>
        <title><fmt:message key="errorPage.heading"/></title>   
        <s:head/>
        <META HTTP-EQUIV="refresh" CONTENT="10;URL=accrual/protected/viewTrials.action">
        
    </head>
    <body>
        
        <s:token/>
        <div class="error_msg" align="center">
           <strong>Error encountered. Please do not use the browser back or refresh buttons.</strong> 
        </div>
    </body>
</html>