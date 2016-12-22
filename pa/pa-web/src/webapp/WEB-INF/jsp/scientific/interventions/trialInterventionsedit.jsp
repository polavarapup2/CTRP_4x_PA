<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="interventions.details.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
            
        <c:url value="/protected/popupInt.action" var="lookupUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=DoseForm&divName=loadDoseFormDetails" var="lookupDoseFormUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=RouteOfAdministration&divName=loadDoseROADetails" var="lookupROAUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=DoseFrequency&divName=loadDoseFreqDetails" var="lookupDoseFreqUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=UnitOfMeasurement&divName=loadDoseUOMDetails" var="lookupDoseUOMUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=UnitOfMeasurement&divName=loadDoseDurationUOMDetails" var="lookupDoseDurationUOMUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=UnitOfMeasurement&divName=loadTotalDoseUOMDetails" var="lookupTotalDoseUOMUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=TargetSite&divName=loadTargetSiteDetails" var="lookupTargetSiteUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=TargetSite&divName=loadApproachSiteDetails" var="lookupApproachSiteUrl" />
        <c:url value="/protected/popupTypeInterventiontype.action?className=MethodCode&divName=loadMethodCodeDetails" var="lookupMethodCodeUrl" />
        
        
        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }

            function interventionAdd() {
                document.interventionForm.action="trialInterventionsadd.action";
                document.interventionForm.submit();     
            }

            function interventionUpdate(){
                document.interventionForm.action="trialInterventionsupdate.action";
                document.interventionForm.submit();
            }

            function statusChange() {
                var url = '/pa/protected/ajaxptpInterventiondisplaySubPage.action';
                var form = $('interventionForm');
                var params = {
                    interventionId: form.interventionIdentifier.value,
                    interventionName: form.interventionName.value,
                    interventionOtherNames: form .interventionOtherNames.value,
                    interventionType: form.interventionType.value
                };
                var div = document.getElementById('loadDetails');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function lookup() {
                showPopup('${lookupUrl}', null, 'Intervention');
            }   

            function lookupDoseForm() {
                showPopup('${lookupDoseFormUrl}', null, 'Dose Form');
            }

            function lookupROA() {
                showPopup('${lookupROAUrl}', null, 'Route of Administration');
            }
            
            function lookupDoseFreq() {
                showPopup('${lookupDoseFreqUrl}', null, 'Dose Frequency');
            }

            function lookupDoseUOM() {
                showPopup('${lookupDoseUOMUrl}', null, 'Dose Unit Of Measure');
            }

            function lookupDoseDurationUOM() {
                showPopup('${lookupDoseDurationUOMUrl}', null, 'Dose Duration Unit Of Measure');
            }

            function lookupTotalDoseUOM() {
                showPopup('${lookupTotalDoseUOMUrl}', null, 'Total Dose Unit Of Measure');
            }

            function lookupTargetSite() {
                showPopup('${lookupTargetSiteUrl}', null, 'Target Site');
            }

            function lookupApproachSite() {
                showPopup('${lookupApproachSiteUrl}', null, 'Approach Site');
            }

            function lookupMethodCode() {
                showPopup('${lookupMethodCodeUrl}', null, 'Method Code');
            }

            function loadDiv(intid) {
                 var url = '/pa/protected/ajaxptpInterventiondisplay.action';
                 var params = { interventionId: intid };
                 var div = $('loadDetails');   
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                 var aj = callAjaxPost(div, url, params);
                 return false;
            }

            function cancel(){
                document.interventionForm.action="trialInterventions.action";
                document.interventionForm.submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="interventions.details.title" /></h1>
        <c:set var="topic" scope="request" value="addintervention"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage />
            <pa:failureMessage/>
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <h2>
                <s:if test="%{currentAction == 'edit'}"><fmt:message key="interventions.edit.title" /></s:if>
                <s:else><fmt:message key="interventions.add.title" /></s:else>
            </h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <s:form name="interventionForm" validate="true">
                            <s:token/>
                            <pa:studyUniqueToken/>
                            <div id="loadDetails">
                                <%@ include file="/WEB-INF/jsp/nodecorate/selectedInterventionDetails.jsp"%>
                            </div>
                        </s:form>
                        <div class="actionsrow">
                            <del class="btnwrapper">
                                <ul class="btnrow">
                                    <li>
                                        <s:if test="%{currentAction == 'edit'}">
                                            <s:a href="javascript:void(0)" cssClass="btn" onclick="interventionUpdate();">
                                                <span class="btn_img"><span class="save"><fmt:message key="interventions.button.save" /></span></span>
                                            </s:a>
                                        </s:if> 
                                        <s:else>
                                            <s:a href="javascript:void(0)" cssClass="btn" onclick="interventionAdd();">
                                                <span class="btn_img"><span class="save"><fmt:message key="interventions.button.save" /></span></span>
                                            </s:a>
                                        </s:else>
                                    </li>
                                    <li>
                                        <s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();">
                                            <span class="btn_img"><span class="cancel"><fmt:message key="interventions.button.cancel" /></span></span>
                                        </s:a>
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