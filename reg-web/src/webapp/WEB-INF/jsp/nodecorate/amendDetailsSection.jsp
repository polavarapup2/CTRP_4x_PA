<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a>
    </div>
        <div id="section3" class="accordion-body in">
            <div class="container">
                <div class="form-group">
                <label for="trialDTO.officialTitle" class="col-xs-4 control-label"><fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                    <div class="col-xs-4">
                     <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="form-control charcounter"/>
                     <span class="alert-danger">
                         <s:fielderror>
                             <s:param>trialDTO.officialTitle</s:param>
                         </s:fielderror>
                     </span>
                   </div>
                     <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.title"/>"  data-placement="top" data-trigger="hover"></i>
           </div>
            <%@ include file="/WEB-INF/jsp/nodecorate/phasePurpose.jsp" %>
        </div>
    </div>
</div>
