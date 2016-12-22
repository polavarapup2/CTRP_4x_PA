<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Update <fmt:message key="submit.proprietary.trial.page.title"/></title>
        <s:head/>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <script type="text/javascript">
            var bla = <s:property value="trialDTO.participatingSitesList.size"/>;
            var orgid;
            var chosenname;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;

            function setorgid(orgIdentifier, oname, p30grant) {
                orgid = orgIdentifier;
                chosenname = oname.replace(/&apos;/g,"'");
            }
            
            function setpersid(persIdentifier, sname, email, phone) {
                persid = persIdentifier;
                chosenname = sname.replace(/&apos;/g,"'");
            }
            
            function lookup4loadleadorg() {
                showPopup('${lookupOrgUrl}', loadLeadOrgDiv, 'Select Lead Organization');
            }
            

            function loadLeadOrgDiv() {
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationName').value = chosenname;
            }

            function reviewProtocol () {
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
                submitFirstForm("review", "updateProprietaryTrialreview.action");
            }
            
            function cancelProtocol() {
                submitFirstForm("cancle", "updateProprietaryTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function toggledisplay (it, box) {
              var vis = (box.checked) ? "block" : "none";
              $(it).style.display = vis;
            }
            
            function toggledisplay2 (it) {
              var vis = $(it).style.display;
              $(it).style.display = (vis == "block") ? "none" : "block";
            }
            
            function addNCTIdentifier() {
                var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddNCTIdentifier.action';
                var params = {
                        nctIdentifier: trim($("nctId").value)
                };
                var div = $('nctIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
        </script>
        
<style type="text/css">
/*Wrap error message text*/
ul.errorMessage > li > span {
    white-space:pre-wrap;
}
</style>
    </head>
    <body>
    <!-- main content begins-->
       <h1 class="heading"><span><fmt:message key="update.trial.page.header"/></span></h1>
        <c:set var="topic" scope="request" value="updatetrial"/>
            <reg-web:failureMessage/>
            <s:form name="updateProprietaryTrial" method="POST" enctype="multipart/form-data" cssClass="form-horizontal" role="form">
                <s:token/>
                <s:if test="hasActionErrors()">
                    <div class="alert alert-danger">
                        <s:actionerror/>
                    </div>
                </s:if>
                <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
                <s:hidden name="trialDTO.sitePiIdentifier" id="trialDTO.sitePiIdentifier"/>
                <s:hidden name="trialDTO.siteOrganizationIdentifier" id="trialDTO.siteOrganizationIdentifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                <s:hidden name="trialDTO.consortiaTrialCategoryCode" id="trialDTO.consortiaTrialCategoryCode"/>
                <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                    <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                </c:if>
                <s:hidden name="page" />
                <div class="accordion-group">
                <div class="accordion">
				<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section1"><fmt:message key="submit.proprietary.trial.trialIdentification"/><span class="required">*</span></a></div>
				<div id="section1" class="accordion-body in">
				<div class="container">
                    <reg-web:valueRowDiv labelKey="view.trial.identifier" noLabelTag="true">
                        <s:property value="trialDTO.assignedIdentifier"/>
                    </reg-web:valueRowDiv>
                    <reg-web:valueRowDiv labelFor="trialDTO.leadOrganizationName" labelKey="view.trial.leadOrganization">
                       <s:property value="trialDTO.leadOrganizationName" />
                    </reg-web:valueRowDiv>
                    <reg-web:valueRowDiv labelFor="trialDTO.leadOrgTrialIdentifier" labelKey="submit.trial.leadOrgidentifier">
                        <s:property value="trialDTO.leadOrgTrialIdentifier" />                        
                    </reg-web:valueRowDiv>
                    <div class="form-group m0">
                      <label class="col-xs-2 control-label"><fmt:message key="submit.trial.nctNumber"/></label>
                      <div class="col-xs-10">
                      	<%@ include file="/WEB-INF/jsp/nodecorate/addNctIdentifier.jsp" %>
                      </div>                    
  					</div>
                 </div>
                 </div>
                 </div>
                 <div class="accordion">
				 <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section2"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a></div>
			     <div id="section2" class="accordion-body in">
			     <div class="container">
                  <reg-web:valueRowDiv labelFor="trialDTO.officialTitle" labelKey="submit.trial.title">
                        <s:property value="trialDTO.officialTitle"/> 
                            <c:if test="${not empty trialDTO.studyAlternateTitles}">
                                <a href="javascript:void(0)" onclick="displayStudyAlternateTitles('${trialDTO.identifier}')">(*)</a>                                                   
                           </c:if>
                    </reg-web:valueRowDiv>
                    <reg-web:valueRowDiv labelFor="trialType" labelKey="submit.trial.type">
                        <c:out value="${trialDTO.trialType!='NonInterventional'?'Interventional':'Non-interventional'}"></c:out>
                    </reg-web:valueRowDiv>
                    
                   
                    
                    <c:if test="${trialDTO.trialType=='NonInterventional'}">
						<reg-web:valueRowDiv labelFor="trialDTO.studySubtypeCode" labelKey="submit.trial.studySubtypeCode">
						     <s:property value="trialDTO.studySubtypeCode"/>						      
						</reg-web:valueRowDiv>                   
                    </c:if>
        
                    <reg-web:valueRowDiv labelFor="trialDTO.primaryPurposeCode" labelKey="submit.trial.purpose">
                        <s:property value="trialDTO.primaryPurposeCode" />
                    </reg-web:valueRowDiv>
          
		            <c:if test="${trialDTO.primaryPurposeOtherText=='Other'}">	       
		                <reg-web:valueRowDiv labelFor="submit.trial.otherPurposeText" labelKey="submit.trial.otherPurposeText">
		                    <s:property value="trialDTO.primaryPurposeOtherText"/>
		                </reg-web:valueRowDiv>
		            </c:if>
          
		          <c:if test="${trialDTO.trialType!='NonInterventional'}">	    
		                <reg-web:valueRowDiv labelFor="submit.trial.secondaryPurpose" labelKey="submit.trial.secondaryPurpose">
		                    <s:property value="trialDTO.secondaryPurposesAsString" />
		                </reg-web:valueRowDiv>   
		                <c:if test="${trialDTO.secondaryPurposeAsReadableString == 'Other'}">
                                <reg-web:valueRowDiv labelKey="view.trial.secOtherPurposeText" noLabelTag="true">
                                    <c:out value="${trialDTO.secondaryPurposeOtherText}"/>
                                </reg-web:valueRowDiv>
                        </c:if>                    
		          </c:if>
          
                 <c:if test="${trialDTO.trialType=='NonInterventional'}">
		             <reg-web:valueRowDiv labelFor="submit.trial.studyModelCode" labelKey="submit.trial.studyModelCode">
		                 <s:property value="trialDTO.studyModelCode"/>
		             </reg-web:valueRowDiv>
	    
		             <c:if test="${trialDTO.studyModelCode=='Other'}">  
			             <reg-web:valueRowDiv labelFor="submit.trial.studyModelOtherText" labelKey="submit.trial.studyModelOtherText">
			                <s:property value="trialDTO.studyModelOtherText"/>
			             </reg-web:valueRowDiv>
			         </c:if>

		             <reg-web:valueRowDiv labelFor="submit.trial.timePerspectiveCode" labelKey="submit.trial.timePerspectiveCode">
		                    <s:property value="trialDTO.timePerspectiveCode"/>
		             </reg-web:valueRowDiv>
      
			        <c:if test="${trialDTO.timePerspectiveCode=='Other'}">
			              <reg-web:valueRowDiv labelFor="submit.trial.timePerspectiveOtherText" labelKey="submit.trial.timePerspectiveOtherText">
			                <s:property value="trialDTO.timePerspectiveOtherText"/>
			              </reg-web:valueRowDiv>
			        </c:if>        
                </c:if>
                    
                <reg-web:valueRowDiv labelFor="submit.trial.phase" labelKey="submit.trial.phase">
                    <s:property value="trialDTO.phaseCode" />
                </reg-web:valueRowDiv>
                    
	            <c:if test="${trialDTO.phaseCode=='NA'}">                    
	               <reg-web:valueRowDiv labelFor="submit.trial.otherPhaseText" labelKey="submit.trial.otherPhaseText">	                  
	                  <c:out value="${trialDTO.phaseAdditionalQualifier=='Pilot'?'Yes':'No'}"/>
	               </reg-web:valueRowDiv>
	            </c:if>

				</div>
				</div>
				</div>
                <!--  summary4 information -->
                <div class="accordion">
				<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a></div>
				<div id="section3" class="accordion-body in">
				<div class="container">
                <reg-web:valueRowDiv labelFor="trialDTO.summaryFourFundingCategoryCode" labelKey="update.proprietary.trial.summary4FundingCategory">
                           <s:property value="trialDTO.summaryFourFundingCategoryCode" />                    
                </reg-web:valueRowDiv>
                <reg-web:valueRowDiv id="trialDTO.summaryFourOrgNameTR" labelFor="trialDTO.summaryFourOrgName" labelKey="update.proprietary.trial.summary4Sponsor">
                    <s:iterator value="trialDTO.summaryFourOrgIdentifiers" id="trialDTO.summaryFourOrgIdentifiers" status="stat">
                        <s:property value="%{orgName}"/><br/>
                        <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="<c:out value="${orgId}" />"/> 
                        <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="<c:out value="${orgName}" />"/>
                    </s:iterator>
                </reg-web:valueRowDiv>
                <reg-web:valueRowDiv labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
                                <c:out value="${empty trialDTO.consortiaTrialCategoryCode?'Yes':'No - '}"/>
                                <c:out value="${trialDTO.consortiaTrialCategoryCode}"/>
                </reg-web:valueRowDiv>
                </div>
                </div>
                </div>
                <div class="accordion">
				<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4">Participating Sites<span class="required">*</span></a></div>
				<div id="section4" class="accordion-body in">
				<div class="container">
					<div class="table-header-wrap">
                  <table class="table table-striped table-bordered sortable">
                        <thead>
                            <tr>
                                <th>Organization/Investigator</th>
                                <th>Local Trial<br/> Identifier<span class="required">*</span></th>
                                <th>Current Site<br/> Recruitment Status<span class="required">*</span></th>
                                <th>Current Site<br/> Recruitment <br/>Status Date<span class="required">*</span><br/>(mm/dd/yyyy) </th>
                                <th>Date Opened <br/>for Accrual <br/>(mm/dd/yyyy) </th>
                                <th>Date Closed <br/>for Accrual <br/>(mm/dd/yyyy) </th>
                            </tr>
                         </thead>
                         <tbody>
                            <s:iterator id="trialDTO.participatingSitesList" value="trialDTO.participatingSitesList" status="psstats">
                            <tr>
                                <td>
                                    <label><s:textarea  name="trialDTO.participatingSitesList[%{#psstats.index}].nameInvestigator" value="%{nameInvestigator}" readonly="true" cssClass="form-control" rows="2"/></label>
                                    <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].name" value="%{name}"/>
                                    <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].investigator" value="%{investigator}"/>
                                </td>
                                <td>
                                    <label><s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].siteLocalTrialIdentifier" value="%{siteLocalTrialIdentifier}" cssClass="form-control" /></label>
                                    <span class="alert-danger">
                                        <s:fielderror escape="false">
                                            <s:param>participatingsite.localTrialId<s:property value="%{#psstats.index}"/></s:param>
                                        </s:fielderror>
                                    </span>
                                    <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].id" value="%{id}"/>
                                </td>
                                <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                                <td class="value">
                                    <label><s:select headerKey="" headerValue="--Select--"
                                              name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                                              list="#recruitmentStatusValues" cssClass="form-control" /></label>
                                    <span class="alert-danger">
                                        <s:fielderror escape="false">
                                            <s:param>participatingsite.recStatus<s:property value="%{#psstats.index}"/></s:param>
                                        </s:fielderror>
                                    </span>
                                </td>
                                <td nowrap="nowrap">
                                    <div id="datetimepicker" class="datetimepicker input-append">
                                       <s:textfield id="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatusDate" name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />
                                       <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                                       <span class="alert-danger">
                                          <s:fielderror>
                                             <s:param>participatingsite.recStatusDate<s:property value="%{#psstats.index}"/></s:param>
                                         </s:fielderror>
                                      </span>
                                   </div>      
                                </td>
                                <td nowrap="nowrap">
                                    <div id="datetimepicker" class="datetimepicker input-append">
                                       <s:textfield id="trialDTO.participatingSitesList[%{#psstats.index}].dateOpenedforAccrual" name="trialDTO.participatingSitesList[%{#psstats.index}].dateOpenedforAccrual" value="%{dateOpenedforAccrual}" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />
                                       <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                                    </div>
                                </td>
                                <td nowrap="nowrap">
                                    <div id="datetimepicker" class="datetimepicker input-append">
                                       <s:textfield id="trialDTO.participatingSitesList[%{#psstats.index}].dateClosedforAccrual" name="trialDTO.participatingSitesList[%{#psstats.index}].dateClosedforAccrual" value="%{dateClosedforAccrual}" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />
                                       <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                                    </div>
                                </td>
                            </tr>
                            </s:iterator >
                        </tbody>
                    </table>
                    </div>
                </div>
                </div>
                </div>
                
                <div id="existingDocsDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateProprietaryTrialDocumentsSection.jsp" %>
                </div>
                
                <div id="uploadDocDiv">
                    <c:set var="disableDocumentDeletion" value="${true}" scope="request"/>
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                <p align="center" class="info">
                   Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.
                   <br>Once you submit the trial you will not be able to modify the information.
                </p>
                <div class="align-center button-row">
			      <button type="button" class="btn btn-icon btn-primary" onclick="reviewProtocol()"><i class="fa-file-text-o"></i>Review Trial</button>
			      <button type="button" class="btn btn-icon btn-default" onclick="cancelProtocol()"><i class="fa-times-circle"></i>Cancel</button>
		    	</div>
		    	<br/>
                <s:hidden name="uuidhidden"/>
                </div>
            </s:form>
    </body>
</html>
