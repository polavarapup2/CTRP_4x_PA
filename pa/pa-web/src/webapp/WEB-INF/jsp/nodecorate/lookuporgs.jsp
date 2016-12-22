<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript">
            function setSearchFormVisible() {
                document.getElementById("searchOrgJsp").style.display="";
                document.getElementById("createOrgJsp").style.display="none";
            }

            function setCreateFormVisible() {
                document.getElementById("searchOrgJsp").style.display="none";
                document.getElementById("createOrgJsp").style.display="";
            }
            
            function submitform(orgid, orgname) {
                top.window.setorgid(orgid, orgname);
                top.window.loadDiv(orgid);
                window.top.hidePopWin(true); 
            }
            
            function callParentSubmit(orgid) {    
                top.window.setorgid(orgid);
                top.window.loadDiv(orgid);
                window.top.hidePopWin(true); 
            }

            function createOrg() {
                var url = '/pa/protected/popupOrgcreateOrganization.action';
                var country = $("orgCountry").value;
                var params = {                	                 		
               		'struts.token.name': document.getElementsByName("struts.token.name")[0].value,
               		'struts.token.popup': document.getElementsByName("struts.token.popup")[0].value,
                    cityName: $("orgCity").value,
                    countryName: country,
                    email: $("orgEmail").value,
                    fax: $("orgFax").value,
                    orgName: $("orgName").value,
                    orgStAddress: $("orgAddress").value,
                    telephone: $("orgPhone").value,
                    stateName: ((country == 'USA') ? $("orgStateSelect").value : $("orgStateText").value),
                    tty: $("orgTty").value,
                    url: $("orgUrl").value,
                    zipCode: $("orgZip").value
                };
            
                var div = $('getOrgs'); 
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function loadDiv() {
            	resetUnusedSearchCriteria();
                var url = '/pa/protected/popupOrgdisplayOrgList.action';
                var params = {
                    orgName: $('orgNameSearch').value,
                    familyName: $('orgFamilyNameSearch').value,
                    countryName: $('orgCountrySearch').value,
                    cityName: $('orgCitySearch').value,
                    zipCode: $('orgZipSearch').value,
                    stateName: $('orgStateSearch').value,
                    ctepId: $('orgCtepIdSearch').value,
                    poId: $('orgPoIdSearch').value
                };
                var div = $('getOrgs');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function resetUnusedSearchCriteria() {
                var orgCtepid = $('orgCtepIdSearch').value;
                var orgPoid = $('orgPoIdSearch').value;
                if(orgPoid.length > 0){
                    $("orgNameSearch").value = '';
                    $("orgFamilyNameSearch").value = '';
                    $("orgCitySearch").value = '';
                    $("orgStateSearch").value = '';
                    $("orgCountrySearch").value = '';
                    $("orgZipSearch").value = '';
                    $('orgCtepIdSearch').value = '';
                }
                if(orgPoid.length == 0 && orgCtepid.length > 0){
                    $("orgNameSearch").value = '';
                    $("orgFamilyNameSearch").value = '';
                    $("orgCitySearch").value = '';
                    $("orgStateSearch").value = '';
                    $("orgCountrySearch").value = '';
                    $("orgZipSearch").value = '';
                }
            }
            
            function formReset() {
                document.forms[0].reset();
                $("orgNameSearch").value = '';
                $("orgFamilyNameSearch").value = '';
                $("orgCitySearch").value = '';
                $("orgStateSearch").value = '';
                $("orgCountrySearch").value = 'USA';
                $("orgZipSearch").value = '';
                $('orgCtepIdSearch').value = '';
                $('orgPoIdSearch').value = '';
                
            }
            
        </script>
    
    </head> 
	<p align="center" class="info">
	    Type a string of characters in any of the text fields in the upper frame OR
	    enter PO ID or CTEP Identifier in the lower frame.
	</p>    
    <body>
        <div class="box">
        <s:form id="poOrganizations" name="poOrganizations" >
            <s:token name="struts.token.popup"/>
            <div id="searchOrgJsp">
            <h2><fmt:message key="popUpOrg.header"/></h2>
            
              <table  class="form">  
                <tr>
                     <td scope="row" class="label">
                        <label for="orgNameSearch"><fmt:message key="popUpOrg.name"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgNameSearch" name="orgName" maxlength="200" size="100" cssStyle="width:200px" />
                     </td>
                      <td scope="row" class="label">
                        <label for="orgCitySearch"><fmt:message key="popUpOrg.city"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgCitySearch" name="cityName" maxlength="200" size="100" cssStyle="width:200px" />
                    </td>
                </tr>
                <tr>  
                     <td scope="row" class="label">
                        <label for="orgFamilyNameSearch"><fmt:message key="popUpOrg.familyName"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgFamilyNameSearch" name="familyName" maxlength="200" size="100" cssStyle="width:200px" />
                     </td>
                     <td scope="row" class="label">
                        <label for="orgCountrySearch"><fmt:message key="popUpOrg.country"/></label>
                    </td>
                    <td>
                        <s:select id="orgCountrySearch" name="countryName" list="countryList"  
                                  listKey="alpha3" listValue="name" headerKey="" headerValue="" cssStyle="width:206px" />
                    </td>
                </tr>
                <tr>
                     <td scope="row" class="label">
                        <label for="orgStateSearch"><fmt:message key="popUpOrg.state"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgStateSearch" name="stateName" maxlength="75" size="20"/><br><font size="1"><span class="info"><fmt:message key="popUpOrg.stateInfo"/></span></font>
                    </td>
                      <td scope="row" class="label">
                        <label for="orgZipSearch"><fmt:message key="popUpOrg.zip"/></label>
                    </td>
                     <td>
                         <s:textfield id="orgZipSearch" name="zipCode" maxlength="75" size="20"/>
                    </td>    
                </tr>
                <tr><td colspan="6"> <hr></td> </tr>
                <tr>
                    <td scope="row" class="label"><label for="orgPoIdSearch"><fmt:message key="organization.search.poID"/></label></td>
                    <td><s:textfield id="orgPoIdSearch" name="poId" maxlength="10" cssStyle="width:294px"  /></td>                
                    <td scope="row" class="label"><label for="orgCtepIdSearch"><fmt:message key="popUpOrg.ctepId"/></label></td>
                    <td><s:textfield id="orgCtepIdSearch" name="ctepId" maxlength="75" cssStyle="width:294px"  /></td>    
                </tr>
              </table>
              <div class="actionsrow">
                 <del class="btnwrapper">
                     <ul class="btnrow">
                         <li>
                           <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search"><fmt:message key="popUpOrg.button.search"/></span></span></s:a>
                           <c:if test="${sessionScope.isAbstractor || sessionScope.isSuAbstractor}">
                             <s:a href="javascript:void(0)" cssClass="btn" onclick="setCreateFormVisible();" id="add_organization_btn"><span class="btn_img"><span class="add">Add Org</span></span></s:a>
                           </c:if>
                           <s:a href="javascript:void(0)" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel"><fmt:message key="popUpOrg.button.reset"/></span></span></s:a>
                           <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                         </li>
                     </ul>   
                 </del>
              </div>
            </div>
            <div id="createOrgJsp" style="display:none">
                <jsp:include page="/WEB-INF/jsp/nodecorate/createOrg.jsp"/>
            </div>
            <div id="getOrgs" align="center">
                   <jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>
               </div>
        </s:form>
        </div>
    </body>
</html>