<%@ tag display-name="trialCheckInWarnings" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div id="comment-dialog" style="display: none">
	<s:hidden id="commentCommand"></s:hidden>
	<div class="body" style="">
		<div>
			<label for="comments">Enter check in comment:</label></br>
			<s:textarea id="comments" name="comments" value="" rows="5"
				maxlength="4000" cssClass="charcounter" cssStyle="width: 100%;" />
		</div>
		<br />
		<div align="center">
			<button type="button" class="btn btn-icon btn-primary"
				data-dismiss="modal" value="Save"
				onclick="$('comment-dialog').hide();saveCheckin('${commentCommand}');">
				<i class="fa fa-save"></i>Ok</button>
			<button type="button" class="btn btn-icon btn-default"
				data-dismiss="modal" value="Cancel"
				onclick="$('comment-dialog').hide();">
				<i class="fa fa-times-circle"></i>Cancel</button>
		</div>
	</div>
</div>
<div id="transitionErrors" title="Trial Status Validation"
	style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 15px 0;"></span> Status Transition
		<b style="color: red;">Errors</b> were found. Trial record cannot be
		checked-in until all Status Transition Errors have been resolved.
		Please use the Trial Status History button to review and make
		corrections.
	</p>
</div>
<div id="transitionErrorsAndWarnings" title="Trial Status Validation"
	style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 15px 0;"></span> Status Transition
		<b style="color: red;">Errors</b> and <b style="color: blue;">Warnings</b>
		were found. Trial record cannot be checked-in until all Status
		Transition Errors have been resolved. Please use the Trial Status
		History button to review and make corrections.
	</p>
</div>
<div id="transitionWarnings" title="Trial Status Validation"
	style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 15px 0;"></span> Status Transition
		<b style="color: blue;">Warnings</b> were found. Use the Trial Status
		History button to review and make corrections, or select Proceed with
		Check-in.
	</p>
</div>
<div id="pickSuperAbstractor" title="Trial Status Validation"
	style="display: none;">
	<div>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 15px 0;"></span> Status Transition
		<b style="color: red;">Errors</b> were found. Please select a Super
		Abstractor from the list below, then click Proceed with Check-in. The
		system will:
		<ol>
			<li>Check the trial in for Scientific Abstraction,</li>
			<li>Check-out the trial to the selected Super Abstractor for
				Admin Abstraction,</li>
			<li>Send an email to the Super Abstractor to correct the errors
				found.</li>
		</ol>
		<table class="form">
			<tr>
				<td scope="row" class="label" nowrap="nowrap"><label
					for="supAbsId">Super Abstractor:</label></td>
				<td><s:set name="superAbstractorsList"
						value="@gov.nih.nci.pa.service.util.CSMUserService@getInstance().superAbstractors" />
					<s:select id="supAbsId" name="supAbsId"
						list="#superAbstractorsList" headerKey="" headerValue="" /></td>
			</tr>
		</table>
	</div>
</div>
