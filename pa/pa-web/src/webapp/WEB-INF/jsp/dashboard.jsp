<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><c:out value="${dashboardTitle}" escapeXml="false" /></title>
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


#date-range-filter input:not([type='radio']) , #trials_bydate input {
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
	display: inline-table;
	padding: 5px;
	margin-right: 15px;
}

h3.ui-accordion-header {
    font-weight: bold;
}

table.dataTable thead th {
    padding: 0px 3px 0px 3px;
    font-size: 85%;
}

table.dataTable tfoot th {
    font-size: 70%;
    font-weight: normal;
    background: none;
    border-bottom: 0px solid white;
    padding: 5px 1px 1px 1px;
    text-align: center;
}

table.dataTable tbody td {
    padding: 3px 3px;
}

th.sorting_disabled {
    background-color: white !important;
}

#TotalMilestone td, #TotalHold td, #TotalByCount td {
    font-weight: bold;
}

#TotalMilestone td a, #TotalHold td a{
    text-decoration: none;
    color: black;
    cursor: auto;
}

a.count {
    color: #386EBF;
    cursor: pointer;
}

li.select2-selection__choice {
    white-space: normal;
}
td.ccenter {
    text-align: center;
}
td.cright {
    text-align: right;
}
div.tcbd {
   padding-top:25px;
}
tr.holiday {
    background: lightgray !important;
}

</style>

<script type="text/javascript" src="${scriptPath}/js/select2.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.doubleScroll.js"></script>
<script type="text/javascript" charset="utf8"
	src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
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

<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl" />
<c:url value="/protected/popupDisdisplayDiseaseWidget.action"
	var="diseaseWidgetURL" />
<c:set scope="request" var="suAbs"
	value="${sessionScope.isSuAbstractor==true}"></c:set>
<c:set scope="request" var="admAbs"
	value="${sessionScope.isAdminAbstractor==true || suAbs}"></c:set>
<c:set scope="request" var="sciAbs"
	value="${sessionScope.isScientificAbstractor==true || suAbs}"></c:set>

