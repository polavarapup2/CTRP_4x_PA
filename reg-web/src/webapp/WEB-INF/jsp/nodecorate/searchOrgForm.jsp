<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<head>
<SCRIPT LANGUAGE="JavaScript">
function formReset(){
    document.getElementById("orgNameSearch").value = '';
    document.getElementById("orgFamilyNameSearch").value = '';
    document.getElementById("orgCitySearch").value = '';
    document.getElementById("orgStateSearch").value = '';
    document.getElementById("orgCountrySearch").value = 'USA';
    document.getElementById("orgZipSearch").value = '';
    document.getElementById("orgCtepIdSearch").value = '';
    document.getElementById("orgPOIdSearch").value = '';
}
</SCRIPT>
</head>

<p align="center" class="info">
    Type a string of characters in any of the text fields in the upper frame, or, 
    enter the exact PO ID, or CTEP ID in the lower frame.
</p> 
</p>
<div class="container">
<div class="form-group">
    <label for="orgNameSearch" class="col-xs-3 control-label"><fmt:message key="popUpOrg.name"/></label>
    <div class="col-xs-3">
        <s:textfield id="orgNameSearch" name="orgSearchCriteria.orgName" maxlength="200" cssClass="form-control" />
    </div>
   	<label for="orgCitySearch" class="col-xs-3 control-label"><fmt:message key="popUpOrg.city"/></label>
    <div class="col-xs-3">
        <s:textfield id="orgCitySearch" name="orgSearchCriteria.orgCity" maxlength="200" cssClass="form-control" />
    </div>
</div>

<div class="form-group">
    <label for="orgFamilyNameSearch" class="col-xs-3 control-label"><fmt:message key="popUpOrg.familyName"/></label>
    <div class="col-xs-3">
        <s:textfield id="orgFamilyNameSearch" name="orgSearchCriteria.familyName" maxlength="200" cssClass="form-control" />
    </div>
   	<label for="orgCountrySearch" class="col-xs-3 control-label"><fmt:message key="popUpOrg.country"/></label>
    <div class="col-xs-3">
        <s:select id="orgCountrySearch" name="orgSearchCriteria.orgCountry" list="countryList"
            listKey="alpha3" listValue="name" headerKey="" headerValue="" cssClass="form-control" />
    </div>
</div>

<div class="form-group">
    <label for="orgStateSearch" class="col-xs-3 control-label"><fmt:message key="popUpOrg.state"/></label>
    <div class="col-xs-3">
        <s:textfield id="orgStateSearch" name="orgSearchCriteria.orgState" maxlength="55" size="30" cssClass="form-control" />
        <span class="info">please enter two letter identifier for US states, for ex: 'MD' for Maryland</span>
    </div>
   	<label for="orgZipSearch" class="col-xs-3 control-label"><fmt:message key="popUpOrg.zip"/></label>
    <div class="col-xs-3">
        <s:textfield id="orgZipSearch" name="orgSearchCriteria.orgZip" maxlength="15" size="20" cssClass="form-control" />
         <span class="info">&nbsp;</span>
    </div>
</div>
</div>

<hr>
<div class="container">
<div class="form-group">
    <label for="orgPOIdSearch" class="col-xs-3 control-label">PO ID (Exact Match):</label>
    <div class="col-xs-3">
        <s:textfield id="orgPOIdSearch" name="orgSearchCriteria.id" maxlength="10" size="100" cssClass="form-control" />
    </div>
   	<label for="orgCtepIdSearch" class="col-xs-3 control-label">CTEP ID:</label>
    <div class="col-xs-3">
        <s:textfield id="orgCtepIdSearch" name="orgSearchCriteria.ctepId" maxlength="200" size="100" cssClass="form-control" />
    </div>
</div>
</div>

<hr>
<div class="bottom align-center">
	  <button type="button" class="btn btn-icon btn-primary" onclick="loadDiv();" id="search_organization_btn"><i class="fa-search"></i>Search</button>
	  <button type="button" class="btn btn-icon btn-primary" onclick="setCreateFormVisible();" id="add_organization_btn"><i class="fa-plus"></i>Add Org</button>
	  <button type="button" class="btn btn-icon btn-default" onclick="formReset();" id="search_organization_reset_btn"><i class="fa-repeat"></i>Reset</button>
	  <button type="button" class="btn btn-icon btn-default" onclick="window.parent.hidePopWin(false);" data-dismiss="modal" aria-hidden="true" id="search_organization_close_btn"><i class="fa-times-circle"></i>Cancel</button>
</div>
