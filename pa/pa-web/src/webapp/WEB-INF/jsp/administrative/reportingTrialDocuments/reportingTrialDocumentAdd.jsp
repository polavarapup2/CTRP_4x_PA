<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialDocument.addtitle" /></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
     
}
function handleAction(){
    var page;
    var addSubmitUrl;
    var editSubmitUrl;
    page=document.forms[0].page.value;
    var parentPage=jQuery("#parentPage").val();
    if(parentPage=="coverSheet") {
    	addSubmitUrl ="resultsReportingDocumentcreate.action";
    	editSubmitUrl="resultsReportingDocumentupdate.action";
    }
    else {
    	addSubmitUrl ="trialViewcreate.action";
    	editSubmitUrl="trialViewupdate.action";
    }
    if (page == "Edit"){
    	var typeCode = jQuery("#typeCode").val();
    	if(typeCode=="Before results" || typeCode== "After results") {
    		var isProceed =  confirm("If \"Before results\" or \"After results\" document is changed the \"Comparison document\" will be removed. Do you wish to proceed?");
    		if(!isProceed) {
    			return false;
    		}
    	}
    	document.forms[0].action=editSubmitUrl;
    	document.forms[0].submit();  	
    } else {
       	document.forms[0].action=addSubmitUrl;
    	document.forms[0].submit();   
    } 
} 


</SCRIPT>
<body>

 <h1><fmt:message key="trialDocument.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <s:if test="parentPage.equals('coverSheet')">
    <s:url id="cancelUrl" namespace="/protected" action="resultsReportingDocumentquery?studyProtocolId=%{studyProtocolId}"/>            
 </s:if>
 <s:else>
   <s:url id="cancelUrl" namespace="/protected" action="trialViewquery?studyProtocolId=%{studyProtocolId}"/>                  
 </s:else> 
 
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
    				<s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.DocumentTypeCode@getTrialReportingDisplayNames()" />
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
       <s:hidden name="studyProtocolId" id="studyProtocolId" />     
       <s:hidden name="parentPage" id="parentPage"/> 
         
    </s:form>
   </div>
 </body>
 </html>
