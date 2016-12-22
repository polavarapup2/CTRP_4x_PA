<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="anatomicSite.details.title" /></title>
<s:head />


<SCRIPT LANGUAGE="JavaScript" type="text/javascript">


// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

 


function handleCreate(){
    document.anatomicSiteForm.action="anatomicSitecreate.action";
    document.anatomicSiteForm.submit();
}
</SCRIPT>
</head>
<body>
<h1><fmt:message key="anatomicSite.details.title"/></h1>
<c:set var="topic" scope="request" value="anatomicsite"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <pa:failureMessage/>
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

    <s:form name="anatomicSiteForm">
        <s:hidden name="selectedRowIdentifier"/>
    <h2>
        <fmt:message key="anatomicSite.details.title"/>
    </h2>
    <c:if test="${fn:length(requestScope.anatomicSiteList) > 5}">
    <div class="actionstoprow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
            <s:if test="%{anatomicSiteList != null && !anatomicSiteList.isEmpty()}">
                <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected anatomic site(s) from the study. Cancel to abort.', 'anatomicSitedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected anatomic site(s) from the study. Cancel to abort.', 'anatomicSitedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                <li><pa:toggleDeleteBtn/></li>
            </s:if>            
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
    </c:if>
    <table class="form">
        <tr>
            <td colspan="2">
            <s:set name="anatomicSiteList" value="anatomicSiteList" scope="request"/>
            <display:table name="anatomicSiteList" id="row" class="data" sort="list" pagesize="10" requestURI="anatomicSite.action" defaultorder="ascending" defaultsort="1">
                <display:column escapeXml="true" property="code" sortable="true" titleKey="anatomicSite.code" headerClass="sortable" />
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <display:column titleKey="anatomicSite.delete" headerClass="centered" class="action">
                        <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
                        <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
                    </display:column>
                </pa:scientificAbstractorDisplayWhenCheckedOut>
            </display:table>
        </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
            <s:if test="%{anatomicSiteList != null && !anatomicSiteList.isEmpty()}">
                <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected anatomic site(s) from the study. Cancel to abort.', 'anatomicSitedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected anatomic site(s) from the study. Cancel to abort.', 'anatomicSitedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                <li><pa:toggleDeleteBtn/></li>
            </s:if>            
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>
