<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set scope="request" var="pageTitleKey">managetrialownership.page.title</c:set>
<c:set scope="request" var="actionName">manageTrialOwnership</c:set>
<c:set scope="request" var="topicValue">manageownership</c:set>
<c:set scope="request" var="pageHeaderKey">managetrialownership.page.header</c:set>
<c:set scope="request" var="trialHeaderKey">managetrialownership.trials.header</c:set>
<c:set scope="request" var="enableEmailPrefs">true</c:set>
<jsp:include page="/WEB-INF/jsp/manageOwnership.jsp"/>