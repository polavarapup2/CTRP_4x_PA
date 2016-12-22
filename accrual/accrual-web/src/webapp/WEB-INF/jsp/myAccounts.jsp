<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal fade" id="myAccount" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">My Account</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							Email Address <span class="required">*</span>
						</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.emailAddress}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							First Name <span class="required">*</span>
						</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.firstName}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">Middle
							Initial</p>
						<div class="col-xs-2">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.middleName}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							Last Name <span class="required">*</span>
						</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.lastName}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							Street Address <span class="required">*</span>
						</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.addressLine}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							City <span class="required">*</span>
						</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.city}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							State <span class="required">*</span>
						</p>
						<div class="col-xs-5">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.state}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							ZIP/Postal Code <span class="required">*</span>
						</p>
						<div class="col-xs-3">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.postalCode}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							Country <span class="required">*</span>
						</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.country}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							Phone Number <span class="required">*</span>
						</p>
						<div class="col-xs-3">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.phone}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">
							Organization Affiliation <span class="required">*</span>
						</p>
						<div class="col-xs-3">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.affiliateOrg}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">PRS
							Organization Name</p>
						<div class="col-xs-7">
							<p class="form-control-static">
								<c:out value="${registryUserWebDTO.prsOrgName}" />
							</p>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">Receive
							Email Notifications</p>
						<div class="col-xs-7">
							<c:if test="${registryUserWebDTO.enableEmails}">
                Yes 
                </c:if>
							<c:if test="${!registryUserWebDTO.enableEmails}">
                 No 
                 </c:if>
						</div>
					</div>
					<div class="form-group">
						<p style="font-weight: 700;" class="col-xs-4 control-label">Color
							Scheme</p>
						<div class="col-xs-7">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-default active"
									onClick="setActiveStyleSheet('default'); return false;">
									<input type="radio" name="options" id="option1"> Light
								</label> <label class="btn btn-default"
									onClick="setActiveStyleSheet('alternate 1'); return false;">
									<input type="radio" name="options" id="option3"> Dark
								</label>
								<label class="btn btn-default" onClick="setActiveStyleSheet('alternate 2'); return false;">
				                  <input type="radio" name="options" id="option3">
				                  Scheme 1</label>
				                <label class="btn btn-default" onClick="setActiveStyleSheet('alternate 3'); return false;">
				                  <input type="radio" name="options" id="option4">
				                  Scheme 2 </label> 
				                <label class="btn btn-default" onClick="setActiveStyleSheet('alternate 4'); return false;">
				                  <input type="radio" name="options" id="option5">
				                  Scheme 3 </label>
							</div>
							<i class="fa-question-circle help-text inside" id="popover"
								rel="popover"
								data-content="Select a color scheme to change the colors on all pages of the application"
								data-placement="top" data-trigger="hover"></i>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>