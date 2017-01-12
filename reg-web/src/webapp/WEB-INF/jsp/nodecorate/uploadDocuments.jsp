<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript">
    function deleteDocument(typeCode, docIndex) {
        var url = '/registry/protected/ajaxUploaddeleteDocument.action';
        var params = {
                pageFrom: document.forms[0].pageFrom.value,
                typeCode: typeCode,
                docIndex: docIndex!=undefined?docIndex:''
                };
        var div = document.getElementById('uploadDocDiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        var aj = callAjaxPost(div, url, params);
        return false;
    }
</script>
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section14">Trial Related Documents <span class="required">*</span></a></div>
    <div id="section14" class="accordion-body in">
      <div class="container">
		<reg-web:failureMessage/>
		<s:hidden name="pageFrom" id="pageFrom"/>
		<s:if test="%{pageFrom == 'submitTrial'}">
		  <%@ include file="/WEB-INF/jsp/nodecorate/submitTrialDocuments.jsp" %>
		</s:if>
		
		<s:if test="%{pageFrom == 'updateTrial'}">
		  <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialDocuments.jsp" %>
		</s:if>
		
		<s:if test="%{pageFrom == 'proprietaryTrial' || pageFrom == 'updateProprietaryTrial'}">
		  <%@ include file="/WEB-INF/jsp/nodecorate/submitProprietaryTrialDocuments.jsp" %>
		</s:if>
		
		<s:if test="%{pageFrom == 'amendTrial'}">
		  <%@ include file="/WEB-INF/jsp/nodecorate/amendTrialDocuments.jsp" %>
		</s:if>
	  </div>
	</div>
</div>