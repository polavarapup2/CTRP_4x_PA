<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script language="JavaScript">

   function alertMsgState(val) {
        if(val.value == 'USA')
        {
         document.getElementById('poOrganizations_orgStateDivSelect').style.display = '';
         document.getElementById('poOrganizations_orgStateDivText').style.display = 'none';
        } else {
         document.getElementById('poOrganizations_orgStateDivSelect').style.display = 'none';
         document.getElementById('poOrganizations_orgStateDivText').style.display = '';
        
        }
      }  
      
   </script>
<div class="container">
<h2>Add Person</h2>
<s:set name="usStates" value="@gov.nih.nci.pa.enums.USStateCode@getAbbreviatedNames()" />
<p align="center" class="info">Please provide professional contact information only.</p><br/>
<div class="form-group">
   <label for="poOrganizations_firstName" class="col-xs-3 control-label">First Name :<span class="required">*</span></label><div class="col-xs-3"><s:textfield name="firstName"  maxlength="50" size="100"  cssClass="form-control" /></div>
   <label for="poOrganizations_lastName" class="col-xs-3 control-label">Last Name :<span class="required">*</span></label><div class="col-xs-3"><s:textfield name="lastName"  maxlength="50" size="100"  cssClass="form-control" /></div>
</div>
<div class="form-group">  
   <label for="poOrganizations_preFix" class="col-xs-3 control-label">Prefix :</label><div class="col-xs-3"><s:textfield name="preFix"  maxlength="10" size="100"  cssClass="form-control" /></div>
   <label for="poOrganizations_middleName" class="col-xs-3 control-label">Middle Name :</label><div class="col-xs-3"><s:textfield name="middleName"  maxlength="50" size="100"  cssClass="form-control" /></div>
</div>
<div class="form-group">  
   <label for="poOrganizations_suffix" class="col-xs-3 control-label">Suffix :</label><div class="col-xs-3"><s:textfield name="suffix"  maxlength="10" size="100"  cssClass="form-control" /></div>
   <label for="poOrganizations_country" class="col-xs-3 control-label">Country :<span class="required">*</span></label><div class="col-xs-3">
            <s:select
             name="country" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" onblur="alertMsgState(this);" cssClass="form-control" onclick="alertMsgState(this);" />
        </div>   
</div>       
<div class="form-group">
   <label for="poOrganizations_streetAddress" class="col-xs-3 control-label">Street Address :<span class="required">*</span></label><div class="col-xs-3"><s:textfield name="streetAddress"  maxlength="254" size="100"  cssClass="form-control"  /></div>
   <label for="poOrganizations_city" class="col-xs-3 control-label">City :<span class="required">*</span></label><div class="col-xs-3"><s:textfield name="city"  maxlength="50" size="100"  cssClass="form-control" /></div>
</div>
<div class="form-group">
     <label for="poOrganizations_orgStateSelect" class="col-xs-3 control-label">State :<br></label><div class="col-xs-3">
      <div id="poOrganizations_orgStateDivSelect" style="display:''">
       <s:select id="poOrganizations_orgStateSelect" name="state" headerKey="" headerValue="-Select a State-"  list="#usStates" cssClass="form-control" />
      </div>
      <div id="poOrganizations_orgStateDivText" style="display:none">
       <s:textfield id ="poOrganizations_orgStateText" name="state"  maxlength="50" size="100"  cssClass="form-control"/>
       <br><span class="tiny">(2-letter state code for Canada <br> and 2 or 3-letter state code for Australia.)</span>
      </div> 
     </div>
     <label for="poOrganizations_zip" class="col-xs-3 control-label">ZIP :</label><div class="col-xs-3"><s:textfield name="zip"  maxlength="20" size="100"  cssClass="form-control" /></div>
     <div class="col-xs-3"></div><div class="col-xs-3"></div>
 </div>
<div class="form-group">
    <label for="poOrganizations_email" class="col-xs-3 control-label">Email :</label><div class="col-xs-3"><s:textfield name="email"  maxlength="254" size="100"  cssClass="form-control" /></div>
    <label for="poOrganizations_phone" class="col-xs-3 control-label">Phone :</label><div class="col-xs-3"><s:textfield name="phone"  maxlength="30" size="100"  cssClass="form-control" /></div>
</div>
<div class="form-group">
    <label for="poOrganizations_url" class="col-xs-3 control-label">URL :</label><div class="col-xs-3"><s:textfield name="url"  maxlength="254" size="100"  cssClass="form-control" /></div>
    <label for="poOrganizations_tty" class="col-xs-3 control-label">TTY :</label><div class="col-xs-3"><s:textfield name="tty"  maxlength="30" size="100"  cssClass="form-control" /></div>
</div>      
<div class="form-group">
     <label for="poOrganizations_fax" class="col-xs-3 control-label">Fax :</label><div class="col-xs-3"><s:textfield name="fax"  maxlength="30" size="100"  cssClass="form-control" /></div>
</div>      
 
<p align="center" class="info">Contact information required for internal administrative use only; not revealed to public</p>
 
<div class="bottom align-center">
  <button type="button" class="btn btn-icon btn-primary" onclick="createPerson()"><i class="fa-floppy-o"></i>Save</button>
  <button type="button" class="btn btn-icon btn-primary" onclick="setSearchFormVisible();"><i class="fa-search"></i>Search</button>
  <button type="button" class="btn btn-icon btn-default" onclick="window.parent.hidePopWin();" ><i class="fa-times-circle"></i>Cancel</button>     
</div>
</div>