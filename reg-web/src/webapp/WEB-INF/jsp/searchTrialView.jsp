<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="view.trial.page.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js?534785924'/>"></script>
        <script type="text/javascript" language="javascript">
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }
            
            function handleBackAction(){
                window.history.back();        
            }
            function viewAction(url){
                    var pid = getUrlVars()["studyProtocolId"];
                    if (pid == undefined) {
                        pid = ${requestScope.trialIdentifier};
                    }
                    document.forms[0].setAttribute("action", url+"?studyProtocolId="+pid);
                    document.forms[0].submit();    
               
            }
             function getUrlVars() {
                var vars = {};
                var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
                    vars[key] = value;
                });
                return vars;
            }
        </script>
    </head>

    <body onload="setFocusToFirstControl();">
            <h1 class="heading"><span><fmt:message key="search.trial.view.page.title" /></h1>            
            <c:set var="topic" scope="request" value="viewresult"/>
                <reg-web:sucessMessage/>
                <reg-web:failureMessage/>
                <c:if test="${showVerifyButton or showAddMySite}">
                  <div class="align-left button-row">
                    <c:if test="${showVerifyButton}">
                        <s:url id="viewUrl" namespace="/protected" action="trialDataVerificationview"/>
                        <s:a onclick="javascript:viewAction('%{viewUrl}');"  href="javascript:void(0)"><button type="button" class="btn btn-icon btn-primary"><i class="fa-floppy-o"></i>Verify Trial Data</button></s:a>
                    </c:if>
                    <c:if test="${showAddMySite}"> 
                        <s:url id="addMySiteUrl" action="addSitepopupview">
                                <s:param name="studyProtocolId"><c:out value= "${requestScope.trialIdentifier}"/></s:param>
                        </s:url>
                        <s:a onclick="showPopup('%{addMySiteUrl}', '', 'Add Participating Site');" onkeypress="showPopup('%{addMySiteUrl}', '', 'Add Participating Site');"  href="javascript:void(0)"><button type="button" class="btn btn-icon btn-primary"><i class="fa-plus"></i>Add My Site</button></s:a>
                    </c:if>
                  </div>
               </c:if>
            
                <c:if test="${param.trialAction == 'submit'}">
                    <div class="alert alert-success">
                        <strong>The trial has been successfully submitted and assigned the NCI Identifier ${requestScope.trialDTO.assignedIdentifier}</strong>
                    </div>
                </c:if>
                <c:if test="${param.trialAction == 'amend'}">
                    <div class="alert alert-success">
                        <strong>The amendment to trial with the NCI Identifier ${requestScope.trialDTO.assignedIdentifier} was successfully submitted.</strong>
                    </div>
                </c:if>
                <c:if test="${param.trialAction == 'update'}">
                    <div class="alert alert-success">
                        <strong>
                            The trial update with the NCI Identifier
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                ${requestScope.trialDTO.assignedIdentifier}
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                ${requestScope.assignedIdentifier}
                            </c:if>
                           was successfully submitted.
                       </strong>
                    </div>
                </c:if>
                <s:form> 
                    <s:token/>
                    <s:actionerror/>
                    <s:hidden name="criteria.identifierType" />
                    <s:hidden name="criteria.identifier" />
                    <s:hidden name="criteria.officialTitle" />
                    <s:hidden name="criteria.organizationId" />
                    <s:hidden name="criteria.participatingSiteId" />
                    <s:hidden name="criteria.phaseCode" />
                    <s:hidden name="criteria.primaryPurposeCode" />
                    <s:hidden name="criteria.organizationType" />
                    <s:hidden name="criteria.myTrialsOnly" />
                    <s:hidden name="criteria.principalInvestigatorId" />
                        <reg-web:titleRowDiv titleKey="view.trial.trialIDs"/>
                        <div class="row form-horizontal details">
                        <reg-web:valueRowDiv labelKey="view.trial.identifier" strong="true" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.assignedIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.assignedIdentifier}"/>
                            </c:if>
                        </reg-web:valueRowDiv>
                        <reg-web:valueRowDiv labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.leadOrgTrialIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.leadOrgTrialIdentifier}"/>
                            </c:if>
                        </reg-web:valueRowDiv>
                        <c:if test="${requestScope.trialDTO.nctIdentifier != null}">
                            <reg-web:valueRowDiv labelKey="view.trial.nctNumber" noLabelTag="true">
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                    <c:out value="${requestScope.trialDTO.nctIdentifier }"/>
                                </c:if>
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                    <c:out value="${requestScope.nctIdentifier }"/>
                                </c:if>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.dcpIdentifier != null}">
                            <reg-web:valueRowDiv labelKey="submit.trial.dcpIdentifier" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.dcpIdentifier}"/>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.ctepIdentifier != null}">
                            <reg-web:valueRowDiv labelKey="submit.trial.ctepIdentifier" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.ctepIdentifier}"/>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.amendmentDate.value != null}">
                            <reg-web:titleRowDiv titleKey="trial.amendDetails"/>
                            
                            <reg-web:valueRowDiv labelKey="view.trial.amendmentNumber" noLabelTag="true">
                                <c:if test="${requestScope.trialSummary.amendmentNumber.value != null}">
                                    <c:out value="${requestScope.trialSummary.amendmentNumber.value}"/>
                                </c:if>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.amendmentDate" noLabelTag="true">
                                <fmt:formatDate value="${requestScope.trialSummary.amendmentDate.value}"/>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.secondaryIdentifierList != null && fn:length(requestScope.trialDTO.secondaryIdentifierList) > 0}">
                            <reg-web:titleRowDiv titleKey="view.trial.otherIdentifiers"/>
                            <s:set name="records" value="requestScope.trialDTO.secondaryIdentifierList" scope="request"/>
                            <tr>
                                <td colspan="2">
                                    <ul>
                                        <c:forEach var="item" items="${requestScope.trialDTO.secondaryIdentifierList}">
                                            <li><c:out value="${item.extension}"/></li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:if>
                        </div>
                        <reg-web:titleRowDiv titleKey="view.trial.trialDetails"/>
                        <div class="row form-horizontal details">
                        <reg-web:valueRowDiv labelKey="view.trial.title" noLabelTag="true">
                            <fmt:message key="studyAlternateTitles.text" var="title" />
                            <c:out value="${requestScope.trialSummary.officialTitle.value}"/>
                            <c:if test="${not empty trialSummary.studyAlternateTitles}">
                               <a href="javascript:void(0)" title="${title}" onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>                                                   
                            </c:if>
                        </reg-web:valueRowDiv>
                        
                        <reg-web:valueRowDiv labelKey="view.trial.phase" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.phaseCode.code }"/>
                        </reg-web:valueRowDiv>
                        <c:if test="${requestScope.trialSummary.phaseCode.code=='NA'}">
                            <reg-web:valueRowDiv labelKey="view.trial.otherPhaseText" noLabelTag="true">                                
                                <c:out value="${requestScope.trialSummary.phaseAdditionalQualifierCode.code=='Pilot'?'Yes':'No'}"/>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <c:if test="${not empty trialSummary.studyProtocolType.value}">
                            <reg-web:valueRowDiv labelKey="view.trial.type" noLabelTag="true">
                                <c:if test="${trialSummary.studyProtocolType.value=='InterventionalStudyProtocol' || trialSummary.studyProtocolType.value=='Interventional'}">
                                    Interventional
                                </c:if>
                                <c:if test="${trialSummary.studyProtocolType.value=='NonInterventionalStudyProtocol' || trialSummary.studyProtocolType.value=='NonInterventional'}">
                                    Non-Interventional
                                </c:if>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <reg-web:valueRowDiv labelKey="view.trial.primaryPurpose" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.primaryPurposeCode.code}"/>
                        </reg-web:valueRowDiv>
                        <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                            <reg-web:valueRowDiv labelKey="view.trial.otherPurposeText" noLabelTag="true">
                                <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                            </reg-web:valueRowDiv>
                        </c:if>
                        <c:if test="${trialSummary.studyProtocolType.value=='InterventionalStudyProtocol' || trialSummary.studyProtocolType.value=='Interventional'}">
                            <c:set var="otherSecPurpose" scope="request" value="${false}"/>
                            <reg-web:valueRowDiv labelKey="view.trial.secondaryPurpose">           
                               <c:forEach items="${requestScope.trialSummary.secondaryPurposes.item}" var="st">
                                   <c:out value="${st.value}"/><br/>
                                   <c:set var="otherSecPurpose" scope="request" value="${otherSecPurpose==false?(st.value=='Other'):true}"/>
                               </c:forEach>
                            </reg-web:valueRowDiv>
                            <c:if test="${otherSecPurpose}">
                                <reg-web:valueRowDiv labelKey="view.trial.secOtherPurposeText" noLabelTag="true">
                                    <c:out value="${requestScope.trialSummary.secondaryPurposeOtherText.value}"/>
                                </reg-web:valueRowDiv>
                            </c:if>                     
                        </c:if>
                        <c:if test="${trialSummary.studyProtocolType.value!='InterventionalStudyProtocol' && trialSummary.studyProtocolType.value!='Interventional'}">
                            <reg-web:valueRowDiv labelKey="submit.trial.studySubtypeCode">                            
                                <c:out value="${trialSummary.studySubtypeCode.code}"/>
                            </reg-web:valueRowDiv>  
                            <reg-web:valueRowDiv labelKey="submit.trial.studyModelCode">                            
                                <c:out value="${trialSummary.studyModelCode.code}"/>
                            </reg-web:valueRowDiv>   
                            <c:if test="${trialSummary.studyModelCode.code == 'Other'}">
                                <reg-web:valueRowDiv labelKey="submit.trial.studyModelOtherText" noLabelTag="true">
                                    <c:out value="${trialSummary.studyModelOtherText.value}"/>
                                </reg-web:valueRowDiv>                                
                            </c:if>
                            <reg-web:valueRowDiv labelKey="submit.trial.timePerspectiveCode">                            
                                <c:out value="${trialSummary.timePerspectiveCode.code}"/>
                            </reg-web:valueRowDiv>   
                            <c:if test="${trialSummary.timePerspectiveCode.code == 'Other'}">
                                <reg-web:valueRowDiv labelKey="submit.trial.timePerspectiveOtherText" noLabelTag="true">
                                    <c:out value="${trialSummary.timePerspectiveOtherText.value}"/>
                                </reg-web:valueRowDiv>                                
                            </c:if>
                        </c:if>
                        <reg-web:valueRowDiv labelKey="view.trial.accrual.disease.term" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.accrualDiseaseCodeSystem.value}"/>
                        </reg-web:valueRowDiv>
                        </div>
                         <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                     || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                             <reg-web:titleRowDiv titleKey="view.trial.leadOrgInvestigator"/>
                         </c:if>
                         <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                     && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                             <reg-web:titleRowDiv titleKey="view.trial.leadOrganization"/>
                         </c:if>
                        <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrganization" noLabelTag="true">
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                            && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                    <c:out value="${requestScope.leadOrganizationName}"/>
                                </c:if>
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                            && requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                    <c:out value="${requestScope.trialDTO.leadOrganizationName}"/>
                                </c:if>
                            </reg-web:valueRowDiv>
                        
                            <c:if test="${(requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false')}">
                                <reg-web:valueRowDiv labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                    <c:out value="${requestScope.trialDTO.piName}"/>
                                </reg-web:valueRowDiv>        
                            </c:if>
                        </div>
                        <c:if test="${requestScope.trialDTO.xmlRequired == true && maskFields != true}">
                              <reg-web:titleRowDiv titleKey="view.trial.sponsorResParty"/>
                              <div class="row form-horizontal details">
                              <reg-web:valueRowDiv labelKey="view.trial.sponsor" noLabelTag="true">
                                  <c:out value="${requestScope.trialDTO.sponsorName}"/>
                              </reg-web:valueRowDiv>
                              <reg-web:valueRowDiv labelKey="view.trial.respParty" noLabelTag="true">
                                  <c:out value="${func:capitalize(requestScope.trialDTO.responsiblePartyTypeReadable)}"/>
                              </reg-web:valueRowDiv>
                              <c:if test="${fn:trim(requestScope.trialDTO.responsiblePersonName) != ''}">
                                  <reg-web:valueRowDiv labelKey="view.trial.respParty.investigator" noLabelTag="true">
                                      <c:out value="${requestScope.trialDTO.responsiblePersonName}"/>
                                  </reg-web:valueRowDiv>
                              </c:if>  
                              <c:if test="${fn:trim(requestScope.trialDTO.responsiblePersonTitle) != ''}">
                                  <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorTitle" noLabelTag="true">
                                      <c:out value="${requestScope.trialDTO.responsiblePersonTitle}"/>
                                  </reg-web:valueRowDiv>
                              </c:if>
                              <c:if test="${fn:trim(requestScope.trialDTO.responsiblePersonAffiliationOrgName) != ''}">
                                  <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorAff" noLabelTag="true">
                                      <c:out value="${requestScope.trialDTO.responsiblePersonAffiliationOrgName}"/>
                                  </reg-web:valueRowDiv>
                              </c:if>                                  
                              </div>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                    && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'
                                    && fn:length(requestScope.summaryFourOrgIdentifiers) > 0}">
                            <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                            <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="view.trial.SubmissionCategory" noLabelTag="true">
                                <c:out value="${requestScope.summaryFourFundingCategoryCode}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.FundingSponsor" noLabelTag="true">
                            <c:forEach items="${requestScope.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                            </c:forEach>
                                
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
                                    <c:out value="${empty requestScope.consortiaTrialCategoryCode?'Yes':'No - '}"/>
                                    <c:out value="${requestScope.consortiaTrialCategoryCode}"/>
                            </reg-web:valueRowDiv>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                    && requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'
                                    && fn:length(requestScope.trialDTO.summaryFourOrgIdentifiers) > 0}">
                            <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                            <div class="row form-horizontal details">
                                <reg-web:valueRowDiv labelKey="view.trial.FundingCategory" noLabelTag="true">
                                    <c:out value="${requestScope.trialDTO.summaryFourFundingCategoryCode}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="view.trial.FundingSponsor" noLabelTag="true">
                                <c:forEach items="${requestScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                    <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                                </c:forEach>
                                </reg-web:valueRowDiv>        
                            </div>                    
                        </c:if>
                       <c:if test="${not empty trialDTO.programCodesList}">
                            <c:if test="${empty requestScope.trialDTO.summaryFourOrgIdentifiers}">
                                <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                            </c:if>
                            <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="studyProtocol.summaryFourPrgCode" noLabelTag="true">
                               <table>
                                 <c:forEach items="${trialDTO.programCodesList}" var="element"> 
                                  <tr>
                                    <td>${element}</td>
                                  </tr>
                                 </c:forEach>
                                </table> 
                            </reg-web:valueRowDiv>
                            </div>
                        </c:if>


                        <c:if test="${requestScope.participatingSitesList != null && fn:length(requestScope.participatingSitesList) > 0}">
                            <reg-web:titleRowDiv titleKey="view.trial.participatingSites"/>
                            <div class="row form-horizontal details">
                              <display:table class="table table-striped table-bordered sortable data" sort="list"  uid="row"  name="${requestScope.participatingSitesList}" >
                                  <display:column escapeXml="true" title="Organization Name" property="name"  headerClass="sortable"/>
                                  <display:column escapeXml="true" title="Site Principal Investigator" property="investigator"  headerClass="sortable"/>
                                  <display:column escapeXml="true" title="Local Trial<br/> Identifier" property="siteLocalTrialIdentifier"  headerClass="sortable"/>
                                  <display:column title="Current Site<br/> Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
                                  <display:column title="Current Site<br/> Recruitment Status Date" property="recruitmentStatusDate"  headerClass="sortable"/>
                                  <display:column title="Date Opened <br/>for Accrual" property="dateOpenedforAccrual"  headerClass="sortable"/>
                                  <display:column title="Date Closed <br/>for Accrual" property="dateClosedforAccrual"  headerClass="sortable"/>
                              </display:table>
                            </div>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                            <%@ include file="/WEB-INF/jsp/nodecorate/viewStatusSection.jsp" %>
                        </c:if>
                    
                    <c:if test="${requestScope.studyIndIde != null}">
                          <h3 class="heading mt20"><span>FDA IND/IDE Information for applicable trials</span></h3>
                          <jsp:include page="/WEB-INF/jsp/searchTrialViewIndIde.jsp"/>
                    </c:if>
                    <c:if test="${requestScope.trialFundingList != null}">
                         <h3 class="heading mt20"><span>NIH Grant Information (for NIH funded Trials)</span></h3>
                         <jsp:include page="/WEB-INF/jsp/searchTrialViewGrants.jsp"/>
                    </c:if>
                    <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                || fn:trim(requestScope.trialSummary.proprietaryTrialIndicator.value) == 'false'
                                && requestScope.trialDTO.xmlRequired == true}">
                        
                            <reg-web:titleRowDiv titleKey="regulatory.title"/>
                            <div class="row form-horizontal details">
                            <!--  Trial Oversight Authority Country -->
                            <reg-web:valueRowDiv labelKey="regulatory.oversight.country.name" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.trialOversgtAuthCountryName}"/>
                            </reg-web:valueRowDiv>
                            <!--  Trial Oversight Authority Organization Name -->
                            <reg-web:valueRowDiv labelKey="regulatory.oversight.auth.name" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.trialOversgtAuthOrgName}"/>
                            </reg-web:valueRowDiv>
                            <!--   FDA Regulated Intervention Indicator-->
                            <reg-web:valueRowDiv labelKey="regulatory.FDA.regulated.interv.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.fdaRegulatoryInformationIndicator}" /> 
                            </reg-web:valueRowDiv>
                            <!--   Section 801 Indicator-->
                            <reg-web:valueRowDiv id="sec801row" labelKey="regulatory.section801.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.section801Indicator}" />
                            </reg-web:valueRowDiv>
                            <!--   Delayed Posting Indicator-->
                            <reg-web:valueRowDiv id="delpostindrow" labelKey="regulatory.delayed.posting.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.delayedPostingIndicator}" />
                            </reg-web:valueRowDiv>
                            <!--   Data Monitoring Committee Appointed Indicator -->
                            <reg-web:valueRowDiv id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.dataMonitoringCommitteeAppointedIndicator}" />
                            </reg-web:valueRowDiv>
                            </div>
                    </c:if>
                    <c:if test="${requestScope.protocolDocument != null}">
                       <h3 class="heading mt20"><span>Trial Related Documents</span></h3>
                       <jsp:include page="/WEB-INF/jsp/searchTrialViewDocs.jsp"/>
                    </c:if>
                    <c:if test="${pageContext.request.method=='GET' && fn:contains(header['Referer'],'/searchTrialquery.action')}">
                    <hr>
                    <div class="mt20 align-center">
                    <button type="button" class="btn btn-icon btn-primary" onclick="handleBackAction();this.blur();"><i class="fa-arrow-circle-left"></i>Back to Search Results</button>
                    </div>
                    </c:if>
                </s:form>
    </body>
</html>
