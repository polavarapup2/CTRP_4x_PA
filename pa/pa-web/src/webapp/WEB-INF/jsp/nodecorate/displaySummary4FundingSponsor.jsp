<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table>
<tr>
<td class="value">
	<s:textfield id="gtdDTO.summaryFourOrgName" size="30" cssStyle="width:200px" readonly="true"/>
</td>
<td> 
  <ul style="margin-top:-2px;">             
        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup4loadSummary4Sponsor();"/><span class="btn_img"><span class="organization">Add Sponsor</span></span></a></li>
  </ul>
</td>
</tr>
<c:forEach items="${gtdDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers" varStatus="stat">
    <tr>
        <td>
        	<label for="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" style="display:none;">SummaryFourOrgIdentifiers</label>
            <input type="text" name="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="<c:out value="${summaryFourOrgIdentifiers.orgName}" />" size="30" readonly class="readonly" style="width:200px" />
            <a href="javascript:void(0)" onclick="displayOrgDetails('<c:out value="${summaryFourOrgIdentifiers.orgId}" />');">
                <img src="${pageContext.request.contextPath}/images/details.gif" alt="details"/>
            </a>
            <input type="hidden" name="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="<c:out value="${summaryFourOrgIdentifiers.orgId}" />"/> 
            <input type="hidden" name="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" id="gtdDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" value="<c:out value="${summaryFourOrgIdentifiers.rowId}" />"/> 
        </td>
        <td class="value">
            <ul style="margin-top:-5px;">              
                <li style="padding-left:0">
                 <a href="javascript:void(0)" class="btn" onclick="deleteSummary4SponsorRow('<c:out value="${summaryFourOrgIdentifiers.rowId}" />');" title="Opens a popup form to select Summary4 Sponsor">
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
   <s:param>summary4FundingSponsor</s:param>
  </s:fielderror>                            
</span>