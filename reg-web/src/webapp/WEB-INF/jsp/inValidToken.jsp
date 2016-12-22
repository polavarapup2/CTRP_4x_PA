<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="errorPage.heading"/></title>   
        <s:head/>
        <META HTTP-EQUIV="refresh" CONTENT="10;URL=registry/protected/searchTrial.action">
        
    </head>
    <body>
        
        <s:token/>
        <div class="alert alert-danger" align="center">
           <strong>Error encountered. Please do not use the browser back or refresh buttons.</strong> 
        </div>
    </body>
</html>