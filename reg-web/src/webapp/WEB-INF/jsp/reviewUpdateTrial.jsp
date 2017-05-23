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
                    document.forms[0].action="updateTrialupdate.action";
                    document.forms[0].submit();
                    showPopWin('${amendProtocol}', 600, 200, '', 'Update Trial');
                }
            }
            
            function editTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action="updateTrialedit.action";
                    document.forms[0].submit();
                }
            }
            
            function printProtocol () {
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption += "scrollbars=yes,width=750,height=600,left=100,top=25";
                var sWinHTML = document.getElementById('contentprint').innerHTML;
                var winprint = window.open("", "", sOption);
                winprint.document.open();
                winprint.document.write("<html><body>");
                winprint.document.write(sWinHTML);
                winprint.document.write("</body></html>");
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
                <s:form  cssClass="form-horizontal" role="form">
                <s:token/> 
                <s:actionerror/>
                <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="pageFrom" id="pageFrom"/>
                <div id="contentprint">
                        <reg-web:titleRowDiv titleKey="view.trial.trialIDs"/>
                        <div class="row form-horizontal details">
                        <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
                            <reg-web:valueRowDiv labelKey="view.trial.nciAccessionNumber" strong="true" noLabelTag="true">
                                <c:out value="${trialDTO.assignedIdentifier}"/>
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
                        </div>
                        <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIds.jsp" %>
                        <reg-web:titleRowDiv titleKey="view.trial.trialDetails"/>
                        <div class="row form-horizontal details">
                            
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
                        <c:if test="${trialDTO.trialType != 'NonInterventionalStudyProtocol' && trialDTO.trialType != 'NonInterventional'}">
	                        <reg-web:valueRowDiv labelKey="view.trial.secondaryPurpose">
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
                        <c:if test="${trialDTO.trialType == 'NonInterventionalStudyProtocol' || trialDTO.trialType == 'NonInterventional'}">
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
                        <c:if test="${trialDTO.xmlRequired == true}">
						      <reg-web:titleRowDiv titleKey="view.trial.sponsorResParty"/>
						      <div class="row form-horizontal details">
						      <reg-web:valueRowDiv labelKey="view.trial.sponsor" noLabelTag="true" required="true">
						          <c:out value="${trialDTO.sponsorName}"/>
						      </reg-web:valueRowDiv>
						      <reg-web:valueRowDiv labelKey="view.trial.respParty" noLabelTag="true" required="true">
						          <c:out value="${func:capitalize(trialDTO.responsiblePartyTypeReadable)}"/>
						      </reg-web:valueRowDiv>
						      <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
						          <reg-web:valueRowDiv labelKey="view.trial.respParty.investigator" noLabelTag="true" required="true">
						              <c:out value="${trialDTO.responsiblePersonName}"/>
						          </reg-web:valueRowDiv>
						      </c:if>  
						      <c:if test="${fn:trim(trialDTO.responsiblePersonTitle) != ''}">
						          <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorTitle" noLabelTag="true">
						              <c:out value="${trialDTO.responsiblePersonTitle}"/>
						          </reg-web:valueRowDiv>
						      </c:if>
						      <c:if test="${fn:trim(trialDTO.responsiblePersonAffiliationOrgName) != ''}">
						          <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorAff" noLabelTag="true" required="true">
						              <c:out value="${trialDTO.responsiblePersonAffiliationOrgName}"/>
						          </reg-web:valueRowDiv>
						      </c:if>                                  
						      
						      </div>
                        </c:if>
                        <c:if test="${fn:length(trialDTO.summaryFourOrgIdentifiers) > 0}">
                            <reg-web:titleRowDiv titleKey="view.trial.Summary4Information"/>
                            <div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="view.trial.FundingCategory" noLabelTag="true">
                                <c:out value="${trialDTO.summaryFourFundingCategoryCode }"/>
                            </reg-web:valueRowDiv>
                            <reg-web:valueRowDiv labelKey="view.trial.FundingSponsor" noLabelTag="true">
                                    <c:forEach items="${trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                        <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                                    </c:forEach>
                            </reg-web:valueRowDiv>
                            </div>
                        </c:if>
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
                    
                    <c:if test="${trialDTO.xmlRequired == true}">
                        <reg-web:titleRowDiv titleKey="regulatory.title"/>
                        	<div class="row form-horizontal details">
                            <reg-web:valueRowDiv labelKey="regulatory.oversight.country.name" noLabelTag="true">
                                <s:property value="trialDTO.trialOversgtAuthCountryName"/>
                            </reg-web:valueRowDiv>
                            <!--  Trial Oversight Authority Organization Name -->
                            <reg-web:valueRowDiv labelKey="regulatory.oversight.auth.name" noLabelTag="true">
                                <s:property value="trialDTO.trialOversgtAuthOrgName"/>
                            </reg-web:valueRowDiv>
                            <!--   FDA Regulated Intervention Indicator-->
                            <reg-web:valueRowDiv labelKey="regulatory.FDA.regulated.interv.ind" noLabelTag="true">
                                <s:property value="trialDTO.fdaRegulatoryInformationIndicator"/>
                            </reg-web:valueRowDiv>
                            <!--   Section 801 Indicator-->
                            <reg-web:valueRowDiv id="sec801row" labelKey="regulatory.section801.ind" noLabelTag="true">
                                <s:property value="trialDTO.section801Indicator"/>
                            </reg-web:valueRowDiv>
                            <!--   Delayed Posting Indicator-->
                            <reg-web:valueRowDiv id="delpostindrow" labelKey="regulatory.delayed.posting.ind" noLabelTag="true">
                                <s:property value="trialDTO.delayedPostingIndicator"/>
                            </reg-web:valueRowDiv>
                            <!--   Data Monitoring Committee Appointed Indicator -->
                            <reg-web:valueRowDiv id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind" noLabelTag="true">
                                <s:property value="trialDTO.dataMonitoringCommitteeAppointedIndicator"/>
                            </reg-web:valueRowDiv>
                            </div>
                    </c:if>
                    <c:if test="${trialDTO.indIdeUpdateDtos != null && fn:length(trialDTO.indIdeUpdateDtos) > 0}">
                        <div class="box">
                            <h3 class="heading mt20"><span>Updated FDA IND/IDE</span></h3>
                            <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.indIdeUpdateDtos}">
                                <display:column title="IND/IDE Type" property="indIde"  headerClass="sortable" style="width:75px" />
                                <display:column escapeXml="true" title="Number" property="number"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Holder" property="holderType"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="NIH Inst Holder Code" property="nihInstHolder"  headerClass="sortable"  style="width:75px"/>
                                <display:column escapeXml="true" title="NCI Div Prog Holder Code" property="nciDivProgHolder" headerClass="sortable"  style="width:75px"/>
                                <display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.indIdeAddDtos != null && fn:length(trialDTO.indIdeAddDtos) > 0}">
                        <div class="box">
                            <h3 class="heading mt20"><span>Added FDA IND/IDE</span> </h3>
                            <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.indIdeAddDtos}">
                                <display:column title="IND/IDE Type" property="indIde"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Number" property="number"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Holder" property="holderType"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable" style="width:75px" />
                                <display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.fundingDtos != null && fn:length(trialDTO.fundingDtos) > 0}">
                        <div class="box">
                            <h3 class="heading mt20"><span>Updated Grant Information</span> </h3>
                            <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.fundingDtos}" >
                                <display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable" style="width:75px"/>
                                <display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.fundingAddDtos != null && fn:length(trialDTO.fundingAddDtos) > 0}">
                        <div class="box">
                            <h3 class="heading mt20"><span>Added Grant Information </span></h3>
                            <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.fundingAddDtos}" >
                                <display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable" style="width:75px"/>
                                <display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.collaborators != null && fn:length(trialDTO.collaborators) > 0}">
                        <div class="box">
                            <h3 class="heading mt20"><span>Colaborators</span> </h3>
                            <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.collaborators}" >
                                <display:column escapeXml="true" title="Collaborator" property="name"  headerClass="sortable"/>
                                <display:column escapeXml="true" title="Functional Role" property="functionalRole"  headerClass="sortable"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.participatingSites != null && fn:length(trialDTO.participatingSites) > 0}">
                        <div class="box">
                            <h3 class="heading mt20"><span>Participating sites</span> </h3>
                            <display:table class="data table table-striped table-bordered sortable" sort="list"  uid="row"  name="${trialDTO.participatingSites}" >
                                <display:column escapeXml="true" title="Site" property="name"  headerClass="sortable"/>
                                <display:column title="Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
                                <display:column title="Date" property="recruitmentStatusDate"  headerClass="sortable"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(trialDTO.docDtos) >0}">
                        <div class="box">
                            <display:table class="data table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                                           name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">
                                <display:column titleKey="search.trial.view.documentTypeCode" property="typeCode"   sortable="true" headerClass="sortable"/>
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
