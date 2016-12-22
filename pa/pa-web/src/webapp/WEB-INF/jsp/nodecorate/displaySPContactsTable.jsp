<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<pa:sucessMessage />
<s:if test="hasActionErrors()">
            <div class="error_msg"><s:actionerror/></div>
 </s:if>

<s:set name="personWebDTOList" value="personWebDTOList" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" 
        uid="row"  name="personWebDTOList" export="false" requestURI="participatingOrganizationsedit.action#investigators">
        
    <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true">
          <a href="javascript:void(0);" onclick="displayPersonDetails(<c:out value="${row.selectedPersId}"/>)"><c:out value="${row.selectedPersId}"/></a>
    </display:column>        
	<display:column escapeXml="true" title="Last Name" property="lastName"  headerClass="sortable"/>
	<display:column escapeXml="true" title="First Name" property="firstName"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Role" property="roleName.code"  headerClass="sortable"/>
	<display:column escapeXml="true" title="Status Code" property="statusCode"  headerClass="sortable"/>
	<c:if test="${sessionScope.isSuAbstractor || (sessionScope.trialSummary.adminCheckout.checkoutBy != null &&
              sessionScope.loggedUserName == sessionScope.trialSummary.adminCheckout.checkoutBy && (sessionScope.isAdminAbstractor))}">
	<s:if test="%{newParticipation}">
		<display:column title="Set as Site Primary Contact" class="action" sortable="false">
			<a href="javascript:void(0)" onclick="setAsPrimaryContact('${row.id}','del');"> <img src="<%=request.getContextPath()%>/images/ico_select_person.gif" alt="Set as Primary" width="16" height="16"/></a>
		</display:column>
	</s:if>
	<s:else>
		<display:column title="Set as Site Primary Contact" class="action" sortable="false">
			<a href="javascript:void(0)" onclick="loadContactPersDivEditMode('${row.id}');   "> <img src="<%=request.getContextPath()%>/images/ico_select_person.gif" alt="Set as Primary" width="16" height="16"/></a>
		</display:column>
	</s:else>
	
	<display:column title="Delete" class="action" sortable="false">
		<a href="javascript:void(0)" onclick="loadPersDiv('${row.id}','del'); "> <img src="<%=request.getContextPath()%>/images/ico_cancel.gif" alt="Delete" width="16" height="16"/></a>
	</display:column>
	</c:if>
</display:table>
