<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/nodecorate/phase.jsp" %>
<div class="form-group">
    <label for="" class="col-xs-4 control-label"><fmt:message key="submit.trial.type"/><span class="required">*</span></label>
    <div class="col-xs-4">
      <input  class="radio-inline" type="radio" name="trialDTO.trialType" value="Interventional" id="trialDTO.trialType.Interventional"
            ${trialDTO.trialType!='NonInterventional'?'checked=checked':''}        
            onclick="hidePrimaryCompletionDate(), setDisplayBasedOnTrialType();">
      <label  class="radio-inline" for = "trialDTO.trialType.Interventional">Interventional</label>
        <input  class="radio-inline" type="radio" name="trialDTO.trialType" value="NonInterventional" id="trialDTO.trialType.Noninterventional"
            ${trialDTO.trialType=='NonInterventional'?'checked=checked':''}
            onclick="hidePrimaryCompletionDate(), setDisplayBasedOnTrialType();">
        <label  class="radio-inline" for = "trialDTO.trialType.Noninterventional">Non-interventional</label>
        <span class="alert-danger">
            <s:fielderror>
                <s:param>trialDTO.trialType</s:param>
            </s:fielderror>
        </span>
    </div>
</div>
<div class="non-interventional form-group ">
    <label for="trialDTO.studySubtypeCode" class="col-xs-4 control-label"><fmt:message key="submit.trial.studySubtypeCode"/><span class="required">*</span></label>
    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />
    <div class="col-xs-4">
        <s:select headerKey="" headerValue="--Select--" id ="trialDTO.studySubtypeCode" name="trialDTO.studySubtypeCode" list="#typeCodeValues" 
            cssClass="form-control" value="trialDTO.studySubtypeCode"/>
           <span class="alert-danger">
              <s:fielderror>
              <s:param>trialDTO.studySubtypeCode</s:param>
             </s:fielderror>
           </span>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>