<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="userAccountDetails.title"/></title>
        <s:head/>
        <c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl" />
        <s:url id="cancelUrl" namespace="/protected" action="userAccountDetailsexecute"/>
        <script type="text/javascript" language="javascript">
        
	        function handleAction() {
	            document.forms[0].action="userAccountDetailssave.action";
	            document.forms[0].submit();
	        }
            function lookup() {
                    showPopup('${lookupUrl}', null, 'Organization');
            }
            function setorgid(orgid) {}
            function loadDiv(orgid) {
                document.forms[0].action='userAccountDetailsupdateOrgName.action?orgId='+orgid;
                document.forms[0].submit();
                return false;
            }
        
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="userAccountDetails.title"/></h1>
        <c:set var="topic" scope="request" value="accountdetails"/>
        <div class="box" id="filters">
            <div class="fixedTopHeader">
            <s:form id="userAccountDetailsForm">
                <s:token/>        
                <pa:failureMessage/>
                <pa:sucessMessage/>                
                <table class="form">
                        <tr>
                            <td  scope="row" class="label" align="left">
                                <fmt:message key="userAccountDetails.loginName"/>
                            </td>
                            <td class="value">                                
                                <i><c:out value="${userName}"/></i>
                            </td>
                        </tr>
                        <tr>
                            <td  scope="row" class="label" align="left">
                                <label for="firstName"> <fmt:message key="userAccountDetails.firstName"/></label>
                               
                            </td>
                            <td class="value">                                
                                <s:textfield id="firstName" name="firstName" maxlength="200" size="100" cssStyle="width:200px" />                                          
                            </td>
                        </tr>
                        <tr>
                            <td  scope="row" class="label" align="left">
                                <label for="lastName"> <fmt:message key="userAccountDetails.lastName"/></label>
                                
                            </td>
                            <td class="value">                                
                                <s:textfield id="lastName" name="lastName" maxlength="200" size="100" cssStyle="width:200px"  />                                          
                            </td>
                        </tr>                        
                        <tr>
                            <td  scope="row" class="label" align="left">
                            <label for="organization"> <fmt:message key="userAccountDetails.organization"/></label>
                                
                            </td>
                            <td class="value">                                
                                <s:textfield id="organization" name="organization" size="100" cssStyle="float:left; width:200px" readonly="true" cssClass="readonly"  />                                          
                                <a href="javascript:void(0)" class="btn" onclick="lookup();"><span class="btn_img"><span class="search">Look Up</span></span></a>                            
                         </td>
                        </tr>                                     
                        <tr>
                            <td  scope="row" class="label" align="left">
                            <label for="phoneNumber"> <fmt:message key="userAccountDetails.phoneNumber"/></label>
                                
                            </td>
                            <td class="value">                                
                                <s:textfield id="phoneNumber" name="phoneNumber" maxlength="200" size="100" cssStyle="width:200px"  />                                          
                            </td>
                        </tr>                                     
                        <tr>
                            <td  scope="row" class="label" align="left">
                             <label for="emailId"> <fmt:message key="userAccountDetails.emailId"/></label>
                                
                            </td>
                            <td class="value">                                
                                <s:textfield id="emailId" name="emailId" maxlength="200" size="100" cssStyle="width:200px"  />                                          
                            </td>
                        </tr>                           
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                            </li>
                        </ul>
                    </del>
                </div>                
            </s:form>
            </div>
        </div>        
    </body>
</html>