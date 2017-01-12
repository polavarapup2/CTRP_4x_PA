<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:hidden name="trialDTO.leadOrgTrialIdentifier" id="trialDTO.leadOrgTrialIdentifier"/>
<s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
<s:hidden name="trialDTO.ctepIdentifier" id="trialDTO.ctepIdentifier"/>
<s:hidden name="trialDTO.dcpIdentifier" id="trialDTO.dcpIdentifier"/>
<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section1"><fmt:message key="view.trial.trialIDs"/><span class="required">*</span></a></div>
	<div id="section1" class="accordion-body in">
	<div class="container">
	<div class="form-group">
       <label class="col-xs-4 control-label ro-field-label"> <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
       <div class="col-xs-4">
       	<s:property value="trialDTO.leadOrgTrialIdentifier"/>
       </div>
    </div>
    <div class="form-group">
		<label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.nctNumber"/></label>
		<div class="col-xs-4">
        	<%@ include file="/WEB-INF/jsp/nodecorate/addNctIdentifier.jsp" %>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-4 control-label ro-field-label"><fmt:message key="view.trial.identifier"/></label>
		<div class="col-xs-4">
        	<s:property value="trialDTO.assignedIdentifier"/>
		</div>
	</div>
    
	<c:if test="${trialDTO.ctepIdentifier != null }">
	    <div class="form-group">
	        <label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.ctepIdentifier"/></label>
			<div class="col-xs-4">
	            <s:property value="trialDTO.ctepIdentifier"/>
	        </div>
	    </div>
	</c:if>
	<c:if test="${trialDTO.dcpIdentifier!= null}">
     	<div class="form-group">
	        <label class="col-xs-4 control-label ro-field-label"><fmt:message key="submit.trial.dcpIdentifier"/></label>
			<div class="col-xs-4">
            	<s:property value="trialDTO.dcpIdentifier"/>
            </div>
        </div>
	</c:if>
	</div>
	</div>
</div>