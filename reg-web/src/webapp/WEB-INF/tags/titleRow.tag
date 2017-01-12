<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tag display-name="titleRow"  description="Generates a table row for a title" body-content="empty" %>
<%@ attribute name="titleKey" required="true" type="java.lang.String" description="The key of the title message" %>
<tr>
    <th colspan="2"><fmt:message key="${titleKey}"/></th>
</tr>