<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%@ include file="/WEB-INF/jsp/nodecorate/trialStatusHistory.jsp" %>

<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section8_2">Trial Dates<span class="required">*</span></a></div>
    <div id="section8_2" class="accordion-body in">
        <div class="container">       
             <div class="form-group">
                <label for="trialDTO_startDate" class="col-xs-4 control-label"><fmt:message key="update.trial.trialStartDate"/><span class="required">*</span></label>
                <s:set name="dateTypeList" value="#{'Actual':'Actual','Anticipated':'Anticipated'}" />
                <div class="col-xs-2">
                  <div id="datetimepicker" class="datetimepicker input-append">
                    <s:textfield id="trialDTO_startDate" name="trialDTO.startDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />                    
                    <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                    <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.startDate</s:param>
                      </s:fielderror>
                  </span>
                 </div>                 
                </div>
                <div class="col-xs-5">
                  <s:radio cssClass="radio-inline" id="trialDTO_startDateType" name="trialDTO.startDateType" list="#dateTypeList" />                 
                  <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.trial_start_date"/>"  data-placement="top" data-trigger="hover"></i>
                  <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.startDateType</s:param>
                      </s:fielderror>
                  </span>   
             </div>
          </div>
          <div class="form-group">
              <label for="trialDTO_primaryCompletionDate" class="col-xs-4 control-label"><fmt:message key="update.trial.primaryCompletionDate"/>
              <c:set var="asterix" value="true"/>
              <c:if test="${trialDTO.trialType == 'NonInterventional' && trialDTO.xmlRequired == false}">
                  <c:set var="asterix" value="false"/>
              </c:if>
              <c:if test="${asterix}"><span class="required" id="primaryCompletionDateId">*</span></c:if>
              </label>
               <div class="col-xs-2">
                   <div id="datetimepicker" class="datetimepicker input-append">
                       <s:textfield id="trialDTO_primaryCompletionDate" name="trialDTO.primaryCompletionDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />
                       <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                       <span class="alert-danger">
                          <s:fielderror>
                             <s:param>trialDTO.primaryCompletionDate</s:param>
                         </s:fielderror>
                      </span>
                   </div>               
               </div>               
               <div class="col-xs-5">
                   <s:radio cssClass="radio-inline" id="trialDTO_primaryCompletionDateType" name="trialDTO.primaryCompletionDateType" list="#dateTypeList" />                   
                   <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.primary_completion_date"/>"  data-placement="top" data-trigger="hover"></i> 
                    <span class="alert-danger">
                       <s:fielderror>
                           <s:param>trialDTO.primaryCompletionDateType</s:param>
                       </s:fielderror>
                   </span>
               </div>
          </div>
          <div class="form-group">
              <label for="trialDTO_completionDate" class="col-xs-4 control-label"><fmt:message key="update.trial.completionDate"/></label>
              <div class="col-xs-2">
                  <div id="datetimepicker" class="datetimepicker input-append">
                    <s:textfield id="trialDTO_completionDate" name="trialDTO.completionDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                    <span class="add-on btn-default"><i class="fa-calendar"></i></span>                 
                    <span class="alert-danger">
                      <s:fielderror>
                          <s:param>trialDTO.completionDate</s:param>
                      </s:fielderror>
                    </span>
                  </div>
              </div>
              <div class="col-xs-5">
                  <s:radio  cssClass="radio-inline" id="trialDTO_completionDateType" name="trialDTO.completionDateType" list="#dateTypeList" />
                  <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.completion_date" />"  data-placement="top" data-trigger="hover"></i>
                  <span class="alert-danger">
                     <s:fielderror>
                         <s:param>trialDTO.completionDateType</s:param>
                     </s:fielderror>
                  </span>
                  </div>
           </div>
          <span class="info">Please refer to <a href="https://wiki.nci.nih.gov/x/l4CNC" target="newPage">Trial Status Rules for Start and Completion dates</a>.</span>
        </div>
    </div>
</div>