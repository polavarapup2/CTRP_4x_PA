<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="plannedMarker.details.title" />
</title>
<s:head />
<script type="text/javascript"
	src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
</head>
<body>
	<h1>
		<fmt:message key="plannedMarker.question.to.submitter" />
	</h1>
    <c:set var="topic" scope="request" value="biomarkers"/>
	<s:url id="cancelUrl" namespace="/protected" action="bioMarkers" />
	<div class="box">
	    <s:if test="hasActionErrors()">
                <div class="error_msg">
                    <s:actionerror />          
                </div>
        </s:if>
		<s:set var="submitUrl" value="'bioMarkerssendQuestionMail'" />
		<table class="form">
			<tr>
				<td colspan="2"><s:form id="plannedMarkerForm"
						action="%{#submitUrl}">
						<s:token />
						<pa:studyUniqueToken />
						<div id="plannedMarkerDetails">
							<table class="form">
								<s:hidden name="plannedMarker.id" />
								<tr>
									<td class="label"><label for="plannedMarker.nciIdentifier"	>
											<fmt:message key="plannedMarker.trial" />:</label></td>
									<td><s:hidden name="plannedMarker.nciIdentifier"
											id="plannedMarker.nciIdentifier" /> <s:property
											value="plannedMarker.nciIdentifier" /></td>
								</tr>
								<tr>
									<td class="label"><label for="plannedMarker.name">
											<fmt:message key="plannedMarker.name" />:</label></td>
									<td><s:hidden name="plannedMarker.name" id="plannedMarker.name" /> <s:property
											value="plannedMarker.name" /></td>
								</tr>
								<tr>
									<td class="label"><label
											for="plannedMarker.csmUserEmailId">
											<fmt:message key="plannedMarker.submitter" />:</label></td>
									<td><s:hidden name="plannedMarker.csmUserEmailId"
											id="plannedMarker.csmUserEmailId" /> <s:property
											value="plannedMarker.csmUserEmailId" /></td>
								</tr>
								<tr>
									<td class="label"><label for="plannedMarker.status">
											<fmt:message key="plannedMarker.status" />:</label></td>
									<td><s:hidden name="plannedMarker.status" id="plannedMarker.status" />
										<s:property value="plannedMarker.status" /></td>
								</tr>
								<tr>
									<td class="label"><label for="plannedMarker.question">
											<fmt:message key="plannedMarker.question" />
										</label></td>
									<td class="value"><s:textarea id="plannedMarker.question"
											name="plannedMarker.question" rows="4" cssStyle="width:206px" />
									</td>
								</tr>
							</table>
						</div>
						<div class="actionsrow">
							<del class="btnwrapper">
								<ul class="btnrow">
									<li>
									  <s:if test="!hasActionErrors()">
									    <s:a cssClass="btn" href="javascript:void(0)"
											onclick="document.forms[0].submit();" >
											<span class="btn_img">Submit</span>
										</s:a> 
									 </s:if>
										<s:a href="%{cancelUrl}" cssClass="btn">
											<span class="btn_img"><span class="cancel">Cancel</span>
											</span>
										</s:a></li>
								</ul>
							</del>
						</div>
					</s:form></td>
			</tr>
		</table>
	</div>
</body>
</html>
