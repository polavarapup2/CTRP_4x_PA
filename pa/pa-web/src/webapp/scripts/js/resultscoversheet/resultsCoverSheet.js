/**
 * 
 */
var editFunction;
var editFunctionChangeType;
var addDiscUrl;
var addDiscSuccessUrl;
var addChangeUrl;
var addChnageSucessUrl;
var saveFinalChangesUrl;
var sendEmailUrl;
function setCoverSheetUrls (addDiscAction, addDiscSucessAction, addChangeAction, addChangeSucessAction, 
		saveFinalChangesAction, sendEmailAction) {
	addDiscUrl = addDiscAction;
	addDiscSuccessUrl = addDiscSucessAction;
	addChangeUrl = addChangeAction;
	addChnageSucessUrl = addChangeSucessAction;
	saveFinalChangesUrl = saveFinalChangesAction;
	sendEmailUrl = sendEmailAction;
}
function initCoverSheetSectionDataTable(tableId) {
	var table = jQuery('#'+tableId).DataTable({
        "paging":   true,
        "ordering": false,
        "info":     true,
        "bFilter" :false ,
        "columnDefs" : [{
            "targets" : 4,
            "sClass" : "hide_column"
        } ]
     
    });
	return table;
}

function initTableEditFunction(tableId ,tableType , table, deleteUrl) {
	
	

	jQuery('#'+tableId+' tbody')
    .on(
            'click',
            'td img',
            function() {
            	
            var imgName =jQuery(this).attr("name");
            
            var colIdx = table.cell(jQuery(this).parent().parent()).index().column;
            var rowIdx = table.cell(jQuery(this).parent().parent()).index().row;
      	   
            var recordID = table.cell(rowIdx, 4).data();
            
           if(imgName=="edit") {
        	   
              
                    var discType =table.cell(rowIdx, 0).data();
                    var actionTaken =table.cell(rowIdx, 1).data();
                    var actionCompletionDate = table.cell(rowIdx, 2).data();
                    if (tableType=="disc") {
                    	 jQuery("#id").val(recordID);
                         jQuery('#oprationType').val("edit");
                         jQuery('#discrepancyType').val(discType);
                         jQuery('#actionTaken').val(actionTaken);
                         jQuery('#actionCompletionDate').val(actionCompletionDate);
                    } else {
                    	 jQuery("#changeId").val(recordID);
                         jQuery('#oprationType').val("edit");
                         jQuery('#changeType').val(discType);
                         jQuery('#actionTakenChangeType').val(actionTaken);
                         jQuery('#actionCompletionDateChangeType').val(actionCompletionDate);
                    }
                   
                    
                    if (tableType=="disc") {
                    	editFunction =  function() {
                    		 	table.cell(rowIdx, 0).data(
                        		jQuery('#discrepancyType').val()).draw();
                    		 	table.cell(rowIdx, 1).data(
                        		jQuery('#actionTaken').val()).draw();
                    		 	table.cell(rowIdx, 2).data(
                                jQuery('#actionCompletionDate').val()).draw();
                    		 	jQuery('#msg').fadeToggle(1000).delay(5000)
                                .fadeToggle(1000);
                    	};
                    } else {
                    	editFunctionChangeType =  function() {
                              
                          	studyRecordChangeTable.cell(rowIdx, 0).data(
                                      jQuery('#changeType').val()).draw();
                          	studyRecordChangeTable.cell(rowIdx, 1).data(
                                      jQuery('#actionTakenChangeType').val()).draw();
                          	studyRecordChangeTable.cell(rowIdx, 2).data(
                                      jQuery('#actionCompletionDateChangeType').val()).draw();
                              jQuery('#msg').fadeToggle(1000).delay(5000)
                                      .fadeToggle(1000);
                          };
                    }
                    if (tableType=="disc") {
                    	openDiscDialog();
                    }
                    else {
                    	openStudyRecordChangeDialog();
                    }
              
              } else if(imgName=="delete") {
            	  if (confirm("Click OK to remove selected records. Cancel to abort")) {
            			jQuery("#deleteType").val("studyrecord");
            			jQuery('#coverSheetForm')[0].action =deleteUrl+"?objectsToDelete="+recordID;
                        jQuery('#coverSheetForm').submit();
            	  }
              }
            });
	 if (tableType=="disc") {
	   return editFunction;
	 } else {
		 return editFunctionChangeType;
	 }
	 
}



