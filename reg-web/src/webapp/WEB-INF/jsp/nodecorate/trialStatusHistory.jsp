<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<style type="text/css">
.fa-trash-o,.fa-edit {
	font-size: 150%;
	cursor: pointer;
	padding-right: 3px;
}

div.row label {
	margin-top: 5px;
}

#dialog-edit .row {
	padding-top: 5px;
}

#dialog-edit .charcounter,#dialog-delete .charcounter {
	font-size: 75%;
}

#trialStatusHistoryTable td:nth-child(3) {
	white-space: pre-wrap;
}

#trialStatusHistoryTable td:nth-child(5) img {
	position: relative;
	top: -4px;
}

div.warning,b.warning {
	color: blue;
}

div.warning:before {
	content: "WARNING: ";
}

div.error:before {
	content: "ERROR: ";
}

div.error,b.error {
	color: red;
}

#openSitesTable {
    font-size: 85%;
}
</style>
<script type="text/javascript">
	var backendUrlTemplate = '${backendUrlTemplate}';
	var calloutImg = '${pageContext.request.contextPath}/images/ico_callout.png';
	var deleteImg = '${pageContext.request.contextPath}/images/ico_delete.gif';
	var openSitesWarningRequired = new Boolean(
			'${openSitesWarningRequired == true}').valueOf();

	(function($) {
		$(function() {
			var table = $('#trialStatusHistoryTable')
					.DataTable(
							{

								bFilter : false,
								"bSort" : false,
								"columns" : [ {
									"data" : "statusDate"
								}, {
									"data" : "statusCode"
								}, {
									"data" : "comments"
								}, {
									"data" : "validationErrors"
								}, {
									"data" : "actions"
								} ],
								"columnDefs" : [ {
									"targets" : 4,
									"render" : function(data, type, r, meta) {
										var content = '<i class="fa fa-edit"></i><i class="fa fa-trash-o"></i>';
										if (r.whyStopped && r.whyStopped != '')
											content += '<img rel="popover" data-content="'+r.whyStopped+'" data-placement="top" data-trigger="hover" width=20 height=20 src="'+calloutImg+'"/>';
										return content;
									}
								} ],
								"ajax" : {
									"url" : backendUrlTemplate
											+ "getStatusHistory.action",
									"type" : "POST"
								}
							}).on('draw', function() {
						$('#runValidations').val('');
						if (table.data().length == 0) {
							$('#trialStatusHistoryContainer').hide();
						} else {
							$('#trialStatusHistoryContainer').show();
							$('img[rel=popover]').popover();
						}
					}).on('preXhr', function(e, settings, data) {
						$('#indicator').show();
						data.runValidations = $('#runValidations').val();
					}).on('xhr', function() {
						$('#indicator').hide();
					});

			$('#trialStatusHistoryTable tbody')
					.on(
							'click',
							'.fa-trash-o',
							function() {
								var uuid = table.row($(this).parents('tr'))
										.data().DT_RowId;
								$('#deleteComment').val('');
								$('#uuid').val(uuid);
								$("#dialog-delete").dialog('open');
							});

			$('#trialStatusHistoryTable tbody')
					.on(
							'click',
							'.fa-edit',
							function() {
								var uuid = table.row($(this).parents('tr'))
										.data().DT_RowId;
								var statusDate = table.row(
										$(this).parents('tr')).data().statusDate;
								var statusCode = table.row(
										$(this).parents('tr')).data().statusCode;
								var whyStopped = table.row(
										$(this).parents('tr')).data().whyStopped;
								$("#dialog-edit").dialog('open');
								$('#uuid').val(uuid);
								$('#statusDate').val(statusDate);
								$('#statusCode').val(statusCode);
								$('#whyStopped').val(
										$('<div />').html(whyStopped).text());
								$('#editComment').val('');
							});

			$("#dialog-edit")
					.dialog(
							{
								modal : true,
								autoOpen : false,
								width : 460,
								buttons : {
									"Save" : function() {
										if ($('#statusDate').val() == '') {
											alert('Please provide a valid status date.');
											return;
										}
										if ($.trim($('#editComment').val()) == '') {
											alert('Please enter a comment explaining why you are editing the status.');
											return;
										}
										if ($.trim($('#whyStopped').val()) == ''
												&& ($('#statusCode').val() == 'Temporarily Closed to Accrual'
														|| $('#statusCode')
																.val() == 'Temporarily Closed to Accrual and Intervention'
														|| $('#statusCode')
																.val() == 'Withdrawn' || $(
														'#statusCode').val() == 'Administratively Complete')) {
											alert('Please explain why the study was stopped.');
											return;
										}
										$(this).dialog("close");
										$('#indicator').show();
										$
												.ajax(
														{
															type : "POST",
															url : backendUrlTemplate
																	+ "editStatus.action",
															data : {
																statusDate : $(
																		'#statusDate')
																		.val(),
																reason : $(
																		'#whyStopped')
																		.val(),
																statusCode : $(
																		'#statusCode')
																		.val(),
																comment : $(
																		'#editComment')
																		.val(),
																uuid : $(
																		'#uuid')
																		.val()
															},
															timeout : 30000
														})
												.always(function() {
													$('#indicator').hide();
												})
												.done(
														function() {
															$('#runValidations')
																	.val('true');
															table.ajax.reload();
														})
												.fail(
														function(jqXHR,
																textStatus,
																errorThrown) {
															alert(jqXHR
																	.getResponseHeader('msg'));
														});

									},
									"Cancel" : function() {
										$(this).dialog("close");
									}
								}
							});

			$("#dialog-delete")
					.dialog(
							{
								modal : true,
								autoOpen : false,
								buttons : {
									"Delete" : function() {
										if ($.trim($('#deleteComment').val()) != '') {
											$(this).dialog("close");
											$('#indicator').show();
											$
													.ajax(
															{
																type : "POST",
																url : backendUrlTemplate
																		+ "deleteStatus.action",
																data : {
																	comment : $(
																			'#deleteComment')
																			.val(),
																	uuid : $(
																			'#uuid')
																			.val()
																},
																timeout : 30000
															})
													.always(function() {
														$('#indicator').hide();
													})
													.done(
															function() {
																$(
																		'#runValidations')
																		.val(
																				'true');
																table.ajax
																		.reload();
															})
													.fail(
															function(jqXHR,
																	textStatus,
																	errorThrown) {
																alert(jqXHR
																		.getResponseHeader('msg'));
															});

										}
									},
									"Cancel" : function() {
										$(this).dialog("close");
									}
								}
							});

			$('#addStatusBtn')
					.bind(
							'click',
							function(e) {
								var statusDate = $('#trialDTO_statusDate')
										.val();
								var statusCode = $('#trialDTO_statusCode')
										.val();
								var reason = $('#trialDTO_reason').val();

								if (statusDate == '') {
									alert('Please provide a valid status date.');
									return;
								} else if (statusCode == '') {
									alert('Please provide a status code.');
									return;
								} else if ($.trim(reason) == ''
										&& (statusCode == 'Temporarily Closed to Accrual'
												|| statusCode == 'Temporarily Closed to Accrual and Intervention'
												|| statusCode == 'Withdrawn' || statusCode == 'Administratively Complete')) {
									alert('Please explain why the study was stopped.');
									return;
								}
								$('#indicator').show();
								$
										.ajax(
												{
													type : "POST",
													url : backendUrlTemplate
															+ "addStatus.action",
													data : {
														statusDate : statusDate,
														statusCode : statusCode,
														reason : reason
													},
													timeout : 30000
												})
										.always(function() {
											$('#indicator').hide();
										})
										.done(function() {
											$('#trialDTO_statusDate').val('');
											$('#trialDTO_statusCode').val('');
											$('#trialDTO_reason').val('');
											$('#runValidations').val('true');
											table.ajax.reload();
										})
										.fail(
												function(jqXHR, textStatus,
														errorThrown) {
													alert(jqXHR
															.getResponseHeader('msg'));
												});

							});

			var reviewBtnClickHandler = $('button.review')[0].onclick;
			$('button.review')[0].onclick = null;
			$('button.review')
					.bind(
							'click',
							function(e) {
								$("#transitionErrors").hide();
								$("#transitionErrorsWarnings").hide();
								var ind = $('button.review')
										.after(
												"<img class='progress_ind' src='${pageContext.request.contextPath}/images/loading.gif' width=18 height=18 />");
								$
										.ajax(
												{
													type : "POST",
													dataType : "json",
													url : backendUrlTemplate
															+ "getValidationSummary.action",
													timeout : 30000
												})
										.always(function() {
											$('button.review + img').remove();
										})
										.done(
												function(data, textStatus,
														jqXHR) {
													if (data.errors == true) {
														var divId = "#transitionErrors"
																+ (data.warnings == true ? "Warnings"
																		: "");
														$(divId).show();
														var offset = $(divId)
																.offset();
														offset.left -= 80;
														offset.top -= 80;
														$('html, body')
																.animate(
																		{
																			scrollTop : offset.top,
																			scrollLeft : offset.left
																		});
														$('#runValidations')
																.val('true');
														table.ajax.reload();
													} else {
														displayOpenSitesWarning(
																e,
																reviewBtnClickHandler);
													}
												})
										.fail(
												function(jqXHR, textStatus,
														errorThrown) {
													alert(jqXHR
															.getResponseHeader('msg'));
												});

							});

		});
	})(jQuery);

	function displayOpenSitesWarning(e, reviewBtnClickHandler) {
		(function($) {
			if (!openSitesWarningRequired) {
				reviewBtnClickHandler(e);
			} else {
				var ind = $('button.review')
						.after(
								"<img class='progress_ind' src='${pageContext.request.contextPath}/images/loading.gif' width=18 height=18 />");
				$
						.ajax(
								{
									type : "POST",
									dataType : "json",
									url : backendUrlTemplate
											+ "mustDisplayOpenSitesWarning.action",
									timeout : 30000
								})
						.always(function() {
							$('button.review + img').remove();
						})
						.done(
								function(data, textStatus, jqXHR) {
									if (data.answer == true) {
										// OK, the trial has open sites. Display a warning and proceed with submission once acknowledged.
										if ($("#dialog-opensites").dialog(
												"instance")) {
											$("#dialog-opensites").dialog(
													"destroy");
										}
										$("#dialog-opensites").dialog({
											modal : true,
											autoOpen : false,
											width : $(window).width() * 0.7,
											buttons : {
												"Proceed" : function() {
													$(this).dialog("close");
													reviewBtnClickHandler(e);
												},
												"Cancel" : function() {
													$(this).dialog("close");
												}
											}
										});
										$("#dialog-opensites").dialog('open');
										if ($.fn.DataTable
												.isDataTable('#openSitesTable')) {
											$('#openSitesTable').DataTable().ajax
													.reload();
										} else {
											var table = $('#openSitesTable')
													.DataTable(
															{

																bFilter : false,
																"bSort" : true,
																"columns" : [
																		{
																			"data" : "poID"
																		},
																		{
																			"data" : "name"
																		},
																		{
																			"data" : "statusCode"
																		},
																		{
																			"data" : "statusDate"
																		} ],
																"ajax" : {
																	"url" : backendUrlTemplate
																			+ "getOpenSites.action",
																	"type" : "POST"
																}
															});
										}
									} else {
										reviewBtnClickHandler(e);
									}
								}).fail(
								function(jqXHR, textStatus, errorThrown) {
									reviewBtnClickHandler(e);
								});

			}
		})(jQuery);
	}
