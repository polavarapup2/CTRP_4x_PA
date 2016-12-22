    function submitform(orgid, name) {
        window.parent.setorgid(orgid, name);
        window.parent.hidePopWin(true); 
    }

    function submitform(orgid, name, p30grant) {
        window.parent.setorgid(orgid, name, p30grant);
        window.parent.hidePopWin(true); 
    }

    function setSearchFormVisible() {
        document.getElementById("searchOrgJsp").style.display="";
        document.getElementById("createOrgJsp").style.display="none";
    }

    function setCreateFormVisible() {
        document.getElementById("searchOrgJsp").style.display="none";
        document.getElementById("createOrgJsp").style.display="";
    }   
    
    function closePopup() {
        window.parent.hidePopWin(false);
    }

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