<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<reg-web:titleRowDiv titleKey="view.trial.statusDates"/>
<div class="row form-horizontal details">
<reg-web:valueRowDiv labelKey="view.trial.currentTrialStatus" noLabelTag="true">
    <c:out value="${trialDTO.statusCode}"/>
</reg-web:valueRowDiv>
<c:if test="${trialDTO.reason != ''}">
    <reg-web:valueRowDiv labelKey="view.trial.trialStatusReason" noLabelTag="true">
        <c:out value="${trialDTO.reason}"/>
    </reg-web:valueRowDiv>
</c:if>
<reg-web:valueRowDiv labelKey="view.trial.currentTrialStatusDate" noLabelTag="true">
    <c:out value="${trialDTO.statusDate}"/>
</reg-web:valueRowDiv>
<reg-web:valueRowDiv labelKey="view.trial.trialStartDate" noLabelTag="true">
    <c:out value="${trialDTO.startDate}"/>
    <c:out value="${trialDTO.startDateType }"/>
</reg-web:valueRowDiv>
<reg-web:valueRowDiv labelKey="view.trial.primaryCompletionDate" noLabelTag="true">
    <c:out value="${trialDTO.primaryCompletionDate}"/>
    <c:out value="${trialDTO.primaryCompletionDateType}"/>
</reg-web:valueRowDiv>
<reg-web:valueRowDiv labelKey="view.trial.completionDate" noLabelTag="true">
    <c:out value="${trialDTO.completionDate}"/>
    <c:out value="${trialDTO.completionDateType}"/>    
</reg-web:valueRowDiv> 
<reg-web:valueRowDiv labelKey="blank.label" noLabelTag="true">
   
    <span class="info">Please refer to <a href="https://wiki.nci.nih.gov/x/l4CNC" target="newPage">Trial Status Rules for Start and Completion dates</a>.</span>
</reg-web:valueRowDiv>
</div>