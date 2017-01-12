<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="submit.trial.leadOrgInvestigator"/><span class="required">*</span></a></div>
    <div id="section4" class="accordion-body in">
        <div class="container">
         <div class="form-group">                                
             <label for="trialDTO.leadOrganizationNameField" class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label>
             <div id="loadOrgField">
                 <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
             </div>
                <div class="col-xs-10">
                            <div align="center"  id ="programCodesLoad"  style="display:none" >
                                <img  src="${pageContext.request.contextPath}/images/loading.gif"
                                />
                                &nbsp;Loading...
                                </div>
                                
                                
                         </div>
         </div>                
         <div class="form-group">
             <label for="trialDTO.piName" class="col-xs-4 control-label"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label>
             <div id="loadPersField">
                 <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadPrincipalInvestigator.jsp" %>
             </div>              
         </div>
    	</div>
	</div>
</div>
