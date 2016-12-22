<i data-trigger="hover" data-placement="right" data-content="<fmt:message key="tooltip.contact_details"/>" rel="popover" id="popover" name="popover" class="fa-info-circle help-text" data-original-title="" title=""></i>
 <div class="form-group">
   <label for="registryUserWebDTO.emailAddress" class="col-xs-4 control-label"><fmt:message key="register.user.emailAddress"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="email" cssClass="form-control" id="registryUserWebDTO.emailAddress" name="registryUserWebDTO.emailAddress"  maxlength="255" min="35" placeholder="example@email.com"/>
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.emailAddress</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.firstName" class="col-xs-4 control-label"><fmt:message key="register.user.firstName"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.firstName"  name="registryUserWebDTO.firstName"  maxlength="200" />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.firstName</s:param>
           </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.middleName" class="col-xs-4 control-label"><fmt:message key="register.user.middleInitial"/></label>
   <div class="col-xs-2">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.middleName" name="registryUserWebDTO.middleName"  maxlength="2" />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.middleName</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.lastName" class="col-xs-4 control-label"><fmt:message key="register.user.lastName"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.lastName"  name="registryUserWebDTO.lastName"  maxlength="200"  />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.lastName</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.addressLine" class="col-xs-4 control-label"><fmt:message key="register.user.streetAddress"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.addressLine" name="registryUserWebDTO.addressLine"  maxlength="200"  />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.addressLine</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.city" class="col-xs-4 control-label"><fmt:message key="register.user.city"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.city" name="registryUserWebDTO.city"  maxlength="200" />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.city</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.state" class="col-xs-4 control-label"><fmt:message key="register.user.state"/> <span class="required">*</span></label>
   <div class="col-xs-5">
   	 <s:set name="stateCodeValues" value="@gov.nih.nci.pa.enums.USStateCode@getDisplayNames()" />
     <s:select id="registryUserWebDTO.state" headerKey="" headerValue="--Select--"
            name="registryUserWebDTO.state"
            list="#stateCodeValues"
            value="registryUserWebDTO.state"
            cssClass="form-control" />
        <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.state</s:param>
           </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.postalCode" class="col-xs-4 control-label"><fmt:message key="register.user.zipCode"/> <span class="required">*</span></label>
   <div class="col-xs-3">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.postalCode" name="registryUserWebDTO.postalCode"  maxlength="15"  />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.postalCode</s:param>
           </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.country" class="col-xs-4 control-label"><fmt:message key="register.user.country"/> <span class="required">*</span></label>
   <div class="col-xs-5">
     <s:set name="countries"
                value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().
                getCountries()" />
        <s:select headerKey="United States" headerValue="United States"
        		 id="registryUserWebDTO.country"
                 name="registryUserWebDTO.country"
                 list="#countries"
                 listKey="name"
                 listValue="name"
                 value="registryUserWebDTO.country"
                 cssClass="form-control"/>
        <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.country</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.phone" class="col-xs-4 control-label"><fmt:message key="register.user.phone"/> <span class="required">*</span></label>
   <div class="col-xs-3">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.phone" name="registryUserWebDTO.phone"  maxlength="50"  placeholder="XXX-XXX-XXXX"/>
     <span class="alert-danger">
           <s:fielderror>
               <s:param>registryUserWebDTO.phone</s:param>
           </s:fielderror>
       </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.affiliateOrg" class="col-xs-4 control-label"><fmt:message key="register.user.affiliateOrg"/> <span class="required">*</span></label>
   <div class="col-xs-7">
   <s:set var="accountOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getAccountOrganization()" />
   	 <s:hidden name="registryUserWebDTO.affiliatedOrganizationId" id="registryUserWebDTO.affiliatedOrganizationId"/>
   	 <s:hidden name="registryUserWebDTO.affiliateOrg" id="registryUserWebDTO.affiliateOrg"/>
<div class="collapse navbar-collapse organization-dropdown">
        <div class="nav navbar-nav" style="width: 100%;">
          <div class="active dropdown"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="registryUserWebDTO.affiliateOrgField">Please Select an Organization Affiliation. <b class="caret"></b></a>
          <div  id="dropdown-affiliateOrganization" class="dropdown-menu" style="height: 200px !important; overflow-y:auto;">    
            <table>
            	<tr><th>PO ID</th><th>CTEP ID</th><th>Name</th></tr>
            	<s:iterator var="orgItem" value="#accountOrgs">
            	<s:if test="%{#orgItem.getType() < 0}">
            		<tr><td colspan="3"><hr/></td></tr>
            	</s:if>
            	<s:else>
        			<tr><td><a href="javascript:void(0)" onclick="lookup4AffiliateOrg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getPoId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4AffiliateOrg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getCtepId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4AffiliateOrg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getName()"/></a></td></tr>
        		</s:else>
        		</s:iterator>
        		<tr><td colspan="3"><a href="javascript:void(0)" onclick="lookup4AffiliateOrg(-1, '')">Search...</a></td></tr>
            </table>
            </div>
          </div>
      </div>
</div>
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.affiliateOrg</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 
 <div id="adminAccessDiv">
     <%@ include file="/WEB-INF/jsp/nodecorate/adminUsers.jsp" %>
 </div>
 
 <div class="form-group">
   <label for="registryUserWebDTO.prsOrgName" class="col-xs-4 control-label"><fmt:message key="register.user.prsOrgName"/></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.prsOrgName"  name="registryUserWebDTO.prsOrgName"  maxlength="200" />
     <span class="alert-danger">
            <s:fielderror>
                <s:param>registryUserWebDTO.prsOrgName</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.enableEmails" class="col-xs-4 control-label"><fmt:message key="register.user.enableEmails"/></label>
   <div class="col-xs-7">
   <s:radio cssClass="radio-inline" id="registryUserWebDTO.enableEmails" name="registryUserWebDTO.enableEmails" list="#{false:'No', true:'Yes'}"  value="registryUserWebDTO.enableEmails"/>
    </div>
 </div>