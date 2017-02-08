<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery-1.10.2.min.js'/>"></script>
<SCRIPT language="JavaScript">
    function submitform() {
        var deleteReason = document.getElementById("deleteReason");
        var deleteReasonValue = deleteReason.options[deleteReason.selectedIndex].value;
        if(!deleteReasonValue) {
        	alert("Please select a delete reason from the list.");
        		return false;
        	}
        	top.window.setDeleteReason(deleteReasonValue);
            window.top.hidePopWin(true);
    }
</SCRIPT> 
</head> 
<body>
<s:form id="subjectDeleteReason" name="subjectDeleteReason" >
     <div class="modal-body">
        <p class="form-control-static">
                    Please select a reason below and then click OK to remove the subject from the study. Cancel to abort. 
               </p><br/>
            <div class="form-group"><label for="deleteReason"  class="col-xs-1 control-label">Reason:</label>
            <div class="col-xs-4">
            <s:select id="deleteReason" name="deleteReason" headerValue="-Select-" headerKey="" list="reasonsList" cssClass="form-control" /></div>
          </div>
       <div class="form-group">
        <div class="col-xs-4 mt20">
                    <button type="button" class="btn btn-icon btn-default" onclick="submitform();"><i class="fa-floppy-o"></i>OK</button>
                    <button type="button" class="btn btn-icon btn-default" onclick="window.top.hidePopWin();"><i class="fa-times-circle"></i>Cancel</button>
                    </div>
           </div>
    </div>
</s:form>
</body>
</html>