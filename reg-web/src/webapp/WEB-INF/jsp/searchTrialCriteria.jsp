<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title><fmt:message key="search.trial.page.title"/></title>
    <s:head/>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
</head>
<style type="text/css">
 /*This style is to restrict the size of the popup and add scroll for more items*/
 .ui-autocomplete { height: 150px; overflow-y: scroll; overflow-x: hidden;}
</style>
<SCRIPT LANGUAGE="JavaScript">
    window.onload=displayOrg;
    
    function resetValues(){
        document.searchTrial.reset();
        document.getElementById("officialTitle").value="";
        document.getElementById("phaseCode").value="";
        document.getElementById("typeCodeValues").value="";
        document.getElementById("identifierType").value="All";
        document.getElementById("identifier").value="";
        document.getElementById("organizationType").value="";
        document.getElementById("typeCodeValues").value="";
        document.getElementById("organizationId").value="";
        document.getElementById("participatingSiteId").value="";
        document.getElementById("leadAndParticipatingOrgId").value="";
        document.getElementById("organizationName").value="";
        document.getElementById("participatingSiteName").value="";
        document.getElementById("leadAndParticipatingOrgName").value="";
        document.getElementById("phaseAdditionalQualifierCode").value="";
        document.getElementById("principalInvestigatorId").value="";
        document.getElementById("trialCategory").value=""; 
    }
    function handleAction(){
         var input = "criteria.myTrialsOnly";
         document.forms[0].elements[input].value="false";
         document.forms[0].action="searchTrialquery.action";
         document.forms[0].submit();
    }
    function viewXml(pId){
         document.forms[0].target = "XML Generation";
         document.forms[0].action="searchTrialviewXML.action?studyProtocolId="+pId;
         document.forms[0].submit();
    }
    
    function viewTsr(pId){
    	displayWaitPanel();
    	
    	 var ajaxReq = new Ajax.Request('searchTrialgetAbstractionErrors.action', {
    	        method: 'post',
    	        parameters: 'studyProtocolId='+pId,
    	        onSuccess: function(transport) {
    	        	hideWaitPanel();   
    	        	var absErrs = transport.responseText;
    	        	if (absErrs.empty()) {
    	        		 document.forms[0].action="searchTrialviewTSR.action?studyProtocolId="+pId;
    	                 document.forms[0].submit();    	        		
    	        	} else {
    	        		$('tsrAbsErrorList').innerHTML = absErrs;
    	        		jQuery("#tsrDownloadWarningModal").modal('show'); 
    	        		jQuery('#viewTsrAnywayBtn').click(function () {  
    	        			jQuery("#tsrDownloadWarningModal").modal('hide'); 
    	        			document.forms[0].action="searchTrialviewTSR.action?studyProtocolId="+pId;
                            document.forms[0].submit();       	        			
    	        		});    	        		
    	        	}
    	        },
    	        onFailure: function(transport) {
    	        	hideWaitPanel();  
    	            alert('Error when communicating with the server.');
    	            document.forms[0].action="searchTrialviewTSR.action?studyProtocolId="+pId;
    	            document.forms[0].submit();
    	        },
    	        onException: function(requesterObj, exceptionObj) {
    	            ajaxReq.options.onFailure(null);
    	        },
    	        on0: function(transport) {
    	            ajaxReq.options.onFailure(transport);
    	        }
    	    });
    	      
   }
    
    function saveDiseaseCode(pId, diseaseCode) {
        if (confirm("<fmt:message key='accrual.disease.code.save.Confirm' />")) {
        document.forms[0].action="searchTrialsaveAccrualDiseaseCode.action?studyProtocolId="+pId+"&accrualDiseaseTerminology="+diseaseCode;
        document.forms[0].submit();
        }
    }
    
    function addMySite(pId) {
    	alert('Not yet implemented.');
    }
    
    function editMySite(pId) {
    	alert('Not yet implemented.');
    }    

    function handleMyAction(){
        var input = "criteria.myTrialsOnly";
        document.forms[0].elements[input].value="true";
        document.forms[0].action="searchTrialquery.action";
        document.forms[0].submit();
    }
    function getMyPartialTrial() {
        var input = "criteria.myTrialsOnly";
        document.forms[0].elements[input].value="true";
        document.forms[0].action="searchTrialgetMyPartiallySavedTrial.action";
        document.forms[0].submit();
    }
    function viewProtocol(pId) {
        document.forms[0].action="searchTrialview.action?studyProtocolId="+pId;
        document.forms[0].submit();
    }
    function displayOrg(){
        var input="criteria.organizationType";
        var inputElement = document.forms[0].elements[input];
        if (inputElement.options[inputElement.selectedIndex].value == "Both") {
        	hideLeadOrganization();
        	hideParticipatingSite();
        	showLeadOrParticipatingSite();
        } else if (inputElement.options[inputElement.selectedIndex].value == "Participating Site") {
        	hideLeadOrganization();
        	showParticipatingSite();
        	hideLeadOrParticipatingSite();
        } else {
        	showLeadOrganization();
        	hideParticipatingSite();
        	hideLeadOrParticipatingSite();
        } 
    }
    function hideParticipatingSite() {
    	document.getElementById("Site").style.display = "none";
    	document.getElementById("participatingSiteId").value = "";
    	document.getElementById("participatingSiteName").value = "";
    }
    function hideLeadOrParticipatingSite() {
    	document.getElementById("LeadOrSite").style.display = "none";
    	document.getElementById("leadAndParticipatingOrgId").value = "";
    	document.getElementById("leadAndParticipatingOrgName").value = "";
    }
    function hideLeadOrganization() {
    	document.getElementById("Lead").style.display = "none";
    	document.getElementById("organizationId").value = "";
    	document.getElementById("organizationName").value = "";
    }
    function showParticipatingSite() {
    	document.getElementById("Site").style.display = "";
    }
    function showLeadOrParticipatingSite() {
    	document.getElementById("LeadOrSite").style.display = "";
    }
    function showLeadOrganization() {
    	document.getElementById("Lead").style.display = "";
    }
    function viewPartialProtocol(pId,user) {
        document.forms[0].action="searchTrialpartiallySubmittedView.action?studyProtocolId="+pId;
        document.forms[0].submit();
    }
   function deletePartialProtocol() {
       return confirm("Do you want to delete?");
   }     
   
   var nameIdMap = {"principalInvestigatorName":"principalInvestigatorId","organizationName":"organizationId", "participatingSiteName":"participatingSiteId", "leadAndParticipatingOrgName":"leadAndParticipatingOrgId"};
   
   jQuery(function() {
     	
         jQuery(".orgautocomplete").autocomplete({delay: 250, minLength:3,
               source: function(req, responseFn) {
                 var orgType = jQuery("#organizationType").val();
                 if('' == orgType) {
                	 return [];
                 }
                 
                 var url = registryApp.contextPath + '/ctro/json/ajaxOrganizationsgetOrganizationsWithTypeAndNameAssociatedWithStudyProtocol.action?organizationType='+orgType+'&organizationTerm=' + req.term;
                 jQuery.getJSON(url,null,function(data){
                        responseFn(jQuery.map(data.organizationDtos, function (value, key) { 
                             return {
                                 label: key,
                                 value: value
                             };
                        }));
                 });
             },
             select: function(event, ui) {
					// prevent autocomplete from updating the textbox
					event.preventDefault();
					// set the label as value in the textbox
					// and set the value/key as value in the hidden field
					event.target.value=ui.item.label;
					var id = event.target.id;
					id = nameIdMap[id];
					document.getElementById(id).value=ui.item.value;
				}
         });
         
         jQuery(".personautocomplete").autocomplete({delay: 250, minLength:3,
             source: function(req, responseFn) {
               
               var url = registryApp.contextPath + '/ctro/json/ajaxPersonsgetPrincipalInvestigatorsByNameAssociatedWithStudyProtocol.action?personTerm=' + req.term;
               jQuery.getJSON(url,null,function(data){
                      responseFn(jQuery.map(data.paPersonDTOs, function (value, key) { 
                           return {
                               label: key,
                               value: value
                           };
                      }));
               });
           },
           select: function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// set the label as value in the textbox
				// and set the value/key as value in the hidden field
				event.target.value=ui.item.label;
				var id = event.target.id;
				id = nameIdMap[id];
				document.getElementById(id).value=ui.item.value;
			}
       });
         
     });
   
