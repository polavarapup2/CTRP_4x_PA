 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="pendingAccruals.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleAction() {
    document.forms[0].action="pendingAccrualsexecute.action";
    document.forms[0].submit();
}
function resetValues() {
    $("identifier").value="";
}
</SCRIPT>
 <body>
 <c:set var="topic" scope="request" value="pendingaccruals"/>
 <h1><fmt:message key="pendingAccruals.title"/></h1>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form name= "pendingAccruals">
        <s:token/>
        <pa:studyUniqueToken/>
        <s:actionerror/>
    <h2><fmt:message key="pendingAccruals.title" /></h2>
    <table class="form">
    <tr>
        <td scope="row" class="label">
                            <label for="identifier"><fmt:message key="studyProtocol.identifier"/></label>
                            <br><span class="info">(e.g: NCI-2008-00015; ECOG-1234, etc)</span>
                        </td>
                        <td>
                            <s:textfield id="identifier" name="identifier" maxlength="200" size="100"  cssStyle="width:200px" />
                        </td>
    </tr>
    </table>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                </li>
            </ul>
        </del>
    </div>
    <h2>Search Results</h2>
    <c:if test="${fn:length(requestScope.pendingAccruals) > 5}">       
        <div class="actionstoprow">
            <del class="btnwrapper">
                <ul class="btnrow">                                         
                        <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected sites. Cancel to abort.', 'pendingAccrualsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Remove Site From Queue</span></span></s:a></li>
                        <li><s:a href="#" cssClass="btn" onclick="document.pendingAccruals.reset();return false"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>
                </ul>
            </del>
        </div>
        </c:if>          
    <s:set name="pendingAccruals" value="pendingAccruals" scope="request"/>
    <display:table name="pendingAccruals" id="row" class="data" sort="list"  pagesize="200" requestURI="pendingAccrualsexecute.action" export="false">
        <display:column escapeXml="true" titleKey="pendingAccruals.trialIdentifier" property="studyIdentifier" sortable="true" headerClass="sortable" />
        <s:if test="identifier != null"> 
            <display:column escapeXml="true" titleKey="pendingAccruals.ctepIdentifier" property="ctepId" sortable="true" headerClass="sortable" />       
            <display:column escapeXml="true" titleKey="pendingAccruals.dcpIdentifier" property="dcpId" sortable="true" headerClass="sortable" />
        </s:if>
        <display:column escapeXml="true" titleKey="pendingAccruals.missingSiteIdentifier" property="studySite" sortable="true" headerClass="sortable" />
        <s:if test="identifier != null"> 
            <display:column escapeXml="true" titleKey="pendingAccruals.missingSiteName" property="orgName" sortable="true" headerClass="sortable" />
        </s:if>
        <display:column title="Delete" class="action">
            <label for="delete${row.id}" style="display:none">delete</label>
            <s:checkbox id="delete%{#attr.row.id}" name="objectsToDelete" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>                
        </display:column>        
    </display:table>
  
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">                                           
                        <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected sites. Cancel to abort.', 'pendingAccrualsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Remove Site From Queue</span></span></s:a></li>
                        <li><s:a href="#" cssClass="btn" onclick="document.pendingAccruals.reset();return false"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>                       
                </ul>
            </del>
        </div>
    </s:form>
   </div>
 </body>
 </html>
