<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tag display-name="label"  description="Generates a label" body-content="empty" %>
<%@ attribute name="labelFor" required="false" type="java.lang.String" description="The for of the label" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" description="The key of the label message" %>
<%@ attribute name="required" required="false" type="java.lang.Boolean" description="True for a required indicator" %>
<%@ attribute name="strong" required="false" type="java.lang.Boolean" description="True for a strong row" %>
<c:choose>
    <c:when test="${not empty labelFor}">
    	<label for="${labelFor}">
	        <c:if test="${strong}"><strong></c:if>
		    <fmt:message key="${labelKey}"/>
		    <c:if test="${required}"><span class="required">*</span></c:if>
		    <c:if test="${strong}"></strong></c:if>
		</label>
    </c:when>
    <c:otherwise>
    	<span class="labelspan">
	        <c:if test="${strong}"><strong></c:if>
		    <fmt:message key="${labelKey}"/>
		    <c:if test="${required}"><span class="required">*</span></c:if>
		    <c:if test="${strong}"></strong></c:if>
	    </span>
    </c:otherwise>
</c:choose>