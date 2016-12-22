<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="studyProtocol.search.title"/></title>
        <s:head/>
        <style type="text/css">
            span.select2-container {
                max-width: 280px; 
            }
            div.exportlinks {
                 text-align: right;
                 padding-right: 5px;
            }
            li.select2-selection__choice {
                white-space: normal;
            }
            
        </style>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>        
        <script type="text/javascript" language="javascript">
            function handleAction() {
                 document.forms[0].action="studyProtocolquery.action";
                 document.forms[0].submit();
            }
            function cancelAction() {
                 document.forms[0].action="trialAssociationsquery.action";
                 document.forms[0].submit();  
            }
            function generateReport(pid) {
                showPopup('/pa/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId=' + pid, null, 'View Trial Summary Report');
            }
            function resetValues() {
            	// This will reset all Select2 backed boxes.
            	jQuery(".select2-hidden-accessible").val(null).trigger("change");
            	
                $("officialTitle").value="";                
                $("identifierType").value="All";
                $("identifier").value="";               
                $("holdStatus").value="";                
                $("studyLockedBy").checked=false;
                $("trialCategory").value="";
                $("ctepDcpCategory").value="";
                $("ctgovXmlRequiredIndicator").value="";
                $("studyProtocolType").value = "";
                $("InterventionalStudyProtocol_studySubtypeCode").value = "";
                $("NonInterventionalStudyProtocol_studySubtypeCode").value = "";                                
                studyProtocolTypeChanged();
                
            }
    
            document.onkeypress = runEnterScript;
            function runEnterScript(e) {
                var KeyID = (window.event) ? event.keyCode : e.keyCode;
                if (KeyID == 13) {
                    handleAction();
                    return false;
                }
            }
            
            function studyProtocolTypeChanged() {
            	var studyType = $F('studyProtocolType');
            	if (studyType == 'NonInterventionalStudyProtocol') {
            		$("InterventionalStudyProtocol_studySubtypeCode").disable();
            		$("InterventionalStudyProtocol_studySubtypeCode").hide();
                    $("NonInterventionalStudyProtocol_studySubtypeCode").enable();
                    $("NonInterventionalStudyProtocol_studySubtypeCode").show();            		
            	} else {
            		$("NonInterventionalStudyProtocol_studySubtypeCode").disable();
                    $("NonInterventionalStudyProtocol_studySubtypeCode").hide();
                    $("InterventionalStudyProtocol_studySubtypeCode").enable();
                    $("InterventionalStudyProtocol_studySubtypeCode").show();              		
            	}
            }
            
            Event.observe(window, "load", studyProtocolTypeChanged);
            
            (function($) {    
                $(function () {
                	// Init Select2 boxes.
                    $("#leadOrganizationId, #principalInvestigatorId, #primaryPurpose, #phaseCode, #studyStatusCode, #documentWorkflowStatusCode, #studyMilestone, #submissionType, #studySourceType").select2({
                    	  placeholder: "All"
                   	});
                   
                	
                    // Prevent opening of the Select2 box upon unselect.
                    var ts = 0;
                    $(".select2-hidden-accessible").on("select2:unselect", function (e) { 
                        ts = e.timeStamp;
                    }).on("select2:opening", function (e) { 
                        if (e.timeStamp - ts < 100) {                   
                            e.preventDefault();
                        }
                    });                	
                })
            }(jQuery));
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="studyProtocol.search.header"/></h1>
        <c:set var="topic" scope="request" value="searchtrial"/>
        <s:if test="records.size > 0">
            <div class="filter_checkbox"><input type="checkbox" name="checkbox" id="filtercheckbox" onclick="toggledisplay('filters', this)" /><label for="filtercheckbox">Hide Search Fields</label></div>
        </s:if>
        <div class="box" id="filters">
            <s:form>
            <c:if test="${empty isBare}">  
                <s:token/>
            </c:if>
                <pa:failureMessage/>
                <c:if test="${isBare}">
	                <p align="center" class="info">
	                   Use this window to search for the trial you need. Once found, select the trial by clicking on its NCI ID hyperlink.
	                </p>                    
                </c:if>
                <table class="form">
                    <s:set name="protocolOrgs" value="@gov.nih.nci.pa.util.PaRegistry@getCachingPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol('Lead Organization')" />
                    <tr>
                        <td  scope="row" class="label">
                            <label for="officialTitle"> <fmt:message key="studyProtocol.officialTitle"/></label>
                        </td>
                        <td>
                            <s:textfield id="officialTitle" name="criteria.officialTitle" maxlength="200" size="100" cssStyle="width:200px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="leadOrganizationId"> <fmt:message key="studyProtocol.leadOrganization"/></label>
                        </td>
                        <td>
                            <s:select size="2" multiple="true" name="criteria.leadOrganizationIds" id="leadOrganizationId" 
                                list="#protocolOrgs" listKey="id"
                                listValue="name" value="criteria.leadOrganizationIdsStrings" />
                        </td>
                    </tr>
                    <s:set name="identifierSearchTypes" value="@gov.nih.nci.pa.enums.IdentifierType@getDisplayNames()" />
                    <tr>
                        <td scope="row" class="label">
                            <label for="identifierType"><fmt:message key="studyProtocol.identifierType"/></label>
                        </td>
                        <td>
                            <s:select id="identifierType" headerKey="All" 
                                headerValue="All" name="criteria.identifierType"
                                list="#identifierSearchTypes" value="criteria.identifierType"  cssStyle="width:206px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>criteria.identifierType</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                        <td scope="row" class="label">
                            <label for="identifier"><fmt:message key="studyProtocol.identifier"/></label>
                            <br><span class="info">(e.g: NCI-2008-00015; ECOG-1234, etc)</span>
                        </td>
                        <td>
                            <s:textfield id="identifier" name="identifier" maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>identifier</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <s:set name="principalInvs" value="@gov.nih.nci.pa.util.PaRegistry@getCachingPAPersonService().getAllPrincipalInvestigators()" />
                        <td  scope="row" class="label">
                            <label for="principalInvestigatorId"> <fmt:message key="studyProtocol.principalInvestigator"/></label>
                        </td>
                        <td align=left>
                            <s:select size="2" multiple="true"
                                name="criteria.principalInvestigatorIds"
                                id="principalInvestigatorId"
                                list="#principalInvs"
                                listKey="id"
                                listValue="fullName"                               
                                value="criteria.principalInvestigatorIds" />
                        </td>
                        <td  scope="row" class="label">
                            <label for="primaryPurpose"> <fmt:message key="studyProtocol.trialType"/></label>
                        </td>
                        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames()" />
                        <td>
                            <s:select size="2" multiple="true"  id="primaryPurpose"  name="criteria.primaryPurposeCodes" list="#primaryPurposeCodeValues"
                                      value="criteria.primaryPurposeCodes" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="phaseCode"> <fmt:message key="studyProtocol.studyPhase"/></label>
                        </td>
                        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                        <td>
                            <s:select size="2" multiple="true"  id="phaseCode"  name="criteria.phaseCodes" list="#phaseCodeValues"  value="criteria.phaseCodes" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="studyStatusCode"> <fmt:message key="studyProtocol.studyStatus"/></label>
                        </td>
                        <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
                        <td>
                           <s:select size="2" multiple="true"  id="studyStatusCode" name="criteria.studyStatusCodeList" list="#studyStatusCodeValues"  value="criteria.studyStatusCodeList" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="documentWorkflowStatusCode"> <fmt:message key="studyProtocol.documentWorkflowStatus"/></label>
                        </td>
                        <s:set name="documentWorkflowStatusCodeValues" value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
                        <td>
                           <s:select size="2" multiple="true" id="documentWorkflowStatusCode" name="criteria.documentWorkflowStatusCodes" list="#documentWorkflowStatusCodeValues"  value="criteria.documentWorkflowStatusCodes" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="studyMilestone"> <fmt:message key="studyProtocol.milestone"/></label>
                        </td>
                        <s:set name="milestoneCodes" value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
                        <td>
                           <s:select size="2" multiple="true" id="studyMilestone" name="criteria.studyMilestone" list="#milestoneCodes"  value="criteria.studyMilestone" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="holdStatus"> <fmt:message key="studyProtocol.searchOnHold"/></label>
                        </td>
                        <td>
                            <s:select headerKey="" headerValue="All" id="holdStatus" name="criteria.holdStatus" 
                                list="#{'onhold':'On-Hold','notonhold':'Not On-Hold'}"  value="criteria.holdStatus" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="studyLockedBy"> <fmt:message key="studyProtocol.searchOnCheckout"/></label>
                        </td>
                        <td>
                            <s:checkbox id="studyLockedBy" name="criteria.studyLockedBy" />
                            <input type="hidden" id="studyLockedBy" value="false">
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="submissionType"> <fmt:message key="studyProtocol.submissionTypeSearch"/></label>
                        </td>
                        <s:set name="submissionTypeValues" value="@gov.nih.nci.pa.enums.SubmissionTypeCode@getDisplayNames()" />
                        <td>
                           <s:select size="2" multiple="true" id="submissionType" name="criteria.trialSubmissionTypesAsString" list="#submissionTypeValues"  value="criteria.trialSubmissionTypesAsString" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="trialCategory"> <fmt:message key="studyProtocol.trialCategorySearch"/></label>
                        </td>
                        <td>
                           <s:select headerKey="" headerValue="All" id="trialCategory" name="criteria.trialCategory" list="#{'p':'Abbreviated','n':'Complete'}"  value="criteria.trialCategory" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="studyProtocolType"><fmt:message key="studyProtocol.trialtype"/></label>
                        </td>                        
                        <td>
                           <s:select headerKey="" headerValue="All" id="studyProtocolType" name="criteria.studyProtocolType" 
                                list="#{'InterventionalStudyProtocol':'Interventional','NonInterventionalStudyProtocol':'Non-interventional'}"  
                                    onchange="studyProtocolTypeChanged();"
                                    value="criteria.studyProtocolType" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="InterventionalStudyProtocol_studySubtypeCode"><fmt:message key="studyProtocol.trialSubType"/></label>
                            <label for="NonInterventionalStudyProtocol_studySubtypeCode" style="display:none"><fmt:message key="studyProtocol.trialSubType"/></label>
                        </td>
                        <td>
                           <s:set name="studySubtypeCodeValues" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />
                           <s:select headerKey="" headerValue="All" id="InterventionalStudyProtocol_studySubtypeCode"
                                name="criteria.studySubtypeCode" list="#{}"  value="criteria.studySubtypeCode" cssStyle="width:206px" />
                           <s:select headerKey="" headerValue="All" id="NonInterventionalStudyProtocol_studySubtypeCode"
                                name="criteria.studySubtypeCode" list="#studySubtypeCodeValues"  value="criteria.studySubtypeCode" cssStyle="width:206px; display:none;" />                                
                        </td>
                    </tr>                    
                    <tr>
                    	<td scope="row" class="label">
                            <label for="ctgovXmlRequiredIndicator"> <fmt:message key="studyProtocol.ctgovXmlRequired"/></label>
                        </td>
                    	<td>                          
                           <s:select headerKey="" headerValue="All" id="ctgovXmlRequiredIndicator" 
                           name="criteria.ctgovXmlRequiredIndicator" list="#{'True':'Yes','False':'No'}"  
                           value="criteria.ctgovXmlRequiredIndicator" cssStyle="width:206px" />                         
                        </td>   
                        <td scope="row" class="label">
                            <label for="ctepDcpCategory"><fmt:message key="studyProtocol.ctepDcpCategory"/></label>
                        </td>
                        <td>
                            <s:select headerKey="" headerValue="All" id="ctepDcpCategory" name="criteria.ctepDcpCategory" 
                                list="#{'ctepdcp':'CTEP and DCP PIO Trials Only','ctep':'CTEP PIO Trials Only','dcp':'DCP PIO Trials Only'}"  
                                value="criteria.ctepDcpCategory" cssStyle="width:206px" />                        
                        </td>                                            
                    </tr>
                    <tr>
                    	<td scope="row" class="label">
                            <label for="studySource"> <fmt:message key="studyProtocol.studySourceType"/></label>
                        </td>
                    	<td>
                           <s:set name="studySourceCodeValues" value="@gov.nih.nci.pa.enums.StudySourceCode@getDisplayNames()" />                          
                           <s:select size="2" multiple="true" id="studySourceType" 
	                           name="criteria.studySource" list="#studySourceCodeValues"  
	                           cssStyle="width:206px" value="criteria.studySource"/>                         
                        </td>   
                        <td scope="row" class="label">
                        </td>
                        <td>
                        </td>                                            
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                               
                                <s:if test="%{pageFrom == 'associate'}">
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                                <span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                                </s:if>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>        
        <div class="line"></div>
        <jsp:include page="/WEB-INF/jsp/studyProtocolQueryResults.jsp"/>
         <c:if test="${studyAlternateTitlesPresent}">
             <div class="label">(*) <fmt:message key="studyAlternateTitles.text"/></div>  
         </c:if>
    </body>
</html>
