<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Add Sites &mdash; Confirmation</title>
<s:head />

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js?534785924'/>"></script>

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/jquery.dataTables.min.js?534785924'/>"></script>

<script type="text/javascript" language="javascript">
	var dataTable;

	(function($) {
		$(document).ready(function() {

			$('[data-toggle="tooltip"]').tooltip({
				'placement' : 'top'
			});

			dataTable = $('#summaryTable').dataTable({
				"bPaginate" : false,
				"bLengthChange" : false,
				"bFilter" : false,
				"bSort" : true,
				"bInfo" : false,
				"bAutoWidth" : false
			});
		});
	}(jQuery));
</script>
</head>
<body>
	<!-- main content begins-->
	<div class="container">
		<c:set var="topic" scope="request" value="addsites" />

		<s:form name="addSites" action="addSites.action" id="addSitesForm"
			cssClass="form-horizontal" role="form">
			<s:token />

			<h3 class="heading">
				<span>Add Sites &mdash; Confirmation</span>
			</h3>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<p class="mb20">The following table shows participating sites
						that have been successfully created as well as sites that have
						failed to create, if any. Please verify that the results are what
						you expect. If any sites have failed to create, you can try adding
						them again.</p>
				</div>

			</div>


			<c:if test="${not empty summary}">
				<h3 class="heading">
					<span>Summary</span>
				</h3>


				<div class="table-wrapper">

					<table class="table table-bordered" id="summaryTable">
						<thead class="sortable">
							<tr>
								<th nowrap="nowrap"><a>Trial Identifier<i
										class="fa-sort"></i></a></th>
								<th><a>Site<i class="fa-sort"></i></a></th>
								<th><a>Local Site Identifier<i class="fa-sort"></i></a></th>
								<th><a>Result<i class="fa-sort"></i></a></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${summary}" var="entry" varStatus="stat">
								<tr>
									<td nowrap="nowrap"><c:out
											value="${entry.trial.nciIdentifier}" /></td>
									<td><c:out value="${entry.siteDTO.name}" /></td>
									<td><c:out
											value="${entry.siteDTO.siteLocalTrialIdentifier}" /></td>
									<td><c:out value="${entry.result}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>

			</c:if>
			<c:if test="${empty summary}">
				<div class="row">
					<div class="col-md-4 col-md-offset-4 alert alert-warning">
						<center>
							<p class="mb20">
								<b>No participating sites created.</b>
							</p>
						</center>

					</div>
				</div>
			</c:if>

			<div class="bottom no-border">
				<button type="button" class="btn btn-icon btn-primary"
					onclick="this.form.submit();">
					<i class="fa-thumbs-up"></i> Done
				</button>
			</div>
		</s:form>
	</div>


</body>
</html>
