<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tag display-name="menuLink"  description="Generates a menu link" body-content="empty" %>
<%@ attribute name="href" required="true" type="java.lang.String" description="The href of the link" %>
<%@ attribute name="id" required="false" type="java.lang.String" description="The id of the link" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" description="The key of the label message" %>
<%@ attribute name="selected" required="false" type="java.lang.Boolean" description="True if the link must be shown as selected" %>
<%@ attribute name="newWindow" required="false" type="java.lang.Boolean" description="True if the link must be opened in a new window" %>
<c:if test="${newWindow==true}">
      <a <c:if test="${not empty id}">id="${id}" </c:if>href="${href}" target="_blank" style="text-transform:none;"><fmt:message key="${labelKey}"/></a> 
</c:if>
<c:if test="${newWindow!=true}">
	<li>
	   <a <c:if test="${not empty id}">id="${id}" </c:if>href="javascript:void(0)" onclick="submitXsrfForm('${href}');" <c:if test="${selected}">class="selected"</c:if>><fmt:message key="${labelKey}"/></a>
	</li>
</c:if>