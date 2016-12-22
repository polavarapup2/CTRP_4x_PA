<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<style type="text/css">
table.dataTable tbody td {
    padding: 3px 3px;
}
</style>
<script type="text/javascript" src="${scriptPath}/js/jquery.doubleScroll.js"></script>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
var speTable;
function callOnloadFunctions(){
	speTable = jQuery('#spe').DataTable({
        "paging":   true,
        "ordering": false,
        "info":     true,
        "bFilter" :false, 
        "columnDefs": [
                       { "visible": false, "targets": 13 }
                     ]
    });
    jQuery("#resolutionDate").datepicker();
    jQuery('#speTableDiv').doubleScroll({
        contentElement: jQuery('#spe')
    });
}

function editActionTaken(id, cell) {
   var rowIdx = speTable.cell(cell).index().row;
   jQuery("#identifier").val(speTable.cell(rowIdx, 13).data());
   jQuery("#comment").val(speTable.cell(rowIdx, 5).data());
   jQuery("#errorType").val(speTable.cell(rowIdx, 6).data());
   jQuery("#cmsTicketId").val(speTable.cell(rowIdx, 7).data());
   jQuery("#actionTaken").val(speTable.cell(rowIdx, 8).data());
   jQuery("#resolutionDate").val(speTable.cell(rowIdx, 9).data());
   jQuery("#editActionTakenDialog").dialog({
       title :"Add/Edit Actions Taken Record",
       autoOpen : false,
       height : 375,
       width : 500,
       modal : true,
       buttons : {
           "Save" : function() {
               jQuery("button.ui-button-text-only:first")
               .before(jQuery('#indicatorChangeType').show());
               jQuery.ajax(
                       {
                           type : "POST",
                           url : "resultsReportingActionsTakenupdateSpeAjax.action",
                           data : jQuery('#editActionTakenForm').serialize()
                       })
               
               .done(
                       function(data) {                            
                           if(data == 'success') {
                               jQuery("#editActionTakenDialog").dialog("close");
                               speTable.cell(rowIdx, 5).data(jQuery("#comment").val());
                               speTable.cell(rowIdx, 6).data(jQuery("#errorType").val());
                               speTable.cell(rowIdx, 7).data(jQuery("#cmsTicketId").val());
                               speTable.cell(rowIdx, 8).data(jQuery("#actionTaken").val());
                               speTable.cell(rowIdx, 9).data(jQuery("#resolutionDate").val());
                               speTable.cell(rowIdx, 10).data('<c:out value="${CsmHelper.username}"/>');
                               speTable.cell(rowIdx, 11).data(jQuery.datepicker.formatDate('mm/dd/yy',new Date()));
                           }else{
                               alert("Error updating study processing error actions, please refresh the page and try again: " +data);  
                           }
                       })
               .fail(
                       function(jqXHR, textStatus, errorThrown) {
                           alert("Error updating study processing error actions, please refresh the page and try again: " + errorThrown);
                       });
               
           },
           "Cancel" : function() {
               jQuery("#editActionTakenDialog").dialog("close");
           }
       } 
    }); 
   jQuery("#editActionTakenDialog").dialog("open");
}

</SCRIPT>
 <h2><fmt:message key="actionsTaken.title" /></h2>
 <pa:sucessMessage/>
 <pa:failureMessage/>
 <div id='speTableDiv'> 
 <s:set name="studyProcessingErrors" value="studyProcessingErrors" scope="request"/>
  <display:table name="${studyProcessingErrors}" id="spe" class="data" sort="list"  pagesize="9999999"
  requestURI="resultsReportingActionsTakenview.action" export="false"
  defaultsort="1"
  decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator">
          <display:setProperty name="basic.msg.empty_list"
                      value="No XML upload error records found." />
          <display:setProperty name="basic.empty.showtable" value="false" />
          <display:setProperty name="paging.banner.one_item_found" value="" />
          <display:setProperty name="paging.banner.all_items_found" value="" />
          <display:setProperty name="paging.banner.onepage" value="" />
          <display:setProperty name="export.xml" value="false" />
    <display:column titleKey="actionsTaken.errorDate" property="errorDate"  sortable="true" format="{0,date,MM/dd/yyyy}"/>
    <display:column titleKey="actionsTaken.nciID" property="studyNCIId"  sortable="true"/>
    <display:column titleKey="actionsTaken.nctID" property="studyNCTId"  sortable="true"/>
    <display:column escapeXml="true" titleKey="actionsTaken.errorMessage" property="errorMessage"  sortable="true"/>
    <display:column titleKey="actionsTaken.recurringError"  sortable="true">
      <c:out value="${spe.recurringError ? 'Yes' : 'No'}"/>
    </display:column>
    <display:column escapeXml="true" titleKey="actionsTaken.comments" property="comment"  sortable="true"/>
    <display:column escapeXml="true" titleKey="actionsTaken.errorType" property="errorType"  sortable="true"/>
    <display:column escapeXml="true" titleKey="actionsTaken.cmsTicketID" property="cmsTicketId"  sortable="true"/>
    <display:column escapeXml="true" titleKey="actionsTaken.actionTaken" property="actionTaken"  sortable="true"/>
    <display:column titleKey="actionsTaken.resolutionDate" property="resolutionDate" format="{0,date,MM/dd/yyyy}"  sortable="true"/>
    <display:column titleKey="actionsTaken.user" property="user"  sortable="true"/>
    <display:column titleKey="actionsTaken.date" property="date" format="{0,date,MM/dd/yyyy}"  sortable="true"/>
    <display:column title="Action" class="action">
             <s:a href="javascript:void(0);" onclick="editActionTaken('%{#attr.spe.identifier}', this.parentElement)"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
  	</display:column>
  	<display:column property="identifier"  class="hidden" headerClass="hidden"/>
</display:table>
</div>
  