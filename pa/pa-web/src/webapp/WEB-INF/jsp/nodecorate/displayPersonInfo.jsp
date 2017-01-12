<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>Person Details</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>

<script type="text/javascript" language="javascript">            
            function displayOrgDetails(orgID) {
                var width = 650;
                var height = 450;
                /*if (Prototype.Browser.IE) {
                    width = 670;
                    height = 500;                   
                }*/
                showPopWin('organizationsSearchshowDetailspopup.action?orgID='+orgID, width, height, '', 'Organization Details');               
            }            
        </script>
</head>
<body class="popupwin">
	<table class="form">
		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Prefix:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.preFix!=null?person.preFix:'N/A'" /></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>PO
					ID:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.id" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>First
					Name:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.firstName" /></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>CTEP
					ID:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.ctepId!=null?person.ctepId:'N/A'" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Middle
					Name:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.middleName!=null?person.middleName:'N/A'" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Last
					Name:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.lastName" /></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Role:</b></td>
			<td class="value" style="width: 250px"><c:forEach
					items="${person.roles}" var="role">
					<c:out value="${role}" />
					<br />
				</c:forEach> <c:if test="${empty person.roles}">N/A</c:if></td>
		</tr>

		<tr>
			<td>&nbsp;</td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Address:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.streetAddress" /> <s:if
					test="person.streetAddress2!=null">
					<br />
					<s:property value="person.streetAddress2" />
				</s:if></td>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Status:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.status" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>City:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.city" /></td>
            <td scope="row" class="label" style="width: 150px" align="right"><b>Status Date:</b></td>
            <td class="value" style="width: 250px"><s:property
                    value="person.statusDate" /></td>					
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>State:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.state" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Postal
					Code:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.zip" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Country:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.country" /></td>
		</tr>

		<tr>
			<td>&nbsp;</td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Phone:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.phone!=null?person.phone:'N/A'" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Fax:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.fax!=null?person.fax:'N/A'" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Email:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.email!=null?person.email:'N/A'" /></td>
		</tr>

		<tr>
			<td scope="row" class="label" style="width: 150px" align="right"><b>Website:</b></td>
			<td class="value" style="width: 250px"><s:property
					value="person.url!=null?person.url:'N/A'" /></td>
		</tr>

		<s:if test="!person.organizations.empty">
			<tr>
				<td colspan="4" align="left">
					<h2>Organization Affiliation</h2> <s:set name="orgs"
						value="person.organizations" scope="request" /> <display:table
						class="data" sort="list" pagesize="3" uid="row" name="orgs"
						export="false"
						requestURI="personsSearchshowDetailspopup.action">
						<display:column escapeXml="false" title="PO ID"
							headerClass="sortable">
							<a href="javascript:void(0);" onclick="displayOrgDetails(${row.identifier.extension});"><c:out
								value="${row.identifier.extension}" />
							</a>
						</display:column>
						<display:column escapeXml="true" title="Name" property="name.part[0].value"
							headerClass="sortable" />
					</display:table>
				</td>
			</tr>
		</s:if>
		<tr>
			<td colspan="4" align="center">
				<div class="actionsrow">
					<del class="btnwrapper">
						<ul class="btnrow">
							<li><a href="javascript:void(0)" class="btn"
								onclick="window.top.hidePopWin(true);"><span class="btn_img"><span
										class="logout">Close</span></span></a></li>
						</ul>
					</del>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>