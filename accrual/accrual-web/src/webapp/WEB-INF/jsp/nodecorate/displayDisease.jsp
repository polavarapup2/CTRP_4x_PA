<%@ taglib prefix="s" uri="/struts-tags"%> 
<div class="col-xs-3">
  <s:textfield id ="disease" readonly="true" name="patient.diseasePreferredName" cssClass="form-control"/> 
  <s:hidden name="patient.diseaseIdentifier"/>
  
  </div>
        <div class="col-xs-3">
          <button type="button" class="btn btn-icon btn-default" onclick="lookup('false');"><i class="fa-search"></i>Look Up</button>
          <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="If you are using ICD-O-3 terminology then Disease is optional (refer to the note above). If you are not using ICD-O-3, then Disease is mandatory."  data-placement="top" data-trigger="hover"></i>
        </div>