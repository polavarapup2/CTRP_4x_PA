 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="subGroups.title"/></title>
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
<c:set var="topic" scope="request" value="abstractsubgroups"/>
 <h1><fmt:message key="subGroups.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <pa:studyUniqueToken/>
        <s:actionerror/>
    <h2><fmt:message key="subGroups.subtitle" /></h2>
    <c:if test="${fn:length(requestScope.subGroupsList) > 5}">    
    <div class="actionstoprow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:scientificAbstractorDisplayWhenCheckedOut>                       
                       <li><s:a href="javascript:void(0);" onclick="submitXsrfForm('subGroupsinput.action');" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{subGroupsList != null && !subGroupsList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected sub-group(s) from the study. Cancel to abort.', 'subGroupsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected sub-group(s) from the study. Cancel to abort.', 'subGroupsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>                    
                    </pa:scientificAbstractorDisplayWhenCheckedOut>
                </ul>
            </del>
    </div>
    </c:if>
    <s:if test="subGroupsList != null">
    <s:set name="subGroupsList" value="subGroupsList" scope="request"/>
    <display:table name="subGroupsList" id="row" class="data" sort="list"  pagesize="200" requestURI="subGroupsquery.action" export="false">
	    <display:column escapeXml="true" titleKey="subGroups.code" property="groupNumberText" sortable="true" headerClass="sortable" />
	    <display:column escapeXml="true" titleKey="subGroups.description" property="description" sortable="true" headerClass="sortable" />
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <display:column title="Edit" class="action">
                <s:url id="url" action="subGroupsedit"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
                <s:a href="%{url}"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
            </display:column>
            <display:column title="Delete" class="action">
                <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>  
                <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>              
            </display:column>
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    	</display:table>
  </s:if>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
                    <pa:scientificAbstractorDisplayWhenCheckedOut>                       
					   <li><s:a href="javascript:void(0);" onclick="submitXsrfForm('subGroupsinput.action');" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{subGroupsList != null && !subGroupsList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected sub-group(s) from the study. Cancel to abort.', 'subGroupsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected sub-group(s) from the study. Cancel to abort.', 'subGroupsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>					   
                    </pa:scientificAbstractorDisplayWhenCheckedOut>
				</ul>
			</del>
		</div>
  	</s:form>
   </div>
 </body>
 </html>
