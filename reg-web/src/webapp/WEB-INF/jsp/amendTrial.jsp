<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="updateOrAmendMode" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="amend.trial.page.title"/></title>
        <link href="<c:url value='/styles/jquery-datatables/css/jquery.dataTables.min.css'/>" rel="stylesheet" type="text/css" media="all" />    
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
        <script type="text/javascript" src="${scriptPath}/js/submitTrial.js"></script>
        <s:head/>
        
        <c:url value="/protected/amendTrial" var="backendUrlTemplate"/>  
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocol"/>
        <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
            var orgid;
            var chosenname;
            var p30GrantSerialNumber;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;

            jQuery(function() {
                jQuery("#serialNumber").autocomplete({delay: 250,
                      source: function(req, responseFn) {
                        var instCode = jQuery("#nihInstitutionCode").val();
                        if ('CA' != instCode) {
                            return;
                        }
                        var url = registryApp.contextPath + '/ctro/json/ajaxI2EGrantsloadSerialNumbers.action?serialNumberMatchTerm=' + req.term;
                        jQuery.getJSON(url,null,function(data){
                               responseFn(jQuery.map(data.serialNumbers, function (value, key) { 
                                    return {
                                        label: value,
                                        value: key
                                    };
                               }));
                        });
                    }
                });
            });

            function setorgid(orgIdentifier, oname, p30grant) {
                orgid = orgIdentifier;
                chosenname = oname.replace(/&apos;/g,"'");
                p30GrantSerialNumber = p30grant;
            }
            
            function setpersid(persIdentifier, sname, email, phone) {
                persid = persIdentifier;
                chosenname = sname.replace(/&apos;/g,"'");
                contactMail = email;
                contactPhone = phone;
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
            
            function lookup4loadleadpers() {
                showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
            }
            
            function loadLeadPersDiv() {
                $("trialDTO.piIdentifier").value = persid;
                $('trialDTO.piName').value = chosenname;
                
                var partyType = $F('trialDTO.responsiblePartyType');
                if (partyType=='pi') {                
                    $('trialDTO.responsiblePersonIdentifier').value=persid;
                    $('trialDTO.responsiblePersonName').value=chosenname;
                }
            }
            
            function loadLeadOrgDiv() {
            	loadProgramCodes(orgid);
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationNameField').innerHTML = chosenname;
                $('trialDTO.leadOrganizationName').value = chosenname;
                deleteP30Grants();
                if( p30GrantSerialNumber){
                    var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
                    var params = {
                        fundingMechanismCode: 'P30',
                        nciDivisionProgramCode: 'OD',
                        nihInstitutionCode: 'CA',
                        serialNumber: p30GrantSerialNumber
                    };
                    var div = $('grantdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    resetGrantRow();
                } 
            }

            function loadSponsorDiv() {
                $("trialDTO.sponsorIdentifier").value = orgid;
                $('trialDTO.sponsorName').value = chosenname;                             
                respartOrgid = orgid;
                
                var partyType = $F('trialDTO.responsiblePartyType');
                if (partyType=='si') {                
                     $('trialDTO.responsiblePersonAffiliationOrgId').value=orgid;
                     $('trialDTO.responsiblePersonAffiliationOrgName').value=chosenname;
                }
            }
            
            function loadSummary4SponsorDiv() {
                var url = '/registry/protected/popupaddSummaryFourOrg.action';
                var params = { orgId: orgid, chosenName : chosenname };
                var div = document.getElementById('loadSummary4FundingSponsorField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function deleteSummary4SponsorRow(rowid) {
                var  url = '/registry/protected/popupdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadSummary4FundingSponsorField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function reviewProtocol () {
            	submitFirstForm("save", "amendTrialreview.action");
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
            }
            
            function cancelProtocol() {
                submitFirstForm("Submit", "amendTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function addGrant(){
                var fundingMechanismCode = $('fundingMechanismCode').value;
                var nihInstitutionCode = $('nihInstitutionCode').value;
                var nciDivisionProgramCode = $('nciDivisionProgramCode').value;
                var serialNumber = $('serialNumber').value;
                serialNumber = trim(serialNumber);
                var fundingPercent =  $('fundingPercent').value;
                var isValidGrant;
                var isSerialEmpty = false;
                var alertMessage = "";
            
                if (fundingMechanismCode.length == 0 || fundingMechanismCode == null) {
                    isValidGrant = false;
                    alertMessage="Please select a Funding Mechanism";
                }
                if (nihInstitutionCode.length == 0 || nihInstitutionCode == null) {
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Please select an Institute Code";
                }
                if (serialNumber.length == 0 || serialNumber == null) {
                    isValidGrant = false;
                    isSerialEmpty = true;
                    alertMessage=alertMessage+ "\n Please enter a Serial Number";
                }
                if (nciDivisionProgramCode.length == 0 || nciDivisionProgramCode == null) {
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Please select a NCI Division/Program Code";
                }
                if (isSerialEmpty == false && isNaN(serialNumber)){
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Serial Number must be numeric";
                } else if (isSerialEmpty == false && serialNumber != null) {
                           var numericExpression = /^[0-9]+$/;
                           if (!numericExpression.test(serialNumber)) {
                               isValidGrant = false;
                               alertMessage=alertMessage+ "\n Serial Number must be numeric";
                           }
                }
                if (fundingPercent.length != 0 && fundingPercent != null) {
                    if (isNaN(fundingPercent)){
                        isValidGrant = false;
                        alertMessage=alertMessage+ "\n % of Grant Funding must be numeric";
                    } else if(Number(fundingPercent) > 100.0 || Number(fundingPercent) < 0.0){
                        isValidGrant = false;
                        alertMessage=alertMessage+ "\n % of Grant Funding must be positive and <= 100";
                    }
                }
                if (isValidGrant == false) {
                    alert(alertMessage);
                    return false;
                }
                var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
                var params = {
                    fundingMechanismCode: fundingMechanismCode,
                    nciDivisionProgramCode: nciDivisionProgramCode,
                    nihInstitutionCode: nihInstitutionCode,
                    serialNumber: serialNumber,
                    fundingPercent: fundingPercent
                };
                var div = $('grantdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetGrantRow();
            }
            
            function deleteGrantRow(rowid) {
                var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrant.action';
                var params = { uuid: rowid };
                var div = $('grantdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function resetGrantRow() {
                $('fundingMechanismCode').value = '';
                $('nihInstitutionCode').value = '';
                $('serialNumber').value = '';
                $('nciDivisionProgramCode').value = '';
                $('fundingPercent').value = '';
            }
            
            function deleteIndIde(rowid) {
                var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIde.action';
                var params = { uuid: rowid };
                var div = $('indidediv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function addIndIde(indIde, number, grantor, holderType, programCode, expandedAccess, expandedAccessType, exemptIndicator) {
                var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicator.action';
                var params = {
                    exemptIndicator: exemptIndicator,
                    expandedAccess: expandedAccess,
                    expandedAccessType: expandedAccessType,
                    grantor: grantor,
                    holderType: holderType,
                    indIde: indIde,
                    number: number,
                    programCode: programCode
                };
                var div = $('indidediv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetValues();
            }
            
            function loadRegAuthoritiesDiv() {
                var url = '/registry/protected/ajaxgetOAuthOrgsgetTrialOversightAuthorityOrganizationNameList.action';
                var params = { countryid: $('countries').value };
                var div = $('loadAuthField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function checkFDADropDown() {
                if ($('trialDTO.fdaRegulatoryInformationIndicatorNo').checked) {
                    input_box = confirm("Section 801 and Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true) {
                        $('trialDTO.section801IndicatorNo').checked = false;
                        $('trialDTO.section801IndicatorYes').checked = false;
                        $('trialDTO.delayedPostingIndicatorNo').checked = false;
                        $('trialDTO.delayedPostingIndicatorYes').checked = false;
                        hideRow($('sec801row'));
                        hideRow($('delpostindrow'));
                    } else {
                      $('trialDTO.fdaRegulatoryInformationIndicatorNo').checked = false;
                      $('trialDTO.fdaRegulatoryInformationIndicatorYes').checked = true;
                    }
                } else {
                    showRow($('sec801row'));
                }
            }
        
            function checkSection108DropDown() {
                if ($('trialDTO.section801IndicatorNo').checked) {
                    input_box = confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true) {
                        hideRow($('delpostindrow'));
                        $('trialDTO.delayedPostingIndicatorNo').checked = false;
                        $('trialDTO.delayedPostingIndicatorYes').checked = false;
                    } else {
                      $('trialDTO.fdaRegulatoryInformationIndicatorNo').checked = false;
                      $('trialDTO.fdaRegulatoryInformationIndicatorYes').checked = true;
                    $('trialDTO.section801IndicatorYes').checked = true;
                    }
                } else {
                    var value = '${sessionScope.trialDTO.delayedPostingIndicator}';
                    if (value !=null && value != '') {
                        if (value == 'Yes') {
                            $('trialDTO.delayedPostingIndicatorYes').checked = true;
                        } else {
                            $('trialDTO.delayedPostingIndicatorNo').checked = true;
                        }
                    } else {
                         $('trialDTO.delayedPostingIndicatorNo').checked = true;
                    }
                    showRow($('delpostindrow'));
                }
            }
            
            function hideRow(row) {
                row.style.display = 'none';
            }
            
            function showRow(row) {
                row.style.display = '';
            }
            
            function addOtherIdentifier() {
                var orgValue = trim($("otherIdentifierOrg").value);
                if (orgValue != null && orgValue != '') {
                    var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue };
                    var div = $('otherIdentifierdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("otherIdentifierOrg").value = "";
                } else {
                    alert("Please enter a valid Other identifier to add");
                }
            }
            
            function deleteOtherIdentifierRow(rowid) {
                var  url = '/registry/protected/ajaxManageOtherIdentifiersActiondeleteOtherIdentifierUpdate.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
     
            document.observe("dom:loaded", function() {
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
            		 
                     loadProgramCodes($('trialDTO.leadOrganizationIdentifier').value,
                    		 programCodeText);
                     
                 }
                displayTrialStatusDefinition('trialDTO_statusCode');
                if($('trialDTO.leadOrganizationName').value) {
                   $('trialDTO.leadOrganizationNameField').innerHTML = $('trialDTO.leadOrganizationName').value;
                }
                if($('trialDTO.sponsorName').value) {
                   $('trialDTO.sponsorNameField').innerHTML = $('trialDTO.sponsorName').value;
                }
               
               
            });
            
            document.observe("dom:loaded", function() {
             if ($('trialDTO.section801IndicatorYes').checked) {
                var value = '${sessionScope.trialDTO.delayedPostingIndicator}';
                if (value !=null && value != '') {
                   if (value == 'Yes') {
                       $('trialDTO.delayedPostingIndicatorYes').checked = true;
                   } else {
                       $('trialDTO.delayedPostingIndicatorNo').checked = true;
                   }
                }
                showRow($('delpostindrow'));
              }
            });
            
            Event.observe(window, "load", setDisplayBasedOnTrialType);
            Event.observe(window, "load", disableTrialTypeChangeRadios);
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
        <h1 class="heading"><span><fmt:message key="amend.trial.page.header"/></span></h1>
        <p>Use this form to register trials with the NCI Clinical Trials Reporting Program. Required fields are marked by asterisks (<span class="required">*</span>).</p>
        <c:set var="topic" scope="request" value="amendtrial"/>
        <reg-web:failureMessage/>
        <div id="general_trial_errors"></div>
            <s:form cssClass="form-horizontal" role="form" name="amendTrial" method="POST" validate="true" enctype="multipart/form-data">
                <s:token/>
                <s:if test="hasActionErrors()">
                    <div class="alert alert-danger"><s:actionerror/></div>
                </s:if>
                <s:else>
                    <s:actionerror/>
                </s:else>
                <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
                <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>                
                <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                    <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                </c:if>
                <s:hidden name="page" />
                <div class="form-group">
                  <label for="organization-type" class="col-xs-4 control-label left-align">XML Required, Enable "Upload from NCI CTRP" in <a data-placement="top" rel="tooltip" data-original-title="Open in new window" href="http://www.clinicaltrials.gov/" target="_new">ClinicalTrials.gov</a>?</label>                    
                  <div class="col-xs-4">
                      <s:radio cssClass="radio-inline" name="trialDTO.xmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="hidePrimaryCompletionDate(), toggledisplayDivs(this);"/>
                      <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.ct_gov_xml"/>" data-placement="top" data-trigger="hover"></i>
                  </div>                    
              </div>
              <button type="button" class="expandcollapse btn btn-icon btn-sm btn-default" state="0"><i class="fa-minus-circle"></i> Collapse All</button>
             <div class="accordion-group">
                <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section0"><fmt:message key="trial.amendDetails"/></a></div>
                      <div id="section0" class="accordion-body in">
                          <div class="container">
                              <div class="form-group">
                                  <label for="trialDTO.localAmendmentNumber" class="col-xs-4 control-label"><fmt:message key="view.trial.amendmentNumber"/></label>
                                  <div class="col-xs-4">
                                        <s:textfield id="trialDTO.localAmendmentNumber" name="trialDTO.localAmendmentNumber" maxlength="200" cssClass="form-control"  />
                                        <span class="alert-danger">
                                            <s:fielderror>
                                                <s:param>trialDTO.localAmendmentNumber</s:param>
                                            </s:fielderror>
                                        </span>
                                  </div>
                              </div>
                              <div class="form-group">
                                <label for="trialDTO.amendmentDate" class="col-xs-4 control-label"><fmt:message key="view.trial.amendmentDate"/><span class="required">*</span></label>
                                <div class="col-xs-2">
                                  <div id="datetimepicker" class="datetimepicker input-append">                    
                                    <s:textfield id="trialDTO.amendmentDate" name="trialDTO.amendmentDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                                    <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                                    <span class="alert-danger">
                                        <s:fielderror>
                                            <s:param>trialDTO.amendmentDate</s:param>
                                        </s:fielderror>
                                    </span>
                                  </div>
                                </div>                                
                                <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.current_trial_status_date"/>"  data-placement="top" data-trigger="hover"></i></div>
                             </div>
                          </div>
                      </div>
                  </div>
                    
                    <%@ include file="/WEB-INF/jsp/nodecorate/amendIdentifiersSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialOtherIdsSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/amendDetailsSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/amendLeadOrganizationSection.jsp" %>
                    
                 
                    
                    <s:if test="%{trialDTO.xmlRequired == true}">
                          <div id="sponsorDiv" style="display:''">
                              <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                          </div>
                      </s:if>
                      <s:else>
                          <div id="sponsorDiv" style="display:none">
                              <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                          </div>
                      </s:else>
                      
                    <!--  summary4 information -->
                    <%@ include file="/WEB-INF/jsp/nodecorate/summaryFourInfo.jsp" %>
                    
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
                                      <th><label for="nihInstitutionCode"><fmt:message key="submit.trial.instituteCode"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.institution_code" />"  data-placement="top" data-trigger="hover"></i></label></th>
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
  
                <!-- Status section -->
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
        
                <s:if test="%{trialDTO.xmlRequired == true}">
                     <div id="regDiv" style="display:''">
                         <!-- Regulatory page -->
                         <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
                     </div>
                 </s:if>
                 <s:else>
                     <div id="regDiv" style="display:none">
                         <!-- Regulatory page -->
                         <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
                     </div>
                 </s:else>
             
                 <c:if test="${requestScope.protocolDocument != null}">
                  <div class="accordion">
                    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section15">Existing Trial Related Documents</a></div>
                        <div id="section15" class="accordion-body in">
                            <div class="container">
                                <div class="table-header-wrap">
                                    <jsp:include page="/WEB-INF/jsp/searchTrialViewDocs.jsp"/>
                                </div>
                        </div>
                    </div>
                  </div>
                 </c:if>
                 <div id="uploadDocDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                 </div>
        
            </div> 
                       
               <p align="center" class="info">
                  Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.
                  <br>Once you submit the trial you will not be able to modify the information.
               </p>
            <div class="align-center button-row">
              <button type="button" class="btn btn-icon btn-primary review" onclick="reviewProtocol()"><i class="fa-file-text-o"></i>Review Trial</button>
              <button type="button" class="btn btn-icon btn-default" onclick="cancelProtocol()"><i class="fa-times-circle"></i>Cancel</button>
            </div>
                
                <s:hidden name="uuidhidden"/>
                <select id="tempProgramCodeValues" multiple="true" size ="2" style="display:none" >
           <c:forEach items="${trialDTO.programCodesList}" var="element"> 
           <option value="${element}" selected >${element}</option>                              
          </c:forEach>
        
            </s:form>
    </body>
</html>
