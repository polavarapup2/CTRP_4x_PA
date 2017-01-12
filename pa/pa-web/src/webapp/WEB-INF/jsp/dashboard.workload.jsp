<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="requestURI" value="dashboardlandingPage.action"
	scope="request" />
<display:table class="data" sort="list" uid="wl" pagesize="2147483647"
	defaultsort="${sessionScope.isSuAbstractor?5:4}"
	defaultorder="ascending"
	decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
	name="${workload}" requestURI="${requestURI}" export="true">
	<display:setProperty name="paging.banner.item_name" value="trial" />
	<display:setProperty name="paging.banner.items_name" value="trials" />
	<display:setProperty name="paging.banner.group_size" value="0" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.excel.filename" value="workload.xls" />
	<display:setProperty name="export.excel.include_header" value="true" />
	<display:setProperty name="export.csv.filename" value="workload.csv" />
	<display:setProperty name="export.csv.include_header" value="true" />

	<display:column class="title" titleKey="studyProtocol.nciIdentifier" escapeXml="false"
		sortable="true" media="html" headerClass="sortable">
		<!--  ${wl.nciIdentifierTruncated} -->
		<a href="javascript:void(0);" class="nciid"
			onclick="viewProtocol('${wl.studyProtocolId}')"> <c:out
				value="${wl.nciIdentifierTruncated}"></c:out>
		</a>
	</display:column>
	<display:column class="title" titleKey="studyProtocol.nciIdentifier"
		sortable="true" media="excel csv xml"
		property="nciIdentifierTruncated" headerClass="sortable">
	</display:column>
	<display:column sortable="true" headerClass="submissionType sortable" property="submissionType"
		titleKey="studyProtocol.submissionType"/>	
	<display:column title="Submitted On" sortable="true" headerClass="filter lastCreated.dateLastCreated"
		property="lastCreated.dateLastCreated" format="{0,date,MM/dd/yyyy}" />
	<c:if test="${sessionScope.isSuAbstractor}">
        <display:column title="Submission Plus 10 Business Days" headerClass="filter calculatedSubmissionPlusTenBizDate"
            sortable="true" property="calculatedSubmissionPlusTenBizDate"
            format="{0,date,MM/dd/yyyy}" />
 
 	</c:if>
    <display:column title="Expected Abstraction Completion Date" headerClass="filter expectedAbstractionCompletionDate" escapeXml="false"    
        sortable="true" media="html"><!--<fmt:formatDate value="${wl.expectedAbstractionCompletionDate}" pattern="yyyy-MM-dd" />-->
        <span data-expected-abstraction-completion-date="true"
            data-overridden="${wl.abstractionCompletionDateOverridden}"
            data-comment="<c:out value="${wl.overriddenExpectedAbstractionCompletionComments}"/>"
            title="<c:out value="${wl.overriddenExpectedAbstractionCompletionComments}"/>"
            data-study-protocol-id="${wl.studyProtocolId}"
            data-calculated-abstraction-completion-date="<fmt:formatDate value="${wl.calculatedAbstractionCompletionDate}" 
                pattern="MM/dd/yyyy" />"            
            data-submission-date="<fmt:formatDate value="${wl.lastCreated.dateLastCreated}" 
                pattern="MM/dd/yyyy" />" ><fmt:formatDate value="${wl.expectedAbstractionCompletionDate}" pattern="MM/dd/yyyy" /></span>
    </display:column>	
	<display:column title="Expected Abstraction Completion Date" 
	    media="excel csv xml" property="expectedAbstractionCompletionDate" format="{0,date,MM/dd/yyyy}" />
	<c:if test="${sessionScope.isSuAbstractor}">
		<display:column title="Business Days Since Submitted" sortable="true"
			property="bizDaysSinceSubmitted" />
		<display:column title="Business Days on Hold (CTRP)" sortable="true"
			property="bizDaysOnHoldCTRP" />
		<display:column title="Business Days on Hold (Submitter)"
			sortable="true" property="bizDaysOnHoldSubmitter" />
	</c:if>
	<display:column title="Current On-Hold Date" sortable="true" headerClass="filter activeHoldDate"
		property="activeHoldDate" format="{0,date,MM/dd/yyyy}" />
	<display:column title="Accepted" sortable="true" headerClass="filter acceptedDate"
		property="acceptedDate" format="{0,date,MM/dd/yyyy}" />
	<display:column title="Admin Abstraction Completed" sortable="true" headerClass="filter adminAbstractionCompletedDate"
		property="adminAbstractionCompletedDate" format="{0,date,MM/dd/yyyy}" />
	<display:column title="Admin QC Completed" sortable="true" headerClass="filter adminQCCompletedDate"
		property="adminQCCompletedDate" format="{0,date,MM/dd/yyyy}" />
	<display:column title="Scientific Abstraction Completed" headerClass="filter scientificAbstractionCompletedDate"
		sortable="true" property="scientificAbstractionCompletedDate"
		format="{0,date,MM/dd/yyyy}" />
	<display:column title="Scientific QC Completed" sortable="true" headerClass="filter scientificQCCompletedDate"
		property="scientificQCCompletedDate" format="{0,date,MM/dd/yyyy}" />
	<display:column title="Ready for TSR" sortable="true" headerClass="filter readyForTSRDate"
		property="readyForTSRDate" format="{0,date,MM/dd/yyyy}" />
	<display:column escapeXml="false" sortable="true" class="checkedOut"
		media="html" title="Checked Out By" headerClass="sortable">
		<c:choose>
			<c:when
				test="${not empty wl.adminCheckout.fullName && not empty wl.scientificCheckout.fullName && wl.adminCheckout.fullName==wl.scientificCheckout.fullName}">
				<span><c:out value="${wl.adminCheckout.fullName}" /> (AS)</span>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty wl.adminCheckout.fullName}">
					<span><c:out value="${wl.adminCheckout.fullName}" /> (AD)</span>
				</c:if>
				<c:if
					test="${not empty wl.scientificCheckout.fullName && not empty wl.adminCheckout.fullName}">
					<br />
				</c:if>
				<c:if test="${not empty wl.scientificCheckout.fullName}">
					<span><c:out value="${wl.scientificCheckout.fullName}" />
						(SC)</span>
				</c:if>
			</c:otherwise>
		</c:choose>
	</display:column>

	<!-- Export only columns -->
	<display:column escapeXml="false" sortable="true" class="checkedOut"
		media="excel csv xml" title="Checked Out By" headerClass="sortable">
		<c:choose>
			<c:when
				test="${not empty wl.adminCheckout.fullName && not empty wl.scientificCheckout.fullName && wl.adminCheckout.fullName==wl.scientificCheckout.fullName}">
				<c:out value="${wl.adminCheckout.fullName}" /> (AS)
            </c:when>
			<c:otherwise>
				<c:if test="${not empty wl.adminCheckout.fullName}">
					<c:out value="${wl.adminCheckout.fullName}" /> (AD)
                    </c:if>
				<c:if
					test="${not empty wl.scientificCheckout.fullName && not empty wl.adminCheckout.fullName}">
                    ,
                </c:if>
				<c:if test="${not empty wl.scientificCheckout.fullName}">
					<c:out value="${wl.scientificCheckout.fullName}" /> (SC)
                </c:if>
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column title="Lead Organization"
		property="leadOrganizationName" media="excel csv xml" />
	<display:column title="Lead Org PO ID" property="leadOrganizationPOId"
		media="excel csv xml" />
	<display:column title="ClinicalTrials.gov Identifier"
		property="nctIdentifier" media="excel csv xml" />
	<display:column titleKey="studyProtocol.ctepIdentifier"
		property="ctepId" media="excel csv xml" />
	<display:column titleKey="studyProtocol.dcpIdentifier" property="dcpId"
		media="excel csv xml" />
	<display:column title="CDR ID" property="cdrId" media="excel csv xml" />
	<display:column title="Amendment #" property="amendmentNumber"
		media="excel csv xml" />
	<display:column title="Data Table 4 Funding"
		property="summary4FundingSponsorType" media="excel csv xml" />
	<display:column title="On Hold Date" property="recentOnHoldDate"
		format="{0,date,MM/dd/yyyy}" media="excel csv xml" />
	<display:column title="Off Hold Date" property="recentOffHoldDate"
		format="{0,date,MM/dd/yyyy}" media="excel csv xml" />
	<display:column title="On Hold Reason" property="recentHoldReason"
		media="excel csv xml" />
	<display:column title="On Hold Description"
		property="recentHoldDescription" media="excel csv xml" />
	<display:column titleKey="studyProtocol.trialtype"
		media="excel csv xml">
		<c:out
			value="${wl.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}" />
	</display:column>
	<display:column title="NCI Sponsored" media="excel csv xml">
		<c:out
			value="${wl.sponsorName=='National Cancer Institute'?'Yes':'No'}" />
	</display:column>
	<display:column title="Processing Status"
		property="documentWorkflowStatusCode.code" media="excel csv xml" />
	<display:column title="Processing Status Date"
		property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}"
		media="excel csv xml" />
	<display:column title="Admin Check out Name"
		property="adminCheckout.checkoutByUsername" media="excel csv xml" />
	<display:column title="Admin Check out Date"
		property="adminCheckout.checkoutDate" format="{0,date,MM/dd/yyyy}"
		media="excel csv xml" />
	<display:column title="Scientific Check out Name"
		property="scientificCheckout.checkoutByUsername" media="excel csv xml" />
	<display:column title="Scientific Check out Date"
		property="scientificCheckout.checkoutDate"
		format="{0,date,MM/dd/yyyy}" media="excel csv xml" />


	<display:column title="CTEP/DCP" property="ctepOrDcp" sortable="true"
		media="excel csv xml" headerClass="sortable" />
	<display:column title="Submitting Organization"
		property="submitterOrgName" sortable="true" headerClass="sortable"
		media="excel csv xml" />
	<display:column title="Submission Date"
		property="lastCreated.dateLastCreated" format="{0,date,MM/dd/yyyy}"
		sortable="true" headerClass="sortable" media="excel csv xml" />
	<display:column title="Last Milestone"
		property="milestones.lastMilestone.milestone.code" sortable="true"
		headerClass="sortable" media="excel csv xml" />
	<display:column title="Last Milestone Date"
		property="milestones.lastMilestone.milestoneDate"
		format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"
		media="excel csv xml" />
	<display:column title="Submission Source" property="studySource"
		sortable="true" headerClass="sortable" media="excel csv xml" />
	<display:column title="Processing Priority"
		property="processingPriority" sortable="true" headerClass="sortable"
		media="excel csv xml" />
	<display:column title="Comments" property="processingComments"
		media="excel csv xml" />

    <display:column title="This Trial is" media="excel csv xml"><c:out escapeXml="false"
                                            value="${wl.checkedOutByMe?'Checked out by me':''}" /><c:out escapeXml="false"
                                            value="${wl.readyForAdminProcessing && admAbs?'Ready for Admin Processing':''}" /><c:out escapeXml="false"
                                            value="${wl.readyForAdminQC && admAbs?'Ready for Admin QC':''}" /><c:out escapeXml="false"
                                            value="${wl.readyForTSRSubmission?'Ready for TSR Submission':''}" /><c:out escapeXml="false"
                                            value="${wl.submittedNotAccepted?'Submitted -- not accepted':''}" /><c:out escapeXml="false"
                                            value="${wl.readyForScientificProcessing && sciAbs?'Ready for Scientific Processing':''}" /><c:out escapeXml="false"
                                            value="${wl.readyForScientificQC && sciAbs?'Ready for Scientific QC':''}" /><c:if
                                            test="${wl.documentWorkflowStatusCode.code=='On-Hold'}">On hold since ${wl.onHoldDate!=null?wl.onHoldDate:'N/A'}, reason: ${not empty wl.onHoldReasons?wl.onHoldReasons:'N/A'}
                                        </c:if></display:column>

	<s:set var="milestoneCodesForReporting" scope="request"
		value="@gov.nih.nci.pa.enums.MilestoneCode@getMilestoneCodesForReporting()" />
	<c:forEach items="${milestoneCodesForReporting}" var="milestone">
		<display:column title="${milestone.code}" media="excel csv xml">
			<s:set scope="request" var="milestoneDate"
				value="%{#attr.wl.getMilestoneForReporting(#attr.milestone).milestoneDate}" />
			<fmt:formatDate value="${requestScope.milestoneDate}"
				pattern="MM/dd/yyyy" />
		</display:column>
		<display:column title="Added By" media="excel csv xml">
			<s:set scope="request" var="milestoneCreator"
				value="%{#attr.wl.getMilestoneForReporting(#attr.milestone).creator}" />
			<c:out value="${requestScope.milestoneCreator}" escapeXml="false" />
		</display:column>
		<display:column title="Added On" media="excel csv xml">
			<s:set scope="request" var="milestoneCreateDate"
				value="%{#attr.wl.getMilestoneForReporting(#attr.milestone).createDate}" />
			<fmt:formatDate value="${requestScope.milestoneCreateDate}"
				pattern="MM/dd/yyyy" />
		</display:column>
	</c:forEach>
