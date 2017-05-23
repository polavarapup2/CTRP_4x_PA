<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section10">Regulatory Information<span class="required">*</span></a></div>
<div id="section10" class="accordion-body in">
<div class="container">
<%-- <div class="form-group">
	<label class="col-xs-4 control-label ro-field-label">
        <fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.lst"/>
        <s:property value="trialDTO.regAuthorityCountry.name"/>
     </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.selectedRegAuth"/>
        <s:property value="trialDTO.regAuthorityOrg.name"/>
    </div>
</div> --%>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.drug.product"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.fdaRegulatedDrug"/>
        <s:property value="trialDTO.fdaRegulatedDrug"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.device.product"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.fdaRegulatedDevice"/>
        <s:property value="trialDTO.fdaRegulatedDevice"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.delayedPostingIndicator"/>
        <s:property value="trialDTO.delayedPostingIndicator"/>
  </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.approval.clearance"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.postPriorToApproval"/>
        <s:property value="trialDTO.postPriorToApproval"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.market.surveillance"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.pedPostmarketSurv"/>
        <s:property value="trialDTO.pedPostmarketSurv"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.usa.export"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.exportedFromUs"/>
        <s:property value="trialDTO.exportedFromUs"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.fdaRegulatoryInformationIndicator"/>
        <s:property value="trialDTO.fdaRegulatoryInformationIndicator"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.section801.ind"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.section801Indicator"/>
        <s:property value="trialDTO.section801Indicator"/>
   </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></label>
    <div class="col-xs-4">
        <s:hidden name="trialDTO.dataMonitoringCommitteeAppointedIndicator"/>
        <s:property value="trialDTO.dataMonitoringCommitteeAppointedIndicator"/>
    </div>
</div>
</div>
</div>
</div>