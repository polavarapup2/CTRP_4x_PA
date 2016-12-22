<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>        
        <script type="text/javascript">
             function callOnloadFunctions(){
                // there are no onload functions to call for this jsp
                // leave this function to prevent 'error on page'
             }
        </script>
    </head>
    <body>
        <h1><fmt:message key="plannedMarker.details.title"/></h1>
        <c:set var="topic" scope="request" value="plannedmarker"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror/></div>
            </s:if>
            <s:form name="diseaseForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                <h2><fmt:message key="plannedMarker.details.title"/></h2>
                <c:if test="${fn:length(requestScope.plannedMarkerList) > 5}">                
                <div class="actionstoprow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <s:url id="addUrl" namespace="/protected" action="plannedMarkercreate"/>
                                    <s:a href="%{addUrl}" cssClass="btn">
                                        <span class="btn_img"><span class="add">Add</span></span>
                                    </s:a>
                                </li>
                                <s:if test="%{plannedMarkerList != null && !plannedMarkerList.isEmpty()}">
                                    <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarkerdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarkerdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                                    <li><pa:toggleDeleteBtn/></li>
                                </s:if>                                
                            </pa:scientificAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
                </c:if>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="plannedMarkerList" value="plannedMarkerList" scope="request"/>
                            <display:table name="plannedMarkerList" htmlId="plannedMarkerTable" id="row" class="data" sort="list" pagesize="200" requestURI="plannedMarker.action">
                                <display:column escapeXml="true" property="name" sortable="true" titleKey="plannedMarker.name" headerClass="sortable"/>
                                <display:column escapeXml="true" titleKey="plannedMarker.evaluationType" headerClass="sortable" sortable="true">
                                   <s:if test='%{#attr.row.evaluationType.contains("Other")}'>  
                                        <c:out value="${row.evaluationType}"/>
                                        <c:out value=":"/>
                                        <c:out value="${row.evaluationTypeOtherText}"/>    
                                    </s:if> 
                                    <s:else>  
                                        <c:out value="${row.evaluationType}"/>    
                                    </s:else>   
                                </display:column>  
                                
                                
                                <display:column escapeXml="true" titleKey="plannedMarker.assayType" headerClass="sortable" sortable="true">
                                   <s:if test='%{#attr.row.assayType.contains("Other")}'>  
                                        <c:out value="${row.assayType}"/>
                                        <c:out value=":"/>
                                        <c:out value="${row.assayTypeOtherText}"/>    
                                    </s:if> 
                                    <s:else>  
                                        <c:out value="${row.assayType}"/>    
                                    </s:else>   
                                </display:column>  
                                <display:column escapeXml="true" property="assayUse" sortable="true" titleKey="plannedMarker.assayUse" headerClass="sortable"/>
                                <display:column escapeXml="true" property="assayPurpose" sortable="true" titleKey="plannedMarker.assayPurpose" headerClass="sortable"/>
                                 <display:column escapeXml="true" titleKey="plannedMarker.tissueSpecimenType" headerClass="sortable" sortable="true">
                                   <s:if test='%{#attr.row.tissueSpecimenType.contains("Other")}'>  
                                        <c:out value="${row.tissueSpecimenType}"/>
                                        <c:out value=":"/>
                                        <c:out value="${row.specimenTypeOtherText}"/>    
                                    </s:if> 
                                    <s:else>  
                                        <c:out value="${row.tissueSpecimenType}"/>    
                                    </s:else>   
                                </display:column>
                                <display:column escapeXml="true" property="status" sortable="true" titleKey="plannedMarker.status" headerClass="sortable" />
                                <pa:scientificAbstractorDisplayWhenCheckedOut>
                                    <display:column titleKey="plannedMarker.edit" headerClass="centered" class="action">
                                        <s:url id="editUrl" namespace="/protected" action="plannedMarkeredit">
                                            <s:param name="selectedRowIdentifier" value="%{#attr.row.id}"/>
                                        </s:url>
                                        <s:a href="%{editUrl}">
                                            <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                                        </s:a>
                                    </display:column>
                                    <display:column titleKey="plannedMarker.delete" headerClass="centered" class="action">
                                        <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
                                        <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
                                    </display:column>
                                </pa:scientificAbstractorDisplayWhenCheckedOut>
                            </display:table>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <s:url id="addUrl" namespace="/protected" action="plannedMarkercreate"/>
                                    <s:a href="%{addUrl}" cssClass="btn">
                                        <span class="btn_img"><span class="add">Add</span></span>
                                    </s:a>
                                </li>
		                        <s:if test="%{plannedMarkerList != null && !plannedMarkerList.isEmpty()}">
		                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarkerdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected marker(s) from the study. Cancel to abort.', 'plannedMarkerdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
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
