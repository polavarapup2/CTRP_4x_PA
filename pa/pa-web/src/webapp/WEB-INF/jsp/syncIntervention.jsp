<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="gov.nih.nci.pa.util.ActionUtils"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.collections.CollectionUtils"%>
<%@page import="java.util.Map"%>
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="disclaimer." />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<s:set name="action" value="action" />
<title><fmt:message
		key="manageTerms.syncIntervention.page.title" /></title>
<s:head />
</head>
<body>
	<h1>
		<fmt:message key="manageTerms.syncIntervention.page.title" />
	</h1>
	<c:set var="topic" scope="request" value="syncintervention" />
	<pa:failureMessage />
	<table class="form">
		<thead>
			<tr>
				<th>Attribute</th>
				<th>Value in CTRP</th>
				<th>Value in NCIt</th>
			</tr>
		</thead>
		<tr>
			<td scope="row" width="20%"><label>NCIt Identifier:</label><span
				class="required">*</span></td>
			<td width="150px"><s:property
					value="currentIntervention.ntTermIdentifier" /></td>
			<td width="150px"><s:property
					value="intervention.ntTermIdentifier" /></td>
		</tr>
		<tr>
			<td scope="row" ><label>CDR Identifier:</label></td>
            <td><s:property value="currentIntervention.identifier" /></td>
			<td><s:property value="intervention.identifier" /></td>
		</tr>
		<tr>
			<td scope="row" ><label>Preferred Name:</label><span
				class="required">*</span></td>
            <td><s:if test="%{currentIntervention.name != intervention.name}"><font color="red"><strong></s:if><s:property value="currentIntervention.name" /><s:if test="%{currentIntervention.name != intervention.name}"></strong></font></s:if></td>
			<td><s:property value="intervention.name" /></td>
		</tr>
		  <% 
		  List<String> newAltnames  = new ArrayList<String> ();
		  newAltnames =(List<String>) request.getAttribute("newAltnames");
		
		  List<String> currentAltnames = new ArrayList<String> ();
		  currentAltnames =(List<String>) request.getAttribute("currentAltnames");
		  
		 
             
         %>
         
		<tr>
			<td scope="row" ><label>Synonyms:</label></td>
            <td><%= ActionUtils.generateListDiffStringForManageTerms(currentAltnames, newAltnames ) %></td>
            <td><%= ActionUtils.generateListDiffStringForManageTerms(newAltnames, currentAltnames ) %></td>
		</tr>
		<tr>
			<td scope="row" ><label>Cancer.gov Type:</label></td>
            <td><s:property value="currentIntervention.type" /></td>
			<td><s:property value="intervention.type" /></td>
		</tr>
		<tr>
			<td scope="row" ><label>ClinicalTrials.gov Type:</label></td>
            <td><s:property value="currentIntervention.ctGovType" /></td>
			<td><s:property value="intervention.ctGovType" /></td>
		</tr>

	</table>
	<div align="center"><span class="info">Note: 'CDR Identifier', 'Cancer.gov Type' and 'ClinicalTrials.gov Type' attributes are NOT synchronized from NCIt, their existing CTRP values shown above will be retained.</span></div>
	<div class="actionsrow">
		<del class="btnwrapper">
			<ul class="btnrow">
				<li><s:a href="manageTermssyncIntervention.action" cssClass="btn">
						<span class="btn_img"><span class="save">Sync Term</span></span>
					</s:a> 
					<s:a href="manageTermssearchIntervention.action?searchStart=true" cssClass="btn">
						<span class="btn_img"><span class="cancel">Cancel</span></span>
					</s:a></li>
			</ul>
		</del>
	</div>
</body>
</html>