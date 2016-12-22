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


// Utility functions.
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

function hideWaitPanel() {
	$('progress_indicator_panel').hide();
}

function displayWaitPanel() {	
	if ($('progress_indicator_panel')!=null) {
		// retrieve required dimensions	
		var eltDims     = $('progress_indicator_panel').getDimensions();
		var browserDims = $(document).viewport.getDimensions();
		 
		// calculate the center of the page using the browser and element dimensions
		var y  = (browserDims.height - eltDims.height) / 2 + $(document).viewport.getScrollOffsets().top;
		var x = (browserDims.width - eltDims.width) / 2;	
		
		$('progress_indicator_panel').absolutize();	
		$('progress_indicator_panel').style.left = x + 'px';
		$('progress_indicator_panel').style.top = y + 'px';
		$('progress_indicator_panel').show();
	}
}

function showProgressIndicatorForPaginationLinks() {
	$$('span.pagelinks > a[href]').each(function (e) {
		$(e).observe('click', displayWaitPanel);
	});
}

function hidePrimaryCompletionDate() {
	//don't validate primary completion date if it is non interventional trial 
    //and CTGovXmlRequired is false.
	if ($('trialDTO.trialType.Noninterventional').checked && $('xmlRequiredfalse').checked) {		
		document.getElementById('primaryCompletionDateId').style.display = 'none';
	} else {
		document.getElementById('primaryCompletionDateId').style.display = 'inline';
	}	
}

function setDisplayBasedOnTrialType() {
	if ($('trialDTO.trialType.Interventional').checked) {
		hideElements('.non-interventional');
		showElements('.interventional');
		disableElements('.non-interventional-input-ctr');
		enableElements('.interventional-input-ctr');		
	} else {
		showElements('.non-interventional');
        hideElements('.interventional');
        enableElements('.non-interventional-input-ctr');
        disableElements('.interventional-input-ctr');        
	}
	if (typeof displayPrimaryPurposeOtherCode=='function') {
		displayPrimaryPurposeOtherCode();
	}
	if (typeof displaySecondaryPurposeOtherCode=='function') {
		displaySecondaryPurposeOtherCode();
	}
	if (!($('trialDTO.trialType.Interventional').checked) && $('secondaryPurposeOtherTextDiv')!=null) {
		$('secondaryPurposeOtherTextDiv').hide();
        document.getElementById('trialDTO.secondaryPurposeOtherText').disabled = true;   		
	}
}

function displayStudyAlternateTitles(studyProtocolId) {
	var width = 300;
    var height = 300;
    if (Prototype.Browser.IE) {
        width = 250;
        height = 250;                   
    }
    showPopWin('searchTrialpopUpStudyAlternateTitles.action?studyProtocolId='+studyProtocolId, width, height, '', 'Trial Alternate Titles');
}

function hideElements(cssRule) {
	$$(cssRule).each(function (e) {
		e.hide();
	});
}

function showElements(cssRule) {
    $$(cssRule).each(function (e) {
        e.show();
    });
}

function disableElements(cssRule) {
	$$(cssRule).each(function (e) {
		e.disable();
	});
}

function enableElements(cssRule) {
    $$(cssRule).each(function (e) {
        e.enable();
    });
}

function disableTrialTypeChangeRadios() {
	if (!$('trialDTO.trialType.Interventional').checked) {
		$('trialDTO.trialType.Interventional').disabled = true;		
	} else if (!$('trialDTO.trialType.Noninterventional').checked) {
		$('trialDTO.trialType.Noninterventional').disabled = true;		
	} 
}

function trim(val) {
    var ret = val.replace(/^\s+/, '');
    ret = ret.replace(/\s+$/, '');
    return ret;
}
