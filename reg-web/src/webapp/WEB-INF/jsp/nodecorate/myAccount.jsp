<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>My Account</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<%@ include file="/WEB-INF/jsp/nodecorate/accountScripts.jsp" %>
<script type="text/javascript" language="javascript">
	function changeActiveStyle(windowelem, selectedstyle) {
		windowelem.setActiveStyleSheet(selectedstyle);
		if(window.parent != windowelem) {
			changeActiveStyle(window.parent, selectedstyle);
		} 
	}
</script>
</head>
<body>
	<div class="modal-body">
           <c:set var="topic" scope="request" value="myaccount"/>
           <s:form cssClass="form-horizontal" role="form" id="myAccountForm" name="myAccountForm" method="POST" >
               <s:token/>
               <s:actionmessage cssClass="alert alert-info"/>
               <s:actionerror/>
               <s:hidden name="registryUserWebDTO.id" />
               <s:hidden name="registryUserWebDTO.csmUserId" />
               <s:hidden name="userWebDTO.username" />
               <s:hidden name="page" />
                   <%@include file="accountCommonForm.jsp" %>
              <div class="form-group">
	            <label for="option1" class="col-xs-4 control-label">Color Scheme</label>
	            <div class="col-xs-8">
	              <div class="btn-group" data-toggle="buttons">
	                <label class="btn btn-default" onClick="changeActiveStyle(window, 'default'); return false;">
	                  <input type="radio" name="options" id="option1">
	                  Light </label>
	                <label class="btn btn-default" onClick="changeActiveStyle(window,'alternate 1'); return false;">
	                  <input type="radio" name="options" id="option2">
	                  Dark </label>
	                <label class="btn btn-default" onClick="changeActiveStyle(window,'alternate 2'); return false;">
	                  <input type="radio" name="options" id="option3">
	                  Scheme1</label>
	                <label class="btn btn-default" onClick="changeActiveStyle(window,'alternate 3'); return false;">
	                  <input type="radio" name="options" id="option4">
	                  Scheme2 </label> 
	                <label class="btn btn-default" onClick="changeActiveStyle(window,'alternate 4'); return false;">
	                  <input type="radio" name="options" id="option5">
	                  Scheme3 </label> 
	              </div>
	              <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.select_color_scheme"/>" data-placement="top" data-trigger="hover"></i> </div>
	          </div> 
               <div class="modal-footer">
               	<button type="button" class="btn btn-icon btn-primary" data-dismiss="modal" onClick="handleRegisterUserAction();"><i class="fa-floppy-o"></i>Save</button>
		        <button type="button" class="btn btn-icon btn-default" onclick="window.top.hidePopWin(true);" data-dismiss="modal"><i class="fa-times-circle"></i>Close</button>
		      </div>
           </s:form>
       </div>
   </div>
</body>
</html>