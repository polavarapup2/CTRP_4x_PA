<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<head>

<script>
       if (self != top) {
               window.parent.location.href = window.parent.location.href;
       }
       
       </script>
</head>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/loginValidation.js'/>"></script>
	
	<script type="text/javascript" language="javascript">
		window.supressTimeoutPopup = true;
	
		document.onkeypress = runEnterScript;
		function runEnterScript(e) {
			var KeyID = (window.event) ? event.keyCode : e.keyCode;
			
			if (KeyID == 13) {
				var fieldID = document.activeElement.id;
				if(fieldID == 'j_password') {
					document.loginForm.submit();
					return false;
				}
				if(fieldID == 'j_username') {
					$('j_password').focus();
					return false;
				}
			}
			
			return true;
		}
		

	</script>

<!-- Begin page content -->
    <div class="row">
      <div class="col-xs-6 intro"> <img src="/registry/images/nci-logo.png" alt="NCI logo" class="pull-left"/>
        <h4>Welcome to NCI&apos;s Clinical Trials Reporting Program</h4>
        <p>This site enables you to register a trial with NCI&apos;s Clinical Trials Reporting Program. You can:</p>
        <ul>
          <li>Register clinical trials</li>
          <li>Search registered trials by Title, Phase, Trial Identifiers and Organizations</li>
		</ul>
		<p>Want to learn more about the Reporting Program? Visit the <a href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main/" target="_blank">NCI Clinical Trials Reporting Program</a> website. You can also email CBIIT Application Support at <a href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a> if you have questions or need assistance.</p>
      </div>
      <div class="col-xs-6">
        <ul class="nav nav-tabs">
          <li class="active"><a href="<s:url action='protected/disClaimerAction.action?actionName=searchTrial.action' />" ><i class="fa-sign-in"></i>Sign In</a></li>
          <li><a href="<s:url action='registerUser.action' />" ><i class="fa-pencil-square-o"></i>Sign Up</a></li>
          <li><a href="#forgot-password" data-toggle="tab"><i class="fa-key"></i>Forgot Your Password</a></li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane fade in active" id="sign-in">
            <form class="form-horizontal" role="form" action="j_security_check" method="post" id="loginForm" name="loginForm" onsubmit="return (<%=Boolean.valueOf(System.getProperty("ctrp.env.ci"))%> || validate());">
            	<c:if test="${not empty param.failedLogin}">
              		<p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            	</c:if>
              <div class="form-group">
                <label for="j_username" class="col-xs-3 control-label">Username</label>
                <div class="col-xs-7">
                  <input type="text" maxlength="100" size="25" class="form-control" id="j_username" name="j_username"  placeholder="Enter your username">
                </div>
              </div>
              <div class="form-group">
                <label for="j_password" class="col-xs-3 control-label">Password</label>
                <div class="col-xs-7">
                  <input type="password" maxlength="100" size="25" class="form-control" id="j_password" name="j_password" autocomplete="off" placeholder="Enter your password">
                </div>
              </div>
              <c:if test="${!empty applicationScope['AUTHENTICATION_SOURCE_MAP']}">
		         <c:choose>
		             <c:when test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) == 1}">
		                  <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
		                    <input type="hidden" name="authenticationServiceURL"
		                         value="<c:out value="${item.value}"/>" />
		                  </c:forEach>
		             </c:when>
		             <c:otherwise>
		             	<div class="form-group">
			                <label for="authenticationServiceURL" class="col-xs-3 control-label">Account Source:</label>
			                <div class="col-xs-7">
			                  <select name="authenticationServiceURL" id="authenticationServiceURL" size="1" class="form-control" placeHolder="Select your account source">
		                        <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
		                        <c:choose>
		                            <c:when test="${fn:contains(item.value,'Dorian')}">
		                                <option value="<c:out value="${item.value}" />" selected="selected">
		                            </c:when>
		                            <c:otherwise>
		                                <option value="<c:out value="${item.value}" />">
		                            </c:otherwise>
		                        </c:choose>
		                                    <c:out value="${item.key}" />
		                                </option>
		                        </c:forEach>
		                    </select>
			                </div>
			              </div>
		             </c:otherwise>
		          </c:choose>
		      </c:if>
              <div class="bottom">
                <button id="loginButton" type="button" class="btn btn-icon-alt btn-primary" onClick="document.loginForm.submit();">Sign In<i class="fa-arrow-circle-right"></i></button>
              </div>
            </form>
          </div>
          <div class="tab-pane fade" id="sign-up">
          </div>
          <div class="tab-pane fade" id="forgot-password">
            <div class="tab-inside">
              <h4 class="heading"><span>Resetting Your Password</span></h4>
              <p>If you forgot your password, please visit the NCI Password Station at <a href="http://password.nci.nih.gov" target="_blank">http://password.nci.nih.gov</a> and follow the instructions there.</p>
              <p>If you need additional assistance or have questions, you can email NCI CBIIT Application Support at <a href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a>,
                or call <strong>240-276-5541</strong> or toll free: <strong>888-478-4423</strong>.</p>
            </div>
          </div>
        </div>
      </div>
    </div>


