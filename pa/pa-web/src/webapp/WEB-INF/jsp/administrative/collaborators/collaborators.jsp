<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.collaborators.title" /></title>
<s:head />

</head>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/coppa.js"/>"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleEdit(studyResourcingId){
    document.collaboratorsForm.cbValue.value = studyResourcingId;
    document.collaboratorsForm.action="collaboratorsedit.action";
    document.collaboratorsForm.submit();
}



function handleCreate(){
    document.collaboratorsForm.action="collaboratorscreate.action";
    document.collaboratorsForm.submit();
}

</SCRIPT>

<body>
<h1><fmt:message key="participatingOrganizations.collaborators.title" /></h1>
<c:set var="topic" scope="request" value="abstractcollaborator"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /><pa:failureMessage/><s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> <s:form name="collaboratorsForm">
        <pa:studyUniqueToken/>
    <h2><fmt:message
        key="participatingOrganizations.collaborators.title" /></h2>
    <table class="form">
        <tr>
            <td colspan="2"><s:hidden name="cbValue" />
            <s:set name="organizationList" value="organizationList" scope="request"/>
            <display:table name="organizationList" id="row" class="data" pagesize="200" requestURI="collaborators.action">
                <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true">
                    <a href="javascript:void(0);" onclick="displayOrgDetails(<c:out value="${row.nciNumber}"/>)"><c:out value="${row.nciNumber}"/></a>
                </display:column>
                <display:column escapeXml="true" property="name" titleKey="participatingOrganizations.name" class="sortable" />
                <display:column escapeXml="true" property="status" titleKey="participatingOrganizations.status" class="sortable" />
                <display:column escapeXml="true" property="functionalRole" titleKey="participatingOrganizations.functionalRole" class="sortable" />
                <pa:adminAbstractorDisplayWhenCheckedOut>
                    <display:column titleKey="participatingOrganizations.edit" headerClass="centered" class="action">
                        <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.id})">
                            <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                        </s:a>
                    </display:column>
                    <display:column titleKey="participatingOrganizations.unlink" headerClass="centered" class="action">
                        <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
                        <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
                    </display:column>
                </pa:adminAbstractorDisplayWhenCheckedOut>
            </display:table>
            </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
        <ul class="btnrow">
            <pa:adminAbstractorDisplayWhenCheckedOut>
                <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
                <s:if test="%{organizationList != null && !organizationList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected collaborator(s) from the study. Cancel to abort.', 'collaboratorsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected collaborator(s) from the study. Cancel to abort.', 'collaboratorsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>                
            </pa:adminAbstractorDisplayWhenCheckedOut>
        </ul>
    </del></div>
</s:form></div>
</body>
</html>
