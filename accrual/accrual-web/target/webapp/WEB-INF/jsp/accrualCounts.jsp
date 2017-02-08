<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="topic" scope="request" value="accrualcounts"/>
<head>
    <title><fmt:message key="accrual.counts.title"/></title>
    <s:head/>
    <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
<script type="text/javascript" language="javascript">
jQuery(function() {
	setDataTable("#row");
});

function setDataTable(tableid) {
	jQuery(tableid).dataTable( {
		"sDom": 'prfltip',
		"pagingType": "full_numbers",
		 "order": [[ 0, "desc" ]],
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
}
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
<body>

<script>
$(function()
        {
	    var export1 = document.getElementById('export');
		if (export1 != null) {
			var export2 = export1.cloneNode(true);
			$("#pageCountPar").after(export2.firstChild);
			export1.parentNode.removeChild(export1);
		}
	});

	$('#accrualCounts').addClass("active");
</script>

 <div class="container">
    <h1 class="heading"><span><fmt:message key="accrual.counts.title"/></span></h1>
    
  <c:set var="requestURI" value="accrualCountsloopback.action" scope="request" />
  <div class="table-wrapper">
  <div class="table-responsive">
  <display:table class="table table-striped" summary="This table contains list of counts.  Please use column headers to sort results"
                  uid="row" name="${accrualCountsSession}" requestURI="${requestURI}" export="true">                                             
       <display:setProperty name="export.xml" value="false"/>
       <display:setProperty name="export.excel.filename" value="resultsAccrualCounts.xls"/>
       <display:setProperty name="export.excel.include_header" value="true"/>
       <display:setProperty name="export.csv.filename" value="resultsAccrualCounts.csv"/>
       <display:setProperty name="export.csv.include_header" value="true"/>
       <display:column titleKey="accrual.counts.nciNumber" property="nciNumber" headerScope="col"/>
       <display:column titleKey="accrual.counts.lead.organization.trialID" property="leadOrgTrialIdentifier" headerScope="col"/>
       <display:column titleKey="accrual.counts.nctTrialNumber" property="nctNumber" headerScope="col"/>
       <display:column titleKey="accrual.counts.lead.organization" property="leadOrgName" headerScope="col"/>
       <display:column titleKey="accrual.counts.site" property="affiliateOrgCount" headerScope="col"/>
       <display:column titleKey="accrual.counts.trials" property="trialCount" headerScope="col"/>
       <display:column titleKey="accrual.counts.date" property="date" format="{0,date,yyyy-MM-dd HH.mm.ss}" headerScope="col"/>
   </display:table> 
   </div>
   </div>
   </div>  
</body>