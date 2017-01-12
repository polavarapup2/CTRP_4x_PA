<%@ tag display-name="successMessage"  description="Displays the succes messages"  body-content="empty" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Success Messages --%>

<c:if test="${requestScope.successMessage  != null || sessionScope.successMessage  != null}">

<div class="alert alert-success">
	<strong>Message:</strong> <c:out value="${successMessage}"/>.
</div>
<c:remove var="successMessage" scope="request"/>
<c:remove var="successMessage" scope="session"/>
</c:if>