function openDiscDialog() {
	 jQuery("#discrepancyFormDiv").dialog({
	        autoOpen : false,
	        height : 350,
	        width : 420,
	        modal : true,
	        buttons : {
	            "Save" : function() {
	                
	                if (jQuery('#discrepancyType').val() == '') {
	                    jQuery('#err')
	                            .html(
	                                    'Discrepancy Type is required.')
	                            .fadeToggle(1000).delay(
	                                    3000).fadeToggle(
	                                    1000);
	                    jQuery('#discrepancyType').focus();
	                    return;
	                } 
	                if (jQuery('#actionTaken').val() == '') {
	                    jQuery('#err')
	                            .html(
	                                    'Action Taken is required.')
	                            .fadeToggle(1000).delay(
	                                    3000).fadeToggle(
	                                    1000);
	                    jQuery('#actionTaken').focus();
	                    return;
	                } 
	                if (jQuery('#actionCompletionDate').val() == '') {
	                    jQuery('#err')
	                            .html(
	                                    'Action Completion Date is required.')
	                            .fadeToggle(1000).delay(
	                                    3000).fadeToggle(
	                                    1000);
	                    jQuery('#actionCompletionDate').focus();
	                    return;
	                } 
	                jQuery("button.ui-button-text-only:first")
                   .before(jQuery('#indicator').show())
	                
	                
	                jQuery.ajax(
	                        {
	                            type : "POST",
	                            url : addDiscUrl,
	                            data : jQuery('#addDiscrepancyForm').serialize()
	                        })
	                
	                .done(
	                        function() {
	                            jQuery('#indicator').hide();
	                            jQuery("#discrepancyFormDiv").dialog("close");
	                            if (jQuery('#oprationType').val()=="add") {
	                               	jQuery('#coverSheetForm')[0].action =addDiscSuccessUrl;
	                            	jQuery('#coverSheetForm').submit();
	                                
	                            }
	                            else {
	                            	editFunction();
	                            }
	                                
	                        })
	                .fail(
	                        function(jqXHR, textStatus, errorThrown) {
	                            alert(errorThrown);
	                            jQuery("#discrepancyFormDiv").dialog("close");
	                        });
	                
	            },
	            "Cancel" : function() {
	                jQuery("#discrepancyFormDiv").dialog("close");
	            }
	        }   ,
	        title :"Add/Edit Data Discrepancy"
	       
	     }); 
	    jQuery("#discrepancyFormDiv").dialog("open");
}

function addDisc() {
	jQuery('#addDiscrepancyForm')[0].reset();
    jQuery('#oprationType').val("add");
    jQuery("#id").val("");
    openDiscDialog();
   
}

function openStudyRecordChangeDialog() {
    jQuery("#studyRecordChangesFormDiv").dialog({
           autoOpen : false,
           height : 350,
           width : 420,
           modal : true,
           buttons : {
               "Save" : function() {
                   if (jQuery('#changeType').val() == '') {
                       jQuery('#errChangeType')
                               .html(
                                       'Change Type is required.')
                               .fadeToggle(1000).delay(
                                       3000).fadeToggle(
                                       1000);
                       jQuery('#changeType').focus();
                       return;
                   } 
                   if (jQuery('#actionTakenChangeType').val() == '') {
                       jQuery('#errChangeType')
                               .html(
                                       'Action Taken is required.')
                               .fadeToggle(1000).delay(
                                       3000).fadeToggle(
                                       1000);
                       jQuery('#actionTakenChangeType').focus();
                       return;
                   } 
                   if (jQuery('#actionCompletionDateChangeType').val() == '') {
                       jQuery('#errChangeType')
                               .html(
                                       'Action Completion Date is required.')
                               .fadeToggle(1000).delay(
                                       3000).fadeToggle(
                                       1000);
                       jQuery('#actionCompletionDateChangeType').focus();
                       return;
                   } 
                   jQuery("button.ui-button-text-only:first")
                   .before(jQuery('#indicatorChangeType').show())
                   
                   var serverUrl=addChangeUrl;
                   if(jQuery("#oprationType").val()=="edit") {
                	   var recordIdVal = jQuery("#changeId").val();
                	   serverUrl = serverUrl +"?id="+recordIdVal
                   }
                   
                   jQuery.ajax(
                           {
                               type : "POST",
                               url : serverUrl,
                               data : jQuery('#studyRecordChangesForm').serialize()
                           })
                   
                   .done(
                           function() {
                               jQuery('#indicatorChangeType').hide();
                               jQuery("#studyRecordChangesFormDiv").dialog("close");
                               if (jQuery('#oprationType').val()=="add") {
                                   jQuery('#studyRecordChangesForm')[0].action =addChnageSucessUrl;
                                   jQuery('#studyRecordChangesForm').submit();
                                   
                               }
                               else {
                            	   editFunctionChangeType();
                               }
                                   
                           })
                   .fail(
                           function(jqXHR, textStatus, errorThrown) {
                               alert(errorThrown);
                               jQuery("#studyRecordChangesFormDiv").dialog("close");
                           });
                   
                  
                   
               },
               "Cancel" : function() {
                   jQuery("#studyRecordChangesFormDiv").dialog("close");
               }
           }   ,
           title :"Add/Edit Study Record Change"
          
        }); 
       jQuery("#studyRecordChangesFormDiv").dialog("open");
}

