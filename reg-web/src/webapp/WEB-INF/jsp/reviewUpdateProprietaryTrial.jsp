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
                    document.forms[0].action="updateProprietaryTrialupdate.action";
                    document.forms[0].submit();
                    showPopWin('${amendProtocol}', 600, 200, '', 'Update Trial');
                }
            }
            function editTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action="updateProprietaryTrialedit.action";
                    document.forms[0].submit();
                }
            }
            
            function printProtocol (){
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption += "scrollbars=yes,width=750,height=600,left=100,top=25";
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
            <h1 class="heading"><span><fmt:message key="review.trial.view.page.title" /></span></h1>
            <c:set var="topic" scope="request" value="edittrial"/>
                <s:form cssClass="form-horizontal" role="form">
                    <s:token/> 
                    <s:actionerror/>
                    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                    <s:hidden name="pageFrom" id="pageFrom"/>
                    <div id="contentprint">
                        <table class="form">
                            <reg-web:titleRowDiv titleKey="submit.proprietary.trial.trialIdentification"/>
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrganization" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrganizationName}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                            </reg-web:valueRowDiv>
                            <c:if test="${fn:trim(trialDTO.nctIdentifier) != ''}">
                                <reg-web:valueRowDiv labelKey="view.trial.nctNumber" noLabelTag="true">
                                    <c:out value="${trialDTO.nctIdentifier}"/>
                                </reg-web:valueRowDiv>
                            </c:if>
                            <reg-web:titleRowDiv titleKey="view.trial.trialDetails"/>
                            <reg-web:valueRowDiv labelKey="view.trial.title" noLabelTag="true">
                                <c:out value="${trialDTO.officialTitle}"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.phase" noLabelTag="true">
                                <c:out value="${trialDTO.phaseCode}"/>
                            </reg-web:valueRowDiv>
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
                            <c:if test="${fn:length(trialDTO.summaryFourOrgIdentifiers) > 0}">
                                <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
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
                            </c:if>
                        </table>
                        <c:if test="${trialDTO.participatingSitesList != null && fn:length(trialDTO.participatingSitesList) > 0}">
                                <h3 class="heading mt20">Participating sites</h3>
                                <display:table class="table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.participatingSitesList}" >
                                    <display:column title="Organization Name" property="name"  headerClass="sortable"/>
                                    <display:column title="Site Principal Investigator" property="investigator"  headerClass="sortable"/>
                                    <display:column title="Local Trial<br/> Identifier" property="siteLocalTrialIdentifier"  headerClass="sortable"/>
                                    <display:column title="Current Site<br/> Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
                                    <display:column title="Current Site<br/> Recruitment Status Date" property="recruitmentStatusDate"  headerClass="sortable"/>
                                    <display:column title="Date Opened <br/>for Accrual" property="dateOpenedforAccrual"  headerClass="sortable"/>
                                    <display:column title="Date Closed <br/>for Accrual" property="dateClosedforAccrual"  headerClass="sortable"/>
                                </display:table>
                        </c:if>
                        <c:if test="${fn:length(trialDTO.docDtos) >0}">
                            <div class="box">
                                <display:table class="table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                                               name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">
                                    <display:column titleKey="search.trial.view.documentTypeCode" property="proprietaryTypeCode"   sortable="true" headerClass="sortable"/>
                                    <display:column titleKey="search.trial.view.documentFileName" property="fileName"   sortable="true" headerClass="sortable"/>"
                                </display:table>
                            </div>
                        </c:if>
                    </div>
                     <div class="bottom">
						<button type="button" class="btn btn-icon btn-primary" onclick="editTrial()"> <i class="fa-edit"></i>Edit </button>
						<button type="button" class="btn btn-icon btn-primary" onclick="submitTrial();"><i class="fa-save"></i>Submit</button>
						<button type="button" class="btn btn-icon btn-primary" onclick="printProtocol();"><i class="fa-print"></i>Print</button>
					</div>
                </s:form>
            </div>
        </div>
    </body>
</html>