</display:table>

<div id="date-range-filter" title="Date Filter" style="display: none;">
  <table>
  <s:iterator 
  value="#{'limit':' Limit the results to the following date range (inclusive):',
  'nullDate':'Does NOT exist',
  'unrestricted':'Unrestricted'}" 
  var ="radioValue"  >
 <tr>
  <td>   
   <s:radio list="#radioValue.key" listValue="#radioValue.value"  name="choice"
    id="choice" onChange="disableFieldsIfNeeded()">
  </s:radio>
  </td>
  </tr>
   <s:if test="#radioValue.key.equals('limit')">
  <tr>
  <td>  
    <table>
        <tr>
            <td><label for="dateFrom">From:</label></td>
            <td><s:textfield name="dateFrom" id="dateFrom"/></td>       
        </tr>
        <tr>
            <td><label for="dateTo">To:</label></td>
            <td><s:textfield name="dateTo" id="dateTo"/></td>       
        </tr> 
    </table>
   </td>
   </tr>
   </s:if>
   </s:iterator>
  </table>  
  <s:hidden name="dateFilterField" id="dateFilterField"/>
</div>

<div id="submission-type-filter" title="Submission Type" style="display: none;">
  <p>   
    Limit the results to the following submission types:
  </p>  
  <div>
    <s:checkboxlist name="submissionTypeFilter" list="#{'Abbreviated':'Abbreviated','Amendment':'Amendment','Complete':'Complete'}"/>
  </div>  
