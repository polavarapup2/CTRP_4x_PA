<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ tag display-name="fieldError" description="Generates a span for a field error" body-content="empty" %>
<%@ attribute name="fieldName" required="true" type="java.lang.String" description="The name of the field" %>
<span class="formErrorMsg">
    <s:fielderror>
        <s:param>${fieldName}</s:param>
    </s:fielderror>
</span>