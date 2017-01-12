<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>My Account</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function handleBatchUploadAction(){
	var form = document.batchUploadForm;
	form.page.value = "Submit";
	form.action="/registry/admin/batchUploadprocess.action";
    form.submit();
}
</SCRIPT>
</head>
<body>
      <div class="modal-body">
      	<c:set var="topic" scope="request" value="batchupload"/>
      	<reg-web:failureMessage/>
		 <s:actionmessage cssClass="alert alert-info"/>
         <s:if test="hasActionErrors()">
              <div class="alert alert-danger">
                  <s:actionerror/>
              </div>
          </s:if>
        <p> Register multiple trials in the NCI Clinical Trials Reporting Program by uploading the Trial Data file and the Zip file that contains trial documents.
          Note the following requirements:</p>
        <ol>
          <li>Use this form to submit complete trials only</li>
          <li>The trial data file must conform to the specifications in the CTRP Registry Complete Batch Upload Template, available on the <a href="https://wiki.nci.nih.gov/display/CTRP/CTRP+Trial+Registration+Batch+File+Templates" target="_blank">CTRP Trial Registration Batch File Templates</a> page</li>
          <li>You must submit a Zip file that contains trial documents for each new and amended trial</li>
          <li>You do not have to submit a Zip file that contains trial documents for updates to registered trials</li>
        </ol>
        <form id="batchUploadForm" name="batchUploadForm" role="form" class="form-horizontal" method="POST" enctype="multipart/form-data">
        	<s:token/>
        <s:actionerror/>
        <s:hidden name="page" />
          <div class="form-group">
            <label for="OrgName" class="col-xs-4 control-label" for=""> <fmt:message key="process.batch.orgName"/><span class="required">*</span></label>
            <div class="col-xs-7">
              <s:textfield name="orgName"  maxlength="200"  cssStyle="form-control" />
                    <span class="alert-danger"> 
                        <s:fielderror>
                        <s:param>orgName</s:param>
                       </s:fielderror>                            
                     </span>
            </div>
          </div>
          <div class="form-group">
            <label for="trialData" class="col-xs-4 control-label"><fmt:message key="process.batch.trialData"/><span class="required">*</span></label>
            <div class="col-xs-8">
              <s:file id="trialData" name="trialData" value="true" />
                 <span class="alert-danger"> 
                    <s:fielderror>
                    <s:param>trialDataFileName</s:param>
                   </s:fielderror>                            
                 </span>
            </div>
          </div>
          <div class="form-group">
            <label for="docZip" class="col-xs-4 control-label"><fmt:message key="process.batch.docZip"/></label>
            <div class="col-xs-8">
              <s:file id="docZip" name="docZip" />
                 <span class="alert-danger"> 
                    <s:fielderror>
                    <s:param>docZipFileName</s:param>
                   </s:fielderror>                            
                 </span>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-icon btn-primary" data-dismiss="modal" onclick="handleBatchUploadAction();"><i class="fa-upload"></i>Upload Trials</button>
        <button type="button" class="btn btn-icon btn-default" onclick="window.top.hidePopWin(true);" data-dismiss="modal"><i class="fa-times-circle"></i>Close</button>
      </div>
</body>
</html>	 