<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="form-group">
    <s:hidden id="trialDTO.primaryPurposeAdditionalQualifierCode" name="trialDTO.primaryPurposeAdditionalQualifierCode"/>
    <label class="col-xs-4 control-label" for="trialDTO.primaryPurposeCode"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label>
    <label class="col-xs-4 control-label" for="trialDTO.primaryPurposeCode2" style="display: none;"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label>
    <s:set name="interventionalTypeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames(@gov.nih.nci.pa.enums.StudyTypeCode@INTERVENTIONAL)" />
    <s:set name="noninterventionalTypeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames(@gov.nih.nci.pa.enums.StudyTypeCode@NON_INTERVENTIONAL)" />
    <div class="col-xs-4">
        <s:select headerKey="" headerValue="--Select--" id ="trialDTO.primaryPurposeCode" name="trialDTO.primaryPurposeCode" list="#interventionalTypeCodeValues" 
            cssClass="form-control interventional interventional-input-ctr" value="trialDTO.primaryPurposeCode" onchange="displayPrimaryPurposeOtherCode(this);"/>
        <s:select headerKey="" headerValue="--Select--" id ="trialDTO.primaryPurposeCode2" name="trialDTO.primaryPurposeCode" list="#noninterventionalTypeCodeValues"  cssStyle="display: none;" 
            cssClass="form-control non-interventional non-interventional-input-ctr" value="trialDTO.primaryPurposeCode" onchange="displayPrimaryPurposeOtherCode(this);" disabled="true"/>                                        
        <span class="alert-danger">
            <s:fielderror>
                <s:param>trialDTO.primaryPurposeCode</s:param>
            </s:fielderror>
        </span>
    </div>
    <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.primary_purpose"/>" data-placement="top" data-trigger="hover"></i> </div>
</div>
<div class="form-group" id="purposeOtherTextDiv" style="display:'none'">
    <label class="col-xs-4 control-label" for="trialDTO.primaryPurposeOtherText"><fmt:message key="submit.trial.otherPurposeText"/></label>
    <div class="col-xs-4">
        <s:textarea id="trialDTO.primaryPurposeOtherText" name="trialDTO.primaryPurposeOtherText"  cols="50" rows="2" 
            maxlength="200" cssClass="form-control charcounter" /><br/>
        <span class="info">Required if Purpose equals &#39;Other&#39;</span>
        <span class="alert-danger"> 
            <s:fielderror>
                <s:param>trialDTO.primaryPurposeOtherText</s:param>
            </s:fielderror>                            
        </span>
    </div>
</div>
<div class="form-group interventional">        
    <label class="col-xs-4 control-label" for="trialDTO.secondaryPurposes"><fmt:message key="submit.trial.secondaryPurpose"/></label>
    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.service.util.PAServiceUtils@getSecondaryPurposeList()" />
    <div class="col-xs-4">
        <s:select id ="trialDTO.secondaryPurposes" name="trialDTO.secondaryPurposes" list="#typeCodeValues"  
            cssClass="form-control" headerKey="" headerValue="--Select--" onchange="displaySecondaryPurposeOtherCode(this);" 
            value="trialDTO.secondaryPurposes"/>
    </div>
</div>
<div class="form-group" id="secondaryPurposeOtherTextDiv" style="display:'none'">
    <label class="col-xs-4 control-label" for="trialDTO.secondaryPurposeOtherText"><fmt:message key="submit.trial.secOtherPurposeText"/></label>
    <div class="col-xs-4">
        <s:textarea id="trialDTO.secondaryPurposeOtherText" name="trialDTO.secondaryPurposeOtherText"  cols="50" rows="2" 
            maxlength="1000" cssClass="form-control charcounter" /><br/>
        <span class="alert-danger"> 
            <s:fielderror>
                <s:param>trialDTO.secondaryPurposeOtherText</s:param>
            </s:fielderror>                            
        </span>
    </div>
</div>

<s:hidden name="accrualDiseaseTerminologyEditable" id="accrualDiseaseTerminologyEditable"/>
<s:if test="accrualDiseaseTerminologyEditable">
<div class="form-group">
    <s:if test="%{trialAction == 'amend'}">
        <label class="col-xs-4 control-label" for="trialDTO.accrualDiseaseCodeSystem">
            <fmt:message key="submit.trial.accrual.disease.term"/>
        </label>
    </s:if>
    <s:else>
        <label class="col-xs-4 control-label" for="trialDTO.accrualDiseaseCodeSystem">
            <fmt:message key="submit.trial.accrual.disease.term"/><span class="required">*</span>
        </label>
    </s:else>
    <div class="col-xs-4">
        <s:if test="%{trialAction == 'amend'}">
            <s:select id ="trialDTO.accrualDiseaseCodeSystem" name="trialDTO.accrualDiseaseCodeSystem"
                      cssClass="form-control" list="accrualDiseaseTerminologyList"
                      value="trialDTO.accrualDiseaseCodeSystem"/>
        </s:if>
        <s:else>
            <s:select id ="trialDTO.accrualDiseaseCodeSystem" name="trialDTO.accrualDiseaseCodeSystem"
                      cssClass="form-control" headerKey="" headerValue="--Select--" list="accrualDiseaseTerminologyList"
                      value="trialDTO.accrualDiseaseCodeSystem"/>
        </s:else>
        <span class="alert-danger"> 
            <s:fielderror>
                <s:param>trialDTO.accrualDiseaseCodeSystem</s:param>
            </s:fielderror>
        </span>
    </div>
</div>
</s:if>
<s:else>
    <s:hidden name="trialDTO.accrualDiseaseCodeSystem"/>