</script>

<s:set name="statusCodeValues"
	value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
<div class="accordion">
	<div class="accordion-heading">
		<a class="accordion-toggle" data-toggle="collapse"
			data-parent="#parent" href="#section8_1">Trial Status<span
			class="required">*</span></a>
	</div>
	<div id="section8_1" class="accordion-body in">
		<div class="container">
			<div class="alert-danger">
				<s:fielderror>
					<s:param>trialDTO.statusHistory</s:param>
				</s:fielderror>
			</div>
			<div class="alert-danger">
				<s:fielderror>
					<s:param>trialDTO.statusDate</s:param>
				</s:fielderror>
			</div>
			<div class="alert-danger">
				<s:fielderror>
					<s:param>trialDTO.statusCode</s:param>
				</s:fielderror>
			</div>
			<div class="alert-danger">
				<s:fielderror>
					<s:param>trialDTO.reason</s:param>
				</s:fielderror>
			</div>
			<div class="table-header-wrap">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th width="20%" nowrap="nowrap"><label
								for="trialDTO_statusDate">Status Date<i
									class="fa-question-circle help-text" id="popover" rel="popover"
									data-content="<fmt:message key="tooltip.current_trial_status_date" />"
									data-placement="top" data-trigger="hover"></i></label></th>
							<th width="30%" nowrap="nowrap"><label
								for="trialDTO_statusCode">Status<i
									class="fa-question-circle help-text" id="popover" rel="popover"
									data-content="<fmt:message key="tooltip.current_trial_status" />"
									data-placement="top" data-trigger="hover"></i></label></th>
							<th width="40%" nowrap="nowrap"><label for="trialDTO_reason">Why
									Study Stopped?<i class="fa-question-circle help-text"
									id="popover" rel="popover"
									data-content="<fmt:message key="update.trial.trialStatusReason"/>"
									data-placement="top" data-trigger="hover"></i>
							</label></th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><div id="datetimepicker"
									class="datetimepicker input-append">
									<s:textfield id="trialDTO_statusDate" value=""
										name="trialDTO.statusDate" data-format="MM/dd/yyyy"
										type="text" cssClass="form-control" placeholder="mm/dd/yyyy" />
									<span class="add-on btn-default"><i class="fa-calendar"></i></span>

								</div></td>
							<td><s:select headerKey="" headerValue="--Select--"
									id="trialDTO_statusCode" name="trialDTO.statusCode"
									list="#statusCodeValues" value=""
									onchange="displayTrialStatusDefinition('trialDTO_statusCode');"
									cssClass="form-control" />
								<div>
									<em><%@ include
											file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp"%></em>
								</div></td>
							<td align="right"><s:textarea id="trialDTO_reason" value=""
									name="trialDTO.reason" maxlength="1000" rows="1"
									cssClass="form-control charcounter" cssStyle="width: 100%;" />
								<div align="left">
									<em>Administratively Complete, Withdrawn and Temporarily
										Closed statuses only</em>
								</div></td>
							<td><button type="button" id="addStatusBtn"
									class="btn btn-icon btn-default">
									<i class="fa-plus"></i>Add Status
								</button></td>
						</tr>
					</tbody>
				</table>
			</div>
			<span class="info">Please refer to the <a
				href="https://wiki.nci.nih.gov/x/r4LWDw" target="newPage">Trial
					Status Transition Rules</a>.
			</span>
			<div style="height: 18px;" align="center">
				<img id="indicator" style="display: none;"
					src="${pageContext.request.contextPath}/images/loading.gif"
					alt="Progress Indicator." width="18" height="18" />
			</div>
			<div id="trialStatusHistoryContainer">
				<div>
					<h5>Trial Status History</h5>
				</div>
				<div id="transitionErrors" class="alert alert-danger"
					style="display: none;">
					<i class="fa fa-exclamation"></i> Status Transition <b
						class="error">Errors</b> were found. This trial cannot be
					submitted until all Status Transition Errors have been resolved.
					Please use the action icons below to make corrections.
				</div>
				<div id="transitionErrorsWarnings" class="alert alert-danger"
					style="display: none;">
					<i class="fa fa-exclamation"></i> Status Transition <b
						class="error">Errors</b> and <b class="warning">Warnings</b> were
					found. This trial cannot be submitted until all Status Transition
					Errors have been resolved. Please use the action icons below to
					make corrections.
				</div>

				<div class="table-header-wrap">
				<!-- I think this table width is not shown because 
				of jQuery version update hence manually set it to 100% -->
					<table class="table table-bordered" id="trialStatusHistoryTable" style="width:100%!important">
						<thead>
							<tr>
								<th nowrap="nowrap">Status Date</th>
								<th nowrap="nowrap">Status</th>
								<th nowrap="nowrap">Comments</th>
								<th nowrap="nowrap">Validation Messages</th>
								<th nowrap="nowrap">Actions</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="dialog-delete" title="Please provide a comment"
	style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Please provide a
		comment explaining why you are deleting this trial status:
	</p>
	<div>
		<s:textarea id="deleteComment" name="deleteComment" rows="2"
			maxlength="1000" cssClass="form-control charcounter"
			cssStyle="width: 100%;" />
	</div>
	<s:hidden name="uuid" id="uuid" />
	<s:hidden name="runValidations" id="runValidations" />
