<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="importctgov.title" /></title>
<s:head />
<script type="text/javascript" language="javascript">
	function handleAction(action) {
		$('importCtGovForm').action = "searchTrial" + action + ".action";
		$('importCtGovForm').submit();
	}
	function submitImport() {
		jQuery("#importTrial").prop("disabled" ,true);
		jQuery("#importText").text("Please wait");
		jQuery("#progressImg").show();
		handleAction('importTrial')
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction();
			return false;
		}
	}
</script>
</head>
<body>
	<!-- main content begins-->
	<h1 class="heading"><span><fmt:message key="importctgov.title" /></span></h1>
	<c:set var="topic" scope="request" value="importctgov" />
	<s:form id="importCtGovForm" cssClass="form-horizontal" role="form">
			<s:token name="struts.token.importctgov" />
			<s:hidden name="searchPerformed"></s:hidden>
			<s:if test="hasActionErrors()">
				<div class="alert alert-danger">
					<s:actionerror />
				</div>
			</s:if>
			<reg-web:failureMessage />
			<reg-web:sucessMessage />
			<c:if test="${searchPerformed}">
				<p align="justify" class="info">No match was found in
					CTRP system using the ClinicalTrials.gov identifier specified. However, a match
					has been found in ClinicalTrials.gov. Please review the following
					trial details and click 'Import Trial From ClinicalTrials.gov'
					button if you wish to proceed and register this trial in CTRP
					system. Otherwise, click 'Cancel' to stop.</p>

				<div id="searchResults">
					<s:if test="study==null">
						<p align="center" class="info">No studies found.</div>
					</s:if>
					<s:if test="study!=null">
						<s:hidden id="nctIdToImport" name="nctIdToImport"
							value="%{study.nctId}" />
						<h2 class="heading"><span>Studies on ClinicalTrials.gov</span></h2>
						<s:set name="studies" value="study" scope="request" />
						<display:table class="table table-striped table-bordered sortable" sort="list" pagesize="10" uid="row"
							name="studies" export="false"
							requestURI="importCtGovquery.action">
							<display:setProperty name="basic.msg.empty_list"
								value="No studies found." />
							<display:column escapeXml="true" title="ClinicalTrials.gov Identifier" property="nctId" />
							<display:column escapeXml="true" title="Status  "
								property="status" />
							<display:column escapeXml="false" title="Study">
								<c:out value="${row.title}" />
								<br />
								<br />
								<b>Condition(s)</b>: <c:out value="${row.conditions}" />
								<br />
								<b>Intervention(s)</b>: <c:out value="${row.interventions}" />
							</display:column>
						</display:table>
						<div class="bottom">
						      
				    		<button type="button" id="importTrial" style="" class="btn btn-icon btn-primary" onclick="submitImport();" align="Left"> <i class="fa-cloud-download"></i>
				    		 <span id="importText" >Import Trial From ClinicalTrials.gov</span>
				    		 <img id="progressImg" src="${pageContext.request.contextPath}/images/loading.gif" alt="Progress Indicator." width="16" height="16" style="display:none" /> </button>
				    		<button type="button" class="btn btn-icon btn-default" onclick="resetValues();return false"><i class="fa-times-circle"></i><s:a action="searchTrial.action">Cancel</s:a></button>
  						</div>
					</s:if>
				</div>
			</c:if>
		</s:form>
	</div>
</body>
</html>
