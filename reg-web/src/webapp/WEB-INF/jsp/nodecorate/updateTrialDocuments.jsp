<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<p><fmt:message key="update.trial.docInstructionalText"/></p>
<p class="mb20"><a href="javascript:void(0)" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a></p>
<div class="form-group">
      <label for="updateTrial_protocolDoc" class="col-xs-4 control-label"><fmt:message key="update.trial.protocolDocument"/></label>
      <div class="col-xs-4">
      <s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
        <s:property value="%{#session.protocolDoc.fileName}"/>
        <button type="button" class="btn btn-icon btn-default"  onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')"><i class="fa-minus"></i>Remove</button>
      </s:if>
      <s:else>
        <s:file name="protocolDoc" value="true" cssStyle="width:270px"/>
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.protocolDocFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
      </div>
      <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.protocol_document"/>" data-placement="top" data-trigger="hover"></i> </div>
</div>
<div class="form-group">
    <label for="updateTrial_irbApproval" class="col-xs-4 control-label"><fmt:message key="update.trial.irbApproval" /></label>
 	<div class="col-xs-4">
      <s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
        <s:property value="%{#session.irbApprovalDoc.fileName}"/>
        <button type="button" class="btn btn-icon btn-default"  onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"><i class="fa-minus"></i>Remove</button>
      </s:if>
      <s:else>
        <s:file name="irbApproval" cssStyle="width:270px"/>
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.irbApprovalFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
	</div>
	<div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.irb_approval"/>" data-placement="top" data-trigger="hover"></i> </div>
</div>
<div class="form-group">
    <label for="updateTrial_participatingSites" class="col-xs-4 control-label"><fmt:message key="update.trial.participatingSites"/></label>
	<div class="col-xs-4">
      <s:if test="%{#session.participatingSitesDoc.typeCode.equals('Participating sites')}">
        <s:property value="%{#session.participatingSitesDoc.fileName}"/>
        <button type="button" class="btn btn-icon btn-default"  onclick="deleteDocument('<s:property value='%{#session.participatingSitesDoc.typeCode}'/>')"><i class="fa-minus"></i>Remove</button>
      </s:if>
      <s:else>
        <s:file name="participatingSites" cssStyle="width:270px"/>
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.participatingSitesFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
   </div>
   <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.list_of_participating_sites"/>" data-placement="top" data-trigger="hover"></i> </div>
</div>
<div class="form-group">
  <label for="updateTrial_informedConsentDocument" class="col-xs-4 control-label"><fmt:message key="update.trial.informedConsent"/></label>
  <div class="col-xs-4">
    <s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
      <s:property value="%{#session.informedConsentDoc.fileName}"/>
      <button type="button" class="btn btn-icon btn-default"  onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')"><i class="fa-minus"></i>Remove</button>
    </s:if>
    <s:else>
      <s:file name="informedConsentDocument" cssStyle="width:270px"/>
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.informedConsentDocumentFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </div>
    <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.informed_consent_document"/>" data-placement="top" data-trigger="hover"></i> </div>
</div>
<c:set var="hasOtherDocs" scope="request" value="${not empty sessionScope.otherDoc}"/>
<c:forEach items="${sessionScope.otherDoc}" var="doc" varStatus="varStatus">
	<div class="form-group">
    <label for="updateTrial_otherDocument" class="col-xs-4 control-label"><fmt:message key="update.trial.otherDocument"/></label>
    <div class="col-xs-4">
      <c:out value="${doc.fileName}"/>
      <button type="button" class="btn btn-icon btn-primary"  onclick="deleteDocument('Other',${varStatus.index})"><i class="fa-minus"></i>Remove</button>
	</div>
	<div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.other"/>" data-placement="top" data-trigger="hover"></i> </div>
	</div>
</c:forEach>
  
<c:set var="addMoreRendered" value="${false}" scope="page"/>
<c:forEach begin="0" end="50" varStatus="varStatus">
<c:set var="fieldErrorKey" scope="page" value="trialDTO.otherDocumentFileName[${varStatus.index}]"/>
<c:set var="hasFieldError" scope="page" value="${not empty request.action.fieldErrors[fieldErrorKey]}"/>
<c:set var="hideUploadRow" value="${(!hasFieldError && (hasOtherDocs || varStatus.index>0))}"/>

<div style="${hideUploadRow?'display:none':''}" class="form-group" id="otherUploadRow_${varStatus.index}">
    <label for="updateTrial_otherDocument_${varStatus.index}" class="col-xs-4 control-label"><fmt:message key="update.trial.otherDocument"/></label>
    <div class="col-xs-4">
        <input id="updateTrial_otherDocument_${varStatus.index}" type="file" style="width:270px" value="" name="otherDocument">
        <span class="alert-danger">
          <s:fielderror>
            <s:param>trialDTO.otherDocumentFileName[${varStatus.index}]</s:param>
          </s:fielderror>
        </span>        
	</div>
	<div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="<fmt:message key="tooltip.other"/>" data-placement="top" data-trigger="hover"></i> </div>
</div>
<div style="${(hideUploadRow && !addMoreRendered)?'':'display:none'}" class="form-group" id="addMoreRow_${varStatus.index}">
 <label class="col-xs-4 control-label">&nbsp;</label>
 	  <button type="button" id="grantbtnid" class="btn btn-icon btn-default" 
        onclick="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"
        onkeypress="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();">
        <i class="fa-plus"></i>Add more...</button>
</div>
<c:set var="addMoreRendered" value="${addMoreRendered || (hideUploadRow && !addMoreRendered)}" scope="page"/>
</c:forEach>  