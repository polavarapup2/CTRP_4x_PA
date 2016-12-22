<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="resultsdashboard.title" /></title>
<s:head />
<link href="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.css"
	rel="stylesheet" media="all" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">
<style type="text/css">
div.exportlinks {
	text-align: right;
}

li[role="treeitem"] table td:first-child {
	width: 100%;
}

li[role="treeitem"] table td:nth-child(2) {
	min-width: 10px;
	padding-left: 5px;
	vertical-align: middle;
}

i.fa-sitemap {
	cursor: default;
}

#wl_table_container {
	max-width: 930px;
	overflow-x: scroll;
}

a.nciid,td.checkedOut span {
	white-space: nowrap;
}

th.filter td,th.submissionType td {
	border: 0;
	padding: 2px;
	vertical-align: middle;
}

i.fa-filter {
	cursor: pointer;
}

#date-range-filter table td {
	padding: 5px;
}

#date-range-filter input {
	min-width: 150px;
	margin-right: 5px;
}

#submission-type-filter input {
	margin-left: 20px;
}

#submission-type-filter label {
	font-weight: normal;
}

#submission-type-filter label:after {
	content: "\A";
	white-space: pre;
}

i.fa-pencil-square-o {
	margin-left: 5px;
	cursor: pointer;
}

#abstraction-date-override input {
	min-width: 200px;
	margin-right: 5px;
}

#abstraction-date-override textarea {
	width: 100%;
	min-height: 50px;
}

span[data-overridden='true'] {
	text-decoration: underline;
}

#count_panels_container {
	margin-top: 20px;
	padding-bottom: 20px;
}

div.trial_count_panel {
	width: 45%;
}

h3.ui-accordion-header {
    font-weight: bold;
}

table.dataTable thead th {
    padding: 0px 0px 0px 0px;
    font-size: 90%;
}

table.dataTable tbody td {
    padding: 3px 3px;
}

#Total td {
    font-weight: bold;
}

#Total td a {
    text-decoration: none;
    color: black;
    cursor: auto;
}

a.count {
    color: #386EBF;
    cursor: pointer;
}

td.middle {
    vertical-align:middle;
}

td.pcdtypetable
{ 
 	vertical-align:middle;
	font-weight:bold; 
	white-space:nowrap; 
	padding:5px 5px 5px 1px !important;
}

label.pcd {
    width: 50px;
}

table.results {
    width:100%;
    padding: 5px;
}

table.searchtable {
    margin: 10px auto;
    width: 98%;
    table-layout: fixed;
}

.flash {
 display:none; 
 color:#00CC00;
}

#results_table_container {
    max-width: 1000px;   


</style>

<script type="text/javascript" src="${scriptPath}/js/select2.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.doubleScroll.js"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/showhide.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/control.tabs.js"/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
	
<script type="text/javascript"
    src="${scriptPath}/js/Chart.min.js"></script>

<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl" />
<c:url value="/protected/popupDisdisplayDiseaseWidget.action"
	var="diseaseWidgetURL" />

<script type="text/javascript" language="javascript">
var oldValue = "";

