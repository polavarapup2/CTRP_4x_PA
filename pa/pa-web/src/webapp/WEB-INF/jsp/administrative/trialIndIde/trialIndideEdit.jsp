<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialIndide.edittitle"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();
    checkAll();
    
    var grantor = "${studyIndldeWebDTO.grantor}";
    if (grantor!='') {
        var list = $('SubCat');
        Form.Element.setValue(list, grantor);
    }    
    
}
function checkAll(){
		if (document.getElementById('group4').value == 'Yes'){
			showRow(document.getElementById('expandedStatusRow'));
		} else {
			hideRow(document.getElementById('expandedStatusRow'));
		}
		if (document.getElementById('group3').value == 'IND'){
			addOption(document.getElementById('SubCat'),"CDER", "CDER");
			addOption(document.getElementById('SubCat'),"CBER", "CBER");
		} else {
		    addOption(document.getElementById('SubCat'),"CDRH", "CDRH");
            addOption(document.getElementById('SubCat'),"CBER", "CBER");
		}

		if (document.getElementById('holderType').value == 'NIH'){
			showRow(document.getElementById('programcodenihid'));
			hideRow(document.getElementById('programcodenciid'));
			$('programcoderow').show();
		} else 
		if (document.getElementById('holderType').value == 'NCI'){
			hideRow(document.getElementById('programcodenihid'));
			showRow(document.getElementById('programcodenciid'));
			$('programcoderow').show();
		} 
		else {
			$('programcoderow').hide();
		}
}
function hideRow(row){			
		row.style.display = 'none';	
}
function showRow(row){
		row.style.display = '';
}
function checkIndicator() {
	if (document.getElementById('group4').value == 'Yes'){
		showRow(document.getElementById('expandedStatusRow'));
	} else {
		hideRow(document.getElementById('expandedStatusRow'));
	}
}
function checkCode() {
var input="studyIndldeWebDTO.nihInstHolder";
var inputElement = document.forms[0].elements[input];
	
if (inputElement.options[inputElement.selectedIndex].value == "NCI-National Cancer Institute")
	{
 		document.getElementById('programcodenciid').style.display = '';
 		document.getElementById('programcodenihid').style.display = 'none';
		$('programcoderow').show();
   	}else
   	{
   		document.getElementById('programcodenciid').style.display = 'none';
 		document.getElementById('programcodenihid').style.display = '';
		$('programcoderow').show();
   	}
}
function getIndIdeRadioValue(size){
		for(var i=0; i<size; i++) {
			if(document.forms[0].group3[i].checked==true) 
				return(document.forms[0].group3[i].value);
		}
}
function getExpandedAccessRadioValue(size){
	for(var i=0; i<size; i++) {
		if(document.forms[0].group4[i].checked==true) 
			return(document.forms[0].group4[i].value);
	}
}
function setProgramCodes(ref){	
		if (ref.value == 'NCI') {
			document.getElementById('programcodenciid').style.display = '';
			document.getElementById('programcodenihid').style.display = 'none';
			$('programcoderow').show();
		} else if (ref.value == 'NIH') {
			document.getElementById('programcodenciid').style.display = 'none';
			document.getElementById('programcodenihid').style.display = '';
			$('programcoderow').show();
		} else {
			document.getElementById('programcodenihid').style.display = 'none';
			document.getElementById('programcodenciid').style.display = 'none';
			$('programcoderow').hide();
		}
}
	
