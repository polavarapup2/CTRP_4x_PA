<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Manage Flagged Trials</title>
<s:head />
<link href="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.css"
	rel="stylesheet" media="all" type="text/css" />
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css"
	href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">

<style type="text/css">
fieldset>label,fieldset>input,fieldset>select {
	display: block;
}

fieldset>label {
	font-weight: bold !important;
	text-align: left;
}

fieldset>input,fieldset>select,fieldset>textarea {
	margin-bottom: 12x;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0 !important;
	margin-top: 25px;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

td.nciID {
    text-decoration: underline;
    cursor: pointer;
    color: #386ebf; 
}
td.nciID:hover {
    text-decoration: none;
}

td.comments {
    white-space: pre-wrap;
}

</style>

<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
	var successfulAddURL = '<c:url value='/protected/manageFlaggedTrialssuccessfulAdd.action'/>';
	var deleteURL = '<c:url value='/protected/manageFlaggedTrialsdelete.action'/>';
	var trialHistURL = '<c:url value='/protected/trialHistory.action'/>';

	jQuery.noConflict();
	(function($) {
		$(function() {
			var table = $('#flaggedTrials').DataTable({
				"columnDefs" : [ {
					"targets" : 4,
					"orderable" : false
				}, {
					"targets" : [ 5, 6 ],
					"orderable" : false,
					"searchable" : false
				}, {
					"targets" : 7,
					"visible" : false
				} ]
			});

			var deletedTable = $('#deletedFlaggedTrials').DataTable({
				"columnDefs" : [ {
					"targets" : 4,
					"orderable" : false
				} ]
			});

			$('#deletedFlaggedTrials tbody').on('click', 'td', function() {
				var colIdx = deletedTable.cell(this).index().column;
				var rowIdx = deletedTable.cell(this).index().row;
				if (colIdx == 0) {
					var nciID = deletedTable.cell(rowIdx, 0).data();
					window.location.replace(trialHistURL + "?nciID=" + nciID);
				}
			});

			$('#flaggedTrials tbody')
					.on(
							'click',
							'td',
							function() {
								var colIdx = table.cell(this).index().column;
								var rowIdx = table.cell(this).index().row;
								if (colIdx == 0) {
									var nciID = table.cell(rowIdx, 0).data();
									window.location.replace(trialHistURL
											+ "?nciID=" + nciID);
								} else if (colIdx == 5) {
									var recordID = table.cell(rowIdx, 7).data();
									var nciID = table.cell(rowIdx, 0).data();
									var reason = table.cell(rowIdx, 1).data();
									var comments = $('<div />').html(table.cell(rowIdx, 4).data()).text();
									$("#flag-form").dialog("open");
									$('#id').val(recordID);
									$('#nciID').val(nciID).prop("disabled",
											true);
									$('#reason').val(reason);
									$('#comments').val(comments);
									$("#flag-form").dialog("instance").onSuccessfulSave = function() {
										table.cell(rowIdx, 1).data(
												$('#reason').val()).draw();
										table.cell(rowIdx, 4).data(
												$('<div />').text($('#comments').val()).html()).draw();
										$('#msg').fadeToggle(1000).delay(5000)
												.fadeToggle(1000);
									};
								}
							});

			var dialog = $("#flag-form")
					.dialog(
							{
								autoOpen : false,
								height : 350,
								width : 420,
								modal : true,
								buttons : {
									"Save" : function() {
										if ($('#nciID').val() == '') {
											$('#err')
													.html(
															'NCI Trial ID is required.')
													.fadeToggle(1000).delay(
															3000).fadeToggle(
															1000);
											return;
										} else if (!/NCI-\d+-\d+/i.test($(
												'#nciID').val().trim())) {
											$('#err').html(
													'NCI Trial ID is invalid.')
													.fadeToggle(1000).delay(
															3000).fadeToggle(
															1000);
											return;
										}
										$("button.ui-button-text-only:first")
												.before($('#indicator').show())
										$
												.ajax(
														{
															type : "POST",
															url : $(
																	'#flaggedTrialForm')
																	.attr(
																			'action'),
															data : $(
																	'#flaggedTrialForm')
																	.serialize(),
															timeout : 30000
														})
												.always(function() {
													$('#indicator').hide();
													dialog.dialog("close");
												})
												.done(
														function() {
															if (dialog
																	.dialog("instance").onSuccessfulSave) {
																dialog
																		.dialog(
																				"instance")
																		.onSuccessfulSave();
															}
														})
												.fail(
														function(jqXHR, textStatus, errorThrown) {
															alert(jqXHR.getResponseHeader('msg'));
														});

									},
									"Cancel" : function() {
										dialog.dialog("close");
									}
								},
								open : function() {
									$('#flaggedTrialForm').find(
											"input, textarea").val("").prop(
											"disabled", false);
								}
							});

			var deleteCommentDialog = $("#comment-form").dialog(
					{
						autoOpen : false,
						height : 270,
						width : 600,
						modal : true,
						buttons : {
							"Delete" : function() {
								if ($('#deleteCommentsBox').val().trim()=='') {
									$('#commentErr').show();
									return;
								}
								$('#deleteComments').val(
										$('#deleteCommentsBox').val());
								$('#manageFlaggedTrialsForm').get(0)
										.setAttribute('action', deleteURL);
								$('#manageFlaggedTrialsForm').submit();
								deleteCommentDialog.dialog("close");
							},
							"Cancel" : function() {
								deleteCommentDialog.dialog("close");
							}
						},
						open : function() {
							$('#comment-form').find("textarea").val("");
						}
					});

			$('#deleteBtn')
					.on(
							"click",
							function() {
								var boxes = $("input[name='objectsToDelete']:checked");
								if (boxes.length == 0) {
									alert('Please select one or more flagged trials to delete.');
									return;
								}
								deleteCommentDialog.dialog("open");
							});

		});
	})(jQuery);

	function add() {
		(function($) {
			$("#flag-form").dialog("open");
			$("#flag-form").dialog("instance").onSuccessfulSave = function() {
				$('#manageFlaggedTrialsForm').get(0).setAttribute('action',
						successfulAddURL);
				$('#manageFlaggedTrialsForm').submit();
			};
		})(jQuery);

	}
</script>
</head>
<body>
	<c:set var="topic" scope="request" value="manageFlaggedTrials" />
	<h1>Manage Flagged Trials</h1>
	<div class="box">
		<pa:sucessMessage />
		<pa:failureMessage />
		<s:form name="manageFlaggedTrials" id="manageFlaggedTrialsForm"
			action="manageFlaggedTrials.action">
			<s:actionerror />
			<s:hidden id="deleteComments" name="deleteComments" />
			<h3>Flagged Trials</h3>
			<br />
			<div align="center">
				<div id="msg" class="confirm_msg" style="display: none;">
					<strong>Changes saved!</strong>
				</div>
				<display:table name="flaggedTrials" id="flaggedTrials"
					defaultsort="1" sort="list" pagesize="9999999"
					requestURI="manageFlaggedTrials.action" export="true">
					<display:setProperty name="basic.msg.empty_list"
						value="No flagged trials found." />
					<display:setProperty name="basic.empty.showtable" value="false" />
					<display:setProperty name="paging.banner.one_item_found" value="" />
					<display:setProperty name="paging.banner.all_items_found" value="" />
					<display:setProperty name="paging.banner.onepage" value="" />
					<display:setProperty name="paging.banner.item_name" value="trial" />
					<display:setProperty name="paging.banner.items_name" value="trials" />
					<display:setProperty name="export.xml" value="false" />
					<display:setProperty name="export.excel.filename"
						value="flagged_trials_all.xls" />
					<display:setProperty name="export.excel.include_header"
						value="true" />
					<display:setProperty name="export.csv.filename"
						value="flagged_trials_all.csv" />
					<display:setProperty name="export.csv.include_header" value="true" />

					<display:column escapeXml="false" title="NCI Trial ID" class="nciID"
						property="nciID" />
					<display:column escapeXml="true" title="Flag Reason"
						property="reason" />
					<display:column escapeXml="true" title="Flagged By"
						property="flaggedBy" />
					<display:column title="Flagged On" property="flaggedOn"
						format="{0,date,MM/dd/yyyy hh:mm aaa}" />
					<display:column escapeXml="true" title="Comments" class="comments"
						property="comments" />
                    <display:column title="Deleted By" media="csv excel"
                        property="deletedBy" />
                    <display:column title="Deleted On" property="deletedOn" media="csv excel"
                        format="{0,date,MM/dd/yyyy hh:mm aaa}" />
					<display:column  title="Delete Comments"  media="csv excel"
                        property="deleteComments" />
					<display:column escapeXml="false" title="Edit" media="html">
						<div align="center">
							<img style="cursor: pointer;" alt="Click here to edit"
								src="${imagePath}/ico_edit.gif">
						</div>
					</display:column>
					<display:column escapeXml="false" title="Delete" media="html">
						<div align="center">
							<s:checkbox name="objectsToDelete"
								id="objectsToDelete_%{#attr.flaggedTrials.id}"
								fieldValue="%{#attr.flaggedTrials.id}"
								value="%{#attr.flaggedTrials.id in objectsToDelete}" />
							<label style="display: none;"
								for="objectsToDelete_${flaggedTrials.id}">Check this box
								to mark row for deletion.</label>
						</div>
					</display:column>
					<display:column escapeXml="false" media="html" property="id"
						class="id_holder" />

				</display:table>
			</div>


			<div class="actionsrow">
				<del class="btnwrapper">
					<ul class="btnrow">
						<li><s:a href="javascript:void(0);" onclick="add();"
								cssClass="btn">
								<span class="btn_img"><span class="save">Add
										Flagged Trial</span></span>
							</s:a></li>
						<c:if test="${not empty flaggedTrials}">
							<li><s:a href="javascript:void(0);" id="deleteBtn"
									cssClass="btn">
									<span class="btn_img"><span class="delete">Delete</span></span>
								</s:a></li>
							<li><pa:toggleDeleteBtn /></li>
						</c:if>
					</ul>
				</del>
			</div>
			<br />
			<br />
			<h3>Deleted Flagged Trials Records</h3>
			<br />
			<div align="center">
				<display:table name="deletedFlaggedTrials" id="deletedFlaggedTrials"
					defaultsort="1" sort="list" pagesize="9999999"
					requestURI="manageFlaggedTrials.action" export="false">
					<display:setProperty name="basic.msg.empty_list"
						value="No flagged trials found." />
					<display:setProperty name="basic.empty.showtable" value="false" />
					<display:setProperty name="paging.banner.one_item_found" value="" />
					<display:setProperty name="paging.banner.all_items_found" value="" />
					<display:setProperty name="paging.banner.onepage" value="" />
					<display:setProperty name="paging.banner.item_name" value="trial" />
					<display:setProperty name="paging.banner.items_name" value="trials" />
					<display:column escapeXml="false" title="NCI Trial ID" class="nciID"
						property="nciID" />
					<display:column escapeXml="true" title="Flag Reason"
						property="reason" />
					<display:column title="Flag Created" escapeXml="false">
						<c:out escapeXml="true" value="${deletedFlaggedTrials.flaggedBy}" />
						<br />
						<fmt:formatDate value="${deletedFlaggedTrials.flaggedOn}"
							pattern="MM/dd/yyyy hh:mm aaa" />
					</display:column>
					<display:column title="Flag Deleted" escapeXml="false">
						<c:out escapeXml="true" value="${deletedFlaggedTrials.deletedBy}" />
						<br />
						<fmt:formatDate value="${deletedFlaggedTrials.deletedOn}"
							pattern="MM/dd/yyyy hh:mm aaa" />
					</display:column>

					<display:column escapeXml="false" title="Comments">
						<b>General Comments:</b>
						<br />
						<c:out escapeXml="true" value="${deletedFlaggedTrials.comments}" />
						<br />
						<b>Delete Comments:</b>
						<br />
						<c:out escapeXml="true"
							value="${not empty deletedFlaggedTrials.deleteComments?deletedFlaggedTrials.deleteComments:'N/A'}" />

					</display:column>
				</display:table>
			</div>
		</s:form>
		<div id="flag-form" title="Add/Edit a Flagged Trial"
			style="display: none;">

			<img alt="Progress Indicator" height="16" width="16" id="indicator"
				style="display: none; padding-right: 5px;"
				src="${imagePath}/loading.gif">

			<s:form name="flaggedTrialForm" id="flaggedTrialForm"
				action="manageFlaggedTrialsaddOrEdit.action">
				<p id="err"
					style="color: red; font-weight: bold; font-size: 75%; display: none;"></p>
				<fieldset>
					<input type="hidden" name="id" id="id" /> <label for="nciID"><b>NCI
							Trial ID <span class="required">*</span>
					</b></label> <input type="text" name="nciID" id="nciID"
						class="text ui-widget-content ui-corner-all"> <label
						for="reason"><b>Flag Reason<span class="required">*</span></b></label>
					<s:set name="reasonCodes"
						value="@gov.nih.nci.pa.enums.StudyFlagReasonCode@getDisplayNames()" />
					<s:select name="reason" id="reason"
						cssClass="ui-widget-content ui-corner-all" list="#{#reasonCodes[0]:#reasonCodes[0],#reasonCodes[1]:#reasonCodes[1]}" />
					<label for="comments"><b>Comments</b></label>
					<textarea name="comments" rows="4" id="comments"
						class="ui-widget-content ui-corner-all charcounter" maxlength="4000"></textarea>
				</fieldset>
			</s:form>
		</div>
		<div id="comment-form" title="Provide a Comment"
			style="display: none;">
			<s:form name="deleteCommentForm" id="deleteCommentForm"
				action="manageFlaggedTrials.action">
				<p id="commentErr"
                    style="color: red; font-weight: bold; font-size: 75%; display: none;">Comment is mandatory.</p>
				<fieldset>
					<label for="deleteCommentsBox"
						style="font-weight: normal !important;">Enter a delete
						comment/reason and confirm delete. Comments will apply to all
						selected records. Select Cancel to abort.</label>
					<textarea name="deleteCommentsBox" rows="4" id="deleteCommentsBox"
						class="ui-widget-content ui-corner-all charcounter" maxlength="4000"></textarea>
				</fieldset>
			</s:form>
		</div>
	</div>
</body>
</html>
