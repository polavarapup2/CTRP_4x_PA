// used by programCodeList jsp file.
var programCodeTable;
var programCodeTrialsTable;
var currentPOId;

document.observe("dom:loaded", function() {
	loadProgramCodes(jQuery);
	});

function idfy(code) {
    return code.split(' ').join('_');
}
function dateChangeHandler() {
	var selectedDate = jQuery("#datetimepicker :input").val();
	var poId = jQuery("#poID").val();        		
		
	jQuery.ajax(
		{
			type : "POST",
			url : 'programCodesajaxChangeDate.action',
			data : {
				reportingDate : selectedDate,
				poId : poId
			},
			success: function(result){
			jQuery('#date_flash').delay(100).fadeIn('normal', function() {
    	    jQuery(this).delay(2500).fadeOut();
    	});
    },
	timeout : 30000
})			
.fail(
	function(jqXHR,
		textStatus,
	errorThrown) {
	alert(jqXHR
			.getResponseHeader('msg'));
});
}

jQuery(function() { 	        		
        	jQuery('#datetimepicker').datetimepicker().on('hide', dateChangeHandler).on('change', dateChangeHandler);  
 });


function loadProgramCodes($) {
    	programCodeTable = $('#programCodesTable').DataTable({
            "dom": 'lprftip<"row">B',
            "pagingType": "full_numbers",
            "order": [
                [0, "asc"]
            ],
            "oLanguage": {
                "sInfo": "Showing _START_ to _END_ of _TOTAL_",
                "sLengthMenu": "Show _MENU_",
                "oPaginate": {
                    "sFirst": "<<",
                    "sPrevious": "<",
                    "sNext": ">",
                    "sLast": ">>"
                }
            },
            "serverSide": false,
            "columns": [{
            	"data": "programCode"
            }, {
                "data": "programName"
            }, {
                "data":function(row,type,val,meta){
                	return '<a href="#"  title="Manage this Program Code\'s assignments to trials where members of your organization family are participants" class="fa fa-exchange"   onclick="manageProgramCodes(\''+row.programCodeId+'\',\''+jQuery("#poID").val() +'\')" data-trigger="hover"></a>';
                }
            }],
            "ajax": {
                "url": "programCodesfetchProgramCodesForFamily.action",
                "data": {
                    "poId": jQuery("#poID").val()  
                },
                "type": "POST"
            },
            buttons: [ {
                extend: 'excelHtml5',
                exportOptions: {
                    columns: [ 0, 1]
                }
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0, 1]
                }
            }
                  ],
            "columnDefs": [{
                "render": function(data, type, full) {
                    return '<a href="#" class="mcapage" rel="' + full.programCodeId + '">' +data+ '</a>';
                 },
                 "targets": [0]
             },{
                "render": function(data, type, full) {
                    if (full.isActive === false) {
                        return ' <span style="color:red;font-weight:bold"> (INACTIVE) </span>' + data;
                    } else {
                        return data;
                    }
                },
                "bSortable": false,
                "targets": [1]
            },{
               "render": function(data, type, row, meta) {
                   return ' <button type="button" id="editPGCodeButton-' + idfy(row.programCode)+
                       '" title="Edit this Program <br> Code and Name" rel="tooltip" data-placement="top" data-trigger="hover"><i class="fa fa-pencil-square-o"></i></button>' +
                       '&nbsp;&nbsp;&nbsp;&nbsp;' +
                        '<button type="button" id="deletePGCodeButton-' +
                       idfy(row.programCode) + '"' +
                       'title="Delete or <br> Inactivate this <br> Program Code" rel="tooltip" data-placement="top" data-trigger="hover"><i class="fa fa-trash-o fa-lg"></i></button>' +
                       '&nbsp;&nbsp;&nbsp;&nbsp;' +
                       '<button type="button"  id="managePGCodeButton-' +
                       idfy(row.programCode) +
                       '" title="Manage this Program Code\'s <br> assignments to trials where <br> members of your organization <br> family are participants" rel="tooltip" data-placement="top" data-trigger="hover" onclick="manageProgramCodes(' +
                       row.programCodeId +',' +
                       jQuery("#poID").val() +
                       ')" data-trigger="hover"><i class="fa fa-exchange"></i></button>';
               },
               "bSortable": false,
               "bSearchable": false,
               "targets": [2]
            }],
            "fnDrawCallback": function( oSettings ) {
                this.$('[rel="tooltip"]').tooltip( {
                    placement : 'top',
                    html : true
            } );       
            }
        });
    	$('#programCodesTable tbody').on('click', 'a.mcapage', function (evt) {
            evt.preventDefault();
            var curpgcfilter = $.attr(evt.target, 'rel');
            document.forms[0].action = "managePCAssignmentexecute.action?programCodeId="+curpgcfilter+"&familyPoId="+ jQuery("#poID").val() ;
	        document.forms[0].submit();
        });
    	
    	 $('#programCodesTable tbody').on( 'click', '[id^=editPGCodeButton]', function () {
 	        var data = programCodeTable.row( $(this).parents('tr') ).data();
 	        $(this).parents('tr').addClass( "selected" );
 	        $("#dialog-edit").data('currentProgramCode', data.programCode);
  	        $("#dialog-edit").data('currentProgramCodeId', data.programCodeId);
 	        $("#updatedProgramCode").val(data.programCode);
 	        $("#updatedProgramName").val(data.programName);
 	      	var row =  $('#programCodesTable').dataTable().fnGetPosition( $(this).parent().parent()[0] );
	        $("#dialog-confirm").data('trClicked', row);
 	        $("#dialog-edit").dialog('open');
 	 } );
    	 
    	 $('#programCodesTable tbody').on( 'click', '[id^=deletePGCodeButton]', function () {
    		var data = programCodeTable.row( $(this).parents('tr') ).data();
  	        $(this).parents('tr').addClass( "selected" );
  	        handleDeleteProgramCode(data.programCodeId, data.programCode, data.programName);
  	 } );
    	 
    	 
    		$("#dialog-edit")
			.dialog(
					{
						modal : true,
						resizable: false,
						autoOpen : false,
						width : 540,
                        buttons:[
                            {
                                id: "dialog-edit-btn-save",
                                text:"Save",
                                click:function(evt) {
                                    $(this).dialog("close");
                                    if(!jQuery('#updatedProgramCode').val()){
                                        jQuery('#programCodesErrorList').html("Program code is required");
                                        jQuery("#programCodeErrorMessageModal").modal('show');
                                        return;
                                    } else if($("#dialog-edit").data('currentProgramCode') != $('#updatedProgramCode').val()){
                                        $("#dialog-confirm").data('currentProgramCode', $("#dialog-edit").data('currentProgramCode'));
                                        $("#dialog-confirm").data('currentProgramCodeId', $("#dialog-edit").data('currentProgramCodeId'));
                                        $("#dialog-confirm").dialog('open');
                                        return;
                                    }
                                    jQuery('#program_code_progress_indicator_panel').css('display', 'inline-block');
                                    $
                                        .ajax(
                                        {
                                            type : "POST",
                                            url : 'programCodesupdateProgramCode.action',
                                            data : {
                                                updatedProgramCode : $('#updatedProgramCode').val(),
                                                updatedProgramName : $('#updatedProgramName').val(),
                                                poId : jQuery("#poID").val(),
                                                currentProgramCode :  $("#dialog-edit").data('currentProgramCode'),
                                                currentProgramCodeId : $("#dialog-edit").data('currentProgramCodeId')
                                            },
                                            timeout : 30000
                                        })
                                        .always(function() {
                                            jQuery('#program_code_progress_indicator_panel').hide();
                                        })
                                        .done(
                                        function() {
                                        	var row =  $("#dialog-confirm").data('trClicked') ;
                                        	// Case where only program name changed, so update only that
											$('#programCodesTable').dataTable().fnUpdate($('#updatedProgramName').val() , row, 1 );

                                            $('#programCodesTable tbody tr').each(function(){
                                                $(this).removeClass("selected");
                                            });
                                            showProgramCodeUpdatedFlashMessage();
                                        })
                                        .fail(
                                        function(jqXHR,textStatus,errorThrown) {
                                            $('#programCodesTable tbody tr').each(function(){
                                                $(this).removeClass("selected");
                                            });
                                            jQuery('#programCodesErrorList').html(jqXHR.getResponseHeader('msg'));
                                            jQuery("#programCodeErrorMessageModal").modal('show');
                                        });

                                }
                            },
                            {
                                id: "dialog-edit-btn-cancel",
                                text: "Cancel",
                                click: function(evt) {
                                    $(this).dialog("close");
                                }

                            }
                        ]

					});
    		

    		$("#dialog-confirm")
			.dialog(
					{
						modal : true,
						resizable: false,
						autoOpen : false,
						width : 476,
                        buttons: [
                            {
                               id:"dialog-confirm-btn-yes",
                               text:"Yes",
                               click: function(evt) {
                                   $(this).dialog("close");
                                   jQuery('#program_code_progress_indicator_panel').css('display', 'inline-block');
                                   $
                                       .ajax(
                                       {
                                           type : "POST",
                                           url : 'programCodesupdateProgramCode.action',
                                           data : {
                                               updatedProgramCode : $('#updatedProgramCode').val(),
                                               updatedProgramName : $('#updatedProgramName').val(),
                                               poId : jQuery("#poID").val(),
                                               currentProgramCode :  $("#dialog-confirm").data('currentProgramCode'),
                                               currentProgramCodeId : $("#dialog-confirm").data('currentProgramCodeId')
                                           },
                                           timeout : 30000
                                       })
                                       .always(function() {
                                           jQuery('#program_code_progress_indicator_panel').hide();
                                       })
                                       .done(
                                       function() {
                                    	   var row =  $("#dialog-confirm").data('trClicked') ;
										   $('#programCodesTable').dataTable().fnUpdate($('#updatedProgramCode').val() , row, 0);
										   $('#programCodesTable').dataTable().fnUpdate($('#updatedProgramName').val() , row, 1 );
                                    	   
                                    	   $('#programCodesTable tbody tr').each(function(){
                                               $(this).removeClass("selected");
                                           });
                                           showProgramCodeUpdatedFlashMessage();
                                       })
                                       .fail(
                                       function(jqXHR,textStatus,errorThrown) {
                                           $('#programCodesTable tbody tr').each(function(){
                                               $(this).removeClass("selected");
                                           });
                                           jQuery('#programCodesErrorList').html(jqXHR.getResponseHeader('msg'));
                                           jQuery("#programCodeErrorMessageModal").modal('show');
                                       });

                               }
                            },
                            {
                              id:"dialog-confirm-btn-no",
                              text: "No",
                              click: function(evt) {
                                  $(this).dialog("close");
                              }
                            }
                        ]

					});
    		
    		$("#dialog-confirm-delete")
			.dialog(
					{
						modal : true,
						resizable: false,
						autoOpen : false,
						width : 420,
                        buttons :[
                            {
                                id:"dialog-confirm-delete-btn-delete",
                                text:"Delete",
                                click:function(evt) {
                                    $(this).dialog("close");
                                    $.ajax({
                                                type : "POST",
                                                url : 'programCodesdeleteProgramCode.action',
                                                data : {
                                                    poId: $("#poID").val(),
                                                    programCodeIdSelectedForDeletion : $("#dialog-confirm-delete").data('programCodeIdToBeDeleted')
                                                },
                                                timeout : 30000
                                            }
                                    ).done(
                                            function() {
                                                $('#programdCodeToBeDeleted').empty();
                                                programCodeTable.row('tr.selected').remove().draw();
                                                showProgramCodeDeletedFlashMessage();
                                            }
                                    ).fail(
                                            function(jqXHR,textStatus,errorThrown) {
                                                $('#programCodesTable tbody tr').each(function(){
                                                    $(this).removeClass("selected");
                                                });
                                                $('#programCodesErrorList').html(jqXHR.getResponseHeader('msg'));
                                                $("#programCodeErrorMessageModal").modal('show');
                                            }
                                    );
                                }
                            },
                            {
                                id:"dialog-confirm-delete-btn-cancel",
                                text:"Cancel",
                                click: function(evt) {
                                    $(this).dialog("close");
                                }
                            }
                        ]
					});
    		$("#dialog-inactivate-program-code")
			.dialog(
					{
						modal : true,
						resizable: false,
						autoOpen : false,
						width : 863,
                        buttons: [
                            {
                                id:"dialog-inactivate-program-code-btn-yes",
                                text:"Yes",
                                click:function(evt){

                                    $(this).dialog("close");
                                    $
                                        .ajax(
                                        {
                                            type : "POST",
                                            url : 'programCodesinactivateProgramCode.action',
                                            data : {
                                                poId : jQuery("#poID").val(),
                                                programCodeIdSelectedForDeletion : $("#dialog-inactivate-program-code").data('programCodeIdToBeInactivated')
                                            },
                                            timeout : 30000
                                        })
                                        .done(
                                        function() {
                                            showProgramCodeInactivatedFlashMessage();
                                            programCodeTable.ajax.reload();
                                        })
                                        .fail(
                                        function(jqXHR,textStatus,errorThrown) {
                                            jQuery('#programCodesErrorList').html(jqXHR.getResponseHeader('msg'));
                                            jQuery("#programCodeErrorMessageModal").modal('show');
                                        });
                                }

                            },
                            {
                              id: "dialog-inactivate-program-code-btn-no",
                              text: "No",
                              click: function(evt) {
                                  $(this).dialog("close");
                              }

                            }
                        ]

					});
    		
    }
    
    function manageProgramCodes(programCode, selectedPID) {
   	 document.forms[0].action = "managePCAssignmentexecute.action?programCodeId="+programCode+"&familyPoId="+ selectedPID;
        document.forms[0].submit();
   }

