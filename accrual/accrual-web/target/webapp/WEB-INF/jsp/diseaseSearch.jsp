<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="diseasesearch" />
<head>
<title><fmt:message key="disease.search" /></title>
<s:head />
<SCRIPT LANGUAGE="JavaScript">
	function loadDiv() {
		var url = '/accrual/protected/popupdisplayList.action';
		var params = {
			searchName : $('#searchName').val(),
			searchCode : $('#searchCode').val(),
			page : "searchMenu",
			genericSearch : "true",
			searchCodeSystem : $('#searchCodeSystem').val()
		};
		$('#getDiseases').load(url, params);
	}
</SCRIPT>
</head>
<body>
	<script>
		$(document).keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
				loadDiv();
			}
		});
		
		$('#diseaseSearch').addClass("active");
		</script>
	<div class="container">
		<h1 class="heading">
			<span><fmt:message key="disease.search" /></span>
		</h1>
		<s:form id="diseases" name="diseases" cssClass="form-horizontal"
			role="form">
			<div class="form-group">
				<label for="searchName" class="col-xs-3 control-label"><fmt:message
						key="disease.name" /></label>
				<div class="col-xs-2">
					<s:textfield cssClass="form-control" id="searchName"
						name="searchName" />
				</div>
				<label for="searchCode" class="col-xs-1 control-label"><fmt:message
						key="disease.code" /></label>
				<div class="col-xs-2">
					<s:textfield cssClass="form-control" id="searchCode"
						name="searchCode" />
				</div>
			</div>
			<div class="form-group">
				<label for="searchCodeSystem" class="col-xs-3 control-label"><fmt:message
						key="disease.codeSystem" /></label>
				<div class="col-xs-2">
					<s:select cssClass="form-control" id="searchCodeSystem"
						name="searchCodeSystem" headerValue="" headerKey=""
						list="listOfDiseaseCodeSystems" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-4 col-xs-offset-3 mt20">
					<button type="button" class="btn btn-icon btn-primary"
						onclick="loadDiv()">
						<i class="fa-search"></i>Search
					</button>
				</div>
			</div>

			<div id="getDiseases" class="table-header-wrap">
				<jsp:include
					page="/WEB-INF/jsp/nodecorate/lookupdiseasesdisplayList.jsp" />
			</div>
		</s:form>
	</div>
</body>