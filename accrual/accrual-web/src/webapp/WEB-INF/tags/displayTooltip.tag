<%@ tag display-name="displayLabel"  description="Displays the label and tooltip"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tooltip-functions.tld" prefix="tooltipfn" %>
<%@ attribute name="tooltip" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="tooltipName" value="${fn:replace(tooltip, '.', '_')}" />
<c:choose>
    <c:when test="${not empty tooltip}">
        <c:set var="quote" value='"' />
        <c:set var="escapeChar" value="\\" />
        <c:set var="escapedQuote" value="${escapeChar}${quote}" />
        <c:set var="tooltipText" value="${fn:replace(tooltipfn:getTooltip(tooltip), quote, escapedQuote)}"/>
    </c:when>
    <c:otherwise><c:set var="tooltipText" value=""/></c:otherwise>
</c:choose>
<i class="fa-question-circle help-text" id="popover" rel="popover" data-content="${tooltipText}"  data-placement="top" data-trigger="hover"></i>
