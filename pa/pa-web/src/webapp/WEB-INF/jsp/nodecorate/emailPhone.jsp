			<%@ taglib prefix="s" uri="/struts-tags"%>	
			<table class="form">
				<tr>
				    <td scope="row" class="label"><s:label for="editOrg.postalCode">Telephone:</s:label><span class="required">*</span></td>
				  
				    <td class="value" colspan="2">
				    	
				        <s:textfield name="personContactWebDTO.telephone" maxlength="200" size="200"				         
				        cssStyle="width: 200px" />
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>personContactWebDTO.telephone</s:param>
                              </s:fielderror>                            
                        </span>				        
				    </td>				    
				</tr>
							<tr>
				    <td scope="row" class="label"><s:label for="editOrg.postalCode">Email:</s:label><span class="required">*</span></td>
				 
				    <td class="value" colspan="2">
				        <s:textfield name="personContactWebDTO.email" maxlength="200" size="200" 
				         cssStyle="width: 200px" />
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>personContactWebDTO.email</s:param>
                              </s:fielderror>                            
                        </span>				          
				    </td>
				     
				</tr>
			</table>