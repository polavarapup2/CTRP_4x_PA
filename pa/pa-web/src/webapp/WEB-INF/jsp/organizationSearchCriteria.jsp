<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="organization.search.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript">
            function handleAction() {
                 $('organizationSearchForm').action="organizationsSearchquery.action";
                 $('organizationSearchForm').submit();
            }
            function resetValues() {
            	$('organizationSearchForm').getElements().each(function (el) {
            		if (el.type!='button') {
            			el.setValue('');
            		}
            	});
            	$('searchResults').innerHTML='';
            }

            document.onkeypress = runEnterScript;
            function runEnterScript(e) {
                var KeyID = (window.event) ? event.keyCode : e.keyCode;
                if (KeyID == 13) {
                    handleAction();
                    return false;
                }
            }
            
            function displayOrgDetails(orgID) {
            	var width = 650;
            	var height = 450;
            	if (Prototype.Browser.IE) {
            		width = 670;
                    height = 500;            		
            	}
            	showPopWin('organizationsSearchshowDetailspopup.action?orgID='+orgID, width, height, '', 'Organization Details');            	
            }
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="organization.search.header"/></h1>
        <c:set var="topic" scope="request" value="searchorganization"/>
        <div class="box" id="filters">
            <s:form id="organizationSearchForm">        
                <pa:failureMessage/>
                <pa:sucessMessage/>
                
                <p align="center" class="info">
                   Enter information for at least one of the criteria and then click Search. 
                   The maximum number of results returned for any search is 500 records. If necessary, 
                   limit your search by providing additional search criteria.
                </p>                  
                
                
                <table class="form fluid_width">
                    <tr>
                        <td  scope="row" class="label" colspan="2" align="center">
                            <h3><fmt:message key="organization.search.idinfoHeader"/></h3>
                        </td>
                        <td  scope="row" class="label" colspan="2" align="center">
                            <h3><fmt:message key="organization.search.addressHeader"/></h3>
                        </td>
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="poID"><fmt:message key="organization.search.poID"/></label>
                        </td>
                        <td>
                            <s:textfield id="poID" name="criteria.identifier" maxlength="10" cssStyle="width:294px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="country"> <fmt:message key="organization.search.country"/></label>
                        </td>
                        <td>
                            <s:select name="criteria.country" id="country" 
                                cssStyle="width:300px;"
                                list="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getCountries()" listKey="alpha3"
                                listValue="name" headerKey="" headerValue="" value="criteria.country" />
                        </td>
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="ctepID"><fmt:message key="organization.search.ctepID"/></label>
                        </td>
                        <td>
                            <s:textfield id="ctepID" name="criteria.ctepId" maxlength="75" cssStyle="width:294px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="city"><fmt:message key="organization.search.city"/></label>
                        </td>
                        <td>
                            <s:textfield id="city" name="criteria.city" maxlength="200" cssStyle="width:294px"  />
                        </td>
                    </tr>                   
                    <tr>
                        <td  scope="row" class="label">
                            <label for="status"> <fmt:message key="organization.search.status"/></label>
                        </td>
                        <td>
                            <s:select name="criteria.status" id="status" cssStyle="width:300px;"
                                 list="#{'ACTIVE':'ACTIVE','PENDING':'PENDING','INACTIVE':'INACTIVE'}" 
                                 headerKey="" headerValue="" value="criteria.status" />
                        </td>
                        <td  scope="row" class="label">
                            <label for="state"> <fmt:message key="organization.search.state"/></label>
                        </td>
                        <td>
                            <s:select name="criteria.state" id="state" cssStyle="width:300px;"
                                list="@gov.nih.nci.pa.enums.USStateCode@values()" listKey="name"
                                listValue="code" headerKey="" headerValue="" value="criteria.state" />
                        </td>                    
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td  scope="row" class="label">
                            <label for="zip"><fmt:message key="organization.search.zip"/></label>
                        </td>
                        <td>
                            <s:textfield id="zip" name="criteria.zip" maxlength="10" cssStyle="width:294px"  />
                        </td>                        
                    </tr>  
                    <tr>
                        <td  scope="row" class="label">
                            <label for="name"><fmt:message key="organization.search.name"/></label>
                        </td>
                        <td>
                            <s:textfield id="name" name="criteria.name" maxlength="200" cssStyle="width:294px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="functionalRole"> <fmt:message key="organization.search.functionalRole"/></label>
                        </td>
                        <td>
                            <s:select name="criteria.functionalRole" id="functionalRole" cssStyle="width:300px;"
                                list="#{'Research Organization':'Lead Organization','Healthcare Facility':'Participating Site'}" 
                                headerKey="" headerValue="Both" value="criteria.functionalRole" />
                        </td>
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="family"><fmt:message key="organization.search.family"/></label>
                        </td>
                        <td>
                            <s:textfield id="family" name="criteria.familyName" maxlength="200" cssStyle="width:294px"  />
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
            </s:form>
        </div>
        <div class="line"></div>
        <div id="searchResults">        
        <s:if test="results!=null && results.empty">
            <div align="center">
            No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria.
            </div>
        </s:if>        
		<s:if test="results!=null && !results.empty">
		    <h2><fmt:message key="organization.search.results"/></h2>		
		    <s:set name="orgs" value="results" scope="request" />
		    <display:table class="data" sort="list" pagesize="10" uid="row" name="orgs" export="false"
		        requestURI="organizationsSearchquery.action">
		        <display:setProperty name="basic.msg.empty_list"
		            value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
		        <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true">
		          <a href="javascript:void(0);" onclick="displayOrgDetails(<c:out value="${row.id}"/>)"><c:out value="${row.id}"/></a>
		        </display:column>
		        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="true"/>
		        <display:column title="Name" headerClass="sortable" sortable="true">
		            <c:out value="${row.name}"/>
		        </display:column>
		        <display:column escapeXml="false" title="Family Name" sortable="false">
		            <c:forEach items="${row.families}" var="family">
		                <c:out value="${family.value}" />
		                <br />
		            </c:forEach>
		        </display:column>
		        <display:column escapeXml="true" title="City" property="city" headerClass="sortable"  sortable="true"/>
		        <display:column escapeXml="true" title="State" property="state" headerClass="sortable"  sortable="true"/>
		        <display:column escapeXml="true" title="Country" property="country" headerClass="sortable"  sortable="true"/>
		        <display:column escapeXml="true" title="Zip" property="zip" headerClass="sortable"  sortable="true"/>
		        <display:column escapeXml="true" title="Status" property="status" headerClass="sortable"  sortable="true"/>
		    </display:table>		
		</s:if>        
        </div>        
    </body>
</html>
