<%@ tag display-name="displayWhenCheckedOut" 
    description="Renders the enclosed mark-up only if 1) the study is checked out by the current user and that user is a scientific abstractor, or 2) the current user is a super abstractor" body-content="scriptless" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<c:if test="${sessionScope.isSuAbstractor || (sessionScope.trialSummary.scientificCheckout.checkoutBy != null &&
              sessionScope.loggedUserName == sessionScope.trialSummary.scientificCheckout.checkoutBy && 
              (sessionScope.isScientificAbstractor))}">
    <jsp:doBody />
</c:if>
