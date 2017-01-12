var addUrl;
var editUrl;
var deleteUrl;
var submitCtroUrl;
var summitCcctUrl;


function setDocumentPageUrl(addAction , editAction , deleteAction, submitCtroAction, submitCcctAction) {
	addUrl = addAction;
	editUrl = editAction;
	deleteUrl = deleteAction;
	submitCtroUrl = submitCtroAction;
	summitCcctUrl = submitCcctAction;
}



function selectComparisonForDelete() {
	
var compareId;	
	  jQuery("[id*='deleteLink']").each(function(index) {
		  
	            var el =jQuery("[id*='deleteLink']")[index];
	            var elementIdString = el.id;
	            
	            var tokens = elementIdString.split("_");
	            if(tokens.length==3) {
	                var type =tokens[2];
	                
	                //select comparision document for deletion
	                if(type=="Comparison") {
	                	compareId = tokens[1];
	                }
	                
	            }
	           
	      
	    });
	  
return compareId;	  
}

function showreviewCtroDialog(id) {
	
	jQuery("#docId").val(id);
	jQuery("#reviewCtroUser").dialog({
		autoOpen : false,
        height : 'auto',
        width : 450,
        title :"CTRO Review Complete"
       
     }); 
    jQuery("#reviewCtroUser").dialog("open");
}

function showreviewCCCTDialog(id) {
	jQuery("#docId").val(id);
    jQuery("#reviewCcctUser").dialog({
    	autoOpen : false,
    	height : 'auto',
        width : 450,
        appendTo: '#trialDocumentsForm',
        title :"CCCT Review Complete"
       
     }); 
    jQuery("#reviewCcctUser").dialog("open");
}

function closeDialog(ctroDialog) {
	if(ctroDialog) {
		 jQuery("#reviewCtroUser").dialog("close");
	}
	else {
		 jQuery("#reviewCcctUser").dialog("close");
	}
	
}

function saveCtroUserReview() {
	
	   var ctroUserId = jQuery("#ctroUserId").val();
	   jQuery('#trialDocumentsForm')[0].action = submitCtroUrl+"?ctroUserId="+ctroUserId;
       jQuery('#trialDocumentsForm').submit();
	 
	    
}

function saveCcctUserReview() {
    
	var ccctUserName =jQuery("#ccctUserName").val();
	
	if (jQuery.trim(ccctUserName)==""){
		alert("Please enter CCCT User");
		jQuery("#ccctUserName").focus();
		return false;
	}
	 var form  =  jQuery('#trialDocumentsForm')[0];
     var datastring = jQuery(form).serialize();
     
    jQuery('#trialDocumentsForm')[0].action = summitCcctUrl;
    jQuery('#trialDocumentsForm').submit();
     
}

function submitAddDocument() {
	
	var studyProtocolId = jQuery("#studyProtocolId").val();
	addUrl = addUrl+"&studyProtocolId="+studyProtocolId;
	jQuery(location).attr("href",addUrl);
	

}

function submitEditDocument(id) {
	
	var studyProtocolId = jQuery("#studyProtocolId").val();
	editUrl = editUrl+"&studyProtocolId="+studyProtocolId+"&page=Edit&id="+id;
	jQuery(location).attr("href",editUrl);

}

function deleteSelectedDocument(id) {
	var objectIdToDelete;
	var isResultDeleted = false;
	 if (confirm("Click OK to remove selected records. Cancel to abort")) {
		 
		 var tokens = id.split("_");
	     if(tokens.length==2) {
		 var type =tokens[1];
		 objectIdToDelete = tokens[0];
		 
		 //check if one of the result documents is deleted
		  	if(type=="Before results" || type== "After results") {
		            		isResultDeleted = true;
		            	}
		            }
		           
		 //display alert indicating comparison will be deleted if 
		 //user is not already deleting comparison document
		  if(isResultDeleted ) {
		   		var isProceed =  confirm("If \"Before results\" or \"After results\" documents is removed the \"Comparison document\" will also be removed. Do you wish to proceed?");
		   		if (!isProceed) {
		       			return false;
		       		}
		   		var compareObjId =selectComparisonForDelete();
		   		if(compareObjId!=undefined) {
		   			objectIdToDelete = objectIdToDelete +","+compareObjId;
		   		}
		   		
        	}
		       
    	 jQuery("#objectsToDelete").val(objectIdToDelete);
		 
		  if (document.forms[0].page!=undefined) {     
		            document.forms[0].page.value = "Delete";
		   }
		  
		   if(jQuery("#deleteType")) {
		      	deleteUrl = deleteUrl+"?deleteType=trialDocument";
		   }
		   jQuery('#trialDocumentsForm')[0].action = deleteUrl;
		   jQuery('#trialDocumentsForm')[0].submit();
	 }
}