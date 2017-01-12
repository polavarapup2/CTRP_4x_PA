<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="studyProtocol.view.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/tooltip.js'/>"></script>
        <script type="text/javascript" language="javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
            }
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }
            
            function handleAction(action) {
                var studyProtocolId = '${sessionScope.trialSummary.studyProtocolId}';
                var form = document.forms[0];
                if (!allowAction(action)) {
                	return;
                }
                if ((action == 'adminCheckIn') || (action == 'scientificCheckIn') || (action == 'adminAndScientificCheckIn')
                		|| (action == 'checkInSciAndCheckOutToSuperAbs')){
                    showCommentsBox(action);
                } 
                else {
	                form.action="studyProtocol" + action + ".action?studyProtocolId=" + studyProtocolId;
	                form.submit();                
                }
            }
            
            function saveCheckin(action) {
                var studyProtocolId = '${sessionScope.trialSummary.studyProtocolId}';
                var form = document.forms[0];
                var commandVal= document.getElementById('commentCommand').value;
               
                if ((commandVal == 'adminCheckIn') || (commandVal == 'scientificCheckIn') || (commandVal == 'adminAndScientificCheckIn')
                		|| (commandVal == 'checkInSciAndCheckOutToSuperAbs')){
                    comment = document.getElementById('comments').value;
                   
                    if (comment==null){
                        return;
                    }
                    form.elements["checkInReason"].value =comment;
                   
               }
                form.action="studyProtocol" + commandVal + ".action?studyProtocolId=" + studyProtocolId;
                form.submit();
            }
            
            function allowAction(action) {
                if (((action == 'adminCheckIn' || action == 'adminAndScientificCheckIn') 
                		&& (trialHasStatusErrors || trialHasStatusWarnings)) || 
                		(action == 'scientificCheckIn' && suAbs && !checkedOutForAdmin && (trialHasStatusErrors || trialHasStatusWarnings)) ){
                	displayStatusTransitionMessages(action);
                    return false;
                } 
                if (action == 'scientificCheckIn' && sciAbs && !checkedOutForAdmin && trialHasStatusErrors) {
                	displayStatusTransitionMessageAndPickSuperAbstractor(action);
                	return false;
                }
                return true;
            }
            
            function displayStatusTransitionMessageAndPickSuperAbstractor(action) {
                var dialogID = '#pickSuperAbstractor';                
                jQuery(dialogID).dialog('open');  
                jQuery(dialogID).attr('act', action);               
            }
            
            function displayStatusTransitionMessages(action) {
            	var dialogID = '';
            	if (trialHasStatusErrors && !trialHasStatusWarnings) {
            		dialogID = '#transitionErrors';
            	} else if (!trialHasStatusErrors && trialHasStatusWarnings) {
                    dialogID = '#transitionWarnings';
                } else if (trialHasStatusErrors && trialHasStatusWarnings) {
                    dialogID = '#transitionErrorsAndWarnings';
                }
            	jQuery(dialogID).dialog('open');  
            	jQuery(dialogID).attr('act', action);            	
            }
            
            var eltDims = null;
            function showCommentsBox(action) {
                document.getElementById('commentCommand').value=action;
                // retrieve required dimensions
                if (eltDims == null) {
                    eltDims = $('comment-dialog').getDimensions();
                }
                var browserDims = $(document).viewport.getDimensions();

                // calculate the center of the page using the browser and element dimensions
                var y  = (browserDims.height - eltDims.height) / 2;
                var x = (browserDims.width - eltDims.width) / 2;    
                
                $('comment-dialog').absolutize(); 
                $('comment-dialog').style.left = x + 'px';
                $('comment-dialog').style.top = y + 'px';
                $('comment-dialog').show();
            }    
            
            var removeNctIdURL = '<c:url value='/protected/studyProtocolremoveNctId.action'/>';
            (function($){    
            	//******************
                //** On DOM Ready **
                //******************
            	$(function () {
            		
            		$( "#confirmNctIdDialog" ).dialog({
                        autoOpen: false,
                        resizable: false,
                        modal: true,                      
                        buttons: {
                            "Confirm": function() {
                              $(this).dialog("close");
                              $("#removeNctIdIcon").hide();
                              $("#removeNctIdWaitIndicator").show();
                              
                              $.ajax(removeNctIdURL, {                  
                                  data: {
                                	  studyProtocolId : ${sessionScope.trialSummary.studyProtocolId}
                                  },     
                                  cache: false,
                                  timeout : 30000
                              }).always(function() {
                            	  $("#removeNctIdWaitIndicator").hide();
                            	  $("#removeNctIdIcon").show();
                              }).done(function() {
                            	  $("#removeNctIdIcon").hide();
                            	  $("#td_CTGOV_value").fadeOut(1500);
                              });
                              
                            },
                            Cancel: function() {
                              $( this ).dialog("close");
                            }
                          }
                     });      
            		
            		 $( "#transitionErrors" ).dialog({
                         modal: true,
                         autoOpen : false,                         
                         buttons: {
                           "Trial Status History": function() {
                                 $(this).dialog("close");
                                 goToTrialStatusHistory();
                           },
                           Cancel: function() {
                             $(this).dialog("close");
                           }
                         }
                     });
            		 
                     $( "#transitionErrorsAndWarnings" ).dialog({
                         modal: true,
                         autoOpen : false,                         
                         buttons: {
                           "Trial Status History": function() {
                                 $(this).dialog("close");
                                 goToTrialStatusHistory();
                           },
                           Cancel: function() {
                             $(this).dialog("close");
                           }
                         }
                     });
                     
                     $( "#transitionWarnings" ).dialog({
                         modal: true,
                         autoOpen : false,         
                         width : 450,
                         buttons: {
                           "Proceed with Check-in": function() {
                                 $(this).dialog("close");
                                 trialHasStatusErrors = false;
                                 trialHasStatusWarnings = false;
                                 handleAction($(this).attr('act'));
                                 
                           },
                           "Trial Status History": function() {
                                 $(this).dialog("close");
                                 goToTrialStatusHistory();
                           },
                           Cancel: function() {
                             $(this).dialog("close");
                           }
                         }
                     });
                     
                     $( "#pickSuperAbstractor" ).dialog({
                         modal: true,
                         autoOpen : false,         
                         width : 450,
                         buttons: {
                           "Proceed with Check-in": function() {
                        	   if ($.isNumeric($('#supAbsId').val())) {
                        		   document.forms[0].elements["superAbstractorId"].value = $('#supAbsId').val();
                        		   $(this).dialog("close");                                
                                   handleAction('checkInSciAndCheckOutToSuperAbs'); 
                        	   }                                                                 
                           },                           
                           "Cancel": function() {
                             $(this).dialog("close");
                           }
                         }
                     });

            		
            		$('#removeNctIdIcon').click(function(e) {
            			$( "#confirmNctIdDialog" ).dialog( "open" );
                    });            		
            	});
            }(jQuery));
            
            function resetValues(){
              var studyProtocolId = '${sessionScope.trialSummary.studyProtocolId}';
              document.forms[0].action="studyProtocolview.action?studyProtocolId=" + studyProtocolId;
              document.forms[0].submit();
            }
            
            var trialStatusHistoryURL = '<c:url value='/protected/studyOverallStatus.action'/>';
            function goToTrialStatusHistory() {
            	 var form = document.forms[0];
            	 form.action=trialStatusHistoryURL;
                 form.submit();
            }
            
            var trialHasStatusErrors = ${sessionScope.trialHasStatusErrors==true};
            var trialHasStatusWarnings = ${sessionScope.trialHasStatusWarnings==true};
            
            var adminAbs = ${sessionScope.isAdminAbstractor==true};
            var sciAbs =	${sessionScope.isScientificAbstractor==true};
            var suAbs =	${sessionScope.isSuAbstractor==true};
            
            var checkedOutForAdmin = ${sessionScope.trialSummary.adminCheckout.checkoutBy != null};
            var checkedOutForSci = ${sessionScope.trialSummary.scientificCheckout.checkoutBy != null};
           
            
        </script>
        <style type="text/css">
            
            .labelDupe {
                font-weight: bold;
                font-size: 92%;
                color: #333333;
        }
