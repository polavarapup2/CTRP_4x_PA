<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
  <script language="JavaScript">

   function alertMsg(val) {
        if(val.value == 'USA')
        {
         document.getElementById('orgStateDivSelect').style.display = '';
         document.getElementById('orgStateDivText').style.display = 'none';
        } else {
         document.getElementById('orgStateDivSelect').style.display = 'none';
         document.getElementById('orgStateDivText').style.display = '';
        
        }
      }  
      
   </script>
<div class="container">
<h2>Add Organization</h2>
	<div class="form-group">
	    <label for="poOrganizations_createOrg_orgName;" class="col-xs-3 control-label">Organization Name :<span class="required">*</span></label>
	    <div class="col-xs-3">
	        <s:textfield  id = "orgName"  name="createOrg.orgName;" maxlength="159" size="100" cssClass="form-control" />
	    </div>
	   	<label for="poOrganizations_createOrg_orgCountry;" class="col-xs-3 control-label">Country :<span class="required">*</span></label>
	    <div class="col-xs-3">
	        <s:select id = "orgCountry" name="createOrg.orgCountry;"  list="countryList" listKey="alpha3" listValue="name" 
	             headerKey="USA" headerValue="United States" onblur="alertMsg(this);" onclick="alertMsg(this);" cssClass="form-control" />
	    </div>
	</div>
	<div class="form-group">
	    <label for="poOrganizations_createOrg_orgStreetAddress;" class="col-xs-3 control-label">Street Address :<span class="required">*</span></label>
	    <div class="col-xs-3">
	        <s:textfield id ="orgAddress" name="createOrg.orgStreetAddress;"  maxlength="254" size="100" cssClass="form-control" />
	    </div>
	   	<label for="poOrganizations_createOrg_orgCity;" class="col-xs-3 control-label">City :<span class="required">*</span></label>
	    <div class="col-xs-3">
	        <s:textfield id="orgCity" name="createOrg.orgCity;" maxlength="50" size="100"  cssClass="form-control" />
	    </div>
	</div>	
	<div class="form-group">
	    <label for="poOrganizations_createOrg_orgState;" class="col-xs-3 control-label">State :</label>
	    <s:set name="usStates" value="@gov.nih.nci.pa.enums.USStateCode@getAbbreviatedNames()" />
	    <div class="col-xs-3">
	        <div id="orgStateDivSelect" style="display:''">
		      <s:select id="orgStateSelect" name="createOrg.orgState" headerKey="" headerValue="-Select a State-"  list="#usStates" cssClass="form-control"/>
		    </div>
		    <div id="orgStateDivText" style="display:none">
		       <s:textfield id ="orgStateText" name="createOrg.orgState"  maxlength="50" size="100" cssClass="form-control"/>
		       <span class="tiny">(2-letter state code for Canada <br> and 2 or 3-letter state code for Australia.)</span>
		   </div>
	    </div>
	   	<label for="poOrganizations_createOrg_orgZip;" class="col-xs-3 control-label">Zip :</label>
	    <div class="col-xs-3">
	        <s:textfield id="orgZip" name="createOrg.orgZip;" maxlength="20" size="100"  cssClass="form-control" />
	    </div>
	</div>	
	<div class="form-group">
	    <label for="poOrganizations_createOrg_orgEmail;" class="col-xs-3 control-label">Email :</label>
	    <div class="col-xs-3">
	        <s:textfield id="orgEmail" name="createOrg.orgEmail;"  maxlength="254" size="100" cssClass="form-control" />
	    </div>
	   	<label for="poOrganizations_createOrg_orgPhone" class="col-xs-3 control-label">Phone Number :</label>
	    <div class="col-xs-3">
	        <s:textfield  id="orgPhone"  name="createOrg.orgPhone" maxlength="30" size="100" cssClass="form-control" />
	    </div>
	</div>
	<div class="form-group">
	    <label for="poOrganizations_createOrg_orgURL;" class="col-xs-3 control-label">URL :</label>
	    <div class="col-xs-3">
	        <s:textfield id="orgUrl" name="createOrg.orgURL;" maxlength="254" size="100"  cssClass="form-control" />
	    </div>
	   	<label for="poOrganizations_createOrg_orgTTY;" class="col-xs-3 control-label">TTY :</label>
	    <div class="col-xs-3">
	        <s:textfield id="orgTty" name="createOrg.orgTTY;"  maxlength="30" size="100" cssClass="form-control" />
	    </div>
	</div>
	<div class="form-group">
	    <label for="poOrganizations_createOrg_orgFax;" class="col-xs-3 control-label">Fax Number :</label>
	    <div class="col-xs-3">
	        <s:textfield id="orgFax"  name="createOrg.orgFax;"  maxlength="30" size="100" cssClass="form-control" />
	    </div>
	</div>
	
</div>

<hr>
<div class="bottom align-center">
	  <button type="button" class="btn btn-icon btn-primary" onclick="createOrg()"><i class="fa-floppy-o"></i>Save</button>
	  <button type="button" class="btn btn-icon btn-primary" onclick="setSearchFormVisible();"><i class="fa-search"></i>Search</button>
	  <button type="button" class="btn btn-icon btn-default" onclick="window.parent.hidePopWin();" id="search_organization_close_btn"><i class="fa-times-circle"></i>Cancel</button>
</div>