</s:else>

<div class="form-group non-interventional">
    <label class="col-xs-4 control-label" for="trialDTO.studyModelCode"><fmt:message key="submit.trial.studyModelCode"/><span class="required">*</span></label>
    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudyModelCode@getDisplayNames()" />
    <div class="col-xs-4">
        <s:select headerKey="" headerValue="--Select--" id ="trialDTO.studyModelCode" name="trialDTO.studyModelCode" list="#typeCodeValues"  
            cssClass="form-control" onchange="displayStudyModelOtherTextDiv();" value="trialDTO.studyModelCode"/>
        <span class="alert-danger">
            <s:fielderror>
                <s:param>trialDTO.studyModelCode</s:param>
            </s:fielderror>
        </span>
    </div>
</div>
<div class="form-group" id="studyModelOtherTextDiv" style="display:none;">
    <label class="col-xs-4 control-label" for="trialDTO.studyModelOtherText"><fmt:message key="submit.trial.studyModelOtherText"/></label>
    <div class="col-xs-4">
        <s:textarea id="trialDTO.studyModelOtherText" name="trialDTO.studyModelOtherText"  cols="50" rows="2" 
            maxlength="200" cssClass="form-control charcounter" /><br/>
        <span class="info">Required if Study Model equals &#39;Other&#39;</span>
        <span class="alert-danger"> 
            <s:fielderror>
                <s:param>trialDTO.studyModelOtherText</s:param>
            </s:fielderror>                            
        </span>
    </div>
</div>
<div class="form-group non-interventional">        
    <label class="col-xs-4 control-label" for="trialDTO.timePerspectiveCode"><fmt:message key="submit.trial.timePerspectiveCode"/><span class="required">*</span></label>                    
    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.TimePerspectiveCode@getDisplayNames()" />
    <div class="col-xs-4">
        <s:select headerKey="" headerValue="--Select--" id ="trialDTO.timePerspectiveCode" 
            name="trialDTO.timePerspectiveCode" list="#typeCodeValues" 
            cssClass="form-control" onchange="displayTimePerspectiveOtherTextDiv();" value="trialDTO.timePerspectiveCode"/>
        <span class="alert-danger">
            <s:fielderror>
                <s:param>trialDTO.timePerspectiveCode</s:param>
            </s:fielderror>
        </span>
    </div>   
</div>
<div class="form-group" id="timePerspectiveOtherTextDiv" style="display:none;">
    <label class="col-xs-4 control-label" for="trialDTO.timePerspectiveOtherText"><fmt:message key="submit.trial.timePerspectiveOtherText"/></label>
    <div class="col-xs-4">
        <s:textarea id="trialDTO.timePerspectiveOtherText" name="trialDTO.timePerspectiveOtherText"  cols="50" rows="2"  
            maxlength="200" cssClass="form-control charcounter" /><br/>
        <span class="info">Required if Time Perspective equals &#39;Other&#39;</span>
        <span class="alert-danger"> 
            <s:fielderror>
                <s:param>trialDTO.timePerspectiveOtherText</s:param>
            </s:fielderror>                            
        </span>
    </div>
</div>          
<SCRIPT LANGUAGE="JavaScript">

displayPrimaryPurposeOtherCode(null);
displaySecondaryPurposeOtherCode($('trialDTO.secondaryPurposes'));

function displayPrimaryPurposeOtherCode(el) {
	if (el==null) {
		el = $('trialDTO.primaryPurposeCode');
		if (el.disabled==true) {
			el = $('trialDTO.primaryPurposeCode2');
		}
	}
    if (el.value == 'Other') {
        $('purposeOtherTextDiv').show();
        document.getElementById('trialDTO.primaryPurposeOtherText').disabled = false;
        document.getElementById('trialDTO.primaryPurposeAdditionalQualifierCode').value = 'Other';
    } else {
        $('purposeOtherTextDiv').hide();
        document.getElementById('trialDTO.primaryPurposeOtherText').disabled = true;
        document.getElementById('trialDTO.primaryPurposeAdditionalQualifierCode').value = null;
    }
}

function displaySecondaryPurposeOtherCode(el) { 
	if (el==null) {
		el = $('trialDTO.secondaryPurposes');
	}
	if (el!=null) {
	    if (el.value == 'Other') {
	        $('secondaryPurposeOtherTextDiv').show();
	        document.getElementById('trialDTO.secondaryPurposeOtherText').disabled = false;        
	    } else {
	        $('secondaryPurposeOtherTextDiv').hide();
	        document.getElementById('trialDTO.secondaryPurposeOtherText').disabled = true;        
	    }
	}
}

displayStudyModelOtherTextDiv();
function displayStudyModelOtherTextDiv() {
    if ($('trialDTO.studyModelCode').value == 'Other') {
        $('studyModelOtherTextDiv').show();
        document.getElementById('trialDTO.studyModelOtherText').disabled = false;        
    } else {
        $('studyModelOtherTextDiv').hide();
        document.getElementById('trialDTO.studyModelOtherText').disabled = true;        
    }	
}

displayTimePerspectiveOtherTextDiv();
function displayTimePerspectiveOtherTextDiv() {
    if ($('trialDTO.timePerspectiveCode').value == 'Other') {
        $('timePerspectiveOtherTextDiv').show();
        document.getElementById('trialDTO.timePerspectiveOtherText').disabled = false;        
    } else {
        $('timePerspectiveOtherTextDiv').hide();
        document.getElementById('trialDTO.timePerspectiveOtherText').disabled = true;        
    }   
}

</SCRIPT>

          