<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery-1.11.1.min.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">

            jQuery(function(){
                jQuery(window).keypress(function(e) {
                    if(e.keyCode == 13) {                 
                      loadResults();
                    }
                  });           
            });
            
             function reset() {
                 $('searchName').value = '';
                 $('searchPublicId').value = '';
                 $('searchBothTerms').value='Both'
                 var ele = document.getElementsByName("caseType");
                 for(var i=0;i<ele.length;i++){
                    ele[i].checked = false;
                 }
                 var ele = document.getElementsByName("highlightRequired");
                 for(var i=0;i<ele.length;i++){
                    ele[i].checked = false;
                 }
             }
              function loadTopDiv(value) {
                var eval = $('plannedMarker.evaluationType').value;
                var assayType =$('plannedMarker.assayType').value; 
                var bioUse =$('plannedMarker.assayUse').value;
                var bioPurpose =$('plannedMarker.assayPurpose').value;
                var specimenType =$('plannedMarker.tissueSpecimenType').value;
                var evalOther =$('plannedMarker.evaluationTypeOtherText').value;
                var assayOther = $('plannedMarker.assayTypeOtherText').value;
                var specimenOther = $('plannedMarker.specimenTypeOtherText').value;
                top.window.loadDiv(value,eval,assayType,bioUse,bioPurpose,specimenType,evalOther,assayOther,specimenOther);
             }
             function loadResults() {     
                 var caseValue = document.forms[0].caseTypetrue.checked;
                 var highlightValue = document.forms[0].highlightRequiredtrue.checked;
                 var searchBothTermsValue = document.forms[0].searchBothTerms.value;
                 var url= '/pa/protected/popupPlannedMarkerlookup.action';
                 var params = {
                         name: $('searchName').value,
                         publicId: $('searchPublicId').value, 
                         caseType :caseValue,
                         highlightRequired :highlightValue,
                         searchBothTerms : searchBothTermsValue,
                         showActionColumn:$('showActionColumn').value
                 };
                 var div = $('getCaDSR');
                 div.innerHTML = '<div><img alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, params);
             }

             function loadRequestEmail() {
                 $('searchcaDSR').hide();
                 $('getCaDSR').hide();
                 $('actionsrow').hide();
                 $('line').hide();
                 $('requestEmail').show();
                 var url = '/pa/protected/popupPlannedMarkersetupEmailRequest.action';
                 var div = $('requestEmail');
                 div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, {});
             }
        </script>
        <style>
.highlight
{
background-color:yellow;
}
</style>
    </head>
    <body>
        <s:form id="cadsrLookup" name="cadsrLookup">
        <s:hidden id="plannedMarker.evaluationType" name="plannedMarker.evaluationType"/>
        <s:hidden id="plannedMarker.assayType" name="plannedMarker.assayType"/>
        <s:hidden id="plannedMarker.assayUse" name="plannedMarker.assayUse"/>
        <s:hidden id="plannedMarker.assayPurpose" name="plannedMarker.assayPurpose"/>
        <s:hidden id="plannedMarker.tissueSpecimenType" name="plannedMarker.tissueSpecimenType"/>
        <s:hidden id="plannedMarker.evaluationTypeOtherText" name="plannedMarker.evaluationTypeOtherText"/>
        <s:hidden id="plannedMarker.assayTypeOtherText" name="plannedMarker.assayTypeOtherText"/>
        <s:hidden id="plannedMarker.specimenTypeOtherText" name="plannedMarker.specimenTypeOtherText"/>
        <s:hidden id="showActionColumn" name="showActionColumn"/>
            <div class="box" id="searchcaDSR">
                <h2><fmt:message key="plannedMarker.lookup.title"/></h2>
                <table class="form">
                    <tr>
                        <td scope="row" class="label">
                            <label for="caseType"><fmt:message key="plannedMarker.lookup.CaseSearch"/>:</label>
                        </td>
                        <td nowrap="nowrap"> 
                          <s:radio name="caseType" id="caseType" value='false' list="#{'true':'Yes', 'false':'No'}"></s:radio>
                        </td>
                        <td scope="row" class="label">
                            <label for="highlightRequired"><fmt:message key="plannedMarker.lookup.highlight"/>:</label>
                        </td>
                        <td nowrap="nowrap"> 
                          <s:radio name="highlightRequired" id="highlightRequired" value='true' list="#{'true':'Yes', 'false':'No'}"></s:radio>
                        </td>

                    </tr>
                    <tr>
                       <td scope="row" class="label">
                            <label for="searchBothTerms"><fmt:message key="plannedMarker.lookup.searchBothTerms"/>:</label>
                       </td>
                       <td nowrap="nowrap">  
                          <s:select id="searchBothTerms"
                                    name="searchBothTerms"
                                    value="defaultSearchScope"
                                    list="#{'Primary':'Primary Term','Synonym':'Synonym','both':'Both'}"
                                    cssStyle="width:206px" />
                       </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="searchName"><fmt:message key="plannedMarker.lookup.name"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchName" name="searchName" maxlength="60" size="60" cssStyle="width:200px"/>
                        </td>
                        <td scope="row" class="label">
                            <label for="searchPublicId"><fmt:message key="plannedMarker.lookup.publicIdExact"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchPublicId" name="searchPublicId" maxlength="60" size="60" cssStyle="width:200px"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="requestEmail" style="display: none">
                <jsp:include page="/WEB-INF/jsp/scientific/plannedMarker/requestEmail.jsp"/>
            </div>
            <div id="actionsrow" class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="loadResults();">
                                <span class="btn_img"><span class="search"><fmt:message key="plannedMarker.lookup.search"/></span></span>
                            </s:a>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="reset();">
                                <span class="btn_img"><span class="cancel"><fmt:message key="plannedMarker.lookup.reset"/></span></span>
                            </s:a>
                            <s:if test="showActionColumn == null">
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="loadRequestEmail();">
                                <span class="btn_img"><span class="search"><fmt:message key="plannedMarker.lookup.createRequest"/></span></span>
                            </s:a>
                            </s:if>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                                <span class="btn_img"><span class="cancel"><fmt:message key="plannedMarker.lookup.cancel"/></span></span>
                            </s:a>
                        </li>
                    </ul>
                </del>
            </div>
            <div id="line" class="line"></div>
            <div id="getCaDSR" align="center">
                <jsp:include page="/WEB-INF/jsp/scientific/plannedMarker/cadsrLookupResults.jsp"/>
            </div>
        </s:form>
    </body>
</html>