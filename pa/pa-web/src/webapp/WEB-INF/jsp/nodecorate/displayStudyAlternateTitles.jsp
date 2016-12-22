<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:choose>  
    <c:when test="${fn:length(sessionScope.studyAlternateTitlesList) > 0}">
        <display:table class="data" decorator="" sort="list" size="false" 
            id="row" name="${sessionScope.studyAlternateTitlesList}" requestURI="" export="false">
            <display:column title="Category" sortable="true" headerClass="sortable">
               <div id="studyAlternateTitleTypeDiv_${row_rowNum}">
                   <c:out value="${row.category.value}"/>
               </div>
               <div id="studyAlternateTitleTypeInputDiv_${row_rowNum}" style="display:none;">
                   <s:set name="studyAlternateTitleTypeCodeValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getStudyAlternateTitleTypes()" />
                   <s:select id="studyAlternateTitleType_%{#attr.row_rowNum}" headerKey="" 
                       name="studyAlternateTitleType" list="#studyAlternateTitleTypeCodeValues" cssStyle="width:110px;"/>
               </div>     
            </display:column>
            <display:column title="Alternate Title" sortable="true" headerClass="sortable">
                <div id="studyAlternateTitleDiv_${row_rowNum}">
                    <c:out value="${row.alternateTitle.value}"/>
                </div>
                <div id="studyAlternateTitleInputDiv_${row_rowNum}" style="display: none;">
                    <textarea id="studyAlternateTitle_${row_rowNum}" name="studyAlternateTitle_${row_rowNum}" 
                        style="width:606px; vertical-align:top;" rows="4" maxlength="4000" class="charcounter"><c:out value="${row.alternateTitle.value}"/></textarea>    
                </div>
            </display:column>
            <display:column title="Action" class="action" sortable="false" style="width:110px">
                <div id="studyAlternateTitleActionEdit_${row_rowNum}">
                    <input type="button" value="Edit" 
                        onclick="editStudyAlternateTitle('${row_rowNum}')" />&nbsp;
                    <input type="button" value="Delete"
                        onclick="if (confirm('Click OK to remove selected alternate title from the study. Cancel to abort.')) {deleteStudyAlternateTitle('${row_rowNum}')}"/>
                </div>
                <div id="studyAlternateTitleActionSave_${row_rowNum}" style="display: none;">
                    <input type="button" value="Done"
                        onclick="saveStudyAlternateTitle('${row_rowNum}')" />&nbsp;
                    <input type="button" value="Delete"
                        onclick="if (confirm('Click OK to remove selected alternate title from the study. Cancel to abort.')) {deleteStudyAlternateTitle('${row_rowNum}')}" />                                                            
                </div>    
            </display:column>
        </display:table>
    </c:when>
</c:choose>
    