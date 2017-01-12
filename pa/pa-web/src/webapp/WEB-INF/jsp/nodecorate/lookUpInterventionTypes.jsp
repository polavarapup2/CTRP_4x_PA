<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="pagePrefix" value="nodecorate.lookUpInterventionTypes."/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script language="javascript" type="text/javascript">
        
            function submitLookUp(id,divName,className) {
                top.window.loadDetails(id,divName,className);
                window.top.hidePopWin(true); 
            }
            
            function callParentSubmit(id,divName,className) {   
                top.window.loadDetails(id,divName,className);
                window.top.hidePopWin(true); 
            }
            
            function loadLookUpDiv() {
                var url = '/pa/protected/popupTypeInterventiondisplayLookUpList.action';
                var params = {
                    className: $("className").value,
                    code: $("codeSearch").value,
                    description: $("descriptionSearch").value,
                    displayName: $("displayNameSearch").value,
                    divName: $("divName").value,
                    publicId: $("publicIdSearch").value
                };
                var div = $('getLists');         
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function formMethodCodeReset(){
                document.forms[0].reset();
                $("codeSearch").value = '';
                $("publicIdSearch").value = '';
                $("descriptionSearch").value = '';
                $("displayNameSearch").value = '';
            }
            
        </script>
    </head> 
    <body>
        <div class="box">
            <s:form id="lookUp" name="lookUp" >
                <h2><fmt:message key="lookup.unitOfMeasurement.title" /></h2>
                <s:label name="lookUpErrorMessage"/>
                <s:hidden id="className" name="lookupSearchCriteria.type"/>  
                <s:hidden id="divName" name="lookupSearchCriteria.divName"/>
                <table class="form">
                    <tr>    
                        <td scope="row" class="label">
                            <label for="code"><fmt:message key="lookUp.code"/></label>
                        </td>
                        <td>
                            <s:textfield id="codeSearch" name="lookupSearchCriteria.code" maxlength="200" size="100" cssStyle="width:200px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="publicId"><fmt:message key="lookUp.publicId"/></label>
                        </td>
                        <td>
                            <s:textfield id="publicIdSearch" name="lookupSearchCriteria.publicId" maxlength="200" size="100" cssStyle="width:200px" />
                        </td>
                    </tr>
                    <tr> 
                        <td scope="row" class="label">
                            <label for="displayName"><fmt:message key="lookUp.displayName"/></label>
                        </td>
                        <td>            
                            <s:textfield id="displayNameSearch" name="lookupSearchCriteria.displayName" maxlength="200" size="100" cssStyle="width:200px" />
                        </td> 
                        <td scope="row" class="label">
                            <label for="description"><fmt:message key="lookUp.description"/></label>
                        </td>
                        <td>            
                            <s:textfield id="descriptionSearch" name="lookupSearchCriteria.description" maxlength="200" size="100" cssStyle="width:200px" />
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>            
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="loadLookUpDiv();"><span class="btn_img"><span class="search"><fmt:message key="${pagePrefix}button.search" /></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="lookUpFormReset();"><span class="btn_img"><span class="cancel"><fmt:message key="${pagePrefix}button.reset" /></span></span></s:a> 
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a> 
                            </li>
                        </ul>   
                    </del>
                </div>
                <div id="getLists" align="center">   
                    <jsp:include page="/WEB-INF/jsp/nodecorate/displayLookUpList.jsp"/>
                </div>
            </s:form>
        </div>
    </body>
</html>