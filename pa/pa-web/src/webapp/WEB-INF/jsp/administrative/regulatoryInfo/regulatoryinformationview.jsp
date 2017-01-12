<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1><fmt:message key="regulatory.title" /></h1>
<c:set var="topic" scope="request" value="abstractregulatory"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<h2><fmt:message key="regulatory.title" /></h2>
   <pa:sucessMessage/>
   <pa:failureMessage/>

<table class="form">
	<!--  Trial Oversight Authority Country -->
	<tr>
		<td scope="row"  class="label"><label><fmt:message key="regulatory.oversight.country.name"/> </td>
		<td class="value"><s:label name="webDTO.trialOversgtAuthCountry" /></td>                    
	</tr>
	<!--  Trial Oversignt Authority Organization Name -->
	<tr>
		<td scope="row"  class="label"><label><fmt:message key="regulatory.oversight.auth.name"/> </td>
		<td class="value"><s:label name="webDTO.trialOversgtAuthOrgName" /></td>
	</tr>
	<!--   FDA Regulated Intervention Indicator-->
	<tr>
		<td scope="row"  class="label"><label><fmt:message key="regulatory.FDA.regulated.interv.ind"/> </td>
		<td class="value"><s:select  id="fdaindid" disabled="true" name="webDTO.fdaRegulatedInterventionIndicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>
	</tr>
	<!--   Section 801 Indicator-->
	<c:if test='${webDTO.fdaRegulatedInterventionIndicator eq true}'>
	<tr id="sec801row">
		<td scope="row" class="label"><label><fmt:message key="regulatory.section801.ind"/></td>
		<td class="value"><s:select id="sec801id" disabled="true" name="webDTO.section801Indicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>
	</tr>
	</c:if>
	<!--  IND/IDE Indicator-->
	<c:if test='${webDTO.section801Indicator eq true}'>
	<tr id="indiderow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.ideind.indicator"/></td>
		<td class="value"><s:select id="indideid" disabled="true" name="webDTO.ideTrialIndicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>		
	</tr>
	</c:if>	
	<!--   Delayed Posting Indicator-->
	<c:if test='${webDTO.ideTrialIndicator eq true}'>
	<tr id="delpostindrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.delayed.posting.ind"/></td>
		<td class="value"><s:select id="delpostindid" disabled="true" name="webDTO.delayedPostingIndicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>		
	</tr>
	</c:if>
	<!--   Data Monitoring Committee Appointed Indicator -->
	<tr id="datamonrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
		<td class="value"><s:select id="datamonid" disabled="true" name="webDTO.dataMonitoringIndicator" list="#{'false':'No', 'true':'Yes'}"/></td>		
	</tr>
</table>
	<div class="actionsrow">
		<del class="btnwrapper">
			<ul class="btnrow">
				<li><a href="regulatoryInfoquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="edit">Edit</span></span></a></li>
			</ul>	
		</del>
	</div>
</div>
</body>
</html>
