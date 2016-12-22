<%@ page import="gov.nih.nci.pa.util.Constants" %>
<%@page import="gov.nih.nci.pa.util.CsmHelper"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="header">
   <div id="logo"><a href="javascript:void(0)"><img src="<%=request.getContextPath()%>/images/logo_pa.gif" width="264" height="43" alt="PA | Protocol Abstraction" /></a></div>
    <!--User Details-->
  	<c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome <c:out value="${CsmHelper.firstName}"/> <c:out value="${CsmHelper.lastName}"/>  |  <a href='<c:url value="/logout.action"/>'>Logout</a></div>
        </c:when>
        <c:otherwise>
        <div id="userarea"><a href='<c:url value="/protected/home.action"/>'>Login</a></div>
        </c:otherwise>
    </c:choose>
</div>