function addStudyRecord() {
    jQuery('#studyRecordChangesForm')[0].reset();
    jQuery('#oprationType').val("add");
    jQuery("#id").val("");
    openStudyRecordChangeDialog();
   
}
function submitFinalChanges() {
	jQuery('#coverSheetForm')[0].action =saveFinalChangesUrl;
    jQuery('#coverSheetForm').submit();
}

function resetFinalChanges() {
	 jQuery('#coverSheetForm')[0].reset();
}

function sendCoverSheetEmail() {
	//check if comparision doc exists
	var compareCheckUrl="resultsReportingDocumentcheckIfComparisionDocExists.action";
	var designeeCheckUrl="resultsReportingCoverSheetcheckIfDesigneeExists.action";
	
	 jQuery.ajax(
             {
                 type : "POST",
                 url : compareCheckUrl,
                 data : jQuery('#coverSheetForm').serialize(),
                 success: function(data)
 	            {    
                	 if(data=="false") {
                		 alert("No Comparison document exists for this trial. Please add Comparison document.")
                		 return false;
                	 }
                	 else {
                		 
                		 //now check if designee exists
                		 jQuery.ajax(
                	             {
                	                 type : "POST",
                	                 url : designeeCheckUrl,
                	                 data : jQuery('#coverSheetForm').serialize(),
                	                 success: function(data)
                	 	            {    
                	                	 if(data=="false") {
                	                		 //if no designee exists then confirm if user wants to continue
                	                		if(confirm("No designee exists for this trial. Click OK to continue. Cancel to abort")) {
                	                			jQuery('#coverSheetForm')[0].action =sendEmailUrl;
                   	                		 	jQuery('#coverSheetForm').submit();
                	                		}
                	                	 }
                	                	 else {
                	                		 jQuery('#coverSheetForm')[0].action =sendEmailUrl;
                	                		 jQuery('#coverSheetForm').submit();
                	                	 }
                	 	            }
                	             })
                	            .fail(
                	             function(jqXHR, textStatus, errorThrown) {
                	                 alert(errorThrown);
                	                
                	             });
                		 
                		 
                	 }
 	            }
             })
            .fail(
             function(jqXHR, textStatus, errorThrown) {
                 alert(errorThrown);
                
             });
 	
}
function submitOnChange(id) {
	
	jQuery.ajax(
            {
                type : "POST",
                url : saveFinalChangesUrl,
                data : jQuery('#coverSheetForm').serialize(),
                success: function(data)
	            {    
                	jQuery('#'+id+'_flash').delay(100).fadeIn('normal', function() {
                        jQuery(this).delay(2500).fadeOut();
                 	});
                	
	            }
            })
           .fail(
            function(jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
               
            });
	
}

function flipDate(id){
	jQuery("#"+id+"Date").val("");
	submitOnChange(id);
}
function flipCtGovDate(){
	jQuery("#changesInCtrpCtGovDate").val("");
	submitOnChange(id);
}

function onFinalSaveDateEnter(id) {
	if(id=="designeeAccessRevokedDate") {
		jQuery("#designeeAccessRevoked_flash").hide();
	}
	else {
		jQuery("#changesInCtrpCtGov_flash").hide();
	}
	submitOnChange(id);
	
}