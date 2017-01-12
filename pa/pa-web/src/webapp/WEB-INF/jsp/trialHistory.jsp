<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="trialHistory.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

        <script type="text/javascript" language="javascript">
        // this function is called from body onload in main.jsp (decorator)

            function callOnloadFunctions() {
                // there are no onload functions to call for this jsp
                // leave this function to prevent 'error on page'
            }

            function handleEdit(rowId) {
                document.listForm.selectedRowIdentifier.value = rowId;
                document.listForm.action="trialHistoryedit.action";
                document.listForm.submit();
            }

            function handlePopup(a,b,c) { // write corresponding content to the popup window
                document.listForm.action="trialHistoryopen.action?studyProtocolii="+a+"&docii="+b+"&docFileName="+c;
                document.listForm.submit();
            }

            function acceptTrialUpdate(id) {
            	acknowledgeUpdate(id, 'Both');
            }
            
            function acknowledgeUpdate(id, updateType) {
                input_remove_box = confirm("Do you want to acknowledge this update?");
                if (input_remove_box==true) {
                    var url = '/pa/protected/ajaxTrialHistoryacceptUpdate.action';
                    var params = { studyInboxId: id, updateType: updateType };
                    var div = document.getElementById('updates');
                    var aj = callAjaxPost(div, url, params);
                }
            }

            
            function acceptAdminTrialUpdate(id) {
            	acknowledgeUpdate(id, 'Admin');
            }
            
            function acceptScientificTrialUpdate(id) {
            	acknowledgeUpdate(id, 'Scientific');
            }

            Event.observe(window, 'load', function() {
            	updateHelpTopic('submission');
            	Event.observe(document.getElementById('submissionsId'), "click", function() {
                    updateHelpTopic('submission');
                });
               Event.observe(document.getElementById('updatesId'), "click", function() {
                updateHelpTopic('update');
               });   
               Event.observe(document.getElementById('auditId'), "click", function() {
                    updateHelpTopic('audit');
               }); 
           });
            
            function updateHelpTopic(tabType) {
                if(tabType =='submission') {
                   document.getElementById('pageHelpid').onclick = function() {
                        Help.popHelp('historysubmissions');
                   } 
                } 
                if(tabType =='update') {
                   document.getElementById('pageHelpid').onclick = function() {
                        Help.popHelp('historyupdates');
                   }
                }
                if (tabType =='audit') {
                   document.getElementById('pageHelpid').onclick = function() {
                       Help.popHelp('historyaudit');
                   }
                }
                return true;
            }
        </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="trialhistory" />
        <h1><fmt:message key="trialHistory.title"/></h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
            <s:form name="listForm">
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                <h2><fmt:message key="trialHistory.title"/></h2>
                <table class="form">
                    <tr>
                        <td colspan="3">
                            <ul id="maintabs" class="tabs">
                                <li><a id="submissionsId" href="#submissions">Submissions</a></li>
                                <li><a id="updatesId" href="#updates">Updates</a></li>
                                <li><a id="auditId" href="#auditTrail">Audit Trail</a></li>
                            </ul>
                            <!--/Tabs -->
                            <!--
                                directions on http://livepipe.net/control/tabs
                                - make sure you add control.tabs.js to your scripts directory!
                                - Matt
                            -->
                            <script type="text/javascript">
                                //<![CDATA[
                                Event.observe(window,'load',function() {
                                	var tabs = new Control.Tabs($('maintabs'));
                                	<c:if test="${not empty param.activeTab}">
                                		tabs.setActiveTab('<%=org.apache.commons.lang.StringUtils.defaultString(
                                		        request.getParameter("activeTab")).replaceAll("\\W","")%>');
                                	</c:if>                                                                        	
                                });
                                //]]>
                            </script>

                            <div id="tabboxwrapper">
                                <div id="submissions" class="box" style="display:none;">
                                    <jsp:include page="/WEB-INF/jsp/displayTrialSubmissions.jsp"/>
                                </div>
                                <div id="updates" class="box" style="display:none;">
                                    <jsp:include page="/WEB-INF/jsp/displayTrialUpdates.jsp"/>
                                </div>
                                <div id="auditTrail" class="box" style="display:none;">
                                    <jsp:include page="/WEB-INF/jsp/auditTrail.jsp"/>                                    
                                </div>
                            </div>                                                   
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </body>
</html>
