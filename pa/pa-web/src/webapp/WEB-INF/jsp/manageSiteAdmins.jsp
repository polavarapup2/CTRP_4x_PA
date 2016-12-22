<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="manageSiteAdmins.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript">
            function handleAction() {
            	 displayWaitPanel();
                 $('manageSiteAdminsForm').action="manageSiteAdminsquery.action";
                 $('manageSiteAdminsForm').submit();
            }
            
            var usersToAssign = new Array();
            var usersToAssignRowsSelected = new Array();
            
            var usersToUnassign = new Array();
            var usersToUnassignRowsSelected = new Array();    
            
            function assignAll(el) {
            	 displayWaitPanel();
                 $('manageSiteAdminsForm').action="manageSiteAdminsassignAll.action";
                 $('manageSiteAdminsForm').submit();
            }
            
            function unassignAll(el) {
            	 displayWaitPanel();
                 $('manageSiteAdminsForm').action="manageSiteAdminsunassignAll.action";
                 $('manageSiteAdminsForm').submit();
            }            
            
            function assignSelected(el) {
                if (usersToAssign.length == 0) {
                    alert('<fmt:message key="manageSiteAdmins.plzSelectToAssign"/>');          
                } else {
                    var studiesStr = '';
                    usersToAssign.each(function (s) {
                        studiesStr = studiesStr + s + ';';
                    });
                    el.form.usersToAssign.value = studiesStr;
                    displayWaitPanel();
                    $('manageSiteAdminsForm').action="manageSiteAdminsassign.action";
                    $('manageSiteAdminsForm').submit();
                }
            }
            
            function unassignSelected(el) {
                if (usersToUnassign.length == 0) {
                    alert('<fmt:message key="manageSiteAdmins.plzSelectToUnassign"/>');          
                } else {
                    var studiesStr = '';
                    usersToUnassign.each(function (s) {
                        studiesStr = studiesStr + s + ';';
                    });
                    el.form.usersToUnassign.value = studiesStr;
                    displayWaitPanel();
                    $('manageSiteAdminsForm').action="manageSiteAdminsunassign.action";
                    $('manageSiteAdminsForm').submit();
                }
            }
            
            function unassignedUserClicked(row, userId, evt) {
                if (usersToAssign.indexOf(userId)==-1) {
                    if (!isCTRL(evt)) {
                        usersToAssign = new Array();
                        clearRowSelections(usersToAssignRowsSelected);
                    }
                    row.className='user_selected';
                    usersToAssign.push(userId);
                    usersToAssignRowsSelected.push(row);
                } else {            
                    if (isCTRL(evt)) {
                        usersToAssign = usersToAssign.without(userId);
                        row.className='user_unselected';
                        usersToAssignRowsSelected = usersToAssignRowsSelected.without(row);
                    } else {
                        usersToAssign = new Array();
                        clearRowSelections(usersToAssignRowsSelected);
                        row.className='user_selected';
                        usersToAssign.push(userId);
                        usersToAssignRowsSelected.push(row);              
                    }
                }       
                 
                return stopEventPropagation(evt);
            }
            
            function assignedUserClicked(row, userId, evt) {
                if (usersToUnassign.indexOf(userId)==-1) {
                    if (!isCTRL(evt)) {
                        usersToUnassign = new Array();
                        clearRowSelections(usersToUnassignRowsSelected);
                    }
                    row.className='user_selected';
                    usersToUnassign.push(userId);
                    usersToUnassignRowsSelected.push(row);
                } else {            
                    if (isCTRL(evt)) {
                        usersToUnassign = usersToUnassign.without(userId);
                        row.className='user_unselected';
                        usersToUnassignRowsSelected = usersToUnassignRowsSelected.without(row);
                    } else {
                        usersToUnassign = new Array();
                        clearRowSelections(usersToUnassignRowsSelected);
                        row.className='user_selected';
                        usersToUnassign.push(userId);
                        usersToUnassignRowsSelected.push(row);              
                    }
                }       
                 
                return stopEventPropagation(evt);
            }
            
            function isCTRL(evt) {
                return evt.ctrlKey==true || evt.metaKey==true;
            }
            
            function stopEventPropagation(evt) {
                 Event.extend(evt);
                 evt.stop();
                 evt.cancelBubble = true;
                 
                 if (document.selection) {
                       document.selection.empty();
                 }        
                 return false;      
            }
            
            function clearRowSelections(rows) {
                rows.each(function (row) {
                    row.className='user_unselected';
                });
                rows.length = 0;
            }
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="manageSiteAdmins.title"/></h1>
        <c:set var="topic" scope="request" value="siteadmins"/>
        <div class="box" id="filters">
            <div class="fixedTopHeader">
	            <s:form id="manageSiteAdminsForm">   
	                <s:hidden name="usersToAssign" value=""/>
                    <s:hidden name="usersToUnassign" value=""/>
	                <s:token/>     
	                <pa:failureMessage/>
	                <pa:sucessMessage/>                
	                <table class="form">                   
	                    <tr>
	                        <td scope="row" class="label" align="right" nowrap="nowrap" width="35%">
	                            <label for="selectedOrganizationId"><fmt:message key="manageSiteAdmins.selectUser"/></label>
	                        </td>
	                        <td width="1%">
	                            <s:select name="selectedOrganizationId" id="selectedOrganizationId" cssStyle="width:300px;"
	                                onchange="handleAction();"
	                                list="model.organizations" listKey="identifier" listValue="name"
	                                headerKey="" headerValue="" value="selectedOrganizationId" />
	                        </td>
	                        <td class="info" align="left" >
	                           Only organizations that have affiliated users are shown in the list
	                        </td>
	                    </tr>                    
	                </table>
	                <br/>
	                <s:if test="selectedOrganizationId !=null">
		                <table class="form">
		                   <tr>
		                       <td class="info" align="center"><fmt:message key="manageSiteAdmins.selectHelp"/></td>
		                       <td></td>
		                       <td class="info" align="center"><fmt:message key="manageSiteAdmins.selectHelp"/></td>
	                       </tr>
	                       <tr>
                                <td width="45%" align="center" nowrap="nowrap"><h3>Members</h3></td>
                                <td></td>
                                <td width="45%"  align="center" nowrap="nowrap"><h3>Site Admins</h3></td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="user_list_container">
                                        <table width="100%">   
                                              <s:iterator value="model.members" var="user">   
                                                   <tr class="user_unselected"
                                                          onclick="unassignedUserClicked(this, ${user.id}, event);">                                                   
                                                       <td align="left">       
                                                          <s:property value="%{@org.apache.commons.lang.StringUtils@capitalize(#user.lastName) + ', ' +  @org.apache.commons.lang.StringUtils@capitalize(#user.firstName)}"/>
                                                          <c:if test="${user.affiliatedOrgUserType!=null && user.affiliatedOrgUserType.name=='PENDING_ADMIN'}">
                                                            <span class="info">(Pending Site Admin)</span>
                                                          </c:if>                                                                                                                                                                              
                                                       </td>
                                                   </tr>
                                              </s:iterator>   
                                        </table>
                                    </div>
                                </td>
                                <td class="user_btn_column" valign="middle" align="center" nowrap="nowrap">
                                    <div class="user_btn_column">
                                        <c:if test="${not empty model.members}">
                                            <div>
                                                <b>Assign</b>
                                            </div>
                                            <div>
                                                <input class="user_btn" type="button" value="&gt;"
                                                    title="Make selected member(s) site admins."
                                                    onclick="assignSelected(this);" onkeypress="assignSelected(this);"
                                                    />                                      
                                            </div>
                                            <div>
                                                <input class="user_btn"
                                                   title="Make all members in the list site admins."
                                                   onclick="assignAll(this);" onkeypress="assignAll(this);"
                                                   type="button" value="&gt;&gt;"/>                                      
                                            </div>  
                                            <br/><br/>
                                        </c:if>
                                        <c:if test="${not empty model.admins}">                                        
                                            <div>
                                                <b>Unassign</b>
                                            </div>
                                            <div>
                                                <input class="user_btn" type="button" value="&lt;" 
                                                   title="Make selected admin(s) ordinary members."
                                                   onclick="unassignSelected(this);" onkeypress="unassignSelected(this);"
                                                    />                                     
                                            </div>
                                            <div>
                                                <input class="user_btn" type="button" value="&lt;&lt;"
                                                   title="Make all admins in the list ordinary members."
                                                   onclick="unassignAll(this);" onkeypress="unassignAll(this);"
                                                   />                                      
                                            </div>                         
                                        </c:if>        
                                    </div>
                                </td>
                                <td>
                                    <div class="user_list_container">
                                        <table width="100%">                                                                                   
                                              <s:iterator value="model.admins" var="user">   
                                                   <tr class="user_unselected"
                                                          onclick="assignedUserClicked(this, ${user.id}, event);">                                                   
                                                       <td align="left">       
                                                          <s:property value="%{@org.apache.commons.lang.StringUtils@capitalize(#user.lastName) + ', ' +  @org.apache.commons.lang.StringUtils@capitalize(#user.firstName)}"/>                                                                                                                    
                                                       </td>
                                                   </tr>
                                              </s:iterator> 
                                        </table>
                                    </div>
                                </td>                               
                            </tr>
		                </table>   
	                </s:if>
	            </s:form>
            </div>
        </div>        
    </body>
</html>