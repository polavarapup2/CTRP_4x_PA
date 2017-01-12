<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="pagePrefix" value="nodecorate.lookupdiseases."/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"></link>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script language="javascript" type="text/javascript">
            function submitform(disid) {
                top.window.loadDiv(disid);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {     
                var url = '<%=request.getContextPath()%>/protected/popupDisdisplayList.action';
                var params = {
                    exactMatch: $("exactMatch") != null && $("exactMatch").checked == true,
                    includeSynonym: $("includeSynonym") != null && $("includeSynonym").checked == true,
                    searchName: $("searchName").value
                };
                var div = $('getDiseases');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function addDisease(diseaseid) {
                 return addOrRemoveDisease(diseaseid, 'addDisease');
            }
            
            function removeDisease(diseaseid) {
                return addOrRemoveDisease(diseaseid, 'remove');
            }
            
            function addOrRemoveDisease(diseaseid, diseaseAction) {
                var url = '<%=request.getContextPath()%>/protected/popupDis'+ diseaseAction +'.action';
                var params = {
                    diseaseId: diseaseid,     
                    exactMatch: $("exactMatch") != null && $("exactMatch").checked == true,
                    includeSynonym: $("includeSynonym") != null && $("includeSynonym").checked == true,
                    includeXml: $('includeXml').checked,
                    searchName: $("searchName").value
                };
                var div = $('getDiseases');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
        </script>
    </head> 
    <body>
        <div class="box">
            <s:form id="diseases" name="diseases" >
                <h2><fmt:message key="${pagePrefix}header" /></h2>
                <table  class="form">  
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
                            <label for="includeSynonym"><fmt:message key="disease.includeSynonym"/></label>                        
                        </td>
                        <td>
                            <s:checkbox name="includeSynonym" id="includeSynonym" />
                        </td>                
                    </tr>
                    <tr> 
                        <td scope="row" class="label">
                            <label for="exactMatch"><fmt:message key="disease.exactMatch"/></label>                        
                        </td>
                        <td>
                            <s:checkbox name="exactMatch" id="exactMatch" />
                        </td>                
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <div><label for="includeXml"><fmt:message key="${pagePrefix}includeXml"/></label>  
                                <s:checkbox id="includeXml" name="includeXml" /></div>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search"><fmt:message key="${pagePrefix}button.search" /></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin(true);"><span class="btn_img"><span class="close"><fmt:message key="${pagePrefix}button.close" /></span></span></s:a>  
                            </li>
                        </ul>
                    </del>
                </div>
                <div class="line"></div>
                <div id="getDiseases" align="center">   
                    <jsp:include page="/WEB-INF/jsp/nodecorate/lookupdiseasesdisplayList.jsp"/>
                </div>
            </s:form>
        </div>
    </body>
</html>