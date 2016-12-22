<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="trialStatus.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
        <c:url value="/protected/studyOverallStatusHistorypopup.action?validate=true" var="lookupUrl" />
        
        <style type="text/css">
            #formTable td:nth-child(3), #formTable td:nth-child(3) > label {
                text-align: right;
                padding-right: 30px;
            }
        </style>
        
        <script type="text/javascript">
            addCalendar("Cal1", "Select Date", "statusDate", "studyoverallstatus");
            addCalendar("Cal2", "Select Date", "startDate", "studyoverallstatus");
            addCalendar("Cal3", "Select Date", "primaryCompletionDate", "studyoverallstatus");
            addCalendar("Cal4", "Select Date", "completionDate", "studyoverallstatus");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }
            
            function lookup() {
            	showWidePopup('${lookupUrl}', null, 'Status History');
            } 
            
            function statusChange() {
                var newStatus = $('currentTrialStatus').value;
                if((newStatus == "Administratively Complete")
                   || (newStatus == "Withdrawn")
                   || (newStatus == "Temporarily Closed to Accrual")
                   || (newStatus == "Temporarily Closed to Accrual and Intervention")) {
                  $('statusReason').disabled=false;
                  $('statusReason').readonly=false;
                } else {
                  $('statusReason').disabled=true;
                  $('statusReason').readonly=true;
                }
            }
            
            function handleAction() {
                document.forms[0].action="studyOverallStatusupdate.action";
                document.forms[0].submit();
            }
            
            function displayTrialStatusDefinition(selectBoxId) {
                $('allTrialStatusDefinitions').childElements().invoke('hide');
                var selectedValue = $(selectBoxId).value;
                if (selectedValue!='') {
                    $(selectedValue).show();
                    $$('span.required').each(function (el) {el.show()});
                } else {
                	$$('span.required').each(function (el) {el.hide()});
                }
            }
            
            document.observe("dom:loaded", function() {
                displayTrialStatusDefinition('currentTrialStatus');
                });
            
            
            var displaySuAbstractorAutoCheckoutMessage = ${displaySuAbstractorAutoCheckoutMessage};
            
            (function($) {
                $(function() {
                	if (displaySuAbstractorAutoCheckoutMessage) {
                		 $( "#displaySuAbstractorAutoCheckoutMessage" ).dialog({
                			     autoOpen : true,	                			 
	                			 height:220,
	                			 width:430,
	                			 modal: true,
	                			 buttons: {
	                				    "Trial Status History": function() {
	                				    	   $( this ).dialog( "close" );
	                				    	   lookup();
	                				     },
	                				     "Cancel": function() {
	                				           $( this ).dialog( "close" );
	                				         }
	                				     }
                		    });                		 
                	}
                });
            })(jQuery);

            
        </script>
    </head>
    <body>
        <h1><fmt:message key="trialStatus.title" /></h1>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
            <c:set var="topic" scope="request" value="reviewstatus"/>
        </c:if>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
            <c:set var="topic" scope="request" value="abstractstatus"/>
        </c:if>
        <c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <s:url id="cancelUrl" namespace="/protected" action="studyOverallStatus"/>
        <div class="box">
            <pa:sucessMessage/>
            <s:form name="studyoverallstatus">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:if test="hasActionErrors()">
                    <div class="error_msg"><s:actionerror escape="false"/></div>
                </s:if>
                <h2><fmt:message key="trialStatus.title" /></h2>
                <table class="form" id="formTable">
                    <tr>
                        <td width="0">
                            <table>
                                <tr>
                                    <pa:valueRow cellOnly="true" labelFor="currentTrialStatus" labelKey="trialStatus.current.trial.status" required="${!sessionScope.trialSummary.proprietaryTrial}">
                                        <s:set name="currentTrialStatusValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
                                        <s:if test="sosDto==null">
	                                        <s:select onchange="statusChange();displayTrialStatusDefinition('currentTrialStatus');" 
	                                                  headerKey="" headerValue=""
	                                                  onfocus="statusChange()" id="currentTrialStatus" name="currentTrialStatus" list="#currentTrialStatusValues" />
                                        </s:if>
                                        <s:else>
	                                        <s:select onchange="statusChange();displayTrialStatusDefinition('currentTrialStatus');"
	                                                  onfocus="statusChange()" id="currentTrialStatus" name="currentTrialStatus" list="#currentTrialStatusValues" />                                        
                                        </s:else>
                                    </pa:valueRow>
                                    <td>
                                        <ul class="btnrow">            
                                            <li style="padding-left:0">
                                                <a href="javascript:void(0)" class="btn" onclick="lookup()"><span class="btn_img"><span class="history">History</span></span></a>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="info"><%@ include file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp" %></td>
                                    <td>&nbsp;</td>
                                </tr>
                                <pa:valueRow labelFor="statusDate" labelKey="trialStatus.current.trial.status.date" required="true">
                                    <s:textfield name="statusDate" id="statusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal1')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a>
                                </pa:valueRow>
                                <tr> 
                                    <td>&nbsp;</td>
                                    <td class="info" colspan="2">Administratively Complete, Withdrawn, and Temporarily Closed statuses only</td>
                                </tr>
                                <tr>
	                                <pa:valueRow labelFor="statusReason" labelKey="trialStatus.current.trial.status.reason" cellOnly="true">
	                                    <s:textarea name="statusReason" id="statusReason" rows="3" cssStyle="width:280px;" maxlength="160" cssClass="charcounter"/>
	                                </pa:valueRow>
	                                <pa:valueRow labelFor="additionalComments" labelKey="trialStatus.current.trial.status.additionalComments" cellOnly="true">
                                        <s:textarea name="additionalComments" id="additionalComments" rows="3" cssStyle="width:240px;" maxlength="160" cssClass="charcounter"/>
                                    </pa:valueRow>
                                </tr>
                                <pa:spaceRow/>
                                <pa:valueRow labelFor="startDate" labelKey="trialStatus.trial.start.date" required="true">
                                    <s:textfield name="startDate" id="startDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal2')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                                    <s:radio name="startDateType" id="startDateType" list="dateTypeList" />
                                </pa:valueRow> 
                                <!-- Hide the asterix in primary completion date label if it is non interventional trial and CTGovXmlRequired is false -->                               
                                <c:set var="isRequired" value="true"/>
                                <c:if test="${sessionScope.trialSummary.studyProtocolType=='NonInterventionalStudyProtocol' && !sessionScope.trialSummary.ctgovXmlRequiredIndicator}">
                                    <c:set var="isRequired" value="false"/>                                
                                </c:if>
                                <pa:valueRow labelFor="primaryCompletionDate" labelKey="trialStatus.primary.completion.date" required="${isRequired}">
                                    <s:textfield name="primaryCompletionDate" id="primaryCompletionDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal3')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                                    <s:radio name="primaryCompletionDateType" id="primaryCompletionDateType" list="#{'Actual':'Actual','Anticipated':'Anticipated','N/A':'N/A'}" />
                                </pa:valueRow>
                                <pa:valueRow labelFor="completionDate" labelKey="trialStatus.completionDate">
                                    <s:textfield name="completionDate" id="completionDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                    <a href="javascript:showCal('Cal4')">
                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                                    <s:radio name="completionDateType" id="completionDateType" list="dateTypeList" />
                                </pa:valueRow>
								<td>&nbsp;</td>
								<td>
								    <span class="info">Please refer to <a href="https://wiki.nci.nih.gov/x/l4CNC" target="newPage">Trial Status Rules for Start and Completion dates</a>.</span>
								    </td>
								
                            </table>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:displayWhenCheckedOut>
                                <li>
                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                    <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                </li>
                            </pa:displayWhenCheckedOut>
                        </ul>   
                    </del>
                </div>
            </s:form>
        </div>
        <div id="displaySuAbstractorAutoCheckoutMessage" title="Trial Status Validation" style="display:none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
            The system has checked-out this trial under your name because Trial Status Transition errors were found.
            Trial record cannot be checked-in until all Status Transition Errors have been resolved.
            Please use the Trial Status History button to review and make corrections, or Cancel to dismiss this message.
            </p>
        </div>
    </body>
</html>