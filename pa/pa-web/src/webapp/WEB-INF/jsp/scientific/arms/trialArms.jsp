<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><s:if test="%{currentAction == 'listArm'}">
        <fmt:message key="arms.details.title" /></s:if>
    <s:elseif test="%{currentAction == 'listGroup'}">
        <fmt:message key="arms.obs.details.title" /></s:elseif></title>
<s:head />

</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleEditArm(rowId){
    document.armForm.selectedArmIdentifier.value = rowId;
    document.armForm.action="trialArmsedit.action";
    document.armForm.submit();
}
function handleEditGroup(rowId){
    document.armForm.selectedArmIdentifier.value = rowId;
    document.armForm.action="trialArmseditGroup.action";
    document.armForm.submit();
}

function handleCreateArm(){
    document.armForm.action="trialArmscreate.action";
    document.armForm.submit();
}
function handleCreateGroup(){
    document.armForm.action="trialArmscreateGroup.action";
    document.armForm.submit();
}
</SCRIPT>

<body> 
<h1><s:if test="%{currentAction == 'listArm'}">
        <fmt:message key="arms.details.title" />
<c:set var="topic" scope="request" value="abstractarms"/></s:if>
    <s:elseif test="%{currentAction == 'listGroup'}">
        <fmt:message key="arms.obs.details.title" />
<c:set var="topic" scope="request" value="abstractcohort"/></s:elseif></h1>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /><pa:failureMessage/> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> 
    <s:form name="armForm">
        <s:token/>
        <pa:studyUniqueToken/>
        <s:hidden name="selectedArmIdentifier"/>
        <s:hidden name="currentAction"/>
    <h2>
    <s:if test="%{currentAction == 'listArm'}">
        <fmt:message key="arms.details.title" /></s:if>
    <s:elseif test="%{currentAction == 'listGroup'}">
        <fmt:message key="arms.obs.details.title" /></s:elseif>
    </h2>
    <c:if test="${fn:length(requestScope.armList) > 5}">
    <div class="actionstoprow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <s:if test="%{currentAction == 'listArm'}">
                <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreateArm();"><span class="btn_img"><span class="add">Add </span></span></a></li>
                <s:if test="%{armList != null && !armList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected arm(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected arm(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>                
            </s:if>
            <s:elseif test="%{currentAction == 'listGroup'}">                
                <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreateGroup();"><span class="btn_img"><span class="add">Add </span></span></a></li>
                <s:if test="%{armList != null && !armList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected group(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected group(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>
            </s:elseif>
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
    </c:if>
    <table class="form">
        <tr>
            <td colspan="2"><s:hidden name="cbValue" />
            <s:set name="armList" value="armList" scope="request"/>
            <display:table name="armList" id="row" class="data" sort="list" pagesize="200" requestURI="trialArms.action">
                <display:column escapeXml="true" property="name" sortable="true" titleKey="arms.name" headerClass="sortable"  />
                <s:if test="%{currentAction == 'listArm'}"><display:column escapeXml="true" property="type" sortable="true" titleKey="arms.type" headerClass="sortable"/></s:if>
                <display:column escapeXml="true" property="description" sortable="true" titleKey="arms.description" headerClass="sortable" />
                <display:column escapeXml="true" property="interventions" titleKey="arms.interventions"/>
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <display:column titleKey="arms.edit" headerClass="centered" class="action">
                        <s:if test="%{currentAction == 'listArm'}">
                            <s:a href="javascript:void(0)" onclick="handleEditArm(%{#attr.row.identifier})">
                                <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                            </s:a>
                        </s:if>
                        <s:elseif test="%{currentAction == 'listGroup'}">
                            <s:a href="javascript:void(0)" onclick="handleEditGroup(%{#attr.row.identifier})">
                                <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                            </s:a>
                        </s:elseif>
                    </display:column>
                    <display:column titleKey="arms.delete" headerClass="centered" class="action">
                        <s:checkbox name="objectsToDelete"  id="objectsToDelete_%{#attr.row.identifier}" fieldValue="%{#attr.row.identifier}" value="%{#attr.row.identifier in objectsToDelete}"/>
                        <label style="display: none;" for="objectsToDelete_${row.identifier}">Check this box to mark row for deletion.</label>
                    </display:column>
                </pa:scientificAbstractorDisplayWhenCheckedOut>
            </display:table>
            </td>
        </tr>
    </table>
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <s:if test="%{currentAction == 'listArm'}">
                <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreateArm();"><span class="btn_img"><span class="add">Add </span></span></a></li>
                <s:if test="%{armList != null && !armList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected arm(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected arm(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>                
            </s:if>
            <s:elseif test="%{currentAction == 'listGroup'}">
                <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreateGroup();"><span class="btn_img"><span class="add">Add </span></span></a></li>
                <s:if test="%{armList != null && !armList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected group(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected group(s) from the study. Cancel to abort.', 'trialArmsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>
            </s:elseif>
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>