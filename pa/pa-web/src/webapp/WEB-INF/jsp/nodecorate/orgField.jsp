<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table>
<tr>
<td> 
<s:textfield id="summary4FundingSponsor" size="30"  readonly="true" cssClass="readonly"/>
</td>
<td> 
                  <ul style="margin-top:-2px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Add Sponsor</span></span></a></li>
                  </ul>
                   </td>
      </tr>
      <c:forEach items="${nciSpecificInformationWebDTO.summary4Sponsors}" var="summaryFourOrgIdentifiers" varStatus="stat">
    <tr>
        <td>
        	<label for="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].orgName" style="display:none">Sponsor:</label>
            <input type="text" name="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].orgName" id="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].orgName" value="${summaryFourOrgIdentifiers.orgName}" size="30" readonly class="readonly" style="width:200px" />
            <a href="javascript:void(0)" onclick="displayOrgDetails('${summaryFourOrgIdentifiers.orgId}');">
                <img src="${pageContext.request.contextPath}/images/details.gif" alt="details"/>
            </a>
            <input type="hidden" name="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].orgId" id="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].orgId" value="${summaryFourOrgIdentifiers.orgId}"/> 
            <input type="hidden" name="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].rowId" id="nciSpecificInformationWebDTO.summary4Sponsors[${stat.index}].rowId" value="${summaryFourOrgIdentifiers.rowId}"/> 
        </td>
        <td class="value">
            <ul style="margin-top:-5px;">              
                <li style="padding-left:0">
                 <a href="javascript:void(0)" class="btn" onclick="deleteSummary4SponsorRow('${summaryFourOrgIdentifiers.rowId}');" title="Opens a popup form to select Summary4 Sponsor">
                    <span class="btn_img"><span class="organization">Delete Sponsor</span></span>
                 </a>
                </li>
            </ul>
        </td>
    </tr>
    
    </c:forEach>
</table>
<span class="formErrorMsg"> 
   <s:fielderror>
   	  <s:param>nciSpecificInformationWebDTO.organizationName</s:param>
    </s:fielderror>                            
</span>
