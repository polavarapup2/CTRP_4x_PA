<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section13">Regulatory Information <span class="required">*</span></a></div>
    <div id="section13" class="accordion-body in">
        <div class="container">
            <s:hidden id="lastUpdatedDate" name="trialDTO.lastUpdatedDate"> </s:hidden>
            <s:hidden id="msId" name="trialDTO.id"> </s:hidden>
            <div class="form-group">

                <div class="form-group">
                    <label for="trialDTO.fdaRegulatedDrug"  class="col-xs-4 control-label"> <fmt:message key="regulatory.drug.product"/></label>
                    <div class="col-xs-8">
                        <s:select id="drug" name="trialDTO.fdaRegulatedDrug" list="#{'':'', 'false':'No', 'true':'Yes'}" />

                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.fdaRegulatedDrug"/>" data-placement="top" data-trigger="hover"></i>
                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.fdaRegulatedDrug</s:param></s:fielderror></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="trialDTO.fdaRegulatedDevice"  class="col-xs-4 control-label"> <fmt:message key="regulatory.device.product"/></label>
                    <div class="col-xs-8">
                        <s:select id="device" name="trialDTO.fdaRegulatedDevice" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkDeviceDropDown();"/>

                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.fdaRegulatedDevice"/>" data-placement="top" data-trigger="hover"></i>
                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.fdaRegulatedDevice</s:param></s:fielderror></span>
                    </div>
                </div>
                <!--   Delayed Posting Indicator-->
                <div class="form-group" id="delpostindrow" style="display: none">
                    <label for="trialDTO.delayedPostingIndicator"  class="col-xs-4 control-label"> <fmt:message key="regulatory.delayed.posting.ind"/></label>
                    <div class="col-xs-8">
                        <s:hidden name="trialDTO.delayedPostingIndicator" value="false"/>
                        <c:out value="No"/>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.delayed_posting_indicator"/>" data-placement="top" data-trigger="hover"></i>
                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.delayedPostingIndicator</s:param></s:fielderror></span>
                        <span style="color:gray">To modify this indicator's value please submit a request to the CTRO at <a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov</a></span>
                    </div>
                </div>

            <div class="form-group" id="approvalRow" style="display: none">
                <label for="trialDTO.postPriorToApproval"  class="col-xs-4 control-label"> <fmt:message key="regulatory.approval.clearance"/></label>
                <div class="col-xs-8">
                    <s:select id="approval" name="trialDTO.postPriorToApproval" list="#{'':'', 'false':'No', 'true':'Yes'}"/>
                    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.postPriorToApproval"/>" data-placement="top" data-trigger="hover"></i>
                    <span class="alert-danger"><s:fielderror><s:param>trialDTO.postPriorToApproval</s:param></s:fielderror></span>
                </div>
            </div>

                <div class="form-group" id="survRow" style="display: none">
                    <label for="trialDTO.pedPostmarketSurv"  class="col-xs-4 control-label"> <fmt:message key="regulatory.market.surveillance"/></label>
                    <div class="col-xs-8">
                        <s:select id="surveillance" name="trialDTO.pedPostmarketSurv" list="#{'':'', 'false':'No', 'true':'Yes'}"/>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.pedPostmarketSurv"/>" data-placement="top" data-trigger="hover"></i>
                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.pedPostmarketSurv</s:param></s:fielderror></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="trialDTO.exportedFromUs"  class="col-xs-4 control-label"> <fmt:message key="regulatory.usa.export"/></label>
                    <div class="col-xs-8">
                        <s:select id="export" name="trialDTO.exportedFromUs" list="#{'':'', 'false':'No', 'true':'Yes'}"/>

                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.exportedFromUs</s:param></s:fielderror></span>
                    </div>
                </div>


                <!--   FDA Regulated Intervention Indicator-->

                <div class="form-group">
                    <label for="trialDTO.fdaRegulatoryInformationIndicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span></label>
                    <div class="col-xs-4">
                        <s:select id="fdaindid" name="trialDTO.fdaRegulatoryInformationIndicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}" onchange="checkFDADropDown();"/>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.fda_regulated_intervention_indicator"/>" data-placement="top" data-trigger="hover"></i>
                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.fdaRegulatoryInformationIndicator</s:param></s:fielderror></span>
                    </div>
                </div>
                <!--   Section 801 Indicator-->
                <div class="form-group" id="sec801row" style="display: none">
                    <label for="trialDTO.section801Indicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></label>
                    <div class="col-xs-4">
                        <s:select id="sec801id" name="trialDTO.section801Indicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}"/>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.section_801_indicator"/>" data-placement="top" data-trigger="hover"></i>
                        <span class="alert-danger"><s:fielderror><s:param>trialDTO.section801Indicator</s:param></s:fielderror></span>
                    </div>
                </div>


                <!--   Data Monitoring Committee Appointed Indicator -->
                <div class="form-group" id="datamonrow">
                    <label for="trialDTO.dataMonitoringCommitteeAppointedIndicator"  class="col-xs-4 control-label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></label>
                    <div class="col-xs-4">
                        <s:select id="datamonid" name="trialDTO.dataMonitoringCommitteeAppointedIndicator" list="#{'':'', 'No':'No', 'Yes':'Yes'}"/>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.data_monitoring_committee_appointed_indicator"/>" data-placement="top" data-trigger="hover"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>