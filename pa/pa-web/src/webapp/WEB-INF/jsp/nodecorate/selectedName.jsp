<%@ taglib prefix="s" uri="/struts-tags"%>	
<table class="form">
	<tr>
	    <td scope="row" class="label"><label for="personContactWebDTO.firstName">First Name:</label><span class="required">*</span></td>
		<td class="value" style="width:250px">
		<s:textfield name="personContactWebDTO.firstName" id="personContactWebDTO.firstName" maxlength="80" size="80" cssStyle="width: 200px" disabled="disabled" readonly="true" />                            
		<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>personContactWebDTO.firstName</s:param>
                               </s:fielderror>                            
                             </span>
		</td>			        
		<td class="value">
			<ul style="margin-top:-6px;">			
				<li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupcontactperson();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
			</ul>
		</td> 
	</tr>
	<tr>
	    <td scope="row" class="label"><label for="personContactWebDTO.middleName">Middle Name:</label></td>
	    <td class="value" colspan="2">
	        <s:textfield name="personContactWebDTO.middleName" id="personContactWebDTO.middleName" maxlength="200" size="200" 
	        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
	    </td>
	</tr>
	<tr>
	    <td scope="row" class="label"><label for="personContactWebDTO.lastName">Last Name:</label></td>
	    <td class="value" colspan="2">
	        <s:textfield name="personContactWebDTO.lastName" id="personContactWebDTO.lastName" maxlength="200" size="200" 
	        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
	        
	        <a href="javascript:void(0)" onclick="displayPersonDetails('${personContactWebDTO.selectedPersId}');">
                <img src="<%=request.getContextPath()%>/images/details.gif" alt="detials"/>
            </a>
            
	    </td>
	</tr>
	<tr><td> OR    </td></tr>
    <tr>
    <td scope="row" class="label"><label name="personContactWebDTO.title" for="personContactWebDTO.title">Generic Contact:</label></td>
    <td><s:textfield name="personContactWebDTO.title" id="personContactWebDTO.title" size="30"  
        readonly="true" cssClass="readonly" cssStyle="width:200px"/>
    </td>
    <td>
        <ul style="margin-top:-5px;">              
            <li style="padding-left:0">
            <a href="javascript:void(0)" class="btn" id="lookupbtn4RP" onclick="lookup4genericcontact();" title="Opens a popup form to select Site Contact"/><span class="btn_img">
            <span class="person">Look Up Generic Contact</span></span></a>
            </li>
        </ul>
    </td>
    </tr>
	<s:hidden name="personContactWebDTO.selectedPersId" id="personContactWebDTO.selectedPersId"/>
</table>