<script type="text/javascript" language="javascript">
    jQuery.noConflict();
    var tabs
    var interventionLookupURL = '<c:url value='/protected/popupIntsearch.action'/>';
    var diseaseLookupURL = '<c:url value='/protected/ajaxDiseaseTreesearch.action'/>';
    var diseaseNameLookupURL = '<c:url value='/protected/ajaxDiseaseTreegetName.action'/>';
    var bizDaysCalcURL = '<c:url value='/protected/dashboardbizDays.action'/>';
    var updateCompletionDateURL = '<c:url value='/protected/dashboardupdateExpectedAbstractionCompletionDate.action'/>';
    var milestonesInProgressURL = '<c:url value='/protected/trialCountsmilestonesInProgress.action'/>';
    var onHoldTrialsURL = '<c:url value='/protected/trialCountsonHoldTrials.action'/>';
    var trialDistURL = '<c:url value='/protected/trialCountstrialDist.action'/>';
    var abstractorsWorkURL = '<c:url value='/protected/trialCountsabstractorsWork.action'/>';
    var countsByDateURL = '<c:url value='/protected/trialCountscountsByDate.action'/>';
    var trialsByDateTable;

    
    function disableFieldsIfNeeded() {
    	var filterTypeValue ="";
    	var selected = jQuery("#date-range-filter input[type='radio']:checked");
    	if (selected.length > 0) {
    		filterTypeValue = selected.val();
    	}
    	
    	if (filterTypeValue!="limit") {
    		jQuery("#dateFrom").val("");
    		jQuery("#dateTo").val("");
    		jQuery("#dateFrom").prop('disabled', true);
    		jQuery("#dateTo").prop('disabled', true);
    		jQuery("#dateFrom").next().hide();
    		jQuery("#dateTo").next().hide();
    	}
    	else {
    		jQuery("#dateFrom").prop('disabled', false);
            jQuery("#dateTo").prop('disabled', false);
            jQuery("#dateFrom").next().show();
            jQuery("#dateTo").next().show();
    	}
    	
    }
    
	function handleAction(action) {
		
		
		if(action!= undefined) {
			document.forms[0].action = "dashboard" + action + ".action";
	        document.forms[0].submit();
		}
		else {
			var activeTab = tabs.activeContainer.id;
			if(activeTab=="search") {
				handleAction('search');
			}
		}
		
	}
	
	function handleCheckoutAction(action) {
        var studyProtocolId = '${sessionScope.summaryDTO.studyProtocolId}';
        if (!allowAction(action)) {
            return;
        }
        if ((action == 'adminCheckIn') || (action == 'scientificCheckIn') || (action == 'adminAndScientificCheckIn')
                || (action == 'checkInSciAndCheckOutToSuperAbs')) {
        	 showCommentsBox(action);
        } else {
        	handleAction(action);
        }       
    }
	
	 function saveCheckin(action) {		 
         var form = $('dashboardForm');
         var commandVal = document.getElementById('commentCommand').value;
         var comment = document.getElementById('comments').value;
         if (comment==null){
        	    return;
         }
         form.elements["checkInReason"].value = comment;
         handleAction(commandVal);
     }
	
	 var eltDims = null;
     function showCommentsBox(action) {
         document.getElementById('commentCommand').value=action;
         // retrieve required dimensions
         if (eltDims == null) {
             eltDims = $('comment-dialog').getDimensions();
         }
         var browserDims = $(document).viewport.getDimensions();

         // calculate the center of the page using the browser and element dimensions
         var y  = (browserDims.height - eltDims.height) / 2;
         var x = (browserDims.width - eltDims.width) / 2;    
         
         $('comment-dialog').absolutize(); 
         $('comment-dialog').style.left = x + 'px';
         $('comment-dialog').style.top = y + 'px';
         $('comment-dialog').show();
     }    
	
	function allowAction(action) {
        if (((action == 'adminCheckIn' || action == 'adminAndScientificCheckIn') 
                && (trialHasStatusErrors || trialHasStatusWarnings)) || 
                (action == 'scientificCheckIn' && suAbs && !checkedOutForAdmin && (trialHasStatusErrors || trialHasStatusWarnings)) ){
            displayStatusTransitionMessages(action);
            return false;
        } 
        if (action == 'scientificCheckIn' && sciAbs && !checkedOutForAdmin && trialHasStatusErrors) {
            displayStatusTransitionMessageAndPickSuperAbstractor(action);
            return false;
        }
        return true;
    }
	
	 function displayStatusTransitionMessageAndPickSuperAbstractor(action) {
         var dialogID = '#pickSuperAbstractor';                
         jQuery(dialogID).dialog('open');  
         jQuery(dialogID).attr('act', action);               
     }
	 
	 function displayStatusTransitionMessages(action) {
         var dialogID = '';
         if (trialHasStatusErrors && !trialHasStatusWarnings) {
             dialogID = '#transitionErrors';
         } else if (!trialHasStatusErrors && trialHasStatusWarnings) {
             dialogID = '#transitionWarnings';
         } else if (trialHasStatusErrors && trialHasStatusWarnings) {
             dialogID = '#transitionErrorsAndWarnings';
         }
         jQuery(dialogID).dialog('open');  
         jQuery(dialogID).attr('act', action);               
     }

	function viewProtocol(pid) {
		$('studyProtocolId').value = pid;
		handleAction('view');
		return false;
	}

    function displayTrialsByDate() {
        trialsByDateTable.ajax.reload();
    }
    function resetTrialsByDate() {
        $('countRangeFrom').clear();
        $('countRangeTo').clear();
    }

	function resetValues() {
		$('dashboardForm').getElements().each(function(el) {
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
		$('submittingOrgId').value = '';
		if ($('error_msg_div')!=null) {
			$('error_msg_div').hide();
		}
		jQuery(".select2-hidden-accessible").val(null).trigger("change");
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction();
			return false;
		}
	}

	function lookupSubmittingOrg() {
		showPopup('${lookupOrgUrl}', function() {
		}, 'Select Organization');
	}

	function setorgid(orgId, orgName) {
		$('submittingOrgId').value = orgId;
		$('submittedBy').value = orgName;
	}

	function loadDiv(orgId) {
	}

	(function($) {    
        //******************
        //** On DOM Ready **
        //******************
        $(function () {
            
        	$( "#transitionErrors" ).dialog({
                modal: true,
                autoOpen : false,                         
                buttons: {
                  "Trial Status History": function() {
                        $(this).dialog("close");
                        goToTrialStatusHistory();
                  },
                  Cancel: function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        	$( "#transitionErrorsAndWarnings" ).dialog({
                modal: true,
                autoOpen : false,                         
                buttons: {
                  "Trial Status History": function() {
                        $(this).dialog("close");
                        goToTrialStatusHistory();
                  },
                  Cancel: function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        	$( "#transitionWarnings" ).dialog({
                modal: true,
                autoOpen : false,         
                width : 450,
                buttons: {
                  "Proceed with Check-in": function() {
                        $(this).dialog("close");
                        trialHasStatusErrors = false;
                        trialHasStatusWarnings = false;
                        handleCheckoutAction($(this).attr('act'));
                        
                  },
                  "Trial Status History": function() {
                        $(this).dialog("close");
                        goToTrialStatusHistory();
                  },
                  Cancel: function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
        	$( "#pickSuperAbstractor" ).dialog({
                modal: true,
                autoOpen : false,         
                width : 450,
                buttons: {
                  "Proceed with Check-in": function() {
                      if ($.isNumeric($('#supAbsId').val())) {
                          document.forms[0].elements["superAbstractorId"].value = $('#supAbsId').val();
                          $(this).dialog("close");                                
                          handleCheckoutAction('checkInSciAndCheckOutToSuperAbs'); 
                      }                                                                 
                  },                           
                  "Cancel": function() {
                    $(this).dialog("close");
                  }
                }
            });
        	
            // Add the Export option: CSV | Excel to the top of the search results in all Dashboards.
            // This is not supported by Display Tag, so doing this manually.
            $( "#wl_table_container > div.exportlinks" ).clone().insertBefore( "#wl_table_container > span.pagebanner" );
            $( "#results > div.exportlinks" ).clone().insertBefore( "#results > span.pagebanner" );
        	
        	// Init Select2 boxes.
            $("#anatomicSites").select2();
        	
            $("#diseases").select2({                 
                ajax: {
                    url: diseaseLookupURL,
                    dataType: 'json',
                    delay: 300,
                    data: function (params) {
                      return {
                        searchName: params.term,
                        page: params.page
                      };
                    },
                    processResults: function (data, page) {
                      return {
                        results: data.items
                      };
                    },
                    cache: true
                  },
                  escapeMarkup: function (markup) { return markup; }, 
                  minimumInputLength: 1,
                  templateResult: function (iv) {
                        if (iv.loading) return iv.text;
                        var markup = '<div><table cellpadding=0 cellspacing=0><tr><td>' + iv.text.replace(new RegExp(iv.term.replace(/[\}\{\+\$\^\)\(\?\*\]\[\.]/i,'\\\\$&'), 'i'), '<b>$&</b>') 
                            + '</td><td><i title="Click here to open up the disease tree" data-diseaseid="'+iv.id+'" class="fa fa-sitemap"></i></td></tr></table></div>';
                        return markup;
                  }, 
                  templateSelection: function (iv) {                         
                      return iv.text;
                  }  
            });
            
            // Set up tricky event handling for tree icon clicks.
            var diseaseID = null;
            $(document).on( "mouseenter", "i.fa-sitemap", function(e) {
            	diseaseID = e.target.attributes['data-diseaseid'].value;
            }).on( "mouseleave", "i.fa-sitemap", function(e) {
            	diseaseID = null;
            });            
            $("#diseases").on("select2:selecting", function (e) { 
            	var dId = diseaseID;
            	diseaseID = null;
            	if (dId != null) {
            		e.preventDefault();	
            		$("#diseases").select2("close");
            		showPopWin('${diseaseWidgetURL}?lookUp=true&diseaseID='+dId, 1100, 500, function(val) {
            			if (val.value) {            			   
            			    $.each(val.value.split(','), function(index, value) {
            			    	// we have disease ID, but not name. We need to make an Ajax call to figure out
            			    	// the name before adding OPTION to #diseases...
            			    	$.get( diseaseNameLookupURL, "diseaseId="+value, function(data) {            			    		  
            			    		// append the new option
            		                $("#diseases").append('<option value="' + value + '">' + data + '</option>');

            		                // get a list of selected values if any - or create an empty array
            		                var selectedValues = $("#diseases").val();
            		                if (selectedValues == null) {
            		                    selectedValues = new Array();
            		                }
            		                selectedValues.push(value);   // add the newly created option to the list of selected items
            		                $("#diseases").val(selectedValues).trigger('change');   // have select2 do it's thing
            		          
            			    	}, "html");
            			    });
            			}
            		}, 'Disease/Condition');            		
            	}            	
            });
           
            
            $("#interventions").select2({            	 
            	  ajax: {
            		    url: interventionLookupURL,
            		    dataType: 'json',
            		    delay: 300,
            		    data: function (params) {
            		      return {
            		    	searchName: params.term,
            		    	page: params.page
            		      };
            		    },
            		    processResults: function (data, page) {
            		      // parse the results into the format expected by Select2.
            		      // since we are using custom formatting functions we do not need to
            		      // alter the remote JSON data
            		      return {
            		        results: data.items
            		      };
            		    },
            		    cache: true
            		  },
            		  escapeMarkup: function (markup) { return markup; }, 
            		  minimumInputLength: 1,
            		  templateResult: function (iv) {
            			    if (iv.loading) return iv.text;
            			    var markup = iv.text.replace(new RegExp(iv.term.replace(/[\}\{\+\$\^\)\(\?\*\]\[\.]/i,'\\\\$&'), 'i'), '<b>$&</b>') 
            			         + " - (" + iv.type + (iv.ctgovType!=''?'/'+iv.ctgovType:'') + ")";
            			    return markup;
            		  }, 
            		  templateSelection: function (iv) {                         
                          return iv.text;
                      }  
            	});
        	
        	// Prevent opening of the Select2 box upon unselect.
        	var ts = 0;
            $(".select2-hidden-accessible").on("select2:unselect", function (e) { 
            	ts = e.timeStamp;
            }).on("select2:opening", function (e) { 
                if (e.timeStamp - ts < 100) {                	
                	e.preventDefault();
                }
            });
            
            // Validation Error Dialog.
            $( "#validationError" ).dialog({
                  autoOpen : false,
                  modal: true,
                  buttons: {                                        
                  "Close": function() {
                        $( this ).dialog( "close" );
                      }
                  }
            });  
            
            // Variable holding filtering field name
            var filterField;
            
            // Date range filter dialog.
            $( "#date-range-filter").dialog({
	             modal: true,
	             autoOpen : false,
	             appendTo: "#workload",
	             buttons: {
	               "OK": function() {
	            	   var dateFrom = $("#dateFrom").val().trim();
	            	   var dateTo = $("#dateTo").val().trim();
	            	   if (dateFrom != '' && !isValidDate(dateFrom)) {
	            		   $("#validationErrorText").html('Invalid From Date: '+dateFrom);
	            		   $("#validationError").dialog('open');
	            	   } else if (dateTo != '' && !isValidDate(dateTo)) {
                           $("#validationErrorText").html('Invalid To Date: '+dateTo);
                           $("#validationError").dialog('open');
                       } else {
                    	   // Dates are valid. Proceed with filtering.
                    	   // Now empty dates also means some kind of filter so don't cancel any existing filter
                    	   $("#dateFilterField").val(filterField);
                    	   $(this).dialog("close");
                    	   handleAction('filter');
	            	   }
	               },
	               "Cancel": function() {
	            	    $(this).dialog("close");
	               }
	             }
            });
            
            // Submission type filter dialog.
            $( "#submission-type-filter").dialog({
                 modal: true,
                 autoOpen : false,
                 appendTo: "#workload",
                 buttons: {
                   "OK": function() {                      
                        $(this).dialog("close");
                        handleAction('filter');
                   },
                   "Cancel": function() {
                        $(this).dialog("close");
                   } 
                 }
            });

            
            // Date pickers
            $( "#dateFrom, #dateTo, #newCompletionDate" ).datepicker({
                  showOn: "button",
                  buttonImage: "<c:url value='/images/ico_calendar.gif'/>",
                  buttonImageOnly: true,
                  buttonText: "Select a date",
                  maxDate: '+1Y'
            });            
        
            // Set up date range filtering columns.
            $("th.filter a" ).wrap( "<table><tr><td></td></tr></table>" );
            $("th.filter table tbody tr" ).prepend('<td><i title="Click here to filter by a date range" class="fa fa-filter fa-2x fa-inverse"></i></td>');
            $("i.fa-filter").click(function() {
            	 // based on column header clicked, determine on which field to filter.
            	 filterField = $(this).parents("th.filter").attr("class").split(" ")[1];            	 
            	 $( "#date-range-filter").dialog('open');
            });
            
            // if a date range filter is active, invert the color of the corresponding funnel icon.
            if ($("#dateFilterField").val().trim().length > 0) {
            	$("th."+$("#dateFilterField").val().replace('.','\\.')+" i.fa-filter").removeClass("fa-inverse");
            }
            
            // Set up submission type filter.
            $("th.submissionType a" ).wrap( "<table><tr><td></td></tr></table>" );
            $("th.submissionType table tbody tr" ).prepend('<td><i title="Click here to filter by submission type" class="fa fa-filter fa-2x fa-inverse submissionType"></i></td>');
            $("i.submissionType").click(function() {                               
                 $( "#submission-type-filter").dialog('open');
            });
            
            // if a submission type filter is active, invert the color of the corresponding funnel icon.
            if ($("input[name='submissionTypeFilter']:checked").length > 0) {
                $("i.submissionType").removeClass("fa-inverse");
            }
            
            // Set up Refresh button.
            $("#refreshBtn").button().click(function(event) {
            	// if we are on Results tab after clicking on a Trial Dist. in panel, refresh it.
            	if ($("#resultsid.active").length > 0 && $("#distr").val().length>0 
            			&& $("#dashboardForm").attr('action').indexOf('searchByDistribution')>=0) {
            		handleAction('searchByDistribution');
                } else if ($("#resultsid.active").length > 0 && 
                		$("#dashboardForm").attr('action').indexOf('searchByCountType')>=0) {
                    handleAction('searchByCountType');
                } else if ($("#resultsid.active").length > 0 || ( $("#resultsid").length > 0 && $("#workloadid.active").length == 0 && $("#countsid.active").length == 0 ) ) {
                	handleAction("search");
                } else {
                	$("#dateFilterField, #dateFrom, #dateTo").val(null);
                	$("#choiceunrestricted").prop('checked', true);
                	$("input[name='submissionTypeFilter']:checked").prop('checked', false);
                	handleAction("execute");
                }    
            });
            
            // For super abstractors, set up expected abs. date override.
            if (suAbs) {
            	$("span[data-expected-abstraction-completion-date]").after("<i data-expected-abstraction-completion-date=\"true\" class=\"fa fa-pencil-square-o\"></i>");
            }            
            $("i[data-expected-abstraction-completion-date]").click(function() {
            	
                var span = $(this).prev("span");
                var submissionDate = $(span).attr('data-submission-date');
                var currentExpectedDate = $(span).text().trim();
                var protocolID = $(span).attr('data-study-protocol-id');
                
                if ($("#abstraction-date-override").dialog("instance")) {
                    $("#abstraction-date-override").dialog("destroy");
                }
                $("#abstraction-date-override").dialog({
                    modal: true,
                    autoOpen : false,      
                    width: 370,
                    appendTo: "#workload",
                    buttons: {
                      "Save": function() {
                    	  var newExpectedDate = $("#newCompletionDate").val().trim();
                    	  if (newExpectedDate=='') {
                    		  $("#validationErrorText").html('Please specify a date.');
                              $("#validationError").dialog('open');
                    	  } else if ($("#newCompletionDateComments").val().trim()=='') {
                              $("#validationErrorText").html('Please provide a comment.');
                              $("#validationError").dialog('open');
                          } else {
                    		  $(this).dialog("close");                   		  
                    		  if ($("#date-override-warning").dialog("instance")) {
                                  $("#date-override-warning").dialog("destroy");
                              }
                    	      $("#date-override-warning").dialog({
                    	          modal: true,
                    	          autoOpen: false,
                    	          width: 510,
                    	          buttons: {
                    	              "Yes": function() {
                    	                  // Finally, make an AJAX call to update the expected completion date.
                    	                  $(this).dialog("close");
                    	                  $.post(updateCompletionDateURL, {
                    	                          studyProtocolId: protocolID,
                    	                          newCompletionDate: newExpectedDate,
                    	                          newCompletionDateComments: $("#newCompletionDateComments").val()
                    	                      },
                    	                      function() {
                    	                    	  $(span).text(newExpectedDate);
                    	                    	  if (newExpectedDate!= $(span).attr('data-calculated-abstraction-completion-date')) {	                    	                    	 
	                    	                    	  $(span).attr('data-overridden', 'true');
	                    	                    	  $(span).attr('title', $("#newCompletionDateComments").val());	                    	                    	 
	                    	                    	  $(span).attr('data-comment', $("#newCompletionDateComments").val());
                    	                    	  } else {
                    	                    		  $(span).attr('data-overridden', 'false');
                                                      $(span).attr('title', '');
                    	                    	  }
                    	                    	  $(span).tooltip();
                    	                      }).fail(function() {
                    	                    	  alert("Unable to save the new completion date due to a server error.");
                    	                  });
                    	              },
                    	              "No": function() {
                    	                  $(this).dialog("close");
                    	              }
                    	          }
                    	      });   
                    	      $("#date-override-warning").dialog('open');
                    	      $("#date1").html(currentExpectedDate);
                    	      $("#date2").html(newExpectedDate);
                    	      $("#days1, #days2").html("<i class='fa fa-spinner fa-spin'></i>");
                    	      // Calculate business days on the server and populate spans...
                    	      $.get(bizDaysCalcURL, {dateFrom: submissionDate, dateTo: currentExpectedDate}, function(data) {
                    	    	  $("#days1").text(data);
                    	      });
                    	      $.get(bizDaysCalcURL, {dateFrom: submissionDate, dateTo: newExpectedDate}, function(data) {
                                  $("#days2").text(data);
                              });
                    	      
                          }                                                     
                      },
                      "Cancel": function() {
                           $(this).dialog("close");
                      } 
                    }
               });
               $("#abstraction-date-override").dialog('open');
               $("#newCompletionDate" ).datepicker("option", "minDate", $.datepicker.parseDate( "mm/dd/yy", submissionDate));
               // Pre-populate the date & comment fields with current values.
               $("#newCompletionDate").val(currentExpectedDate);
               $("#newCompletionDateComments").val($(span).attr('data-comment'));
               $("#trialSubmissionDate").text(submissionDate);
               
            });
            
            // Initialize tooltips for overridden completion dates.
            $("span[data-overridden='true']").tooltip();

            
            // Prevent form submission upon clicking enter within newCompletionDateComments.
            $('#newCompletionDateComments').keydown(function (e) {
                 if (e.keyCode == 13) {
                     e.preventDefault();
                     return false;
                 }
            });
            
            // Add double scroll to workload div.
            $('#wl_table_container').doubleScroll({
                contentElement: $('#wl')
            });

            
            // Set up Milestones in Progress panel.   
            $("#milestones_in_progress" ).accordion({
                collapsible: true,
                heightStyle: "content"
            });
            $('#milestones_in_progress_table').DataTable({
            	"bFilter" : false,
                "paging":   false,                
                "searching":   false,
                "info":     false,
                "order": [],
                "columns": [
                            { "data": "milestone" },
                            { "data": "count" }                            
                        ],
                "ajax" : {
                    "url" : milestonesInProgressURL,
                    "type" : "POST"
                },
                "columnDefs" : [{
                    "targets" : [0,1],
                    "orderable" : false
                    },{
                    "targets" : 1,
                    "render" : function(data, type, r, meta) {
                        var content = '<a class="count" data-milestone="'+r.milestone+'">'+r.count+'</a>';
                        return content;
                    }
                }]
            }).on('xhr', function() {
            	$('#wl_table_container').doubleScroll('refresh');
            }); 
            $('#milestones_in_progress_table tbody')
                .on(
                    'click',
                    "a[data-milestone][data-milestone!='Total']",
                    function() {
                    	resetValues();                        
                        $("#milestoneTypelast").prop("checked", true);
                        $("#milestone").val($(this).attr('data-milestone'));
                        $("#onHoldStatus").val('notonhold');
                        $("#processingStatus").val(["Submitted","Amendment Submitted","Accepted",
                                                    "Abstracted","Verification Pending","Abstraction Verified Response",
                                                    "Abstraction Verified No Response"]);
                        handleAction('search');
            });
            
            // Set up On-Hold Trials panel.   
            $("#on_hold_trials" ).accordion({
                collapsible: true,
                heightStyle: "content"
            });
            $('#on_hold_trials_table').DataTable({
                "bFilter" : false,
                "paging":   false,                
                "searching":   false,
                "info":     false,
                "order": [],
                "columns": [
                            { "data": "reason" },
                            { "data": "count" }                            
                        ],
                "ajax" : {
                    "url" : onHoldTrialsURL,
                    "type" : "POST"
                },
                "columnDefs" : [{
                    "targets" : [0,1],
                    "orderable" : false
                    }, {
                    "targets" : 1,
                    "render" : function(data, type, r, meta) {
                        var content = '<a class="count" data-onhold-reason="'+r.reasonKey+'">'+r.count+'</a>';
                        return content;
                    }
                }]
            }).on('xhr', function() {
                $('#wl_table_container').doubleScroll('refresh');
            }); 
            $('#on_hold_trials_table tbody')
                .on(
                    'click',
                    "a[data-onhold-reason][data-onhold-reason!='Total']",
                    function() {
                        resetValues();                        
                        $("#onHoldStatus").val('onhold');                        
                        $("#onHoldReason").val($(this).attr('data-onhold-reason'));
                        $("#processingStatus").val(["Submitted","Amendment Submitted","Accepted",
                                                    "Abstracted","Verification Pending","Abstraction Verified Response",
                                                    "Abstraction Verified No Response", "On-Hold"]);
                        handleAction('search');
            });
            
            // Set up Trial Dist. panel.   
            $("#trial_dist" ).accordion({
                collapsible: true,
                heightStyle: "content"
            });
            $('#trial_dist_table').DataTable({
                "bFilter" : false,
                "paging":   false,                
                "searching":   false,
                "info":     false,
                "order": [],
                "columns": [
                            { "data": "range" },
                            { "data": "count" }                            
                        ],
                "ajax" : {
                    "url" : trialDistURL,
                    "type" : "POST"
                },
                "columnDefs" : [{
                    "targets" : [0,1],
                    "orderable" : false
                    },{
                    "targets" : 1,
                    "render" : function(data, type, r, meta) {
                        var content = '<a class="count" data-range="'+r.range+'">'+r.count+'</a>';
                        return content;
                    }
                }]
            }).on('xhr', function() {
                $('#wl_table_container').doubleScroll('refresh');
            }); 
            $('#trial_dist_table tbody')
                .on(
                    'click',
                    "a[data-range]",
                    function() {
                        resetValues();             
                        $("#distr").val($(this).attr('data-range'));
                        handleAction('searchByDistribution');
            });
            
            // Set up Abstractors Work in Progress Panel.
            $("#abstractors_work" ).accordion({
                collapsible: true,
                heightStyle: "content"
            });
            $('#abstractors_work_table').DataTable({
                "bFilter" : false,
                "paging":   false,                
                "searching":   false,
                "info":     false,
                "order": [],
                "columns": [
                            { "data": "name" },
                            { "data": "admin" },                            
                            { "data": "scientific" },
                            { "data": "admin_scientific" }
                        ],
                "ajax" : {
                    "url" : abstractorsWorkURL,
                    "type" : "POST"
                },
                "columnDefs" : [{
                    "targets" : [0,1,2,3],
                    "orderable" : false
                    },
                    {
                    "targets" : 0,
                    "render" : function(data, type, r, meta) {
                        var content = '<a class="count" data-user-id="'+r.user_id+'">'+r.name+'</a>';
                        return content;
                    }
                }]
            }).on('xhr', function() {
                $('#wl_table_container').doubleScroll('refresh');
            }); 
            $('#abstractors_work_table tbody')
                .on(
                    'click',
                    "a[data-user-id]",
                    function() {
                        resetValues();             
                        $("#checkedOutBy").val($(this).attr('data-user-id'));
                        $("#processingStatus").val(["Submitted","Amendment Submitted","Accepted",
                                                    "Abstracted","Verification Pending","Abstraction Verified Response",
                                                    "Abstraction Verified No Response", "On-Hold"]);
                        handleAction('search');
            });
            
           //enable/disable date filer
           disableFieldsIfNeeded();
            
            //Setup trials by count Panel
            $("#trials_bydate" ).accordion({
                collapsible: true,
                heightStyle: "content"
        });
            trialsByDateTable =  $('#trials_bydate_table').DataTable({
                "bProcessing" :true,
                "oLanguage": {
                    "sProcessing": "<div class=\"ctrp-loading ui-corner-all ui-tooltip-shadow\">Loading trial counts...<img src=\"../images/loading.gif\" width='16px' height='16px'></div>"
                },
                "bFilter" : false,
                "paging":   false,
                "searching":   false,
                "info":     false,
                "order": [],
                "createdRow": function( row, data, index ) {
                    if(data.day == 'Total') {
                        $(row).attr('id', "TotalByCount");
                    } else if(!data.bday) {
                        $(row).addClass('holiday');
                    }
                },
                "columns": [
                    { "data": "day" },
                    { "data": "submittedCnt" },
                    { "data": "pastTenCnt" },
                    { "data": "expectedCnt" }
                ],
                "ajax" : {
                    "url" : countsByDateURL,
                    "type" : "POST",
                    "data": function ( d ) {
                        d.fromDate = $('#countRangeFrom').val();
                        d.toDate = $('#countRangeTo').val();
                    }
                },
                "columnDefs" : [{
                	"className": "dt-center",
                    "targets" : [0,1,2,3],
                    "orderable" : false
                },
                {
                        "targets" : 1,
                        "render" : function(data, type, r, meta) {
                            if(r.submittedCnt == 0) return '';
                            var content = '<a class="count" countForDay="'+r.day+'" countType="submittedCnt">'+r.submittedCnt+'</a>';
                            return content;
                        }
                },
                {
                    "targets" : 2,
                    "render" : function(data, type, r, meta) {
                        if(r.pastTenCnt == 0) return '';
                        var content = '<a class="count" countForDay="'+r.day+'" countType="pastTenCnt">'+r.pastTenCnt+'</a>';
                        return content;
                    }
                },
                {
                    "targets" : 3,
                    "render" : function(data, type, r, meta) {
                        if(r.expectedCnt == 0) return '';
                        var content = '<a class="count" countForDay="'+r.day+'" countType="expectedCnt">'+r.expectedCnt+'</a>';
                        return content;
                    }
                }]
            }).on('xhr', function() {
                $('#wl_table_container').doubleScroll('refresh');
            });
            $('#trials_bydate_table tbody').on('click', "a[countType]",function() {
                        var curFromDate = $('#countRangeFrom').val()
                        var curToDate = $('#countRangeTo').val()
                        resetValues();
                        $('#countRangeFrom').val(curFromDate) ;
                        $('#countRangeTo').val(curToDate);
                        $("#countForDay").val($(this).attr('countForDay'));
                        $("#countType").val($(this).attr('countType'));
                        handleAction('searchByCountType');
            });

        });
	}(jQuery));
        
        var trialStatusHistoryURL = '<c:url value='/protected/studyOverallStatus.action'/>';
        
        function goToTrialStatusHistory() {
             var form = document.forms[0];
             form.action=trialStatusHistoryURL;
             form.submit();
        }
        
        var trialHasStatusErrors = ${sessionScope.trialHasStatusErrors==true};
        var trialHasStatusWarnings = ${sessionScope.trialHasStatusWarnings==true};
        
        var adminAbs = ${sessionScope.isAdminAbstractor==true};
        var sciAbs =    ${sessionScope.isScientificAbstractor==true};
        var suAbs = ${sessionScope.isSuAbstractor==true};
        
        var checkedOutForAdmin = ${sessionScope.trialSummary.adminCheckout.checkoutBy != null};
        var checkedOutForSci = ${sessionScope.trialSummary.scientificCheckout.checkoutBy != null};
       
	
	Event.observe(window, 'load', function() {
		
		addCalendar("Cal1", "Select Date", "submittedOnOrAfter",
				"dashboardForm");
		addCalendar("Cal2", "Select Date", "submittedOnOrBefore",
				"dashboardForm");

        jQuery("#countRangeFrom").datepicker({
            showOn: "button",
            buttonImage: "<c:url value='/images/ico_calendar.gif'/>",
            buttonImageOnly: true,
            buttonText: "Select a date",
            dateFormat:"mm/dd/yy"
        });

        jQuery("#countRangeTo").datepicker({
            showOn: "button",
            buttonImage: "<c:url value='/images/ico_calendar.gif'/>",
            buttonImageOnly: true,
            buttonText: "Select a date",
            dateFormat:"mm/dd/yy"
        });


		setWidth(90, 1, 15, 1);
		setFormat("mm/dd/yyyy");
		
		tabs = new Control.Tabs($('maintabs'));
		
		<c:if test="${not empty failureMessage}">
        tabs.setActiveTab('search');
        updateHelpTopic('search');
        </c:if>
        
		<c:if test="${toggleResultsTab==true}">
		tabs.setActiveTab('results');
		updateHelpTopic('results');
		</c:if>
		
		<c:if test="${toggleDetailsTab==true}">
		tabs.setActiveTab('details');
		updateHelpTopic('details');
		</c:if>
		
        if ($('workloadid')!=null) {
            Event.observe($('workloadid'), "click", function() {
                     updateHelpTopic('workload');
              jQuery('#wl_table_container').doubleScroll('refresh');
            });
        }
        if ($('countsid')!=null) {
            Event.observe($('countsid'), "click", function() {
                    updateHelpTopic('counttab');
            });
        }
		
		if ($('resultsid')!=null) {
		    Event.observe($('resultsid'), "click", function() {
		             updateHelpTopic('results');
		    });
		}
	    
	    if($('detailsid') !=null) {
	    	 Event.observe($('detailsid'), "click", function() {
	             updateHelpTopic('details');
	         });
	    }
	    
	    if ($('searchid')!=null) {
		    Event.observe($('searchid'), "click", function() {
		             updateHelpTopic('search');
		    }); 
	    }
	});
	
	function updateHelpTopic(tabType) {
		if(tabType =='results') {
		       if("${sessionScope.isAdminAbstractor}" == "true") {
		    	   document.getElementById('pageHelpid').onclick = function() {
                       Help.popHelp('dashboardadmin');
                   } 
	            }
	              if("${sessionScope.isScientificAbstractor}"  == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardsci');
	                    }
	                }
	              if("${sessionScope.isAdminAbstractor}"  == "true" && "${sessionScope.isScientificAbstractor}" == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardadmin-sci');
	                    }
	                }
	              if("${sessionScope.isSuAbstractor}" == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardresults');
	                    }
	                }
		} 
		if(tabType =='workload') {
			      if("${sessionScope.isAdminAbstractor}" == "true") {
		    	   document.getElementById('pageHelpid').onclick = function() {
                    Help.popHelp('dashboardworkloadadmin');
                   } 
	             }
	              if("${sessionScope.isScientificAbstractor}"  == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardworkloadsci');
	                    }
	                }
	              if("${sessionScope.isAdminAbstractor}"  == "true" && "${sessionScope.isScientificAbstractor}" == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardworkloadadminsci');
	                    }
	                }
	              if("${sessionScope.isSuAbstractor}" == "true") {
	                    document.getElementById('pageHelpid').onclick = function() {
	                        Help.popHelp('dashboardsuper');
	                    }
	                }
        }		
		if(tabType =='search') {
			if("${sessionScope.isSuAbstractor}" == "true") {
                document.getElementById('pageHelpid').onclick = function() {
                    Help.popHelp('dashboardsuper');
                }
            }
		}
		if (tabType =='details') {
		     if("${sessionScope.isAdminAbstractor}" == "true") {
		            document.getElementById('pageHelpid').onclick = function() {
		                Help.popHelp('admindetails');
		            }
		        }
		          if("${sessionScope.isScientificAbstractor}" == "true") {
		                document.getElementById('pageHelpid').onclick = function() {
		                    Help.popHelp('scidetails');
		                }
		            }
		          if("${sessionScope.isAdminAbstractor}"  == "true"&& "${sessionScope.isScientificAbstractor}" == "true") {
		                document.getElementById('pageHelpid').onclick = function() {
		                    Help.popHelp('sciadmindetails');
		                }
		            }
		          if("${sessionScope.isSuAbstractor}" == "true") {
		                document.getElementById('pageHelpid').onclick = function() {
		                    Help.popHelp('dashboarddetails');
		                }
		            }
		}
		
		if(tabType=="counttab") {
			   if("${sessionScope.isSuAbstractor}" == "true") {
                   document.getElementById('pageHelpid').onclick = function() {
                       Help.popHelp('dashboardtrialcount');
                   }
               }
		}
		return true;
	}


</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<c:out value="${dashboardTitle}" escapeXml="false" />
	</h1>
	<c:if test="${sessionScope.isAdminAbstractor==true}">
		<c:set var="topic" scope="request" value="dashboardadmin" />
	</c:if>
	<c:if test="${sessionScope.isScientificAbstractor==true}">
		<c:set var="topic" scope="request" value="dashboardsci" />
	</c:if>
    <c:if test="${sessionScope.isAdminAbstractor==true && sessionScope.isScientificAbstractor==true}">
		<c:set var="topic" scope="request" value="dashboardadmin-sci" />
	</c:if>
	<c:if test="${sessionScope.isSuAbstractor==true}">
		<c:set var="topic" scope="request" value="dashboardsuper" />
	</c:if>
	<jsp:useBean id="currentDate" class="java.util.Date" scope="request"></jsp:useBean>
	<div class="box" id="filters">
		<s:form id="dashboardForm">
			<s:token />
			<pa:sucessMessage />
			<pa:failureMessage />
			<s:hidden id="studyProtocolId" name="studyProtocolId" />
			<s:hidden name="checkInReason" id="checkInReason" />
			<s:hidden name="superAbstractorId" />
			<s:hidden name="distr" id="distr"/>
			<s:hidden name="countForDay" id="countForDay"/>
			<s:hidden name="countType" id="countType"/>
			<table class="form">
				<tr>
					<td align="right" nowrap="nowrap" style="padding: 0; margin: 0;">
						<c:if test="${dashboardSearchResults!=null}">Results
                        as of <fmt:formatDate value="${currentDate}"
                            pattern="MM/dd/yyyy -- hh:mm:ss aaa" />&nbsp;&nbsp;</c:if>
                    <input
						type="button" id="refreshBtn" value="Refresh" />
					</td>
				</tr>
				<tr>
					<td>
						<ul id="maintabs" class="tabs">
							<li><a id="workloadid" href="#workload">Workload</a></li>
							<c:if test="${suAbs}">
								<li><a id="countsid" href="#counts">Counts</a></li>
							</c:if>
                            <c:if test="${suAbs}">
								<li><a id="searchid" href="#search">Search Criteria</a></li>
							</c:if>
							<c:if test="${dashboardSearchResults!=null}">
								<li><a id="resultsid" href="#results">${suAbs?'Results':'Trial List'}</a></li>
							</c:if>
							<c:if test="${queryDTO!=null}">
								<li><a id="detailsid" href="#details">Details</a></li>
							</c:if>
						</ul>
						<div id="tabboxwrapper">
							<div id="workload" class="box"
								style="${toggleResultsTab || toggleDetailsTab?'display:none;':''}">
								<div id="wl_table_container">
									<jsp:include page="dashboard.workload.jsp" />
								</div>
							</div>
							<c:if test="${suAbs}">
                            <div id="counts" class="box" style="display:none;">
                                <div id="count_panels_container">
                                    <jsp:include page="dashboard.trialcounts.jsp" />
                                </div>
                            </div>
							<div id="search" class="box"
								style="display:none;">
									<table class="form">
										<tr>
											<td scope="row" class="label"><label for="assignee"><fmt:message
														key="dashboard.assignedTo" /></label></td>
											<td><s:select id="assignee" name="assignee"
													list="assigneeList" headerKey="" headerValue=""
													value="assignee" cssStyle="width:206px" /></td>
										</tr>
										<tr>
											<td scope="row" class="label"><label for="checkedOutBy"><fmt:message
														key="dashboard.checkedOutBy" /></label></td>
											<td><s:select id="checkedOutBy" name="checkedOutBy"
													list="checkedOutByList" headerKey="" headerValue=""
													value="checkedOutBy" cssStyle="width:206px" /></td>
											<td scope="row" class="label"><label
												for="processingPriority"><fmt:message
														key="dashboard.processingPriority" /></label></td>
											<td rowspan="2"><s:select id="processingPriority"
													name="processingPriority"
													list="#{'1':'1 - High','2':'2 - Normal','3':'3 - Low'}"
													headerKey="" headerValue="All" multiple="true"
													value="processingPriority" cssStyle="width:206px" /></td>

										</tr>
										<tr>
											<td scope="row" class="label"><label
												for="submittedOnOrBefore"><fmt:message
														key="dashboard.submittedBetween" /></label></td>
										<label for="submittedOnOrAfter" style="display:none">Do not show </label>
											<td nowrap="nowrap"><s:textfield id="submittedOnOrAfter"
													name="submittedOnOrAfter" maxlength="10" size="10" /> <a
												href="javascript:showCal('Cal1')"> <img
													src="${pageContext.request.contextPath}/images/ico_calendar.gif"
													alt="Select Date" class="calendaricon" /></a> &nbsp;and&nbsp; <s:textfield
													id="submittedOnOrBefore" name="submittedOnOrBefore"
													maxlength="10" size="10" /> <a
												href="javascript:showCal('Cal2')"> <img
													src="${pageContext.request.contextPath}/images/ico_calendar.gif"
													alt="Select Date" class="calendaricon" />
											</a></td>
										</tr>
										<tr>
										<tr>
											<td scope="row" class="label"><label for="submittedBy"><fmt:message
														key="dashboard.submittedBy" /></label></td>
											<td nowrap="nowrap"><s:textfield id="submittedBy"
													name="submittedBy" cssStyle="width:100%;" readonly="true" /></td>
											<td align="left" colspan="2">
												<ul style="margin-top: -1px;">
													<li style="padding-left: 0"><a
														href="javascript:void(0)" class="btn"
														onclick="lookupSubmittingOrg();"><span class="btn_img"><span
																class="organization">Look Up</span></span></a></li>
												</ul> <s:hidden name="submittingOrgId" id="submittingOrgId" />
											</td>
										</tr>
										<tr>
											<td colspan="4"><div class="line"></div></td>
										</tr>
										<tr>
											<s:set name="submissionTypeValues"
												value="@gov.nih.nci.pa.enums.SubmissionTypeCode@getDisplayNames()" />
										<td scope="row" class="label"><label for="submissionType"><fmt:message
														key="dashboard.submissionType" /></label></td>
											<td><s:select headerKey="" headerValue="All"
													multiple="true" id="submissionType" name="submissionType"
													list="#submissionTypeValues" value="submissionType"
													cssStyle="width:206px" /></td>
											<td scope="row" class="label"><label for="nciSponsored"><fmt:message
														key="dashboard.nciSponsored" /></label></td>
											<td><s:select id="nciSponsored" name="nciSponsored"
													list="#{'true':'NCI Sponsored','false':'Not NCI Sponsored'}"
												headerKey="" headerValue="All" size="4" value="nciSponsored"
												cssStyle="width:206px" /></td>

										</tr>

										<tr>
											<td scope="row" class="label"><label for="onHoldStatus"><fmt:message
														key="dashboard.onHoldStatus" /></label></td>
											<td><s:select headerKey="" headerValue="All"
													multiple="true" id="onHoldStatus" name="onHoldStatus"
													list="#{'onhold':'On-Hold (now)','notonhold':'Not On-Hold (now)','onholdanytime':'On-Hold (at anytime)'}"
													value="onHoldStatus" cssStyle="width:206px" /></td>
											<td scope="row" class="label"><label
												for="ctepDcpCategory"><fmt:message
														key="dashboard.ctepDcpCategory" /></label></td>
										<td><s:select id="ctepDcpCategory" name="ctepDcpCategory"
													list="#{'ctepdcp':'CTEP and DCP PIO Trials Only','ctep':'CTEP PIO Trials Only','dcp':'DCP PIO Trials Only','exclude':'Exclude CTEP and DCP Trials'}"
													headerKey="" headerValue="All" size="5"
													value="ctepDcpCategory" cssStyle="width:206px" /></td>
										</tr>

										<tr>											
											<td scope="row" class="label"><label for="onHoldReason"><fmt:message
														key="dashboard.onHoldReason" /></label></td>
											<td><s:select headerKey="" headerValue="All" size="5"
													multiple="true" id="onHoldReason" name="onHoldReason"
												list="onHoldValuesMap" 
												 /></td>
											<td></td>
											<s:set name="milestoneCodes"
												value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
											<td nowrap="nowrap">
												<fieldset style="margin-left: 0px;">
													<legend>
														<fmt:message key="dashboard.milestones" />
													</legend>
													<s:radio name="milestoneType" id="milestoneType"
														value="milestoneType" list="#{'any':'Has milestone'}"></s:radio>
													<br />
													<s:radio name="milestoneType" id="milestoneType"
														value="milestoneType"
														list="#{'last':'Has a last milestone'}"></s:radio>
												<br />
												<label for="milestone" style="display:none">milestone </label>
													<s:select headerKey="" headerValue="" id="milestone"
														name="milestone" list="#milestoneCodes" value="milestone"
														cssStyle="width:206px" />
												</fieldset>
											</td>
										</tr>
										<tr>
											<s:set name="documentWorkflowStatusCodeValues"
												value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
											<td scope="row" class="label"><label
												for="processingStatus"><fmt:message
														key="dashboard.processingStatus" /></label></td>
											<td><s:select headerKey="" headerValue="All" size="5"
													multiple="true" id="processingStatus"
													name="processingStatus"
													list="#documentWorkflowStatusCodeValues"
													value="processingStatus" cssStyle="width:206px" /></td>
											<td scope="row" class="label" style="vertical-align: middle;"><label
												for="showTrialsReadyFor"><fmt:message
														key="dashboard.showTrialsReadyFor" /></label></td>
											<td>
												<table class="milestone_matrix" border="1">
													<tr>
														<td class="noborder">&nbsp;</td>
														<td class="label"><fmt:message
																key="dashboard.abstraction" /></td>
														<td class="label"><fmt:message key="dashboard.qc" /></td>
													</tr>
													<tr>
														<td class="label"><fmt:message
																key="dashboard.administrative" /></td>
													<td><s:checkbox name="adminAbstraction" id="showTrialsReadyFor"/></td>
													<td><s:checkbox name="adminQC" id="showTrialsReadyFor"/></td>
													</tr>
													<tr>
														<td class="label"><fmt:message
																key="dashboard.scientific" /></td>
													<td><s:checkbox name="scientificAbstraction" id="showTrialsReadyFor"/></td>
													<td><s:checkbox name="scientificQC" id="showTrialsReadyFor"/></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
                                        <td scope="row" class="label"><label
                                            for="diseases">Disease/Condition</label></td>
											<td><s:select size="2" multiple="true"
													listKey="diseaseIdentifier" listValue="preferredName"
													id="diseases" name="diseases" list="diseasesList"
													value="diseases" cssStyle="width:206px" /></td>
											<s:set name="anatomicSites"
												value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getAnatomicSites()" />
                                        <td scope="row" class="label"><label
                                            for="anatomicSites">Data Table 4 Anatomic Site</label></td>
                                        <td><s:select size="2" multiple="true"
                                                listKey="id" listValue="displayName"
                                                id="anatomicSites" name="anatomicSites" list="#anatomicSites"
													value="anatomicSites" cssStyle="width:206px" /></td>
										</tr>
										<tr>
                                        <td scope="row" class="label"><label
                                            for="interventions">Interventions</label></td>
											<td><s:select size="2" multiple="true"
													listKey="identifier.extension" listValue="name.value"
                                                id="interventions" name="interventions" list="interventionsList"
                                                value="interventions" cssStyle="width:206px" /></td>                                        
										</tr>
									</table>
									<div class="actionsrow">
										<del class="btnwrapper">
											<ul class="btnrow">
												<li><s:a href="javascript:void(0)" cssClass="btn" id="searchBtn"
														onclick="handleAction('search');">
														<span class="btn_img"><span class="search">Search</span></span>
													</s:a> <s:a href="javascript:void(0)" cssClass="btn" id="resetBtn"
														onclick="resetValues();return false">
														<span class="btn_img"><span class="cancel">Reset</span></span>
													</s:a></li>
											</ul>
										</del>
									</div>
								</div>
							</c:if>

							<div id="results" class="box"
								style="${toggleResultsTab?'':'display:none;'}">
								<c:set var="requestURI" value="dashboardloopback.action"
									scope="request" />
								<display:table class="data" sort="list" uid="results" pagesize="2147483647"
									defaultsort="9"
									decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
									name="${dashboardSearchResults}" requestURI="${requestURI}"
									export="true">
                                    <display:setProperty name="paging.banner.item_name" value="trial"/>
                                    <display:setProperty name="paging.banner.items_name" value="trials"/> 
									<display:setProperty name="paging.banner.group_size" value="0" />
									<display:setProperty name="export.xml" value="false" />
									<display:setProperty name="export.excel.filename"
										value="dashboardSearchResults.xls" />
									<display:setProperty name="export.excel.include_header"
										value="true" />
									<display:setProperty name="export.csv.filename"
										value="dashboardSearchResults.csv" />
									<display:setProperty name="export.csv.include_header"
										value="true" />

									<display:column class="title"
										titleKey="studyProtocol.nciIdentifier" sortable="true"
										media="html"
										headerClass="sortable">
										<!--  ${results.nciIdentifierTruncated} -->
										<a href="javascript:void(0);"
											onclick="viewProtocol('${results.studyProtocolId}')"> <c:out
												value="${results.nciIdentifierTruncated}"></c:out>
										</a>
									</display:column>
									<display:column class="title"
										titleKey="studyProtocol.nciIdentifier" sortable="true"
										media="excel csv xml" property="nciIdentifierTruncated"
										headerClass="sortable">
									</display:column>
                                    <display:column  title="Lead Organization" property="leadOrganizationName"
                                        media="excel csv xml"/>
                                    <display:column  title="Lead Org PO ID" property="leadOrganizationPOId"
                                        media="excel csv xml"/>                                                                         
                                    <display:column  title="ClinicalTrials.gov Identifier" property="nctIdentifier"
                                        media="excel csv xml"/>									
						            <display:column  titleKey="studyProtocol.ctepIdentifier" property="ctepId"
						                media="excel csv xml"/>
						            <display:column  titleKey="studyProtocol.dcpIdentifier" property="dcpId"
						                media="excel csv xml"/>									
									<display:column title="CDR ID" property="cdrId"
										media="excel csv xml" />
									<display:column title="Amendment #" property="amendmentNumber"
										media="excel csv xml" />
                                    <display:column  title="Data Table 4 Funding" property="summary4FundingSponsorType"
                                        media="excel csv xml"/>                                        
                                    <display:column  title="On Hold Date" property="recentOnHoldDate" format="{0,date,MM/dd/yyyy}"
										media="excel csv xml" />
                                    <display:column  title="Off Hold Date" property="recentOffHoldDate" format="{0,date,MM/dd/yyyy}"
                                        media="excel csv xml"/>
                                    <display:column  title="On Hold Reason" property="recentHoldReason"
                                        media="excel csv xml"/>
                                    <display:column  title="On Hold Description" property="recentHoldDescription"
										media="excel csv xml" />


						            <display:column titleKey="studyProtocol.trialtype" media="excel csv xml">
						                <c:out value="${results.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
									</display:column>
									<display:column title="NCI Sponsored" media="excel csv xml">
                                        <c:out value="${results.sponsorName=='National Cancer Institute'?'Yes':'No'}"/>
									</display:column>
                                    <display:column  title="Processing Status" property="documentWorkflowStatusCode.code" media="excel csv xml"/>                                    
                                    <display:column  title="Processing Status Date" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" media="excel csv xml"/>
                                    <display:column  title="Admin Check out Name" property="adminCheckout.checkoutByUsername" media="excel csv xml"/>
                                    <display:column  title="Admin Check out Date" property="adminCheckout.checkoutDate" format="{0,date,MM/dd/yyyy}" media="excel csv xml"/>
                                    <display:column  title="Scientific Check out Name" property="scientificCheckout.checkoutByUsername" media="excel csv xml"/>
                                    <display:column  title="Scientific Check out Date" property="scientificCheckout.checkoutDate" format="{0,date,MM/dd/yyyy}" media="excel csv xml"/>

									<display:column escapeXml="true" sortable="true"
										titleKey="studyProtocol.submissionType"
										property="submissionType" headerClass="sortable" />
									<display:column title="CTEP/DCP" property="ctepOrDcp"
										sortable="true" headerClass="sortable" />
									<display:column title="Submitting Organization"
										property="submitterOrgName" sortable="true"
										headerClass="sortable" />
									<display:column title="Submission Date"
										property="lastCreated.dateLastCreated"
										format="{0,date,MM/dd/yyyy}" sortable="true"
										headerClass="sortable" />
									<display:column title="Last Milestone"
										property="milestones.lastMilestone.milestone.code"
										sortable="true" headerClass="sortable" />
									<display:column title="Last Milestone Date"
										property="milestones.lastMilestone.milestoneDate"
										format="{0,date,MM/dd/yyyy}" sortable="true"
										headerClass="sortable" />
									<display:column title="Submission Source"
										property="studySource" sortable="true"
										headerClass="sortable" />
									<display:column title="Processing Priority"
										property="processingPriority" sortable="true"
										headerClass="sortable" />
                                    <display:column  title="Comments" property="processingComments" media="excel csv xml"/>

                                    <display:column title="This Trial is" 
                                        class="this_trial_is" sortable="true" headerClass="sortable"><c:out escapeXml="false"
											value="${results.checkedOutByMe?'Checked out by me
':''}" /><c:out escapeXml="false"
											value="${results.readyForAdminProcessing && admAbs?'Ready for Admin Processing
':''}" /><c:out escapeXml="false"
											value="${results.readyForAdminQC && admAbs?'Ready for Admin QC
':''}" /><c:out escapeXml="false"
											value="${results.readyForTSRSubmission?'Ready for TSR Submission
':''}" /><c:out escapeXml="false"
											value="${results.submittedNotAccepted?'Submitted -- not accepted
':''}" /><c:out escapeXml="false"
											value="${results.readyForScientificProcessing && sciAbs?'Ready for Scientific Processing
':''}" /><c:out escapeXml="false"
											value="${results.readyForScientificQC && sciAbs?'Ready for Scientific QC
':''}" /><c:if
											test="${results.documentWorkflowStatusCode.code=='On-Hold'}">On hold since ${results.onHoldDate!=null?results.onHoldDate:'N/A'},
reason: ${not empty results.onHoldReasons?results.onHoldReasons:'N/A'}
                                        </c:if></display:column>


									<s:set var="milestoneCodesForReporting" scope="request"
										value="@gov.nih.nci.pa.enums.MilestoneCode@getMilestoneCodesForReporting()" />
                                    <c:forEach items="${milestoneCodesForReporting}" var="milestone">
                                        <display:column title="${milestone.code}" media="excel csv xml">
                                            <s:set scope="request" var="milestoneDate" value="%{#attr.results.getMilestoneForReporting(#attr.milestone).milestoneDate}"/>                                            
                                            <fmt:formatDate value="${requestScope.milestoneDate}" pattern="MM/dd/yyyy" />
										</display:column>
										<display:column title="Added By" media="excel csv xml">
                                            <s:set scope="request" var="milestoneCreator" value="%{#attr.results.getMilestoneForReporting(#attr.milestone).creator}"/>
                                            <c:out value="${requestScope.milestoneCreator}" escapeXml="false"/>
										</display:column>
										<display:column title="Added On" media="excel csv xml">
                                            <s:set scope="request" var="milestoneCreateDate" value="%{#attr.results.getMilestoneForReporting(#attr.milestone).createDate}"/>
                                            <fmt:formatDate value="${requestScope.milestoneCreateDate}" pattern="MM/dd/yyyy" />
										</display:column>
									</c:forEach>
								</display:table>
							</div>

							<div id="details" class="box"
								style="${toggleDetailsTab?'':'display:none;'}">
								<table class="form">
									<tr>
								        <td colspan="4" nowrap="nowrap" class="label">
								            <s:if test="%{!checkoutCommands.isEmpty()}">
				                                <s:set name="checkoutCommands" scope="page" value="%{checkoutCommands}" /> 
				                                <c:forEach items="${checkoutCommands}" var="command" varStatus="vstat">			                                    
			                                        <a href="javascript:void(0)" onclick="handleCheckoutAction('${command}')">
			                                            <fmt:message key="studyProtocolView.button.${command}" />
													</a>&nbsp;&nbsp; 		                                    
				                                </c:forEach>
			                                </s:if>	
                                            <a href="checkOutHistory.action">
                                                Check out History
                                            </a>&nbsp;&nbsp; 	

											<c:choose>
                                                <c:when test="${queryDTO.documentWorkflowStatusCode.code=='Submitted' || queryDTO.documentWorkflowStatusCode.code=='Amendment Submitted'}">
                                                    <a href="studyProtocolview.action?studyProtocolId=${summaryDTO.studyProtocolId}">
                                                        Validate
                                                    </a>&nbsp;&nbsp;                                                 
                                                </c:when>
                                                <c:when test="${queryDTO.documentWorkflowStatusCode.code=='Rejected'}">
												</c:when>
												<c:otherwise>
	                                                <a href="studyProtocolview.action?studyProtocolId=${summaryDTO.studyProtocolId}">
	                                                    Abstract
	                                                </a>&nbsp;&nbsp;                                                 
                                                </c:otherwise>
                                            </c:choose>   
                                            <a href="ajaxAbstractionCompletionviewTSR.action">
                                                       View TSR
                                            </a>&nbsp;                                          
								        </td>
									</tr>
									<tr>
										<td scope="row" class="label">Submission Type</td>
										<td>${queryDTO.submissionType}</td>
										<td scope="row" class="label">Processing Status</td>
										<td>${queryDTO.documentWorkflowStatusCode.code}</td>
									</tr>
									<tr>
										<td scope="row" class="label">NCI ID</td>
										<td>${queryDTO.nciIdentifierTruncated}</td>
										<td scope="row" class="label">Processing Status Date</td>
										<td><fmt:formatDate
												value="${queryDTO.documentWorkflowStatusDate}"
												pattern="MM/dd/yyyy" />

										</td>
									</tr>
									<tr>
										<td scope="row" class="label">ClinicalTrials.gov Identifier</td>
										<td><c:out value="${nctIdentifier}"></c:out></td>
										<td scope="row" class="label">Admin Check-out</td>
										<td><c:out value="${queryDTO.adminCheckout.fullName}" />
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">CTEP ID</td>
										<td><c:out value="${queryDTO.ctepId}"></c:out></td>
										<td scope="row" class="label">Scientific Check-out</td>
										<td><c:out
												value="${queryDTO.scientificCheckout.fullName}" /></td>
									</tr>
									<tr>
										<td scope="row" class="label">DCP ID</td>
										<td><c:out value="${queryDTO.dcpId}"></c:out></td>
									</tr>
									<tr>
										<td scope="row" class="label">CDR ID</td>
                                        <td><c:out value="${not empty summaryDTO.cdrId?summaryDTO.cdrId:'N/A'}"/></td>
                                        <td colspan="2" rowspan="16">
                                            <display:table class="data" sort="list" uid="milestones"                                                                                             
                                                name="${summaryDTO.milestoneHistory}"
                                                export="false">                                             
                                                <display:column title="Milestone"
                                                    property="milestone.code" sortable="false"
                                                    headerClass="sortable" />
                                                <display:column title="Date"
                                                    property="milestoneDate"
													format="{0,date,MM/dd/yyyy}" sortable="false"
													headerClass="sortable" />
                                                <display:column title="Creator"
                                                    property="creator" sortable="false"
                                                    headerClass="sortable" />                                               
                                            </display:table>                                        
                                        </td>
									</tr>
									<tr>
										<td scope="row" class="label">Submitting Organization</td>
										<td><c:out value="${queryDTO.submitterOrgName}"></c:out>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">Submission Date</td>
										<td><fmt:formatDate
												value="${queryDTO.lastCreated.dateLastCreated}"
												pattern="MM/dd/yyyy" />
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">Amendment #</td>
										<td><c:out value="${not empty summaryDTO.amendmentNumber?summaryDTO.amendmentNumber:'N/A'}"></c:out>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">Trial Category</td>
                                        <td><c:out value="${queryDTO.trialCategory}"></c:out>
                                        </td>
									</tr>
									<tr>
										<td scope="row" class="label">Trial Type</td>
                                        <td>
                                            <c:out value="${queryDTO.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">Data Table 4 Funding</td>
                                        <td><c:out value="${func:capitalizeFully(fn:replace(queryDTO.summary4FundingSponsorType, '_',' '))}"></c:out>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">On Hold Date</td>
                                        <td><c:out value="${not empty queryDTO.recentOnHoldDate?func:format(queryDTO.recentOnHoldDate,'MM/dd/yyyy'):'N/A'}"></c:out>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">Off Hold Date</td>
                                        <td><c:out value="${not empty queryDTO.recentOffHoldDate?func:format(queryDTO.recentOffHoldDate,'MM/dd/yyyy'):'N/A'}"></c:out>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">On Hold Reason</td>
                                        <td><c:out value="${not empty queryDTO.recentHoldReason?queryDTO.recentHoldReason:'N/A'}"></c:out>
										</td>
									</tr>
									<tr>
										<td scope="row" class="label">NCI Sponsored?</td>
                                        <td><c:out value="${queryDTO.sponsorName=='National Cancer Institute'?'Yes':'No'}"></c:out>
										</td>
									</tr>
                                    <tr><td>&nbsp;</td></tr>
									<tr>
                                        <td scope="row" class="label"><label for="assignedTo">Assigned To</label></td>
                                        <td> 
                                            <s:set name="abstractorsList"
												value="@gov.nih.nci.pa.service.util.CSMUserService@getInstance().abstractors" />
											<s:select id="assignedTo" name="assignedTo"
                                                list="#abstractorsList" headerKey="" headerValue="Unassigned"
                                                value="#session.summaryDTO.assignedUserId" cssStyle="width:206px" />
                                        </td>
									</tr>
									<tr>
										<td scope="row" class="label">Submission Source</td>
                                        <td><c:out value="${queryDTO.studySource}"></c:out>
                                        </td>
									</tr>
									<tr>
                                        <td scope="row" class="label"><label for="newProcessingPriority">Processing Priority</label></td>
                                        <td> 
                                            <s:select id="newProcessingPriority"
												name="newProcessingPriority"
												list="#{'1':'1 - High','2':'2 - Normal','3':'3 - Low'}"
                                                value="#session.summaryDTO.processingPriority" cssStyle="width:206px" />
                                            
                                            <c:if test="${!suAbs}">
												<script type="text/javascript">
                                                      Event.observe(window, "load", function () {
                                                          $('assignedTo').disabled = true;
                                                          $('newProcessingPriority').disabled = true;
                                                      });
                                                </script>
                                            </c:if>                                        
                                                                                        
                                        </td>
									</tr>
									<tr>
                                        <td scope="row" class="label" colspan="2"><label for="processingComments">Trial Processing Comments</label></td>
									</tr>
									<tr>
                                        <td colspan="2">
	                                        <s:textarea id="processingComments" name="processingComments"    
	                                            maxlength="4000" cssClass="charcounter"                                        
	                                            cssStyle="width: 100%;" rows="5">
												<s:param name="value">
													<s:property value="#session.summaryDTO.processingComments" />
												</s:param>
	                                        </s:textarea> 
                                        </td>                                        
									</tr>
									<tr>
										<td colspan="4" align="center">
											<div class="actionsrow">
												<del class="btnwrapper">
													<ul class="btnrow">
														<li><s:a href="javascript:void(0)" cssClass="btn"
																onclick="handleAction('save');">
																<span class="btn_img"><span class="search">Save</span></span>
															</s:a></li>
													</ul>
												</del>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<pa:trialCheckInWarnings />
		</s:form>
		<s:form action="studyProtocol" id="checkoutForm">
			<s:token></s:token>
		</s:form>
	</div>
</body>
</html>
