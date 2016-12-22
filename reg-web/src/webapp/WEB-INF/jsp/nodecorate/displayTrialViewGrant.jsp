<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(sessionScope.grantList) >0}">
<display:table class="data table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
     name="${sessionScope.grantList}" requestURI="searchTrialview.action" export="false">
     <display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanismCode"   sortable="true" headerClass="sortable"/>
     <display:column titleKey="search.trial.view.nihInstitutionCode" property="nihInstitutionCode"   sortable="true" headerClass="sortable"/>
     <display:column escapeXml="true" titleKey="search.trial.view.serialNumber" property="serialNumber"   sortable="true" headerClass="sortable"/>
     <display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode"   sortable="true" headerClass="sortable"/>
     <display:column title="Action" class="action" sortable="false">
        <c:choose>
            <c:when test="${row.id != null}">
            </c:when>
            <c:otherwise>
              <input type="button" value="Delete" onclick="deleteGrantRow('${row.rowId}')"/>
            </c:otherwise>
        </c:choose>

    </display:column>
</display:table>
</c:when>
<c:when test="${trialDTO.fundingDtos != null && fn:length(trialDTO.fundingDtos) >0}">
<div class="box" id="grantsDiv">
<h3 class="heading mt20"><span>NIH Grant Information (for NIH funded Trials)</span></h3>
Is this trial funded by an NCI grant? <s:radio name="trialDTO.nciGrant" id="nciGrant"  list="#{true:'Yes', false:'No'}" disabled="true"/>
<display:table class="data table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
     name="${trialDTO.fundingDtos}" requestURI="searchTrialview.action" export="false">
    <display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanismCode"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.view.nihInstitutionCode" property="nihInstitutionCode"   sortable="true" headerClass="sortable"/>
    <display:column escapeXml="true" titleKey="search.trial.view.serialNumber" property="serialNumber"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode"   sortable="true" headerClass="sortable"/>
    </display:table>
</div>
</c:when>
</c:choose>