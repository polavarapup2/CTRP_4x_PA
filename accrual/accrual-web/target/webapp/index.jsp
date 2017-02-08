<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${(sessionScope.accrualRole == 'Submitter')}"><% response.sendRedirect("/accrual/protected/home.action"); %></c:when>
    <c:otherwise><% response.sendRedirect("/accrual/home.action"); %></c:otherwise>
</c:choose>