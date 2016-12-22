<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="manageAccrualAccess.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
            }

            function accessAdd() {
                document.editForm.action="manageAccrualAccessadd.action";
                document.editForm.submit();
            }

            function accessUpdate() {
                document.editForm.action="manageAccrualAccessupdate.action";
                document.editForm.submit();
            }

            function cancel() {
                document.editForm.action="manageAccrualAccess.action";
                document.editForm.submit();
            }

            function loadEmailDiv() {
                var url = '/pa/protected/ajaxManageAccrualAccessEmail.action';
                var params = { registryUserId: $('registryUserId').value };
                var div = $('emailDiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function loadPhoneDiv() {
                var url = '/pa/protected/ajaxManageAccrualAccessPhone.action';
                var params = { registryUserId: $('registryUserId').value };
                var div = $('phoneDiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function loadSiteRecruitmentStatusDiv() {
                var url = '/pa/protected/ajaxManageAccrualAccessSiteRecruitmentStatus.action';
                var params = { studySiteId: $('studySiteId').value };
                var div = $('siteRecruitmentStatusDiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="manageAccrualAccess.title" /></h1>
        <c:set var="topic" scope="request" value="accrualedit"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
            <h2><fmt:message key="manageAccrualAccess.title"/></h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <table class="form">
                            <tr>
                                <td colspan="2">
                                    <h3>
                                        <s:if test="%{currentAction == 'create'}">
                                            <fmt:message key="manageAccrualAccess.create.title"/>
                                        </s:if>
                                        <s:else><fmt:message key="manageAccrualAccess.edit.title"/></s:else>
                                    </h3>
                                    <s:form name="editForm">
                                        <s:token/>
                                        <pa:studyUniqueToken/>
                                        <s:hidden name="currentAction"/>
                                        <s:hidden name="access.identifier"/> 
                                        <table class="form">
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.userName"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value" style="width: 250px">
                                                    <s:if test="%{currentAction == 'create'}">
                                                        <s:select id="registryUserId" headerKey="" headerValue="--Select--" 
                                                                  name="access.registryUserId" list="regUserNames" listKey="id" 
                                                                  listValue="name" onchange="loadEmailDiv();loadPhoneDiv();"/>
                                                    </s:if><s:else>
                                                        <s:textfield name="access.userName" cssStyle="width:200px;float:left" readonly="true" cssClass="readonly"/>
                                                        <s:hidden name="access.registryUserId"/>
                                                    </s:else>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.email"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value">
                                                    <div id="emailDiv">
                                                        <%@ include file="/WEB-INF/jsp/nodecorate/manageAccrualAccessEmail.jsp" %>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.phone"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value">
                                                    <div id="phoneDiv">
                                                        <%@ include file="/WEB-INF/jsp/nodecorate/manageAccrualAccessPhone.jsp" %>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.siteName"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value" style="width: 250px">
                                                    <s:if test="%{currentAction == 'create'}">
                                                        <s:set name="siteValues" value="sites" />
                                                        <s:select id="studySiteId" headerKey="" headerValue="--Select--" 
                                                                  name="access.studySiteId" list="#siteValues" 
                                                                  onchange="loadSiteRecruitmentStatusDiv();"/>
                                                    </s:if>
                                                    <s:else>
                                                        <s:textfield name="access.siteName" cssStyle="width:400px;float:left" readonly="true" cssClass="readonly"/>
                                                        <s:hidden name="access.studySiteId"/>
                                                    </s:else>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.siteRecruitmentStatus"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value">
                                                    <div id="siteRecruitmentStatusDiv">
                                                        <%@ include file="/WEB-INF/jsp/nodecorate/manageAccrualAccessSiteRecruitmentStatus.jsp" %>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.ActiveInactiveCode@getDisplayNames()" />
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.statusCode"/></s:label>
                                                    <span class="required">*</span>
                                                </td>
                                                <td class="value" style="width: 250px">
                                                    <s:select headerKey="" headerValue="--Select--" name="access.statusCode" list="#statusCodeValues"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="label">
                                                    <s:label><fmt:message key="manageAccrualAccess.requestDetails"/></s:label>
                                                </td>
                                                <td class="value">
                                                    <s:textarea name="access.requestDetails" rows="3" cssStyle="width:420px;float:left"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </s:form>
                                    <div class="actionsrow">
                                        <del class="btnwrapper">
                                            <ul class="btnrow">
                                                <li>
                                                    <s:if test="%{currentAction == 'create'}">
                                                        <s:a href="javascript:void(0)" cssClass="btn" onclick="accessAdd();"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                                    </s:if>
                                                    <s:else>
                                                        <s:a href="javascript:void(0)" cssClass="btn" onclick="accessUpdate();"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                                    </s:else>
                                                </li>
                                                <li>
                                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();">
                                                        <span class="btn_img"><span class="cancel">Cancel</span></span>
                                                    </s:a>
                                                </li>
                                            </ul>
                                        </del>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>