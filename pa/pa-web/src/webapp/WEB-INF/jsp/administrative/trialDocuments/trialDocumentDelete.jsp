<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialDocument.title"/></title>
    <s:head />
</head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();        
}

function handleAction(){
	document.forms[0].action="trialDocumentdelete.action";
	document.forms[0].submit(); 
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
  <div class="box">  
    <s:form>
        <s:token/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialDocument.subtitle" /></h2>
    <s:hidden name="id"/>
    <s:hidden name="objectsToDelete"/>
    <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <label for="deletion">                      
                            <fmt:message key="trialDocument.inactiveCommentText"/><span class="required">*</span>
                        </label>
                     </td>                     
                    <td class="value">
                        <s:textarea name="trialDocumentWebDTO.inactiveCommentText" id="inactiveComment" rows="4" cssStyle="width:206px"
                            maxlength="200" cssClass="charcounter"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialDocumentWebDTO.inactiveCommentText</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>         
                </tr>                 
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Done</span></span></s:a></li>                
                </ul>   
            </del>
        </div>
                   
    </s:form>
   </div>
 </body>
 </html>