function validate() {
    var box = document.forms[0].j_username;
    
    re = /^[0-9a-zA-Z\_\.\-]*$/;

    if (!re.exec(box.value)) {
        alert("Invalid Entry:\nOnly Alphanumeric, _ or . are allowed.");
        return false;
    }
    return true;
}