</div>
<div id="dialog-edit" title="Edit Trial Status" style="display: none;">
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-4" align="right">
				<label for="statusDate">Status Date:<span class="required">*</span></label>
			</div>
			<div class="col-xs-8">
				<div id="datetimepicker" class="datetimepicker input-append">
					<s:textfield id="statusDate" name="statusDate"
						data-format="MM/dd/yyyy" type="text" cssClass="form-control"
						placeholder="mm/dd/yyyy" />
					<span class="add-on btn-default"><i class="fa-calendar"></i></span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4" align="right">
				<label for="statusCode">Status:<span class="required">*</span></label>
			</div>
			<div class="col-xs-8">
				<s:select id="statusCode" name="statusCode" list="#statusCodeValues"
					cssClass="form-control" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4" align="right">
				<label for="whyStopped">Why Study Stopped:</label>
			</div>
			<div class="col-xs-8">
				<s:textarea id="whyStopped" name="whyStopped" rows="1"
					maxlength="1000" cssClass="form-control charcounter"
					cssStyle="width: 100%;" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4"></div>
			<div class="col-xs-8">
				<em style="font-size: 75%">Administratively Complete, Withdrawn
					and Temporarily Closed statuses only </em>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4" align="right">
				<label for="editComment">Comment:<span class="required">*</span></label>
			</div>
			<div class="col-xs-8">
				<s:textarea id="editComment" name="editComment" rows="2"
					maxlength="1000" cssClass="form-control charcounter"
					cssStyle="width: 100%;" />
			</div>
		</div>
	</div>
</div>
<div id="dialog-opensites" title="The trial has open sites"
	style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> Since you are
		closing the trial, all open sites will be closed as well as a result.
		For your information, below is a list of currently open sites that
		will be affected by this operation.

	</p>
	<div>
		<div class="table-header-wrap">
			<table class="table table-bordered" id="openSitesTable">
				<thead>
					<tr>
						<th width="20%" nowrap="nowrap">PO ID</th>
						<th width="30%" nowrap="nowrap">Name</th>
						<th width="40%" nowrap="nowrap">Status</th>
						<th>Status Date</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</div>