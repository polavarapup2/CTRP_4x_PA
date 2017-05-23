<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="review.trial.page.title"/></title>
        <s:head/>
        
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="amendProtocol"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="submitProtocol"/>
        <script type="text/javascript" language="javascript">
        //<![CDATA[
            function submitTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action = "amendTrialamend.action";
                    document.forms[0].submit();
                    showPopWin('${amendProtocol}', 600, 200, '', 'Amend Trial');
                } else {
                    document.forms[0].action = "submitTrialcreate.action";
                    document.forms[0].submit();
                    showPopWin('${submitProtocol}', 600, 200, '', 'Submit Register Trial');
                }
            }
            
            function editTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action = "amendTrialedit.action";
                    document.forms[0].submit();
                } else {
                    document.forms[0].action = "submitTrialedit.action";
                    document.forms[0].submit();
                }
            }
            
            function printProtocol () {
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption += "scrollbars=yes,width=850,height=600,left=100,top=25";
                var sWinHTML = document.getElementById('contentprint').innerHTML;
                var winprint = window.open("", "", sOption);
                    winprint.document.open();
                    winprint.document.write('<html><body><form class="form-horizontal" role="form">');
                    winprint.document.write(sWinHTML);
                    winprint.document.write('</form></body></html>');
                    winprint.document.close();
                    winprint.focus();
            }
        //]]>
        </script>
    </head>
    <body>
        <div id="contentwide">
            <h1 class="heading"><span><fmt:message key="review.trial.view.page.title" /></span></h1>
            <c:set var="topic" scope="request" value="edittrial"/>
            
                <s:form id="reviewTrialForm" cssClass="form-horizontal" role="form">
                    <s:token/>
                    <s:actionerror/>
                    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                    <s:hidden name="pageFrom" id="pageFrom"/>
                    <c:if test="${requestScope.protocolId != null && requestScope.partialSubmission != null && requestScope.partialSubmission == 'submit'}">
                        <div class="confirm_msg">
                            <strong>The trial draft has been successfully saved and assigned the Identifier ${requestScope.protocolId}</strong>
                        </div>
                    </c:if>
                    <div id="contentprint">

                            <reg-web:titleRowDiv titleKey="view.trial.trialIDs"/>
                            <div class="row form-horizontal details">
                            <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
                                <reg-web:valueRowDiv labelKey="view.trial.nciAccessionNumber" strong="true" noLabelTag="true">
                                    <c:out value="${trialDTO.assignedIdentifier}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            <c:if test="${requestScope.protocolId != null}" >
                                <reg-web:valueRowDiv labelKey="view.trial.assignedIdentifier" strong="true" noLabelTag="true">
                                    <c:out value="${requestScope.protocolId}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                            </reg-web:valueRowDiv>
                            <c:if test="${trialDTO.nctIdentifier != null}">
                                <reg-web:valueRowDiv labelKey="view.trial.nctNumber" noLabelTag="true">
                                    <c:out value="${trialDTO.nctIdentifier}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
                                <c:if test="${trialDTO.ctepIdentifier != null}">
                                    <reg-web:valueRowDiv labelKey="view.trial.ctepIdentifier" noLabelTag="true">
                                        <c:out value="${trialDTO.ctepIdentifier}"/>
                                    </reg-web:valueRowDiv>
                                </c:if>
                                <c:if test="${trialDTO.dcpIdentifier != null}">
                                    <reg-web:valueRowDiv labelKey="view.trial.dcpIdentifier" noLabelTag="true">
                                        <c:out value="${trialDTO.dcpIdentifier}"/>
                                    </reg-web:valueRowDiv>
                                </c:if>
                                <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIds.jsp" %>
                                <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
                                    <reg-web:titleRowDiv titleKey="trial.amendDetails"/>
                                    <reg-web:valueRowDiv labelKey="view.trial.amendmentNumber" noLabelTag="true">
                                        <c:out value="${trialDTO.localAmendmentNumber}"/>
                                    </reg-web:valueRowDiv>
                                    <reg-web:valueRowDiv labelKey="view.trial.amendmentDate" noLabelTag="true">
                                        <c:out value="${trialDTO.amendmentDate}"/>
                                    </reg-web:valueRowDiv>
                                </c:if>
                            </c:if>
                            </div>
                            <reg-web:titleRowDiv titleKey="view.trial.trialDetails"/>
                            <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="view.trial.title" noLabelTag="true">
                                <c:out value="${trialDTO.officialTitle}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.phase" noLabelTag="true">
                                <c:out value="${trialDTO.phaseCode}"/>
                            </reg-web:valueRowDiv>
                            <c:if test="${trialDTO.phaseAdditionalQualifier!= '' && trialDTO.phaseCode=='NA'}">
                                <reg-web:valueRowDiv labelKey="view.trial.otherPhaseText" noLabelTag="true">
                                    <c:out value="${trialDTO.phaseAdditionalQualifier=='Pilot'?'Yes':'No'}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            <reg-web:valueRowDiv labelKey="view.trial.type" noLabelTag="true">
                                <c:out value="${trialDTO.trialType}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.primaryPurpose" noLabelTag="true">
                                <c:out value="${trialDTO.primaryPurposeCode}"/>
                            </reg-web:valueRowDiv>
                            <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                                <reg-web:valueRowDiv labelKey="view.trial.otherPurposeText" noLabelTag="true">
                                    <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            <c:if test="${trialDTO.trialType != 'NonInterventional'}">
	                            <reg-web:valueRowDiv labelKey="view.trial.secondaryPurpose" noLabelTag="true">
	                                <c:out value="${trialDTO.secondaryPurposeAsReadableString}"/>
	                            </reg-web:valueRowDiv>   
	                            <c:if test="${trialDTO.secondaryPurposeAsReadableString == 'Other'}">
	                                <reg-web:valueRowDiv labelKey="view.trial.secOtherPurposeText" noLabelTag="true">
	                                    <c:out value="${trialDTO.secondaryPurposeOtherText}"/>
	                                </reg-web:valueRowDiv>
                                </c:if> 
                            </c:if>
                            <reg-web:valueRowDiv labelKey="view.trial.accrual.disease.term" noLabelTag="true">
                                <c:out value="${trialDTO.accrualDiseaseCodeSystem}"/>
                            </reg-web:valueRowDiv>
                            <c:if test="${trialDTO.trialType == 'NonInterventional'}">
                                <reg-web:valueRowDiv labelKey="submit.trial.studySubtypeCode" noLabelTag="true">
                                    <c:out value="${trialDTO.studySubtypeCode}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="submit.trial.studyModelCode" noLabelTag="true">
                                    <c:out value="${trialDTO.studyModelCode}"/>
                                </reg-web:valueRowDiv>
                                <c:if test="${trialDTO.studyModelCode == 'Other'}">
                                    <reg-web:valueRowDiv labelKey="submit.trial.studyModelOtherText" noLabelTag="true">
                                        <c:out value="${trialDTO.studyModelOtherText}"/>
                                    </reg-web:valueRowDiv>                                
                                </c:if>                                
                                <reg-web:valueRowDiv labelKey="submit.trial.timePerspectiveCode" noLabelTag="true">
                                    <c:out value="${trialDTO.timePerspectiveCode}"/>
                                </reg-web:valueRowDiv>             
                                <c:if test="${trialDTO.timePerspectiveCode == 'Other'}">
                                    <reg-web:valueRowDiv labelKey="submit.trial.timePerspectiveOtherText" noLabelTag="true">
                                        <c:out value="${trialDTO.timePerspectiveOtherText}"/>
                                    </reg-web:valueRowDiv>                                
                                </c:if>
                            </c:if>                                                                                
                            
                            </div>
                            
                            <reg-web:titleRowDiv titleKey="view.trial.leadOrgInvestigator"/>
                            <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrganization" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrganizationName}"/>
                            </reg-web:valueRowDiv>
                            <c:if test="${trialDTO.propritaryTrialIndicator == 'Yes'}">
                                <reg-web:valueRowDiv labelKey="submit.proprietary.trial.siteOrganization" noLabelTag="true">
                                    <c:out value="${trialDTO.siteOrganizationName}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="submit.proprietary.trial.siteidentifier" noLabelTag="true">
                                    <c:out value="${trialDTO.localSiteIdentifier}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                    <c:out value="${trialDTO.sitePiName}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            </div>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
                                <reg-web:valueRowDiv labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                    <c:out value="${trialDTO.piName}"/>
                                </reg-web:valueRowDiv>            
                                
                                <c:if test="${trialDTO.sponsorName != null && trialDTO.xmlRequired == true}">
                                    <reg-web:titleRowDiv titleKey="view.trial.sponsorResParty"/>
                                    <div class="row form-horizontal details">
                                    <reg-web:valueRowDiv labelKey="view.trial.sponsor" noLabelTag="true">
                                        <c:out value="${trialDTO.sponsorName}"/>
                                    </reg-web:valueRowDiv>
                                    <reg-web:valueRowDiv labelKey="view.trial.respParty" noLabelTag="true">
                                        <c:out value="${func:capitalize(trialDTO.responsiblePartyTypeReadable)}"/>
                                    </reg-web:valueRowDiv>
                                    <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
                                        <reg-web:valueRowDiv labelKey="view.trial.respParty.investigator" noLabelTag="true">
                                            <c:out value="${trialDTO.responsiblePersonName}"/>
                                        </reg-web:valueRowDiv>
                                    </c:if>  
                                    <c:if test="${fn:trim(trialDTO.responsiblePersonTitle) != ''}">
                                        <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorTitle" noLabelTag="true">
                                            <c:out value="${trialDTO.responsiblePersonTitle}"/>
                                        </reg-web:valueRowDiv>
                                    </c:if>
                                    <c:if test="${fn:trim(trialDTO.responsiblePersonAffiliationOrgName) != ''}">
                                        <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorAff" noLabelTag="true">
                                            <c:out value="${trialDTO.responsiblePersonAffiliationOrgName}"/>
                                        </reg-web:valueRowDiv>
                                    </c:if>               
                                    </div>
                                </c:if>
                            </c:if>
                            
                            <c:if test="${fn:length(trialDTO.summaryFourOrgIdentifiers) > 0}">
                                <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
								<div class="row form-horizontal details">
                                <reg-web:valueRowDiv labelKey="view.trial.FundingCategory" noLabelTag="true">
                                    <c:out value="${trialDTO.summaryFourFundingCategoryCode}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="view.trial.FundingSponsor" noLabelTag="true">
                                    <c:forEach items="${trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                        <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                                    </c:forEach>
                                </reg-web:valueRowDiv>
                                <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'Yes'}">
	                                <reg-web:valueRowDiv labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
	                                    <c:out value="${empty trialDTO.consortiaTrialCategoryCode?'Yes':'No - '}"/>
	                                    <c:out value="${trialDTO.consortiaTrialCategoryCode}"/>
	                                </reg-web:valueRowDiv>
                                </c:if>
                                </div>
                            </c:if>
                            
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
                             
                                    <c:if test="${empty trialDTO.summaryFourOrgIdentifiers}">
                                    <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                                    </c:if>
                                    <div class="row form-horizontal details">
                                    <reg-web:valueRowDiv labelKey="studyProtocol.summaryFourPrgCode" noLabelTag="true">
                                    <table>
                                    <c:if test="${not empty trialDTO.programCodesList}">
                                        <c:forEach items="${trialDTO.programCodesList}" var="element"> 
                                        <tr>
                                         <td>${element}</td>
      								     </tr>
										</c:forEach>
									</c:if>	
								   </table>		
                                    </reg-web:valueRowDiv>
                                    
                                    </div>
                               
                                <%@ include file="/WEB-INF/jsp/nodecorate/viewStatusSection.jsp" %>
                            </c:if>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'Yes'}">
                                <c:if test="${fn:trim(trialDTO.siteProgramCodeText) != ''}">
                                    <c:if test="${empty trialDTO.summaryFourOrgIdentifiers}">
                                        <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                                    </c:if>
                                    <div class="row form-horizontal details">
                                    <reg-web:valueRowDiv labelKey="studyProtocol.summaryFourPrgCode" noLabelTag="true">
                                        <c:out value="${trialDTO.siteProgramCodeText}"/>
                                    </reg-web:valueRowDiv>
                                    
                                    </div>
                                </c:if>
                                <reg-web:titleRowDiv titleKey="view.trial.statusDates"/>
                                <div class="row form-horizontal details">
                                <reg-web:valueRowDiv labelKey="view.trial.siteRecruitmentStatus" noLabelTag="true">
                                    <c:out value="${trialDTO.siteStatusCode}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="view.trial.siteRecruitmentStatusDate" noLabelTag="true">
                                    <c:out value="${trialDTO.siteStatusDate}"/>
                                </reg-web:valueRowDiv>
                                <c:if test="${fn:trim(trialDTO.dateOpenedforAccrual) != ''}">
                                    <reg-web:valueRowDiv labelKey="submit.proprietary.trial.dateOpenedforAccrual" noLabelTag="true">
                                        <c:out value="${trialDTO.dateOpenedforAccrual}"/>
                                    </reg-web:valueRowDiv>
                                </c:if>
                                <c:if test="${fn:trim(trialDTO.dateClosedforAccrual) != ''}">
                                    <reg-web:valueRowDiv labelKey="submit.proprietary.trial.dateClosedforAccrual" noLabelTag="true">
                                        <c:out value="${trialDTO.dateClosedforAccrual}"/>
                                    </reg-web:valueRowDiv>
                                </c:if>
                                </div>
                            </c:if>

                        <c:if test="${fn:length(trialDTO.indIdeDtos) > 0}">
                            <div class="box" id="indideDiv">
                                <h3 class="heading mt20"><span>FDA IND/IDE Information for applicable trials</span></h3>
                                <%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
                            </div>
                        </c:if>
                        <c:if test="${trialDTO.fundingDtos != null}">
                            <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                        </c:if>
                        <c:if test="${fn:trim(trialDTO.propritaryTrialIndicator) == 'No' && trialDTO.xmlRequired == true}">
                            
                                <reg-web:titleRowDiv titleKey="regulatory.title"/>
                                
                                <div class="row form-horizontal details">
                                <!--  Trial Oversight Authority Country -->
                                <reg-web:valueRowDiv labelKey="regulatory.oversight.country.name" noLabelTag="true">
                                    <c:out value="${trialDTO.trialOversgtAuthCountryName }"/>
                                </reg-web:valueRowDiv>
                                <!--  Trial Oversight Authority Organization Name -->
                                <reg-web:valueRowDiv labelKey="regulatory.oversight.auth.name" noLabelTag="true">
                                    <c:out value="${trialDTO.trialOversgtAuthOrgName }"/>
                                </reg-web:valueRowDiv>
                                <!--   FDA Regulated Intervention Indicator-->
                                <reg-web:valueRowDiv labelKey="regulatory.FDA.regulated.interv.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.fdaRegulatoryInformationIndicator}" /> 
                                </reg-web:valueRowDiv>
                                <!--   Section 801 Indicator-->
                                <reg-web:valueRowDiv id="sec801row" labelKey="regulatory.section801.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.section801Indicator}" />
                                </reg-web:valueRowDiv>
                                <!--   Delayed Posting Indicator-->
                                <reg-web:valueRowDiv id="delpostindrow" labelKey="regulatory.delayed.posting.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.delayedPostingIndicator}" />
                                </reg-web:valueRowDiv>
                                <!--   Data Monitoring Committee Appointed Indicator -->
                                <reg-web:valueRowDiv id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.dataMonitoringCommitteeAppointedIndicator}" />
                                </reg-web:valueRowDiv>
                                </div>
                        </c:if>
                        <c:if test="${fn:length(trialDTO.docDtos) >0}">
                            <div class="box">
                                <h3 class="heading mt20"><span>Trial Related Documents</span></h3>
                                <display:table class="data table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                                                name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">
                                    <display:column titleKey="search.trial.view.documentTypeCode" property="typeCode"   sortable="true" headerClass="sortable"/>
                                    <display:column titleKey="search.trial.view.documentFileName" property="fileName"   sortable="true" headerClass="sortable"/>"
                                </display:table>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${requestScope.partialSubmission == null}">
                                <p align="center" class="info">
                                   Please verify ALL the trial information you provided on this screen before clicking the <b>Submit</b> button below.
                                   <br>Once you submit the trial you will not be able to modify the information.
                                </p>
                                <div class="bottom">
    								<button type="button" class="btn btn-icon btn-primary" onclick="editTrial()"> <i class="fa-edit"></i>Edit </button>
    								<button type="button" class="btn btn-icon btn-primary" onclick="submitTrial();"><i class="fa-save"></i>Submit</button>
    								<button type="button" class="btn btn-icon btn-primary" onclick="printProtocol();"><i class="fa-print"></i>Print</button>
  								</div>
                            </del>
                        </div>
                    </c:if>
                </s:form>
        </div>
    </body>
</html>
