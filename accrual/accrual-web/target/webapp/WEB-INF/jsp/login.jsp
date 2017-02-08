<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
	<c:set var="topic" scope="request" value="login" />
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/loginValidation.js'/>"></script>
	<c:url value="/../registry/registerUser.action" var="createAccountUrl" />
	<body>
	<script>
	window.supressTimeoutPopup = true;
	
	$(document).keypress(function(event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
        	document.loginForm.submit();
        }
    });
	$('#wrap').addClass("login");
	</script>
	<div class="container">
		<div class="row">
			<div class="col-xs-6 intro">
				<img src="<%=request.getContextPath()%>/images/nci-logo.png" alt="NCI logo"
					class="pull-left" />
				<h4>Welcome to NCI's Clinical Trials Reporting Program</h4>
				<p>This site allows for the upload of accrual data for trials submitted to CTRP. 
				If trials are NCI-supported CTEP/DCP trials, accrual submission is handled via your normal accrual reporting, 
				and it is not necessary to submit additional accrual data via this site. If you have any questions regarding 
				accrual submission for a specific trial, please contact the Clinical Trials Reporting Office (CTRO) at
				<a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov.</a>
			     </p>
				<p>
					Want to learn more about the Reporting Program? Visit the <a
						href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main/" target="_blank">NCI Clinical Trials Reporting Program</a> website. You
					can email NCI CBIIT Application Support at <a
						href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a> if
					you have questions or need assistance.
				</p>
			</div>
			<div class="col-xs-6">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#sign-in" data-toggle="tab"><i
							class="fa-sign-in"></i>Sign In</a></li>
					<li><a href="#sign-up" data-toggle="tab"><i
							class="fa-pencil-square-o"></i>Sign Up</a></li>
					<li><a href="#forgot-password" data-toggle="tab"><i
							class="fa-key"></i>Forgot Password</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="sign-in">
						<form class="form-horizontal" role="form"
							action="j_security_check" method="post" id="loginForm"
							name="loginForm" onsubmit="return (<%=Boolean.valueOf(System.getProperty("ctrp.env.ci"))%> || validate());">
							<c:if test="${not empty param.failedLogin}">
								<p class="directions">
									<fmt:message key="errors.password.mismatch" />
								</p>
							</c:if>
							<div class="form-group">
								<label for="j_username" class="col-xs-3 control-label">Username</label>
								<div class="col-xs-7">
									<input type="text" class="form-control" id="j_username"
										name="j_username" maxlength="100" size="25"
										placeholder="Enter your username">
								</div>
							</div>
							<div class="form-group">
								<label for="j_password" class="col-xs-3 control-label">Password</label>
								<div class="col-xs-7">
									<input type="password" class="form-control" id="j_password"
										name="j_password" maxlength="100" size="25" autocomplete="off"
										placeholder="Enter your password">
								</div>
							</div>
							<c:if
								test="${!empty applicationScope['AUTHENTICATION_SOURCE_MAP']}">
								<c:choose>
									<c:when
										test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) == 1}">
										<c:forEach var="item"
											items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
											<input type="hidden" name="authenticationServiceURL"
												value="<c:out value="${item.value}"/>" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<label for="authenticationServiceURL"
												class="col-xs-3 control-label">Account Source:</label>
											<div class="col-xs-7">
												<select class="form-control" name="authenticationServiceURL"
													id="authenticationServiceURL" size="1">
													<c:forEach var="item"
														items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
														<option value="<c:out value="${item.value}" />">
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
								<button type="button" class="btn btn-icon-alt btn-primary"
									onClick="document.loginForm.submit();">
									Sign In<i class="fa-arrow-circle-right"></i>
								</button>
							</div>
						</form>
					</div>
					<div class="tab-pane fade" id="sign-up">
						<div class="tab-inside">
							<h4 class="heading">
								<span>To Create an Account</span>
							</h4>
							<p>
								If you have not yet registered, you may do so by clicking <a
									title="To Create an Account" href="${createAccountUrl}">here</a>.
							</p>
						</div>
					</div>
					<div class="tab-pane fade" id="forgot-password">
						<div class="tab-inside">
							<h4 class="heading">
								<span>Resetting Your Password</span>
							</h4>
							<p>
								If you forgot your password, please visit the NCI Password
								Station at <a href="mailto:http://password.nci.nih.gov">http://password.nci.nih.gov</a>
								and follow the instructions there.
							</p>
							<p>
								If you need additional assistance or have questions, you can
								email NCI CBIIT Application Support at <a
									href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a>,
								or call <strong>240-276-5541</strong> or toll free: <strong>888-478-4423</strong>.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
	</body>
</page:applyDecorator>