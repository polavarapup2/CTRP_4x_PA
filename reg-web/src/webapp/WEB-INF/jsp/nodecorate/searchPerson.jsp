<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<SCRIPT LANGUAGE="JavaScript">
function formReset(){
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('city').value = '';
    document.getElementById('state').value = '';
    document.getElementById('country').value = 'USA';
    document.getElementById('zip').value = '';
    document.getElementById('email').value = '';
    document.getElementById('perCtepIdSearch').value = '';
    document.getElementById('perPOIdSearch').value = '';
}

function formClose() {
    var winObj = window.top;
    if (window.parent!=window.top && window.parent!=window.self) {
        winObj = window.parent;
    }
	winObj.hidePopWin();	
}

</SCRIPT>
</head>
<p align="center" class="info">
        Type a string of characters in any of the text fields in the upper frame OR
        enter PO ID or CTEP Identifier in the lower frame.
    <br>
    Please do not use wildcard characters.<br>

</p>

<div class="container">
<div class="form-group">
    <label for="firstName" class="col-xs-3 control-label">First Name:</label>
    <div class="col-xs-3">
        <s:textfield name="personDTO.firstName" id="firstName" maxlength="200"  cssClass="form-control" />
    </div>
   	<label for="lastName" class="col-xs-3 control-label">Last Name:</label>
    <div class="col-xs-3">
        <s:textfield name="personDTO.lastName"  id="lastName" maxlength="200"  cssClass="form-control" />
    </div>
</div>
<div class="form-group">
    <label for="email" class="col-xs-3 control-label">Email:</label>
    <div class="col-xs-3">
        <s:textfield name="personDTO.email" id="email" maxlength="200" cssClass="form-control" />
    </div>
</div>
<div class="form-group">
    <label for="city" class="col-xs-3 control-label">City:</label>
    <div class="col-xs-3">
        <s:textfield name="personDTO.city"  id="city" maxlength="200" cssClass="form-control" />
    </div>
    <label for="state" class="col-xs-3 control-label">State:</label>
    <div class="col-xs-3">
        <s:textfield name="personDTO.state"  id="state" maxlength="200" cssClass="form-control" />
        <span class="info">please enter two letter identifier for US states<br> for ex: 'MD' for Maryland</span>
    </div>
</div>
<div class="form-group">
    <label for="zip" class="col-xs-3 control-label">Zip:</label>
    <div class="col-xs-3">
        <s:textfield name="personDTO.zip"  id="zip" maxlength="200" cssClass="form-control" />
    </div>
    <label for="country" class="col-xs-3 control-label">Country:</label>
    <div class="col-xs-3">
        <s:select
                name="personDTO.country"
                list="countryList"
                id = "country"
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssClass="form-control" />
    </div>
</div>
</div>
<hr>
<div class="container">
<div class="form-group">
    <label for="perPOIdSearch" class="col-xs-3 control-label">PO ID (Exact Match):</label>
    <div class="col-xs-3">
        <s:textfield id="perPOIdSearch" name="perPOIdSearch" maxlength="10"  cssClass="form-control" />
    </div>
   	<label for="perCtepIdSearch" class="col-xs-3 control-label">CTEP Identifier:</label>
    <div class="col-xs-3">
        <s:textfield name="perCtepIdSearch" id="perCtepIdSearch" maxlength="200" cssClass="form-control" />
    </div>
</div>
</div>

<hr>
<div class="bottom align-center">
	  <button type="button" class="btn btn-icon btn-primary" onclick="loadDiv();" id="search_person_btn"><i class="fa-search"></i>Search</button>
	  <button type="button" class="btn btn-icon btn-primary" onclick="setCreateFormVisible();" id="add_person_btn"><i class="fa-plus"></i>Add Person</button>
	  <button type="button" class="btn btn-icon btn-default" onclick="formReset();" id="search_person_reset_btn"><i class="fa-repeat"></i>Reset</button>
	  <button type="button" class="btn btn-icon btn-default" onclick="formClose();"data-dismiss="modal" aria-hidden="true" id="search_person_close_btn"><i class="fa-times-circle"></i>Cancel</button>
</div>

