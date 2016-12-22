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
		<fmt:message key="plannedMarker.edit.marker.name" />
	</h1>
    <c:set var="topic" scope="request" value="biomarkers"/>
	<s:url id="cancelUrl" namespace="/protected" action="bioMarkers" />
	<div class="box">
		<s:set var="submitUrl" value="'bioMarkersupdate'" />
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
									<td class="label">
									
									<label for="nciIdentifier"> <fmt:message key="plannedMarker.trial" />:</label>
                                </td>
									<td><s:hidden name="plannedMarker.nciIdentifier"
											id="nciIdentifier" /> <s:property
											value="plannedMarker.nciIdentifier" /></td>
								</tr>
								<tr>
									<td class="label">
									<label for="name"> <fmt:message key="plannedMarker.name" />:</label>
								<span class="required">*</span></td>
									<td class="value" style="width: 250px"><s:textfield
											name="plannedMarker.name" id="name" maxlength="200"
											size="200" cssStyle="width:250px" /> <span
										class="formErrorMsg"> <s:fielderror>
												<s:param>plannedMarker.name</s:param>
											</s:fielderror> </span></td>
								</tr>
							</table>
						</div>
						<div class="actionsrow">
							<del class="btnwrapper">
								<ul class="btnrow">
									<li><s:a cssClass="btn" href="javascript:void(0)"
											onclick="document.forms[0].submit();">
											<span class="btn_img">Save</span>
										</s:a> <s:a href="%{cancelUrl}" cssClass="btn">
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
