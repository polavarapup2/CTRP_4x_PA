 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%> 

<head>
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
	width:250px;
}
/*Reduce Column chooser button height*/
button.ColVis_Button {
	height : 25px;
}
</style>
</head>
<h3 class="heading mt20"><span><fmt:message key="accrual.list.trials.page.header"/></span></h3>
    <accrual:sucessMessage />
    <s:if test="hasActionErrors()"><div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong><s:actionerror />.</div></s:if>   
    
    <s:set name="accrualDiseaseValues" value="@gov.nih.nci.pa.util.PaRegistry@getAccrualDiseaseTerminologyService().getValidCodeSystems()" />
    <display:table class="table table-striped" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.accweb.decorator.SearchTrialResultDecorator"
       id="row" name="displayTagList" requestURI="viewTrials.action" export="true">
       <display:setProperty name="export.xml" value="false"/>
       <display:setProperty name="export.excel.filename" value="accrualTrialResults.xls"/>
       <display:setProperty name="export.excel.include_header" value="true"/>
       <display:setProperty name="export.csv.filename" value="accrualTrialResults.csv"/>
       <display:setProperty name="export.csv.include_header" value="true"/>
       <display:column titleKey="accrual.list.trials.protocolNumber" headerScope="col" media="html">
           <s:if test="%{#attr.row.industrial.value && #attr.row.trialType.value == 'Interventional'}">
                <s:url id="url" action="industrialPatients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:if>
           <s:elseif test="%{!(#attr.row.industrial.value) && #attr.row.trialType.value == 'Interventional'}">
                <s:url id="url" action="patients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:elseif>
           <s:elseif test="%{#attr.row.trialType.value == 'Non-interventional' && #attr.row.accrualSubmissionLevel.value == 'Subject Level'}">
                <s:url id="url" action="patients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:elseif>
           <s:elseif test="%{#attr.row.trialType.value == 'Non-interventional'}">
                <s:url id="url" action="industrialPatients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:elseif>           
           <s:a href="%{url}"><s:property value="%{#attr.row.assignedIdentifier.value}" /></s:a>
       </display:column>
       <display:column titleKey="accrual.list.trials.protocolNumber" headerScope="col" media="csv excel">
       				<s:property value="%{#attr.row.assignedIdentifier.value}"/>
       </display:column>
       <display:column escapeXml="true" titleKey="accrual.list.trials.protocolTitle" property="officialTitle" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialStatus" property="studyStatusCode" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialType" property="trialType" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.diseaseCodeSystem" headerScope="col" media="html">
           <s:if test="%{#attr.row.canChangeDiseaseCodeSystem.value}">
                <s:select id="disCodeSystem_%{#attr.row.studyProtocolIdentifier.extension}" 
                          name="disCodeSystem_%{#attr.row.studyProtocolIdentifier.extension}"
                          headerKey="" headerValue="--Select--" 
                          list="#accrualDiseaseValues" value="%{#attr.row.diseaseCodeSystem.value}"
                          onchange="saveCodeSystem('%{#attr.row.studyProtocolIdentifier.extension}',this.value);"/>
                <s:div id="termupdlbl_%{#attr.row.studyProtocolIdentifier.extension}" cssClass="text-muted"/>
           </s:if>
           <s:else>
               <s:property value="%{#attr.row.diseaseCodeSystem.value}"/>
           </s:else>
       </display:column>
       <display:column titleKey="accrual.list.trials.diseaseCodeSystem" headerScope="col" media="csv excel">
       		<s:property value="%{#attr.row.diseaseCodeSystem.value}"/>
       </display:column>
   </display:table>
