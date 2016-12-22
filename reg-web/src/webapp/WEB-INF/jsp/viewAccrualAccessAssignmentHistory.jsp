<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manage.accrual.access.page.title" /></title>
<s:head />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
<script type="text/javascript" language="javascript">
jQuery(function() {
	jQuery('#row').dataTable( {
		"sDom": 'pCrfltip',
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
});
</script>
<style type="text/css">
/*To not wrap header*/
thead th { 
	white-space: nowrap; 
}
/*Increase column chooser items width*/
ul.ColVis_collection {
	width:200px;
}
/*Reduce Column chooser button height*/
button.ColVis_Button {
	height : 25px;
}
</style>
</head>
<body>
    <!-- main content begins-->
	<c:set var="topic" scope="request" value="assignmentHistory" />
	<div class="container">
	    <h1 class="heading"><span><fmt:message key="manage.accrual.access.history.header" /></span></h1>
	    <div class="table-header-wrap" id="filters">
        <reg-web:failureMessage />
        <reg-web:sucessMessage />
        <s:form name="manageAccrualAccess" 
            action="manageAccrualAccesshistoryPaging.action">
            <table class="form" width="100%">
                <c:if test="${empty model.history}">
                    <tr height="200">
                        <td align="center" class="info">
                           <fmt:message key="manage.accrual.access.history.norecords" />
                        </td>
                    </tr>               
                </c:if>
                <tr>
                    <td>
                        <display:table class="data table table-striped table-bordered" 
                            uid="row" name="model.history" export="true" requestURI="manageAccrualAccesshistoryPaging.action">
                             <display:setProperty name="export.xml" value="false"/>
						     <display:setProperty name="export.excel.filename" value="accrualAccessHistory.xls"/>
						     <display:setProperty name="export.excel.include_header" value="true"/>
						     <display:setProperty name="export.csv.filename" value="accrualAccessHistory.csv"/>
						     <display:setProperty name="export.csv.include_header" value="true"/>
                            <display:setProperty name="basic.msg.empty_list" value="" />                 
                            <display:column escapeXml="true" title="Date" property="date"  />
                            <display:column escapeXml="true" title="Assignee" property="assignee"  />
                            <display:column escapeXml="true" title="Trial ID" property="trialNciId" />
                            <display:column escapeXml="true" title="Assignment Action" property="action" />                          
                            <display:column escapeXml="true" title="Comments" property="comments"/>
                            <display:column escapeXml="true" title="Assigner" property="assigner" />
                       </display:table>
                   </td>
                </tr>
            </table>
        </s:form>
    </div>
    </div>	
</body>
</html>
