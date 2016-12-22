<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<pa:sucessMessage/>
<pa:failureMessage/>
<c:choose>	
    <c:when test="${fn:length(sessionScope.otherIdentifiersList) > 0}">
		<display:table class="data"  
			id="row" name="${sessionScope.otherIdentifiersList}" requestURI=""
			export="false">
			<display:column title="Identifier Type" 
				headerClass="sortable">
				<div id="identifierTypeDiv_${row_rowNum}">
					<c:out value="${row.type.code}" />
					<c:if test="${row.type.required}">
					   <span class="required">*</span>
					</c:if>
				</div>				
			</display:column>
			
		    <display:column title="Value" 
                headerClass="sortable">
                <div id="identifierDiv_${row_rowNum}" style="white-space: pre;"><c:out value="${row.value}" /></div>
                <div id="identifierInputDiv_${row_rowNum}" style="display: none;">
                    <label for="identifier_${row_rowNum}" style="display:none">Identifier</label>
                    <input id="identifier_${row_rowNum}" type="text"
                        value="<c:out value="${row.value}"/>" />
                </div>
            </display:column>

            <c:if test="${otherIdentifiersEditable}">				
				<display:column title="Action" class="action" style="width:110px; text-align:left;">
					<div id="actionEdit_${row_rowNum}">
						<input type="button" value="Edit"
						    id="otherIdEditBtn_${row_rowNum}"
							onclick="editIdentifierRow('${row_rowNum}')" />&nbsp;
						<c:if test="${!row.type.required}">
						   <input type="button" value="Delete"
						      id="otherIdDeleteBtn_${row_rowNum}"
							  onclick="if (confirm('Click OK to remove selected identifier from the study. Cancel to abort.')) {deleteOtherIdentifierRow('${row_rowNum}')}" />
						</c:if>
					</div>
					<div id="actionSave_${row_rowNum}" style="display: none;">
						<input type="button" value="Save"
						    id="otherIdSaveBtn_${row_rowNum}"
							onclick="saveIdentifierRow('${row_rowNum}')" />&nbsp;
						<input type="button" value="Cancel"
						    id="otherIdCancelBtn_${row_rowNum}"
							onclick="initializeOtherIdentifiersSection();" />
					</div>				
				</display:column>
			</c:if>
		</display:table>
	</c:when>
</c:choose>

<script type="text/javascript" language="javascript">

    var opt;
	var listEl = $('otherIdentifierType');
	if (listEl!=null) {
		var curSelection = $F(listEl);
			                
		listEl.options.length = 0;
		
		<c:forEach items="${otherIdentifiersTypesList}" var="idType">
		    opt = document.createElement("option");
		    opt.value= '<c:out value="${idType.code}"/>';
		    opt.innerHTML = '<c:out value="${idType.code}"/>';              
		    listEl.options.add(opt);
	    </c:forEach>
	    
	    listEl.setValue(curSelection);
	}
</script>
