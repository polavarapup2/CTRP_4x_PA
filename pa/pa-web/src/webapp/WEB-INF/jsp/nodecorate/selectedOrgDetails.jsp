<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table  class="form">
		<tr>
		    <td scope="row" class="label"><label for="editOrg.name">Organization Name:</label><span class="required">*</span>
	    
		    
		    </td>
			<td>
                <s:textfield id="editOrg.name" name="orgFromPO.name" maxlength="80" size="80" cssStyle="width: 300px" readonly="true" cssClass="readonly"/>
                <s:if test="%{currentAction == 'edit'}">
                    <a href="javascript:void(0)" onclick="displayOrgDetails(<c:out value='${editOrg.identifier}'/>);">
                        <img src="<%=request.getContextPath()%>/images/details.gif" alt="details"/>
                    </a>
                </s:if>
            
				<s:if test="%{currentAction == 'create'}">
				<span class="info">Click <strong>Look Up</strong> to choose an organization.</span>
				<span class="formErrorMsg"></span>
				</s:if>
				         <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>editOrg.name</s:param>
                              </s:fielderror>                            
                        </span>	
			</td>			        
			<td class="value">
			         <s:if test="%{currentAction == 'create'}">
						<ul style="margin-top:-6px;">			
							<li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
						</ul>
			     </s:if>
			 </td> 
		</tr>
		<s:if test="proprietaryTrialIndicator == 'false'">
		<tr>
		    <td scope="row" class="label"><label for="editOrg.city">City:</label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield id="editOrg.city" name="orgFromPO.city" maxlength="200" size="200" 
		         cssStyle="width: 200px" readonly="true" cssClass="readonly"/>
		    </td>
		</tr>
		<tr>
		    <td scope="row" class="label"><label for="orgFromPO.state">State:</label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield id="orgFromPO.state" name="orgFromPO.state" maxlength="200" size="200" 
		         cssStyle="width: 200px" readonly="true" cssClass="readonly"/>
		    </td>
		</tr>		
		<tr>
		    <td scope="row" class="label"><label for="orgFromPO.country">Country:</label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield id="orgFromPO.country" name="orgFromPO.country" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" cssClass="readonly"/>
		    </td>
		</tr>
		<tr>
		    <td scope="row" class="label"><label for="editOrg.postalCode">Zip/Postal Code(US/Canada/Australia):</label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield id="editOrg.postalCode" name="orgFromPO.zip" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" cssClass="readonly"/>
		    </td>
		</tr>
    </s:if>
    <s:hidden name="editOrg.identifier" id="editOrg.identifier" ></s:hidden>
</table>