jQuery(function() {
	
	
	
	var reportDateUpdateError = function(study, attr){
		alert("Update of "+attr+ " failed for study "+ study+"." )
	}
	
	jQuery("#pcdFrom").datepicker();
	jQuery("#pcdTo").datepicker();
	initChart();
	jQuery('#results_table_container').doubleScroll({
        contentElement: jQuery('#results')
    });
	
	if ('${pcdFrom}' != '') {
      jQuery("#pcdFrom").val(jQuery.datepicker.formatDate('mm/dd/yy', new Date('${pcdFrom}')));
	}
	if ('${pcdTo}' != '') {
	  jQuery("#pcdTo").val(jQuery.datepicker.formatDate('mm/dd/yy', new Date('${pcdTo}')));
	}
	
	
	
	jQuery(".datePicker").datepicker({
		  beforeShow : function (date, inp){
			   var id = inp.id;
			   oldValue = jQuery("#"+id).val();
			   
		  },
		
		onClose : function (date, inp){
			var id = inp.id;
			var attr = id.split('_')[0];
			var studyId = id.split('_')[1];
			var newValue = jQuery("#"+id).val();
		
			//save only if there are changes
			if(oldValue!=newValue) {
			
						  		
			var ajaxReq = new Ajax.Request('resultsDashboardajaxChangeDate.action', {
            method: 'post',
            parameters: 'studyId='+studyId+'&dateAttr='+attr+'&dateValue='+date,
            onSuccess: function(response) {
                if (response.responseText != 'success'){
                   	    reportDateUpdateError(studyId, attr);
                    } else {
                   	    jQuery('#'+id+'_flash').delay(100).fadeIn('normal', function() {
                   	    jQuery(this).delay(2500).fadeOut();
                 	});
                }
            },
            onFailure: function(response) {
                	 reportDateUpdateError(studyId, attr);
            },
            onException: function(requesterObj, exceptionObj) {
                 ajaxReq.options.onFailure(null);
            },
            on0: function(response) {
                  ajaxReq.options.onFailure(response);
            }
          });	
			
			}	
		}
	});

  });
  


function handleAction(action) {
    document.forms[0].action = "resultsDashboardsearch.action";
    document.forms[0].submit();
}

function resetValues() {
	 document.forms[0].getElements().each(function(el) {
        if (el.type=='text') {
               el.clear();                
        }
        if (el.type=='checkbox' || el.type=='radio') {               
            el.checked = false;
        }
    });
    $$('option').each(function(optEl) {
        optEl.selected = false;
    });
    if ($('error_msg_div')!=null) {
        $('error_msg_div').hide();
    }
}

function initChart() {
	var chartData = [
	                 {
	                     value: ${inProcessCnt},
	                     color:"#4F81BC",
	                     highlight: "#5F91CC",
	                     label: "In Process"
	                 },
	                 {
	                     value: ${completedCnt},
	                     color: "#C0504E",
	                     highlight: "#DA6351",
	                     label: "Completed"
	                 },
	                 {
	                     value: ${notStartedCnt},
	                     color: "#A0B953",
	                     highlight: "#BFC860",
	                     label: "Not Started"
	                 },
                     {
                         value: ${issuesCnt},
                         color: "#7B65A5",
                         highlight: "#8F78B0",
                         label: "Issues"
                     }
	             ]

	var ctx = jQuery("#resultsChart").get(0).getContext("2d");
   var opts = {
		    //Number - Amount of animation steps
		    animationSteps : 60,
		    //String - Animation easing effect
		    animationEasing : "easeOutBounce",
		    //Boolean - Whether we animate the rotation of the Doughnut
		    animateRotate : true,
		    //Boolean - Whether we should show a stroke on each segment
		    segmentShowStroke : false,
		    legendTemplate : ${'"<ul class=\'<%=name.toLowerCase()%>-legend\'><% for (var i=0; i<segments.length; i++){%><li><span style=\'background-color:<%=segments[i].fillColor%>;width:15px; height:15px; float:left;\'></span><%if(segments[i].label){%>&nbsp;&nbsp;<%=segments[i].label%><%}%></li><%}%></ul>"'}
	}
	var statChart = new Chart(ctx).Pie(chartData,opts);
	jQuery("#legend").html(statChart.generateLegend());
}

