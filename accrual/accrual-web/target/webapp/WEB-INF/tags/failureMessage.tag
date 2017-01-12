<%@ tag display-name="failureMessage"  description="Displays the failure messages"  body-content="empty" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Failure Messages --%>
<c:if test="${requestScope.failureMessage  != null}">
<div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong> <c:out value="${requestScope.failureMessage }"/>.</div>
<c:remove var="failureMessage" scope="request"/>
</c:if>
