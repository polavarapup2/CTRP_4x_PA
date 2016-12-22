<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="pagePrefix" value="nodecorate.lookupcontactpersons."/>
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
             
            function callCreateContactPerson(persid) {
                top.window.setpersid(persid);
                top.window.loadContactPersDiv(persid);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {
                var url = '/pa/protected/popupdisplaycontactPersonsList.action';
                var params = {
                    cityName: $("personCity").value,
                    countryName: $("personCountry").value,
                    firstName: $("personFirstName").value,
                    lastName: $("personLastName").value,
                    stateName: $("personState").value,
                    zipCode: $("personZip").value
                };
                var div = $('getPersons');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function formReset(){
                document.forms[0].reset();
                $("personFirstName").value = '';
                $("personLastName").value = '';
                $("personCity").value = '';
                $("personCountry").value = 'USA';
                $("personState").value = '';
                $("personZip").value = '';
            }
        </script> 
    </head> 
    <body>
        <div class="box">
            <s:form id="poOrganizations" name="poOrganizations" >
                <h2><fmt:message key="${pagePrefix}header" /></h2>
                <table  class="form">  
                       <tr>
                           <td scope="row" class="label"><label for="firstname"><fmt:message key="${pagePrefix}firstName" /></label></td>
                         <td><s:textfield id="personFirstName" name="perSearchCriteria.firstName"  maxlength="200" size="100" cssStyle="width:200px" /></td>
                        <td scope="row" class="label"><label for="lastname"><fmt:message key="${pagePrefix}lastName" /></label></td>
                         <td><s:textfield id="personLastName" name="perSearchCriteria.lastName"  maxlength="200" size="100" cssStyle="width:200px" /></td>
                    </tr>
                       <tr>
                           <td scope="row" class="label"><label for="city"><fmt:message key="${pagePrefix}city" /></label></td>
                         <td><s:textfield id="personCity" name="perSearchCriteria.city"  maxlength="200" size="100" cssStyle="width:200px" /></td>
                        <td scope="row" class="label"><label for="state"><fmt:message key="${pagePrefix}state" /></label></td>
                         <td><s:textfield id="personState" name="perSearchCriteria.state"  maxlength="200" size="100" cssStyle="width:200px" /><br><font size="1"><span class="info"><fmt:message key="${pagePrefix}stateInfo" /></span></font></td>
                    </tr>        
                       <tr>
                           <td scope="row" class="label"><label for="country"><fmt:message key="${pagePrefix}country" /></label></td>
                        <td>
                            <s:select id="personCountry" name="perSearchCriteria.country" list="countryList" listKey="alpha3" 
                                      listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
                        </td>    
                        <td scope="row" class="label"><label for="zip"><fmt:message key="${pagePrefix}zip" /></label></td>
                         <td><s:textfield id="personZip" name="perSearchCriteria.zip"  maxlength="200" size="100" cssStyle="width:200px" /></td>
                    </tr>    
                </table>
                <div class="actionsrow">
                     <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>          
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search"><fmt:message key="${pagePrefix}button.search" /></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel"><fmt:message key="${pagePrefix}button.reset" /></span></span></s:a>  
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
                   <jsp:include page="/WEB-INF/jsp/nodecorate/displaycontactPersonsList.jsp"/>
               </div>
        </div>
    </body>
</html>