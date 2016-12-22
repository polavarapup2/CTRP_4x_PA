<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="updateOrAmendMode" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="update.trial.page.title"/></title>
            <link href="<c:url value='/styles/jquery-datatables/css/jquery.dataTables.min.css'/>" rel="stylesheet" type="text/css" media="all" />    
            <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
            <script type="text/javascript" src="${scriptPath}/js/submitTrial.js"></script>        
        <s:head/>
        <c:url value="/protected/updateTrial" var="backendUrlTemplate"/>          
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocolUrl"/>
        <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
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

            function reviewProtocolUpdate() {
            	submitFirstForm("save", "updateTrialreviewUpdate.action");
                showPopWin('${reviewProtocolUrl}', 600, 200, '', 'Review Register Trial');
            }
            
            function cancelProtocol(){
                submitFirstForm("Submit", "updateTrialcancel.action");
            }
            
            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function addGrant() {
                var fundingMechanismCode = $('fundingMechanismCode').value;
                var nihInstitutionCode = $('nihInstitutionCode').value;
                var nciDivisionProgramCode = $('nciDivisionProgramCode').value;
                var serialNumber = trim($('serialNumber').value);
                serialNumber = trim(serialNumber);
                var fundingPercent =  $('fundingPercent').value;
                var isValidGrant;
                var isSerialEmpty = false;
                var alertMessage = "";
                if (fundingMechanismCode.length == 0 || fundingMechanismCode == null) {
                    isValidGrant = false;
                    alertMessage = "Please select a Funding Mechanism";
                }
                if (nihInstitutionCode.length == 0 || nihInstitutionCode == null) {
                    isValidGrant = false;
                    alertMessage += "\nPlease select an Institute Code";
                }
                if (serialNumber.length == 0 || serialNumber == null) {
                    isValidGrant = false;
                    isSerialEmpty = true;
                    alertMessage += "\nPlease enter a Serial Number";
                }
                if (nciDivisionProgramCode.length == 0 || nciDivisionProgramCode == null) {
                    isValidGrant = false;
                    alertMessage += "\nPlease select a NCI Division/Program Code";
                }
                if (isSerialEmpty == false && isNaN(serialNumber)) {
                    isValidGrant = false;
                    alertMessage += "\nSerial Number must be numeric";
                } else if (isSerialEmpty == false && serialNumber != null) {
                           var numericExpression = /^[0-9]+$/;
                           if (!numericExpression.test(serialNumber)) {
                               isValidGrant = false;
                               alertMessage += "\nSerial Number must be numeric";
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
                var  url = '/registry/protected/ajaxManageGrantsActionaddGrantForUpdate.action';
                var params = {
                    fundingMechanismCode: fundingMechanismCode,
                    nciDivisionProgramCode: nciDivisionProgramCode,
                    nihInstitutionCode: nihInstitutionCode,
                    serialNumber: serialNumber,
                    fundingPercent: fundingPercent
                };
                var div = $('grantadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetGrantRow();
            }
            
            function deleteGrantRow(rowid) {
                var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrantForUpdate.action';
                var params = { uuid: rowid };
                var div = $('grantadddiv');
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

            function addOtherIdentifier() {
                var orgValue = trim($("otherIdentifierOrg").value);
                if (orgValue != null && orgValue != '') {
                    var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue };
                    var div = $('otherIdentifierdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("otherIdentifierOrg").value="";
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
            
            function addNCTIdentifier() {
                var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddNCTIdentifier.action';
                var params = {
                		nctIdentifier: $("nctId").value
                };
                var div = $('nctIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
        
            document.observe("dom:loaded", function() {
             displayTrialStatusDefinition('trialDTO_statusCode');
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
            	  
          });
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
        <h1 class="heading"><span><fmt:message key="update.trial.page.header"/></span></h1>
        <c:set var="topic" scope="request" value="updatetrial"/>
        
            <reg-web:failureMessage/>
            <div id="general_trial_errors"></div>
            <s:form name="updateTrial" id="updateTrial" method="POST" validate="false" enctype="multipart/form-data" cssClass="form-horizontal" role="form">
                <s:token/>
                <s:if test="hasActionErrors()">
                    <div class="alert alert-danger"><s:actionerror/></div>
                </s:if>
                <s:else>
                    <s:actionerror/>
                </s:else>
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.xmlRequired" id="trialDTO.xmlRequired" />
                <s:hidden name="trialDTO.nciGrant" id="trialDTO.nciGrant" />
                <s:hidden name="page" />
                <s:hidden name="uuidhidden"/>
                <p>Use this form to update trial information. You can not change the information in certain fields, including the trial title.</p>
                <button type="button" class="expandcollapse btn btn-icon btn-sm btn-default" state="0"><i class="fa-minus-circle"></i> Collapse All</button>
                <div class="accordion-group">
                <%@ include file="/WEB-INF/jsp/nodecorate/updateIdentifiersSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/trialOtherIdsSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateDetailsSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateLeadOrganizationSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateSponsorResponsiblePartySection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateSummary4InfoSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateGrantsSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>  
                <%@ include file="/WEB-INF/jsp/nodecorate/updateIdeIndIndicatorSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateRegulatoryInformationSection.jsp" %>
                <%@ include file="/WEB-INF/jsp/nodecorate/updateParticipatingSitesSection.jsp" %>      
                <%@ include file="/WEB-INF/jsp/nodecorate/updateCollaboratorsSection.jsp" %>   
                <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialDocumentsSection.jsp" %>
                              
                <div id="uploadDocDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                </div>
                <br/>
                <p align="center" class="info">
                    <b>Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.</b>
                </p>                
                <div class="align-center button-row">
			      <button type="button" class="btn btn-icon btn-primary review" onclick="reviewProtocolUpdate()"><i class="fa-floppy-o"></i>Review Trial</button>
			      <button type="button" class="btn btn-icon btn-default" onclick="cancelProtocol()"><i class="fa-times-circle"></i>Cancel</button>
		    	</div>
		    	  <select id="tempProgramCodeValues" multiple="true" size ="2" style="display:none" >
                     <c:forEach items="${trialDTO.programCodesList}" var="element"> 
                    <option value="${element}" selected >${element}</option>     
                    </c:forEach>
                    </select>  
                    </s:form>
        <div id="general_trial_errors_container" style="display: none;">
            <span class="info">
                The following errors may not be related to the update you are trying to submit, but rather may indicate a general problem
                with the abstraction of the trial that is preventing your update from submitting properly. If so, please
                communicate these error messages to the CTRO office, so that the abstraction could be fixed and the update could proceed. 
            </span>
            <br/> <br/>
            <c:forEach items="${flattenedRemainingFieldErrors}" var="error">
	           <div class="alert alert-danger">	            
	               <strong>Trial Abstraction Error:</strong> <c:out value="${error}"></c:out>	            
	           </div>
	        </c:forEach>
        </div>
        <c:if test="${not empty flattenedRemainingFieldErrors}">
        <script type="text/javascript">
           Event.observe(window, "load", function() {
        	   $('general_trial_errors').innerHTML = $('general_trial_errors_container').innerHTML;
           });
        </script>
        </c:if>
    </body>
</html>