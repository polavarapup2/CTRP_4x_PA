<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>Organization Details</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
</head>
<body class="popupwin">
	<table class="form">
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Name:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.name" /></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>PO
					ID:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.id" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Family
					Name:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${organization.families}" var="family">
					<c:out value="${family.value}" />
					<br />
				</c:forEach> <c:if test="${empty organization.families}">N/A</c:if></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>CTEP
					ID:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.ctepId" /> <c:if
					test="${empty organization.ctepId}">N/A</c:if></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Address:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.address1" /> <s:if
					test="organization.address2!=null">
					<br />
					<s:property value="organization.address2" />
				</s:if></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Type:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${organization.organizationTypes}" var="orgType">
					<c:out value="${orgType}" />
					<br />
				</c:forEach> <c:if test="${empty organization.organizationTypes}">N/A</c:if></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>City:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.city" /></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Status:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.status" /></td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>State:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.state" /></td>
            <td scope="row" class="label" style="width: 150px" align="right"><b>Status Date:</b></td>
            <td class="value" style="width: 250px"><s:property
                    value="organization.statusDate" /></td>					
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Postal
					Code:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.zip" /></td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Country:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="organization.country" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Phone:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${organization.contactInfo.phones}" var="val">
					<c:out value="${val}" />
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.phones}">N/A</c:if></td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Fax:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${organization.contactInfo.faxes}" var="val">
					<c:out value="${val}" />
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.faxes}">N/A</c:if></td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Email:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${organization.contactInfo.emails}" var="val">
					<a href="mailto:<c:out value="${val}" />"><c:out value="${val}" /></a>
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.emails}">N/A</c:if></td>
		</tr>
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Website:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${organization.contactInfo.websites}" var="val">
					<a href="<c:out value="${val}" />"><c:out value="${val}" /></a>
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.websites}">N/A</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<div class="actionsrow">
					<del class="btnwrapper">
						<ul class="btnrow">
							<li><a href="javascript:void(0)" class="btn"
								onclick="window.parent.hidePopWin(true);"><span class="btn_img"><span
										class="logout">Close</span></span></a></li>
						</ul>
					</del>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>