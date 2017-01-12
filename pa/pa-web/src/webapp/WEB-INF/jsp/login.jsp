<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<page:applyDecorator name="main">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/loginValidation.js'/>"></script>
<SCRIPT TYPE="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();
} 
<!--
function submitenter(myfield,e) {
 var keycode;
 if (window.event) keycode = window.event.keyCode;
 else if (e) keycode = e.which;
 else return true;

    if (keycode == 13) {            
      myfield.form.submit();
      return false;
   }
else return true;
  
}
function validateForm() {
    if (<%=Boolean.valueOf(System.getProperty("ctrp.env.ci"))%> || validate()) {
       document.forms[0].submit()
    }
}
//-->
</SCRIPT>

</head>
<body>

<h1><fmt:message key="login.title" /></h1>
<div class="box">
<form action="j_security_check" method="post" id="loginForm">
<c:set var="topic" scope="request" value="login"/>
 <table class="form">
            <c:if test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) > 1}">
                <p><fmt:message key="login.instructions"/></p>
            </c:if>
            <c:if test="${not empty param.failedLogin}">
              <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            </c:if>
             <div class="clearfloat"></div>
             <div class="fieldrow">
                <label for="j_username">Username:</label>
                <div class="fieldbox_m required"><input name="j_username" maxlength="100" size="15" type="text" id="j_username"></div>
            </div>
            <div class="fieldrow">
                <label for="j_password">Password:</label>
                <div class="fieldbox_m required"><input name="j_password" maxlength="100" size="15" type="password" autocomplete="off" onKeyPress="return submitenter(this,event)" id="j_password"/></div>
            </div>
            <div>
               <c:if test="${!empty applicationScope['AUTHENTICATION_SOURCE_MAP']}">
                <c:choose>
                  <c:when test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) == 1}">
                    <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
                     <input type="hidden" name="authenticationServiceURL"
                         value="<c:out value="${item.value}"/>" />
                    </c:forEach>
                  </c:when>
                 <c:otherwise>   
                     <div class="fieldrow">
                         <label for="authenticationServiceURL">Account Source:</label>
                         <div class="fieldbox_m required">
                            <select name="authenticationServiceURL" id="authenticationServiceURL" size="1">
                              <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
                                <c:choose>
                                    <c:when test="${fn:contains(item.value,'AuthenticationService')}">
                                        <option value="<c:out value="${item.value}" />" selected="selected">
                                    </c:when>
                                    <c:otherwise>
                                        <option value="<c:out value="${item.value}" />">
                                    </c:otherwise>
                                </c:choose>
        		                            <c:out value="${item.key}" />
        		                        </option>
                              </c:forEach>
                            </select>
                         </div>
                     </div>
                 </c:otherwise>
                </c:choose>
               </c:if>
            </div>
            <div class="clearfloat"></div>
           <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="javascript:void(0)" cssClass="btn" onclick="validateForm();" id="loginLink"><span class="btn_img"><span class="login">Login</span></span></s:a>
                    </li>
                </ul>
            </del>

        </div>


</table></form>
</div>
</body>
</html>
</page:applyDecorator>