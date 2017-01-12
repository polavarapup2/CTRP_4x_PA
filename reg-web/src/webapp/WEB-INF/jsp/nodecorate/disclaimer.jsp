<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="disclaimer.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function submitForm(btnSelected){
    if(btnSelected == 'accept') {
        document.forms[0].action = "disClaimerActionaccept.action";
        document.forms[0].submit();
    } else{
        document.forms[0].action="<%=request.getContextPath()%>/logout.action";
        document.forms[0].submit();
    } 
    
}
</SCRIPT>
<s:form name="disclaimer" method="POST">
<div class="row">
	<div class="col-xs-8 col-xs-offset-2">
	  <h3 class="heading align-center"><span><fmt:message key="disclaimer.page.ctrp"/></span></h3>
	  <p><s:property escapeHtml="false" escapeXml="false" value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('Disclaimer','Registry')"/></p>
	  <h3 class="heading align-center"><span><fmt:message key="disclaimer.page.ctrp.burden.title"/></span></h3>
	  <small class="omb">OMB#: <s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentVersion('OMB','Registry')"/> 
                                EXP. DATE:
                                <s:set var="ombExpDate" value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentExpiration('OMB','Registry')"/>
                                <fmt:formatDate value="${ombExpDate}" dateStyle="SHORT"/> 
                                </p></small>
	  <p><s:property escapeHtml="false" escapeXml="false" value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('OMB','Registry')"/></p>
	  <hr>
	  <div class="align-center">
	    <button type="button" class="btn btn-primary btn-icon mr20" data-dismiss="modal" onClick="submitForm('accept');" id="acceptDisclaimer"><i class="fa-check-circle"></i>Accept</button>
	    <button type="button" class="btn btn-default btn-icon" data-dismiss="modal" onclick="submitForm('decline');" id="rejectDisclaimer"><i class="fa-times-circle"></i>Reject</button>
	   </div>
	 </div>
</div>
</s:form>  
 