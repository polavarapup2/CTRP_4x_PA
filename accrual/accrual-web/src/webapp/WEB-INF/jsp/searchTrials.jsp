<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="trialsintro" />
<head>
<title><fmt:message key="accrual.search.trials.page.title" /></title>
<s:head />
<SCRIPT LANGUAGE="JavaScript">
	function resetValues() {
		document.searchTrial.reset();
		document.getElementById("officialTitle").value = "";
		document.getElementById("assignedIdentifier").value = "";
		document.getElementById("leadOrgTrialIdentifier").value = "";

	}
	function handleAction() {
		document.forms[0].action = "viewTrials.action";
		document.forms[0].submit();
	}
    function saveCodeSystem(spId, codeSystem) {
        var urlx = "viewTrialsupdateDiseaseCodeSystem.action?studyProtocolId="+spId+"&diseaseCodeSystem="+codeSystem;
        $.ajax({type: "POST",url: urlx});
        var lblid = "#termupdlbl_" + spId;
        $(lblid).html("Value changed to <b>" + codeSystem + "</b>.");
        return false;
    }
</SCRIPT>
</head>
<body>
<script>
$('#viewTrials').addClass("active");

$(function() {
	$('input[type="text"]').keypress(function (e) {
	    if (e.which == 13) {
	      handleAction();
	      return false;    //<---- Add this line
	    }
	  });
});



</script>
	<div class="container">
		<h1 class="heading">
			<span><fmt:message key="accrual.search.trials.page.header" /></span>
		</h1>
		<s:form cssClass="form-horizontal" role="form" name="searchTrial">
			<div class="form-group">
				<label for="assignedIdentifier" class="col-xs-4 control-label">
					<fmt:message key="accrual.search.trials.nciTrialNumber" />
				</label>
				<div class="col-xs-4">
					<s:textfield cssClass="form-control" id="assignedIdentifier"
						name="criteria.assignedIdentifier.value" />
				</div>
				<accrual:displayTooltip tooltip="tooltip.nci_trial_identifier" />
			</div>

			<div class="form-group">
				<label for="leadOrgTrialIdentifier" class="col-xs-4 control-label">
					<fmt:message key="accrual.search.trials.nctTrialNumber" />
				</label>
				<div class="col-xs-4">
					<s:textfield cssClass="form-control" id="leadOrgTrialIdentifier"
						name="criteria.leadOrgTrialIdentifier.value" />
				</div>
				<accrual:displayTooltip tooltip="tooltip.nct_number" />
			</div>
			<div class="form-group">
				<label for="OfficialTitle" class="col-xs-4 control-label"> <fmt:message
						key="accrual.search.trials.officialTitle" />
				</label>
				<div class="col-xs-4">
					<s:textfield cssClass="form-control" id="officialTitle" name="criteria.officialTitle.value" />
				</div>
				<accrual:displayTooltip tooltip="tooltip.official_title" />
			</div>

			<div class="form-group">
				<div class="col-xs-4 col-xs-offset-4 mt20">
					<button type="button" class="btn btn-icon btn-primary mr20"
						onclick="handleAction()">
						<i class="fa-search"></i>Search Trials
					</button>
					<button type="button" class="btn btn-icon btn-default"
						onclick="resetValues();return false">
						<i class="fa-repeat"></i>Reset
					</button>
				</div>
			</div>
		</s:form>

		<div class="clearfix"></div>
		<jsp:include page="/WEB-INF/jsp/listTrials.jsp" />
	</div>
</body>