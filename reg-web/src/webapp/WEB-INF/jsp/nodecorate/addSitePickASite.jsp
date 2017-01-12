<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:set name="spID" scope="page" value="studyProtocolId" />
<s:set name="ssID" scope="page" value="siteDTO.id" />
<c:url value="/protected/searchTrialview.action?studyProtocolId=${spID}"
	var="viewTrialUrl" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
</head>
<body class="addsite">

	<reg-web:failureMessage />
	<reg-web:sucessMessage />
	<reg-web:actionErrorsAndMessages />
	<div class="modal-body">
		<s:form name="addSiteForm" id="addSiteForm"
			action="addSitepopuppickSite" cssClass="form-horizontal" role="form">
			<s:token />
			<s:hidden name="studyProtocolId" />
			<s:hidden name="addSitesMultiple" />
			<div class="form-group">
				<label class="col-xs-4  control-label"> <fmt:message
						key="add.site.trial.nciIdentifier" /></label>
				<div class="col-xs-4">
					<c:out value="${sessionScope.NCI_ID}" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4  control-label"> <fmt:message
						key="add.site.trial.localStudyProtocolIdentifier" /></label>
				<div class="col-xs-4">
					<c:out value="${sessionScope.LEAD_ORG_ID}" />
				</div>
			</div>
			<div class="form-group">
				<fmt:message key="studyAlternateTitles.text" var="title" />
				<label class="col-xs-4  control-label"> <fmt:message
						key="add.site.trial.officialTitle" /></label>
				<div class="col-xs-4">
					<c:out value="${sessionScope.TITLE}" />
					<c:if test="${not empty trialSummary.studyAlternateTitles}">
						<a href="javascript:void(0)" title="${title}"
							onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>
					</c:if>
				</div>
			</div>
			<div class="form-group"></div>
			<div class="form-group">
				<div class="col-xs-3"></div>
				<s:if test="%{addSitesMultiple}">
				    <div class="col-xs-5">Because your organization belongs to a family, 
               		you can add to this trial any site within that family. 
               		Please select the site you would like to add below:</div>
               </s:if>
               <s:else>
               		<div class="col-xs-5">Based on the fact that your organization
					belongs to a family, you can update more than one site on this
					trial. Please select the site you would like to update below:</div>
               </s:else>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label" for="pickedSiteOrgPoId">
					Participating Site:<span class="required">*</span>
				</label>
				<div class="col-xs-4">
				<s:if test="%{addSitesMultiple}">
						<s:select id="pickedSiteOrgPoId" listKey="identifier"
						listValue="name" name="pickedSiteOrgPoId"
						list="listOfSitesUserCanAdd" value="userAffiliationPoOrgId"
						cssClass="form-control" />
                 </s:if>
                 <s:else>
					<s:select id="pickedSiteOrgPoId" listKey="identifier"
						listValue="name" name="pickedSiteOrgPoId"
						list="listOfSitesUserCanUpdate" value="userAffiliationPoOrgId"
						cssClass="form-control" />
				</s:else>
				</div>
				
			</div>
			<div class="align-center button-row">
				<button id="pickSiteBtn" type="submit"
					class="btn btn-icon btn-primary">
					<i class="fa-chevron-circle-right"></i>Next
				</button>
				<button type="button" class="btn btn-icon btn-default"
					onkeypress="window.top.hidePopWin();"
					onclick="window.top.hidePopWin();">
					<i class="fa-times-circle"></i>Cancel
				</button>
			</div>
		</s:form>
	</div>

</body>
</html>