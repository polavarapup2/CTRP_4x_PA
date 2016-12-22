<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
<s:hidden name="trialDTO.sponsorName" id="trialDTO.sponsorName" />
<s:hidden name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" />
<s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
<s:hidden name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName"/>
<s:hidden name="trialDTO.responsiblePersonTitle" id="trialDTO.responsiblePersonTitle"/>
<s:hidden name="trialDTO.responsiblePersonAffiliationOrgName" id="trialDTO.responsiblePersonAffiliationOrgName"/>
<s:hidden name="trialDTO.responsiblePersonAffiliationOrgId" id="trialDTO.responsiblePersonAffiliationOrgId"/>        

<c:if test="${trialDTO.xmlRequired == true}">
	<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="view.trial.sponsorResParty"/><span class="required">*</span></a></div>
	<div id="section4" class="accordion-body in">
	<div class="container">
		
		<div class="form-group">
			<label class="col-xs-4 control-label ro-field-label"> 
		        <fmt:message key="view.trial.sponsor"/><span class="required">*</span>
		    </label>
		    <div class="col-xs-4">
		        <s:property value="trialDTO.sponsorName"/>
		    </div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label ro-field-label"> 
		        <fmt:message key="view.trial.respParty"/><span class="required">*</span>
		    </label>
		    <div class="col-xs-4">
		        <s:property value="trialDTO.responsiblePartyTypeReadable"/>
		    </div>
		</div>
		 <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
          <div class="form-group">
			<label class="col-xs-4 control-label ro-field-label"> 
		        <fmt:message key="view.trial.respParty.investigator"/><span class="required">*</span>
		    </label>
		    <div class="col-xs-4">
		        <s:property value="trialDTO.responsiblePersonName"/>
		    </div>
		</div>
      </c:if> 
      <c:if test="${fn:trim(trialDTO.responsiblePersonTitle) != ''}">
          <div class="form-group">
			<label class="col-xs-4 control-label ro-field-label"> 
		        <fmt:message key="view.trial.respParty.investigatorTitle"/><span class="required">*</span>
		    </label>
		    <div class="col-xs-4">
		        <s:property value="trialDTO.responsiblePersonTitle"/>
		    </div>
		</div>
      </c:if>
      <c:if test="${fn:trim(trialDTO.responsiblePersonAffiliationOrgName) != ''}">
          <div class="form-group">
			<label class="col-xs-4 control-label ro-field-label"> 
		        <fmt:message key="view.trial.respParty.investigatorAff"/><span class="required">*</span>
		    </label>
		    <div class="col-xs-4">
		        <s:property value="trialDTO.responsiblePersonAffiliationOrgName"/>
		    </div>
		</div>
      </c:if>
    
	</div>
	</div>
	</div>                                  
</c:if>