<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="register.user.page.title"/></title>
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">
function handleAction(){
    document.registerUser.page.value = "Submit";
    document.registerUser.action="registerUserverifyEmail.action";
    document.registerUser.submit();
}
</SCRIPT>

 <div class="row">
   <%@ include file="/WEB-INF/jsp/nodecorate/loginInfo.jsp" %>
   <div class="col-xs-6">
     <ul class="nav nav-tabs">
       <li><a href="<s:url action='protected/disClaimerAction.action?actionName=searchTrial.action' />" ><i class="fa-sign-in"></i>Sign In</a></li>
       <li class="active"><a href="<s:url action='registerUser.action' />" ><i class="fa-pencil-square-o"></i>Sign Up</a></li>
       <li><a href="#forgot-password" data-toggle="tab"><i class="fa-key"></i>Forgot Password</a></li>
     </ul>
     <div class="tab-content">
       <div class="tab-pane fade" id="sign-in">
       </div>
       <div class="tab-pane fade in active" id="sign-up">
         <c:set var="topic" scope="request" value="createaccount"/>
         <s:form cssClass="form-horizontal" role="form" name="registerUser" validate="true" method="POST">
         	<s:hidden name="page" />
         	<s:url id="existingLdapAccountUrl" action="registerUserexistingLdapAccount.action" />
   			<p align="center" class="info"><i style="color:red;" class="fa fa-exclamation fa-lg fa-fw"></i>If you already have an NIH or NCI account, click <s:a href="%{existingLdapAccountUrl}">here</s:a> to proceed.</p>
            <p align="center" class="info">If you do not have an NIH or NCI account, enter the email address below and click Next.</p>
            
            <div class="form-group">
	        	<label for="registerUser_registryUserWebDTO_emailAddress" class="col-xs-4 control-label">
	           <fmt:message key="register.user.emailAddress" /><span class="required">*</span></label>
	           <div class="col-xs-6"><s:textfield id="registerUser_registryUserWebDTO_emailAddress" name="registryUserWebDTO.emailAddress" maxlength="200" size="100"
	               cssClass="form-control" /> <span class="alert-danger"> <s:fielderror>
	               <s:param>registryUserWebDTO.emailAddress</s:param></s:fielderror> </span>
	           </div>
	        </div>
            <div class="bottom">
	              <button type="button" class="btn btn-icon-alt btn-primary" onClick="handleAction();">Next<i class="fa-arrow-circle-right"></i></button>
            </div>
         </s:form>
       </div>
       <div class="tab-pane fade" id="forgot-password">
         <div class="tab-inside">
           <h4 class="heading"><span>Resetting Your Password</span></h4>
           <p>If you forgot your password, please visit the NCI Password Station at <a href="http://password.nci.nih.gov" target="_blank">http://password.nci.nih.gov</a> and follow the instructions there.</p>
           <p>If you need additional assistance or have questions, you can email NCI CBIIT Application Support at <a href="mailto:ncicbiit@mail.nih.gov">ncicbiit@mail.nih.gov</a>,
             or call <strong>240-276-5541</strong> or toll free <strong>888-478-4423</strong>.</p>
         </div>
       </div>
     </div>
   </div>
 </div>
 