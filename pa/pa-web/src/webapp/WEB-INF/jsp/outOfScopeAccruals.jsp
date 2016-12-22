<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Accruals Out-Of-Scope Trials</title>
<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">
	function callOnloadFunctions() {
	}

	function cancel() {
		document.forms[0].action = "outOfScopeAccruals.action";
		document.forms[0].submit();
	}

	function save() {
		document.forms[0].action = "outOfScopeAccrualssave.action";
		document.forms[0].submit();
	}
</SCRIPT>
<body>
	<c:set var="topic" scope="request" value="outofscopeaccruals" />
	<h1>Accruals Out-Of-Scope Trials</h1>
	<div class="box">
		<pa:sucessMessage />
		<pa:failureMessage />
		<s:form name="outOfScopeAccruals" id="outOfScopeAccrualsForm"
			action="outOfScopeAccruals.action">
			<s:token />
			<s:actionerror />
			<c:choose>
				<c:when test="${not empty records}">

					<div class="actionsrow">
						<del class="btnwrapper">
							<ul class="btnrow">
								<li><s:a href="javascript:void(0);" onclick="save();"
										cssClass="btn">
										<span class="btn_img"><span class="save">Save</span></span>
									</s:a></li>
								<li><s:a href="#" cssClass="btn" onclick="cancel();">
										<span class="btn_img"><span class="cancel">Cancel</span></span>
									</s:a></li>
							</ul>
						</del>
					</div>

					<display:table name="records" id="row" class="data" sort="list"
						pagesize="9999999" requestURI="outOfScopeAccruals.action"
						export="true">

						<display:setProperty name="paging.banner.item_name" value="trial" />
						<display:setProperty name="paging.banner.items_name"
							value="trials" />
						<display:setProperty name="export.xml" value="false" />
						<display:setProperty name="export.excel.filename"
							value="outOfScopeAccruals.xls" />
						<display:setProperty name="export.excel.include_header"
							value="true" />
						<display:setProperty name="export.csv.filename"
							value="outOfScopeAccruals.csv" />
						<display:setProperty name="export.csv.include_header" value="true" />

						<display:column escapeXml="true" title="Trial CTEP ID"
							property="ctepID" sortable="true" headerClass="sortable" />
						<display:column escapeXml="true" title="Failure Reason"
							property="failureReason" sortable="true" headerClass="sortable" />
						<display:column title="Submission Date/Time" sortable="true"
							headerClass="sortable" property="submissionDate"
							format="{0,date,MM/dd/yyyy hh:mm aaa}" />
						<display:column escapeXml="true" title="User"
							property="userLoginNameStripped" sortable="true"
							headerClass="sortable" />

						<display:column escapeXml="true" title="CTRO Action"
							property="action" media="excel csv" />

						<display:column escapeXml="false" title="CTRO Action" media="html"
							sortable="false" headerClass="sortable">
							<s:select name="ctroAction_%{#attr.row.id}" list="ctroActions"
								headerKey="" headerValue="" value="%{#attr.row.action}" />
						</display:column>

					</display:table>


					<div class="actionsrow">
						<del class="btnwrapper">
							<ul class="btnrow">
								<li><s:a href="javascript:void(0);" onclick="save();"
										cssClass="btn">
										<span class="btn_img"><span class="save">Save</span></span>
									</s:a></li>
								<li><s:a href="#" cssClass="btn" onclick="cancel();">
										<span class="btn_img"><span class="cancel">Cancel</span></span>
									</s:a></li>
							</ul>
						</del>
					</div>

				</c:when>
				<c:otherwise>
					<center>
						<p class="info">
							<b>No records found.</b>
						</p>
					</center>
				</c:otherwise>
			</c:choose>

		</s:form>
	</div>
</body>
</html>
