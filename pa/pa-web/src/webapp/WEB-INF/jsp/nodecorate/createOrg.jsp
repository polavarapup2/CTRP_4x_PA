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
<h2>Add Organization</h2>
<s:set name="usStates" value="@gov.nih.nci.pa.enums.USStateCode@getAbbreviatedNames()" />
<table  class="form">  
   <tr>     
        <td scope="row" class="label"><label for="orgName"> Organization Name :</label><span class="required">*</span></td><td><s:textfield  id = "orgName"  name="orgName;" maxlength="159" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="orgCountry">Country :</label><span class="required">*</span></td><td>
            <s:select
             id = "orgCountry"
             name="countryName;" 
             list="countryList" 
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" onblur="alertMsg(this);" cssStyle="width:206px" onclick="alertMsg(this);"/>
        </td>
        </tr>
   <tr>
        <td scope="row" class="label"><label for="orgAddress">Street Address :</label><span class="required">*</span></td><td><s:textfield id = "orgAddress" name="orgStAddress"  maxlength="254" size="100"  cssStyle="width:200px" /></td>       
        <td scope="row" class="label"><label for="orgCity">City :</label><span class="required">*</span></td><td><s:textfield id="orgCity" name="cityName" maxlength="50" size="100"  cssStyle="width:200px" /></td>        
   </tr>
   <tr> 
   <td scope="row" class="label"><label for="orgStateSelect">State :<br></label></td>
   <td>
    <div id="orgStateDivSelect" style="display:''">
      <s:select id="orgStateSelect" name="stateName" headerKey="" headerValue="-Select a State-"  list="#usStates" />
    </div>
    <div id="orgStateDivText" style="display:none">
       <s:textfield id ="orgStateText" name="stateName"  maxlength="50" size="100"  cssStyle="width:200px"/>
       <br><span class="tiny">(2-letter state code for Canada <br> and 2 or 3-letter state code for Australia.)</span>
   </div>    
  </td>    
    <td scope="row" class="label"><label for="orgZip">ZIP :</label></td><td><s:textfield id="orgZip" name="zipCode" maxlength="20" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="orgEmail">Email :</label></td><td><s:textfield id="orgEmail" name="email;"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="orgPhone">Phone Number :</label></td><td><s:textfield  id="orgPhone"  name="telephone" maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="orgUrl">URL :</label></td><td><s:textfield id="orgUrl" name="url" maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="orgTty">TTY :</label></td><td><s:textfield id="orgTty" name="tty"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="orgFax">Fax Number :</label></td><td><s:textfield id="orgFax"  name="fax;"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>       
</table>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="createOrg()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Search</span></span></s:a>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
       </li>
       </ul>   
  </del>
  </div>