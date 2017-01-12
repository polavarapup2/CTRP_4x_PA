<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<s:set name="action" value="action" />
<title><fmt:message key="manageTerms.lookupDisease.page.title" /></title>
<s:head />
<script type="text/javascript">
function cancel() {
    submitXsrfForm('${pageContext.request.contextPath}/protected/disease.action')
}

</script>
</head>
<body>
	<c:set var="topic" scope="request" value="importdisease" />
	<div id="box">
		<h1>
			<fmt:message key="manageTerms.lookupDisease.page.title" />
		</h1>
		<pa:failureMessage />

		<s:form name="lookupDiseaseForm" method="post"
			action="manageTermssearchDisease.action">
			<s:hidden name="importTerm" />
			<s:hidden  name="pageDiscriminator" ></s:hidden>
			<table class="form">
				<tr>
					<td colspan="3"><span class="info"><fmt:message key="manageTerms.lookup.helpmessage" /> </span></td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="ntTermIdentifier">
							NCIt Identifier</label> <span class="required">*</span></td>
					<td width="150px"><s:textfield id="ntTermIdentifier"
							name="disease.ntTermIdentifier" maxlength="10" size="10"
							cssStyle="width:150px" /></td>
					<td><s:a href="javascript:void(0)" cssClass="btn"
							onclick="document.forms[0].submit()">
							<span class="btn_img"><span class="search">Look Up</span></span>
						</s:a></td>
				</tr>
			</table>
			<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:if test="%{pageDiscriminator.equals('disease')}">
                         <s:a onclick="javascript:cancel();" href="javascript:void(0)" cssClass="btn">
                           <span class="btn_img"><span class="cancel">Cancel</span></span>
                         </s:a>
                        </s:if>
                        <s:else>
                        <s:a href="manageTerms.action" cssClass="btn">
                            <span class="btn_img"><span class="cancel">Cancel</span></span>
                        </s:a>
                        </s:else> 
                   </li>
                </ul>
            </del>
        </div>
		</s:form>
	</div>
</body>
</html>