<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="milestones_in_progress" class="trial_count_panel">
	<h3>Milestones in Progress</h3>
	<div>
		<table id="milestones_in_progress_table">
			<thead>
				<tr>
					<th>Milestone (Excluding on-hold)</th>
					<th>Trial Count</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="on_hold_trials" class="trial_count_panel">
	<h3>On-Hold Trials</h3>
	<div>
		<table id="on_hold_trials_table">
			<thead>
				<tr>
					<th>On-Hold Reason</th>
					<th>Trial Count</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="trial_dist" class="trial_count_panel">
	<h3>Trial Submission Distribution</h3>
	<div>
		<table id="trial_dist_table">
			<thead>
				<tr>
					<th>Business Days Since Trial Submission</th>
					<th>Trial Count</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="abstractors_work" class="trial_count_panel">
	<h3>Abstractors Work in Progress</h3>
	<div>
		<table id="abstractors_work_table">
			<thead>
				<tr>
					<th>Abstractor (Role)</th>
					<th>Admin</th>
					<th>Scientific</th>
					<th>Admin &amp; Scientific</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th colspan="4">AD=Admin/SC=Scientific/AS=Admin
						&amp; Scientific/SU=Super Abstractor</th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>


<div id="trials_bydate" class="trials_bydate_panel">
    <h3><fmt:message key="dashboard.trialCountsByDate.tabtitle" /></h3>
    <div>
        <table id="trials_bydate_criteria_table">
            <tr>
                <td colspan="4">
                    <fmt:message key="dashboard.trialCountsByDate.header" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label cright">
                    <label for="countRangeFrom"><fmt:message key="dashboard.trialCountsByDate.from" /></label>
                </td>
                <td nowrap="nowrap">
                    <s:textfield id="countRangeFrom" name="countRangeFrom" maxlength="10" size="10" />
                </td>
                <td  scope="row" class="label cright">
                    <label for="countRangeTo"><fmt:message key="dashboard.trialCountsByDate.to" /></label>
                </td>
                <td nowrap="nowrap">
                    <s:textfield id="countRangeTo" name="countRangeTo" maxlength="10" size="10" />
                </td>
            </tr>
            <tr>
                <td colspan="2" class="cright">
                    <input id="btnDisplayCounts" type="button" value="<fmt:message key="dashboard.trialCountsByDate.btnDisplay" />" onclick="displayTrialsByDate()"  />
                </td>
                <td colspan="2" class="ccenter">
                    <input id="btnResetCounts" type="button" value="<fmt:message key="dashboard.trialCountsByDate.btnReset" />" onclick="resetTrialsByDate()"/>
                </td>
            </tr>
        </table>

        <div class="tcbd">
            <table id="trials_bydate_table">
                <thead>
                <tr>
                    <th style="height:35px;!important"><fmt:message key="dashboard.trialCountsByDate.date" /></th>
                    <th><fmt:message key="dashboard.trialCountsByDate.submitted" /></th>
                    <th><fmt:message key="dashboard.trialCountsByDate.submittedplus10" /></th>
                    <th><fmt:message key="dashboard.trialCountsByDate.expectedToComplete" /></th>
                </tr>
                </thead>
            </table>
        </div>
    </div>

</div>







