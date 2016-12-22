<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:studyUniqueToken/>
<s:set name="trialHistoryWebDTO" value="trialHistoryWebDTO" scope="request" />
<s:set name="deletedDocuments" value="deletedDocuments" scope="request" />
<p class="info">
    Note: for trials submitted prior to the 3.9 Release, the <b>Documents</b> column
    might not precisely represent the <u>original</u> documents that came with a submission or an amendment.
    Also for trial updates made prior to the 3.9 Release, the corresponding documents will not be available here.
</p>
<display:table name="trialHistoryWebDTO" id="row" class="data" sort="list" 
    pagesize="10" requestURI="trialHistory.action">
    <display:column escapeXml="false" sortable="true" title="Submission Number" class="action"> 
     <a href="<s:url action='milestoneview'> 
     <s:param name="submissionNumber" value="%{#attr.row.submissionNumber}"/>
     </s:url>">${row.submissionNumber}</a>
    </display:column>  
    <display:column property="submissionDate" sortable="true" title="Date" format="{0,date,MM/dd/yyyy}" />    
    <display:column escapeXml="false" sortable="true" title="Type">
        <c:out value="${row.type}"></c:out>
        <c:if test="${row.type == 'Amendment'}">
            <br/><br/>
            Date:&nbsp;<c:out value="${row.amendmentDate}"></c:out><br/>
            Number:&nbsp;<c:out value="${row.amendmentNumber}"></c:out><br/>
            Reason:&nbsp;<c:out value="${row.amendmentReasonCode}"></c:out><br/>
        </c:if>
    </display:column>    
    <display:column escapeXml="true" property="submitter" sortable="true" title="Submitter" />        
    <display:column escapeXml="false" property="documents" sortable="true" style="word-wrap: break-word"
        titleKey="trialHistory.documents" />
        
    
    <display:column escapeXml="false" sortable="false" title="Milestone">
        <c:out value="${row.lastMileStone}"></c:out>
        <c:if test="${row.lastMileStone != 'Submission Rejection Date' && row.lastMileStone != 'Late Rejection Date'}">
        <br/> <br/>
         <c:out value="${row.comment}"></c:out>
         </c:if>
        <br/> <br/>
        <c:if test="${not empty row.rejectComment && (row.lastMileStone == 'Submission Rejection Date' || row.lastMileStone == 'Late Rejection Date') }">
            <c:out value="${row.rejectComment}"></c:out>
        </c:if>
        
    </display:column>  
    <pa:displayWhenCheckedOut>
        <display:column title="Action" headerClass="centered" class="action">
            <s:if test="%{#attr.row.submissionNumber != 1 && #attr.row.submissionNumber != null}">
                <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.identifier})">
                    <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                </s:a>
            </s:if>
        </display:column>
    </pa:displayWhenCheckedOut>
</display:table>

<br/>

<div id="showDocsDiv">
    <a href="javascript:void(0);" onclick="$('showDocsDiv').hide();$('deletedDocDiv').show();$('hideDocsDiv').show();"><b>Show Deleted Documents</b></a>
</div>
<div id="hideDocsDiv" style="display: none;">
    <a href="javascript:void(0);" onclick="$('showDocsDiv').show();$('deletedDocDiv').hide();$('hideDocsDiv').hide();"><b>Hide Deleted Documents</b></a>
</div>


<div id="deletedDocDiv" style="display: none;">
	<c:if test="${empty deletedDocuments}">
	    <p class="info">No history of deleted trial documents.</p>
	</c:if>
	<c:if test="${not empty deletedDocuments}">
		<display:table name="deletedDocuments" id="delDoc" class="data" sort="list" 
		    pagesize="50" requestURI="trialHistory.action">
		    <display:column escapeXml="true" property="dateLastUpdated" sortable="true" title="Deletion Date" />    
		    <display:column escapeXml="true" property="userLastUpdated" sortable="true" title="Deleted By" />
		    <display:column escapeXml="true" property="typeCode" sortable="true" title="Document Type" />
		    <display:column escapeXml="false" sortable="true" title="File Name">
		        <a onclick="handlePopup('${delDoc.studyProtocolId}','${delDoc.id}','<c:out value="${delDoc.fileName}"/>')" href="javascript:void(0);">
		            <c:out value="${delDoc.fileName}"/>
		        </a>
		    </display:column>
		</display:table>
	</c:if>
</div>