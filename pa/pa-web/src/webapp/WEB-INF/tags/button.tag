<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ tag display-name="button" description="Generates a button within a button bar"  body-content="empty"%>
<%@ attribute name="id" required="true" type="java.lang.String" description="The id of the button" %>
<%@ attribute name="imgClass" required="false" type="java.lang.String" description="The css class for the image" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" description="The key of the label message" %>

<li><a href="javascript:void(0)" class="btn" id="${id}"><span class="btn_img"><span class="${imgClass}"><fmt:message key="${labelKey}"/></span></span></a></li>