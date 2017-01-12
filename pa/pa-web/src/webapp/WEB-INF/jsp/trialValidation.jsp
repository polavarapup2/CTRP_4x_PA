<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="trialValidation.page.title"/></title>   
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js?7b391da0-c0d3-11e3-8a33-0800200c9a66'/>"></script>
        <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <script type="text/javascript" language="javascript"> 
            var orgid;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;
            var selectedName;    
            var orgname;
            var ts = 0;
            
            jQuery(function() {
                setFocusToFirstControl();
                jQuery("#saveButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "trialValidationupdate.action";
                    form.submit();
                });
                jQuery("#acceptButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "trialValidationaccept.action";
                    form.submit();
                });
                jQuery("#rejectButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "trialValidationreject.action";
                    form.submit();
                });
                jQuery("#onholdButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "onhold.action";
                    // We are essentially doing a redirect to a different Action here using Form POST submit.
                    // Since Trial Validation form here is being submitted to a different Action, this generates
                    // a bunch of harmless error messages in the log, which are, however, bothering the QA: https://tracker.nci.nih.gov/browse/PO-4815
                    // We'll effectively disable all form controls, except struts token, here to avoid those errors.
                    try {                    
                    	$(form).getElements().each(function (el) {
                    		if (!((el.name.indexOf("token") == 0) || (el.name.indexOf("struts.token") == 0))) 
                    			el.disable();
                    	});
                    } catch(err) {
                    	// oops.
                    }
                    form.submit();
                });

                jQuery("#programCodeIds").select2({
                    templateResult : function(pg){
                        return pg.title;
                    },
                    width: '200px'
                });


                jQuery(".select2-hidden-accessible").on("select2:unselect",function (e) {
                    ts = e.timeStamp;
                }).on("select2:opening", function (e) {
                    if (e.timeStamp - ts < 100) {
                        e.preventDefault();
                    }
                });

            });
        
            function setorgid(orgIdentifier, name) {
                orgid = orgIdentifier;
                orgname = name;
            }
            
            function setpersid(persIdentifier,name,email,phone) {
                persid = persIdentifier;
                selectedName = name;
                contactMail = email;
                contactPhone = phone;
            }
            
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn"); 
            }
            
            function loadSummary4SponsorDiv() {
                var url = 'ajaxTrialValidationdisplaySummary4FundingSponsor.action';
                var params = { orgId: orgid };
                var div = $('loadSummary4FundingSponsorField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Data Table 4 Sponsor...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function deleteSummary4SponsorRow(rowid) {
                var  url = '/pa/protected/ajaxTrialValidationdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadSummary4FundingSponsorField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
        
            function loadDiv(orgid) {
            }
            
            function loadPersDiv(persid, func) {
            }
            
            function lookup4loadresponsibleparty() {
                var orgid = document.getElementById('sponsorIdentifier').value;
                showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
            }
            
            function lookup4loadresponsiblepartygenericcontact() {
                var orgid = document.getElementById('sponsorIdentifier').value;
                showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid,  createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
            }
        
            // Other Identifiers handling code.
            Event.observe(window, 'load', initializeOtherIdentifiersSection);            
            
            function initializeOtherIdentifiersSection() {
                 var  url = 'ajaxManageOtherIdentifiersActionquery.action';
                 var params = {};
                 var div = $('otherIdentifierdiv');
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                 var aj = callAjaxPost(div, url, params, {evalScripts:true});  
            }

            function deleteOtherIdentifierRow(rowid){ 
                var  url = 'ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params, {evalScripts:true});
            }            
            
            function saveIdentifierRow(rowid){
                var orgValue = $("identifier_"+rowid).value;                
                if (orgValue != null && orgValue != '') {
                    var  url = 'ajaxManageOtherIdentifiersActionsaveOtherIdentifierRow.action';
                    var params = { uuid: rowid, otherIdentifier : orgValue };
                    var div = $('otherIdentifierdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Saving...</div>';
                    var aj = callAjaxPost(div, url, params, {evalScripts:true}); 
                } else {
                    alert("Please enter a valid Other Identifier.");
                }
            }            
            
             function editIdentifierRow(rowid){
                jQuery("#identifierDiv_"+rowid).hide();
                jQuery("#identifierInputDiv_"+rowid).show();
                jQuery("#actionEdit_"+rowid).hide();
                jQuery("#actionSave_"+rowid).show();                                
             }
             
             function addOtherIdentifier() {
                var orgValue = $("otherIdentifierOrg").value;
                var otherIdentifierTypeValue = $("otherIdentifierType").value;
                if (orgValue != null && orgValue != '') {
                    var  url = 'ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue, otherIdentifierType: otherIdentifierTypeValue };  
                                      
                    var div = $('otherIdentifierdiv');   
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params, {evalScripts:true});
                    $("otherIdentifierOrg").value="";
                } else {
                    alert("Please enter a valid Other Identifier.");
                }
            }



        
        </script>
        <style type="text/css">
            li.select2-results__option {
                text-overflow: ellipsis;
                overflow: hidden;
                max-width: 35em;
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
        <c:set var="topic" scope="request" value="validatetrial"/>
        <h1><fmt:message key="trialValidation.page.title" /></h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box" >
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form>
                <s:token/>
                <pa:studyUniqueToken/>
                <s:actionerror/> 
                <s:hidden name="gtdDTO.submissionNumber" id="gtdDTO.submissionNumber"/>
                <s:hidden name="gtdDTO.studyProtocolId" id="gtdDTO.studyProtocolId"/>
                <s:hidden name="gtdDTO.nonOtherIdentifiers.extension" id="gtdDTO.nonOtherIdentifiers.extension"/>
                <s:hidden name="gtdDTO.nonOtherIdentifiers.root" id="gtdDTO.nonOtherIdentifiers.root"/>
                <s:hidden name="gtdDTO.nonOtherIdentifiers.identifierName" id="gtdDTO.nonOtherIdentifiers.identifierName"/>
                <s:hidden name="gtdDTO.keywordText" id ="gtdDTO.keywordText"/>
                <h2><fmt:message key="trialValidation.trialDetails" /></h2>
                <table class="form">
                    <pa:valueRow labelKey="studyProtocol.nciIdentifier">
                        <c:out value="${sessionScope.trialSummary.nciIdentifier }"/>
                    </pa:valueRow>
                    <c:choose>
                        <c:when test="${!sessionScope.trialSummary.proprietaryTrial}">
                            <tr>
                                <td scope="row" class="label">
                                    <a href="http://ClinicalTrials.gov" target="_blank"><fmt:message key="trialValidation.ctgov" /></a> <fmt:message key="trialValidation.xmlRequired" />
                                </td>
                                <td>
                                    <s:radio name="gtdDTO.ctGovXmlRequired" id="gtdDTO.ctGovXmlRequired" list="#{true:'Yes', false:'No'}" onclick="toggledisplayDivs(this);"/>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <s:hidden name="gtdDTO.ctGovXmlRequired" id="gtdDTO.ctGovXmlRequired"/>
                        </c:otherwise>
                    </c:choose>
                    <tr><td>&nbsp;</td></tr>
                    <pa:titleRow titleKey="trialValidation.otherIdentifiers"/>
                    <pa:valueRow labelKey="studyProtocol.otherId">
	                    <c:remove var="otherIdentifiersEditable"/>
	                    <pa:displayWhenCheckedOut>
	                        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Submitted' || sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Amendment Submitted' }">                            
	                            <table>
	                                        <tr>                                
	                                            <td><s:select id="otherIdentifierType" name="otherIdentifierType" 
	                                                  list="#{}"  cssStyle="margin-top: 0px;" /></td>
	                                            <td>
	                                            <label for="otherIdentifierOrg" style="display:none">orgid</label>
	                                            <input type="text" name="otherIdentifierOrg"
	                                                id="otherIdentifierOrg" value="" />&nbsp;</td>
	                                            <td><input type="button" id="otherIdbtnid"
	                                                value="Add Other Identifier" onclick="addOtherIdentifier();" />
	                                            </td>
	                                        </tr>
	                            </table>
	                            <c:set var="otherIdentifiersEditable" value="${true}" scope="session"/>
	                       </c:if>
	                    </pa:displayWhenCheckedOut>
                </pa:valueRow>
                  <tr>
                      <td colspan="2" class="space">
                           <div id="otherIdentifierdiv"></div>
                      </td>
                  </tr>
                    
                    
                    <pa:valueRow labelKey="studyProtocol.proprietaryTrial">
                        <s:property value="gtdDTO.proprietarytrialindicator"/>
                    </pa:valueRow>
                    <pa:valueRow labelFor="officialTitle" labelKey="studyProtocol.officialTitle" required="true">
                        <s:textarea id="officialTitle" name="gtdDTO.officialTitle" cssStyle="width:606px" rows="4"/>
                        <pa:fieldError fieldName="gtdDTO.OfficialTitle"/>
                    </pa:valueRow>
                    <%@ include file="/WEB-INF/jsp/nodecorate/phaseAndPurpose.jsp" %>
                    
                    
                    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>  
                    <pa:titleRow titleKey="trialValidation.summary4Info"/>  
                    <pa:valueRow labelFor="summaryFourFundingCategoryCode" labelKey="studyProtocol.summaryFourFundingCategoryCode">
                        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                        <s:select id="summaryFourFundingCategoryCode" name="gtdDTO.summaryFourFundingCategoryCode" headerKey="" headerValue="" 
                                list="#summaryFourFundingCategoryCodeValues" value="gtdDTO.summaryFourFundingCategoryCode" 
                                cssStyle="width:206px" />
                    </pa:valueRow>
                    <pa:valueRow labelKey="studyProtocol.summaryFourFundingSource" labelFor="gtdDTO.summaryFourOrgName">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/displaySummary4FundingSponsor.jsp" %>
                        </div> 
                    </pa:valueRow>
                    <s:if test="cancerTrial">
                        <pa:valueRow labelFor="programCodeIds" labelKey="studyProtocol.summaryFourPrgCode">
                            <s:select id="programCodeIds" name="programCodeIds"
                                      value="programCodeIds"
                                      list="programCodeList"
                                      listKey="id"
                                      listValue="programCode"
                                      listTitle="displayName"
                                      multiple="true" />
                            <pa:fieldError fieldName="programCodeIds"/>
                        </pa:valueRow>
                    </s:if>

                    <s:if test="gtdDTO.submissionNumber > 1">
                        <pa:titleRow titleKey="trialValidation.amendmentInfo"/>
                        <pa:valueRow labelFor="amendmentReasonCode" labelKey="studyProtocol.amendmentReasonCodeValues" required="true">
                            <s:set name="amendmentReasonCodeValues" value="@gov.nih.nci.pa.enums.AmendmentReasonCode@getDisplayNames()" />
                            <s:select id="amendmentReasonCode" name="gtdDTO.amendmentReasonCode" headerKey="" headerValue="" 
                                      list="#amendmentReasonCodeValues" value="gtdDTO.amendmentReasonCode" 
                                      cssStyle="width:206px" />
                            <pa:fieldError fieldName="gtdDTO.amendmentReasonCode"/>          
                        </pa:valueRow>
                    </s:if>    
                </table>  
                <pa:buttonBar>
                    <pa:displayWhenCheckedOut>
                        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Submitted' || sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Amendment Submitted' }">
                            <pa:button id="saveButton" imgClass="save" labelKey="trialValidation.button.save"/>
                            <pa:button id="acceptButton" imgClass="confirm" labelKey="trialValidation.button.accept"/>
                            <pa:button id="rejectButton" imgClass="cancel"  labelKey="trialValidation.button.reject"/>
                            <pa:button id="onholdButton" imgClass="history"  labelKey="trialValidation.button.onhold"/>
                        </c:if>
                        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'On-Hold'}">
                            <pa:button id="onholdButton" imgClass="save"  labelKey="trialValidation.button.offhold"/>
                        </c:if>
                    </pa:displayWhenCheckedOut>  
                </pa:buttonBar>   
             </s:form>
         </div>
     </body>
 </html>