<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="gov.nih.nci.pa.util.CsmHelper"%>
 <script>
          function logout(){
            document.forms[0].action="<%=request.getContextPath()%>/logout.action";
            document.forms[0].submit();
          }
        </script>
<header class="masthead">
    <div class="container">
      <div class="row">
      <c:choose>
        <c:when test="${pageContext.request.remoteUser == null 
                || (pageContext.request.remoteUser != null && !sessionScope.disclaimerAccepted)}">
            <div class="align-center">
                <div class="navbar-brand"><img alt="Accrual" src="<%=request.getContextPath()%>/images/logo.png"/></div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="col-xs-9">
                <div class="navbar-brand"><a href="#"><img alt="Accrual" src="<%=request.getContextPath()%>/images/logo.png"/></a></div>
            </div>
            <div class="col-xs-3">
                <div class="dropdown pull-right"> <a href="#" data-toggle="dropdown" class="dropdown-toggle nav-user">${CsmHelper.firstName} ${CsmHelper.lastName}</a>
		            <ul class="dropdown-menu">
		              <li><a href="#" class="account" data-toggle="modal" data-target="#myAccount">My Account</a></li>
		              <li class="divider"></li>
		              <li class="sign-out">
		                <button type="button" class="btn btn-default btn-sm" onClick="logout();">Sign Out</button>
		              </li>
		            </ul>
                </div>
            </div>
        </c:otherwise>
      </c:choose>      
      </div>
    </div>
  </header>
