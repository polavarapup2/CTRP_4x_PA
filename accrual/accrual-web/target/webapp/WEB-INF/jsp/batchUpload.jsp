<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="batchupload" />
<head>
<title><fmt:message key="accrual.batchUpload.title" /></title>
<s:head />
</head>
<body>
<script>
$('#batchUpload').addClass("active");

$(function() {
	$( "#upload" ).on( "click", function() {
		$(".alert").hide();
	});
});

</script>
	<s:if test="hasActionErrors()">
		<div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong>
			<s:actionerror />.
		</div>
	</s:if>
	<s:if test="hasActionMessages()">
		<div class="alert alert-success"> <i class="fa-check-circle"></i><strong>Message:</strong> 
			<s:actionmessage />.
		</div>
	</s:if>
	<div class="container">
		<h1 class="heading">
			<span><fmt:message key="accrual.batchUpload.title" /></span>
		</h1>
		<div class="row">
			<div class="col-xs-5">
				<p>Click Browse and select the ZIP or TXT file that contains the
					accrual data. Then click Submit.</p>
				<hr>
				<s:form role="form" cssClass="form-horizontal mt20"
					action="batchUploaddoUpload" method="POST"
					enctype="multipart/form-data">
					<s:token />
					<div class="form-group">
						<label for="upload" class="col-xs-4 control-label left-align"><fmt:message
								key="accrual.batchUpload.label" /></label>
						<div class="col-xs-8">
							<s:file id="upload" name="upload" label="File" />
						</div>
					</div>
					<hr>
					<div class="form-group">
						<button type="button" style="margin-left: 95px;"
							class="btn btn-icon-alt btn-primary"
							onclick="document.forms[0].submit();">
							Submit<i class="fa-arrow-circle-right"></i>
						</button>
					</div>
				</s:form>
			</div>
		</div>
	</div>
</body>