function addProgramCode(){
	if (validateProgramCode()){
		createNewProgramCode();
	} 
}

function createNewProgramCode(){
	    jQuery.ajax(
		{
			type : "POST",
			url : 'programCodescreateProgramCode.action',
			beforeSend: function () {
				jQuery('#program_code_progress_indicator_panel').css('display', 'inline-block');
	        },
			data : {
				poId : jQuery("#poID").val(),
				newProgramCode : jQuery("#newProgramCode").val(),
				newProgramName : jQuery("#newProgramName").val()
			},
		    success: function(result) {
		    		jQuery('#program_code_progress_indicator_panel').hide();
		    		showProgramCodeAddedFlashMessageAndClearInputFields();
					programCodeTable.ajax.reload();
		   },
		   timeout : 30000
		})			
		.fail(
			function(jqXHR,	textStatus,	errorThrown) {
				jQuery('#program_code_progress_indicator_panel').hide();
				$('programCodesErrorList').innerHTML = jqXHR
				.getResponseHeader('msg');
				jQuery("#programCodeErrorMessageModal").modal('show'); 
		});        	

}


function loadTrialsAssociatedToProgramCode(programCode) {
	programCodeTrialsTable = jQuery('#trialsAssociatedToProgramCodes').DataTable( {
		 "dom": 'lprftip<"row">',
	       "pagingType": "full_numbers",
	       "order": [[ 0, "desc" ]],
	       "oLanguage": {
	           "sInfo": "Showing _START_ to _END_ of _TOTAL_",
	           "sLengthMenu": "Show _MENU_",
	           "oPaginate": {
	               "sFirst": "<<",
	               "sPrevious": "<",
	               "sNext": ">",
	               "sLast": ">>"
	           }
	       },
	       pageLength:5,       
	       serverSide: false,
	       columns: [{
	           "data": "nciIdentifier"
	       }, {
	           "data": "title"
	       }, {
	           "data" : "leadOrganizationName"
	       }, {
	           "data": "principalInvestigatorName"
	       }, {
	           "data" : "statusCode"
	       }, {
	           "data" : "trialProgramCodes"
	       }],
	       ajax: {
	           url: "programCodesgetStudyProtocolsAssociatedToAProgramCode.action",
	           type:"POST",
	           data: function(d) {
	               d.programCodeIdSelectedForDeletion  = programCode;
                   d.poId = jQuery("#poID").val();
	           }
	       },
	       sProcessing: "Loading...",
	       processing:true
	   });

}


