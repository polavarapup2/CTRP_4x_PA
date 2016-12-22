<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="onhold.title" /></title>
        <s:head />
        <script type="text/javascript">
            addCalendar("Cal1", "Select Date", "onhold.dateLow", "editForm");
            addCalendar("Cal2", "Select Date", "onhold.dateHigh", "editForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
            
            jQuery(function() {
                setFocusToFirstControl();
                jQuery("#addButton").bind("click", function(ev) {
                    var form = document.editForm;
                    form.action = "onholdadd.action";
                    form.submit();
                });
                jQuery("#updateButton").bind("click", function(ev) {
                    var form = document.editForm;
                    form.action = "onholdupdate.action";
                    form.submit();
                });
                jQuery("#cancelButton").bind("click", function(ev) {
                    var form = document.editForm;
                    form.action = "onhold.action";
                    form.submit();
                });
            });
            function displayReasonCategory() {
            	jQuery("#reasonCategoryList").prop("disabled",true);
            	var reasonCode =jQuery("#reasonCode").val();
            	if(reasonCode=="Other") {
            		jQuery("#reasonCategoryList").prop("disabled",false);
            	}
            	else if(reasonCode!="") {
            	    var form  = document.forms[0];
                    var datastring = $(form).serialize();
                    jQuery("#addButton").hide();
                    jQuery("#reasonCategoryList").prop("disabled",true);
                    jQuery("#progressImg").show();
                    jQuery("#reasonCategoryList").hide();
            		
            		jQuery.ajax({
            			   type: "POST",
            			    url: "onholdgetOnHoldReasonCode.action",
            			    data: datastring, 
            			    success: function(data)
                            {
            			    	jQuery("#progressImg").hide();
            			    	jQuery("#reasonCategoryList").val(data);
            			    	jQuery("#reasonCategoryList").show();
            			    	jQuery("#addButton").show();
            			    	
            			    },
                            error: function (request, status, error) {
                                alert("Error occured while fetching On Hold Reason Category value");
                                jQuery("#progressImg").hide();
                                jQuery("#reasonCategoryList").show();
                                jQuery("#addButton").show();
                                }
                      });
            		
            	}
            	
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="onhold.title" /></h1>
        <c:set var="topic" scope="request" value="trialonhold"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <h2><fmt:message key="onhold.title"/></h2>
            <s:form name="editForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="currentAction"/>
                <s:hidden name="onhold.identifier"/>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <h3>
                                <s:if test="%{currentAction == 'create'}"><fmt:message key="onhold.create.title"/></s:if>
                                <s:else><fmt:message key="onhold.edit.title"/></s:else>
                            </h3>
                        </td>
                    </tr>    
                    <pa:valueRow labelFor="reasonCode" labelKey="onhold.reason.code" required="true">
                        <s:set name="onholdCodeValues" value="@gov.nih.nci.pa.enums.OnholdReasonCode@getDisplayNames()" />
                        <s:if test="%{currentAction == 'create'}">
                            <s:select headerKey="" headerValue="--Select--" name="onhold.reasonCode" list="#onholdCodeValues" id="reasonCode" onChange="displayReasonCategory()"/>
                        </s:if>
                        <s:else>
                            <s:textfield name="onhold.reasonCode" cssStyle="width:200px;float:left" readonly="true" cssClass="readonly" id="reasonCode"/>
                        </s:else>
                        <pa:fieldError fieldName="onhold.reasonCode"/>
                    </pa:valueRow>
                      <pa:valueRow labelFor="reasonCategory" labelKey="onhold.reason.category" required="true">
                      <img id="progressImg" src="${pageContext.request.contextPath}/images/loading.gif" alt="Progress Indicator." width="16" height="16" style="display:none" />
                        <s:if test="%{currentAction == 'create'}">
                            <s:select headerKey=""  name="onhold.reasonCategory" list="onhold.allReasonCategoryValuesList" id="reasonCategoryList" disabled="true" />
                           
                        </s:if>
                        <s:else>
                            <s:textfield name="onhold.reasonCategory" cssStyle="width:200px;float:left" readonly="true" cssClass="readonly" id="reasonCategory"/>
                        </s:else>
                           
                      </pa:valueRow>
                    <pa:valueRow labelFor="reasonText" labelKey="onhold.reason.text">
                        <s:if test="%{currentAction == 'create'}">
                            <s:textarea id="reasonText" name="onhold.reasonText" rows="3" cssStyle="width:280px;" maxlength="4000" cssClass="charcounter"/>
                        </s:if>
                        <s:else>
                            <s:textarea id="reasonText" name="onhold.reasonText" rows="3" cssStyle="width:280px;float:left;" disabled="true" readonly="true" cssClass="readonly"/>
                        </s:else>
                    </pa:valueRow>
                    <pa:valueRow labelFor="onHoldDate" labelKey="onhold.date.low">
                        <s:textfield id="onHoldDate" name="onhold.dateLow" cssStyle="width:70px;float:left" readonly="true" cssClass="readonly"/>
                        <pa:fieldError fieldName="onhold.dateLow"/>
                    </pa:valueRow>
                    <s:if test="%{currentAction != 'create'}">
	                    <pa:valueRow labelFor="dateHigh" labelKey="onhold.date.high">
	                        <s:textfield id="dateHigh" name="onhold.dateHigh" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
	                        <a href="javascript:showCal('Cal2')">
	                            <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
	                        </a>
	                        <pa:fieldError fieldName="onhold.dateHigh"/>
	                    </pa:valueRow>
                    </s:if>
                    <s:if test="%{onhold.processingLog!=null && onhold.processingLog!=''}">
	                    <pa:valueRow labelFor="processing" labelKey="onhold.processingLog">
	                        <s:textarea id="processing" name="onhold.processingLog" rows="5" cssStyle="width:500px;float:left;" 
	                         readonly="true" cssClass="readonly"/>
	                    </pa:valueRow>                    
                    </s:if>                    
                </table>
                <pa:buttonBar>
                    <s:if test="%{currentAction == 'create'}">
                        <pa:button id="addButton" imgClass="save" labelKey="onhold.button.save"/>
                    </s:if>
                    <s:else>
                        <pa:button id="updateButton" imgClass="save" labelKey="onhold.button.save"/>
                    </s:else>
                    <pa:button id="cancelButton" imgClass="cancel" labelKey="onhold.button.cancel"/>
                </pa:buttonBar>
            </s:form>
        </div>
    </body>
</html>