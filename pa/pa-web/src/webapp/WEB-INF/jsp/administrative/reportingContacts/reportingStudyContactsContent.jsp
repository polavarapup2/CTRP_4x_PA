 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
    
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

var dscTable;
var pscTable;

jQuery(document).ready(function() {
    
    dscTable = initSectionDataTable('dscWeb');
    pscTable = initSectionDataTable('pscWeb');
});

function initSectionDataTable(tableId) {
    var table = jQuery('#'+tableId).DataTable({
        "paging":   false,
        "ordering": false,
        "info":     false,
        "bFilter" :false
    });
    return table;
}

function lookupdesigneeorg() {
	$('contactType').value = "dc";
    showPopup('${lookupUrl}', null, 'Organization');
}

function lookupdesigneeperson() {
	$('contactType').value = "dc";
    showPopup('${lookupPersonsUrl}', null, 'Persons');
}

function lookuppioperson() {
    $('contactType').value = "pc";
    showPopup('${lookupPersonsUrl}', null, 'Persons');
}

function setorgid(orgId, orgName) {
	var ct = $('contactType').value;
    if(ct=='dc') {
        $('dscOrgId').value = orgId;
        $('dscOrg').value = orgName;
    }
}

function setpersid(persIdentifier,name,email,phone) {
	var ct = $('contactType').value;
	if(ct=='pc') {
		$('pscPrsnId').value = persIdentifier;
	    $('pscPrsn').value = name;
	    $('pscEmail').value = email;
	    $('pscPhone').value = phone;
	} else if(ct=='dc') {
		$('dscPrsnId').value = persIdentifier;
	    $('dscPrsn').value = name;
	    $('dscEmail').value = email;
        $('dscPhone').value = phone;
	}
}

function setPersonCountry(country) {
    var ct = $('contactType').value;
    if(ct=='dc') {
        jQuery("#dcCountry").val(country);
    } else if(ct=='pc') {
    	  jQuery("#pcCountry").val(country);
    }
}

function loadDiv(orgId) {
}

function loadPersDiv(persid, func) {
}

function validateEmail(emailFldId) {
    var box = $('' + emailFldId);
    if(box.value == '') return true;
    
    if (!validateEmailWithRegex(box.value)) {
        alert("Invalid Entry:\nOnly alphanumeric and '-@_.' are allowed for 'Email'.");
        return false;
    }
    return true;
}

function validatePhone(phoneFldId) {
    var box = $('' + phoneFldId);
    if(box.value == '') return true;   

    if (!validateUSPhoneNumber(box.value)) {
        alert("Invalid Entry:\nOnly numbers and '-' are allowed for 'Phone'. Supported format is XXX-XXX-XXXX");
        return false;
    }
    return true;
}

function validateExt(extFldId, phoneId) {
	
    var box = $('' + extFldId);
    if(box.value == '') return true; 
    
    var phoneVal = jQuery("#"+phoneId).val();
    if(jQuery.trim(phoneVal)=="") {
        alert("Please enter phone");
        return false;
    }

    if (!validateExtWithRegex(box.value)) {
        alert("Invalid Entry:\nOnly numbers are allowed for 'Ext'.");
        return false;
    }
    return true;
}

function getCountry() {
	var country; 
    var ct = jQuery('#contactType').val();
    if(ct=='dc') {
        country = jQuery("#dcCountry").val();
    }
    else if(ct=='pc') {
        country = jQuery("#pcCountry").val();
    }
    return country;
}



function addOrUpdateDesigneeContact() {
	var personVal = jQuery("#dscPrsn").val();
	if(jQuery.trim(personVal)=="") {
		 alert("Name is required");
		 jQuery("#dscPrsn").focus();
		 return false;
	}
	
	if(!validateEmail('dscEmail')) return false;
	var country = getCountry();
		//only validate phone for usa or canada
	    if(country=="USA" || country=="CAN") {
	    	   if(!validatePhone('dscPhone')) return false;
	    } 
    if(!validateExt('dscExt','dscPhone')) return false;
	submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactaddOrEditDesigneeContact.action');
}

function addOrUpdatePioContact() {
	var personVal = jQuery("#pscPrsn").val();
    if(jQuery.trim(personVal)=="") {
         alert("Name is required");
         jQuery("#pscPrsn").focus();
         return false;
    }
    
    var country = getCountry();
   if(!validateEmail('pscEmail')) return false;
	//only validate phone for usa or canada
    if(country=="USA" || country=="CAN") {
     if(!validatePhone('pscPhone')) return false;
    }
    if(!validateExt('pscExt','pscPhone')) return false;
    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactaddOrEditPIOContact.action');
}

function editDesigneeContact(selSCId) {
	$('process').value='edit';
    $('dscToEdit').value=selSCId;
    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactviewDesigneeStudyContact.action');
}

function editPioContact(selSCId) {
	$('process').value='edit';
	$('pscToEdit').value=selSCId;
	submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactviewPioStudyContact.action');
}

function deleteDesigneeContact(selSCId) {
	var r = confirm("Please confirm to delete the selected designee contact");
	if (r == false) {
	    return false;
	}
    $('process').value='delete';
    $('dscToEdit').value=selSCId;
    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactdelete.action');
}

function deletePioContact(selSCId) {
	var r = confirm("Please confirm to delete the selected PIO contact");
    if (r == false) {
        return false;
    }
    $('process').value='delete';
    $('pscToEdit').value=selSCId;
    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactdelete.action');
}

function saveStudyContacts() {
	submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactsaveFinalChanges.action');
}

function cancelStudyContacts() {
	 var result = confirm("All changes made to study contacts will be cancelled. Please confirm?");
	 if (result == true) {
		    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactcancel.action');
	 }
}

function submitStudyContact(scFormName, scUrl) {
    jQuery.ajax(
            {
                type : "POST",
                url : scUrl,
                data : jQuery('#reportStudyContactsForm').serialize(),
                success : function(data) {
                    var scdiv = jQuery('#studycontacts');
                    scdiv.html(data);
                    jQuery('html, body').animate({ scrollTop: 0 }, 0);
                 }
            });
}

</SCRIPT>

</head>

 <div id="scdiv" class="box">
    <pa:sucessMessage/>
    <pa:failureMessage/>
    <s:if test="hasActionErrors()">
        <div class="action_error_msg"><s:actionerror escape="false"/></div>
    </s:if>
    <s:form name="reportStudyContactsForm" id ="reportStudyContactsForm" action="">
        <pa:studyUniqueToken/>
        
         <s:hidden id="contactType" name="contactType"/>
        <s:hidden id="process" name="process"/>
        <s:hidden id="studyProtocolId" name="studyProtocolId"/>
        <s:hidden id="dcCountry" name="dcCountry" />
        <s:hidden id="pcCountry" name="pcCountry" />
        
        <jsp:include page="reportAddEditDesigneeStudyContact.jsp"/>
        <jsp:include page="reportAddEditPioStudyContact.jsp"/>
    </s:form>
 </div>
 