<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/nodecorate/accountScripts.jsp" %>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<!-- Begin page content -->
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
             <s:form action="registerUsercompleteActivation.action" name="completeActivation" id="completeActivation" method="POST" cssClass="form-horizontal" role="form">
             <s:if test="hasActionErrors()">
             	<div class="alert alert-danger">
                	<s:actionerror/>
               	</div>
             </s:if>
                <s:hidden name="page" />
                <s:hidden name="token" />
                <div>Please enter the LDAP ID of the newly created user account and click <b>Activate</b>.</div>
                <br/>
                 <div class="form-group">
                    <label for="ldapID" class="col-xs-4 control-label">LDAP ID:<span class="required">*</span></label>
                    <div class="col-xs-7"><s:textfield placeholder="LDAP ID" required="true" name="ldapID" maxlength="64" size="32" cssClass="form-control" id="ldapID"/></div>
                 </div>
               <div class="bottom">
	              <button type="submit" class="btn btn-icon-alt btn-primary">Activate<i class="fa-arrow-circle-right"></i></button>
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