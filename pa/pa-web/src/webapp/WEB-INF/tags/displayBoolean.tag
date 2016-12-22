<%@ tag display-name="displayBoolean"  description="Displays a Boolean as Yes/No"  body-content="empty" %>
<%@ attribute name="value" required="true" rtexprvalue="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
    <c:when test="${value}">
        <fmt:message key="literal.yes"/> 
    </c:when>
    <c:otherwise>
        <fmt:message key="literal.no"/> 
    </c:otherwise>
</c:choose>