</SCRIPT>
<body>
<div class="container">
  <ul class="nav nav-tabs">
    <li <s:if test="records == null">class="active"</s:if>><a href="#search-clinical-trials" data-toggle="tab"><i class="fa-flask"></i><fmt:message key="search.trial.page.header"/></a></li>
    <li><a href="<s:url action='personsSearch.action' />" ><i class="fa-user"></i><fmt:message key="person.search.header"/></a></li>
    <li><a href="<s:url action='organizationsSearch.action' />"><i class="fa-sitemap"></i><fmt:message key="organization.search.header"/></a></li>
    <s:if test="records != null">
    	<li class="active"><a href="#search-results" data-toggle="tab"><i class="fa-search"></i><fmt:message key="search.results"/></a></li>
    </s:if>
  </ul>
  
  <!-- main content begins-->
<div class="tab-content">
<s:if test="records == null">
 <div class="tab-pane fade active in" id="search-clinical-trials">
</s:if>
<s:else>
 <div class="tab-pane fade" id="search-clinical-trials">
</s:else>
    <!--  <a href="#search_results" id="navskip2">Skip Search Filters and go to Search Results</a> -->
    <c:set var="topic"  scope="request" value="searchtrials"/>    
    <s:form name="searchTrial" cssClass="form-horizontal" role="form">
   	<s:if test="hasActionErrors()">
		<div class="alert alert-danger">
			<s:actionerror />
		</div>
	</s:if>
	<reg-web:sucessMessage />
    <reg-web:failureMessage/>
    <p class="mb20"><fmt:message key="search.trial.instructions"/></p> 
        
    <s:hidden name="criteria.myTrialsOnly" id="criteria.myTrialsOnly"/>
    <div class="form-group">
		<label for="officialTitle" class="col-xs-2 control-label"> <fmt:message key="search.trial.title"/></label>
		<div class="col-xs-10">
        	<s:textfield id ="officialTitle" name="criteria.officialTitle" maxlength="400" cssClass="form-control" placeholder="Enter keywords" />
		</div>        	
    </div>     
    <div class="form-group">
		<label for="phaseCode"  class="col-xs-2 control-label"> <fmt:message key="search.trial.phase"/></label>
		<s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
		<div class="col-xs-4">
        	<s:select id="phaseCode" headerKey="" headerValue="--Select--" name="criteria.phaseCode" list="#phaseCodeValues"  value="criteria.phaseCode" cssClass="form-control" />
		</div>
        <label for="typeCodeValues" class="col-xs-2 control-label"> <fmt:message key="search.trial.purpose"/></label>
        <s:set name="typeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames()" />
		<div class="col-xs-4">
        	<s:select id="typeCodeValues" headerKey="" headerValue="--Select--" name="criteria.primaryPurposeCode" list="#typeCodeValues"  value="criteria.primaryPurposeCode" cssClass="form-control" />
        </div>
	</div>
    <div class="form-group">
    	<label for="phaseAdditionalQualifierCode" class="col-xs-2 control-label"><fmt:message key="search.trial.phaseAdditionalQualifier"/></label>
        <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
        <div class="col-xs-4">
        	<s:select id="phaseAdditionalQualifierCode" headerKey="" headerValue="--Select--" name="criteria.phaseAdditionalQualifierCode" list="#phaseAdditionlQualiefierCodeValues"
				value="criteria.phaseAdditionalQualifierCode" cssClass="form-control" />
		</div>
	</div>
	<div class="form-group">
    	<label for="identifierType" class="col-xs-2 control-label"> <fmt:message key="search.trial.identifierType"/></label>
        <div class="col-xs-4">       
             <s:select
                 id = "identifierType"
                 headerKey="All"
                 headerValue="--Select--"
                 name="criteria.identifierType"
                 list="#{'NCI':'NCI','NCT':'NCT (Exact Match)','Lead Organization':'Lead Organization','Other Identifier':'Other Identifier'}"
                 value="criteria.identifierType"
                 cssClass="form-control"
                 />
                 <span class="alert-danger">
                     <s:fielderror>
                     <s:param>criteria.identifierType</s:param>
                    </s:fielderror>
                 </span>
		</div>
        <label for="identifier" class="col-xs-2 control-label"> <fmt:message key="search.trial.identifier"/></label>
        <div class="col-xs-4">
	       	<s:textfield id="identifier" name="criteria.identifier"  maxlength="200" size="100"  cssClass="form-control" placeholder="Examples: NCI-2008-00015; ECOG-1234"/>
	             <span class="alert-danger">
	                 <s:fielderror>
	                 <s:param>criteria.identifier</s:param>
	                </s:fielderror>
	             </span>
        </div>
    </div>
    <div class="form-group">
		<label for="organizationType" class="col-xs-2 control-label">  <fmt:message key="search.trial.organizationType"/></label>
		<div class="col-xs-4">
        	<s:select id="organizationType" headerKey="" headerValue="--Select--" name="criteria.organizationType"  list="#{'Lead Organization':'Lead Organization','Participating Site':'Participating Site','Both':'Both'}" value="criteria.organizationType" cssClass="form-control" onchange="displayOrg()"/>
        	<em>Please select an organization type before selecting an organization</em>
        </div>
        <div id="Lead">
        <label for="organizationName" class="col-xs-2 control-label"> <fmt:message key="search.trial.organization"/></label>
        <s:hidden name="criteria.organizationId" id="organizationId"/>
		<div class="col-xs-4">                    
			<s:textfield id="organizationName" name="criteria.organizationName" cssClass="form-control orgautocomplete" placeholder="Enter keyword and select an organization from the list"/>
             <span class="alert-danger">
                 <s:fielderror>
                 <s:param>criteria.organizationId</s:param>
                </s:fielderror>
             </span>
        </div>
     </div>
     <div id="Site">
		<label for="participatingSiteName" class="col-xs-2 control-label"> <fmt:message key="search.trial.organization"/></label>
		<s:hidden name="criteria.participatingSiteId" id="participatingSiteId"/>
		<div class="col-xs-4">
			<s:textfield id="participatingSiteName" name="criteria.participatingSiteName" cssClass="form-control orgautocomplete" placeholder="Enter keyword and select an organization from the list"/>
            <span class="alert-danger">
                <s:fielderror>
                <s:param>criteria.organizationId</s:param>
               </s:fielderror>
            </span>
    	</div>
    </div>
    <div id="LeadOrSite">
        <label for="leadAndParticipatingOrgName" class="col-xs-2 control-label"> <fmt:message key="search.trial.organization"/></label>
        <s:hidden name="criteria.leadAndParticipatingOrgId" id="leadAndParticipatingOrgId"/>
        <div class="col-xs-4">                
			<s:textfield id="leadAndParticipatingOrgName" name="criteria.leadAndParticipatingOrgName" cssClass="form-control orgautocomplete" placeholder="Enter keyword and select an organization from the list"/>
            <span class="alert-danger">
                <s:fielderror>
                <s:param>criteria.organizationId</s:param>
               </s:fielderror>
            </span>
        </div>
	</div>
    </div>
   
	<div class="form-group">
		<%-- <s:set name="principalInvs" value="getAllPrincipalInvestigators()" /> --%>
        <label for="principalInvestigatorId" class="col-xs-2 control-label"> <fmt:message key="search.trial.principalInvestigator"/></label>
        <s:hidden name="criteria.principalInvestigatorId" id="principalInvestigatorId"/>
        <div class="col-xs-4">
            <s:textfield id="principalInvestigatorName" name="criteria.principalInvestigatorName" cssClass="form-control personautocomplete" placeholder="Enter keyword and select a PI from the list"/>
       </div>
       <label for="trialCategory" class="col-xs-2 control-label"> <fmt:message key="search.trial.trialCategorySearch"/></label>
       <div class="col-xs-4">        
	       <s:select headerKey="" headerValue="--Select--" id="trialCategory" name="criteria.trialCategory" 
	        list="#{'p':'Abbreviated','n':'Complete', 'b':'Both'}"  value="criteria.trialCategory" 
	           cssClass="form-control" />
		</div>
    </div>      
              <%-- Initially implemented, but then asked to remove as part of https://tracker.nci.nih.gov/browse/PO-4852.
                   Keeping the code in case we need to bring back in.
              <tr>
                <td scope="row" class="label">
                    <label for="holdStatus"> <fmt:message key="search.trial.searchOnHold"/></label>
                </td>
                <td>
                    <s:select headerKey="" headerValue="Both" id="holdStatus" name="criteria.holdStatus" 
                        list="#{'onhold':'On-Hold','notonhold':'Not On-Hold'}"  value="criteria.holdStatus" cssStyle="width:206px" />
                </td>
              </tr>
               --%>
                           
        </table>
        
        <div class="bottom">
            <div class="btn-group">
              <button id="runSearchBtn" type="button" class="btn btn-icon btn-primary dropdown-toggle" data-toggle="dropdown"> <i class="fa-search"></i>Search <span class="caret"></span> </button>
              <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:void(0)" onclick="handleMyAction()" id="searchMyTrialsBtn">My Trials<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Search the trials registered in the CTRP that I own"  data-placement="top" data-trigger="hover"></i></a></li>
                <li><a href="javascript:void(0)"  onclick="handleAction()" id="searchAllTrialsBtn">All Trials<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Search the trials registered in the CTRP that I own as well as those registered by others"  data-placement="top" data-trigger="hover"></i></a></li>
                <li><a href="javascript:void(0)" onclick="getMyPartialTrial()" id="searchSavedDraftsBtn">Saved Drafts<i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Search my saved drafts"  data-placement="top" data-trigger="hover"></i></a></li>
              </ul>
            </div>
            <button type="button" class="btn btn-icon btn-default" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/searchTrial.action');" id="resetSearchBtn"><i class="fa-repeat"></i>Reset</button>
          </div>
        
    </s:form>     
  </div>
 <s:if test="records != null">
	    <div class="tab-pane fade in active" id="search-results">
	        <div class="tab-inside">
		          <div class="mb20 control-bar">
			    		<s:if test="criteria.myTrialsOnly">
			      			<jsp:include page="/WEB-INF/jsp/searchMyTrialResults.jsp"/>
			    		</s:if>
			    		<s:else>
			    			<jsp:include page="/WEB-INF/jsp/searchTrialResults.jsp"/>
			    		</s:else>
				  </div>
				  <c:if test="${studyAlternateTitlesPresent}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">(*) <fmt:message key="studyAlternateTitles.text"/></label>
                    </div>
                 </c:if>				  
			</div>			
		</div>
	</s:if>
	</div>
</div>

<!-- TSR Abstraction Validation Warnings Modal -->
<div class="modal fade" id="tsrDownloadWarningModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">Warning</h4>
      </div>
      <div class="modal-body">
        <p>The abstraction of this trial is not valid; therefore the TSR may be inaccurate. Abstraction errors are below:</p>
        <p id="tsrAbsErrorList"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="viewTsrAnywayBtn">View TSR Anyway</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>
