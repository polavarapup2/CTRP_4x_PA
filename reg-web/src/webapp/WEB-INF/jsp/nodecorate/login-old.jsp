<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/loginValidation.js'/>"></script>
<SCRIPT>
    function showPasswordResetInfo(){
        showPopWin('/registry/popupdisplayPasswordReset.action', 900, 400, '', 'Resetting Your Password');
    }
</SCRIPT>
<body><h1>Login</h1>
<c:set var="topic" scope="request" value="login"/>
<div class="box">

<s:actionmessage/>

 <c:choose>
     <c:when test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) > 1}">
        <p><fmt:message key="login.instructions"/></p>
     </c:when>
     <c:when test="${param.failureMessage == 'noUser'}">
        <div class="alert alert-danger">
             <strong>Please <a title="To Create an Account" href="/registry/registerUser.action">create an account</a> before logging in.</strong>
        </div>
     </c:when>
     <c:otherwise>
           <p style="margin:0; padding:0">Please log in to search, view and register clinical trial details.
             If you have not yet registered, you may do so by clicking <a title="To Create an Account" href="/registry/registerUser.action">here</a>. </p>
     </c:otherwise>
 </c:choose>
<form action="j_security_check" method="post" id="loginForm" onsubmit="return validate();">

 <table style="margin:0 auto">
            <c:if test="${not empty param.failedLogin}">
              <p class="directions"><fmt:message key="errors.password.mismatch"/></p>
            </c:if>
             <tr>
            <td class="space" colspan="2">
                    &nbsp;
            </td>
            </tr>
            <tr>
                <td class="label" scope="row">
                <label for="j_username">Username:</label>
                </td>
                <td class="value">
                    <input name="j_username" maxlength="100" size="25" type="text" id="j_username">
                </td>
            </tr>
            <tr>
                <td class="label" scope="row">
                <label for="j_password">Password:</label>
                </td>
                <td class="value"><input name="j_password" maxlength="100" size="25" type="password" autocomplete="off" id="j_password"/></td>
            </tr>
         <c:if test="${!empty applicationScope['AUTHENTICATION_SOURCE_MAP']}">
         <c:choose>
             <c:when test="${fn:length(applicationScope.AUTHENTICATION_SOURCE_MAP) == 1}">
                  <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
                    <input type="hidden" name="authenticationServiceURL"
                         value="<c:out value="${item.value}"/>" />
                  </c:forEach>
             </c:when>
             <c:otherwise>
            <tr>
                <td class="label" scope="row">
                <label for="authenticationServiceURL">Account Source:</label>
                </td>
                <td class="value">
                     <select name="authenticationServiceURL" id="authenticationServiceURL" size="1">
                        <c:forEach var="item" items="${applicationScope.AUTHENTICATION_SOURCE_MAP}">
                        <c:choose>
                            <c:when test="${fn:contains(item.value,'Dorian')}">
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
                </td>
            </tr>
             </c:otherwise>
         </c:choose>
         </c:if>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <span class="small"><a title="To Create an Account" href="/registry/registerUser.action">Create an Account</a></span>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <span class="small"><a title="To Reset Password" onclick="showPasswordResetInfo();">Forgot your password?</a></span>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><span class="small"><a title="To Search your user name" href="/registry/searchUser.action">Forgot your username?</a></span></td>
            </tr>
</table>
            </div>
           <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:submit id="loginButton" type="image" src="../images/btn_login.gif" cssClass="btn" title="Log In" value="Log In" align="center" />
                    </li>

                </ul>
            </del>

        </div><div id="newSessionMarker"></div>
        </form>
</div>
</body>

