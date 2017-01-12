<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="col-xs-8" style="padding-top: 7px">
<s:set var="sponsorOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
<s:hidden id="trialDTO.sponsorName" name="trialDTO.sponsorName"/>
<div class="collapse navbar-collapse organization-dropdown" style="padding-left:0px;">
        <div class="nav navbar-nav" style="width: 100%;">
          <div class="active dropdown"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="trialDTO.sponsorNameField">Please Select the Sponsor Organization <b class="caret"></b></a>  
            <table class="dropdown-menu" id="dropdown-sponsorOrganization">
            	<tr><th>PO ID</th><th>CTEP ID</th><th>Name</th></tr>
            	<s:iterator var="orgItem" value="#sponsorOrgs">
            	<s:if test="%{#orgItem.getType() < 0}">
            		<tr><td colspan="3"><hr/></td></tr>
            	</s:if>
            	<s:else>
        			<tr><td><a href="javascript:void(0)" onclick="lookup4sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getPoId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getCtepId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getHTMLName()"/></a></td></tr>
        		</s:else>
        		</s:iterator>
        		<tr><td colspan="3"><a href="javascript:void(0)" onclick="lookup4sponsor(-1, '')">Search...</a></td></tr>
            </table>
          </div>
      </div>
</div>
<span class="alert-danger" id="sponsorErr"> 
     <s:fielderror>
        <s:param>trialDTO.sponsorIdentifier</s:param>
    </s:fielderror>                            
</span>
</div>