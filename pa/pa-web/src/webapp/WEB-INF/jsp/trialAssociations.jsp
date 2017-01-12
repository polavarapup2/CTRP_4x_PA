 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialAssociations.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
</SCRIPT>
 <body>
 <c:set var="topic" scope="request" value="associatetrial"/>
 <h1><fmt:message key="trialAssociations.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <pa:studyUniqueToken/>
        <s:actionerror/>
    <h2><fmt:message key="trialAssociations.title" /></h2>
    
    <s:set name="trialAssociations" value="trialAssociations" scope="request"/>
    <display:table name="trialAssociations" id="row" class="data" sort="list"  pagesize="200" requestURI="trialAssociationsquery.action" export="false">
	    <display:column escapeXml="true" titleKey="trialAssociations.trialIdentifier" property="studyIdentifier.value" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialAssociations.identifierType" property="identifierType.code" sortable="true" headerClass="sortable" />	    
        <display:column escapeXml="true" titleKey="trialAssociations.trialType" property="studyProtocolType.code" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialAssociations.trialSubType" property="studySubtypeCode.code" sortable="true" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="trialAssociations.officialTitle" property="officialTitle.value" sortable="true" headerClass="sortable" />        
        <display:column title="Edit" class="action">
            <s:url id="url" action="trialAssociationsedit"><s:param name="id" value="%{#attr.row.identifier.extension}" /><s:param name="page" value="%{'Edit'}"/></s:url>
            <s:a href="%{url}"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
        </display:column>
        <display:column title="Delete" class="action">
            <label for="delete" style="display:none">delete</label>
            <s:checkbox id="delete" name="objectsToDelete" fieldValue="%{#attr.row.identifier.extension}" value="%{#attr.row.identifier.extension in objectsToDelete}"/>                
        </display:column>        
    </display:table>
  
		<div class="actionsrow">
			<del class="btnwrapper">
			<c:if test="${sessionScope.isAdminAbstractor==true
	                                ||sessionScope.isScientificAbstractor==true ||sessionScope.isSuAbstractor==true}">
				<ul class="btnrow">                                           
					   <li><s:a href="javascript:void(0);" onclick="submitXsrfForm('trialAssociationsinput.action');" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{!trialAssociations.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected associated trials. Cancel to abort.', 'trialAssociationsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>
				</ul>
		   </c:if>
			</del>
		</div>
  	</s:form>
   </div>
 </body>
 </html>
