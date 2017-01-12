<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialFunding.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleAction(studyResourcingId){
    document.forms[0].cbValue.value = studyResourcingId;
    document.forms[0].page.value = "Edit";
    document.forms[0].action="trialFundingedit.action";
    document.forms[0].submit();
}

function updateNciGrant() {
    var newValue = document.forms[0].nciGranttrue.checked;
    var  url = '/pa/protected/trialFundingupdateNciGrant.action';
    var params = {newValue : newValue};
    var aj = callAjaxPost(null, url, params);
    var div = $('ncigrantmessagediv');
    if(newValue)div.innerHTML = 'Value updated to <b>Yes</b>.';
    else div.innerHTML = 'Value updated to <b>No</b>.';
}

</SCRIPT>

<body>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="reviewfunding"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstractfunding"/>
</c:if>
 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">
  <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialFunding.subtitle" /></h2>
    <c:if test="${fn:length(requestScope.trialFundingList) > 5}">
    <div class="actionstoprow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                        <li><s:a href="trialFunding.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{trialFundingList != null && !trialFundingList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected NIH Grant(s) from the study. Cancel to abort.', 'trialFundingdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected NIH Grant(s) from the study. Cancel to abort.', 'trialFundingdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>                        
                    </pa:adminAbstractorDisplayWhenCheckedOut>
                </ul>
            </del>
        </div>
    </c:if>
   <table>
    <tr>
      <td>
        <s:set var="hasOnlyResultsAbstractorRole">${!(sessionScope.isAdminAbstractor==true
	                                ||sessionScope.isScientificAbstractor==true ||sessionScope.isSuAbstractor==true)}</s:set>
        Is this trial funded by an NCI grant?<span class="required">*</span>
        <s:radio name="nciGrant" id="nciGrant"  list="#{true:'Yes', false:'No'}" onchange="updateNciGrant()" disabled="#hasOnlyResultsAbstractorRole"  />
      </td>
    </tr>
    <tr><td align="right"><div id="ncigrantmessagediv" class="info"/></td></tr>
    <tr><td style="padme10">&nbsp;</td></tr>
    </table>
    <s:if test="trialFundingList != null">
    <s:hidden name="page" />
    <s:hidden name="cbValue" />
    <s:set name="trialFundingList" value="trialFundingList" scope="request"/>
    <display:table name="trialFundingList" id="row" class="data" sort="list" pagesize="200" requestURI="trialFundingquery.action" export="false"
            decorator="gov.nih.nci.pa.decorator.TrialFundingTableDecorator">
        <display:column escapeXml="true" titleKey="trialFunding.funding.mechanism" property="fundingMechanismCode" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialFunding.institution.code" property="nihInstitutionCode" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialFunding.serial.number" property="serialNumber"  sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="studyProtocol.monitorCode" property="nciDivisionProgramCode" sortable="true" headerClass="sortable" />
        <pa:adminAbstractorDisplayWhenCheckedOut>
            <display:column title="Edit" class="action">
                <s:a href="javascript:void(0)" onclick="handleAction(%{#attr.row.id})"><img src='<c:url value="/images/ico_edit.gif"/>' alt="Edit" width="16" height="16" /></s:a>
            </display:column>

            <display:column title="Delete" class="action">
               <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}"
                   fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
               <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
           </display:column>
        </pa:adminAbstractorDisplayWhenCheckedOut>
    </display:table>
  </s:if>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                        <li><s:a href="trialFunding.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{trialFundingList != null && !trialFundingList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected NIH Grant(s) from the study. Cancel to abort.', 'trialFundingdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected NIH Grant(s) from the study. Cancel to abort.', 'trialFundingdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>                        
                    </pa:adminAbstractorDisplayWhenCheckedOut>
                </ul>
            </del>
        </div>
        <h2><fmt:message key="trialFunding.subtitle.delete" /></h2>
        <display:table name="trialFundingDeleteList" id="row" class="data" sort="list" pagesize="200" requestURI="trialFundingquery.action" export="false"
                decorator="gov.nih.nci.pa.decorator.TrialFundingTableDecorator">
        <display:column escapeXml="true" titleKey="trialFunding.funding.mechanism" property="fundingMechanismCode" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialFunding.institution.code" property="nihInstitutionCode" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialFunding.serial.number" property="serialNumber"  sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="studyProtocol.monitorCode" property="nciDivisionProgramCode" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="studyProtocol.inactiveText" property="inactiveCommentText" sortable="true" headerClass="sortable" /> 
        <display:column escapeXml="true" titleKey="studyProtocol.deletedDate" property="lastUpdatedDate" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="studyProtocol.deletedBy" property="userLastUpdated" sortable="true" headerClass="sortable" /> 
    </display:table>
      </s:form>
   </div>
 </body>
 </html>
