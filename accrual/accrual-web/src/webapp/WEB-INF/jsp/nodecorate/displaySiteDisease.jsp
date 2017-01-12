<%@ taglib prefix="s" uri="/struts-tags"%> 
<div class="col-xs-3">
  <s:textfield id ="sitedisease" readonly="true" name="patient.siteDiseasePreferredName" cssClass="form-control"/> 
  <s:hidden name="patient.siteDiseaseIdentifier"/>
  
  </div>
        <div class="col-xs-3" style="padding-left: 0">
          <button type="button" class="btn btn-icon btn-default" onclick="lookup('true');"><i class="fa-search"></i>Look Up</button>
          <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Site applies ONLY if ICD-O-3 disease terminology is being used. Either Site or Disease is required for ICD-O-3."  data-placement="top" data-trigger="hover"></i>       
         </div> 