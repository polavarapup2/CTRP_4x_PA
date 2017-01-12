<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
<div class="accordion">
<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section6"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a></div>
<div id="section6" class="accordion-body in">
<div class="container">
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label">Data Table 4 Funding Sponsor Type:<span class="required">*</span></label>
    <div class="col-xs-4">
        <s:property value="trialDTO.summaryFourFundingCategoryCode"/>
    </div>
</div>
<div class="form-group">
    <label class="col-xs-4 control-label ro-field-label">Data Table 4 Funding Sponsor: <span class="required">*</span></label>
    <div class="col-xs-4">    
        <s:iterator value="trialDTO.summaryFourOrgIdentifiers" id="trialDTO.summaryFourOrgIdentifiers" status="stat">
            <s:property value="%{orgName}"/><br/>
            <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="<c:out value="${orgId}" />"/>
            <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="<c:out value="${orgName}" />"/>
        </s:iterator>
    </div>
</div>
<div class="form-group" id="programCodeBlock" style="display:none">
    <label class="col-xs-4 control-label ro-field-label"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
    <div class="col-xs-4">
                    <s:select size="2" multiple="true"  name="trialDTO.programCodesList" id="programCodesValues"  list="#{trialDTO.programCodesMap}"
                        cssClass="form-control"  />
     </div>                   
     <div class="col-xs-4" >
                               <c:if test="${sessionScope.isSiteAdmin}">
                                     <a  style ="vertical-align:middle!important" href="../siteadmin/programCodesexecute.action">Manage Program Codes</a>
                                     <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.summary_4_program_code" />"  data-placement="top" data-trigger="hover"></i>
                                 </c:if>
                                  <c:if test="${sessionScope.isSiteAdmin==false}">
                                   <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.summary_4_program_code" />"  data-placement="top" data-trigger="hover"></i>
                                  </c:if>
                                </div>  
</div>
</div>
</div>
</div>
 
