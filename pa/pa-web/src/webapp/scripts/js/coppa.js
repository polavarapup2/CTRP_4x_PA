function setFocusToFirstControl() {
    for (var f=0; f < document.forms.length; f++) {
        for(var i=0; i < document.forms[f].length; i++) {
            var elt = document.forms[f][i];
            if (elt.type != "hidden" && elt.disabled != true && elt.id != 'enableEnterSubmit') {
                try {
                    elt.focus();
                    return;
                } catch(er) {
                }
            }
        }
    }
}

function handleMultiDelete(confirmationMessage, submitAction) {
    // PO-3390: Only display confirmation if at least one box is checked.
    // If nothing is checked, submit to the action class without warnings.
    // Action class will handle this sitation and will come back with a message.
    var atLeastOne = false;
    var input_box = false;
    
    $(document.forms[0]).getInputs().each(function(el) {
        if (el.name=='objectsToDelete' && el.checked) {
            atLeastOne = true;
        }
    });
    
    if (atLeastOne) {
        input_box = confirmationMessage!=''?confirm(confirmationMessage):true;
    }
    
    if (input_box || !atLeastOne) {
        if (document.forms[0].page!=undefined) {     
            document.forms[0].page.value = "Delete";
        }
        document.forms[0].action = submitAction;
        document.forms[0].submit();
    } else {
    	$(document.forms[0]).getInputs().each(function(el) {
            if (el.name=='objectsToDelete') {
            	el.checked = false;
            }
        });
    }
    
}


var deleteAllToggled = false;
function toggleDeleteCheckboxes() {
	deleteAllToggled = !deleteAllToggled;
	$(document.forms[0]).getInputs().each(function(el) {
        if (el.name=='objectsToDelete') {
        	el.checked = deleteAllToggled;
        }
    });
    $$('[name="multiDeleteBtnText"]').each(function(n) {
        n.innerHTML = deleteAllToggled?'Deselect All':'Select All';
    });
}



/*
If you want to use this script, please keep the original author in this header!

Purpose:	Script for applying maxlengths to textareas and monitoring their character lengths.
Author: 	James O'Cull (adjustments for CTRP by Denis Krylov)
Date: 		08/14/08

To use, simply apply a maxlenth value to a textarea.
If you need it to prevent typing past a certain point, add lengthcut="true"

Example:
<textarea maxlength="1000" lengthcut="true"></textarea>

If you add a new text area with javascript, simply call parseCharCounts() again find the new textarea(s) and label them!
*/

var LabelCounter = 0;

function parseCharCounts()
{
	//Get Everything...
	var elements = $$('.charcounter');;
	var element = null;	
	var newlabel = null;
	
	for(var i=0; i < elements.length; i++)
	{
		element = elements[i];
		
		if(element.getAttribute('maxlength') != null && element.getAttribute('limiterid') == null)
		{
			//Create new label
			newlabel = $(document.createElement('span'));
			newlabel.id = 'limitlbl_' + LabelCounter;
			newlabel.addClassName('info');
			newlabel.addClassName('charcounter');
			newlabel.innerHTML = "";
			
			//Attach limiter to our textarea
			element.setAttribute('limiterid', newlabel.id);
			element.observe('keyup', function(event) { displayCharCounts(this, event); });
			element.observe('change', function(event) { displayCharCounts(this, event); });
			
			if (element.nextSibling!=null) {
				element.parentNode.insertBefore(newlabel, element.nextSibling);
			} else {
				element.parentNode.appendChild(newlabel);
			}
			
			// Force the update now!
			displayCharCounts(element);
		}
		
		//Push up the number
		LabelCounter++;
	}
}

function displayCharCounts(element, event)
{
	var limitLabel = $(element.getAttribute('limiterid'));
	var maxlength = element.getAttribute('maxlength');	
	
	//Replace \r\n with \n then replace \n with \r\n
	//Can't replace \n with \r\n directly because \r\n will be come \r\r\n

	//We do this because different browsers and servers handle new lines differently.
	//Internet Explorer and Opera say a new line is \r\n
	//Firefox and Safari say a new line is just a \n
	//ASP.NET seems to convert any plain \n characters to \r\n, which leads to counting issues
	var value = element.value.replace(/\u000d\u000a/g,'\u000a').replace(/\u000a/g,'\u000d\u000a');
	var currentLength = value.length;
	var remaining = 0;
	
	if(maxlength == null || limitLabel == null)
	{
		return true;
	}
	remaining = maxlength - currentLength;
	
	if(remaining >= 0)
	{		
		limitLabel.innerHTML = remaining + ' character';
		if(remaining != 1)
			limitLabel.innerHTML += 's';
		limitLabel.innerHTML += ' left';
	}
	else
	{
		limitLabel.innerHTML = '0 characters left';
		if (element.disabled != true && element.readOnly != true && element.readonly != true) {
			value = value.substring(0, maxlength);
			element.value = value;
			element.setSelectionRange(maxlength, maxlength);			
		}
	}
}