</div>

<div id="abstraction-date-override" title="Expected Abstraction Completion Date" style="display: none;">
  <p>   
    Trial Submission Date: <span id="trialSubmissionDate"></span>
  </p>
  <div>
    <label for="newCompletionDate">Expected Abstraction Completion Date <span class="required">*</span></label>
  </div>  
  <div>
    <s:textfield name="newCompletionDate" id="newCompletionDate" readonly="true"/>
  </div>
   <div>
    <label for="newCompletionDateComments">Comments <span class="required">*</span></label>
  </div>  
  <div>
    <s:textarea name="newCompletionDateComments" id="newCompletionDateComments" maxlength="1000" cssClass="charcounter"/>
  </div>  
</div>

<div id="date-override-warning"
        title="Confirm Date" style="display: none;">
        <p>
            <span class="ui-icon ui-icon-alert"
                style="float: left; margin: 0 7px 15px 0;"></span>
                Warning: You are about to modify the Abstraction Completion Date as follows: <br/><br/>
                <b>From:</b> <span id="date1"></span> &mdash; <span id="days1"></span> business day(s) from trial submission</br>
                <b>To:&nbsp;&nbsp;&nbsp;&nbsp;</b>   <span id="date2"></span> &mdash; <span id="days2"></span> business day(s) from trial submission</br></br>
                Are you sure?
        </p>
</div>


<div id="validationError"
        title="Error" style="display: none;">
        <p>
            <span class="ui-icon ui-icon-alert"
                style="float: left; margin: 0 7px 15px 0;"></span><span id="validationErrorText"></span>
        </p>
</div>


