<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="assignOwbership.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script language="javascript" type="text/javascript">
            var orgid;
            var disableConfirmations = false;

            function setorgid(orgIdentifier) {
                orgid = orgIdentifier;
            }

            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                // there are no onload functions to call for this jsp
                // leave this function to prevent 'error on page'
            }

            function searchForUsers() {
                document.forms[0].action="assignOwnershipsearch.action";
                document.forms[0].submit();
            }

            function resetSearch() {
                document.getElementById("firstName").value="";
                document.getElementById("lastName").value="";
                document.getElementById("emailAddress").value="";
                document.forms[0].elements["criteria.affiliatedOrgName"].value="";
            }

            function assignOwner(userId, inFamily) {
             var trialCategory = $('trialCategory').value;
             if (inFamily == 'false') {
	             if (!disableConfirmations && !confirm("<fmt:message key='assignOwnership.notFamily.Confirm' />")) {
	            	 return;
	             }
             }
             if (trialCategory == 'Abbreviated Trial') {
                if (confirm("<fmt:message key='assignOwnership.industrial.Confirm' />")) {
                    document.forms[0].action="assignOwnershipsave.action?userId=" + userId;
                    document.forms[0].submit();
                }
             } else {
                 document.forms[0].action="assignOwnershipsave.action?userId=" + userId;
                 document.forms[0].submit();
             }
             
            }

            function removeOwner(userId) {
                document.forms[0].action="assignOwnershipremove.action?userId=" + userId;
                document.forms[0].submit();
            }

            function lookup4loadorg() {
                showPopup('${lookupOrgUrl}', loadOrgDiv, 'Select Organization');
            }

            function loadDiv() {
            }

            function loadOrgDiv() {
                var url = 'ajaxAssignOwnershipdisplayAffiliatedOrganization.action';
                var params = { orgId: orgid };
                var div = $('loadOrgField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function setEmailNotificationsPreference(userId, enableEmails) {
                $('ajaxIndicator').show();
                $('orderSaveConfirmation').hide(); 
                $('orderSaveError').hide(); 

                var ajaxReq = new Ajax.Request('assignOwnershipsaveEmailPreference.action', {
                    method: 'post',
                    parameters: 'userId='+userId+'&enableEmails='+enableEmails,
                    onSuccess: function(transport) {
                        $('ajaxIndicator').hide();
                        $('orderSaveConfirmation').show();
                    },
                    onFailure: function(transport) {
                        $('ajaxIndicator').hide();   
                        $('orderSaveError').show();
                    },
                    onException: function(requesterObj, exceptionObj) {
                        ajaxReq.options.onFailure(null);
                    },
                    on0: function(transport) {
                        ajaxReq.options.onFailure(transport);
                    }
                });
            }         

        </script>
    </head>
    <body>
        <h1><fmt:message key="assignOwbership.title"/></h1>
        <c:set var="topic" scope="request" value="recordownership"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <input type="hidden" name="trialCategory" id="trialCategory" value="${sessionScope.trialSummary.trialCategory}"/>
        <s:set name="trialOwners" value="trialOwners" scope="request"/>
        <div style="font-weight:bold;color:#333333">
        <fmt:message key="trialOwners.title"/>
        </div>
        <div class="box">

          <div id="ajaxIndicator" class="info" style="display: none;">
                <img alt="Indicator" align="middle" src="../images/loading.gif"/>&nbsp;<fmt:message key="assignOwnership.saving"/>
          </div>
          <div class="confirm_msg" style="display: none;" id="orderSaveConfirmation">
              <strong>Message.</strong>&nbsp;<fmt:message key="assignOwnership.saved"/>
          </div>
          <div class="error_msg" style="display: none;" id="orderSaveError">
              <strong>Message.</strong>&nbsp;<fmt:message key="assignOwnership.error"/>
          </div>         

        
            <display:table class="data" pagesize="10" id="row" name="trialOwners" requestURI="assignOwnershipsearch.action" export="false">
                <display:column titleKey="user.fullName" sortable="true" headerClass="sortable">
                    <c:out value="${row.regUser.firstName}"/> 
                    <c:out value="${row.regUser.lastName}"/>
                </display:column>
                <display:column titleKey="pending.emailAddress" sortable="true" headerClass="sortable">
                    <a href="<c:out value="${row.regUser.emailAddress}"/>"><c:out value="${row.regUser.emailAddress}"/></a>
                </display:column>
                <display:column titleKey="user.phone" property="regUser.phone" sortable="true" headerClass="sortable" />       
                <display:column titleKey="user.address" sortable="true" headerClass="sortable" >
                    <c:out value="${row.regUser.addressLine}"/><br/>
                    <c:out value="${row.regUser.city}"/>
                    <c:out value="${row.regUser.state}"/>
                    <c:out value="${row.regUser.postalCode}"/>
                </display:column>                
                <pa:displayWhenCheckedOut>
                    <display:column class="title" titleKey="assignOwnership.emailNotifications">
                        <label for="email_pref_chk_${row.regUser.id}" style="display:none">empty</label>
                        <input type="checkbox" id="email_pref_chk_${row.regUser.id}"
                            onclick="setEmailNotificationsPreference(${row.regUser.id}, this.checked);"
                            <c:if test="${row.enableEmails}">
                            checked="checked"
                            </c:if>
                        >
                    </display:column>                       
	                <display:column class="title" titleKey="studyProtocol.action">
	                    <a href="javascript:void(0)" onclick="removeOwner('${row.regUser.id}');">Remove Ownership</a>
	                </display:column>
                </pa:displayWhenCheckedOut>              
            </display:table>
        </div>
        
        <pa:displayWhenCheckedOut>
	        <div class="box"><pa:sucessMessage /> 
	            <s:if test="hasActionErrors()">
	                <div class="error_msg"><s:actionerror /></div>
	            </s:if> 
	            <s:form name="assignOwnershipForm" action="assignOwnershipview.action">
	                <pa:studyUniqueToken/>
	                <h2 id="search_form">Search Users</h2>
	                <table class="form">
	                    <tr>
	                        <td scope="row" class="label">
	                            <label for="firstName">
	                                <fmt:message key="assignOwnership.criteria.firstname" />
	                            </label>
	                        </td>
	                        <td>
	                            <s:textfield id="firstName" name="criteria.firstName" maxlength="200" size="100"
	                                         cssStyle="width:200px" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td scope="row" class="label">
	                            <label for="lastName"> 
	                                <fmt:message key="assignOwnership.criteria.lastname" />
	                            </label>
	                        </td>
	                        <td>
	                            <s:textfield id="lastName" name="criteria.lastName" maxlength="200" size="100"
	                                         cssStyle="width:200px" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td scope="row" class="label">
	                            <label for="emailAddress"> 
	                                <fmt:message key="assignOwnership.criteria.email" />
	                            </label>
	                        </td>
	                        <td>
	                            <s:textfield id="emailAddress" name="criteria.emailAddress" maxlength="200" size="100"
	                                         cssStyle="width:200px" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td scope="row" class="label">
	                            <label for="organization">
	                                <fmt:message key="assignOwnership.criteria.organization" />
	                            </label>
	                        </td>
	                        <td colspan="2">
	                            <div id="loadOrgField">
	                                <table>
	                                    <tr>
	                                        <td>
	                                            <s:textfield id="organization" label="Organization Name" name="criteria.affiliatedOrgName" 
	                                                         size="30" cssStyle="width:200px" readonly="true" 
	                                                         cssClass="readonly"/>
	                                        </td>
	                                        <td>
	                                            <a href="javascript:void(0)" class="btn" onclick="lookup4loadorg();" />
	                                                <span class="btn_img"><span class="organization">Look Up Org</span></span>
	                                            </a>
	                                            <s:hidden name="criteria.affiliatedOrgId" id="affiliatedOrgId" />
	                                        </td>
	                                    </tr>
	                                </table>
	                            </div> 
	                        </td>
	                    </tr>
	                </table>
	                <div class="actionsrow">
	                    <del class="btnwrapper">
	                        <ul class="btnrow">
	                            <li>
	                                <s:a href="javascript:void(0)" cssClass="btn" onclick="searchForUsers();">
	                                    <span class="btn_img"><span class="search"><fmt:message key="assignOwnership.buttons.search" /></span></span>
	                                </s:a> 
	                                <s:a href="javascript:void(0)" cssClass="btn" onclick="resetSearch();">
	                                    <span class="btn_img"><span class="cancel"><fmt:message key="assignOwnership.buttons.reset" /></span></span>
	                                </s:a>
	                            </li>
	                        </ul>
	                    </del>
	                </div>
	                <div class="line"></div>
	                <s:set name="users" value="users" scope="request" />
	                <s:if test="users != null">
	                    <h2 id="search_results">Search Results</h2>
	                    <s:hidden name="currentAction" />
	                    <display:table class="data" pagesize="10" id="results" name="users" requestURI="assignOwnershipsearch.action" export="false">
	                        <display:column  titleKey="pending.userFirstName" sortable="true" headerClass="sortable" >
	                           <c:out value="${results.regUser.firstName}"/> 
	                        </display:column>
	                        <display:column  titleKey="pending.userLastName"  sortable="true" headerClass="sortable">
	                           <c:out value="${results.regUser.lastName}"/> 
	                        </display:column>
	                        <display:column  titleKey="pending.emailAddress" sortable="true" headerClass="sortable">
	                           <c:out value="${results.regUser.emailAddress}"/> 
	                        </display:column>
	                        <display:column  titleKey="pending.inFamily" sortable="true" headerClass="sortable">
	                           <c:choose>
	                                <c:when test="${results.inFamily == true}">
	                                    <img src="../images/ico_check.gif" alt="Same Organization Family"/>
	                                </c:when>
	                                <c:otherwise>
	                                    <img src="../images/ico_cancel.gif" alt="Different Organization"/>
	                                </c:otherwise>
	                            </c:choose>
	                        </display:column>
	                        <display:column class="title" titleKey="studyProtocol.action">
	                            <c:choose>
	                                <c:when test="${results.owner == true}">
	                                    <a href="javascript:void(0)" onclick="removeOwner('${results.regUser.id}');">Remove Ownership</a>
	                                </c:when>
	                                <c:otherwise>
	                                    <a href="javascript:void(0)" onclick="assignOwner('${results.regUser.id}', '${results.inFamily}');">Assign Ownership</a>
	                                </c:otherwise>
	                            </c:choose>
	                        </display:column>
	                    </display:table>
	                </s:if>
	            </s:form>
	        </div>
        </pa:displayWhenCheckedOut>
    </body>
</html>