<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="">
<title><decorator:title default="NCI CTRP Accrual Site" /></title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<!-- Version: ${initParam["appTagVersion"]}, revision: ${initParam["appTagRevision"]} -->
<decorator:head />
</head>
<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/jsp/common/nciheader.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/accrualheader.jsp" />
		<c:if test="${sessionScope.disclaimerAccepted}">
			<jsp:include page="/WEB-INF/jsp/common/accrualmenu.jsp" />
		</c:if>
			<decorator:body />	
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>
