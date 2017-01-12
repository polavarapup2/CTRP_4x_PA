<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<head>
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
<script type="text/javascript" language="javascript">
jQuery(function() {
	jQuery('#row').dataTable( {
		"sDom": 'prfltip',
		"pagingType": "full_numbers",
		 "order": [[ 0, "asc" ]],
        "oColVis": {
            "buttonText": "Choose columns"
        },
        "oLanguage": {
            "sInfo": "Showing _START_ to _END_ of _TOTAL_",
            "sLengthMenu": "Show _MENU_",
            "oPaginate": {
                "sFirst": "<<",
                "sPrevious": "<",
                "sNext": ">",
                "sLast": ">>"
              }
        }
	});
});
</script>
<style type="text/css">

/*To not wrap header*/
thead th { 
	white-space: nowrap; 
}
/*Increase column chooser items width*/
ul.ColVis_collection {
	width:250px;
}
/*Reduce Column chooser button height*/
button.ColVis_Button {
	height : 25px;
}
</style>
</head>
<script language="javascript">
function handleUpdate(rowId) {
	document.countform.selectedRowIdentifier.value = rowId;
	document.countform.submittedCounts.value = document.getElementById("submittedCounts[" +  rowId+ "]").value;
	document.countform.action = "industrialPatientsupdate.action";
    document.countform.submit(); 
}
function handleDelete(rowId){
	document.countform.selectedRowIdentifier.value = rowId;
    var msg = 'Click OK to remove selected site(s) accrual counts. Cancel to abort.';
    var result = confirm(msg);
    if (result == true) {
        document.countform.action = "industrialPatientsdelete.action";
        document.countform.submit(); 
        return true;
    } else {
        document.countform.reset();
        return false;
    }
}
function handleSwitchUrl(spId) {
	document.forms[0].action = "patients.action?studyProtocolId=" + spId;
    document.forms[0].submit(); 	
} 
</script>
<c:set var="topic" scope="request" value="accrualcount" />
<div class="container">
	<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
	<h3 class="heading mt20">
		<span> <fmt:message key="participatingsite.accrual.count.title" />
		</span>
		<c:if
			test="${sessionScope.trialSummary.trialType.value == sessionScope.nonInterTrial && sessionScope.trialSummary.accrualSubmissionLevel.value == sessionScope.both}">
			<button type="button"
				class="btn btn-icon-alt btn-sm btn-light pull-right"
				onclick="handleSwitchUrl('<c:out value='${studyProtocolId}'/>')">
				Switch to Subject Level Accrual<i class="fa-angle-right"></i>
			</button>
		</c:if>
	</h3>
	<accrual:sucessMessage />
	<s:if test="hasActionErrors()">
		<div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong>
			<s:actionerror />.
		</div>
	</s:if>

<script>
$('#viewTrials').addClass("active");
</script>
	<s:form name="countform" cssClass="form-horizontal" role="form">
		<s:token />
		<s:hidden name="selectedRowIdentifier" />
		<s:hidden name="submittedCounts" />
		<div class="table-header-wrap">
		<display:table class="table table-striped"
			uid="row" name="studySiteCounts" export="false"
			decorator="gov.nih.nci.accrual.accweb.decorator.SubjectAccrualCountDecorator"
			requestURI="industrialPatients.action">
			<display:column titleKey="participatingsite.accrual.count.siteid" headerScope="col" property="siteId" />
			<display:column titleKey="participatingsite.accrual.count.sitename" headerScope="col" property="siteName" />
			<display:column
				titleKey="participatingsite.accrual.count.numOfSubjectEnrolled" headerScope="col">
				<s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">
					<s:textfield id="submittedCounts[%{#attr.row.studySite.id}]"
						value="%{#attr.row.accrualCount}" cssClass="form-control"
						cssStyle="width:20%" />
						
						<script>
							jQuery(function() {
							    $('#submittedCounts\\[${row.studySite.id}\\]').keypress(function (e) {
							        if (e.which == 13) {
							          handleUpdate(${row.studySite.id});
							          return false;    //<---- Add this line
							        }
							      });
							});
						</script>
						
				</s:if>
				<s:else>
					<s:property value="%{#attr.row.accrualCount}" />
				</s:else>
			</display:column>
			<display:column
				titleKey="participatingsite.accrual.count.dateLastUpdated"
				property="dateLastUpdated" headerScope="col" />
			<display:column title="Actions" headerClass="align-center"
				class="actions">
				<s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">

					<s:a href="#" data-placement="top" rel="tooltip" id="saveBtn%{#attr.row.studySite.id}"
						data-original-title="Save"
						onclick="handleUpdate(%{#attr.row.studySite.id})">
						<i class="fa-floppy-o"></i>
					</s:a>
					<s:if test="%{#attr.row.dateLastUpdated != null}">
						<s:a href="#" data-placement="top" rel="tooltip"
							data-original-title="Delete"
							onclick="handleDelete(%{#attr.row.studySite.id})">
							<i class="fa-trash-o"></i>
						</s:a>
					</s:if>
				</s:if>
			</display:column>
		</display:table>
		</div>
		<div class="align-center mb20">
			<button class="btn btn-icon btn-default" type="button"
				onclick="document.countform.reset();return false">
				<i class="fa-repeat"></i>Reset
			</button>
		</div>
	</s:form>
</div>
