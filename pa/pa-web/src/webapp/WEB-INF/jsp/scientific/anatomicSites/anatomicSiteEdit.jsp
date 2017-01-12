<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="anatomicSite.details.title" /></title>
<s:head />

<script type="text/javascript">
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();
    }
    function anatomicSiteAdd(){
        if ($('anatomicSite_code').value == '') {
            alert("An Anatomic Site value must be selected.");
        } else {
            document.anatomicSiteForm.action="anatomicSiteadd.action";
            document.anatomicSiteForm.submit();
        }
    }
</script>
</head>
<body>
<h1><fmt:message key="anatomicSite.details.title" /></h1>
<c:set var="topic" scope="request" value="abstractanatomicsite"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage />
    <s:url id="cancelUrl" namespace="/protected" action="anatomicSite"/>
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <s:form name="anatomicSiteForm">
        <s:token/>
        <pa:studyUniqueToken/>
    <h2>
        <fmt:message key="anatomicSite.add.details.title"/>
    </h2>
   
    <table class="form">
        <tr>
            <td colspan="2">
             <label for="anatomicSite_code" style="display:none">site</label>
            <s:set name="anatomicSiteList" value="anatomicSiteList" scope="request"/>

            <s:select name="anatomicSite.code" id="anatomicSite_code" list="anatomicSiteList" listKey="code"
                        listValue="code" headerKey="" headerValue="--Select Item--" />
        </td>
        </tr>
        <tr>
            <td>
            <div class="actionsrow"><del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="anatomicSiteAdd();">
                        <span class="btn_img"> <span class="save">Save</span></span>
                    </s:a>
                    <s:a href="%{cancelUrl}" cssClass="btn">
                        <span class="btn_img"><span class="cancel">Cancel</span></span>
                    </s:a>
                </li>
            </ul>
            </del></div>
            </td>
        </tr>
    </table>
</s:form>
 </div>
</body>
</html>
