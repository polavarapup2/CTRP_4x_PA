<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<c:set var="pagePrefix" value="disclaimer."/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="disclaimer.page.title"/></title>   
        <s:head/>
        <script type="text/javascript" language="javascript">
            function submitForm(btnSelected) {
                var form = document.forms[0];
                if (btnSelected != 'accept') {
                   form.action="logout.action";
                } 
                form.submit();
            }
        </script>
    </head>
    <body>
    <c:set var="topic" scope="request" value="login"/>
        <s:form name="disclaimer" method="POST" action="disClaimerActionaccept.action">
        <s:token/>
            <!-- main content begins-->
            <br/>
            <br/>
            <div>
                <table width="65%" align="center">
                    <tr>
                        <td align="left">
                            <br/>
                            <center><b><fmt:message key="${pagePrefix}page.ctrp"/></b></center><br/> 
                            <hr/><br/>
                            <s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('Disclaimer','PA')"/><br/>
                            <br/>
                            <hr/>
                        </td>
                    </tr>
                </table>
            </div>
            <s:hidden name="actionName" id="actionName"/>
            <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">            
                        <li>
                            <a href="javascript:void(0)" class="btn" onclick="submitForm('accept');" id="acceptDisclaimer">
                                <span class="btn_img"><span class="confirm"><fmt:message key="${pagePrefix}button.accept"/></span></span>
                            </a>
                        </li> 
                        <li>
                            <a href="javascript:void(0)" class="btn" onclick="submitForm('decline');" id="rejectDisclaimer">
                                <span class="btn_img"><span class="cancel"><fmt:message key="${pagePrefix}button.reject"/></span></span>
                            </a>
                        </li>
                    </ul>    
                </del>
            </div>
        </s:form>
    </body>
</html>