<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="disclaimer" />
<head>
<title><fmt:message key="disclaimer.page.title" /></title>

<script type="text/javascript" language="javascript">
function submitForm(btnSelected){

	 if(btnSelected == 'accept') {
        document.forms[0].submit();
    } else{
        document.forms[0].action="<%=request.getContextPath()%>/logout.action";
        document.forms[0].submit();
    } 
    
}
</script>
</head>
<body>
<script>
$('#wrap').addClass("login disclaimer");

$('body').keypress(function (e) {
    if (e.which == 13) {
      submitForm('accept');
      return false;    //<---- Add this line
    }
  });

</script>
	<s:form name="disclaimer" method="POST"
		action="disClaimerActionaccept.action">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2">
					<h3 class="heading align-center">
						<span><fmt:message key="disclaimer.page.ctrp" /></span>
					</h3>
					<%
  gov.nih.nci.pa.util.PaHibernateUtil.getHibernateHelper().openAndBindSession();
%>
					<p>
						<s:property escapeHtml="false" escapeXml="false"
							value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('Disclaimer','Accrual')" />
					</p>
					<h3 class="heading align-center">
						<span><fmt:message key="disclaimer.page.ctrp.burden.title" /></span>
					</h3>
					<small class="omb">OMB#: <s:property escapeHtml="false"
							escapeXml="false"
							value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentVersion('OMB','Accrual')" />
						EXP. DATE: <s:set var="ombExpDate"
							value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentExpiration('OMB','Accrual')" />
						<fmt:formatDate value="${ombExpDate}" dateStyle="SHORT" /></small>

					<p>
						<s:property escapeHtml="false" escapeXml="false"
							value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('OMB','Accrual')" />
					</p>
					<%
  gov.nih.nci.pa.util.PaHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
%>

					<hr>
					<s:hidden name="actionName" id="actionName" />

					<div class="align-center">
						<button type="button" class="btn btn-primary btn-icon mr20"
							data-dismiss="modal" onclick="submitForm('accept')"
							id="acceptDisclaimer">
							<i class="fa-check-circle"></i>Accept
						</button>
						<button type="button" class="btn btn-default btn-icon"
							data-dismiss="modal" onclick="submitForm('decline');"
							id="rejectDisclaimer">
							<i class="fa-times-circle"></i>Reject
						</button>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</body>
