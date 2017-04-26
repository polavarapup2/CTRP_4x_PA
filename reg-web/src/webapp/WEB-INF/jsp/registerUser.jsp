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
       <li><a href="#forgot-password" data-toggle="tab"><i class="fa-key"></i>Reset Password</a></li>
     </ul>
     <div class="tab-content">
       <div class="tab-pane fade" id="sign-in">
       </div>
       <div class="tab-pane fade in active" id="sign-up">
         <c:set var="topic" scope="request" value="createaccount"/>
         <s:form cssClass="form-horizontal" role="form" name="registerUser" validate="true" method="POST">
         	<s:hidden name="page" />
         	<s:url id="existingLdapAccountUrl" action="registerUserexistingLdapAccount.action" />
   			<p align="center" class="info"><i style="color:red;" class="fa fa-exclamation fa-lg fa-fw"></i>To request a CTRP user account, enter the email address you plan to use and click Next.</p>
            <p align="center" class="info">Then follow the onscreen instructions.</p>

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
           <p>If you forgot your password or simply want to reset it, please visit the CTRP Password Self Service Station <a href="/ctrp-password/">HERE</a> and follow the instructions there.</p>
           <p>If you need additional assistance or have questions, send an email to <a href="mailto:ctrp_support@nih.gov">ctrp_support@nih.gov</a>.</p>
         </div>
       </div>
     </div>
   </div>
 </div>
 