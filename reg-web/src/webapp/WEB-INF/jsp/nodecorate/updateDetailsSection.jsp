<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:hidden name="trialDTO.phaseCode" id="trialDTO.phaseCode"/>
<s:hidden name="trialDTO.phaseAdditionalQualifier" id="trialDTO.phaseAdditionalQualifier"/>
<s:hidden name="trialDTO.trialType" id="trialDTO.trialType"/>
<s:hidden id="trialDTO.primaryPurposeCode" name="trialDTO.primaryPurposeCode"/>
<s:hidden id="trialDTO.primaryPurposeAdditionalQualifierCode" name="trialDTO.primaryPurposeAdditionalQualifierCode"/>
<s:hidden id="trialDTO.secondaryPurposesAsString" name="trialDTO.secondaryPurposesAsString"/>                
<s:hidden id="trialDTO.secondaryPurposeOtherText" name="trialDTO.secondaryPurposeOtherText"/>
<s:hidden id="trialDTO.studySubtypeCode" name="trialDTO.studySubtypeCode"/>
<s:hidden id="trialDTO.studyModelCode" name="trialDTO.studyModelCode"/>
<s:hidden id="trialDTO.studyModelOtherText" name="trialDTO.studyModelOtherText"/>
<s:hidden id="trialDTO.timePerspectiveCode" name="trialDTO.timePerspectiveCode"/>
<s:hidden id="trialDTO.timePerspectiveOtherText" name="trialDTO.timePerspectiveOtherText"/>         

<div class="accordion">
<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a></div>
<div id="section3" class="accordion-body in">
<div class="container">
<div class="form-group">
	<label for="submitTrial_protocolWebDTO_trialTitle" class="col-xs-4 control-label"> 
    <fmt:message key="submit.trial.title"/><span class="required">*</span></label>
    <fmt:message key="studyAlternateTitles.text" var="title" />
    <div class="col-xs-4">
        <s:textarea id="submitTrial_protocolWebDTO_trialTitle" name="trialDTO.officialTitle" cssClass="readonly form-control" readonly="true" cols="75" rows="4" />
        <c:if test="${not empty trialDTO.studyAlternateTitles}">
            <a href="javascript:void(0)" title="${title}" onclick="displayStudyAlternateTitles('${trialDTO.identifier}')">(*)</a>                                                   
        </c:if>
    </div>
</div>
<div class="form-group">
	<label class="col-xs-4 control-label ro-field-label">
	<fmt:message key="submit.trial.phase"/><span class="required">*</span></label>
	<div class="col-xs-4">
        <s:property value="trialDTO.phaseCode"/>
    </div>
</div>
<s:if test="trialDTO.phaseCode == 'NA'">
    <div class="form-group">
		<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.otherPhaseText"/></label>
		<div class="col-xs-4">
			<c:out value="${trialDTO.phaseAdditionalQualifier=='Pilot'?'Yes':'No'}"/>
		</div>
    </div>
</s:if>
<div class="form-group">
	<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.type"/><span class="required">*</span></label>
	<div class="col-xs-4">
         <s:property value="trialDTO.trialType"/>
    </div>
</div>
<div class="form-group">
	<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label>
	<div class="col-xs-4">
    	<s:property value="trialDTO.primaryPurposeCode"/>
    </div>
</div>
<s:if test="trialDTO.primaryPurposeCode == 'Other'">
	<div class="form-group">
		<label class="col-xs-4 control-label" for="trialDTO.primaryPurposeOtherText">
        <fmt:message key="submit.trial.otherPurposeText"/></label>
        <div class="col-xs-4">
            <s:textarea id="trialDTO.primaryPurposeOtherText" name="trialDTO.primaryPurposeOtherText" cssClass="readonly form-control" readonly="true" cols="50" rows="2" />
            <span class="info">Required if Purpose equals &#39;Other&#39;</span>
        </div>
    </div>
</s:if>
<s:if test="trialDTO.trialType == 'InterventionalStudyProtocol' || trialDTO.trialType == 'Interventional'">
<div class="form-group">
		<label class="col-xs-4 control-label ro-field-label"><fmt:message key="view.trial.secondaryPurpose"/></label>
        <div class="col-xs-4"><s:property value="trialDTO.secondaryPurposeAsReadableString"/></div>
</div>

<c:if test="${trialDTO.secondaryPurposeAsReadableString=='Other'}">
	<div class="form-group">
		<label class="col-xs-4 control-label ro-field-label">
	            <fmt:message key="view.trial.secOtherPurposeText"/>
	        </label>
	   <div class="col-xs-4">
	        <s:property value="trialDTO.secondaryPurposeOtherText"/>
	    </div>
	</div>
</c:if>
</s:if>

<s:hidden name="accrualDiseaseTerminologyEditable" id="accrualDiseaseTerminologyEditable"/>
<s:if test="accrualDiseaseTerminologyEditable">
<div class="form-group">
    <label class="col-xs-4 control-label" for="trialDTO.accrualDiseaseCodeSystem"><fmt:message key="submit.trial.accrual.disease.term"/></label>
    <div class="col-xs-4">
        <s:select id ="trialDTO.accrualDiseaseCodeSystem" name="trialDTO.accrualDiseaseCodeSystem"
            cssClass="form-control" list="accrualDiseaseTerminologyList"
            value="trialDTO.accrualDiseaseCodeSystem"/>
    </div>
</div>
</s:if>
<s:else>
    <s:hidden name="trialDTO.accrualDiseaseCodeSystem"/>
</s:else>

<s:if test="trialDTO.trialType != 'InterventionalStudyProtocol' && trialDTO.trialType != 'Interventional'">
<div class="form-group">
   <label class="col-xs-4 control-label ro-field-label">
           <fmt:message key="submit.trial.studySubtypeCode"/>
       </label>
   <div class="col-xs-4">
       <s:property value="trialDTO.studySubtypeCode"/>
   </div>
</div>
<div class="form-group">
	<label class="col-xs-4 control-label ro-field-label">
           <fmt:message key="submit.trial.studyModelCode"/>
       </label>
    <div class="col-xs-4">
        <s:property value="trialDTO.studyModelCode"/>
   	</div>
</div>

<s:if test="trialDTO.studyModelCode == 'Other'">
	<div class="form-group">
		<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.studyModelOtherText"/>
	        </label>
	    <div class="col-xs-4">
	        <s:property value="trialDTO.studyModelOtherText"/>
	    </div>
	</div>
</s:if>
<div class="form-group">
	<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.timePerspectiveCode"/>
       </label>
    <div class="col-xs-4">
       <s:property value="trialDTO.timePerspectiveCode"/>
   </div>
</div>

<s:if test="trialDTO.timePerspectiveCode == 'Other'">
   	<div class="form-group">
	<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.timePerspectiveOtherText"/>
           </label>
        <div class="col-xs-4">
           <s:property value="trialDTO.timePerspectiveOtherText"/>
		</div>
	</div>
</s:if>
</s:if>
</div>
</div>
</div>