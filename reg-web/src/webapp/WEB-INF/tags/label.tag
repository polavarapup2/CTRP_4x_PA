<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tag display-name="label"  description="Generates a label" body-content="empty" %>
<%@ attribute name="labelFor" required="false" type="java.lang.String" description="The for of the label" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" description="The key of the label message" %>
<%@ attribute name="required" required="false" type="java.lang.Boolean" description="True for a required indicator" %>
<%@ attribute name="strong" required="false" type="java.lang.Boolean" description="True for a strong row" %>
<%@ attribute name="noLabelTag" required="false" type="java.lang.Boolean" description="If set to true, dont use label tag" %>
<%@ attribute name="requiredId" required="false" type="java.lang.String" description="The id of the asterix span" %>
<c:if test="${not noLabelTag}"><label<c:if test="${not empty labelFor}"> for="${labelFor}"</c:if>></c:if>
    <c:if test="${strong}"><strong></c:if>
    <fmt:message key="${labelKey}"/>
    <c:if test="${required}"><span class="required" id="${requiredId}">*</span></c:if>
    <c:if test="${strong}"></strong></c:if>
<c:if test="${not noLabelTag}"></label></c:if>