<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <div class="form-group">
      <label for="trialDTO.phaseCode" class="col-xs-4 control-label"><fmt:message key="submit.trial.phase"/><span class="required">*</span></label>
      <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
      <div class="col-xs-4">
         <s:select headerKey="" headerValue="--Select--" name="trialDTO.phaseCode" id ="trialDTO.phaseCode" 
             list="#phaseCodeValues" cssClass="form-control"  value="trialDTO.phaseCode" onchange="displayPhaseAdditonalCode();"/>
         <span class="alert-danger">
             <s:fielderror>
             <s:param>trialDTO.phaseCode</s:param>
             </s:fielderror>
         </span>
      </div>
      <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.phase"/>" data-placement="top" data-trigger="hover"></i></div>
 </div>
 <div class="form-group" id ="phaseOtherDiv" style="display:'none'">
     <label for="trialDTO.phaseAdditionalQualifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherPhaseText"/></label>
     <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
     <div class="col-xs-4">
         <s:select id="trialDTO.phaseAdditionalQualifier" headerKey="" headerValue="No" name="trialDTO.phaseAdditionalQualifier" list="#phaseAdditionlQualiefierCodeValues" 
                        value="trialDTO.phaseAdditionalQualifier" cssClass="form-control"/>
     </div>
 </div> 
<SCRIPT LANGUAGE="JavaScript">
displayPhaseAdditonalCode();
function displayPhaseAdditonalCode(){
        if ($('trialDTO.phaseCode').value == 'NA') {
            $('phaseOtherDiv').show();
            document.getElementById('trialDTO.phaseAdditionalQualifier').disabled = false;
        } else {
            $('phaseOtherDiv').hide();
            document.getElementById('trialDTO.phaseAdditionalQualifier').disabled = true;
        }
    }
</SCRIPT>
          