<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
<script type="text/javascript" language="javascript">
     function sendEmail() {
         var url = '/pa/protected/popupPlannedMarkersendEmailRequestWithMarkerUpdate.action?nciIdentifier='+$('nciIdentifier').value+'&selectedRowIdentifier='+$('selectedRowIdentifier').value;
         var params = {
             'plannedMarker.foundInHugo': $('foundInHugo').checked,
             'plannedMarker.fromEmail': $('fromEmail').value,
             'plannedMarker.hugoCode': $('hugoCode').value,
             'plannedMarker.message': $('message').value,
             'plannedMarker.name': $('name').value
         };
         var div = $('cdeRequest');
         div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
         var options = {
             onComplete: function(transport) {
                             if ($('passedValidation').value == 'true') {
                                 top.window.loadTopDiv();
                             } else {
                                toggleHugoCode();
                             }
                         }      
         };
         var aj = callAjaxPost(div, url, params, options);
     }

     function toggleHugoCode() {
         var foundInHugo = $('foundInHugo').checked;
         if (foundInHugo) {
             $('hugoCodeRow').show();
         } else {
             $('hugoCodeRow').hide();
         }
     }
</script>
<div id="cdeRequest">
    <s:hidden id="passedValidation" name="passedValidation"/>
     <s:hidden name="plannedMarker.dateEmailSent" id="dateEmailSent"/>
     <s:hidden name="nciIdentifier" id="nciIdentifier"/>
     <s:hidden name="selectedRowIdentifier" id="selectedRowIdentifier"/>
    <div>
        <pa:failureMessage/>
        <pa:sucessMessage/>
    </div>
    <h2><fmt:message key="plannedMarker.request.title"/></h2>
    <table class="form">
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedmMarker.protocolId"><fmt:message key="plannedMarker.protocolId"/>:</s:label><span class="required">*</span>
            </td>
            <td class="value" colspan="2">
                <s:property value="nciIdentifier"/>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedmMarker.toEmail"><fmt:message key="plannedMarker.request.toEmail"/>:</s:label><span class="required">*</span>
            </td>
            <td class="value" colspan="2">
                <s:property value="toEmail"/>
                <s:hidden id="toEmail" name="toEmail" />
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedMarker.fromEmail"><fmt:message key="plannedMarker.request.fromEmail" />:</s:label><span class="required">*</span>
            </td>
            <td class="value" colspan="2">
                <s:textfield id="fromEmail" name="plannedMarker.fromEmail" maxlength="200" size="200" cssStyle="width: 200px" /> 
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.fromEmail</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedMarker.name"><fmt:message key="plannedMarker.request.markerName" />:</s:label><span class="required">*</span>
            </td>
            <td>
                <s:textfield id="name" name="plannedMarker.name" maxlength="200" size="200" cssStyle="width: 200px"/>
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.name</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="plannedMarker.foundInHugo"><fmt:message key="plannedMarker.request.foundInHugo"/>:</s:label></td>
            <td class="value" colspan="2">
                <s:checkbox id="foundInHugo" name="plannedMarker.foundInHugo" onclick="toggleHugoCode();" />
                <a href="http://www.genenames.org" target="_blank">HUGO</a>
            </td>
        </tr>
        <tr id="hugoCodeRow" style="display: none">
            <td scope="row" class="label">
                <s:label for="plannedMarker.hugoCode"><fmt:message key="plannedMarker.request.hugoCode"/>:</s:label><span class="required">*</span>
            </td>
            <td class="value" colspan="2">
                <s:textfield id="hugoCode" name="plannedMarker.hugoCode" maxlength="200" size="200" cssStyle="width: 200px"/>
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.hugoCode</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedMarker.message"><fmt:message key="plannedMarker.request.message"/>:</s:label>
            </td>
            <td class="value" colspan="2">
                <s:textarea id="message" name="plannedMarker.message" cssStyle="width:606px" rows="4" /> 
            </td>
        </tr>
    </table>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="sendEmail();">
                        <span class="btn_img"><span class="save">Send Email</span></span>
                    </s:a>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                        <span class="btn_img"><span class="cancel"><fmt:message key="plannedMarker.lookup.cancel"/></span></span>
                    </s:a>
                </li>
            </ul>
        </del>
    </div>
</div>
