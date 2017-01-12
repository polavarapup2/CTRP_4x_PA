<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:choose>
    <c:when test="${not empty trialDTO.nctIdentifier}">
        <c:out value="${trialDTO.nctIdentifier}"/>
        <input type="hidden" name="trialDTO.nctIdentifier" id="trialDTO.nctIdentifier" value='<c:out value="${trialDTO.nctIdentifier}"/>'/>
    </c:when>
    <c:otherwise>
      <div id="nctIdentifierdiv">
        <s:textfield id="nctId" name="nctId"  maxlength="200" size="100"  cssClass="form-control" />&nbsp; 
        <button onclick="addNCTIdentifier();" id="nctIdbtnid" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i>Add ClinicalTrials.gov Identifier</button>
	    <span class="alert-danger">
	        <s:fielderror cssStyle = "white-space:pre-line;">
	              <s:param>trialDTO.nctIdentifier</s:param>
	        </s:fielderror>
	    </span>
      </div>
    </c:otherwise>
</c:choose>