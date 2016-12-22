<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="reg-web" %>
<%@ tag display-name="valueRow"  description="Generates a table row for a value" %>
<%@ attribute name="cellOnly" required="false" type="java.lang.Boolean" description="True to generate only the tds" %>
<%@ attribute name="id" required="false" type="java.lang.String" description="The id of the td" %>
<%@ attribute name="labelFor" required="false" type="java.lang.String" description="The for of the label" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" description="The key of the label message" %>
<%@ attribute name="required" required="false" type="java.lang.Boolean" description="True for a required indicator" %>
<%@ attribute name="strong" required="false" type="java.lang.Boolean" description="True for a strong row" %>
<%@ attribute name="tooltip" required="false" type="java.lang.String" description="An optional tooltip" %>
<%@ attribute name="noLabelTag" required="false" type="java.lang.Boolean" description="If set to true, dont use label tag" %>
<%@ attribute name="requiredId" required="false" type="java.lang.String" description="The id of the asterix span" %>
<c:if test="${not cellOnly}">
    <c:choose>
        <c:when test="${empty id}"><tr></c:when>
        <c:otherwise><tr id="${id}"></c:otherwise>
    </c:choose>
</c:if>
    <c:choose>
        <c:when test="${noLabelTag}"><td scope="row" class="label-noinput"></c:when>
        <c:otherwise><td scope="row" class="label"></c:otherwise>
    </c:choose>
        <c:choose>
            <c:when test="${not empty tooltip}">
                <reg-web:displayTooltip tooltip="${tooltip}">
                    <reg-web:label requiredId="${requiredId}" labelFor="${labelFor}" labelKey="${labelKey}" required="${required}" strong="${strong}" noLabelTag="${noLabelTag}"/>
                </reg-web:displayTooltip>
            </c:when>
            <c:otherwise>
                <reg-web:label requiredId="${requiredId}" labelFor="${labelFor}" labelKey="${labelKey}" required="${required}" strong="${strong}" noLabelTag="${noLabelTag}" />
            </c:otherwise>
        </c:choose>
    </td>
    <td class="value">
        <c:if test="${strong}"><strong></c:if>
        <jsp:doBody/>
        <c:if test="${strong}"></strong></c:if>
    </td>
<c:if test="${not cellOnly}">    
    </tr>
</c:if>    