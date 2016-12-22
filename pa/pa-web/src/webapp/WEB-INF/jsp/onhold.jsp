<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="onhold.title" /></title>
        <s:head />
        <script type="text/javascript">
            jQuery(function() {
                jQuery("#addButton").bind("click", function(ev) {
                    var form = document.listForm;
                    form.action = "onholdcreate.action";
                    form.submit();
                });
                jQuery("#backButton").bind("click", function(ev) {
                    var form = document.listForm;
                    form.action = "trialValidationquery.action";
                    form.submit();
                });
            });
        
            function handleEdit(rowId) {
                var form = document.listForm;
                form.selectedRowIdentifier.value = rowId;
                form.action="onholdedit.action";
                form.submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="onhold.title"/></h1>
        <c:set var="topic" scope="request" value="trialonhold"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <s:form name="listForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                <input type="hidden" name="studyProtocolId" value="${sessionScope.trialSummary.studyProtocolId}"/>
                <h2>
                    <fmt:message key="onhold.title"/>
                </h2>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="onholdList" value="onholdList" scope="request"/>
                            <display:table name="onholdList" id="row" class="data" sort="list" pagesize="10" requestURI="onhold.action">
                                <display:column escapeXml="true" property="reasonCode" sortable="false" titleKey="onhold.reason.code"/>
                                <display:column escapeXml="true" property="reasonCategory" sortable="false" titleKey="onhold.reason.category"/>
                                <display:column escapeXml="true" property="reasonText" sortable="false" titleKey="onhold.reason.text"/>
                                <display:column escapeXml="true" property="dateLow" sortable="false" titleKey="onhold.date.low"/>
                                <display:column escapeXml="true" property="dateHigh" sortable="false" titleKey="onhold.date.high"/>
                                <pa:displayWhenCheckedOut>
                                    <display:column titleKey="onhold.edit" headerClass="centered" class="action">
                                        <c:if test="${(row.dateHigh==null)}">
                                            <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.identifier})">
                                                <img src="${imagePath}/ico_edit.gif" alt="Edit" width="16" height="16" />
                                            </s:a>
                                        </c:if>
                                    </display:column>
                                </pa:displayWhenCheckedOut>
                             </display:table>
                        </td>
                    </tr>
                </table>
                <pa:buttonBar>
                    <pa:displayWhenCheckedOut>
                        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'On-Hold' && sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submission Terminated'}">
                            <pa:button id="addButton" imgClass="add" labelKey="onhold.button.add"/>
                        </c:if>
                    </pa:displayWhenCheckedOut>
                    <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
                        <pa:button id="backButton" imgClass="back" labelKey="onhold.button.back"/>
                    </c:if>
                </pa:buttonBar>
            </s:form>
        </div>
    </body>
</html>