</style>
    </head>
    <body>
        <c:set var="topic" scope="request" value="trialdetails"/>
        <c:set scope="request" var="suAbs" value="${sessionScope.isSuAbstractor==true}"></c:set>
        <c:set var="isRejected" value="${trialSummary.documentWorkflowStatusCode.code  == 'Rejected'}"/>
        <h1>Trial Identification</h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <s:form>
                <s:token/>
                <s:hidden name="checkInReason"/>
                <s:hidden name="superAbstractorId"/>
                <s:actionerror/>
                <h2>Trial Identification</h2>
                <table class="form" cellspacing="10" cellpadding="10">
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe" width="1%">
                                                
                                <fmt:message key="studyProtocol.studyProtocolType"/>
                                                      
                        </td>
                        <td nowrap="nowrap" >
                            <c:out value="${trialSummary.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
                        </td>
                    </tr>   
                    <c:if test="${trialSummary.studyProtocolType=='NonInterventionalStudyProtocol'}">
	                    <tr>
	                        <td nowrap="nowrap" scope="row" class="labelDupe">                            
	                                <fmt:message key="studyProtocol.nonIntTrialType"/>
	                                                          
	                        </td>
	                        <td nowrap="nowrap" >
	                            <c:out value="${trialSummary.studySubtypeCode}"/>
	                        </td>
	                    </tr>
                    </c:if>             
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">
                           
                                <fmt:message key="studyProtocol.nciIdentifier"/>
                            
                        </td>
                        <td nowrap="nowrap">
                            <c:out value="${sessionScope.trialSummary.nciIdentifier }"/>
                        </td>
                    </tr>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <td nowrap="nowrap" scope="row" class="labelDupe">
                                 ClinicalTrials.gov XML required?
                            </td>
                            <td nowrap="nowrap">
                                <pa:displayBoolean value="${sessionScope.trialSummary.ctgovXmlRequiredIndicator}"/>
                            </td>
                        </tr>
                    </c:if>       
                    <tr><td>&nbsp;</td></tr>
                    <c:forEach var="identifier" items="${studyIdentifiersByType}">                      
	                    <tr>
	                        <td nowrap="nowrap" scope="row" class="labelDupe" id="td_${identifier.key.name}_name">		                           
	                                <c:out value="${identifier.key.code}"/>
	                        </td>
	                        <td nowrap="nowrap" id="td_${identifier.key.name}_value">
	                            <c:out value="${identifier.value}"/>
	                            <c:if test="${isRejected && identifier.key.name=='CTGOV'}">
                                    <img id="removeNctIdIcon" src="<c:url value="/images/ico_delete.gif"/>"
                                        style="cursor:pointer;"
                                        alt="Icon to remove ClinicalTrials.gov Identifier from the trial"
                                        title="Click here to remove the ClinicalTrials.gov Identifier from this trial." /> 
                                    <img id="removeNctIdWaitIndicator" style="display:none;" src="<c:url value="/images/loading.gif"/>"
                                        height="16" width="16"
                                        alt="AJAX Wait Indicator"
                                        title="AJAX Wait Indicator" />                                        
	                            </c:if>
	                        </td>
	                    </tr>
                    </c:forEach>                                 
                     <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">
                            
                                <fmt:message key="studyProtocol.proprietaryTrial"/>
                            
                        </td>
                        <td nowrap="nowrap" >
                            <pa:displayBoolean value="${sessionScope.trialSummary.proprietaryTrial}"/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">
                            <fmt:message key="studyProtocol.lastVerificationDate"/>       
                        </td>
                        <td nowrap="nowrap">
                            <c:out value="${sessionScope.trialSummary.recordVerificationDate}"/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">                          
                                <fmt:message key="studyProtocol.officialTitle"/>   
                        </td>
                        <td>
                             <c:out value="${sessionScope.trialSummary.officialTitle }"/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" scope="row" class="labelDupe">                          
                                <fmt:message key="studyProtocol.studySource"/>   
                        </td>
                        <td>
                             <c:out value="${sessionScope.trialSummary.studySource }"/>
                        </td>
                    </tr>
                </table>
                <s:if test="%{!checkoutCommands.isEmpty()}">
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <pa:studyUniqueToken/>
                                <s:set name="checkoutCommands" scope="page" value="%{checkoutCommands}" /> 
                                <c:forEach items="${checkoutCommands}" var="command" varStatus="vstat">
                                    <c:if test="${vstat.index==2}">
                                        <!-- Force the 3rd button onto a separate row: https://tracker.nci.nih.gov/browse/PO-3966 -->
                                        </ul></del><br/><del class="btnwrapper"><ul class="btnrow">                                        
                                    </c:if>
                                    <li>
                                        <a href="javascript:void(0)" class="btn" onclick="handleAction('${command}')">
                                            <span class="btn_img"><span class="save"><fmt:message key="studyProtocolView.button.${command}" /></span></span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </del>
                    </div>
                </s:if>
                <c:if test="${pageContext.request.method=='GET' && fn:contains(header['Referer'],'/studyProtocolquery.action')}">
	                <div class="actionsrow" style="border: none;">
	                  <del class="btnwrapper">
	                      <ul class="btnrow">
	                          <li><a href="javascript:void(0)" class="btn" onclick="window.history.back();"><span class="btn_img"><span class="back">Back to Search Results</span></span></a></li>
	                      </ul>
	                  </del>
	                 </div>     
                 </c:if>  
                 
                 <div class="line"></div>
                 <table class="form">
                         <tr>
                             <td scope="row" class="label"><label for="assignedTo">Assigned To</label></td>
                             <td> 
                                 <s:set name="abstractorsList"
                                     value="@gov.nih.nci.pa.service.util.CSMUserService@getInstance().abstractors" />
                                 <s:select id="assignedTo" name="assignedTo"
                                     list="#abstractorsList" headerKey="" headerValue="Unassigned"
                                     value="#session.trialSummary.assignedUserId" cssStyle="width:206px" />                                        
                             </td>
                         </tr>
                         <tr>
                             <td scope="row" class="label"><label for="newProcessingPriority">Processing Priority</label></td>
                             <td> 
                                 <s:select id="newProcessingPriority"
                                     name="newProcessingPriority"
                                     list="#{'1':'1 - High','2':'2 - Normal','3':'3 - Low'}"                                               
                                     value="#session.trialSummary.processingPriority" cssStyle="width:206px" />    
                                 
                                 <c:if test="${!suAbs}">
                                       <script type="text/javascript">
                                             Event.observe(window, "load", function () {
                                                 $('assignedTo').disabled = true;
                                                 $('newProcessingPriority').disabled = true;
                                             });
                                       </script>
                                  </c:if>                                        
                                                                     
                             </td>
                         </tr>  
                         <tr>
                             <td scope="row" class="label" colspan="2"><label for="processingComments">Trial Processing Comments</label></td>
                         </tr>
                         <tr>
                             <td colspan="2">
                                 <s:textarea id="processingComments" name="processingComments"    
                                     maxlength="4000" cssClass="charcounter"                                        
                                     cssStyle="width: 100%;" rows="5">
                                     <s:param name="value">
                                         <s:property value="#session.trialSummary.processingComments"/>
                                     </s:param>                                            
                                 </s:textarea> 
                             </td>                                        
                         </tr>
                         <tr>
                             <td colspan="2" align="center">
                                 <div class="actionsrow">
                                     <del class="btnwrapper">
                                         <ul class="btnrow">
                                             <li><s:a href="javascript:void(0)" cssClass="btn"
                                                     onclick="handleAction('save');">
                                                     <span class="btn_img"><span class="search">Save</span></span>
                                                 </s:a>
                                                 <s:a onclick="resetValues();" href="javascript:void(0)" cssClass="btn">
                                                <span class="btn_img"><span class="cancel">Cancel</span></span>
                                            </s:a></li>
                                         </ul>
                                     </del>
                                 </div>                                        
                             </td>
                         </tr>  
                 </table>                 
            <div id="confirmNctIdDialog" title="Confirm ClinicalTrials.gov ID Removal">
                <p>Please confirm you want to remove ClinicalTrials.gov Identifier from this trial</p>
            </div> 
            <pa:trialCheckInWarnings/>
            </s:form>
        </div>
    </body>
</html>
