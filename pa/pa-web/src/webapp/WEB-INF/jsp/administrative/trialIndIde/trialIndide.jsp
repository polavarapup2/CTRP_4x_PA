<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialIndide.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleAction(studyProtocolIi){
    document.forms[0].cbValue.value = studyProtocolIi;
    document.forms[0].page.value = "Edit";
    document.forms[0].action="trialIndideedit.action";
    document.forms[0].submit();
}



</SCRIPT>

<body>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="reviewind"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstractind"/>
</c:if>
 <h1><fmt:message key="trialIndide.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">
  <pa:sucessMessage/>
  <pa:failureMessage/>
    <s:form>
        <pa:studyUniqueToken/>
        <s:actionerror/>
    <h2><fmt:message key="trialIndide.subtitle" /></h2>
    <s:if test="studyIndideList != null">
    <s:hidden name="page" />
    <s:hidden name="cbValue" />
    <s:set name="studyIndideList" value="studyIndideList" scope="request"/>
    <display:table name="studyIndideList" id="row" class="data" sort="list"  pagesize="200" requestURI="trialIndidequery.action" export="false">
        <display:column escapeXml="true" titleKey="trialIndide.indldeType" property="indldeType" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialIndide.indideNumber" property="indldeNumber" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialIndide.grantor" property="grantor"   sortable="true" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="trialIndide.holderType" property="holderType"   sortable="true" headerClass="sortable"/>
    	<display:column escapeXml="true" titleKey="trialIndide.nihInstHolderCode" property="nihInstHolder"   sortable="true" headerClass="sortable"/>
    	<display:column escapeXml="true" titleKey="trialIndide.nciDivProgHolderCode" property="nciDivProgHolder"   sortable="true" headerClass="sortable"/>
    	<display:column escapeXml="true" titleKey="trialIndide.expandedAccessIndicator" property="expandedAccessIndicator"   sortable="true" headerClass="sortable"/>
    	<display:column escapeXml="true" titleKey="trialIndide.expandedAccessStatusCode" property="expandedAccessStatus"   sortable="true" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="trialIndide.exemptIndicator">
            <pa:displayBoolean value="${row.exemptIndicator}"/>
        </display:column>
        
        <pa:adminAbstractorDisplayWhenCheckedOut>
            <display:column title="Edit" class="action">
                <s:a href="javascript:void(0)" onclick="handleAction(%{#attr.row.id})"><img src='<c:url value="/images/ico_edit.gif"/>' alt="Edit" width="16" height="16"/></s:a>
            </display:column>
            <display:column title="Delete" class="action">
        	   <s:checkbox name="objectsToDelete" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
            </display:column>
        </pa:adminAbstractorDisplayWhenCheckedOut>
    </display:table>
  </s:if>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                        <li><s:a href="trialIndide.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{studyIndideList != null && !studyIndideList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected IND/IDE(s) from the study. Cancel to abort.', 'trialIndidedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected IND/IDE(s) from the study. Cancel to abort.', 'trialIndidedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>
                    </pa:adminAbstractorDisplayWhenCheckedOut>
                </ul>
            </del>
        </div>
    </s:form>
   </div>
 </body>
 </html>