<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <div class="accordion">
     <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section1"><fmt:message key="view.trial.trialIDs"/><span class="required">*</span></a></div>
     <div id="section1" class="accordion-body in">
       <div class="container">
         <div class="form-group">
             <label for="trialDTO.leadOrgTrialIdentifier"  class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
             <div class="col-xs-4">
                 <s:textfield id="trialDTO.leadOrgTrialIdentifier" name="trialDTO.leadOrgTrialIdentifier" maxlength="30" cssClass="form-control charcounter"/>
             	 <span class="alert-danger">
             	 	<s:fielderror>
             	 		<s:param>trialDTO.leadOrgTrialIdentifier</s:param>
             	 	</s:fielderror>
             	 </span>
             </div>
             <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.trial_id" />"  data-placement="top" data-trigger="hover"></i></div>
         </div>
         <div class="form-group">
            <label for="trialDTO.nctIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.nctNumber"/></label>
            <div class="col-xs-4">
                <s:textfield id="trialDTO.nctIdentifier" name="trialDTO.nctIdentifier"  maxlength="200"  cssClass="form-control"/>
                <span class="alert-danger">
                    <s:fielderror cssStyle = "white-space:pre-line;">
                        <s:param>trialDTO.nctIdentifier</s:param>
                    </s:fielderror>
                </span>
            </div>
            <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.nct_number" />"  data-placement="top" data-trigger="hover"></i> </div>
         </div>
     </div>
 </div>
           
