<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="disease.details.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupDis.action" var="lookupUrl" />
        
        <script type="text/javascript" language="javascript" >
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
            }

            function diseaseUpdate() {
                document.diseaseForm.action="diseaseupdate.action";
                document.diseaseForm.submit();
            }

            function lookup() {
                showPopup('${lookupUrl}', null, 'Disease');
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="disease.details.title" /></h1>
        <c:set var="topic" scope="request" value="abstractdisease"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
         <s:url id="cancelUrl" namespace="/protected" action="disease"/>
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
            <h2>
                <s:if test="%{currentAction == 'edit'}"> 
                    <fmt:message key="disease.edit.details.title"/>
                </s:if>
            </h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <s:form name="diseaseForm">
                            <s:token/>
                           <div id="loadDetails">
                                <%@ include file="/WEB-INF/jsp/nodecorate/selectedDiseaseDetails.jsp"%>
                            </div>
                        </s:form>
                        <div class="actionsrow">
                            <del class="btnwrapper">
                                <ul class="btnrow">
                                    <li>
                                        <s:if test="%{currentAction == 'edit'}">
                                            <s:a href="javascript:void(0)" cssClass="btn" onclick="diseaseUpdate();">
                                                <span class="btn_img"> <span class="save">Save</span></span>
                                            </s:a>
                                         <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                        </s:if> 
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