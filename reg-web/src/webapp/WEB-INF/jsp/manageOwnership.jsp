<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="${pageTitleKey}"/></title>
        <s:head/>

        <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
		<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
		<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js?534785924'/>"></script>
    
        <script type="text/javascript" language="javascript">
           var tableConf = {
                "paging":false,    
                "scrollY": 300,
                "scrollCollapse": true,
                "scrollX": true,
                "bAutoWidth": false,
                "aoColumnDefs" : [ {
                    'bSortable' : false,
                    'aTargets' : [ "no-sort" ]
                } ]
            }  
           
          
           jQuery(function() {
               jQuery('#conttbl').css("width",(window.innerWidth - 50));
               jQuery('#regUserRowDiv').css("width",(window.innerWidth/2.5));
               
           });
            jQuery(function() {
                jQuery('#regUserRow').dataTable(tableConf);
                jQuery('#regUserRow').css("width","100%");
            });
            
            jQuery(function() {
                jQuery('#studyProtocolRow').dataTable(tableConf);
                jQuery('#studyProtocolRow').css("width","100%");
            });
            
            jQuery(function() {
                jQuery('#trialOwenership').dataTable(tableConf);
                jQuery('#trialOwenership').css("width","100%");
            }); 

            
	        function selectAll(field, chkd){
	            if(field){
	                if(typeof field.length == 'undefined'){
	                    field.checked = chkd ;
	                } else {
	                    for (i = 0; i < field.length; i++)
	                           field[i].checked = chkd ;
	                }
	             }
	       }
            
           function updateOwnership(assignOwnership) {
                var form = document.forms[0];
                form.action = (assignOwnership) ? "${actionName}assignOwnership.action" : "${actionName}unassignOwnership.action";
                displayWaitPanel();
                form.submit();
            }
            
            function setEmailPref(trialId, uid, selected) {
            	if(selected){
                    $("yes_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny active"
                    $("no_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny"
                }else{
                    $("yes_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny "
                    $("no_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny active"
                }
                var  url = '${pageContext.request.contextPath}/siteadmin/${actionName}updateEmailPref.action';
                var params = {
                    selected: selected,
                    trialId: trialId,
                    regUserId: uid
                };
                var aj = callAjaxPost(null, url, params);
                return false
            }
            
            function setEmailPrefAll(selected) {    
                var  url = '${pageContext.request.contextPath}/siteadmin/${actionName}updateEmailPref.action';
                var params = {
                    selected: selected,
                    siteName: '${func:escapeJavaScript(siteName)}'
                };
                displayWaitPanel();
                jQuery.ajax({
                    url: url ,
                    data : params,
                    success: null,
                    async: false, 
               });          
               submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageTrialOwnershipsearch.action');
               return true;
            }
            
            
            function siteOptsChange(){
            	if($('optionsOrg').checked){
            		submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageTrialOwnershipsearch.action');
            	}else{
            		submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageSiteOwnershipsearch.action');
            	}
            	
            }
        
        </script>
    </head>
    <body>
        <!-- for hiding the page contents while loading-->
        <div style="display: block" id="hideAll"><img src="${pageContext.request.contextPath}/images/loading.gif"/></div>
        <script type="text/javascript">
         document.getElementById("hideAll").style.display = "block";
        </script> 
        <!-- main content begins-->
        <c:set var="topic" scope="request" value="${topicValue}"/>
        <h3 class="heading"><span><fmt:message key="${pageHeaderKey}"/></span></h3>
         <reg-web:failureMessage/>
         <reg-web:sucessMessage/>
		 <s:form name="formManageTrialOwnership" action="%{#request.actionName}view.action" cssClass="form-horizontal" role="form">
	      <p><strong>Manage trial record ownership for:</strong> (select one)</p>
	      <div class="radio">
	          <label><input type="radio" name="siteOpts" id="optionsOrg" value="org" onchange="siteOptsChange()" <s:if test="%{#attr.topicValue == 'manageownership'}">checked</s:if>>
	          Trials where the <strong>Lead Organization</strong> is from affiliated Organization's family. (includes only Complete trials)</label>
	      </div>
	      <div class="radio">
	          <label><input type="radio" name="siteOpts" id="optionsSite" value="site"  onchange="siteOptsChange()" <s:if test="%{#attr.topicValue== 'managesiteownership'}">checked</s:if>>
	          Trials where a <strong>Participating Site</strong> is from affiliated Organization's family. (includes only Abbreviated trials) </label>
	      </div>
	      <br/><br/><br/>
         <s:hidden name="checked" id="checked"/>
         <s:token/>
         <h3 class="heading"><span><fmt:message key="managetrialownership.users.header"><fmt:param><c:out value="${siteName}"/></fmt:param></fmt:message></span></h3>
         <p><strong>Select one or more user names:</strong><i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.select_users" />" data-placement="top" data-trigger="hover"></i></p>
         
              <div class="trial-ownership-container" id="regUserRowDiv" >
                <s:set name="orgMembers" value="registryUsers" scope="request"/>
                <display:table class="table trialOwnership " summary="This table contains your search results." 
                               decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" id="regUserRow"
                               name="orgMembers" requestURI="${actionName}view.action" export="false">
                    <display:column headerClass="no-sort" title="<input type='checkbox' name='regUserIds' id='regUserIds'  value='0' onclick='selectAll(document.formManageTrialOwnership.regUserIds, this.checked)' />">
	                    <label for="${regUserRow.registryUser.id}" style="display:none"><fmt:message key="managetrialownership.users.allow"/></label>
	                    <input type="checkbox" name="regUserIds" value="${regUserRow.registryUser.id}" id="${regUserRow.registryUser.id}"/>
                    </display:column>
                    <display:column escapeXml="true" titleKey="managetrialownership.users.name" headerScope="col">
                        <c:out value="${regUserRow.registryUser.lastFirstName}"/>
                    </display:column>
                    <display:column escapeXml="true" titleKey="managetrialownership.users.email" property="registryUser.emailAddress" headerScope="col"/>
                </display:table>
              </div>
            <br/>
            <div class="clearfix"></div>
            <h3 class="heading"><span>Trials where this site is the <strong><s:if test="%{#attr.topicValue == 'manageownership'}">Lead Organization</s:if><s:else>Participating Site</s:else></strong></span></h3>
            <p class="mb20"><em><s:if test="%{#attr.topicValue == 'manageownership'}"><fmt:message key="managetrialownership.trials.instruction" /></s:if>
            <s:else><fmt:message key="managesiteownership.trials.instruction" /></s:else>
            </em><i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<s:if test="%{#attr.topicValue == 'manageownership'}"><fmt:message key="tooltip.managetrialowner.trial_list" /></s:if>
            <s:else><fmt:message key="tooltip.managesiteowner.trial_list" /></s:else>" data-placement="top" data-trigger="hover"></i></p>
             <table id="conttbl" >
                  <tbody>
                    <tr width="100%">
                      <td width="45%" nowrap="nowrap" align="center"><h3 class="notAssigned"><s:if test="%{#attr.topicValue == 'manageownership'}">All Available Trials</s:if><s:else>All Available Abbreviated Trials</s:else><i class="fa-question-circle help-text left" id="popover" rel="popover" data-content="<fmt:message key="tooltip.select_trial" />" data-placement="top" data-trigger="hover"></i></h3></td>
                      <td width="10%"></td>
                      <td width="45%" nowrap="nowrap" align="center"><h3 class="assigned"><s:if test="%{#attr.topicValue == 'manageownership'}">Trial</s:if><s:else>Site</s:else> Owner Assignments<i class="fa-question-circle help-text left" id="popover" rel="popover" data-content="<fmt:message key="tooltip.select_trial" />" data-placement="top" data-trigger="hover"></i></h3></td>
                    </tr>
                    <tr>
                      <td width="45%">
                          <div class="trial-ownership-container">
                             <s:set name="orgTrials" value="studyProtocols" scope="request"/>
                             <display:table class="table-striped  table trialOwnership " summary="This table contains trials assigned to the site"
                                            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" id="studyProtocolRow"
                                            name="orgTrials" requestURI="${actionName}view.action" export="false">
                                 <display:column headerClass="no-sort" title="<input type='checkbox' name='trialIds'  id='trialIdsAll' value='0' onclick='selectAll(document.formManageTrialOwnership.trialIds, this.checked)' />">
                                   <label for="${studyProtocolRow.studyProtocol.id}" style="display:none"><fmt:message key="managetrialownership.trials.allow"/></label>
                                   <input type="checkbox" name="trialIds" value="${studyProtocolRow.studyProtocol.id}" id="${studyProtocolRow.studyProtocol.id}" />
                                 </display:column>
                                 <display:column titleKey="managetrialownership.trials.nciidentifier" property="nciIdentifier" headerScope="col"/>
                                 <display:column titleKey="managetrialownership.trials.leadOrgId" property="leadOrgId" headerScope="col"/>
                             </display:table>
                          </div>
                     </td>
                   <td width="10%" valign="middle" nowrap="nowrap" align="center" class="accrual_btn_column"><div> <b>Assign</b> </div>
                       <div>
                          <button type="button" onkeypress="updateOwnership(true);" onclick="updateOwnership(true);"  class="btn btn-light" data-placement="left" rel="tooltip" data-original-title="Assign selected"><i class="fa-angle-right"></i></button>
                       </div>
                       <br/><br/>
                       <div> <b>Unassign</b> </div>
                       <div>
                           <button type="button" onkeypress="updateOwnership(false);" onclick="updateOwnership(false);"  class="btn btn-default" data-placement="right" rel="tooltip" data-original-title="Unassign selected"><i class="fa-angle-left"></i></button>
                       </div>
                   </td>
                   <td width="45%">
                     <div class="trial-ownership-container">
                       <s:set name="trialOwenership" value="trialOwnershipInfo" scope="request"/>
                       <display:table class="table trialOwnership table-striped  " 
                                     decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" id="trialOwenership"
                                     name="trialOwenership" requestURI="${actionName}view.action" export="false">
                           <display:column headerClass="no-sort" title="<input type='checkbox' value='0_0' name='trialOwners' id='trialOwners'  onclick='selectAll(document.formManageTrialOwnership.trialOwners, this.checked)' />">
                             <c:set var="trialOwnersId" value="${trialOwenership.trialId}_${trialOwenership.userId}" />
                                <label for="${trialOwnersId}" style="display:none"><fmt:message key="managetrialownership.trials.allow"/></label>
                                <input type="checkbox" name="trialOwners" value="${trialOwnersId}" id="${trialOwnersId}"/>
                           </display:column>
                           <display:column escapeXml="true" titleKey="managetrialownership.users.name" maxLength="200" headerScope="col">
                                <c:out value="${trialOwenership.lastFirstName}"/>
                           </display:column>
                           <display:column titleKey="managetrialownership.trials.nciidentifier" property="nciIdentifier" headerScope="col"/>
                           
                           <s:if test="%{#attr.topicValue == 'manageownership'}">
                           <display:column titleKey="managetrialownership.trials.leadOrgId" property="leadOrgId" headerScope="col"/>
                           <display:column headerClass="no-sort" title='<div><span class="wrap">Email <br/>
                                  Notification?
                                  <div class="btn-group">
                                    <button data-toggle="dropdown" class="btn btn-default dropdown-toggle btn-tiny" type="button">All <span class="caret"></span></button>
                                    <ul id="emailMenu" role="menu" class="dropdown-menu">
                                      <li><a href="#" onclick="setEmailPrefAll(true)">Select <strong>Yes</strong> for all</a></li>
                                      <li><a href="#" onclick="setEmailPrefAll(false)">Select <strong>No</strong> for all</a></li>
                                    </ul>
                                  </div>
                                  </span></div>' headerScope="col">
                               <div class="btn-group" data-toggle="buttons">
                                 <label id="yes_${trialOwenership.trialId}_${trialOwenership.userId}" name="emailYes" onclick="setEmailPref('${trialOwenership.trialId}','${trialOwenership.userId}', true)" class="btn btn-default btn-tiny <s:if test='%{#attr.trialOwenership.emailsEnabled}'>active</s:if> ">
                                   <input type="radio" name="options">
                                   Yes </label>
                                 <label id="no_${trialOwenership.trialId}_${trialOwenership.userId}" name="emailNo" onclick="setEmailPref('${trialOwenership.trialId}', '${trialOwenership.userId}', false)" class="btn btn-default btn-tiny <s:if test='%{!#attr.trialOwenership.emailsEnabled}'>active</s:if>" >
                                   <input type="radio" name="options">
                                   No </label>
                               </div>
                           </display:column>  
                           </s:if>
                           <s:else>
                           <display:column titleKey="managetrialownership.trials.siteId" property="leadOrgId" headerScope="col"/>
                           </s:else>
                       </display:table>
                     </div>
                   </td>
                </tr>
              </tbody>
            </table>             
            <div class="line"></div>
         </s:form>
         <script type="text/javascript">
         document.getElementById("hideAll").style.display = "none";
         </script> 
    </body>
</html>
