<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialDocument.addtitle" /></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();       
}
function handleAction(){
    var page;
    page=document.forms[0].page.value;
    if (page == "Edit"){
    	document.forms[0].action="trialDocumentupdate.action";
    	document.forms[0].submit();  	
    } else {
    	document.forms[0].action="trialDocumentcreate.action";
    	document.forms[0].submit();   
    } 
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}

</SCRIPT>
<body>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="reviewdocs"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstractdocs"/>
</c:if>
 <h1><fmt:message key="trialDocument.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <s:url id="cancelUrl" namespace="/protected" action="trialDocumentquery"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form method="POST" enctype="multipart/form-data">
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialDocument.addtitle" /></h2>
    <s:hidden name="page" />
    <s:hidden name="id" />
    <table class="form">                
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="trialDocument.type"/><span class="required">*</span>
                     </label>
                    </td>
    				<s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.DocumentTypeCode@getDocTypeCodes()" />
                    <td class="value">
    					<s:if test="page.equals('Edit')">
                      		<s:select headerKey="" headerValue="" name="trialDocumentWebDTO.typeCode" id="typeCode"
                           		list="#typeCodeValues" cssStyle="width:206px" disabled="true"/>   
                           		<s:hidden name="trialDocumentWebDTO.typeCode" />              
    					</s:if>
    					<s:else>
                    		<s:select headerKey="" headerValue="" name="trialDocumentWebDTO.typeCode" id="typeCode"
                           		list="#typeCodeValues" cssStyle="width:206px"/>                    
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialDocumentWebDTO.typeCode</s:param>
                               </s:fielderror>                            
                            </span>
						</s:else>    
                      </td>         
                </tr>                
                <tr>
                     <td scope="row" class="label">
                     <label for="fileUpload">
                            <fmt:message key="trialDocument.fileName"/><span class="required">*</span>
                     </label>
                    </td>
                    <td class="value">
                        <s:file name="upload" id="fileUpload" cssStyle="width:270px"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialDocumentWebDTO.uploadFileName</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>         
                </tr>                 
        </table>
		<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                     <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                    </li>                
                </ul>   
            </del>
        </div> 

                   
    </s:form>
   </div>
 </body>
 </html>
