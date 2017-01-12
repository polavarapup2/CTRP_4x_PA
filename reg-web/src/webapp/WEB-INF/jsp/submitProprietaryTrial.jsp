<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="submit.proprietary.trial.page.title"/></title>
        <s:head/>
         <script type="text/javascript" src="${scriptPath}/js/submitTrial.js"></script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <script type="text/javascript" language="javascript">
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
            
            function lookup4loadleadorg(selection, name) {
                if(selection === undefined || selection == "") {
                    $('trialDTO.leadOrganizationNameField').innerHTML = 'Please Select Lead Organization';
                    $("trialDTO.leadOrganizationIdentifier").value = '';
                    $('trialDTO.leadOrganizationName').value = '';
                } else if(selection == -1) {
                    showPopup("${lookupOrgUrl}",loadLeadOrgDiv, 'Select Lead Organization');
                } else if(selection > 0) {
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
            
            function lookup4loadSiteOrg (selection, name) {
                if(selection === undefined || selection == "") {
                    $('trialDTO.siteOrganizationNameField').innerHTML = 'Please Select Submitting Organization';
                    $("trialDTO.siteOrganizationIdentifier").value = '';
                    $('trialDTO.siteOrganizationName').value = '';
                } else if (selection == -1) {
                    showPopup("${lookupOrgUrl}", function() {
                        lookup4loadSiteOrg(orgid, chosenname);
                    }, 'Select Submitting Organization');
                } else if(selection > 0) {
                    $('trialDTO.siteOrganizationNameField').innerHTML = name;
                    $('trialDTO.siteOrganizationName').value = name;
                    $("trialDTO.siteOrganizationIdentifier").value = selection;
                } else {
                    //if there is no valid selection we undo the selection enteirly.
                    $('trialDTO.siteOrganizationNameField').innerHTML = 'Please Select Submitting Organization';
                    $("trialDTO.siteOrganizationIdentifier").value = '';
                    $('trialDTO.siteOrganizationName').value = '';
                }
                $('dropdown-siteOrganization').hide();
            }
            
            function lookup4loadSitePerson(){
                showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Site Principal Investigator');
            }
            
            function loadLeadPersDiv() {
                $("trialDTO.sitePiIdentifier").value = persid;
                $('trialDTO.sitePiName').value = chosenname;
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

            function deleteSummary4SponsorRow(rowid) {
                var  url = '/registry/protected/popupdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadSummary4FundingSponsorField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function reviewProtocol () {
                submitFirstForm("review", "submitProprietaryTrialreview.action");
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
            }
            
            function cancelProtocol() {
                submitFirstForm("cancle", "submitProprietaryTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }

            function partialSave() {
                submitFirstForm(null, "submitProprietaryTrialpartialSave.action");
                showPopWin('${partialSave}', 600, 200, '', 'Saving Draft');
            }
            
            function toggledisplay (it, box) {
                var vis = (box.checked) ? "block" : "none";
                $(it).style.display = vis;
            }
            
            function toggledisplay2 (it) {
                var vis = $(it).style.display;
                $(it).style.display = (vis == "block") ? "none" : "block";
            }
            
            document.observe("dom:loaded", function() {
                if($('trialDTO.leadOrganizationName').value) {
                   $('trialDTO.leadOrganizationNameField').innerHTML = $('trialDTO.leadOrganizationName').value;
                }
                if($('trialDTO.siteOrganizationName').value) {
                   $('trialDTO.siteOrganizationNameField').innerHTML = $('trialDTO.siteOrganizationName').value;
                }
               
            });
            
            Event.observe(window, "load", setDisplayBasedOnTrialType);
            
        </script>
    </head>
    <body>
        <!-- main content begins-->
        <h1 class="heading"><span><fmt:message key="submit.trial.page.header"/></span></h1>
        <c:set var="topic" scope="request" value="ctimport"/>        
        <reg-web:failureMessage/>
        <s:form name="submitProprietaryTrial" method="POST" enctype="multipart/form-data" cssClass="form-horizontal" role="form">
            <s:token/>
            <s:if test="hasActionErrors()">
                <div class="alert alert-danger">
                    <s:actionerror/>
                </div>
            </s:if>
                <s:hidden name="trialDTO.sitePiIdentifier" id="trialDTO.sitePiIdentifier"/>
                
                <s:hidden name="trialDTO.siteOrganizationIdentifier" id="trialDTO.siteOrganizationIdentifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                <s:hidden name="page" />
                <p>Register trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
                <button type="button" class="expandcollapse btn btn-icon btn-sm btn-default" state="0"><i class="fa-minus-circle"></i> Collapse All</button>
                <div class="accordion-group">
                <div class="accordion">
                    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section1"><fmt:message key="submit.proprietary.trial.trialIdentification"/><span class="required">*</span></a></div>
                    <div id="section1" class="accordion-body in">
                    <div class="container">
                    <div class="form-group">
                        <label for="trialDTO.leadOrganizationName" class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label>
                        <div id="loadOrgField" class="col-xs-8" >
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="trialDTO.leadOrgTrialIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                            <s:textfield id="trialDTO.leadOrgTrialIdentifier" name="trialDTO.leadOrgTrialIdentifier"  maxlength="30"  cssClass="form-control" />
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                                </s:fielderror>
                            </span>
                        </div>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.trial_id" />'  data-placement="top" data-trigger="hover"></i>
                    </div>
                    <div class="form-group">
                        <label for="trialDTO.siteOrganizationName" class="col-xs-4 control-label"><fmt:message key="submit.proprietary.trial.siteOrganization"/><span class="required">*</span></label>
                        <div class="col-xs-6"><%@ include file="/WEB-INF/jsp/nodecorate/trialSiteOrganization.jsp" %>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.submitting_organization_name" />'  data-placement="top" data-trigger="hover"></i></div>
                    </div>
                   <div class="form-group">
                        <label for="trialDTO.localSiteIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.proprietary.trial.siteidentifier"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                            <s:textfield id="trialDTO.localSiteIdentifier" name="trialDTO.localSiteIdentifier"  maxlength="200"  cssClass="form-control" />
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.localSiteIdentifier</s:param>
                                </s:fielderror>
                            </span>
                        </div>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.submitting_organization_local_trial_identifier"/>'  data-placement="top" data-trigger="hover"></i>
                    </div>  
                       
                   <div class="form-group">
                        <label for="trialDTO.nctIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.nctNumber"/></label>
                        <div class="col-xs-4">
                            <s:textfield id="trialDTO.nctIdentifier" name="trialDTO.nctIdentifier"  maxlength="200"  cssClass="form-control" />
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.nctIdentifier</s:param>
                                </s:fielderror>
                            </span>
                        </div>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.nct_number" />'  data-placement="top" data-trigger="hover"></i>
                    </div>  
                 </div>
                 </div>
                 </div>
                 <div class="accordion">
                 <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section2"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a></div>
                 <div id="section2" class="accordion-body in">
                 <div class="container">    
                    <div class="form-group">
                        <label for="trialDTO.officialTitle" class="col-xs-4 control-label"><fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                             <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="form-control"/>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.officialTitle</s:param>
                                </s:fielderror>
                            </span>
                        </div>
                        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.title" />'  data-placement="top" data-trigger="hover"></i>
                    </div>  
 
                    <div class="form-group">
                        <label for="trialDTO.trialType" class="col-xs-4 control-label"><fmt:message key="submit.trial.type"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                             <input type="radio" name="trialDTO.trialType" value="Interventional"
                                ${trialDTO.trialType!='NonInterventional'?'checked=checked':''}
                                onclick="setDisplayBasedOnTrialType();" 
                                id="trialDTO.trialType.Interventional"> <label for ="trialDTO.trialType.Interventional">Interventional</label>
                            <input type="radio" name="trialDTO.trialType" value="NonInterventional"
                                ${trialDTO.trialType=='NonInterventional'?'checked=checked':''}
                                onclick="setDisplayBasedOnTrialType();" 
                                id="trialDTO.trialType.Noninterventional"><label for="trialDTO.trialType.Noninterventional"> Non-interventional</label>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.trialType</s:param>
                                </s:fielderror>
                            </span>
                        </div>
                    </div>  
                 
                    <div class="form-group non-interventional">
                        <label for="trialDTO.studySubtypeCode" class="col-xs-4 control-label"><fmt:message key="submit.trial.studySubtypeCode"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                            <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />
                            <s:select headerKey="" headerValue="--Select--" id ="trialDTO.studySubtypeCode" name="trialDTO.studySubtypeCode" list="#typeCodeValues"  cssClass="form-control" 
                                            value="trialDTO.studySubtypeCode"/>
                            <span class="alert-danger">
                              <s:fielderror>
                              <s:param>trialDTO.studySubtypeCode</s:param>
                             </s:fielderror>
                            </span>
                        </div>
                    </div>  
 
                    <%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/phase.jsp" %>
                    <!-- include po person jsp -->
                    <div class="form-group">
                        <label for="trialDTO.sitePiName" class="col-xs-4 control-label"><fmt:message key="submit.proprietary.trial.siteInvestigator"/><span class="required">*</span></label>
                        <div id="loadPersField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSitePrincipalInvestigator.jsp" %>
                        </div>
                    </div>
                </div>
                </div>
                </div>
                <!--  summary4 information -->
                <div class="accordion">
                <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a></div>
                <div id="section3" class="accordion-body in">
                <div class="container">
                     <div class="form-group">
                        <label for="trialDTO.summaryFourFundingCategoryCode" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.summary4FundingCategory"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                            <s:select headerKey="" headerValue="--Select--"
                                      id="trialDTO.summaryFourFundingCategoryCode"
                                      name="trialDTO.summaryFourFundingCategoryCode"
                                      list="#{'National':'National', 'Externally Peer-Reviewed':'Externally Peer-Reviewed','Institutional':'Institutional','Industrial':'Industrial/Other'}"
                                      cssClass="form-control" disabled="true" />
                             <span class="alert-danger">
                                   <s:fielderror>
                                       <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                                   </s:fielderror>
                             </span>
                       </div>
                       <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.summary_4_funding_sponsor_type" />'  data-placement="top" data-trigger="hover"></i>
                     </div>
                    
                    <div class="form-group">
                        <label for="trialDTO.summaryFourOrgName" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.summary4Sponsor"/><span class="required">*</span></label>
                        <div class="col-xs-8">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsorSelector.jsp" %>
                        </div>
                    </div>
                   
                    <div class="form-group">
                        <label for="trialDTO.consortiaTrialCategoryCode" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.consortiaTrialCategoryCode"/></label>
                        <div class="col-xs-4">
                          <s:select headerKey="" headerValue="Yes"
                                  id="trialDTO.consortiaTrialCategoryCode"
                                  name="trialDTO.consortiaTrialCategoryCode"
                                  list="consortiaTrialCategoryValueMap"
                                  cssClass="form-control" />
                            <span class="alert-danger">
                               <s:fielderror>
                                   <s:param>trialDTO.consortiaTrialCategoryCode</s:param>
                               </s:fielderror>
                            </span>
                        </div>
                    </div>
                   
                   
                </div>
                </div>
                </div>    
                <div class="accordion">
                <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="submit.proprietary.trial.statusDate"/><span class="required">*</span></a></div>
                <div id="section4" class="accordion-body in">
                <div class="container">
                
                    <div class="form-group">
                        <label for="trialDTO.siteStatusCode" class="col-xs-4 control-label"><fmt:message key="submit.trial.siteRecruitmentStatus"/><span class="required">*</span></label>
                        <div class="col-xs-4">
                            <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                            <s:select id="trialDTO.siteStatusCode" headerKey="" headerValue="--Select--" name="trialDTO.siteStatusCode" list="#recruitmentStatusValues" value="trialDTO.siteStatusCode" cssClass="form-control"/>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.siteStatusCode</s:param>
                                </s:fielderror>
                            </span>
                       </div>
                       <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.current_trial_status" />'  data-placement="top" data-trigger="hover"></i>
                    </div>                            
                    <div class="form-group">
                        <label for="trialDTO.siteStatusDate" class="col-xs-4 control-label"><fmt:message key="submit.trial.siteRecruitmentStatusDate"/><span class="required">*</span></label>
                        <div class="col-xs-2">
                          <div id="datetimepicker" class="datetimepicker input-append">                    
                            <s:textfield id="trialDTO.siteStatusDate" name="trialDTO.siteStatusDate" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                            <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.siteStatusDate</s:param>
                                </s:fielderror>
                            </span>
                            </div>
                       </div>
                        <div class="col-xs-4"><i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.site_recruitment_status_date" />'  data-placement="top" data-trigger="hover"></i></div>
                    </div>

                    <div class="form-group">
                        <label for="trialDTO.dateOpenedforAccrual" class="col-xs-4 control-label"><fmt:message key="submit.proprietary.trial.dateOpenedforAccrual"/></label>
                        <div class="col-xs-2">
                          <div id="datetimepicker" class="datetimepicker input-append">                    
                            <s:textfield id="trialDTO.dateOpenedforAccrual" name="trialDTO.dateOpenedforAccrual"  data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                            <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.dateOpenedforAccrual</s:param>
                                </s:fielderror>
                            </span>
                          </div>
                       </div>
                       <div class="col-xs-4"><i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.date_opened_for_accrual" />'  data-placement="top" data-trigger="hover"></i></div>
                    </div>

                    <div class="form-group">
                        <label for="trialDTO.dateClosedforAccrual" class="col-xs-4 control-label"><fmt:message key="submit.proprietary.trial.dateClosedforAccrual"/></label>
                        <div class="col-xs-2">
                          <div id="datetimepicker" class="datetimepicker input-append">                    
                            <s:textfield id="trialDTO.dateClosedforAccrual" name="trialDTO.dateClosedforAccrual" data-format="MM/dd/yyyy" type="text" cssClass="form-control" placeholder="mm/dd/yyyy"/>
                            <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                            <span class="info"><fmt:message key="error.proprietary.submit.dateOpenReq" /></span>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.dateClosedforAccrual</s:param>
                                </s:fielderror>
                            </span>
                         </div>
                       </div>
                       <div class="col-xs-4"><i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content='<fmt:message key="tooltip.date_closed_for_accrual" />'  data-placement="top" data-trigger="hover"></i></div>
                    </div>
                </div>
                </div>
                </div>
                <div id="uploadDocDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                
                </div>
                
                <p align="center" class="info">
                   Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.
                   <br>Once you submit the trial you will not be able to modify the information.
                </p>
                <div class="align-center button-row">
                  <button type="button" class="btn btn-icon btn-primary" onclick="partialSave()"><i class="fa-floppy-o"></i>Save as Draft</button>
                  <button id="reviewTrialBtn" type="button" class="btn btn-icon btn-primary" onclick="reviewProtocol()"><i class="fa-file-text-o"></i>Review Trial</button>
                  <button type="button" class="btn btn-icon btn-default" onclick="cancelProtocol()"><i class="fa-times-circle"></i>Cancel</button>
                </div>
                <s:hidden name="uuidhidden"/>
            </s:form>
    </body>
</html>