function handleDeleteProgramCode(programCodeId, programCode, programName){
    currentPOId = jQuery("#poID").val();
    jQuery.ajax(
	{
		type : "POST",
		url : 'programCodesisProgramCodeAssignedToATrial.action',
		data : {
			programCodeIdSelectedForDeletion : programCodeId,
            poId: currentPOId
		},
	    success: function(result) {
	    	if(jQuery.parseJSON(result).data[0] === false) {
		    	jQuery("#dialog-confirm-delete").data('programCodeIdToBeDeleted', programCodeId);
		    	jQuery('#programdCodeToBeDeleted').html('&nbsp;&nbsp;&nbsp;&nbsp;<b>' + programCode+ ' - ' + programName + '</b>');
		    	jQuery("#dialog-confirm-delete").dialog('open');
	    	} else {
	    		
	    		jQuery("#dialog-inactivate-program-code").data('programCodeIdToBeInactivated', programCodeId);
	    		jQuery('#programdCodeToBeInactivated').html('<ul style="list-style-type:none"><li><b>' + programCode+ ' - ' + programName + '</b></li></ul>');
	    		jQuery("#dialog-inactivate-program-code").dialog('open');
	    		if ( jQuery.fn.dataTable.isDataTable( '#trialsAssociatedToProgramCodes' ) ) {
	    		    programCodeTrialsTable.destroy();
	    		}
	    		
	    		loadTrialsAssociatedToProgramCode(programCodeId);
	    		
	    	}
	   },
	   timeout : 30000
	})			
	.fail(
		function(jqXHR,	textStatus,	errorThrown) {
			jQuery('#program_code_progress_indicator_panel').hide();
			jQuery('programCodesErrorList').innerHTML = jqXHR
			.getResponseHeader('msg');
			jQuery("#programCodeErrorMessageModal").modal('show'); 
	});        	

}


