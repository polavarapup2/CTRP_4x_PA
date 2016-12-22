<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@page import="gov.nih.nci.pa.util.CsmHelper"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" language="javascript">
	function displayMyAccount() {
		var width = 900;
		var height = 600;
		if (Prototype.Browser.IE) {
			width = 920;
	        height = 620;            		
		}
		showPopWin('registerUsershowMyAccount.action', width, height, donothing, 'My Account');
	}
	function donothing() {
		//does nothing
	}
</script>
<header class="masthead">
    <div class="container">
    	<div class="row">
    	 
       <!--User Details-->
      <c:choose>
        <c:when test="${(pageContext.request.remoteUser != null && sessionScope.disclaimerAccepted)}">
	        <div class="col-xs-9">
	          <div class="navbar-brand"><a data-placement="top" rel="tooltip" href="#" data-original-title="Clinical Trials Reporting Program"><img src="${imagePath}/logo.png" alt="logo"></a></div>
	        </div>
	        <div class="col-xs-3">
	          <div class="dropdown pull-right">
	          	<a href="#" data-toggle="dropdown" class="dropdown-toggle nav-user"><c:out value="${CsmHelper.firstName}"/> <c:out value="${CsmHelper.lastName}"/></a>
	            <ul class="dropdown-menu">
	              <li><a href="javascript:void(0);" class="account" onclick="displayMyAccount();">My Account</a></li>
	              <li class="divider"></li>
	              <li class="sign-out">
	                <button type="button" class="btn btn-default btn-sm" href="javascript:void(0)" onclick="submitXsrfForm('/registry/logout.action')">Sign Out</button>
	              </li>
	            </ul>
	          </div>
	        </div>
        </c:when>
        <c:otherwise>
        	<div class="align-center">
	          <div class="navbar-brand"><a data-placement="top" rel="tooltip" href="#" data-original-title="Clinical Trials Reporting Program"><img src="${imagePath}/logo.png" alt="logo"></a></div>
	        </div>
        </c:otherwise>
	  </c:choose>
      </div>
    </div>
  </header>