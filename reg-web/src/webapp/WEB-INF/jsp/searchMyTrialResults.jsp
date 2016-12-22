<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
<script type="text/javascript" language="javascript">
jQuery(function() {
    jQuery('#row').dataTable( {
        "sDom": 'pCrfltip',
        "pagingType": "full_numbers",
         "order": [[ 0, "desc" ]],
        "oColVis": {
            "buttonText": "Choose columns"
        },
        "oLanguage": {
            "sInfo": "Showing _START_ to _END_ of _TOTAL_",
            "sLengthMenu": "Show _MENU_",
            "oPaginate": {
                "sFirst": "<<",
                "sPrevious": "<",
                "sNext": ">",
                "sLast": ">>"
              }
        }
    });
});
</script>
<style type="text/css">
#actmenu {
    z-index:100;
    position:relative;
}
/*To not wrap header*/
thead th { 
    white-space: nowrap; 
}
/*Increase column chooser items width*/
ul.ColVis_collection {
    width:250px;
}
/*Reduce Column chooser button height*/
button.ColVis_Button {
    height : 25px;
}
</style>
</head>
<s:set name="records" value="records" scope="request"/>
<s:if test="records != null">
    <s:if test="records.size > 0">
        <c:set var="topic" scope="request" value="searchresults"/>
    </s:if>
    <c:choose>
        <c:when test="${requestScope.partialSubmission != null}">
            <h2 id="search_results">Saved Draft Search Results</h2>
            <div class="table-wrapper">
            <div class="table-responsive">
            <display:table class="table table-striped table-bordered" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" id="row"
                           name="records" requestURI="searchTrialgetMyPartiallySavedTrial.action" export="true">
                <display:setProperty name="paging.banner.item_name" value="trial"/>
                <display:setProperty name="paging.banner.items_name" value="trials"/>                           
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsSavedDraftSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsSavedDraftSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column class="title" title="Temp Trial Identifier" headerScope="col" scope="row" media="html">
                    <a href="javascript:void(0)" onclick="viewPartialProtocol('${row.studyProtocolId}');"><c:out value="${row.studyProtocolId}"/></a>
                </display:column>
                <display:column class="title" title="Temp Trial Identifier" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.studyProtocolId}"/>
                 </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"  headerScope="col"/>
                <display:column titleKey="search.trial.action" media="html">
                    <s:if test="%{#attr.row.proprietaryTrial}">
                        <s:url id="completeUrl" action="submitProprietaryTrialcomplete"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    </s:if>
                    <s:else>
                        <s:url id="completeUrl" action="submitTrialcompletePartialSubmission"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    </s:else>
                    <s:a href="%{completeUrl}"><button type="button" class="btn btn-icon btn-primary"> <i class="fa-floppy-o"></i>Complete</button></s:a>
                </display:column>
                <display:column titleKey="search.trial.action" media="html">
                    <s:url id="deleteUrl" action="submitTrialdeletePartialSubmission">
                        <s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" />
                        <s:param name="usercreated" value="%{#attr.row.userLastCreated}" />
                    </s:url>
                    <s:a href="%{deleteUrl}"><button type="button" class="btn btn-icon btn-primary" onclick="return deletePartialProtocol()"> <i class="fa-trash-o"></i>Delete</button></s:a>
                </display:column>
            </display:table>
            </div>
            </div>
        </c:when>
        <c:otherwise>
            <h2 id="search_results">Clinical Trials Search Results</h2>
            <div class="table-wrapper">
            <div class="table-responsive">
            <s:set name="accrualDiseaseValues" value="@gov.nih.nci.pa.util.PaRegistry@getAccrualDiseaseTerminologyService().getValidCodeSystems()" />
            <display:table class="table table-striped table-bordered" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" id="row"
                           name="records" requestURI="searchTrialquery.action" export="true">
                <display:setProperty name="paging.banner.item_name" value="trial"/>
                <display:setProperty name="paging.banner.items_name" value="trials"/>
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsTrialSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsTrialSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column escapeXml="true" class="title" title="NCI Trial Identifier"
                    property="nciIdentifier" href="searchTrialview.action" paramId="studyProtocolId" paramProperty="studyProtocolId"
                    headerScope="col" scope="row" media="html"/>
                <display:column class="title" title="NCI Trial Identifier" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.nciIdentifier}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength="200" headerScope="col" media="excel csv"/>
                <display:column escapeXml="false" title="<div style='width:350px;text-align: left;'>Title</div>" headerScope="col" media="html">
                    <span style="display:none"><c:out value="${row.officialTitle}"/></span>
                    <fmt:message key="studyAlternateTitles.text" var="title" />
                    <c:if test="${not empty row.studyAlternateTitles}">                    
                        <a href="javascript:void(0)" title="${title}" onclick="displayStudyAlternateTitles('${row.studyProtocolId}')">(*)</a>
                    </c:if>
                    <c:out value="${row.officialTitle}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.piFullName" property="piFullName" headerScope="col"/>
                <display:column titleKey="search.trial.nctNumber" property="nctIdentifier" />
                <display:column title="Other Identifiers"  property="otherIdentifiersAsString" />
                <display:column escapeXml="true" titleKey="search.trial.studyStatusCode" property="studyStatusCode.code" headerScope="col"/>
                <display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode.code" headerScope="col"/>
                 <display:column title="Available Actions" media="html">
                    <s:if test="%{#attr.row.actionVisible}">
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle btn-sm" type="button">Select Action <span class="caret"></span></button>
                        <ul role="menu" class="dropdown-menu" id="actmenu" >
                                <li>
                                    <s:if test="%{!(#attr.row.update == null || #attr.row.update.equals(''))}">                                 
                                        <s:if test="%{#attr.row.proprietaryTrial}">
                                        <s:url id="url" action="updateProprietaryTrialview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                                            <s:a href="%{url}"><s:property value="%{#attr.row.update}" /></s:a>
                                        </s:if>
                                        <s:else>
                                            <s:url id="url" action="updateTrialview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                                            <s:a href="%{url}"><s:property value="%{#attr.row.update}" /></s:a>
                                        </s:else>
                                    </s:if>                                                                 
                                </li>
                                <li>
                                    <s:if test="%{!(#attr.row.amend == null || #attr.row.amend.equals(''))}">
                                        <s:url action="amendTrialview.action" var="urlTag" >
                                            <s:param name="studyProtocolId"><s:property value="%{#attr.row.studyProtocolId}" /></s:param>
                                        </s:url>
                                        <s:a href="%{urlTag}">Amend</s:a>
                                    </s:if>
                                </li>
                                <li>
                                    <s:if test="%{!(#attr.row.statusChangeLinkText == null || #attr.row.statusChangeLinkText.equals(''))}">
                                        <s:if test="%{#attr.row.proprietaryTrial}">
                                        </s:if>
                                        <s:else>
                                            <s:url id="updateTrialStatusUrl" action="updateTrialStatuspopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                                            <a href="javascript:void(0)" onclick="showPopup('${updateTrialStatusUrl}', '', 'Update Trial Status');">Change Status</a>
                                        </s:else>
                                    </s:if>
                                </li>
                                <li>
                                    <s:if test="%{#attr.row.showViewTSR.booleanValue() == true}">
                                         <a href="javascript:void(0)" onclick="viewTsr('${row.studyProtocolId}');">View TSR</a>
                                    </s:if>
                                </li>
                                <li>
                                    <s:if test="%{#attr.row.showSendXml.booleanValue() == true}">
                                         <a href="javascript:void(0)" onclick="viewXml('${row.studyProtocolId}');">View XML</a>
                                    </s:if>
                                </li>
                                <li>
                                    <s:if test="%{#attr.row.currentUserCanAddSite}">
                                        <s:url id="addMySiteUrl" action="addSitepopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                                        <a href="javascript:void(0);" onclick="showPopup('${addMySiteUrl}', '', 'Add Participating Site');" 
                                           onkeypress="showPopup('${addMySiteUrl}', '', 'Add Participating Site');">Add My Site</a>
                                    </s:if>
                                </li>
                                <li>
                                    <s:if test="%{#attr.row.currentUserCanEditSite}">
                                        <s:url id="updateMySiteUrl" action="addSitepopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                                        <a href="javascript:void(0);" onclick="showPopup('${updateMySiteUrl}', '', 'Update Participating Site');" 
                                           onkeypress="showPopup('${updateMySiteUrl}', '', 'Update Participating Sit');">Update My Site</a>
                                    </s:if>
                                </li>
                                
                                <li>
                                    <s:if test="%{#attr.row.verifyData.booleanValue() == true}">
                                        <s:url id="editUrl" namespace="/protected" action="trialDataVerificationview">
                                            <s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}"/>
                                        </s:url>
                                        <s:a href="%{editUrl}">
                                          Verify Data
                                        </s:a>
                                    </s:if>
                                </li>
                            </ul>
                        </div>  
                    </s:if>                                                                 
                 </display:column>  
                 <display:column title="Accrual Disease Terminology" media="html">
                    <s:if test="%{#attr.row.showAccrualOption.booleanValue() == true}">
                    <s:set name="accrualDiseaseCode" value="%{#attr.row.accrualDiseaseCode}"/>
                    <s:select id="accrualDisease_%{#attr.row.studyProtocolId}" headerKey="" headerValue="--Select--" name="accrualDiseaseTerminology_%{#attr.row.studyProtocolId}" 
                    list="#accrualDiseaseValues" onchange="saveDiseaseCode('%{#attr.row.studyProtocolId}',this.value)" value="accrualDiseaseCode" cssClass="form-control" />
                    </s:if>
                    <s:if test="%{#attr.row.showAccrualOption.booleanValue() == false}">
                        <c:out value="${row.accrualDiseaseCode}"/>
                    </s:if>
                </display:column>
                <display:column title="Accrual Disease Terminology" media="csv excel xml">
                    <c:out value="${row.accrualDiseaseCode}"/>
                </display:column>
                <display:column title="Sites" media="html">
                    <s:url id="viewParticipatingSites" action="participatingSitespopup"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    <a href="javascript:void(0)" onclick="showPopup('${viewParticipatingSites}', '', 'View Participating Sites');">View</a>
                </display:column> 
                <display:column title="Phase" property="phaseName" />
                <display:column title="Primary Purpose" property="primaryPurpose" />
                <display:column title="Category" property="trialCategory" />  
                <display:column title="Trial Start Date" property="startDate" format="{0,date,MM/dd/yyyy}" />                                             
                <display:column title="Responsible Party" property="responsiblePartyName" />               
                <display:column title="<div style='width:150px;text-align: left;'>Sponsor</div>" property="sponsorName" media="html"/>
                <display:column title="Sponsor" property="sponsorName" media="csv excel xml"/>
                <display:column title="Data Table 4 Funding Sponsor Type" property="summary4FundingSponsorType" />
                <display:column titleKey="search.trial.recordVerificationDate" property="recordVerificationDate" format="{0,date,MM/dd/yyyy}" />
                <display:column title="<div style='width:150px;text-align: left;'>Submitter</div>" property="lastCreated.userLastDisplayName" media="html"/>
                <display:column title="Submitter" property="lastCreated.userLastDisplayName" media="csv excel xml"/>
                <display:column title="Primary Completion Date" property="primaryCompletionDate" format="{0,date,MM/dd/yyyy}" />                             
                <display:column title="Last Update Submitted" property="updatedDate" format="{0,date,MM/dd/yyyy}" />
                <display:column title="Last Updater Name" property="lastUpdaterDisplayName" />
                <display:column title="Last Amendment Submitted" property="amendmentDate" format="{0,date,MM/dd/yyyy}" />                               
                <display:column title="Last Amender Name" property="lastUpdatedUserDisplayName" />     
                <display:column title="On-Hold Reason" property="onHoldReasons" escapeXml="true"/>
                
            </display:table>
            </div>
            </div>
        </c:otherwise>
    </c:choose>
</s:if>