<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><fmt:message key="milestone.details.title" /></title>
        <s:head />

        <script type="text/javascript" language="javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/datetimepicker.js"/>"></script>
        
        <script type="text/javascript" language="javascript">
        
            var trialAmended = ${not empty trialSummary.amendmentNumber};
        
            addCalendar("Cal1", "Select Date", "milestone.date", "milestoneForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }
            
            function milestoneAdd() {
                document.addmilestoneForm.action = "milestoneadd.action";
                document.addmilestoneForm.submit();
            }
            
            function cancel() {
                if (winCal != null ) { 
                    winCal.close();
                }    
                var form = document.addmilestoneForm;
                form.action="milestoneview.action";
                form.submit();
            }
            
            function setCommentsLabel(labelSpan) {
            	lblHTML = labelSpan.innerHTML;
            	document.getElementById('milestoneCommentsLbl').innerHTML=lblHTML;
            }
            
            function statusChange() {
                var selectedMilestone = document.forms["addmilestoneForm"].elements["milestone.milestone"].value;
                if (selectedMilestone == "Late Rejection Date") {
                	setCommentsLabel(document.getElementById('latemilestoneCommentsSpan'));
                } else {
                	setCommentsLabel(document.getElementById('milestoneCommentsSpan'));
                }
            }
            Event.observe(window, "load", statusChange);
            function handleChange() {
                var form = document.listmilestoneForm;
                form.action="milestoneview.action";
                form.submit();
            }    
            
            (function ($) {
                $(function() {
                	
                	$("#late-reject-dialog").dialog({
                        modal : true,
                        width : '400px',
                        autoOpen : false,                       
                        buttons : {
                            "Reject Entire Trial" : function() {
                            	$('#lateRejectBehavior').val('ENTIRE_TRIAL');
                            	$(this).dialog("close");
                            	milestoneAdd();
                            },
                            "Reject This Amendment Only" : function() {
                            	$('#lateRejectBehavior').val('AMENDMENT_ONLY');
                            	$(this).dialog("close");
                            	milestoneAdd();
                            }
                        }
                    });
                	
                	$('#addMilestoneBtn')
                    .bind(
                            'click',
                            function(e) {                            	                              
                            	if (trialAmended && document.forms["addmilestoneForm"].elements["milestone.milestone"].value == "Late Rejection Date") {
                            		$("#late-reject-dialog").dialog('open');
                            	} else {
                            		milestoneAdd();
                            	}                            	
                            });
                })
            }(jQuery));
            var eltDims = null;
            function unreject() {
                // retrieve required dimensions
                if (eltDims == null) {
                    eltDims = $('comment-dialog').getDimensions();
                }
                var browserDims = $(document).viewport.getDimensions(); 
                
                var tableDims = $('tablemilestonid').getDimensions();
                // calculate the center of the page using the browser and element dimensions
                var y  = (browserDims.height + tableDims.height - eltDims.height) / 2 + 200;
                var x = (browserDims.width - eltDims.width) / 2;    
                
                $('comment-dialog').absolutize(); 
                $('comment-dialog').style.left = x + 'px';
                $('comment-dialog').style.top = y + 'px';
                $('comment-dialog').show();
            }
            
            function saveUnrejectReason() {
                var studyProtocolId = '${sessionScope.trialSummary.studyProtocolId}';
                var form = document.forms[0];
                comment = document.getElementById('comments').value;
                if (comment==null || comment == '' || comment.trim() == ''){
                    return;
                }
                document.addmilestoneForm.elements["unRejectReason"].value =comment;
                document.addmilestoneForm.action="milestoneunrejectTrial.action";
                document.addmilestoneForm.submit();
            }
            
        </script>
    </head>
    <body>
        <h1><fmt:message key="milestone.details.title"/></h1>
        <c:set var="topic" scope="request" value="trialmilestones"/>
        <c:set var="isRejected" value="${trialSummary.documentWorkflowStatusCode.code  == 'Rejected'}"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box" id="tablemilestonid">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="error_msg">
                    <s:actionerror />
                </div>
            </s:if>
            <s:form name="listmilestoneForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                
                <s:if test="%{amendmentMap.size > 1}">
                    <div>
                        <span class="label">
                            <label for="submissionNumber"><fmt:message key="milestone.submissionNumber"/></label>
                        </span>
                        <s:select id="submissionNumber" list="amendmentMap" listValue="value.submissionNumber" 
                                  name="submissionNumber" onchange="handleChange();" value="submissionNumber"/>
                    </div>
                </s:if>
                
                <h2>
                    <fmt:message key="milestone.details.title"/>
                </h2>
                
                
                <table class="form">
                    <tr>
                        <td colspan="2">
						    <s:if test="milestoneList != null" >
						       <table class="data">
						                <tr><td>
						                 <table class="form" id="table-1">
						                 <tbody> 
						                 <tr class="nodrag nodrop">
						                 <th><label for="typeCode1"><fmt:message key="milestone.milestone"/></label></th>
						                 <th><label for="typeCode2"><fmt:message key="milestone.date"/></label></th>
						                  <th><label for="typeCode3"><fmt:message key="milestone.comment"/></label></th>
						                  <th><label for="typeCode4"><fmt:message key="milestone.creator"/></label></th>
						                  <th><label for="typeCode5"><fmt:message key="milestone.creationDate"/></label></th>
						                  </tr>
						      
						                      <s:iterator value="milestoneList" id="milestoneList" status="stat" >
						                      <tr id="<s:property value='#stat.index'/>">
						                       <td class="tdBoxed">
						                       <s:textfield id="typeCode1" wrap="true"  name="milestoneList[%{#stat.index}].milestone" value="%{milestone}" cssStyle="width:250px;border: 1px solid #FFFFFF" readonly="true"/>
						                       </td>
						                       <td class="tdBoxed">
						                        <s:textfield id="typeCode2"  name="milestoneList[%{#stat.index}].date" value="%{date}" cssStyle="width:110px;border: 1px solid #FFFFFF" readonly="true" rows="1"/>
						                       </td>
						                       <td class="tdBoxed">
						                        <s:textarea id="typeCode3" wrap="true" name="milestoneList[%{#stat.index}].comment" value="%{comment}" cssStyle="width:250px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
						                       </td>
						                       <td class="tdBoxed">
						                        <s:textfield id="typeCode4" name="milestoneList[%{#stat.index}].creator" value="%{creator}" cssStyle="width:150px;border: 1px solid #FFFFFF" readonly="true" rows="1"/>
						                       </td>
						                       <td class="tdBoxed">
						                        <s:textfield id="typeCode5" name="milestoneList[%{#stat.index}].creationDate" value="%{creationDate}" cssStyle="width:110px;border: 1px solid #FFFFFF" readonly="true"/>
						                       </td>
						                     </tr>
						                    </s:iterator>
						            </tbody>   
						              
						           </table>
						          
						           </td></tr></table>
						       </s:if>                            
                        </td>
                    </tr>
                </table> 
            </s:form>
        </div>
        
        
        
        
        <pa:displayWhenCheckedOut>
            <s:if test="%{addAllowed}">
		        <div class="box">
		            <h2>
		                <fmt:message key="milestone.add.details.title"/>
		            </h2>
		            <table class="form">
		                <tr>
		                    <td colspan="2">
		                        <table class="form">
		                            <tr>
		                                <td colspan="2">
		                                    <h3><fmt:message key="milestone.milestone"/></h3>
		                                    <s:form name="addmilestoneForm">
		                                        <s:token/>
		                                        <pa:studyUniqueToken/>
		                                        <s:hidden name="lateRejectBehavior" id="lateRejectBehavior"/>
		                                        <s:hidden name="unRejectReason" id="unRejectReason"></s:hidden>
		                                        <table class="form">
		                                            <tr>
		                                                <td class="label">
		                                                     <label for="milestone"> <fmt:message key="milestone.milestone"/></label>
		                                                    <span class="required">*</span>
		                                                </td>
		                                                <td class="value" style="width: 250px">
		                                                    <s:select id="milestone" headerKey="" headerValue="--Select--" name="milestone.milestone" list="%{allowedMilestones}" 
		                                                              onchange="statusChange()" onfocus="statusChange()"/>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="label">
		                                                    <label for="date"> <fmt:message key="milestone.date"/></label>
		                                                    <span class="required">*</span>
		                                                </td>
		                                                <td class="value">
		                                                    <s:textfield name="milestone.date" id="date" maxlength="30" size="30" cssStyle="width:170px;float:left" readonly="true"/>
		                                                    <a href="javascript:NewCal('date','mmddyyyy',false,24)">
		                                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
		                                                    </a>
		                                                </td> 
		                                            </tr>
		                                            <tr >
		                                                <td class="label" id="milestoneComments" >
		                                                  	<label id="milestoneCommentsLbl" for="milestoneCommentsTA"></label>
		                                                </td>
	                                                     <td class="value">
	                                                         <s:textarea id="milestoneCommentsTA" name="milestone.comment" rows="4" cssStyle="width:280px;"/>
                                                       </td> 
		                                            </tr>
                                                    
		                                        </table>
		                                            <pa:unRejectTrialComment/> 
		                                       
		                                    </s:form>
		                                    <!-- Comments label holding items -->
		                                    <span id="milestoneCommentsSpan" style="display:none;"><fmt:message key="milestone.comment"/></span>
		                                    <span id="latemilestoneCommentsSpan" style="display:none;"><fmt:message key="milestone.latecomment"/><span class="required">*</span></span>
		                                    <div class="actionsrow">
		                                        <del class="btnwrapper">
		                                            <ul class="btnrow">
		                                                <li>
		                                                    <s:a href="javascript:void(0)" cssClass="btn" id="addMilestoneBtn">
		                                                        <span class="btn_img"> <span class="add">Add Milestone</span></span>
		                                                    </s:a>
		                                                </li>
		                                                <li>
		                                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();">
		                                                        <span class="btn_img"><span class="cancel">Cancel</span></span>
		                                                    </s:a>
		                                                </li>
		                                                <c:if test="${sessionScope.isSuAbstractor && isRejected}" >
		                                                <li>
		                                               <s:a onclick="unreject();" href="javascript:void(0)" cssClass="btn">
                                                         <span class="btn_img">Un-Reject Trial</span>
                                                       </s:a>
		                                                </li>
		                                                </c:if>
		                                            </ul>
		                                        </del>
		                                    </div>
		                                </td>
		                            </tr>
		                        </table>
		                    </td>
		                </tr>
		            </table>
		        </div>
            </s:if>
        </pa:displayWhenCheckedOut>     
        <div id="late-reject-dialog" title="Late Rejection"
	       style="display: none;">
		    <p>
		        <span class="ui-icon ui-icon-alert"
		            style="float: left; margin: 0 7px 20px 0;"></span>Please indicate an action you want to take on this trial:
		    </p>		   
	   </div>       
      
    </body>
</html>