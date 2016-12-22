<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="manage.accrual.access.page.title" /></title>
    <s:head/>
 <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
<script type="text/javascript" language="javascript">
jQuery(function() {
	setDataTable("table[id='Externally Peer-Reviewed']");
	setDataTable('#Institutional');
	setDataTable('#Industrial');
});

function setDataTable(tableid) {
	jQuery(tableid).dataTable( {
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
	<!-- main content begins-->
	<c:set var="topic" scope="request" value="assignmentByTrial" />
	<h1 class="heading"><span><fmt:message key="manage.accrual.access.byTrial.header" /></span></h1>

	<div class="box" id="filters">
		<reg-web:failureMessage />
		<reg-web:sucessMessage />
		<s:form name="manageAccrualAccess"
			action="manageAccrualAccessbyTrialPaging.action">
			    <s:set var="byTrial" scope="request" value="%{model.byTrial}" />			    			
                <c:forEach var="entry" items="${requestScope['byTrial']}">
                	<div class="mb20">
	                	<h3 class="table-title"><c:out value="${entry.key.code}"/></h3>
	                </div>
	                <div class=table-header-wrap">
	                <display:table class="data table table-striped b1px mb40"
                            uid="${entry.key.code}" name="${entry.value}" export="true"
                            requestURI="manageAccrualAccessbyTrialPaging.action">
                             <display:setProperty name="export.xml" value="false"/>
						     <display:setProperty name="export.excel.filename" value="accrualAccessView.xls"/>
						     <display:setProperty name="export.excel.include_header" value="true"/>
						     <display:setProperty name="export.csv.filename" value="accrualAccessView.csv"/>
						     <display:setProperty name="export.csv.include_header" value="true"/>                            
                            <display:setProperty name="basic.msg.empty_list"
                                value="No Accrual Access Assignment records available." />   
                            <c:set scope="page" var="row" value="${pageScope[entry.key.code]}"/>                
                            <display:column title="NCI Trial Identifier">
                                <c:out value="${row.trialNciId}"></c:out>
                            </display:column>
                            <display:column title="Title" >
                                <c:out value="${func:abbreviate(row.trialTitle, 200)}"></c:out>
                            </display:column>
                            <display:column title="Accrual Submitter">
                                <c:forEach var="submitter" items="${row.accrualSubmitters}">
                                    <c:out value="${submitter}"></c:out><br/>
                                </c:forEach>
                            </display:column>                            
                     </display:table>
                     </div>
                </c:forEach>
                <c:if test="${empty byTrial}">
                           <fmt:message key="manage.accrual.access.byTrial.norecords" />
                </c:if>
		</s:form>
	</div>
</body>
</html>