//Go find our textareas with maxlengths and handle them when we load!
Event.observe(window, "load", parseCharCounts);

function displayWaitPanel() {	
	// retrieve required dimensions	
	var eltDims     = $('progress_indicator_panel').getDimensions();
	var browserDims = $(document).viewport.getDimensions();
	// To adjust the window to the center of the viewport, when the page is scrolled down/right
	var scrollTop = $(document).viewport.getScrollOffsets()['top']
	var scrollLeft = $(document).viewport.getScrollOffsets()['left']
	// calculate the center of the page using the browser and element dimensions
	var y  = scrollTop + (browserDims.height - eltDims.height) / 2;
	var x = scrollLeft + (browserDims.width - eltDims.width) / 2;	
	
	$('progress_indicator_panel').absolutize();	
	$('progress_indicator_panel').style.left = x + 'px';
	$('progress_indicator_panel').style.top = y + 'px';
	$('progress_indicator_panel').show();
}

function hideWaitPanel() {
	if ($('progress_indicator_panel')==null) {
		return;
	}
	$('progress_indicator_panel').hide();
}

Event.observe(window, "load", function() {
	var rudMenuLink = $('registeredUserDetailsMenuOption');
	if (rudMenuLink!=null) {
		Event.observe(rudMenuLink, "click", displayWaitPanel);
	}
});


function displayPersonDetails(personID){
    var width = 700;
    var height = 550;
    showPopWin('personsSearchshowDetailspopup.action?personID='+personID, width, height, '', 'Person Details');
}

function displayOrgDetails(orgID) {
    var width = 650;
    var height = 450;
    if (Prototype.Browser.IE) {
        width = 670;
        height = 500;                   
    }
    showPopWin('organizationsSearchshowDetailspopup.action?orgID='+orgID, width, height, '', 'Organization Details');               
}

function extractPhoneNumberNoExt(phone) {
	return phone!=null?phone.gsub(/(ext.*|x.*)$/i,'').strip():'';
}

function extractPhoneNumberExt(phone) {
	if (phone!=null) {
		var ext = phone.gsub(/^(.*extn|.*ext|.*x)/i,'');
		if (ext!=phone) {
			return ext.strip();
		}
	}
	return '';
}


function handleTrialSelect(trialId, trialNciId) {
	window.top.hidePopWin(true); 
	if (window.parent.trialSelected != undefined) {
		window.parent.trialSelected(trialId, trialNciId);
	}
}

function cancelAction(url){
    document.forms[0].setAttribute("action", url);
    document.forms[0].submit();
}

//Validates that the input string is a valid date formatted as "mm/dd/yyyy"
function isValidDate(dateString)
{
    // First check for the pattern
    if(!/^\d{1,2}\/\d{1,2}\/\d{4}$/.test(dateString))
        return false;

    // Parse the date parts to integers
    var parts = dateString.split("/");
    var day = parseInt(parts[1], 10);
    var month = parseInt(parts[0], 10);
    var year = parseInt(parts[2], 10);

    // Check the ranges of month and year
    if(year < 1000 || year > 3000 || month == 0 || month > 12)
        return false;

    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        monthLength[1] = 29;

    // Check the range of the day
    return day > 0 && day <= monthLength[month - 1];
}

/**
 * Validates a phone number according to US format. 
 * @param phone
 * @returns {Boolean}
 */
function validateUSPhoneNumber(phone) {	
	var re = /^[0-9]{3}-[0-9]{3}-[0-9]{4}$/;

    if (!re.exec(phone)) {        
        return false;
    }
    return true;
}

/**
 * Validates a email using regex
 * @param email
 */
function validateEmailWithRegex(email) {
	var re = /^[0-9a-zA-Z\-\_\.\@]*$/;

    if (!re.exec(email)) {        
        return false;
    }
    return true;
}

/**
 * Validates a extension for phone using regex
 * @param ext
 * @returns {Boolean}
 */
function validateExtWithRegex(ext) {
	var re = /^[0-9]*$/;

    if (!re.exec(ext)) {        
        return false;
    }
    return true;	
}
