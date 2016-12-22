<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message key="isdesign.details.title"/></title>
<s:head />
</head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();
    displayMaskingRoles();         
}

function displayMaskingRoles() {	
	
	var input2="webDTO.blindingSchemaCode";
	var inputElement2 = document.forms[0].elements[input2];
		if ((inputElement2.options[inputElement2.selectedIndex].value == "Single Blind")
			|| (inputElement2.options[inputElement2.selectedIndex].value == "Double Blind"))
		{
   			document.getElementById("blindingRoleCode").style.display = "";
   		}else
   		{
   			document.getElementById("blindingRoleCode").style.display = "none";
   		}
	}
function showAlert() {
	var input="webDTO.phaseCode";
  	var inputElement = document.forms[0].elements[input];
	
   		if (inputElement.options[inputElement.selectedIndex].value == "Other")
		{
			alert("Please select a different Trial Phase");
		}
}
function checkValue() {
    var input="webDTO.minimumTargetAccrualNumber";
    var conf = true;
    var inputElement = document.forms[0].elements[input];
    
        if (inputElement.value == "0")
        {
            conf = confirm("Please confirm if Target Enrollment 0 is OK");
            if(conf != true) {
              inputElement.value='';
              inputElement.focus();
            }
            
        }
       return conf; 
}
function ChecksCount(name) {

var input2="webDTO.blindingSchemaCode";
var inputElement2 = document.forms[0].elements[input2];

		if (inputElement2.options[inputElement2.selectedIndex].value == "Single Blind") 
		{
			var maxchecked = 1;
			var count = 0;
			if(document.forms[0].subject.checked == true) { count++; }
			if(document.forms[0].investigator.checked == true) { count++; }
			if(document.forms[0].caregiver.checked == true) { count++; }
			if(document.forms[0].outcomesassessor.checked == true) { count++; }
			if(count > maxchecked) {
				eval('document.forms[0].' + name + '.checked = false');
				alert('Only ' + maxchecked + ' needs to be checked for Single Blind.');
			}
		}else
		{
			var maxchecked = 2;
			var count = 0;
			if(document.forms[0].subject.checked == true) { count++; }
			if(document.forms[0].investigator.checked == true) { count++; }
			if(document.forms[0].caregiver.checked == true) { count++; }
			if(document.forms[0].outcomesassessor.checked == true) { count++; }
			if(count > maxchecked) {
				eval('document.forms[0].' + name + '.checked = false');
				alert('Only ' + maxchecked + ' needs to be checked for Double Blind.');
			}
		}

}



function handleAction(){
 var conf = checkValue();
 if(conf == true) {
  document.forms[0].action="interventionalStudyDesignupdate.action";
  document.forms[0].submit();
 } 
} 

function changeStudyType() {
	displayWaitPanel();
	document.forms[0].action="interventionalStudyDesignchangeStudyType.action";
	document.forms[0].submit();	
}

function tooltip() {
	BubbleTips.activateTipOn("acronym");
	BubbleTips.activateTipOn("dfn"); 
}

function displayPrimaryPurposeOtherText(){
   if ($('webDTO.primaryPurposeCode').value == 'Other') {
        $('purposeOtherTextDiv').show();
   } else {
        $('purposeOtherTextDiv').hide();
  }
}
function displaySecondaryPurposeOtherText(){
	   if ($('webDTO.secondaryPurposes').value == 'Other') {
	        $('secondaryPurposeOtherTextDiv').show();
	   } else {
	        $('secondaryPurposeOtherTextDiv').hide();
	  }
}
function displayPhaseAdditonalCode(){
   if ($('webDTO.phaseCode').value == 'NA') {
         $('phaseOtherDiv').show();
   } else {
       $('phaseOtherDiv').hide();
   }
}
function initialize() {
    displayPrimaryPurposeOtherText();
    displaySecondaryPurposeOtherText();
    displayPhaseAdditonalCode();
}
</SCRIPT>
<body>
<h1><fmt:message key="isdesign.details.title"/></h1>
<c:set var="topic" scope="request" value="abstractdesign"/>
<c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <s:url id="cancelUrl" namespace="/protected" action="interventionalStudyDesigndetailsQuery"/>
<div class="box">
<pa:sucessMessage/>
   <pa:failureMessage/>
<s:form>
    <s:token/>
    <pa:studyUniqueToken/>
