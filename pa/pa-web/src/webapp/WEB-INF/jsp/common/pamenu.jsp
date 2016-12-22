<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:form name="qForm">
    <li class="stdnav">
        <div><fmt:message key="pamenu.abstraction"/></div>
        <ul>
            <c:if test="${sessionScope.isAnyAbstractor}">
	            <li><a href="javascript:void(0);" class="fakelink"><fmt:message key="pamenu.abstraction.dashboard"/></a></li>
	            <li class="stdsub">
	                <ul>
	                <c:if test="${sessionScope.isAbstractor==true}">
	                    <c:set var="dashboardMenu"  value="pamenu.abstraction.dashboardAbs" />
	                </c:if>
	                <c:if test="${sessionScope.isAdminAbstractor==true}">
	                   <c:set var="dashboardMenu" value="pamenu.abstraction.dashboardAdmin" />
	                </c:if>
	                <c:if test="${sessionScope.isScientificAbstractor==true}">
	                   <c:set var="dashboardMenu" value="pamenu.abstraction.dashboardSci" />
	                </c:if>
	                <c:if test="${sessionScope.isAdminAbstractor==true && sessionScope.isScientificAbstractor==true}">
	                    <c:set var="dashboardMenu"  value="pamenu.abstraction.dashboardAbsSci" />
	                </c:if>
	                <c:if test="${sessionScope.isSuAbstractor==true}">
	                    <c:set var="dashboardMenu" value="pamenu.abstraction.dashboardSuAbs" />
	                </c:if>
	                   <c:if test="${sessionScope.isAdminAbstractor==true
	                                ||sessionScope.isScientificAbstractor==true ||sessionScope.isSuAbstractor==true}">
	                       <pa:menuLink href="${pageContext.request.contextPath}/protected/dashboard.action" id="dashboardMenuOption" labelKey="${dashboardMenu}" selected="${requestScope.topic == 'dashboard'}"/>            
	                   </c:if>
	                   
	                   <c:if test="${sessionScope.isResultsAbstractor}">
	                       <pa:menuLink href="${pageContext.request.contextPath}/protected/resultsDashboard.action" id="resultsDashboardMenuOption" labelKey="pamenu.results.dashboard" selected="${requestScope.topic == 'resultsDashboard'}"/>
	                   </c:if>
	                </ul>
	            </li>
            </c:if>
            
            <li><a href="javascript:void(0);" class="fakelink"><fmt:message key="pamenu.abstraction.search"/></a></li>
            <li class="stdsub">
                <ul>
		            <pa:menuLink href="${pageContext.request.contextPath}/protected/studyProtocolexecute.action" id="trialSearchMenuOption" labelKey="pamenu.abstraction.search.trials" selected="${requestScope.topic == 'searchtrial'}"/>
                    <pa:menuLink href="${pageContext.request.contextPath}/protected/personsSearchexecute.action" id="personSearchMenuOption" labelKey="pamenu.abstraction.search.persons" selected="${requestScope.topic == 'searchperson'}"/>
                    <pa:menuLink href="${pageContext.request.contextPath}/protected/organizationsSearchexecute.action" id="organizationSearchMenuOption" labelKey="pamenu.abstraction.search.organizations" selected="${requestScope.topic == 'searchorganization'}"/>
	            </ul>            
            </li>
            <pa:menuLink href="${pageContext.request.contextPath}/protected/userAccountDetailsexecute.action" id="userAccountDetailsMenuOption" labelKey="pamenu.abstraction.useraccountdetails" selected="${requestScope.topic == 'accountdetails'}"/>
            <c:if test="${sessionScope.isSecurityAdmin}">
                <pa:menuLink href="${pageContext.request.contextPath}/security/manageUserGroups.action" id="manageUserGroupsMenuOption" labelKey="pamenu.abstraction.manageUserGroups" selected="${requestScope.topic == 'manageUserGroups'}"/>
            </c:if>
            <c:if test="${sessionScope.isAnyAbstractor}">
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/registeredUserDetailsexecute.action" id="registeredUserDetailsMenuOption" labelKey="pamenu.abstraction.reguserdetails" selected="${requestScope.topic == 'userdetails'}"/>            
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/inboxProcessingexecute.action" id="inboxProcessingMenuOption" labelKey="pamenu.abstraction.inbox" selected="${requestScope.topic == 'inboxaccess'}"/>
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/manageSiteAdmins.action" id="manageSiteAdminsMenuOption" labelKey="pamenu.abstraction.manageSiteAdmins" selected="${requestScope.topic == 'siteadmins'}"/>
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/bioMarkersexecute.action" id="newMarkerRequestMenuOption" labelKey="pamenu.new.marker.request" selected="${requestScope.topic == 'biomarkers'}"/>
	            <li><a href="javascript:void(0);" class="fakelink"><fmt:message key="pamenu.ctgov"/></a></li>
	            <li class="stdsub">
	                <ul>
	                    <pa:menuLink href="${pageContext.request.contextPath}/protected/importCtGovexecute.action" id="importCtGovMenuOption" labelKey="pamenu.importCtGov" selected="${requestScope.topic == 'ctimport'}"/>
	                    <pa:menuLink href="${pageContext.request.contextPath}/protected/ctGovImportLogexecute.action" id="ctGovImportLogMenuOption" labelKey="pamenu.ctGovImportLogs" selected="${requestScope.topic == 'ctimportlog'}"/>
	                </ul>            
	            </li>            
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/pendingAccrualsexecute.action" id="pendingAccrualsMenuOption" labelKey="pamenu.pendingAccruals" selected="${requestScope.topic == 'pendingaccruals'}"/>
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/outOfScopeAccruals.action" id="outOfScopeAccrualsMenuOption" labelKey="pamenu.outOfScopeAccruals" selected="${requestScope.topic == 'outofscopeaccruals'}"/>            
	            <pa:menuLink href="${pageContext.request.contextPath}/../registry/protected/submitProprietaryTrial.action?sum4FundingCatCode=Industrial" id="submitProprietaryTrialMenuOption" labelKey="pamenu.submitProprietaryTrial" newWindow="${true}"/>
	            <pa:menuLink href="${pageContext.request.contextPath}/protected/manageTerms.action" id="manageTermsMenuOption" labelKey="pamenu.manageTerms" selected="${requestScope.topic == 'manageterms'}"/>
	            <c:if test="${sessionScope.isSuAbstractor}">
	                <pa:menuLink href="${pageContext.request.contextPath}/protected/manageFlaggedTrials.action" id="manageFlaggedTrialsOption" 
	                    labelKey="pamenu.abstraction.manageFlaggedTrials" selected="${requestScope.topic == 'manageFlaggedTrials'}"/>            
	            </c:if>
            </c:if>             
            <c:if test="${pageContext.request.remoteUser != null}">
                <pa:menuLink href="${pageContext.request.contextPath}/logout.action" id="logoutMenuOption" labelKey="pamenu.abstraction.logout"/>
            </c:if>
        </ul>
    </li>

    <c:if test="${sessionScope.trialSummary != null && (sessionScope.isAbstractor || sessionScope.isSuAbstractor || sessionScope.isReportViewer)}">
        <c:set var="status" value="${ (sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'On-Hold') ? sessionScope.trialSummary.lastOffHollStatusCode.code : sessionScope.trialSummary.documentWorkflowStatusCode.code}"/>
        <c:choose>
            <c:when test="${status == 'Submitted' || status == 'Amendment Submitted'}">
                <c:set var="menuStatus" value="submitted" />
            </c:when>
            <c:when test="${status == 'Rejected' || status == 'Submission Terminated'}">
                <c:set var="menuStatus" value="rejected" />
            </c:when>
            <c:otherwise>
                <c:set var="menuStatus" value="accepted" />
            </c:otherwise>
        </c:choose>
        <li class="sub">
            <div><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></div>
            <ul>
                <li>
                    <div><fmt:message key="pamenu.overview"/></div>
                    <ul>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/studyProtocolview.action?studyProtocolId=${sessionScope.trialSummary.studyProtocolId}" labelKey="pamenu.overview.identification" selected="${requestScope.topic == 'trialdetails'}"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/trialHistory.action" labelKey="pamenu.overview.history" selected="${requestScope.topic == 'trialhistory'}"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/milestoneview.action" labelKey="pamenu.overview.milestone" selected="${requestScope.topic == 'trialmilestones'}"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/onhold.action" labelKey="pamenu.overview.onhold" selected="${requestScope.topic == 'trialonhold'}"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/manageAccrualAccess.action" labelKey="pamenu.overview.accrualaccess" selected="${requestScope.topic == 'accrualaccess'}"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/ajaxAbstractionCompletionviewTSR.action" labelKey="pamenu.overview.viewTsr"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/assignOwnershipview.action" labelKey="pamenu.overview.assignOwnership" selected="${requestScope.topic == 'recordownership'}"/>                        
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/checkOutHistory.action" labelKey="pamenu.overview.checkOutHistory" selected="${requestScope.topic == 'checkouthistory'}"/>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/trialAssociationsquery.action" labelKey="pamenu.overview.trialAssociations" selected="${requestScope.topic == 'associatetrial'}"/>
                    </ul>
                </li>
                <c:if test="${menuStatus == 'submitted'}">
                    <div><fmt:message key="pamenu.validation"/></div>
                    <ul>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/trialDocumentquery.action" labelKey="pamenu.admin.documents" selected="${requestScope.topic == 'reviewdocs'}"/>
                        <c:choose>
                            <c:when test="${sessionScope.trialSummary.proprietaryTrial}">
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/participatingOrganizations.action" labelKey="pamenu.admin.participatingSites" selected="${requestScope.topic == 'abstractsite'}"/>
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/regulatoryInfoquery.action" labelKey="pamenu.admin.regulatory" selected="${requestScope.topic == 'reviewregulatory'}"/>
                            </c:when>
                            <c:otherwise>
                                <pa:menuLink id="studyOverallStatusLink" href="${pageContext.request.contextPath}/protected/studyOverallStatus.action" labelKey="pamenu.admin.status" selected="${requestScope.topic == 'reviewstatus'}"/>
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/trialFundingquery.action" labelKey="pamenu.admin.funding" selected="${requestScope.topic == 'reviewfunding'}"/>
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/trialIndidequery.action" labelKey="pamenu.admin.indide" selected="${requestScope.topic == 'reviewind'}"/> 
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/regulatoryInfoquery.action" labelKey="pamenu.admin.regulatory" selected="${requestScope.topic == 'reviewregulatory'}"/> 
                            </c:otherwise>
                        </c:choose>
                        <pa:menuLink href="${pageContext.request.contextPath}/protected/trialValidationquery.action?studyProtocolId=${sessionScope.trialSummary.studyProtocolId}" labelKey="pamenu.validation.validation" selected="${requestScope.topic == 'validatetrial'}"/>
                    </ul>
                </c:if>
                <c:if test="${menuStatus == 'accepted'}">
                    <li>
                        <div><fmt:message key="pamenu.admin"/></div>
                        <ul>
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/generalTrialDesignquery.action" labelKey="pamenu.admin.general" selected="${requestScope.topic == 'abstractgeneral'}"/> 
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/nciSpecificInformationquery.action" labelKey="pamenu.admin.nciSpecific" selected="${requestScope.topic == 'abstractnci'}"/> 
                            
                           <li class="hassubmenu">
                               <fmt:message key="pamenu.admin.regulatory"/>
                               <ul id="part_sites">
                                   <pa:menuLink href="${pageContext.request.contextPath}/protected/regulatoryInfoquery.action" labelKey="pamenu.admin.regulatory" selected="${requestScope.topic == 'abstractregulatory'}"/> 
                                   <pa:menuLink href="${pageContext.request.contextPath}/protected/irb.action" labelKey="pamenu.admin.safety" selected="${requestScope.topic == 'abstractsafety'}"/>
                                   <pa:menuLink href="${pageContext.request.contextPath}/protected/trialIndidequery.action" labelKey="pamenu.admin.indide" selected="${requestScope.topic == 'abstractind'}"/> 
                              </ul>
                           </li>
                           <pa:menuLink id="studyOverallStatusLink" href="${pageContext.request.contextPath}/protected/studyOverallStatus.action" labelKey="pamenu.admin.status" selected="${requestScope.topic == 'abstractstatus'}"/>
                           <pa:menuLink href="${pageContext.request.contextPath}/protected/trialFundingquery.action" labelKey="pamenu.admin.funding" selected="${requestScope.topic == 'abstractfunding'}"/> 
                            
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/participatingOrganizations.action" labelKey="pamenu.admin.participatingSites" selected="${requestScope.topic == 'abstractsite'}"/>
                            
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/collaborators.action" labelKey="pamenu.admin.collaborators" selected="${requestScope.topic == 'abstractcollaborator'}"/>
                            
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/trialDocumentquery.action" labelKey="pamenu.admin.documents" selected="${requestScope.topic == 'abstractdocs'}"/>
                        </ul>
                    </li>
                    <li>
                        <div><fmt:message key="pamenu.scientific"/></div>
                        <ul>
                            
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/trialDescriptionquery.action" labelKey="pamenu.scientific.description" selected="${requestScope.topic == 'abstractdescription'}"/>
                                <c:choose>
                                    <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                                        <li class="hassubmenu">
                                            <fmt:message key="pamenu.scientific.interventionalDesign"/>
                                            <ul id="part_sites">
                                                <pa:menuLink href="${pageContext.request.contextPath}/protected/interventionalStudyDesigndetailsQuery.action" labelKey="pamenu.scientific.design" selected="${requestScope.topic == 'abstractdesign'}"/>
                                                <pa:menuLink href="${pageContext.request.contextPath}/protected/interventionalStudyDesignoutcomeQuery.action" labelKey="pamenu.scientific.outcome" selected="${requestScope.topic == 'abstractoutcome'}"/>
                                                <pa:menuLink href="${pageContext.request.contextPath}/protected/eligibilityCriteriaquery.action" labelKey="pamenu.scientific.eligibility" selected="${requestScope.topic == 'abstracteligibility'}"/>
                                            </ul>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="hassubmenu">
                                            <fmt:message key="pamenu.scientific.observationalDesign"/>
                                            <ul id="part_sites">
                                                <pa:menuLink href="${pageContext.request.contextPath}/protected/noninterventionalStudyDesigndetailsQuery.action" labelKey="pamenu.scientific.design" selected="${requestScope.topic == 'abstractdesignnoninterventional'}"/>
                                                <pa:menuLink href="${pageContext.request.contextPath}/protected/interventionalStudyDesignoutcomeQuery.action" labelKey="pamenu.scientific.outcome" selected="${requestScope.topic == 'abstractoutcomenoninterventional'}"/>
                                                <pa:menuLink href="${pageContext.request.contextPath}/protected/eligibilityCriteriaquery.action" labelKey="pamenu.scientific.eligibility" selected="${requestScope.topic == 'abstracteligibilitynoninterventional'}"/>
                                            </ul>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/disease.action" labelKey="pamenu.scientific.disease" selected="${requestScope.topic == 'abstractdisease'}"/>
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/anatomicSite.action" labelKey="pamenu.scientific.anatomicSite" selected="${requestScope.topic == 'anatomicsite'}"/>
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/plannedMarker.action" labelKey="pamenu.scientific.markers" selected="${requestScope.topic == 'plannedmarker'}"/>
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/trialInterventions.action" labelKey="pamenu.scientific.interventions" selected="${requestScope.topic == 'abstractinterventions'}"/>
                            
                                <c:choose>
                                    <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                                        <pa:menuLink href="${pageContext.request.contextPath}/protected/trialArms.action" labelKey="pamenu.scientific.arms" selected="${requestScope.topic == 'abstractarms'}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <pa:menuLink href="${pageContext.request.contextPath}/protected/trialArms.action" labelKey="pamenu.scientific.groups" selected="${requestScope.topic == 'abstractcohort'}"/>
                                    </c:otherwise>
                                </c:choose>
                                <pa:menuLink href="${pageContext.request.contextPath}/protected/subGroupsquery.action" labelKey="pamenu.scientific.subGroups" selected="${requestScope.topic == 'abstractsubgroups'}"/>
                            
                        </ul>
                    </li>
                    <li>
                        <div><fmt:message key="pamenu.completion"/></div>
                        <ul>
                            <pa:menuLink href="${pageContext.request.contextPath}/protected/abstractionCompletionquery.action" labelKey="pamenu.completion.abstraction" selected="${requestScope.topic == 'validateabstract'}"/>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </li>
    </c:if>
</s:form>
