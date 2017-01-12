<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script language="javascript" type="text/javascript">
            function submitform(persid) {
                top.window.setpersid(persid);
                top.window.loadPersDiv(persid, func);
                window.top.hidePopWin(true);
            }
        
            function callCreatePerson(persid, rolecode, fname, lname, email, phone, country) {
                top.window.setpersid(persid, (lname+', '+fname), email, phone);
                top.window.loadPersDiv(persid, rolecode, 'add');
                //check if function exists in top window to accept value of country
                if(typeof top.window['setPersonCountry'] =='function') {
                	top.window.setPersonCountry(country);
                } 
                
                window.top.hidePopWin(true);
            }
        
            function loadDiv() {
            	
            	clearUnusedSearchCriteria();
            	
                var url = '/pa/protected/popupdisplayPersonsList.action';
                var params = {
                    firstName: $("personFirstName").value,
                    lastName: $("personLastName").value,
                    countryName: $("personCountry").value,
                    cityName: $("personCity").value,
                    zipCode: $("personZip").value,
                    stateName: $("personState").value,
                    poId: $("poID").value,
                    ctepId: $("ctepID").value
                };
                var div = $('getPersons');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function clearUnusedSearchCriteria(){
            	var poid = $("poID").value;
            	var ctepid = $("ctepID").value;
            	if(poid.length > 0){
            		$("ctepID").value = '';
            		$("personFirstName").value = '';
            		$("personLastName").value = '';
            		$("personCity").value = '';
                    $("personState").value = '';
                    $("personZip").value = '';
            	}
            	if(poid.length == 0 && ctepid.length > 0){
                    $("personFirstName").value = '';
                    $("personLastName").value = '';
                    $("personCity").value = '';
                    $("personState").value = '';
                    $("personZip").value = '';
            	}
            }
        
            function formReset(){
                $('poOrganizations').reset();
                $("personFirstName").value = '';
                $("personLastName").value = '';
                $("personCity").value = '';
                $("personCountry").value = 'USA';
                $("personState").value = '';
                $("personZip").value = '';
                $("poID").value = '';
                $("ctepID").value = '';
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
                <h2><fmt:message key="popUpPerson.header" /></h2>
                <table  class="form">
                     <tr>
                         <td scope="row" class="label"><label for="firstname"><fmt:message key="popUpPerson.firstname" /></label></td>
                         <td><s:textfield id="personFirstName" name="perSearchCriteria.firstName" maxlength="200" size="100" cssStyle="width:200px" /></td>
                         <td scope="row" class="label"><label for="lastname"><fmt:message key="popUpPerson.lastname" /></label></td>
                         <td><s:textfield id="personLastName" name="perSearchCriteria.lastName" maxlength="200" size="100" cssStyle="width:200px" /></td>
                    </tr>
                    <tr>
                        <td scope="row" class="label"><label for="city"><fmt:message key="popUpPerson.city" /></label></td>
                        <td><s:textfield id="personCity" name="perSearchCriteria.city"  maxlength="200" size="100" cssStyle="width:200px" /></td>
                        <td scope="row" class="label"><label for="state"><fmt:message key="popUpPerson.state" /></label></td>
                        <td><s:textfield id="personState" name="perSearchCriteria.state"  maxlength="200" size="100" cssStyle="width:200px" /><br><font size="1"><span class="info">please enter two letter identifier for US states for ex: 'MD' for Maryland</span></font></td>
                    </tr>
                    <tr>
                        <td scope="row" class="label"><label for="country"><fmt:message key="popUpPerson.country" /></label></td>
                        <td>
                            <s:select id="personCountry" name="perSearchCriteria.country" list="countryList"
                                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label"><label for="zip"><fmt:message key="popUpPerson.zip" /></label></td>
                        <td><s:textfield id="personZip" name="perSearchCriteria.zip"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
                    </tr>
                    <tr><td colspan="6"> <hr></td> </tr>
                    <tr>
                        <td  scope="row" class="label"><label for="poID"><fmt:message key="person.search.poID"/></label></td>
                        <td><s:textfield id="poID" name="criteria.id" maxlength="10" cssStyle="width:294px"  /></td>
                        <td  scope="row" class="label"><label for="ctepID"><fmt:message key="person.search.ctepID"/></label></td>
                        <td><s:textfield id="ctepID" name="criteria.ctepId" maxlength="75" cssStyle="width:294px"  /></td>
                    </tr>
                    
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search"><fmt:message key="popUpPerson.button.search" /></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel"><fmt:message key="popUpPerson.button.reset" /></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
        <div class="line"></div>
        <div class="box" align="center">
            <div id="getPersons">
                 <jsp:include page="/WEB-INF/jsp/nodecorate/displayPersonsList.jsp"/>
           </div>
        </div>
    </body>
</html>
