 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section13">Regulatory Information <span class="required">*</span></a></div>
  <div id="section13" class="accordion-body in">
    <div class="container">
        <div class="form-group">
           <label for="countries"  class="col-xs-4 control-label"><fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span> </label>
           <div class="col-xs-4">
           		<s:select id="countries" headerValue="-Select-" headerKey=""
                      name="trialDTO.lst"
                      list="trialDTO.countryList"
                      listKey="id" listValue="name"
                      onchange="loadRegAuthoritiesDiv();"
                      cssClass="form-control"
                      />
                    <span class="alert-danger">
                       <s:fielderror>
                       <s:param>trialDTO.lst</s:param>
                      </s:fielderror>
                    </span>
           </div>
           <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.oversight_authority_organization_country"/>" data-placement="top" data-trigger="hover"></i>
        </div>
        <div class="form-group">
           <label for="trialDTO.selectedRegAuth"  class="col-xs-4 control-label"><fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span></label>
           <div id="loadAuthField" class="col-xs-4">
                <%@ include file="/WEB-INF/jsp/nodecorate/oversightAuthInfo.jsp" %>
                <span class="alert-danger"> 
			        <s:fielderror>
			        <s:param>trialDTO.selectedRegAuth</s:param>
			        </s:fielderror>                            
			   </span>
           </div>
           <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.oversight_authority_organization_name"/>" data-placement="top" data-trigger="hover"></i>
     	</div>

 <!--   FDA Regulated Intervention Indicator-->
     <div class="form-group">
          <label for="trialDTO.fdaRegulatoryInformationIndicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span></label>
          <div class="col-xs-4">
          	<s:radio cssClass="radio-inline" id ="trialDTO.fdaRegulatoryInformationIndicator" name="trialDTO.fdaRegulatoryInformationIndicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkFDADropDown();" value="trialDTO.fdaRegulatoryInformationIndicator"/>
       		<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.fda_regulated_intervention_indicator"/>" data-placement="top" data-trigger="hover"></i> 
     		<span class="alert-danger"><s:fielderror><s:param>trialDTO.fdaRegulatoryInformationIndicator</s:param></s:fielderror></span>
     	</div>
      </div>
     <!--   Section 801 Indicator-->
     <div class="form-group" id="sec801row">
        <label for="trialDTO.section801Indicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></label>
        <div class="col-xs-4">
            <s:radio cssClass="radio-inline" id="trialDTO.section801Indicator" name="trialDTO.section801Indicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkSection108DropDown();" value="trialDTO.section801Indicator"/>
         	<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.section_801_indicator"/>" data-placement="top" data-trigger="hover"></i>
     		<span class="alert-danger"><s:fielderror><s:param>trialDTO.section801Indicator</s:param></s:fielderror></span>
     	</div>
     </div>

     <!--   Delayed Posting Indicator-->
     <div class="form-group" id="delpostindrow" style="display: none">
          <label for="trialDTO.delayedPostingIndicator"  class="col-xs-4 control-label"> <fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span></label>
          <div class="col-xs-8">
            <s:radio cssClass="radio-inline" id="trialDTO.delayedPostingIndicator" name="trialDTO.delayedPostingIndicator" list="#{'No':'No', 'Yes':'Yes'}" value="trialDTO.delayedPostingIndicator" disabled="true"/>
         	<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.delayed_posting_indicator"/>" data-placement="top" data-trigger="hover"></i>
     		<span class="alert-danger"><s:fielderror><s:param>trialDTO.delayedPostingIndicator</s:param></s:fielderror></span>
     		<span style="color:gray">To modify this indicator's value please submit a request to the CTRO at <a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov</a></span>
     	</div>
     </div>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <div class="form-group" id="datamonrow">
             <label for="trialDTO.dataMonitoringCommitteeAppointedIndicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></label>
         <div class="col-xs-4">
            <s:radio cssClass="radio-inline" id="trialDTO.dataMonitoringCommitteeAppointedIndicator" name="trialDTO.dataMonitoringCommitteeAppointedIndicator" list="#{'No':'No', 'Yes':'Yes'}" value="trialDTO.dataMonitoringCommitteeAppointedIndicator" />
        	<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.data_monitoring_committee_appointed_indicator"/>" data-placement="top" data-trigger="hover"></i>
        </div>
      </div>
	</div>
  </div>
</div>