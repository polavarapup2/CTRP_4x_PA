 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set scope="request" var="disableDefaultJQuery" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="reportCoverSheet.title"/></title>
	
	<link href="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.css"
    rel="stylesheet" media="all" type="text/css" />
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css"
    href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">
    
 <style type="text/css">
fieldset>label,fieldset>input{
    display: block;
}

fieldset>label {
    font-weight: bold !important;
    text-align: left;
}

fieldset>input:not([id="actionCompletionDate"]):not([id="actionCompletionDateChangeType"]) {
    margin-bottom: 12x;
    width: 95%;
    padding: .4em;
}

fieldset {
    padding: 0;
    border: 0 !important;
    margin-top: 25px;
}

.ui-dialog .ui-state-error {
    padding: .3em;
}

fieldset>input[id="actionCompletionDate"] {
  display:inline-block;
}
fieldset>input[id="actionCompletionDateChangeType"] {
  display:inline-block;
}
.hide_column{
    display : none;
}
.flash {
 display:none; 
 color:#00CC00;
}

</style>   
    
    <script type="text/javascript"
    src="${scriptPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
    src="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<script type="text/javascript"
    src="${scriptPath}/js/resultscoversheet/resultsCoverSheet.js"></script>
<script type="text/javascript"   src="${scriptPath}/js/resultsDocuments/resultsDocuments.js"></script>    
<!-- DataTables -->
<script type="text/javascript" charset="utf8"
    src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript" language="javascript" src="${scriptPath}/js/ajaxHelper.js"></script>
</head>

<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}


//script needed for cover sheet screens
var table;
var editFunction;
var editFunctionChangeType;
var studyRecordChangeTable;
jQuery(document).ready(function() {
    
	
	
	
	studyRecordChangeTable = initCoverSheetSectionDataTable('recordChanges');
	
	
	editFunctionChangeType = initTableEditFunction('recordChanges' ,'studyRecord' , studyRecordChangeTable,'trialViewdelete.action') ;
	
	
	
	//set url to be submitted in case of add/edit
    setCoverSheetUrls("trialViewaddOrEdit.action" ,"trialViewsuccessfulAdd.action",
    "trialViewaddOrEditRecordChange.action", "trialViewsuccessfulAddRecordChange.action",
    "trialViewsaveFinalChanges.action", "trialViewsendConverSheetEmail.action")
	
   

    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactquery.action');
	

    jQuery("#designeeAccessRevokedDate").datepicker({
        onClose : function (date, inp){
            var id = inp.id;
            onFinalSaveDateEnter(id)
        }
  });
    
    jQuery("#changesInCtrpCtGovDate").datepicker({
        onClose : function (date, inp){
            var id = inp.id;
            onFinalSaveDateEnter(id)
        }
  });
});


addCalendar("Cal1", "Select Date", "actionCompletionDate", "addDiscrepancyForm");
addCalendar("Cal2", "Select Date", "actionCompletionDateChangeType", "studyRecordChangesForm");
addCalendar("Cal3", "Select Date", "designeeAccessRevokedDate", "coverSheetForm");
addCalendar("Cal4", "Select Date", "changesInCtrpCtGovDate", "coverSheetForm");
setFormat("mm/dd/yyyy");


setDocumentPageUrl("resultsReportingDocumentinput.action?parentPage=trialView",
		"resultsReportingDocumentedit.action?parentPage=trialView","trialViewdelete.action",
				"trialViewreviewCtro.action","trialViewreviewCcct.action");


</SCRIPT>
</head>
 <body>

 <h1><fmt:message key="trialView.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
    <pa:sucessMessage/>
    <pa:failureMessage/>
 
      <s:actionerror/>
      <pa:studyUniqueToken/>
      <!-- jsp for designee & PIO study contacts -->
     <div id="studycontacts">
        <jsp:include page="/WEB-INF/jsp/administrative/reportingContacts/reportingStudyContactsContent.jsp"/>
     </div>
     <!-- jsp for trial comparision document -->
     <s:set name="requestUrl" value="trialViewquery.action" />
     <jsp:include page="/WEB-INF/jsp/administrative/reportingTrialDocuments/reportingTrialDocumentsList.jsp"/>
    
     <div id="actions">
        <jsp:include page="/WEB-INF/jsp/administrative/reportingActionsTaken/actionsTakenRecords.jsp"/>
        <jsp:include page="/WEB-INF/jsp/administrative/reportingActionsTaken/editActionsTaken.jsp"/>
     </div> 
     </br></br>
     <s:form name="coverSheetForm" id ="coverSheetForm" action="">
        <!-- jsp for cover sheet do not include anything in this form please create separate form for another views-->
        <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetStudyRecord.jsp"/>
        <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetFinalCleanup.jsp"/>          
	</s:form>
	<!-- jsp for cover sheet -->
	<jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetDisTypeDialog.jsp"/>
    <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetStudyRecordDialog.jsp"/>
    
    <s:hidden name="parentPage" id="parentPage" value="trialView" />     
    
</div>
	 
 </body>
 </html>
