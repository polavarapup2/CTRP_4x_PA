<div class="col-xs-8">
<s:set var="siteOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
<s:hidden id="trialDTO.siteOrganizationName" name="trialDTO.siteOrganizationName"/>
<div class="collapse navbar-collapse organization-dropdown">
        <div class="nav navbar-nav" style="width: 100%;">
          <div class="active dropdown"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="trialDTO.siteOrganizationNameField">Please Select Submitting Organization<b class="caret"></b></a>  
            <table class="dropdown-menu" id="dropdown-siteOrganization">
                <tr><th>PO ID</th><th>CTEP ID</th><th>Name</th></tr>
                <s:iterator var="orgItem" value="#siteOrgs">
                <s:if test="%{#orgItem.getType() < 0}">
                    <tr><td colspan="3"><hr/></td></tr>
                </s:if>
                <s:else>
                    <tr><td><a href="javascript:void(0)" onclick="lookup4loadSiteOrg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getPoId()"/></a></td>
                    <td><a href="javascript:void(0)" onclick="lookup4loadSiteOrg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getCtepId()"/></a></td>
                    <td><a href="javascript:void(0)" onclick="lookup4loadSiteOrg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getHTMLName()"/></a></td></tr>
                </s:else>
                </s:iterator>
                <tr><td colspan="3"><a href="javascript:void(0)" onclick="lookup4loadSiteOrg(-1, '')">Search...</a></td></tr>
            </table>
          </div>
      </div>
</div>
<span class="alert-danger" id="sponsorErr"> 
     <s:fielderror>
        <s:param>trialDTO.siteOrganizationIdentifier</s:param>
    </s:fielderror>                            
</span>
</div>