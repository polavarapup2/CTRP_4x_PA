<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialAssociations.addtitle" /></title>
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
    if ($F('trialAssociation.identifier.extension')!=''){
 		document.forms[0].action="trialAssociationsupdateAssociation.action";
 		document.forms[0].submit();  	
	} else {
 		document.forms[0].action="trialAssociationscreate.action";
 		document.forms[0].submit();   
 	} 
} 

function cancelAdd() {
	document.forms[0].action="trialAssociationsquery.action";
    document.forms[0].submit();  
}

function lookUpTrial() {
	showWidePopup('/pa/bare/studyProtocolexecute.action?pageFrom=associate', null, 'Select a Trial');
}

function trialSelected(trialId, trialNciId) {
	$('trialId').value = trialId;
	displayWaitPanel();
    document.forms[0].action="trialAssociationsassociateWithTrial.action";
    document.forms[0].submit();
}

</SCRIPT>
<body>
<c:set var="topic" scope="request" value="trialAssociations"/>
 <h1><fmt:message key="trialAssociations.addtitle" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialAssociations.addtitle" /></h2>
        
    <s:hidden name="trialAssociation.identifier.extension" id="trialAssociation.identifier.extension"/>
    <s:hidden name="trialId" id="trialId"/>
    
    <s:if test="%{trialAssociation.identifier.extension!=null && trialId==null}">
	    <p align="center" class="info">
	       This association is with a trial that is not yet registered in CTRP.
	    </p>     
    </s:if>
    
    
    
    <s:if test="%{trialAssociation.identifier.extension!=null && trialId!=null}">
        <p align="center" class="info">
           Because this association is with a registered CTRP trial, you will not be able to change trial
           information on this screen. <br/> Use <b>Look Up Trial</b> button if you need to change the association to a different registered
           trial, or create a brand new association if your trial is not yet registered in CTRP.
        </p>
        <c:set scope="request" var="disableControls" value="${true}"/>     
        <s:set var="disableControls" value="%{true}"/>
    </s:if>    
    
    <table class="form">                
                <tr>
                     <td scope="row" class="label">
	                     <label for="trialAssociation.studyIdentifier.value">
	                            <fmt:message key="trialAssociations.trialIdentifier"/><span class="required">*</span>
	                     </label>
                    </td>
    				<td class="value" width="1%" nowrap="nowrap">    					
                    		<s:textfield id="trialAssociation.studyIdentifier.value"
                    		  readonly="%{disableControls}"
                    		  name="trialAssociation.studyIdentifier.value" maxlength="64"  cssStyle="width:206px"/>                   
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialAssociation.studyIdentifier</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td> 
                      <td class="value">
                        <a href="javascript:void(0)" class="btn" onclick="lookUpTrial();" />
                            <span class="btn_img"><span class="organization">Look Up Trial</span></span>
                        </a>                      
                      </td>        
                </tr>
                
                <tr>
                     <td scope="row" class="label">
                         <label for="trialAssociation.identifierType.code">
                                <fmt:message key="trialAssociations.identifierType"/><span class="required">*</span>
                         </label>
                    </td>
                    <td class="value">  
                       <s:set name="identifierSearchTypes" value="@gov.nih.nci.pa.enums.IdentifierType@getDisplayNames()" />   
                       <s:select id="trialAssociation.identifierType.code" headerKey="" 
                           headerValue="" name="trialAssociation.identifierType.code"
                           disabled="%{disableControls}"
                           list="#identifierSearchTypes" value="trialAssociation.identifierType.code"  cssStyle="width:206px" />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialAssociation.identifierType</s:param>
                               </s:fielderror>                            
                        </span>                            
                    </td>         
                </tr>  
                
                <tr>
                     <td scope="row" class="label">
                         <label for="trialAssociation.studyProtocolType.code">
                                <fmt:message key="trialAssociations.trialType"/>
                         </label>
                    </td>
                    <td class="value">  
                       <s:set name="selectList" value="@gov.nih.nci.pa.enums.StudyTypeCode@getDisplayNames()" />   
                       <s:select id="trialAssociation.studyProtocolType.code" headerKey="" 
                           headerValue="" name="trialAssociation.studyProtocolType.code"
                            disabled="%{disableControls}"
                           list="#selectList" value="trialAssociation.studyProtocolType.code"  cssStyle="width:206px" />
                    </td>         
                </tr>                

                <tr>
                     <td scope="row" class="label">
                         <label for="trialAssociation.studySubtypeCode.code">
                                <fmt:message key="trialAssociations.trialSubType"/>
                         </label>
                    </td>
                    <td class="value">  
                       <s:set name="selectList" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />   
                       <s:select id="trialAssociation.studySubtypeCode.code" headerKey="" 
                           headerValue="" name="trialAssociation.studySubtypeCode.code"
                            disabled="%{disableControls}"
                           list="#selectList" value="trialAssociation.studySubtypeCode.code"  cssStyle="width:206px" />
                    </td>         
                </tr>                

                <tr>
                     <td scope="row" class="label">
                         <label for="trialAssociation.officialTitle.value">
                                <fmt:message key="trialAssociations.officialTitle"/>
                         </label>
                    </td>
                    <td class="value">                      
                            <s:textarea id="trialAssociation.officialTitle.value" cols="40" rows="4" name="trialAssociation.officialTitle.value" maxlength="4000"
                                readonly="%{disableControls}"
                                cssClass="charcounter"/>
                    </td>         
                </tr>                              
                                
        </table>        
        <c:if test="${not disableControls}">
			<div class="actionsrow">
	            <del class="btnwrapper">
	                <ul class="btnrow">
	                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
	                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="cancelAdd()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>                
	                </ul>   
	            </del>
	        </div>
        </c:if>
        <c:if test="${disableControls}"> 
        <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li><s:a href="javascript:void(0)" cssClass="btn" onclick="cancelAdd()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>                
                    </ul>   
                </del>
            </div>
        </c:if>                  
    </s:form>
   </div>
 </body>
 </html>
