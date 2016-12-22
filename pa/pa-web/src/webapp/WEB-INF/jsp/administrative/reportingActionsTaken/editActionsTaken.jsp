<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="editActionTakenDialog" title="Add/Edit Actions taken record"
    style="display: none;">
    <s:form method="POST" action="resultsReportingActionsTakenupdate.action"  name="editActionTakenForm" id="editActionTakenForm">
    <table class="form">                
                <tr>
                    <td scope="row" class="label">
                    <label for="comment">
                       <fmt:message key="actionsTaken.comments"/>
                    </label>
                    </td>
    				<td class="value">
              		<s:textarea id="comment" name="studyProcessingErrDto.comment" cssStyle="width:350px" rows="4" maxLength="5000"/>
                    <span class="formErrorMsg"> 
                      <s:fielderror>
                        <s:param>trialDocumentWebDTO.typeCode</s:param>
                      </s:fielderror>                            
                    </span>
    			   </td>         
                </tr>                
                <tr>
                    <td scope="row" class="label">
                    <label for="errorType">
                       <fmt:message key="actionsTaken.errorType"/>
                    </label>
                    </td>
                    <td class="value">
                    <s:textfield id="errorType" name="studyProcessingErrDto.errorType" cssStyle="width:200px" maxLength="50"/>
                    <span class="formErrorMsg"> 
                      <s:fielderror>
                        <s:param>studyProcessingErrDto.errorType</s:param>
                      </s:fielderror>                            
                    </span>
                   </td>         
                </tr>
                <tr>
                    <td scope="row" class="label">
                    <label for="cmsTicketId">
                       <fmt:message key="actionsTaken.cmsTicketID"/>
                    </label>
                    </td>
                    <td class="value">
                    <s:textfield id="cmsTicketId" name="studyProcessingErrDto.cmsTicketId" cssStyle="width:200px" maxLength="50"/>
                    <span class="formErrorMsg"> 
                      <s:fielderror>
                        <s:param>studyProcessingErrDto.cmsTicketId</s:param>
                      </s:fielderror>                            
                    </span>
                   </td>         
                </tr>
                <tr>
                    <td scope="row" class="label">
                    <label for="actionsTaken">
                       <fmt:message key="actionsTaken.actionTaken"/>
                    </label>
                    </td>
                    <td class="value">
                    <s:textarea id="actionTaken" name="studyProcessingErrDto.actionTaken" cssStyle="width:350px" rows="4" maxLength="5000"/>
                    <span class="formErrorMsg"> 
                      <s:fielderror>
                        <s:param>studyProcessingErrDto.actionsTaken</s:param>
                      </s:fielderror>                            
                    </span>
                   </td>         
                </tr>
                <tr>
                    <td scope="row" class="label">
                    <label for="resolutionDate">
                       <fmt:message key="actionsTaken.resolutionDate"/>
                    </label>
                    </td>
                    <td class="value">
                    <s:textfield id="resolutionDate" name="studyProcessingErrDto.resolutionDate" cssStyle="width:100px" maxLength="15"/>
                    <span class="formErrorMsg"> 
                      <s:fielderror>
                        <s:param>studyProcessingErrDto.resolutionDate</s:param>
                      </s:fielderror>                            
                    </span>
                   </td>         
                </tr>
        </table>
        <s:hidden name="studyProcessingErrDto.identifier" id="identifier" /> 
  </s:form>
</div>
