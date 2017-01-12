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
                document.forms[0].action="submitProprietaryTrialcreate.action";
                document.forms[0].submit();
                showPopWin('${amendProtocol}', 600, 200, '', 'Submit Register Trial');
            }
            
            function editTrial() {
                    document.forms[0].action = "submitProprietaryTrialedit.action";
                    document.forms[0].submit();
            }
            
            function printProtocol (){
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption+="scrollbars=yes,width=750,height=600,left=100,top=25";
                var sWinHTML = document.getElementById('contentprint').innerHTML;
                var winprint=window.open("","",sOption);
                winprint.document.open();
                winprint.document.write('<html><body>');
                winprint.document.write(sWinHTML);
                winprint.document.write('</body></html>');
                winprint.document.close();
                winprint.focus();
            }
        //]]>
        </script>
    </head>
    <body>
        <div id="contentwide">
            <h1><fmt:message key="review.trial.view.page.title" /></h1>
            <c:set var="topic" scope="request" value="edittrial"/>
                <s:form cssClass="form-horizontal" role="form">
                    <s:token/> 
                    <s:actionerror/>
                    <s:hidden name="trialDTO.accrualDiseaseCodeSystem" id="trialDTO.accrualDiseaseCodeSystem" />
                    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                    <s:hidden name="pageFrom" id="pageFrom"/>
                    <c:if test="${requestScope.protocolId != null}">
                        <c:choose>
                            <c:when test="${requestScope.partialSubmission == 'submit'}">
                                <div class="confirm_msg">
                                    <strong>The trial draft has been successfully saved and assigned the Identifier ${requestScope.protocolId}</strong>
                                </div>                            
                            </c:when>
                            <c:otherwise>
		                        <div class="confirm_msg">
		                            <strong>The trial has been successfully submitted and assigned the NCI Identifier ${requestScope.protocolId}</strong>
		                        </div>                            
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <div id="contentprint">
                            <reg-web:titleRowDiv titleKey="submit.proprietary.trial.trialIdentification"/>
                            <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrganization" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrganizationName}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="submit.proprietary.trial.siteOrganization" noLabelTag="true">
                                <c:out value="${trialDTO.siteOrganizationName}"/>
                            </reg-web:valueRowDiv>  
                            <reg-web:valueRowDiv labelKey="submit.proprietary.trial.siteidentifier" noLabelTag="true">
                                <c:out value="${trialDTO.localSiteIdentifier}"/>
                            </reg-web:valueRowDiv>  
                            <c:if test="${fn:trim(trialDTO.nctIdentifier) != ''}">
                                <reg-web:valueRowDiv labelKey="view.trial.nctNumber" noLabelTag="true">
                                    <c:out value="${trialDTO.nctIdentifier}"/>
                                </reg-web:valueRowDiv>  
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
	                            <reg-web:valueRowDiv labelKey="view.trial.secondaryPurpose">
	                                <c:out value="${trialDTO.secondaryPurposeAsReadableString}"/> 
	                            </reg-web:valueRowDiv>
	                            <c:if test="${trialDTO.secondaryPurposeAsReadableString == 'Other'}">
	                                <reg-web:valueRowDiv labelKey="view.trial.secOtherPurposeText" noLabelTag="true">
	                                    <c:out value="${trialDTO.secondaryPurposeOtherText}"/>
	                                </reg-web:valueRowDiv>
                                </c:if>                         
	                        </c:if>
                                                    
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
	                                                   
                            <reg-web:valueRowDiv labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                <c:out value="${trialDTO.sitePiName}"/>
                            </reg-web:valueRowDiv>
                            </div>                            
                            <c:if test="${fn:length(trialDTO.summaryFourOrgIdentifiers) > 0}">
                                <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                                <div class="row form-horizontal details">
                                <reg-web:valueRowDiv labelKey="view.trial.SubmissionCategory" noLabelTag="true">
                                    <c:out value="${trialDTO.summaryFourFundingCategoryCode}"/>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="view.trial.FundingSponsor" noLabelTag="true">
                                    <c:forEach items="${trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                        <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                                    </c:forEach>
                                </reg-web:valueRowDiv>
                                <reg-web:valueRowDiv labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
                                    <c:out value="${empty trialDTO.consortiaTrialCategoryCode?'Yes':'No - '}"/>
                                    <c:out value="${trialDTO.consortiaTrialCategoryCode}"/>
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
                        <c:if test="${fn:length(trialDTO.indIdeDtos) > 0}">
                                <h3 class="heading mt20"><span>>FDA IND/IDE Information for applicable trials</span></h3>
                                <div class="row form-horizontal details">
                                <%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${trialDTO.fundingDtos != null}">
                            <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                        </c:if>
                        <c:if test="${fn:length(trialDTO.docDtos) >0}">
                            <div class="box">
                                <display:table class=" table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                                               name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">
                                    <display:column titleKey="search.trial.view.documentTypeCode" property="proprietaryTypeCode"   sortable="true" headerClass="sortable"/>
                                    <display:column titleKey="search.trial.view.documentFileName" property="fileName"   sortable="true" headerClass="sortable"/>"
                                </display:table>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${requestScope.protocolId == null}">
	                    <div class="bottom">
	                        <button type="button" class="btn btn-icon btn-primary" onclick="editTrial()"> <i class="fa-edit"></i>Edit </button>
	                        <button id="submitTrialBtn" type="button" class="btn btn-icon btn-primary" onclick="submitTrial();"><i class="fa-save"></i>Submit</button>
	                        <button type="button" class="btn btn-icon btn-primary" onclick="printProtocol();"><i class="fa-print"></i>Print</button>
	                    </div>
                    </c:if>
                </s:form>
        </div>
    </body>
</html>