function searchResults(url, studyNCIid){
	var studyIdentifier; 
	if(studyNCIid != ''){
		 displayWaitPanel();
		 var ajaxReq = new Ajax.Request('resultsDashboardajaxGetStudyStudyProtocolIdByNCIId.action?', {
             method: 'post',
             parameters: 'studyNCIId='+studyNCIid.trim().toUpperCase(),
             onSuccess: function(response) {
            	 hideWaitPanel();
            	 if(response.responseText != ''){
            		 window.location.href = url+'?studyProtocolId='+response.responseText;
            	 } else {
            		 alert("No trial with NCI ID '"+studyNCIid+"' found in PA!");
            	 }
             },
			 onFailure: function(transport) {
				 hideWaitPanel();   
				 alert("Error loading study details, please try again");
	         },
	         onException: function(requesterObj, exceptionObj) {
	             ajaxReq.options.onFailure(null);
	         }
           });
		 
	} else if (url == 'resultsReportingActionsTakenview.action') {
		window.location.href = url;
	}
}
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<fmt:message key="resultsdashboard.title" />
	</h1>
	<c:set var="topic" scope="request" value="resultsDashboard"/>
	<div class="box" id="filters">
         <s:form>
             <pa:failureMessage/>
             <table class="searchtable" >
                 <tr>
                 	<td scope="row"  class="label middle">
                 		<table>
                      		<tr>
                      			<td>
                 					<label for="trialIdentifier"> <fmt:message key="resultsdashboard.trialIdentifier"/></label>
                 					<s:textfield id="trialIdentifier" name="trialIdentifier" maxlength="200" size="20" cssStyle="width:120px"  />
                 	 			</td>	
                 			</tr>
                 			<tr>
                 				<td  scope="row" class="label middle">
                         			<label><fmt:message key="resultsdashboard.section801Indicator"/></label>
                           			<label for="section801IndicatorYes">Yes</label>
                           			<s:checkbox id="section801IndicatorYes" name="section801IndicatorYes"  onclick="jQuery('#section801IndicatorNo').prop('checked', false);" />
                           			<label for="section801IndicatorNo">No</label>
                           			<s:checkbox id="section801IndicatorNo" name="section801IndicatorNo" onclick="jQuery('#section801IndicatorYes').prop('checked', false);" />
                     			</td>
                     		</tr>	
                		</table>	
                  	</td>
                	<td  scope="row" class="label middle" >
						<table >
                       		<tr >
                     			<td  scope="row" class="label middle">
                         			<label> <fmt:message key="resultsdashboard.primaryCompletionDate"/></label>
                    			</td>
                    			<td class="middle">
                         			<table>
                            			<tr>
		                       				<td  scope="row" class="pcdtypetable" align="right" >
		                           				<label for="pcdFrom" > <fmt:message key="resultsdashboard.pcdFrom"/></label>
		                       				</td>
		                       				<td  scope="row" class="pcdtypetable">
		                           				<input type="text" id="pcdFrom" name="pcdFrom" size="15"/>
		                       				</td>
		                       				<td class="pcdtypetable" align="right" > 
		                           				<label for="pcdTo"><fmt:message key="resultsdashboard.pcdTo"/></label>
		                       				</td>
                               				<td  scope="row" class="pcdtypetable">
		                           				<input type="text" id="pcdTo" name="pcdTo" size="15"/>
		                       				</td>
	                       				</tr>
	                       				<tr>
		                       				<td class="pcdtypetable" align="right" >
		                          				<label for="pcdType"><fmt:message key="resultsdashboard.pcdType"/></label>
		                       				</td>
                               				<td  scope="row" class="pcdtypetable">
		                          				<s:select headerKey="" headerValue="Any" id="pcdType" name="pcdType" 
                                  				list="#{'Actual':'Actual','Anticipated':'Anticipated'}"  value="pcdType" cssStyle="width:100%" />
		                       				</td>		                       
		                    			</tr>
                         			</table>
                     			</td>
                  
                 		</tr>
                 	  </table>
                 	 </td> 	
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                               
                                <s:if test="%{pageFrom == 'associate'}">
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                                <span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                                </s:if>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
          <h2><fmt:message key="resultsdashboard.moreinfo"/></h2>
         <table class="results">
          <tr>
           <td align="center" width="40%" style="border-bottom: 2px solid black;border-left:  2px solid black;" >
             <h4><fmt:message key="resultsdashboard.chartHeader"/></h4><br>
             <canvas id="resultsChart" width="300" height="300"></canvas>
            </td>
           <td width="10%" style="border-bottom: 2px solid black;border-right: 2px solid black; vertical-align: middle">
             <div id="legend"></div>
           </td>
           <td  width="50%" height="100%" style="border-bottom: 2px solid black;">
            <table class="results" height="350px">
              <tr height="10%" style="border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.designee"/></h4></td>
              </tr>
              <tr height="15%" style="border-right: 2px solid black;">
                <td style="padding: 10px;">
                 <label for="designeeTrialId" > <fmt:message key="resultsdashboard.trialId"/></label><span class="required">*</span> <input type="text" id="designeeTrialId" name="designeeTrialId" size="20"/>
                 </td>
                 <td >
               <s:a id="designeeTrialIdSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingContactexecute.action', jQuery('#designeeTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
              <tr height="10%" style="border-top: 2px solid black;border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.trialCompDocs"/></h4></td>
              </tr>
              <tr  height="15%" style="border-right: 2px solid black;">
                <td style="padding: 10px;">
                 <label for="trialCompDocsTrialId" > <fmt:message key="resultsdashboard.trialId"/></label><span class="required">*</span> <input type="text" id="trialCompDocsTrialId" name="trialCompDocsTrialId" size="20"/>
                 </td>
                 <td>
                 <s:a id="trialCompDocsTrialSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingDocumentquery.action', jQuery('#trialCompDocsTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
              <tr height="10%" style="border-top: 2px solid black;border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.coverSheet"/></h4></td>
              </tr>
              <tr height="15%" style="border-right: 2px solid black;">
                <td style="padding: 10px;">
                 <label for="coverSheetTrialId" > <fmt:message key="resultsdashboard.trialId"/></label><span class="required">*</span> <input type="text" id="coverSheetTrialId" name="coverSheetTrialId" size="20"/>
                 </td>
                 <td>
                 <s:a id="coverSheetTrialSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingCoverSheetquery.action', jQuery('#coverSheetTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
              <tr height="10%" style="border-top: 2px solid black;border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.uploadErrors"/></h4></td>
              </tr>
              <tr height="15%" style="border-right: 2px solid black">
                <td style="padding: 10px;">
                 <label for="uploadErrorsTrialId" ><fmt:message key="resultsdashboard.trialId"/></label> <input type="text" id="uploadErrorsTrialId" name="uploadErrorsTrialId" size="20"/>
                 </td>
                 <td>
                 <s:a id="uploadErrorsTrialSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingActionsTakenview.action', jQuery('#uploadErrorsTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
            </table>
           </td>
         </tr>
         </table>
       </br></br>               
       <s:if test="results != null">
         <div class="line"></div>
         <h2>Search Results</h2>
         <div id="results_table_container">
         <div id="results">
             <c:set var="requestURI" value="resultsDashboardsearch.action"
                 scope="request" />
             <display:table class="data" sort="list" pagesize="30"
                 decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
                 name="results" requestURI="${requestURI}"
                 defaultsort="1"
                 export="true" uid="row">
                 <display:setProperty name="export.xml" value="false" />
                 <display:setProperty name="export.excel.filename" value="resultsReportingDashboard.xls" />
                 <display:setProperty name="export.excel.include_header" value="true" />
                 <display:setProperty name="export.csv.filename" value="resultsReportingDashboard.csv" />
                 <display:setProperty name="export.csv.include_header" value="true" />
                
                 <display:column  class="title" titleKey="studyProtocol.nciIdentifier" sortable="true" scope="row"  media="html">
                    <!-- <c:out value="${row.nciIdentifier}"/> --><a id="trialview_${row.studyProtocolId}" href="javascript:void(0)" onclick="searchResults('trialViewquery.action', '${row.studyProtocolId}')"><c:out value="${row.nciIdentifier}"/></a>
                 </display:column>
                 <display:column  class="title" titleKey="studyProtocol.nciIdentifier" sortable="true" scope="row" media="excel csv xml" >
                   <c:out value="${row.nciIdentifier}"/>
                 </display:column>
                 
                 <display:column  title="NCT ID" sortable="true" property="nctIdentifier"/>
                 <display:column  title="CTEP ID" sortable="true">
                    <c:out value ="${row.ctepId}"/>
			     </display:column>
                  <display:column  title="DCP ID" sortable="true">
                   <c:out value ="${row.dcpId}"/>
                  </display:column>
                 <display:column  title="Lead Org PO ID" sortable="true" property="leadOrganizationPOId"/>          
                 <display:column  title="Lead Organization" sortable="true" property="leadOrganizationName"/>
                 <display:column  title="Results Designee" sortable="true" property="designeeNamesList" style="width:200px;"/> 
                    
                  <display:column title="PCD" sortable="true" media="html excel csv xml">
                     <fmt:formatDate value="${row.primaryCompletionDate}" pattern="MM/dd/yyyy"/>
                 </display:column>   
                    
               
                                                  
                 <display:column title="PCD Sent to PIO" sortable="true" media="html">
                    <!-- <c:out value="${row.pcdSentToPIODate}"/> -->
                    <input id="pcdSentToPIODate_${row.studyProtocolId}" class="datePicker" size="8" value="<fmt:formatDate value="${row.pcdSentToPIODate}" pattern="MM/dd/yyyy"/>"/>
                    <div id="pcdSentToPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                <display:column title="PCD Sent to PIO" sortable="true" media="excel csv xml" >
                     <c:out value="${row.pcdSentToPIODate}"/>
                 </display:column>
                 
                 <display:column  title="PCD Confirmed" sortable="true" media="html">
                     <!-- <c:out value="${row.pcdConfirmedDate}"/> -->
                     <input id="pcdConfirmedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.pcdConfirmedDate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="pcdConfirmedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>    
                 <display:column  title="PCD Confirmed" sortable="true" media="excel csv xml">
                     <c:out value="${row.pcdConfirmedDate}"/>
                 </display:column>    
                                   
                 <display:column  title="Designee Notified" sortable="true" media="html">
                      <!-- <c:out value="${row.desgneeNotifiedDate}"/> -->
                      <input id="desgneeNotifiedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.desgneeNotifiedDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="desgneeNotifiedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Designee Notified" sortable="true" media="excel csv xml">
                      <c:out value="${row.desgneeNotifiedDate}"/>
                 </display:column>
                 
                 <display:column  title="Reporting in Process" sortable="true" media="html">
                      <!-- <c:out value="${row.reportingInProcessDate}"/> -->                 
                      <input id="reportingInProcessDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.reportingInProcessDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="reportingInProcessDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Reporting in Process" sortable="true" media="excel csv xml">
                      <c:out value="${row.reportingInProcessDate}"/>                 
                 </display:column>
                 
                 <display:column  title="3 Month Reminder" sortable="true" media="html">
                      <!-- <c:out value="${row.threeMonthReminderDate}"/> -->                 
                     <input id="threeMonthReminderDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.threeMonthReminderDate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="threeMonthReminderDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>                             
                 <display:column  title="3 Month Reminder" sortable="true" media="excel csv xml" >
                      <c:out value="${row.threeMonthReminderDate}"/>                 
                 </display:column>                             

                 <display:column  title="5 Month Reminder" sortable="true" media="html">
                      <!-- <c:out value="${row.fiveMonthReminderDate}"/> -->                 
                      <input id="fiveMonthReminderDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.fiveMonthReminderDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="fiveMonthReminderDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="5 Month Reminder" sortable="true" media="excel csv xml">
                      <c:out value="${row.fiveMonthReminderDate}"/>                 
                 </display:column>
                 
                 <display:column  title="7 Month Escalation" sortable="true" media="html">
                      <!-- <c:out value="${row.sevenMonthEscalationtoPIODate}"/> -->                 
                     <input id="sevenMonthEscalationtoPIODate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.sevenMonthEscalationtoPIODate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="sevenMonthEscalationtoPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="7 Month Escalation" sortable="true" media="excel csv xml">
                      <c:out value="${row.sevenMonthEscalationtoPIODate}"/>                 
                 </display:column>
                 
                 <display:column  title="Results Sent to PIO" sortable="true" media="html">
                      <!-- <c:out value="${row.resultsSentToPIODate}"/> -->                 
                     <input id="resultsSentToPIODate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.resultsSentToPIODate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="resultsSentToPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Results Sent to PIO" sortable="true" media="excel csv xml">
                      <c:out value="${row.resultsSentToPIODate}"/>                 
                 </display:column>

                 <display:column  title="Results Approved by PIO" sortable="true" media="html">
                      <!-- <c:out value="${row.resultsApprovedByPIODate}"/> -->                 
                      <input id="resultsApprovedByPIODate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.resultsApprovedByPIODate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="resultsApprovedByPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Results Approved by PIO" sortable="true" media="excel csv xml">
                      <c:out value="${row.resultsApprovedByPIODate}"/>                 
                 </display:column>

                 <display:column  title="CTRO Trial Comparison Review" sortable="true" media="html">
                     <c:out value="${row.ctroUserName}"/>                 	
                     <fmt:formatDate value="${row.ctroUserCreatedDate}" pattern="MM/dd/yyyy"/>
                 </display:column>
                 <display:column  title="CTRO Trial Comparison Review" sortable="true" media="excel csv xml">
                     <c:out value="${row.ctroUserName}"/> <fmt:formatDate value="${row.ctroUserCreatedDate}" pattern="MM/dd/yyyy"/>
                 </display:column>
                
                 <display:column  title="Trial Comparison Approval" sortable="true" media="html">
                 	  <c:out value="${row.ctroUserName}"/>
                 	  <fmt:formatDate value="${row.ctroUserCreatedDate}" pattern="MM/dd/yyyy"/>
                 </display:column>
                 <display:column  title="Trial Comparison Approval" sortable="true" media="excel csv xml">
                      <c:out value="${row.ctroUserName}"/> <fmt:formatDate value="${row.ctroUserCreatedDate}" pattern="MM/dd/yyyy"/>
                 </display:column>
                 
                  <display:column  title="CCCT Trial Comparison Review" sortable="true" media="html">
                    <c:out value="${row.ccctUserName}"/>
                    <fmt:formatDate value="${row.ccctUserCreatedDate}" pattern="MM/dd/yyyy"/>
                 </display:column>
                 <display:column  title="CCCT Trial Comparison Review" sortable="true" media="excel csv xml">
                    <c:out value="${row.ccctUserName}"/> <fmt:formatDate value="${row.ccctUserCreatedDate}" pattern="MM/dd/yyyy"/>
                 </display:column>
                 
                 <display:column  title="PRS Release Date" sortable="true" media="html">
                      <!-- <c:out value="${row.prsReleaseDate}"/> -->
                      <input id="prsReleaseDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.prsReleaseDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="prsReleaseDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="PRS Release Date" sortable="true" media="excel csv xml">
                      <c:out value="${row.prsReleaseDate}"/>
                 </display:column>

                 <display:column  title="QA Comments Returned Date" sortable="true" media="html">
                      <!-- <c:out value="${row.qaCommentsReturnedDate}"/> -->                 
                      <input id="qaCommentsReturnedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.qaCommentsReturnedDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="qaCommentsReturnedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="QA Comments Returned Date" sortable="true" media="excel csv xml">
                      <c:out value="${row.qaCommentsReturnedDate}"/>                 
                 </display:column>
                 
                 <display:column  title="Trial Results Published Date" sortable="true" media="html">
                      <!-- <c:out value="${row.trialPublishedDate}"/> -->                 
                      <input id="trialPublishedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.trialPublishedDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="trialPublishedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Trial Results Published Date" sortable="true" media="excel csv xml">
                      <c:out value="${row.trialPublishedDate}"/>                 
                 </display:column>
             </display:table>
         </div>
         </div>
       </s:if>
       
    </body>
</html>
