<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.trial.page.title"/></title>
    
    <link href="<c:url value='/styles/jquery-datatables/css/jquery.dataTables.min.css'/>" rel="stylesheet" type="text/css" media="all" />    
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
  
<s:head/>
  
  <c:url value="/protected/submitTrial" var="backendUrlTemplate"/>  
  
  <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
  <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
  <c:url value="/protected/ajaxSubmitTrialActionshowWaitDialog.action" var="submitProtocol"/>
  <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
  <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
  
  
  
  <script type="text/javascript" src="${scriptPath}/js/submitTrial.js"></script>
  <script type="text/javascript">

  function lookup4loadleadpers() {
      showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
  }
  
  function lookup4loadleadorg(selection, name) {
	  if(selection === undefined || selection == "") {
            $('trialDTO.leadOrganizationNameField').innerHTML = 'Please Select Lead Organization';
            $("trialDTO.leadOrganizationIdentifier").value = '';
            $('trialDTO.leadOrganizationName').value = '';
        } else if(selection == -1) {
            showPopup("${lookupOrgUrl}",loadLeadOrgDiv, 'Select Lead Organization');
        } else if(selection > 0) {
        	loadProgramCodes(selection);
        	$('trialDTO.leadOrganizationNameField').innerHTML = name;
            $('trialDTO.leadOrganizationName').value = name;
            $("trialDTO.leadOrganizationIdentifier").value = selection;
        } else {
            //if there is no valid selection we undo the selection enteirly.
            $('trialDTO.leadOrganizationNameField').innerHTML = 'Please Select Lead Organization';
            $("trialDTO.leadOrganizationIdentifier").value = '';
            $('trialDTO.leadOrganizationName').value = '';
        }
        $('dropdown-leadOrganization').hide();
      }
      
      function lookup4sponsor(selection, name) {
          if(selection === undefined || selection == "") {
                $('trialDTO.sponsorNameField').innerHTML = 'Please Select Sponsor Organization';
                $('trialDTO.sponsorName').value = '';
                $("trialDTO.sponsorIdentifier").value = '';
            } else if(selection == -1) {
                showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
            } else if(selection > 0) {
                $('trialDTO.sponsorNameField').innerHTML = name;
                $('trialDTO.sponsorName').value = name;
                $("trialDTO.sponsorIdentifier").value = selection;
            } else {
                //if there is no valid selection we undo the selection enteirly.
                $('trialDTO.sponsorNameField').innerHTML = 'Please Select Sponsor Organization';
                $("trialDTO.sponsorIdentifier").value = '';
                $('trialDTO.sponsorName').value = '';
            }
            $('dropdown-sponsorOrganization').hide();
          
      }
      
      function lookup4loadSummary4Sponsor(selection, name) {
        if(selection === undefined || selection == "") {
            //do nothing
        } else if(selection == -1) {
            showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Data Table 4 Sponsor/Source');
        } else if(selection > 0) {
          var url = '/registry/protected/popupaddSummaryFourOrg.action';
          var params = { orgId: selection, chosenName : name };
          var div = document.getElementById('loadSummary4FundingSponsorField');   
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
          var aj = callAjaxPost(div, url, params);
        }
        $('dropdown-sum4Organization').hide();
          
      }
      
      document.observe("dom:loaded", function() {
    	                                 displayTrialStatusDefinition('trialDTO_statusCode');
    	                                 
                                         if($('trialDTO.leadOrganizationName').value) {
                                            $('trialDTO.leadOrganizationNameField').innerHTML = $('trialDTO.leadOrganizationName').value;
                                         }
                                         if($('trialDTO.sponsorName').value) {
                                            $('trialDTO.sponsorNameField').innerHTML = $('trialDTO.sponsorName').value;
                                         }
                                         if($('trialDTO.leadOrganizationIdentifier').value) {
                                                                                	 
                                        	 var values = jQuery("#tempProgramCodeValues").val();
                                        	 var programCodeText ="";
                                        	 if(values!=null && values!=undefined) {
                                        	 for(var i=0;i<values.length;i++) {
                                        		 if(i==0) {
                                        			 programCodeText = values[i];
                                        		 }
                                        		 else {
                                        			 programCodeText = programCodeText +";"+values[i];      			
                                        		 }
                                        	 }
                                        	 } 
                                        	 //in case retrive from draft
                                        	 else if (jQuery('#programCodeTextValue').val()!=null) {
                                        		 programCodeText = jQuery('#programCodeTextValue').val();
                                        	 }
                                        	
                                        	 loadProgramCodes($('trialDTO.leadOrganizationIdentifier').value,
                                        	 programCodeText);
                                        	 
                                         }
                                        
                                         
                                     });
      
                 
      Event.observe(window, "load", setDisplayBasedOnTrialType);
  
  </script>
  <style>
  li.select2-results__option {
               text-overflow: ellipsis;
               overflow: hidden;
               max-width: 395px;
               white-space: nowrap;
}
 li.select2-selection__choice > span.select2-selection__choice__remove {
               right: 3px !important;
               left: inherit !important;
               color:#d03b39 !important;
               padding-left:2px;
               float:right;
 }
  </style>
