<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-6">
    <span class="alert-danger"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>    
</div>

<div class="col-xs-6">
    <s:set var="sum4SponsorOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
    
</div>

<c:forEach items="${sessionScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers" varStatus="stat">
<div class="col-xs-12">
    <div class="col-xs-6">
       <input type="text" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="<c:out value="${summaryFourOrgIdentifiers.orgName}" />" size="30" readonly="true" 
           class="form-control"  />
       <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="<c:out value="${summaryFourOrgIdentifiers.orgId}" />"/> 
       <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" value="<c:out value="${summaryFourOrgIdentifiers.rowId}" />"/>
    </div>
    <div class="col-xs-6">
        <button onclick="deleteSummary4SponsorRow('<c:out value="${summaryFourOrgIdentifiers.rowId}" />');" title="Remove this Data Table 4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-minus"></i>Delete Sponsor</button>
    </div> 
    <div>
    <br>
    <br>
    <br>
    </div>
</div>
</c:forEach>