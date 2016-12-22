<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="manageCTGovUpload.title"/></title>
        <s:head/>        
        <script type="text/javascript" language="javascript">
        
	        function save() {
	            document.forms[0].action="manageCTGovUploadsave.action";
	            document.forms[0].submit();
	        }
	        
           function triggerCTGovUpload() {
	            document.forms[0].action="manageCTGovUploadtriggerCTGovUpload.action";
	            document.forms[0].submit();
            }
        
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="userAccountDetails.title"/></h1>        
        <div class="box" id="filters">
            <div class="fixedTopHeader">
            <s:form id="userAccountDetailsForm">
                <s:token/>        
                <pa:failureMessage/>
                <pa:sucessMessage/>                
                <table class="form">
                        <tr>
                            <td  scope="row" class="label" align="left">
                                <label for="enabled">ClinicalTrials.gov Upload Enabled?</label>                               
                            </td>
                            <td class="value">                                
                                <s:select id="enabled" name="enabled" list="#{'false':'No', 'true':'Yes'}"/>                                          
                            </td>
                        </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="save()"><span class="btn_img"><span class="save">Save Settings</span></span></s:a>
                            </li>
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="triggerCTGovUpload()"><span class="btn_img"><span class="save">Start ClinicalTrials.gov Upload Now</span></span></s:a>
                            </li>                            
                        </ul>
                    </del>
                </div>                
            </s:form>
            </div>
        </div>        
    </body>
</html>