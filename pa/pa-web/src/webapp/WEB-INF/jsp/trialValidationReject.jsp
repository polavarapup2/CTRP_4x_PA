<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialValidation.page.title"/></title>
    <s:head />
</head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();        
}

function handleAction(){
 document.forms[0].action="trialValidationrejectReason.action";
 document.forms[0].submit();
} 

</SCRIPT>

<body>
<c:set var="topic" scope="request" value="validatetrial"/>
 <h1><fmt:message key="trialValidation.page.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box"> 
   <pa:sucessMessage/>
   <pa:failureMessage/> 
    <s:form>
        <s:token/>
        <pa:studyUniqueToken/>
    <h2>Trial Rejection</h2>
    <table class="form">
                <tr>
                <td scope="row" class="label">
                        <label for="deletion">                      
                          Rejection Reason<span class="required">*</span>
                        </label>
                     </td>                     
                    <td class="value">
                        <s:set name="reasonCodeValues" value="@gov.nih.nci.pa.enums.RejectionReasonCode@getDisplayNames()" />
                        <s:select headerKey="" id="reasonCodes" headerValue="--Select--" name="gtdDTO.rejectionReasonCode" 
                          list="#reasonCodeValues"  value="gtdDTO.rejectionReasonCode" cssStyle="width:206px"/>
                         <span class="formErrorMsg"> 
                           <s:fielderror>
                             <s:param>gtdDTO.getRejectionReasonCode</s:param>
                           </s:fielderror>                            
                         </span> 
                      </td>  
                </tr> 
                <tr>
                    <td scope="row" class="label">
                        <label for="deletion">                      
                            Comments
                        </label>
                     </td>                     
                    <td class="value">
                        <s:textarea name="gtdDTO.commentText" rows="4" cssStyle="width:206px"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>gtdDTO.commentText</s:param>
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