<s:actionerror/>
<h2><fmt:message key="isdesign.details.title"/></h2>
<table class="form">

    <tr>
        <td  scope="row" class="label"><label for="study">
            <fmt:message key="osdesign.details.study.type"/><span class="required">*</span></label></td>        
        <td>
                <s:set var="hasOnlyResultsAbstractorRole">${!(sessionScope.isAdminAbstractor==true
	                                ||sessionScope.isScientificAbstractor==true ||sessionScope.isSuAbstractor==true)}</s:set>
        
          <s:select id="study" name="webDTO.studyType" list="#{'Interventional':'Interventional','NonInterventional':'Non-Interventional'}"  
                   onchange="changeStudyType();"
                   value="webDTO.studyType" cssStyle="width:200px" disabled="#hasOnlyResultsAbstractorRole" />
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyType</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    
    <tr>
        <td style="padding: 0 0 0 0;"></td>
        <td style="padding: 0 0 0 0;">
            <p class="info" style="margin-top: 0px;">
                Changing Study Type will immediately reload this page in order to display
                data fields that apply to Non-Interventional Trials. 
            </p>
        </td>
    </tr>
    <tr>
        <td  scope="row" class="label"><label for="webDTO.expandedIndicator">
            <fmt:message key="osdesign.details.expanded.access"/></label></td>        
         <td>
        <s:radio name="webDTO.expandedIndicator" id="webDTO.expandedIndicator"  list="#{true:'Yes', false:'No'}" 
        value="webDTO.expandedIndicator" disabled="#hasOnlyResultsAbstractorRole"/>
        </td>
    </tr>
    <tr>
        <td style="padding: 0 0 0 0;"></td>
        <td style="padding: 0 0 0 0;">
            <p class="info" style="margin-top: 0px;">
                Expanded Access trials will not be sent to the cancer.gov API.
            </p>
        </td>
    </tr>

	<tr>
	 	<td  scope="row" class="label"><label for="webDTO.primaryPurposeCode">
	 		<fmt:message key="isdesign.details.primary.purpose"/><span class="required">*</span></label></td>
     	<s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames(@gov.nih.nci.pa.enums.StudyTypeCode@INTERVENTIONAL)" />
        <td>
          <s:select headerKey="" headerValue="" name="webDTO.primaryPurposeCode" id="webDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="webDTO.primaryPurposeCode" cssStyle="width:150px" onchange="displayPrimaryPurposeOtherText()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="purposeOtherTextDiv" style="display:'none'">
         <td scope="row" class="label">
            <label for="otherText"><fmt:message key="isdesign.details.primary.purpose.otherText"/></label>
         </td>
         <td>
               <s:textarea id="otherText" name="webDTO.primaryPurposeOtherText"  cols="50" rows="2" maxlength="200" cssClass="charcounter"/><br/>
               <span class="info">Required if Purpose equals &#39;Other&#39;</span>
               <span class="formErrorMsg"> 
               <s:fielderror>
               <s:param>webDTO.primaryPurposeOtherText</s:param>
               </s:fielderror>                            
               </span>
         </td>
      </tr>
      
    <tr>
        <td  scope="row" class="label">
            <label for="webDTO.secondaryPurposes">
                <fmt:message key="isdesign.details.secondary.purpose"/>
            </label>
        </td>
        <s:set name="secondaryPurposeCodeValues" value="@gov.nih.nci.pa.service.util.PAServiceUtils@getSecondaryPurposeList()" />
        <td>
          <s:select headerKey="" headerValue="" 
                   name="webDTO.secondaryPurposes" id="webDTO.secondaryPurposes" list="#secondaryPurposeCodeValues"
                   value="webDTO.secondaryPurposes" cssStyle="width:150px" onchange="displaySecondaryPurposeOtherText()"/>
                   
        </td>
    </tr>
    
    <tr id="secondaryPurposeOtherTextDiv" style="display:'none'">
         <td scope="row" class="label">
            <label for="secondaryOtherText"><fmt:message key="isdesign.details.secondary.purpose.otherText"/></label>
         </td>
         <td>
               <s:textarea id="secondaryOtherText" name="webDTO.secondaryPurposeOtherText"  cols="50" rows="2" maxlength="1000" cssClass="charcounter"/><br/>               
               <span class="formErrorMsg"> 
               <s:fielderror>
               <s:param>webDTO.secondaryPurposeOtherText</s:param>
               </s:fielderror>                            
               </span>
         </td>
      </tr>    
      
    <tr>
        <td scope="row" class="label"><label for="webDTO.phaseCode">
        	 <fmt:message key="studyProtocol.studyPhase"/><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
        	<s:select headerKey="" headerValue="" name="webDTO.phaseCode" id="webDTO.phaseCode" list="#phaseCodeValues" 
				value="webDTO.phaseCode" cssStyle="width:100px" onchange="displayPhaseAdditonalCode()" />
			<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id ="phaseOtherDiv" style="display:'none'">
		<td   scope="row" class="label"><label for="comment">
	 		<fmt:message key="isdesign.details.phase.comment"/>
            </label></td>
		<td>
		<s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
        <s:select id="comment" headerKey="" headerValue="No" name="webDTO.phaseAdditionalQualifierCode" list="#phaseAdditionlQualiefierCodeValues" 
                value="webDTO.phaseAdditionalQualifierCode" cssStyle="width:120px" />
	           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.phaseAdditionalQualifierCode</s:param>
             </s:fielderror>                            
          </span>
		</td>
	</tr>	
	<tr>
		<td scope="row" class="label"><label for="conf">
	 		<fmt:message key="isdesign.details.intervention.model"/><span class="required">${asterisk}</span></label></td>
		<s:set name="designConfigurationCodeValues" value="@gov.nih.nci.pa.enums.DesignConfigurationCode@getDisplayNames()" />
        <td>
           <s:select id="conf" headerKey="" headerValue="" name="webDTO.designConfigurationCode" list="#designConfigurationCodeValues"  value="webDTO.designConfigurationCode" cssStyle="width:150px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.designConfigurationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label for="interventionGroup">
	 		<fmt:message key="isdesign.details.no.arms"/><span class="required">${asterisk}</span></label></td>
		<td>
         	<s:textfield id="interventionGroup" name="webDTO.numberOfInterventionGroups" maxlength="3" cssStyle="width:25px"/>
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.numberOfInterventionGroups</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
    <tr>
		<td scope="row" class="label"><label for="masking">
	 		<fmt:message key="isdesign.details.masking"/><span class="required">${asterisk}</span></label></td>
		<s:set name="blindingSchemaCodeValues" value="@gov.nih.nci.pa.enums.BlindingSchemaCode@getDisplayNames()" />
        <td>
           <s:select id="masking" headerKey="" headerValue="" name="webDTO.blindingSchemaCode" list="#blindingSchemaCodeValues"  
	           value="webDTO.blindingSchemaCode" cssStyle="width:120px" onchange="displayMaskingRoles();" />
	       <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.blindingSchemaCode</s:param>
             </s:fielderror>                            
          </span>
         </td>
	</tr>
	<tr id="blindingRoleCode">
		<td   scope="row" class="label"><fmt:message key="isdesign.details.masking.role"/></td>
		<td>
		  <table cellpadding="0" cellspacing="0">
		      <tr>
		          <td style="padding: 0 0 0 0;" nowrap="nowrap">
			           <s:checkbox id="subject" name="subject" fieldValue="Subject"  value="%{subjectChecked}" />
			           <label for="subject">Subject</label>
					   <s:checkbox id="investigator" name="investigator" fieldValue="Investigator"  value="%{investigatorChecked}" />
					   <label for="investigator">Investigator</label>
					   <s:checkbox id="caregiver" name="caregiver" fieldValue="Caregiver"  value="%{caregiverChecked}" />
					   <label for="caregiver">Caregiver</label>
					   <s:checkbox id="outcomesassessor" name="outcomesassessor" fieldValue="Outcomes Assessor"  value="%{outcomesAssessorChecked}" />
					   <label for="outcomesassessor">Outcomes Assessor</label>
				   </td>
			  </tr>  
			  <tr>                
                <td style="padding: 5 0 0 0;">
                    <p class="info" style="margin-top: 0px; padding-bottom: 0px;">
                        Even though not mandatory on this screen, failure to select masking role(s) may lead to abstraction validation warnings/errors.
                    </p>
                </td>
            </tr>        
          </table>
         </td>
	</tr>	
	<tr> 
        <td scope="row" class="label"><label for="allocation">
	 		<fmt:message key="isdesign.details.allocation"/><span class="required">${asterisk}</span></label> </td>
        <s:set name="allocationCodeValues" value="@gov.nih.nci.pa.enums.AllocationCode@getDisplayNames()" />
        <td>
        	<s:select id="allocation" headerKey="" headerValue="" name="webDTO.allocationCode" list="#allocationCodeValues"  value="webDTO.allocationCode" cssStyle="width:206px" />
        	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.allocationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
		<td scope="row" class="label"><label for="classification">
	 		<fmt:message key="isdesign.details.trial.classification"/></label></td>
		<s:set name="studyClassificationCodeValues" value="@gov.nih.nci.pa.enums.StudyClassificationCode@getDisplayNames()" />
        <td>
           <s:select id="classification" headerKey="" headerValue="" name="webDTO.studyClassificationCode" list="#studyClassificationCodeValues"  value="webDTO.studyClassificationCode" cssStyle="width:206px" />
           <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.studyClassificationCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
		<td scope="row" class="label"><label for="enrollment">
	 		<fmt:message key="isdesign.details.target.enrollment"/><span class="required">${asterisk}</span></label></td>
		<td>
         	<s:textfield id="enrollment" name="webDTO.minimumTargetAccrualNumber" maxlength="6" cssStyle="width:50px" />
         	<span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.minimumTargetAccrualNumber</s:param>
             </s:fielderror>                            
          </span>
        </td>
	</tr>
	<tr>
        <td scope="row" class="label"><label for="webDTO.finalAccrualNumber">
            <fmt:message key="isdesign.details.finalAccrualNumber"/></label></td>
        <td>
            <s:textfield id="webDTO.finalAccrualNumber" name="webDTO.finalAccrualNumber" maxlength="6" cssStyle="width:50px" />
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>webDTO.finalAccrualNumber</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
        <td scope="row" class="label"><fmt:message key="isdesign.details.accrualNum"/></td>
        <td id="isdesign.details.accrualNum">
            <s:property value="(new gov.nih.nci.pa.service.util.PAServiceUtils()).getTrialAccruals(#session.studyProtocolIi)"/>
        </td>
    </tr>
</table>
	
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
            <pa:scientificAbstractorDisplayWhenCheckedOut>
                <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                </li>
            </pa:scientificAbstractorDisplayWhenCheckedOut>    
		</ul>	
	</del>
</div>
</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">
initialize();
</SCRIPT>
</body>
</html>
