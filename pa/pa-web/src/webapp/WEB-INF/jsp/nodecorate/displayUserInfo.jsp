<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>	
<table class="form" border=1>
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>First Name:</b></td>
		<td class="value" style="width:250px"><s:property value="webDTO.firstName"/></td>			        
	</tr>
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>Middle Name:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.middleName"/></td>
	</tr>
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>Last Name:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.lastName"/></td>	        
	</tr>
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>City:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.city"/></td>
	</tr>	
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>State:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.state"/></td>	        
	</tr>
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>Country:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.country"/></td>	        
	</tr>
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>Zip:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.zip"/></td>	        
	</tr>	
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>Email:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.email"/></td>	        
	</tr>	
	<s:if test="webDTO.telephone != null">	
	<tr>
	    <td scope="row" class="label" style="width:250px" align="right"><b>Phone:</b></td>
	    <td class="value" colspan="2"><s:property value="webDTO.telephone"/></td>	        
	</tr>
    <tr>
        <td scope="row" class="label" style="width:250px" align="right"><b>Trial Submitter Organization:</b></td>
        <td class="value" colspan="2"><c:out value="${sessionScope.trialSubmitterOrg}"/></td>           
    </tr>		
	</s:if>	
</table>
