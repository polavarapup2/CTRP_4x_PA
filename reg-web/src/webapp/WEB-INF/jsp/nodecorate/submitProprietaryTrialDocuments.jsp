<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<p class="info"><fmt:message key="submit.proprietary.trial.docInstructionalText"/></p>
<p class="mb20"><a href="javascript:void(0)" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a></p>
 <div class="form-group">
 	<label for="irbApproval" class="col-xs-4 control-label"><fmt:message key="submit.trial.irbApproval"/></label>
    <div class="col-xs-4">
      <s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
        <s:property value="%{#session.irbApprovalDoc.fileName}"/>
        <c:if test="${!(disableDocumentDeletion==true)}">
        <button id="irbApproval" type="button" class="btn btn-icon btn-primary" onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"><i class="fa-minus"></i>Remove</button>
        </c:if>
      </s:if>
      <s:else>
        <s:file id="irbApproval" name="irbApproval" />
        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.irb_approval"/>"  data-placement="top" data-trigger="hover"></i>
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.irbApprovalFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </div>
  </div>
  <div class="form-group">
      <label for="informedConsentDocument" class="col-xs-4 control-label"><fmt:message key="submit.trial.informedConsent"/></label>
  	<div class="col-xs-4">
    <s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
      <s:property value="%{#session.informedConsentDoc.fileName}"/>
      <c:if test="${!(disableDocumentDeletion==true)}">
        <button id="informedConsentDocument" type="button" class="btn btn-icon btn-primary" onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')"><i class="fa-minus"></i>Remove</button>
      </c:if>
    </s:if>
    <s:else>
      <s:file id="informedConsentDocument" name="informedConsentDocument" />
      <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.informed_consent_document" />"  data-placement="top" data-trigger="hover"></i>
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.informedConsentDocumentFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
   </div> 
   </div>
  
  
  
<c:set var="hasOtherDocs" scope="request" value="${not empty sessionScope.otherDoc}"/>
<c:forEach items="${sessionScope.otherDoc}" var="doc" varStatus="varStatus">
 <div class="form-group">
    <label class="col-xs-4 control-label"><fmt:message key="submit.trial.otherDocument"/></label>
    <div class="col-xs-4">
        <c:out value="${doc.fileName}"/>
        <c:if test="${!(disableDocumentDeletion==true)}">
           <button type="button" class="btn btn-icon btn-primary" onclick="deleteDocument('Other',${varStatus.index})"><i class="fa-minus"></i>Remove</button>
        </c:if>      
	</div>
</div>
</c:forEach>
  
<c:set var="addMoreRendered" value="${false}" scope="page"/>
<c:forEach begin="0" end="50" varStatus="varStatus">
<c:set var="fieldErrorKey" scope="page" value="trialDTO.otherDocumentFileName[${varStatus.index}]"/>
<c:set var="hasFieldError" scope="page" value="${not empty request.action.fieldErrors[fieldErrorKey]}"/>
<c:set var="hideUploadRow" value="${(!hasFieldError && (hasOtherDocs || varStatus.index>0))}"/>
  
<div style="${hideUploadRow?'display:none':''}" class="form-group" id="otherUploadRow_${varStatus.index}">
  <label for="submitProprietaryTrial_otherDocument_${varStatus.index}" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherDocument"/></label>
  <div class="col-xs-4">
      <input id="submitProprietaryTrial_otherDocument_${varStatus.index}" type="file" style="width:270px" value="" name="otherDocument">
      <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.other"/>"  data-placement="top" data-trigger="hover"></i>
      <span class="alert-danger">
        <s:fielderror>
          <s:param>trialDTO.otherDocumentFileName[${varStatus.index}]</s:param>
        </s:fielderror>
      </span>
</div>
</div>
<div style="${(hideUploadRow && !addMoreRendered)?'':'display:none'}" id="addMoreRow_${varStatus.index}">
    <div class="col-xs-4"></div>
    <div class="col-xs-4">
	      <button type="button" class="btn btn-icon btn-primary" 
	        onclick="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"
      		onkeypress="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"><i class="fa-plus"></i>Add More...</button>
	</div> 
</div>     
<c:set var="addMoreRendered" value="${addMoreRendered || (hideUploadRow && !addMoreRendered)}" scope="page"/>
</c:forEach>  
