<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <s:if test="pendingAdminUsers != null"> 
    <h1>Pending Admin User</h1>  
    <s:form name="spForm">
        <s:actionerror/>
        <pa:studyUniqueToken/>
        <s:set name="pendingAdminUsers" value="pendingAdminUsers" scope="request"/>
            <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
             name="pendingAdminUsers" requestURI="inboxProcessinggetPendingAdminUserRole.action" export="false">
            <display:column escapeXml="true" class="title" titleKey="pending.userFirstName" property="firstName" paramId="id" paramProperty="id" 
                 href="inboxProcessingviewPendingUserAdmin.action" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="pending.userLastName" maxLength= "200" property="lastName" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="pending.affiliateOrg" maxLength= "200" property="affiliateOrg" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="pending.dateLastCreated" maxLength= "200" property="dateLastCreated" sortable="true" headerClass="sortable"/>
        </display:table>
    </s:form>
</s:if>
