<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="pagePrefix" value="nodecorate.lookupinterventions."/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script language="javascript" type="text/javascript">
            function submitform(intid) {
                top.window.loadDiv(intid);
                window.top.hidePopWin(true); 
            }
            
            function callParentSubmit(intid) {   
                top.window.loadDiv(intid);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {        
                var url = '/pa/protected/popupIntdisplayList.action';
                var params = {
                    exactMatch: $("exactMatch") != null && $("exactMatch").checked == true,
                    includeSynonym: $("includeSynonym") != null && $("includeSynonym").checked == true,   
                    searchName: $("searchName").value
                };
                var div = document.getElementById('getInterventions');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function cancel() {
                window.top.hidePopWin(true);
            } 
        </script>
    </head> 
    <body>
        <div class="box">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <s:form id="interventions" name="interventions" >
                <h2><fmt:message key="${pagePrefix}header" /></h2>
                <s:label name="interventionErrorMessage"/>
                <s:hidden id="interventionType" name="interventionType"/>
                <table class="form">  
                    <tr>    
                        <td scope="row" class="label">
                            <label for="searchName"><fmt:message key="${pagePrefix}name" /></label>
                        </td>
                        <td>
                            <s:textfield id="searchName" name="searchName" maxlength="60" size="60" cssStyle="width:200px" />
                        </td> 
                    </tr>
                    <tr> 
                        <td scope="row" class="label">
                            <label for="includeSynonym"><fmt:message key="interventions.includeSynonym"/></label>                        
                        </td>
                        <td>
                            <s:checkbox name="includeSynonym" id="includeSynonym" />
                        </td>                
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="exactMatch"> <fmt:message key="interventions.exactMatch"/></label>                        
                        </td>
                        <td>
                            <s:checkbox name="exactMatch" id="exactMatch"/>
                        </td>                
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                               <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search"><fmt:message key="${pagePrefix}button.search" /></span></span></s:a>
                               <s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();"><span class="btn_img"><span class="cancel"><fmt:message key="${pagePrefix}button.cancel" /></span></span></s:a>  
                            </li>
                        </ul>
                    </del>
                </div>
                <div class="line"></div>
                <div id="getInterventions" align="center">   
                    <jsp:include page="/WEB-INF/jsp/nodecorate/lookupinterventionsdisplayList.jsp"/>
                </div>
            </s:form>
        </div>
    </body>
</html>