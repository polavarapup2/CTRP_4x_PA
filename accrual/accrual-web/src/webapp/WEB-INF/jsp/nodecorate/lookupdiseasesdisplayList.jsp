<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
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
<accrual:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="table table-striped"  uid="row" name="disWebList" export="false" style="font-size: 90% !important; font-family: arial,helvetica,sans-serif;">
    <display:column escapeXml="true" title="Name" property="preferredName" />
    <display:column title="Code" property="diseaseCode"  />
    <display:column title="System" property="codeSystem" />
    <display:column escapeXml="true" title="Menu Display Name" property="displayName" />
    <c:if test="${page == 'searchLookup'}">
    <display:column title="Select" headerClass="centered" class="action" >
            <button type="button" class="btn btn-icon btn-default" onclick="submitform('${row.diseaseIdentifier}', '${row.codeSystem}')">
                <i class="fa-hand-o-up"></i>Select</button>
    </display:column>
    </c:if>
</display:table>
</c:if>