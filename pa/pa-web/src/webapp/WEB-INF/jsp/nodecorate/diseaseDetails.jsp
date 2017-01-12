<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
    function callParentHandleView(disid){
        top.window.handleView(disid);
    }
    function handleClose(){
        window.top.hidePopWin(true);
    }
</SCRIPT>

</head>
<body>
<div class="box">
<s:form>
    <h2>Disease/Condition Details</h2>
    <table class="form">
        <tr>
            <td class="label"><fmt:message key="disease.preferredName"/>:</td>
            <td class="value"><s:textfield name="disease.preferredName" readonly="true" cssStyle="width:200px"/></td>
            <td class="label"><fmt:message key="disease.code"/>:</td>
            <td class="value"><s:textfield name="disease.code" readonly="true" cssStyle="width:200px"/></td>
        </tr>
        <tr>
            <td class="label"><fmt:message key="disease.conceptId"/>:</td>
            <td class="value"><s:textfield name="disease.conceptId" readonly="true" cssStyle="width:200px"/></td>
            <td class="label"><fmt:message key="disease.menuDisplayName"/>:</td>
            <td class="value"><s:textfield name="disease.menuDisplayName" readonly="true" cssStyle="width:200px"/></td>
        </tr>
        <tr>
            <td class="label"><fmt:message key="disease.alternames"/>:</td>
            <td class="value" colspan="3"><s:textfield name="disease.alternames" readonly="true" cssStyle="width:600px"/></td>
        </tr>
    </table>

    <div class="actionsrow"><del class="btnwrapper"><ul class="btnrow">
        <li>
            <s:a href="javascript:void(0)" cssClass="btn" onclick="handleClose()"><span class="btn_img"><span class="logout">Close</span></span></s:a>
        </li>
    </ul></del></div>

    <table align="center" class="form">
    <tr>
      <td width="45%">
          <h2 class="small"><fmt:message key="disease.parents.title"/></h2>
          <s:set name="parentList" value="parentList" scope="request"/>
          <display:table name="parentList" id="row" class="data">
            <display:column escapeXml="true" property="preferredName" titleKey="disease.preferredName" headerClass="small"/>
            <display:column titleKey="disease.view" headerClass="centeredsmall" class="action" style="width:30%">
                <s:a href="javascript:void(0)" onclick="callParentHandleView(%{#attr.row.diseaseIdentifier})">
                    <img src="<%=request.getContextPath()%>/images/ico_search.gif" alt="View" width="16" height="16" />
                </s:a>
            </display:column>
          </display:table>
      </td>
      <td/>
      <td width="45%">
          <h2 class="small"><fmt:message key="disease.children.title"/></h2>
          <s:set name="childList" value="childList" scope="request"/>
          <display:table name="childList" id="row" class="data">
            <display:column escapeXml="true" property="preferredName" titleKey="disease.preferredName" headerClass="small"/>
            <display:column titleKey="disease.view" headerClass="centeredsmall" class="action" style="width:30%">
                <s:a href="javascript:void(0)" onclick="callParentHandleView(%{#attr.row.diseaseIdentifier})">
                    <img src="<%=request.getContextPath()%>/images/ico_search.gif" alt="View" width="16" height="16" />
                </s:a>
            </display:column>
          </display:table>
      </td>
    </tr>
    </table>
</s:form>
</div>
</body>
</html>
