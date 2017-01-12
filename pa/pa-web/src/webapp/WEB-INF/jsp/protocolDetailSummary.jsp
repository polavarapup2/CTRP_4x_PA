<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>

<script type="text/javascript" language="javascript">
	function displaySubmitter(){
	    showPopWin('ajaxdisplaypersoninfoquery.action', 600, 300, '', 'Trial Submitter Information');
	}
</script>

<div class="summarybox">
	<div class="summarytitle">
		<span class="value"><strong> <c:out value="${sessionScope.trialSummary.nciIdentifier }"/></strong>:
		    <c:out value="${func:abbreviate(sessionScope.trialSummary.officialTitle, 200)}"/>
		</span>
	</div>
	<div class="float33_first">
		<div class="row">
			<span class="label"> <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>:</span>
			<span class="value"><c:out value="${sessionScope.trialSummary.localStudyProtocolIdentifier}"/></span>
		</div>
		<div class="row">
			<span class="label"><fmt:message key="studyProtocol.leadOrganization"/>:</span> <span class="value"><a href="javascript:void(0);"
				onclick="javascript:displayOrgDetails(<c:out value="${sessionScope.trialSummary.leadOrganizationPOId}"/>);">
					<c:out value="${sessionScope.trialSummary.leadOrganizationName}" />
			</a></span>
		</div>
		<c:if test="${sessionScope.trialSummary.amendmentNumber != null}">
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.amendmentNumber"/>:</span>
                <span class="value"><c:out value="${sessionScope.trialSummary.amendmentNumber }"/></span>
            </div>
        </c:if>
        
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.trialCategory"/></span>
                <span class="value"><c:out value="${sessionScope.trialSummary.trialCategory }" /></span>
            </div>
       <c:if test="${sessionScope.trialSummary.studySource != null}">
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.studySource"/>:</span>
                <span class="value"><c:out value="${sessionScope.trialSummary.studySource }"/></span>
            </div>
        </c:if>
        
	</div>
	<div class="float33">
		<c:if test="${sessionScope.trialSummary.piFullName != null }">
    		<div class="row">
    			<span class="label"><fmt:message key="studyProtocol.principalInvestigator"/>:</span>
    			<span class="value"><a href="javascript:void(0);" 
    			     onclick="javascript:displayPersonDetails(<c:out value="${sessionScope.principalInvestigatorPOId}"/>);"> 
    			         <c:out value="${sessionScope.trialSummary.piFullName }"/>
                </a></span>
    		</div>
		</c:if>
		<div class="row">
			<span class="label"><fmt:message key="last.submitter"/>:</span>
			<span class="value"><a href="javascript:displaySubmitter();" id="displaySubmitterLink"><c:out value="${sessionScope.trialSummary.lastCreated.userLastCreatedUsername }"/></a></span>
		</div>
		<div class="row">
            <span class="label"><fmt:message key="last.submitter.organization"/>:</span>
             <span class="value"><a href="javascript:void(0);"
                onclick="javascript:displayOrgDetails(<c:out value="${sessionScope.trialSubmitterOrgPOId}"/>);">
                    <c:out value="${sessionScope.trialSubmitterOrg}" /></a></span>
        </div>
	    <c:if test="${sessionScope.trialSummary.amendmentDate != null}">
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.amendmentDate"/>:</span>
                <span class="value"><fmt:formatDate value="${sessionScope.trialSummary.amendmentDate }" pattern="MM/dd/yyyy"/></span>
            </div>
        </c:if>
	</div>
	<div class="float33">
	    <c:if test="${sessionScope.trialSummary.studyStatusCode.code != null}">
    		<div class="row">
    			<span class="label"> <fmt:message key="studyProtocol.studyStatus"/>:</span>
    			<span class="value"><c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/></span>
    		</div>
		</c:if>
        <c:if test="${sessionScope.trialSummary.studyStatusDate != null}">
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.studyStatusDate"/>:</span>
                <span class="value"><fmt:formatDate value="${sessionScope.trialSummary.studyStatusDate }" pattern="MM/dd/yyyy"/></span>
            </div>
        </c:if>
		<div class="row">
			<span class="label"><fmt:message key="studyProtocol.documentWorkflowStatus"/>:</span>
			<span class="value"><c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/></span>
		</div>
	    <c:if test="${sessionScope.trialSummary.adminCheckout.checkoutBy != null}">
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.adminCheckOutBy"/>:</span>
                <span class="value"><c:out value="${sessionScope.trialSummary.adminCheckout.checkoutByUsername }"/></span>
            </div>
        </c:if>
        <c:if test="${sessionScope.trialSummary.scientificCheckout.checkoutBy != null}">
            <div class="row">
                <span class="label"><fmt:message key="studyProtocol.scientificCheckOutBy"/>:</span>
                <span class="value"><c:out value="${sessionScope.trialSummary.scientificCheckout.checkoutByUsername }"/></span>
            </div>
        </c:if>
	</div>
	<div class="clear"></div>

</div>