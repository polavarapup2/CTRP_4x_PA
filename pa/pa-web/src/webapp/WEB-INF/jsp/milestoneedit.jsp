<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><fmt:message key="milestone.details.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/datetimepicker.js"/>"></script>
        <script type="text/javascript" language="javascript">
            addCalendar("Cal1", "Select Date", "milestone.date", "milestoneForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }
            
            function milestoneAdd() {
                document.milestoneForm.action = "milestoneadd.action";
                document.milestoneForm.submit();
            }
            
            function cancel() {
                if (winCal != null ) { 
                    winCal.close();
                }    
                var form = document.milestoneForm;
                form.action="milestone.action";
                form.submit();
            }
            
            function hideCol(col) {          
                col.style.display = 'none'; 
            }
            
            function showCol(col) {
                col.style.display = '';
            }
            
            function statusChange() {
            	var selectedMilestone = document.forms["milestoneForm"].elements["milestone.milestone"].value;
            	if (selectedMilestone == "Late Rejection Date") {
            		hideCol(document.getElementById('milestoneComments'));
            		showCol(document.getElementById('latemilestoneComments'));
            	} else {
            		hideCol(document.getElementById('latemilestoneComments'));
            		showCol(document.getElementById('milestoneComments'));
            	}
            }
            Event.observe(window, "load", statusChange);
        </script>
    </head>
    <body>
        <h1><fmt:message key="milestone.details.title" /></h1>
        <c:set var="topic" scope="request" value="trialmilestones"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
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
                                    <s:form name="milestoneForm">
                                        <s:token/>
                                        <pa:studyUniqueToken/>
                                        <table class="form">
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="milestone.milestone"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value" style="width: 250px">
                                                    <s:select headerKey="" headerValue="--Select--" name="milestone.milestone" list="%{allowedMilestones}" 
                                                              onchange="statusChange()" onfocus="statusChange()"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="milestone.date"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value">
                                                    <s:textfield name="milestone.date" id="date" maxlength="30" size="30" cssStyle="width:170px;float:left" readonly="true"/>
                                                    <a href="javascript:NewCal('date','mmddyyyy',false,24)">
                                                        <img src="<%=request.getContextPath()%>/images/cal.gif" alt="select date" class="calendaricon" />
                                                    </a>
                                                </td> 
                                            </tr>
                                            <tr>
                                                <td class="label" id="milestoneComments">
                                                    <s:label><fmt:message key="milestone.comment"/></s:label>
                                                </td>
                                                <td class="label" id="latemilestoneComments" style="display: none;">
                                                    <s:label>Late Rejection <fmt:message key="milestone.comment"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value">
                                                    <s:textarea maxlength="200" name="milestone.comment" cssClass="charcounter" rows="3" cssStyle="width:280px;"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </s:form>
                                    <div class="actionsrow">
                                        <del class="btnwrapper">
                                            <ul class="btnrow">
                                                <li>
                                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="milestoneAdd();">
                                                        <span class="btn_img"> <span class="save">Save</span></span>
                                                    </s:a>
                                                </li>
                                                <li>
                                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();">
                                                        <span class="btn_img"><span class="cancel">Cancel</span></span>
                                                    </s:a>
                                                </li>
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
    </body>
</html>