<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="participatingOrganizations.collaborators.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl" />
        
        <script type="text/javascript" language="javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }

            function facilitySave() {
                document.facility.action="collaboratorsfacilitySave.action";
                document.facility.submit();     
            }

            function facilityUpdate() {
                document.facility.action="collaboratorsfacilityUpdate.action";
                document.facility.submit();     
            }

            // do not remove these two callback methods!
            function setpersid(persid) {}

            function setorgid(orgid) {}

            function lookup() {
                showPopup('${lookupUrl}', null, 'Organization');
            }   

            function loadDiv(orgid){
                 var url = '/pa/protected/ajaxptpOrgdisplayOrg.action';
                 var params = { orgId: orgid };
                 var div = document.getElementById('loadOrgDetails');
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                 var aj = callAjaxPost(div, url, params);
                 return false;
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="participatingOrganizations.collaborators.title" /></h1>
        <c:set var="topic" scope="request" value="abstractcollaborator"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
         <s:url id="cancelUrl" namespace="/protected" action="collaborators"/>
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <h2>Collaborator</h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <s:form name="facility">
                            <s:token/>
                            <pa:studyUniqueToken/>
                            <div id="loadOrgDetails">
                                <div id="orgDetailsDiv">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/selectedOrgDetails.jsp"%>
                                </div>
                            </div>
                            <table class="form">
                                <tr>
                                    <td class="label">
                                        <label for="functionalCode">Functional Role:</label><span class="required">*</span>
                                    </td>
                                    <s:set name="functionalCodeValues" value="@gov.nih.nci.pa.enums.StudySiteFunctionalCode@getCollaboratorDisplayNames()" />
                                    <td class="value" colspan="2">
                                        <s:select id="functionalCode" headerKey="" headerValue="--Select--" name="functionalCode" list="#functionalCodeValues" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">
                                        <label for="statusCode">Status:</label>
                                    </td>
                                    <td class="value" colspan="2">
                                        <s:textfield id="statusCode" name="statusCode" readonly="true" cssClass="readonly" maxlength="80" size="80" cssStyle="width: 200px"/>
                                    </td>
                                </tr>
                            </table>
                        </s:form>
                        <div class="actionsrow">
                            <del class="btnwrapper">
                                <ul class="btnrow">
                                    <li>
                                        <s:if test="%{currentAction == 'edit'}">
                                            <s:a href="javascript:void(0)" cssClass="btn" onclick="facilityUpdate();">
                                                <span class="btn_img"> <span class="save">Save</span></span>
                                            </s:a>
                                        </s:if> 
                                        <s:else>
                                            <s:a href="javascript:void(0)" cssClass="btn" onclick="facilitySave();">
                                                <span class="btn_img"> <span class="save">Save</span></span>
                                            </s:a>
                                        </s:else>
                                     <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                    </li>
                                </ul>
                            </del>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>