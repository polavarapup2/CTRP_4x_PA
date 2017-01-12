<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="topic" scope="request" value="subjectsintro"/>
<c:url value="/protected/ajaxpatientsgetDeleteReasons.action" var="deleteReason"/>        
<%@ include file="/WEB-INF/jsp/nodecorate/tableTagParameters.jsp" %>
<head>
    <title><fmt:message key="patient.search.title"/></title>
    <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>

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

<script LANGUAGE="JavaScript">
var urlParameters = '<%=StringEscapeUtils.escapeJavaScript(urlParams)%>';
function handleSearch(){
    document.forms[0].action="patients.action";
    document.forms[0].submit();
}
function handleCreate(){
    document.forms[0].action="patientscreate.action";
    document.forms[0].submit();
}
function handleRetrieve(rowId){
    document.forms[0].selectedRowIdentifier.value = rowId;
    document.forms[0].action="patientsretrieve.action" + urlParameters;
    document.forms[0].submit();
}
function handleUpdate(rowId){
    document.forms[0].selectedRowIdentifier.value = rowId;
    document.forms[0].action="patientsupdate.action" + urlParameters;
    document.forms[0].submit();
}
function handleDelete(rowId){
    showPopWin('${deleteReason}', 950, 200, function deleteReason() {
        document.forms[0].selectedRowIdentifier.value = rowId;
        document.forms[0].action="patientsdelete.action" + urlParameters;
        document.forms[0].submit();
    }, 'Subject Delete Reason');
}
function setDeleteReason(reason){
    document.forms[0].deleteReason.value = reason;
} 
function handleSwitchUrl(spId) {
    document.forms[0].action = "industrialPatients.action?studyProtocolId=" + spId;
    document.forms[0].submit();     
} 
</script>
</head>
<body>
<script>
$('#viewTrials').addClass("active");
$(function() {
    $('input[type="text"]').keypress(function (e) {
        if (e.which == 13) {
        	handleSearch();
          return false;   
        }
      });
});
</script>
<div class="container">
  <div class="scroller_anchor"></div>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1 class="heading"><span><fmt:message key="patient.search.title"/></span>
<c:if test="${sessionScope.trialSummary.trialType.value == sessionScope.nonInterTrial && sessionScope.trialSummary.accrualSubmissionLevel.value == sessionScope.both}">
    <button type="button"  class="btn btn-icon-alt btn-sm btn-light pull-right" onclick="handleSwitchUrl('<c:out value='${studyProtocolId}'/>')">Switch to Summary Level Accrual<i class="fa-angle-right"></i></button>
</c:if>
</h1>
  <s:form name="listForm" cssClass="form-horizontal" role="form">
    <s:token/>
    <s:hidden name="selectedRowIdentifier"/>
    <s:hidden name="deleteReason"/>
    <div class="form-group">
           <label for="assignedIdentifier" class="col-xs-4 control-label">
              <fmt:message key="patient.assignedIdentifier"/>
           </label>
         <div class="col-xs-3">
           <s:textfield id ="assignedIdentifier" name="criteria.assignedIdentifier" cssClass="form-control" />
         </div>
      </div>
      <div class="form-group">
          <label for="organizationName" class="col-xs-4 control-label">
             <fmt:message key="patient.organizationName"/>
          </label>
         <div class="col-xs-3">
             <s:select id="organizationName" name="criteria.studySiteId" list="listOfStudySites" headerKey=""
                       listKey="ssIi" listValue="orgName" headerValue="--Select--" cssClass="form-control"/>
        </div>
      </div>
      <div class="form-group">
           <label for="birthDate" class="col-xs-4 control-label">
              <fmt:message key="patient.birthDate"/>
          </label>
         <div class="col-xs-3">
          <s:textfield id ="birthDate" name="criteria.birthDate"  cssClass="form-control" placeholder="mm/yyyy" />
          </div>
      </div>
    <div class="form-group">
        <div class="col-xs-4 col-xs-offset-4 mt20">
          <button type="button" class="btn btn-icon btn-primary mr20" onclick="handleSearch()"> <i class="fa-search"></i>Search</button>
          <s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">
          <button type="button" class="btn btn-icon btn-default" onclick="handleCreate()"><i class="fa-plus"></i>Add New Study Subject</button>
          </s:if>
        </div>
      </div>
  </s:form>


<h3 class="heading mt20"><span><fmt:message key="patient.list.header"/></span></h3>
   <accrual:sucessMessage />
   <s:if test="hasActionErrors()"><div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong><s:actionerror />.</div></s:if>
   <div class="table-header=wrap">
   <display:table class="table table-striped" summary="This table contains your Study Subject search results.  Please use column headers to sort results"
                  decorator="gov.nih.nci.accrual.accweb.decorator.SearchPatientDecorator"
                  id="row" name="displayTagList" requestURI="patients.action" export="false">
       <display:column titleKey="patient.assignedIdentifier" headerScope="col">
            <s:a href="#" onclick="handleRetrieve(%{#attr.row.identifier})"><c:out value="${row.assignedIdentifier}"/></s:a>
       </display:column>
       <display:column titleKey="patient.registrationDate" property="registrationDate" headerScope="col"/>
       <display:column escapeXml="true" titleKey="patient.organizationName" property="organizationName" headerScope="col"/>
       <display:column titleKey="patient.lastUpdateDateTime" property="dateLastUpdated" headerScope="col" />
       <s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">
	       <display:column title="Actions" headerClass="align-center" class="actions">
	            <s:a href="#" data-placement="top" rel="tooltip" data-original-title="Delete" data-toggle="modal" data-target="#delete" onclick="handleDelete(%{#attr.row.identifier})"><i class="fa-trash-o"></i></s:a>
	            <s:a href="#" data-placement="top" rel="tooltip" data-original-title="Edit" onclick="handleUpdate(%{#attr.row.identifier})"><i class="fa-pencil"></i></s:a>
	       </display:column>
       </s:if>
   </display:table>
   </div>
   </div>
</body>