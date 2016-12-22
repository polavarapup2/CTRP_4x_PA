<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="register.searchUser.page.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript">
            function cancelAction() {
                submitForm("${pageContext.request.contextPath}/protected/disClaimerAction.action?actionName=searchTrial.action");
            }
            function searchAction() {
                submitForm("${pageContext.request.contextPath}/searchUsersearch.action");
            }
            function submitForm(action) {
                var form = document.getElementById("searchUserForm");
                form.action = action;
                form.submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="register.searchUser.page.header"/></h1>
        <div class="box" id="filters">
            <s:form id="searchUserForm" name="searchUserForm">
                <s:if test="hasActionErrors()">
                    <div class="alert alert-danger"><s:actionerror/></div>
                </s:if>
                <p><fmt:message key="register.searchUser.formTitle"/></p>
                <table class="form">
                    <tr>
                        <td scope="row" class="label">
                            <label for="emailAddress"><fmt:message key="register.searchUser.emailAddress"/><span class="required">*</span></label>
                        </td>
                        <td>
                            <s:textfield id="emailAddress" name="emailAddress" maxlength="200" size="100" cssStyle="width:200px" />
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="searchAction()"><span class="btn_img"><span class="search"><fmt:message key="register.searchUser.button.search"/></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="cancelAction()"><span class="btn_img"><span class="cancel"><fmt:message key="register.searchUser.button.cancel"/></span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>