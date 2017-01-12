<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="ctgov.import.logs.title" /></title>
<s:head />
<c:url value="/protected/trialHistory.action" var="trialHistoryUrl"/>
<script type="text/javascript" language="javascript">
    function displayCTGovImportLogDetails(nctId) {
        var width = 850;
        var height = 500;
        if (Prototype.Browser.IE) {
            width = 670;
            height = 500;                   
        }
        showPopWin('ctGovImportLogshowDetailspopup.action?nctId='+nctId, width, height, '', 'ClinicalTrials.Gov Import Log Details');               
    }
    
    function displayClinicalTrialDetails(nctId) {    	
        window.open('http://clinicaltrials.gov/ct2/show/'+nctId+'?term='+nctId+'&rank=1');        
    }   
    
    function displayTrialHistory(nciId) {       
        window.open('${trialHistoryUrl}?activeTab=updates&nciID='+nciId);        
    }
    
	function handleAction(action) {
		$('ctGovImportLogsForm').setAttribute("action","ctGovImportLog" + action + ".action");
		$('ctGovImportLogsForm').submit();
	}

	function resetValues() {
		$('ctGovImportLogsForm').reset();
		$("logsOnOrAfter").value = "";
		$("logsOnOrBefore").value = "";
		$("nciIdentifier").value = "";
		$("nctIdentifier").value = "";
		$("officialTitle").value = "";
		$("action").value = "";
		$("importStatus").value = "";
		$("userCreated").value = "";
		$("pendingAdminAcknowledgment").checked = false;
		$("pendingScientificAcknowledgment").checked = false;
		$("performedAdminAcknowledgment").checked = false;
		$("performedScientificAcknowledgment").checked = false;
		$("ctGovImportLogsDiv").hide();
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction('query');
			return false;
		}
	}

	Event.observe(window, 'load', function() {
		addCalendar("Cal1", "Select Date", "logsOnOrAfter",
				"ctGovImportLogsForm");
		addCalendar("Cal2", "Select Date", "logsOnOrBefore",
				"ctGovImportLogsForm");
		setWidth(90, 1, 15, 1);
		setFormat("mm/dd/yyyy");
	});
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<fmt:message key="ctgov.import.logs.title" />
	</h1>
	<c:set var="topic" scope="request" value="ctimportlog" />
	<div class="box" id="filters">
		<s:form id="ctGovImportLogsForm">
			<s:token name="struts.token.ctgovimportlogs" />
			<s:if test="hasActionErrors()">
				<div class="error_msg">
					<s:actionerror />
				</div>
			</s:if>
			<pa:failureMessage />
			<pa:sucessMessage />

			<table class="form" style="width: 0%;">
			    <tr>
			        <td scope="row" class="label">
			            <label for="officialTitle"> <fmt:message key="logs.officialTitle"/></label>
			        </td>
			        <td>
			            <s:textfield id="officialTitle" name="searchCriteria.officialTitle" maxlength="200" size="100" cssStyle="width:200px"  />
			        </td>
			        <td scope="row" class="label">
			            <label for="nciIdentifier"><fmt:message key="logs.nciIdentifier"/></label>
			            <br><span class="info">(e.g: NCI-2008-00015)</span>
			        </td>
			        <td>
			            <s:textfield id="nciIdentifier" name="searchCriteria.nciIdentifier" maxlength="200" size="100"  cssStyle="width:200px" />
			            <span class="formErrorMsg">			                
			            </span>
			        </td>                    
			    </tr>
			    <tr>
			        <td scope="row" class="label">
			            <label for="nctIdentifier"> <fmt:message key="logs.nctIdentifier"/></label>
			            <br><span class="info">(e.g: NCT00810576)</span>
                    </td>
                    <td>
                        <s:textfield id="nctIdentifier" name="searchCriteria.nctIdentifier" maxlength="200" size="100" cssStyle="width:200px"  />
                        <span class="formErrorMsg">                            
                        </span>
                    </td>
                    <td scope="row" class="label">
                        <label for="userCreated"><fmt:message key="logs.userCreated"/></label>
                    </td>
                    <td>
                        <s:textfield id="userCreated" name="searchCriteria.userCreated" maxlength="200" size="100"  cssStyle="width:200px" />
                        <span class="formErrorMsg">                            
                        </span>
                    </td>                    
                </tr>
                <tr>
                    <td scope="row" class="label">
                        <label for="action"> <fmt:message key="logs.action"/></label>
                    </td>
                    <td>
                        <s:select headerKey="" headerValue="All" id="action" name="searchCriteria.action" 
                        list="#{'New Trial':'New Trial','Update':'Update'}"  value="searchCriteria.action" cssStyle="width:206px" />
                    </td>
                    <td scope="row" class="label">
                        <label for="importStatus"> <fmt:message key="logs.importStatus"/></label>
                    </td>
                    <td>
                        <s:select headerKey="" headerValue="All" id="importStatus" name="searchCriteria.importStatus" 
                        list="#{'Success':'Success','Failure':'Failure'}"  value="searchCriteria.importStatus" cssStyle="width:206px" />
                    </td>
                </tr>
				<tr>
					<td scope="row" class="label"><label for="logsOnOrAfter"><fmt:message
								key="logs.startDate" /></label></td>
					<td nowrap="nowrap"><s:textfield id="logsOnOrAfter"
							name="logsOnOrAfter" maxlength="10" size="10" /> <a
						href="javascript:showCal('Cal1')"> <img
							src="${pageContext.request.contextPath}/images/ico_calendar.gif"
							alt="Select Date" class="calendaricon" /></a>
					<td scope="row" class="label"><label for="logsOnOrBefore"><fmt:message
								key="logs.endDate" /></label></td>

					<td nowrap="nowrap"><s:textfield id="logsOnOrBefore"
							name="logsOnOrBefore" maxlength="10" size="10" /> <a
						href="javascript:showCal('Cal2')"> <img
							src="${pageContext.request.contextPath}/images/ico_calendar.gif"
							alt="Select Date" class="calendaricon" />
					</a></td>
				</tr>
				<tr>
				    <td scope="row" class="label">
				        <label for="acknowledgments"><fmt:message key="logs.acknowledgments" /></label>
				    </td>
				</tr>
				<tr>
				    <td class="noborder">&nbsp;</td>
				    <td>
				        <table class="milestone_matrix" border="0">
				            <tr>
				                <td class="noborder">&nbsp;</td>
				                <td class="label"><fmt:message key="logs.adminAcknowledgment" /></td>
				                <td class="label"><fmt:message key="logs.scientificAcknowledgement" /></td>
				            </tr>
				            <tr>
				                <td class="label"><fmt:message key="logs.pendingAcknowledgment" /></td>
				                <td><s:checkbox name="searchCriteria.pendingAdminAcknowledgment" id="pendingAdminAcknowledgment"/></td>
				                <td><s:checkbox name="searchCriteria.pendingScientificAcknowledgment" id="pendingScientificAcknowledgment"/></td>
				            </tr>
				            <tr>
                                <td class="label"><fmt:message key="logs.performedAcknowledgment" /></td>
                                <td><s:checkbox name="searchCriteria.performedAdminAcknowledgment" id="performedAdminAcknowledgment"/></td>
                                <td><s:checkbox name="searchCriteria.performedScientificAcknowledgment" id="performedScientificAcknowledgment"/></td>
                            </tr>
				        </table>
				    </td>
				</tr>
				<tr>
					<td colspan="5" class="info"><b>Note:</b> Leave all the fields
						empty to display the entire log. In any case, the number of
						displayed records will be limited to 10,000 most recent ones of the
						time interval requested.</td>
				</tr>

			</table>
			<div class="actionsrow">
				<del class="btnwrapper">
					<ul class="btnrow">
						<li><s:a href="javascript:void(0)" cssClass="btn"
								onclick="handleAction('query')">
								<span class="btn_img"><span class="search">Display
										Log </span></span>
							</s:a> <s:a href="javascript:void(0)" cssClass="btn"
								onclick="resetValues();">
								<span class="btn_img"><span class="cancel">Reset </span></span>
							</s:a></li>
					</ul>
				</del>
			</div>

			<c:if test="${searchPerformed}">
				<div id="ctGovImportLogsDiv" align="left">
					<s:if test="allCtGovImportLogs==null || allCtGovImportLogs.empty">
						<div align="center" class="info">
							<b>No log entries found.</b>
						</div>
					</s:if>
					<s:else>
						<s:set name="logs" value="allCtGovImportLogs" scope="request" />
						<display:table class="data" sort="list" pagesize="100" uid="row"
							defaultorder="descending" defaultsort="6" name="logs"
							export="true" requestURI="ctGovImportLogquery.action">
							<display:setProperty name="basic.msg.empty_list"
								value="No log entries found." />
							<display:setProperty name="export.xml" value="false" />
							<display:setProperty name="export.excel.filename"
								value="CTGovImportLogs.xls" />
							<display:setProperty name="export.excel.include_header"
								value="true" />
							<display:setProperty name="export.csv.filename"
								value="CTGovImportLogs.csv" />
							<display:setProperty name="export.csv.include_header"
								value="true" />
							<display:column escapeXml="false" title="NCI ID" property="nciID" sortable="true" media="excel csv xml"/>
							<display:column escapeXml="false" title="NCI ID" sortable="true" media="html">
							    <a href="javascript:void(0);" onclick="displayTrialHistory('${row.nciID}');">
                                    <c:out value="${row.nciID}"/>
                                </a>
							</display:column>
							<display:column escapeXml="false" title="ClinicalTrials.gov Identifier" sortable="true" media="html">
							    <a href="javascript:void(0);" onclick="displayClinicalTrialDetails('${row.nctID}');">
							        <c:out value="${row.nctID}"/>
							    </a>
							</display:column>
							<display:column title="ClinicalTrials.gov Identifier" sortable="true" media="csv excel" property="nctID"/>
							<display:column escapeXml="true" title="Title" property="title"
								sortable="true" />
							<display:column escapeXml="false" title="Last Action" sortable="true" media="html">
							    <!-- <c:out value="${row.action}"/> -->
							    <a href="javascript:void(0);" onclick="displayCTGovImportLogDetails('${row.nctID}');">
							        <c:out value="${row.action}"/>
							    </a>							    						    
							</display:column>
							<display:column title="Action" sortable="true" media="csv excel" property="action"/>
							<display:column escapeXml="true" title="Last Action User"
								property="userCreated" sortable="true" />
							<display:column  title="Last Import Date/Time" format="{0,date,MM/dd/yyyy hh:mm aaa}"
								property="dateCreated" sortable="true" />
							<display:column escapeXml="true" title="Last Import Status"
								property="importStatus" sortable="true" />								
							<display:column escapeXml="true" title="Ack. Pending?" property="ackPending" sortable="true"/>
							<display:column escapeXml="true" title="Ack. Performed?" property="ackPerformed" sortable="true"/>	
						</display:table>
					</s:else>
				</div>
			</c:if>
		</s:form>
	</div>
</body>
</html>
