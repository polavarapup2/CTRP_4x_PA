	function toggledisplay (it, box) {
		var vis = (box.checked) ? "none" : "block";
		document.getElementById(it).style.display = vis;
	}

	function toggledisplay2 (it) {
		var vis = document.getElementById(it).style.display
		if (vis == "block") { 
			document.getElementById(it).style.display = "none"; 
		} else { 
			document.getElementById(it).style.display = "block"; 
		}
	} 
	
	function toggledisplayDivs(val) {
	  var vis = val.value;
	  if (vis == 'false') { 
	     document.getElementById('regDiv').style.display = "none"; 
	     document.getElementById('sponsorDiv').style.display = "none"; 
	  } else { 
	     document.getElementById('regDiv').style.display = "block"; 
	     document.getElementById('sponsorDiv').style.display = "block"; 
	     }
	}

	function ToggleNav(theitem) {
		if (document.getElementById(theitem).style.display == "none") {
			document.getElementById(theitem).style.display = "block"
		} else {
			document.getElementById(theitem).style.display = "none"
		}
	}

	function displayTrialStatusDefinition(selectBoxId) {
		$('allTrialStatusDefinitions').childElements().invoke('hide');
		var selectedValue = $(selectBoxId).value;
		if (selectedValue != "") {
		    $(selectedValue).show();
		}
	}