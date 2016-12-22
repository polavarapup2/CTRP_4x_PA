<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script language="javascript" type="text/javascript">
            function submitform(orgid) {
                top.window.loadDiv(orgid);
                window.top.hidePopWin(true); 
            }
            
            function callParentSubmit(orgid) {    
                top.window.loadDiv(orgid);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {
                var url = '/pa/protected/ajaxorgdisplayOrgList.action';
                var params = $('poOrganizations').serialize(true);
                var div = $('getOrgs');          
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function formReset() {
                var form = $('poOrganizations');
                form.reset();
                form.orgName.value = '';
                form.nciOrgName.value = '';
                form.cityName.value='';
                form.countryName.value = 'USA';
                form.zipCode.value = '';
            }
        </script> 
    </head> 
    <body>
        <div class="box">
            <s:form id="poOrganizations" name="poOrganizations" >
                <h2><fmt:message key="popUpOrg.header"/></h2>
                <table  class="form">
                       <tr>     
                         <td scope="row" class="label">
                            <label for="name"><fmt:message key="popUpOrg.name"/></label>
                        </td>
                         <td>
                             <s:textfield id="name" name="orgName"  maxlength="200" size="100"  cssStyle="width:200px" />
                         </td>
                         <td scope="row" class="label">
                            <label for="nciorgname"><fmt:message key="popUpOrg.nciorgnumber"/></label>
                        </td>
                         <td>
                             <s:textfield name="nciOrgName"  maxlength="200" size="100"  cssStyle="width:200px" />
                         </td>
                         <td scope="row" class="label">
                            <label for="country"><fmt:message key="popUpOrg.country"/></label>
                        </td>
                        <td>
                             <s:select name="countryName" list="countryRegDTO" listKey="id" listValue="name"  />
                        </td>
                    </tr>
                    <tr>  
                          <td scope="row" class="label">
                            <label for="city"><fmt:message key="popUpOrg.city"/></label>
                        </td>
                         <td>
                             <s:textfield name="cityName"  maxlength="200" size="100"  cssStyle="width:200px" />
                        </td>
                          <td scope="row" class="label">
                            <label for="zip"><fmt:message key="popUpOrg.zip"/></label>
                        </td>
                         <td>
                             <input type="text" name="zipCode" maxlength="75" size="20">
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                     <del class="btnwrapper">
                         <ul class="btnrow">
                             <li>
                                 <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search"><fmt:message key="popUpOrg.button.search"/></span></span></s:a>
                                 <s:a href="javascript:void(0)" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel"><fmt:message key="popUpOrg.button.reset"/></span></span></s:a>
                                 <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>  
                             </li>
                         </ul>
                     </del>
                 </div>
            </s:form>
        </div>
        <div class="line"></div>
        <div class="box" align="center">
            <div id="getOrgs">    
                   <jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>
               </div>
        </div>
    </body>
</html>