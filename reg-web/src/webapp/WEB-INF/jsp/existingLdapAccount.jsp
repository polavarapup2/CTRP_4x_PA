<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/nodecorate/accountScripts.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
        function handleAction(){
            document.existingAccount.page.value = "Submit";
            document.existingAccount.action="registerUserregisterExistingLdapAccount.action";
            if (validate()) {
                document.existingAccount.submit()
             }
        }
        function validate() {
            var box = document.existingAccount.username;
            
            re = /^[0-9a-zA-Z\_\.\-]*$/;

            if (!re.exec(box.value)) {
                alert("Invalid Entry:\nOnly Alphanumeric, _ or . are allowed.");
                return false;
            }
            return true;
        }
</SCRIPT>

<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<!-- B egin page content -->
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
             <s:form name="existingAccount" id="existingAccount" method="POST" cssClass="form-horizontal" role="form" >
             <s:if test="hasActionErrors()">
             	<div class="alert alert-danger">
                	<s:actionerror/>
               	</div>
             </s:if>
                <s:hidden name="page" />
                 <div class="form-group">
                    <label for="username" class="col-xs-4 control-label"><fmt:message key="register.user.username"/></label>
                    <div class="col-xs-7"><s:textfield required="true" name="userWebDTO.username" maxlength="15" size="20" cssClass="form-control" id="username"/></div>
                 </div>
                 <div class="form-group">
                    <label for="password" class="col-xs-4 control-label"><fmt:message key="register.user.password"/></label>
                    <div class="col-xs-7"><s:password required="true"  name="userWebDTO.password"  showPassword="true" maxlength="100" size="35"   cssClass="form-control" id="password"/></div>
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