function validateProgramCode(){
	if(!jQuery('#newProgramCode').val()) {
		jQuery('#programCodesErrorList').html("Program code is required");
		jQuery("#programCodeErrorMessageModal").modal('show'); 
		return false;
	}
	
	return true;
}

function showProgramCodeUpdatedFlashMessage() {
	jQuery('#programCodeUpdatedMessageDiv').effect("highlight", {}, 3000);
	  setTimeout(function() {
		  jQuery("#programCodeUpdatedMessageDiv").hide('blind', {}, 500);
	    }, 5000);
}

function showProgramCodeDeletedFlashMessage() {
	jQuery('#programCodeDeletedMessageDiv').effect("highlight", {}, 3000);
	  setTimeout(function() {
		  jQuery("#programCodeDeletedMessageDiv").hide('blind', {}, 500);
	    }, 5000);
}

function showProgramCodeInactivatedFlashMessage() {
	jQuery('#programCodeInactivatedMessageDiv').effect("highlight", {}, 3000);
	  setTimeout(function() {
		  jQuery("#programCodeInactivatedMessageDiv").hide('blind', {}, 500);
	    }, 5000);
}

function showProgramCodeAddedFlashMessageAndClearInputFields() {
	jQuery('#programCodeAddedMessageDiv').effect("highlight", {}, 3000);
	  setTimeout(function() {
		  jQuery("#programCodeAddedMessageDiv").hide('blind', {}, 500);
		  jQuery('#newProgramCode').val('');
  		  jQuery('#newProgramName').val('');
	    }, 5000);
}




