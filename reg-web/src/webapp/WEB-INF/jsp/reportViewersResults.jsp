<%@ page import="java.util.*" %>
<%@ page import="gov.nih.nci.pa.domain.*" %>

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:set name="records" value="registryUsers" scope="request"/>
<s:set name="reports" value="reportList" scope="request"/>

<script type="text/javascript" src="${scriptPath}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${scriptPath}/js/select2.min.js"></script>
<link href="${stylePath}/select2.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">

$(function(){
  var $basicMultiple = $(".report-drop-down-select2");
  $basicMultiple.select2();

});

</script>
 
<s:set name="records" value="registryUsers" scope="request"/>
<!-- <b>Request Param: ${param.enableMS}</b> -->

<c:set var="showCheckBx" value="${param.enableMS != null && param.enableMS.equals('true')}"></c:set>
	<c:choose>
            <c:when test="${showCheckBx}">
            	<c:set var="msColName" value="Can View Data Table 4 Report?" />
            </c:when>
            <c:otherwise>
            	<c:set var="msColName" value="Permitted Reports" />
            </c:otherwise>
    </c:choose>
    
<h3 id="search_results" class="heading mt20"><span>Search Results</span></h3>

<display:table class="data table table-striped sortable" summary="This table contains your search results."
            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" id="row"
              name="records" requestURI="viewReportViewersview.action" export="false" defaultorder="ascending">
	
	<display:column escapeXml="true" titleKey="reportviewers.results.org" 
					property="affiliateOrg" maxLength= "200" 
					sortable="false" headerClass="sortable" headerScope="col"
					group="1" style="width: 300px;"/>   
    
    <display:column escapeXml="true" titleKey="reportviewers.results.firstname" property="firstName" 
    				maxLength= "200" sortable="false" headerClass="sortable" headerScope="col"/>
    				
    <display:column escapeXml="true" titleKey="reportviewers.results.lastname" property="lastName" 
    				sortable="false" headerClass="sortable" headerScope="col"/>
    				
    <display:column escapeXml="true" titleKey="reportviewers.results.email" property="emailAddress" 
    				sortable="false" headerClass="sortable" headerScope="col"/>
    				
    <c:set var="chkId" value="chkc${row.id}" />
    <c:set var="registerBean" value="${row}" />
    
    <display:column title="${msColName}" sortable="false" headerClass="sortable">
       	<c:set var="chkId" value="chkDT4${row.id}" />
       	
       	<select id="drop-report-select-${row.id}" title="Report Viewers" multiple="multiple" 
		               			name="permittedReports" size="2" class="report-drop-down-select2" style="width: 200px;">
		               		
							<%
							    RegistryUser regUser = (RegistryUser)pageContext.getAttribute("registerBean");
								
								List<String> reportInfoList = (List<String>)session.getAttribute( "reportList" );
								
								for(String repInfo: reportInfoList){
									
									String selectedStr = "";
									String userRptGroups = regUser.getReportGroups();
									if(userRptGroups != null && userRptGroups.length() >0){
										List<String> usGrpList = Arrays.asList(userRptGroups.split("[,]"));
										if(usGrpList.contains(repInfo)){
											selectedStr = "selected = \"selected\"";
										}else{
											selectedStr = "";
										}
									}else{
										selectedStr = "";
									}
							%>
							<option value="<%=repInfo%>~<%=regUser.getId() %>" <%=selectedStr%> > <%=repInfo%></option>
							<% 		
								}
							%>
						</select>
    </display:column>
</display:table>
