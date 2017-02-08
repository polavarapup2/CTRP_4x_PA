<%@ tag display-name="button" body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="href" required="true" type="java.lang.String" %>
<%@ attribute name="onclick" required="false" type="java.lang.String" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="style" required="true" type="java.lang.String" %>

<c:set var="onclickValue" value="onclick='${onclick}'"/>
<li>
    <a href="${href}" class="btn" ${onclickValue}>
        <span class="btn_img"><span class="${style}"><fmt:message key="${labelKey}"/></span></span>
    </a>
</li>