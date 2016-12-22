<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 

<div class="row summary-box">
    <div class="col-xs-5">
       <p><strong>
          <c:out value="${sessionScope.trialSummary.assignedIdentifier.value}"/>:
       </strong><span id="popover" rel="popover" data-content='<c:out value="${sessionScope.trialSummary.officialTitle.value}"/>'>
       <c:out value="${sessionScope.trialSummary.officialTitle.value}"/></span></p>
      </div> 
    <div class="col-xs-4">
          <p><strong><fmt:message key="accrual.trialSummary.lead.organization.trialID"/></strong> <c:out value="${sessionScope.trialSummary.leadOrgTrialIdentifier.value}"/></p>
      <p> <strong><fmt:message key="accrual.trialSummary.lead.organization"/></strong> <c:out value="${sessionScope.trialSummary.leadOrgName.value}"/></p>
      </div>
    <div class="col-xs-3">
      <c:if test="${not empty sessionScope.trialSummary.principalInvestigator.value}">
          <p><strong><fmt:message key="accrual.trialSummary.principal.investigator"/></strong> <span class="text-muted"><c:out value="${sessionScope.trialSummary.principalInvestigator.value}"/></span></p>
      </c:if>
      </div>
    </div>