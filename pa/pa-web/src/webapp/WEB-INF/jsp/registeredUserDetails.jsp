<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="registeredUserDetails.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript">
            function handleAction() {
            	 displayWaitPanel();
                 $('registeredUserDetailsForm').action="registeredUserDetailsquery.action";
                 $('registeredUserDetailsForm').submit();
            }
            
            function displayOrgDetails(orgID) {
                var width = 650;
                var height = 450;
                if (Prototype.Browser.IE) {
                    width = 670;
                    height = 500;                   
                }
                showPopWin('organizationsSearchshowDetailspopup.action?orgID='+orgID, width, height, '', 'Organization Details');               
            }   
            
            function toggleSiteDisplay(trialId) {
            	var toggler = $('toggler_'+trialId);
            	var sites = $('sites_'+trialId);
            	if (sites.visible()) {
            		sites.hide();
            		toggler.innerHTML='+';
            	} else {
                    sites.show();
                    toggler.innerHTML='&ndash;';            		
            	}
            }
            
            Event.observe(window, "load", function (e) {
                var infoPane = $('scrollable_info_pane');
                if (infoPane!=null) {
                	var trialsOwnedTable = $('trialsOwned');
                	var trialsSubmittedTable = $('trialsSubmitted');                	
                	var maxTableWidth = Math.max(trialsOwnedTable!=null?trialsOwnedTable.getWidth():0, trialsSubmittedTable!=null?trialsSubmittedTable.getWidth():0);
                	var paneWidth = infoPane.getWidth();
                	var containerDiv = $('content');
                	var containerWidth = containerDiv.getWidth();
                	
                	if (containerWidth<maxTableWidth) {
                		containerDiv.style.width = (maxTableWidth+100)+"px";
                		infoPane.style.width = "100%";
                        if (trialsOwnedTable!=null) {
                            trialsOwnedTable.style.width = "98%";
                        }
                        if (trialsSubmittedTable!=null) {
                            trialsSubmittedTable.style.width = "98%";
                        }
                	}
                }
            });
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="registeredUserDetails.title"/></h1>
        <c:set var="topic" scope="request" value="userdetails"/>
        <div class="box" id="filters">
            <div class="fixedTopHeader">
            <s:form id="registeredUserDetailsForm">        
                <pa:failureMessage/>
                <pa:sucessMessage/>                
                <table class="form">                   
                    <tr>
                        <td  scope="row" class="label" align="right">
                            <label for="selectedUserId"><fmt:message key="registeredUserDetails.selectUser"/></label>
                        </td>
                        <td>
                            <s:select name="selectedUserId" id="selectedUserId" cssStyle="width:300px;"
                                onchange="handleAction();"
                                list="model.userList" listKey="id" listValue="%{@org.apache.commons.lang.StringUtils@capitalize(lastName) + ', ' +  @org.apache.commons.lang.StringUtils@capitalize(firstName)}"
                                headerKey="" headerValue="" value="model.user.id" />
                        </td>
                    </tr>
                    <c:if test="${model.user!=null}">
	                    <tr>
	                        <td  scope="row" class="label" align="right">
	                            <fmt:message key="registeredUserDetails.address"/>
	                        </td>
	                        <td class="value">
	                            <c:if test="${not empty model.user.addressLine}">
                                    <c:out value="${model.user.addressLine}"></c:out> </br>
                                </c:if>
                                <c:if test="${not empty model.user.city}">
                                    <c:out value="${model.user.city}"></c:out>,  
                                </c:if>
                                <c:if test="${not empty model.user.state}">
                                    <c:out value="${model.user.state}"></c:out> 
                                </c:if>
                                <c:if test="${not empty model.user.postalCode}">
                                    <c:out value="${model.user.postalCode}"></c:out> 
                                </c:if>                 
                                <c:if test="${not empty model.user.country}">
                                    <br/>
                                    <c:out value="${model.user.country}"></c:out> 
                                </c:if>           
	                        </td>
	                    </tr>    
                        <tr>
                            <td  scope="row" class="label" align="right">
                                <fmt:message key="registeredUserDetails.email"/>
                            </td>
                            <td class="value">                                
                                <c:out value="${model.user.emailAddress}"/>                                          
                            </td>
                        </tr>
                        <tr>
                            <td  scope="row" class="label" align="right">
                                <fmt:message key="registeredUserDetails.orgAffiliation"/>
                            </td>
                            <td class="value">                                
                                <c:out value="${model.user.affiliateOrg}"/>                                          
                            </td>
                        </tr>
                        <tr>
                            <td  scope="row" class="label" align="right">
                                <fmt:message key="registeredUserDetails.prsOrg"/>
                            </td>
                            <td class="value">                                
                                <c:out value="${model.user.prsOrgName}"/>                                          
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <hr/>
                            </td>
                        </tr>                              
                    </c:if>
                </table>
            </s:form>
            </div>
            <c:if test="${model.user!=null}">
                <div class="scrollable_info_pane" id="scrollable_info_pane">
                <h3><fmt:message key="registeredUserDetails.siteAdminOrg"/></h3>            
                <display:table class="data" uid="adminOrg" 
                    name="model.siteAdminOrgs" requestURI="registeredUserDetailsquery.action">
                    <display:column titleKey="registeredUserDetails.poid">
                        <a href="javascript:void(0);" 
                        onclick="displayOrgDetails(<c:out value="${adminOrg.identifier.extension}"/>)">
                        <c:out value="${adminOrg.identifier.extension}"/></a>
                    </display:column>
                    <display:column escapeXml="true" titleKey="registeredUserDetails.name" 
                        property="name.part[0].value"/>                        
                </display:table>
                
                <br/>
                <h3><fmt:message key="registeredUserDetails.accrualAccess"/></h3>  
                <c:if test="${empty model.accrualSubmissionAccess}">
                    Nothing found to display.  <br/>
                </c:if>                          
                <c:forEach items="${model.accrualSubmissionAccess}" var="trial">
                    <div class="accrual_trial_head">
                        <span title="<fmt:message key="registeredUserDetails.togglerTip"/>"
                            onclick="toggleSiteDisplay(${trial.trialId});" 
                            class="toggler" id="toggler_${trial.trialId}">&ndash;</span>
                        <a class="title" title="<fmt:message key="registeredUserDetails.protLinkTip"/>" 
                            href="studyProtocolview.action?studyProtocolId=${trial.trialId}"><c:out value="${trial.trialNciId}"></c:out></a>                        
                        <span class="trial_title"><c:out value="${trial.trialTitle}"></c:out></span>
                    </div>
                    <div id="sites_${trial.trialId}">
	                    <c:forEach items="${trial.participatingSites}" var="site">
	                        <div class="accrual_site">
	                             <a href="javascript:void(0);" title="<fmt:message key="registeredUserDetails.orgTip"/>" 
	                                onclick="displayOrgDetails(${site.poId})">
	                                <c:out value="${site.name}"/></a>
	                        </div>
	                    </c:forEach>
                    </div>
                </c:forEach>
                
                <s:form name="sForm">
                    <s:token/>
                    
	                <br/>
	                <h3><fmt:message key="registeredUserDetails.trialsOwned"/></h3>
	                <c:set var="records" value="${model.trialsOwned}" scope="request"/>
	                <c:set var="requestURI" value="registeredUserDetailspaging.action" scope="request"/>
	                <c:set scope="request" var="displayTableUID" value="trialsOwned"/>
                    <jsp:include page="/WEB-INF/jsp/studyProtocolQueryResultsTable.jsp"/>
                    
	                <br/><br/>
	                <h3><fmt:message key="registeredUserDetails.trialsSubmitted"/></h3>
	                <c:set var="records" value="${model.trialsSubmitted}" scope="request"/>	     
	                <c:set scope="request"  var="displayTableUID" value="trialsSubmitted"/>                           
                    <jsp:include page="/WEB-INF/jsp/studyProtocolQueryResultsTable.jsp"/>
                
                </s:form>
                </div>
            </c:if>
        </div>        
    </body>
</html>