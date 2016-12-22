<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="interWebList != null">

<s:set name="interWebList" value="interWebList" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row"
    name="interWebList" export="false">
    <display:column escapeXml="true" title="Preferred Name" property="name"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Other Names" property="otherNames"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Type Code" property="type"  headerClass="sortable"/>
    <display:column escapeXml="true" title="ClinicalTrials.gov<br>Type Code" property="ctGovType"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Description" property="description"  headerClass="sortable"/>
    <display:column title="Action" headerClass="centered" class="action" sortable="false">
        <a href="javascript:void(0)" class="btn" onclick="submitform('${row.identifier}')">
            <span class="btn_img"><span class="add">Select</span></span>
        </a>
    </display:column>
</display:table>

</s:if>