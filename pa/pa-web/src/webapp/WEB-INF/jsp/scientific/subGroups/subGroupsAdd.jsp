<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="subGroups.addtitle" /></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();         
}
function handleAction(){
    var page=document.forms[0].page.value;
    if (page == "Edit"){
 		document.forms[0].action="subGroupsupdate.action";
 		document.forms[0].submit();  	
	} else {
 		document.forms[0].action="subGroupscreate.action";
 		document.forms[0].submit();   
 	} 
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}
</SCRIPT>
<body>
<c:set var="topic" scope="request" value="abstractsubgroups"/>
 <h1><fmt:message key="subGroups.addtitle" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <s:url id="cancelUrl" namespace="/protected" action="subGroupsquery"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="subGroups.addtitle" /></h2>
    <s:hidden name="page" />
    <s:hidden name="id"/>
    <table class="form">                
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="subGroups.code"/><span class="required">*</span>
                     </label>
                    </td>
    				<td class="value">    					
                    		<s:textfield id="typeCode" cssClass="charcounter" name="subGroupsWebDTO.groupNumberText" maxlength="200"  cssStyle="width:80px"/>                   
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>subGroupsWebDTO.code</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td>         
                </tr>                
                <tr>
                     <td scope="row" class="label">
                     <label for="fileName">
                            <fmt:message key="subGroups.description"/><span class="required">*</span>
                     </label>
                    </td>
                    <td class="value">
                        <s:textarea id="fileName" name="subGroupsWebDTO.description" cssStyle="width:606px" rows="20" 
                            maxlength="200" cssClass="charcounter"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>subGroupsWebDTO.description</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>         
                </tr>                 
        </table>
		<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                    <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                    </li>                
                </ul>   
            </del>
        </div> 

                   
    </s:form>
   </div>
 </body>
 </html>
