<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="orgDetailsDiv">
<pa:failureMessage/>
<pa:sucessMessage /> 
 <s:if test="hasActionErrors()">
    <div class="action_error_msg"><s:actionerror escape="false"/></div>
</s:if>
<c:if test="${not empty statusTransitionErrors}">
    <div class="action_error_msg"><span class="error"><b>ERROR: </b> <c:out
            value="${statusTransitionErrors}" /></span></div>
</c:if>
<c:if test="${not empty statusTransitionWarnings}">
    <div class="action_error_msg"><span class="warning"><b>WARNING: </b> <c:out
            value="${statusTransitionWarnings}" /></span></div>
    <br />
</c:if>
<h3><fmt:message key="participatingOrganizations.subtitle2" /></h3>

<%@ include file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp" %>
</div>
<s:hidden name="programCode" id="programCode" />
<table class="form">
				    <tr>
				        <td scope="row" class="label"><label for="siteLocalTrialIdentifier"><fmt:message key="proprietary.siteidentifier"/>:</label>
				        <span class="required">${asterisk}</span>
				        </td>
				    <td>
				        <s:textfield name="siteLocalTrialIdentifier" id="siteLocalTrialIdentifier" maxlength="20" size="200" cssStyle="width: 200px" />
				        <span class="formErrorMsg">
				        <s:fielderror>
				            <s:param>siteLocalTrialIdentifier</s:param>
				        </s:fielderror>                            
				        </span>
				    </td>
				    </tr>	
               		<tr>
					<td scope="row" class="label"><label for="recStatus">Site Recruitment Status:</label><span class="required">*</span></td>
                    <s:set name="recruitmentStatusValues" 
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                    <td class="value" colspan="2">
                        <table>
                                <tr>
                                    <td class="value" colspan="2"><s:select headerKey="" headerValue="--Select--"
				                        name="recStatus" id="recStatus"
			                            list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
			                            <span class="formErrorMsg"> 
				                              <s:fielderror>
				                              <s:param>recStatus</s:param>
				                              </s:fielderror>                            
				                        </span>
	                        </td>
                            <td valign="top">
                                <ul class="btnrow">         
                                    <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupStatusHistory()"><span class="btn_img"><span class="history">History</span></span></a></li>
                                </ul>
                            </td>
                         </tr>
                       </table>
                    <td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="recStatusDate">Site Recruitment Status Date:</label><span class="required">*</span></td>
                    <td class="value" colspan="2">
                        <s:textfield name="recStatusDate" id="recStatusDate" maxlength="10" size="10" readonly="true"  cssStyle="text-align:left;width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>recStatusDate</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
				</tr>
				<tr>
                    <td scope="row" class="label"><label for="recStatusComments">Site Recruitment Status Comments:</label></td>
                    <td class="value" colspan="2">
                        <s:textarea name="recStatusComments" id="recStatusComments" rows="3" cssStyle="width:240px;" maxlength="160"  cssClass="charcounter"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>programCode</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
                </tr>
                <tr>
                    <td class="label"><label for="targetAccrualNumber">Target Accrual Number:</label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="targetAccrualNumber" id="targetAccrualNumber" maxlength="10" size="10" cssStyle="text-align:left;width:70px;float:left"/>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>targetAccrualNumber</s:param>
                              </s:fielderror>                            
                        </span>                            
                    </td>               
                </tr>
                <tr> 
                    <td/>
                    <td class="info" colspan="2">Mandatory if Participating site/Lead organization is a cancer center</td>
                </tr>
                <c:if test="${not sessionScope.trialSummary.proprietaryTrial}">
                    <s:hidden name="dateOpenedForAccrual"></s:hidden>
                    <s:hidden name="dateClosedForAccrual"></s:hidden>
                </c:if>
                <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
                <script>
	                addCalendar("Cal2", "Select Date", "dateOpenedForAccrual", "facility");
	                addCalendar("Cal3", "Select Date", "dateClosedForAccrual", "facility");     
                </script>
                <tr>
                        <td scope="row" class="label">
                            <label for="startDate"><fmt:message key="proprietary.trial.dateOpenedforAccrual" />:</label>
                        </td>
                        <td class="value">
                            <s:textfield name="dateOpenedForAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal2')">
                                <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy) 
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>dateOpenedForAccrual</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="completionDate"><fmt:message key="proprietary.Site.dateClosedforAccrual" />:</label>
                        </td>
                        <td class="value">
                            <s:textfield name="dateClosedForAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal3')">
                                <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="info"><fmt:message key="error.proprietary.dateOpenReq" /></span>  
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>dateClosedForAccrual</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>
                </c:if>
                
			</table>


