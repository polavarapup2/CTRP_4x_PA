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
		$('importCtGovForm').action = "importCtGov" + action + ".action";
		$('importCtGovForm').submit();
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction('query');
			return false;
		}
	}
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<fmt:message key="importctgov.title" />
	</h1>
	<c:set var="topic" scope="request" value="ctimport" />
	<div class="box" id="filters">
		<s:form id="importCtGovForm">
			<s:token name="struts.token.importctgov" />
			<s:if test="hasActionErrors()">
				<div class="error_msg">
					<s:actionerror />
				</div>
			</s:if>
			<pa:failureMessage />
			<pa:sucessMessage />

			<table class="form" style="width: 0%;">

				<tr>
					<td nowrap="nowrap" scope="row" class="label"><label
						for="nctID"><fmt:message key="studyProtocol.nctNumber" /></label></td>
					<td nowrap="nowrap"><s:textfield id="nctID" name="nctID"
							required="true" maxlength="16" cssStyle="width:294px" /></td>
					<td nowrap="nowrap" scope="row"><del class="btnwrapper">
							<ul class="btnrow">
								<li><s:a href="javascript:void(0)" cssClass="btn"
										onclick="handleAction('query')">
										<span class="btn_img"><span class="search">Search
												Studies</span></span>
									</s:a></li>
							</ul>
						</del></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2" class="info"><b>Note:</b> Any trials imported using this feature will be registered
					    as Abbreviated in CTRP system. If the trial should be classified as "Other" then please contact 
					    the Clinical Trials Reporting Office staff at <a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov</a> 
					    after importing/registering this trial in the CTRP system.</td>
				</tr>
			</table>
			<c:if test="${searchPerformed}">
				<div id="searchResults">
					<s:if test="study==null">
						<div align="center">No trial was found with the specified ClinicalTrials.gov identifier. Please check the identifier and try again.</div>
					</s:if>
					<s:if test="study!=null">
						<s:hidden id="nctIdToImport" name="nctIdToImport"
							value="%{study.nctId}" />
						<s:hidden id="studyExists" name="studyExists"
                            value="%{studyExists}" />	
						<h2>Studies on ClinicalTrials.gov</h2>
						<s:set name="studies" value="study" scope="request" />
						<display:table class="data" sort="list" pagesize="10" uid="row"
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
						
						<c:if test="${potentialMatch!=null}">
						  <br/>
						  <div class="error_msg">
                            <strong>Message.</strong> Although there is no trial with the given ClinicalTrials.gov Identifier
                            in CTRP, we found an exact match by title and category. The matching trial's NCI ID is
                            <c:out value="${potentialMatch.nciIdentifier}"></c:out>. 
                             Please click Cancel to abort the import, or
                            click Import Trial to go ahead and import this trial from ClinicalTrials.gov as new anyway.
                          </div>
						</c:if>
						
						<div class="actionsrow">
							<del class="btnwrapper">
								<ul class="btnrow">
									<li><s:a href="javascript:void(0)" cssClass="btn"
											onclick="handleAction('importTrial')">
											<span class="btn_img"><span class="search">
											${studyExists?'Import &amp; Update Trial':'Import Trial'}
											</span></span>
										</s:a> <c:url value="/protected/importCtGovexecute.action"
											var="cancelUrl" /> <s:a
											action="importCtGovexecute.action" cssClass="btn">
											<span class="btn_img"><span class="cancel">Cancel</span></span>
										</s:a></li>
								</ul>
							</del>
						</div>
					</s:if>

				</div>
			</c:if>
		</s:form>
	</div>
</body>
</html>