</head>
<body>
<!-- main content begins-->
  <h1 class="heading"><span><fmt:message key="submit.trial.page.header"/></span></h1>
  <p>Use this form to register trials with the NCI Clinical Trials Reporting Program. Required fields are marked by asterisks (<span class="required">*</span>).</p>
  <c:set var="topic" scope="request" value="submittrial"/>
  
  

      <reg-web:failureMessage/>
      <s:form cssClass="form-horizontal" role="form" name="submitTrial" method="POST" enctype="multipart/form-data">
          <s:token/>
          <s:if test="hasActionErrors()">
              <div class="alert alert-danger">
                  <s:actionerror/>
              </div>
          </s:if>
          <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
          <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
          <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
          <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
          <s:hidden name="page" />
          <div class="form-group">
              <label for="organization-type" class="col-xs-4 control-label left-align">XML Required, Enable "Upload from NCI CTRP" in <a data-placement="top" rel="tooltip" data-original-title="Open in new window" href="http://www.clinicaltrials.gov/" target="_new">ClinicalTrials.gov</a>?</label>                    
              <div class="col-xs-4">
                  <s:radio cssClass="radio-inline" name="trialDTO.xmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="hidePrimaryCompletionDate(), toggledisplayDivs(this);"/>
                  <i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message key="tooltip.ct_gov_xml" />' data-placement="top" data-trigger="hover"></i>
              </div>                    
          </div>
          <button type="button" class="expandcollapse btn btn-icon btn-sm btn-default" state="0"><i class="fa-minus-circle"></i> Collapse All</button>
          <div class="accordion-group">
              <%@ include file="/WEB-INF/jsp/nodecorate/trialIdentifiers.jsp" %>
              <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section2"><fmt:message key="submit.trial.otherIdentifiers"/></a></div>
                      <div id="section2" class="accordion-body in">
                          <div class="container">
                              <div class="form-group">
                                  <label for="otherIdentifierOrg" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherIdentifier"/></label>
                                  <div class="col-xs-4">
                                      <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value="" class="form-control"/>
                                  </div>
                                  <div class="col-xs-4">
                                      <button onclick="addOtherIdentifier();" id="otherIdbtnid" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i> Add Other Identifier</button>
                                      <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.other_identifier"/>"  data-placement="top" data-trigger="hover"></i> 
                                  </div>
                              </div>
                              <div class="table-header-wrap" id="otherIdentifierdiv">
                                      <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp" %>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="accordion">
                   <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a></div>
                       <div id="section3" class="accordion-body in">
                           <div class="container">
                               <div class="form-group">
                               <label for="trialDTO.officialTitle" class="col-xs-4 control-label"><fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                                   <div class="col-xs-4">
                                    <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="form-control charcounter"/>
                                    <span class="alert-danger">
                                        <s:fielderror>
                                            <s:param>trialDTO.officialTitle</s:param>
                                        </s:fielderror>
                                    </span>
                                  </div>
                                    <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.title"/>"  data-placement="top" data-trigger="hover"></i>
                          </div>
                           <%@ include file="/WEB-INF/jsp/nodecorate/phasePurpose.jsp" %>
                       </div>
                   </div>
               </div>
               <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="submit.trial.leadOrgInvestigator"/><span class="required">*</span></a></div>
                  <div id="section4" class="accordion-body in">
                      <div class="container">
                      <div class="form-group">                                
                          <label for="trialDTO.leadOrganizationNameField" class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label>
                          <div id="loadOrgField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                          </div>
                          
                          <div class="col-xs-10">
                            <div align="center"  id ="programCodesLoad" style="display:none" >
                                <img  src="${pageContext.request.contextPath}/images/loading.gif"
                                />
                                &nbsp;Loading...
                                </div>
                                
                                
                         </div>
                          
                                   
                      </div>     
                        
                      <div class="form-group">
                          <label for="trialDTO.piName" class="col-xs-4 control-label"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label>
                          <div id="loadPersField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadPrincipalInvestigator.jsp" %>
                          </div>              
                      </div>
                  </div>
              </div>
              </div>
              <s:if test="%{trialDTO.xmlRequired == true}"><div id="sponsorDiv" style="display:''">
                      <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                  </div></s:if>
              <s:else><div id="sponsorDiv" style="display:none">
                      <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                  </div></s:else>
              <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section6"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a></div>
                  <div id="section6" class="accordion-body in">
                      <div class="container">
                          <div class="form-group">
                              <label for="trialDTO.summaryFourFundingCategoryCode" class="col-xs-4 control-label"><fmt:message key="update.trial.summary4FundingCategory"/></label>
                              <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                              <div class="col-xs-4">
                                 <s:select headerKey="" headerValue="--Select--" id="trialDTO.summaryFourFundingCategoryCode" 
                                     name="trialDTO.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues" 
                                     cssClass="form-control" disabled="true"/>
                                 <span class="alert-danger">
                                     <s:fielderror>
                                         <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                                     </s:fielderror>
                                 </span>
                              </div>
                              <div class="col-xs-4">
                              <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Trial category you selected for trial submission."  data-placement="top" data-trigger="hover"></i>
                              </div>
                          </div>
                          <div class="form-group">
                              <label id="trialDTO.summaryFourOrgNameTR" for="trialDTO.summaryFourOrgName" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.summary4Sponsor"/><span class="required">*</span></label>
                              <div class="col-xs-6" style="padding-top: 7px">
                                  <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsorSelector.jsp" %>
                              </div>   
                              
                          </div>
                           <div class="form-group" id="programCodeBlock" style="display:none" >                               
                              <label for="trialDTO.programCodesList" class="col-xs-4 control-label"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                              <!-- As per new requirements we don't need to display program codes tex field at all -->
                              
                              <div class="col-xs-4" >
                              <s:select size="2" multiple="true"  name="trialDTO.programCodesList" id="programCodesValues"  list="#{trialDTO.programCodesMap}"
                                cssClass="form-control" />
                                
                              </div>  
                                <div class="col-xs-4"  >
                               <c:if test="${sessionScope.isSiteAdmin}">
                                     <a  style="display: inline-block;padding: 6px;border-bottom:none!important;" href="javascript:void();" onclick="showManageProgramCodes();">Manage Program Codes</a>
                                     <i  class="fa-question-circle help-text" style="vertical-align: -5px;" id="popover" rel="popover" data-content="<fmt:message key="tooltip.summary_4_program_code" />"  data-placement="top" data-trigger="hover"></i>
                                 </c:if>
                                  <c:if test="${sessionScope.isSiteAdmin==false}">
                                   <i class="fa-question-circle help-text"  id="popover" rel="popover" data-content="<fmt:message key="tooltip.summary_4_program_code" />"  data-placement="top" data-trigger="hover"></i>
                                  </c:if>
                                </div>
                            </div>
                      </div>
                  </div>
              </div>
              <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section7"><fmt:message key="submit.trial.grantInfo"/><span class="required">*</span></a></div>
                  <div id="section7" class="accordion-body in">
                      <div class="container">
                      <p><fmt:message key="submit.trial.grantInstructionalText"/></p>              
                          <div class="form-group">
                              <label for="nciGrant" class="col-xs-4 control-label">Is this trial funded by an NCI grant? <span class="required">*</span></label>
                              <div class="col-xs-4">
                                  <s:radio cssClass="radio-inline" name="trialDTO.nciGrant" id="nciGrant"  list="#{true:'Yes', false:'No'}" />
                              </div>
                          </div> 
                          <div class="table-header-wrap">
                          <table class="table table-bordered">
                              <thead>
                                  <tr>
                                      <th><label for="fundingMechanismCode"><fmt:message key="submit.trial.fundingMechanism"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.funding_mechanism" />"  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th><label for="nihInstitutionCode"><fmt:message key="submit.trial.instituteCode"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.institution_code" />" data-placement="top" data-trigger="hover"></i></label></th>
                                      <th><label for="serialNumber"><fmt:message key="submit.trial.serialNumber"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.serial_number"/>"  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th><label for="nciDivisionProgramCode"> <fmt:message key="submit.trial.divProgram"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.nci_division_program_code"/>"  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th style="display:none">
                                          <label for="fundingPercent"><fmt:message key="submit.trial.fundingPercent"/></label>
                                      </th>
                                      <th>&nbsp;</th>
                                  </tr>
                              </thead>
                              <tbody>
                                  <tr>
                                      <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                                      <td>
                                          <s:select headerKey="" headerValue="--Select--"
                                               name="fundingMechanismCode"
                                               list="#fundingMechanismValues"
                                               listKey="fundingMechanismCode"
                                               listValue="fundingMechanismCode"
                                               id="fundingMechanismCode"
                                               cssClass="form-control"/>
                                      </td>
                                      <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                                      <td>
                                          <s:select headerKey="" headerValue="--Select--"
                                               name="nihInstitutionCode"
                                               list="#nihInstituteCodes"
                                               listKey="nihInstituteCode"
                                               listValue="nihInstituteCode"
                                               id="nihInstitutionCode"
                                                cssClass="form-control"/>
                                      </td>
                                      <td>
                                          <s:textfield name="serialNumber" id="serialNumber" maxlength="200" cssClass="form-control"/>
                                      </td>
                                      <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                                      <td>
                                          <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  
                                              cssClass="form-control"/>
                                      </td>
                                      <td style="display:none">
                                          <s:textfield name="fundingPercent" id="fundingPercent" maxlength="5" size="5"  cssClass="form-control"/>%
                                      </td>
                                      <td><button type="button" id="grantbtnid" class="btn btn-icon btn-default" onclick="addGrant();"><i class="fa-plus"></i>Add Grant</button></td>
                                  </tr>
                              </tbody>
                          </table>
                          </div>
                          <p/>
                          <div class="table-header-wrap" id="grantdiv">
                              <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                          </div>
                          <span class="alert-danger">
                              <s:fielderror>
                                  <s:param>trialDTO.nciGrant</s:param>
                              </s:fielderror>
                          </span>                              
                   </div>
              </div>
          </div>
          <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>
          <div class="accordion">
            <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section9"><fmt:message key="submit.trial.indInfo"/></a></div>
                <div id="section9" class="accordion-body in">
                    <div class="container">
                        <p><fmt:message key="submit.trial.indInstructionalText"/></p>
                        <%@ include file="/WEB-INF/jsp/nodecorate/indide.jsp" %>
                        <p/>
                      <div id="indidediv" class="table-header-wrap">
                          <%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
                      </div>
                      <div class="mt10 align-center scrollable"><i class="fa-angle-left"></i> Scroll left/right to view full table <i class="fa-angle-right"></i></div>
            </div>
          </div>
        </div>
        <s:if test="%{trialDTO.xmlRequired == true}"><div id="regDiv" style="display:''">
                 <!-- Regulatory page -->
                 <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
             </div></s:if>
         <s:else><div id="regDiv" style="display:none">
                 <!-- Regulatory page -->
                 <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
             </div></s:else>
          <div id="uploadDocDiv">
              <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
          </div>
        
         </div>
          <div class="align-center button-row">
              <button type="button" class="btn btn-icon btn-primary" onclick="partialSave()"><i class="fa-floppy-o"></i>Save as Draft</button>
              <button type="button" class="btn btn-icon btn-primary review" onclick="reviewProtocol()"><i class="fa-file-text-o"></i>Review Trial</button>
              <button type="button" class="btn btn-icon btn-default" onclick="cancelProtocol()"><i class="fa-times-circle"></i>Cancel</button>
            </div>
          <s:hidden name="uuidhidden"/>
            <select id="tempProgramCodeValues" multiple="true" size ="2" style="display:none" >
           <c:forEach items="${trialDTO.programCodesList}" var="element"> 
           <option value="${element}" selected >${element}</option>                              
          </c:forEach>
        
         </select>
          <s:hidden id="programCodeTextValue" name="trialDTO.programCodeText"/>
      </s:form>
</body>
</html>