function resetValues(){
		document.getElementById('indidenumber').value='';
		clearRadios('group3');
    	removeAllOptions(document.getElementById('SubCat'));
    	addOption(document.getElementById('SubCat'), "", "--Select--", "");	
    	document.getElementById('holderType').value='';    	
		document.getElementById('programcodenihselectedvalue').value='';	
		document.getElementById('programcodenciselectedvalue').value='';
		document.getElementById('programcodenihid').style.display = 'none';
		document.getElementById('programcodenciid').style.display = 'none';
		//$('programcoderow').hide();
		document.forms[0].group4[0].checked = false;
		document.forms[0].group4[1].checked = true;
		document.getElementById('expandedStatus').value='';
		document.getElementById('expandedStatus').disabled=true;
		document.getElementById('addbtn').disabled=true;
        document.getElementById('exemptIndicator').checked = false;
}
function clearRadios( radioname ){
	   for( i = 0; i < document.forms[0][radioname].length; i++ )
	      document.forms[0][radioname][i].checked = false;
}
function removeAllOptions(selectbox){
		var i;
		for(i=selectbox.options.length-1;i>=0;i--){
			selectbox.remove(i);
		}
}
function addOption(selectbox, value, text ){
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
}	
function SelectSubCat(i){
		removeAllOptions(document.getElementById('SubCat'));
		addOption(document.getElementById('SubCat'), "", "-Select-", "");	
		if(i.value == 'IND'){
			addOption(document.getElementById('SubCat'),"CDER", "CDER");
			addOption(document.getElementById('SubCat'),"CBER", "CBER");
		}
		if(i.value == 'IDE'){
			addOption(document.getElementById('SubCat'),"CDRH", "CDRH");
		    addOption(document.getElementById('SubCat'),"CBER", "CBER");
		}
}
function handleAction(){
 var page;
page=document.forms[0].page.value;
if (page == "Edit"){
 document.forms[0].action="trialIndideupdate.action";
 document.forms[0].submit(); 
} else {
 document.forms[0].action="trialIndidecreate.action";
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
<c:set var="topic" scope="request" value="reviewind"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstractind"/>
</c:if>
 <h1><fmt:message key="trialIndide.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialIndide.edittitle" /></h2>
    	<s:hidden name="page" />
    	<s:hidden name="cbValue" />
    	<s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.NihInstituteCode@getDisplayNames()" />
		<s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
		<s:set name="expandedAccessStatusCodeValues" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@getDisplayNames()" />
    <table class="form">   
  		<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.indldeType"/>:<span class="required">*</span></label>
						</td>
						<td class="value">		
						<s:select id="group3" name="studyIndldeWebDTO.indldeType" list="#{'IND':'IND', 'IDE':'IDE'}" onclick="SelectSubCat(this);" />	
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.indldeType</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.indideNumber"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<s:textfield  name="studyIndldeWebDTO.indldeNumber" maxlength="10" cssStyle="width:150px"/>
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.indldeNumber</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.grantor"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<select id="SubCat" name="studyIndldeWebDTO.grantor"  style="width:150px"></select>							
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.grantor</s:param>
                               </s:fielderror>                            
                         </span>
						</td>	
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.holderType"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<s:select id="holderType" name="studyIndldeWebDTO.holderType" headerKey="" headerValue="-Select-" cssStyle="width:150px" onclick="setProgramCodes(this);"
							list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}"/>
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.holderType</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr id="programcoderow">
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.nihnciDivProgHolderCode"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
						<div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.nihInstHolder" list="#phaseCodeValuesNIH" onclick="checkCode();" /></div>
						<div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.nciDivProgHolder" list="#phaseCodeValuesNCI" /></div>
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.nihInstHolder</s:param>
                               </s:fielderror>                            
                         </span>
                         <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.nciDivProgHolder</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr>
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.expandedAccessIndicator"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<s:select id="group4" name="studyIndldeWebDTO.expandedAccessIndicator" list="#{'No':'No', 'Yes':'Yes'}" onclick="checkIndicator();" />
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.expandedAccessIndicator</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
					<tr id="expandedStatusRow">
						<td scope="row"  class="label"><label>
							<fmt:message key="trialIndide.expandedAccessStatusCode"/>:<span class="required">*</span></label>
						</td>
						<td class="value">
							<s:select id="expandedStatus" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.expandedAccessStatus"  
							list="#expandedAccessStatusCodeValues"/>
							<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>studyIndldeWebDTO.expandedAccessStatus</s:param>
                               </s:fielderror>                            
                         </span>
						</td>
					</tr>
                    <tr>
                         <td scope="row"  class="label"><label>
                            <fmt:message key="trialIndide.exemptIndicator"/>:</label>
                        </td>
                       <td>
                        <s:select name="studyIndldeWebDTO.exemptIndicator" id="exemptIndicator" list="#{'false':'No', 'true':'Yes'}"></s:select>
                        </td>
                    </tr>
    </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>                
                    <li><s:a href="trialIndidequery.action" cssClass="btn"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>
                </ul>   
            </del>
        </div>             
    </s:form>
   </div>
 </body>
 </html>