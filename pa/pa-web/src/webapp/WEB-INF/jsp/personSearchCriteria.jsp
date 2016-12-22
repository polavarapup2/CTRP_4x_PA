<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="person.search.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript">
            function handleAction() {
                 $('personSearchForm').action="personsSearchquery.action";
                 $('personSearchForm').submit();
            }
            function resetValues() {
            	$('personSearchForm').getElements().each(function (el) {
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
            
            function displayPersonDetails(personID) {
            	var width = 700;
            	var height = 550;
            	/*if (Prototype.Browser.IE) {
            		width = 670;
                    height = 500;            		
            	}*/
            	showPopWin('personsSearchshowDetailspopup.action?personID='+personID, width, height, '', 'Person Details');            	
            }
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="person.search.header"/></h1>
        <c:set var="topic" scope="request" value="searchperson"/>
        <div class="box" id="filters">
            <s:form id="personSearchForm">        
                <pa:failureMessage/>
                <pa:sucessMessage/>
                
                <p align="center" class="info">
                   Enter information for at least one of the criteria and then click Search. 
                   The maximum number of results returned for any search is 500 records. If necessary, 
                   limit your search by providing additional search criteria.
                </p>                
                
                <table class="form fluid_width">
                   
                    <tr>
                        <td  scope="row" class="label">
                            <label for="poID"><fmt:message key="person.search.poID"/></label>
                        </td>
                        <td>
                            <s:textfield id="poID" name="criteria.id" maxlength="10" cssStyle="width:294px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="functionalRole"> <fmt:message key="person.search.role"/></label>
                        </td>
                        <td>
                             <s:select name="criteria.functionalRole" id="functionalRole" cssStyle="width:300px;"
                                list="#{'Healthcare Provider':'Healthcare Provider',
                                'Clinical Research Staff':'Clinical Research Staff',
                                'Organizational Contact':'Organizational Contact'}" 
                                headerKey="" headerValue="Any" value="criteria.functionalRole" />
                        </td>
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="ctepID"><fmt:message key="person.search.ctepID"/></label>
                        </td>
                        <td>
                            <s:textfield id="ctepID" name="criteria.ctepId" maxlength="75" cssStyle="width:294px"  />
                        </td>                        
                    </tr>                   
                    <tr>
                        <td  scope="row" class="label">
                            <label for="status"> <fmt:message key="person.search.status"/></label>
                        </td>
                        <td>
                            <s:select name="criteria.status" id="status" cssStyle="width:300px;"
                                 list="#{'ACTIVE':'ACTIVE','PENDING':'PENDING','INACTIVE':'INACTIVE'}" 
                                 headerKey="" headerValue="" value="criteria.status" />
                        </td>                                           
                    </tr>
                    <tr>
                      
                        <td  scope="row" class="label">
                            <label for="firstName"><fmt:message key="person.search.firstName"/></label>
                        </td>
                        <td>
                            <s:textfield id="firstName" name="criteria.firstName" maxlength="10" cssStyle="width:294px"  />
                        </td>                        
                    </tr>  
                    <tr>
                        <td  scope="row" class="label">
                            <label for="lastName"><fmt:message key="person.search.lastName"/></label>
                        </td>
                        <td>
                            <s:textfield id="lastName" name="criteria.lastName" maxlength="200" cssStyle="width:294px"  />
                        </td>                       
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="affiliation"><fmt:message key="person.search.affiliation"/></label>
                        </td>
                        <td>
                            <s:textfield id="affiliation" name="criteria.affiliation" maxlength="200" cssStyle="width:294px"  />
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
            No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria.
            </div>
        </s:if>        
		<s:if test="results!=null && !results.empty">
		    <h2><fmt:message key="person.search.results"/></h2>		
		    <s:set name="persons" value="results" scope="request" />
		    <display:table class="data" sort="list" pagesize="10" uid="row" name="persons" export="false"
		        requestURI="personsSearchquery.action">
		        <display:setProperty name="basic.msg.empty_list"
		            value="No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
		        <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true">
		              <a href="javascript:void(0);" onclick="displayPersonDetails(<c:out value="${row.id}"/>)"><c:out value="${row.id}"/></a>
		        </display:column>
		        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="true"/>
			    <display:column escapeXml="true" title="First Name" property="firstName"  headerClass="sortable" sortable="true"/>			    
			    <display:column escapeXml="true" title="Last Name" property="lastName"  headerClass="sortable" sortable="true"/>			    
			    <display:column escapeXml="true" title="Email" property="email" sortable="true"/>
		        <display:column escapeXml="false" title="Organization Affiliation" sortable="false">
		            <c:forEach items="${row.organizations}" var="org">
		                <c:out value="${org.name.part[0].value}" />
		                <br />
		            </c:forEach>
		        </display:column>
                <display:column escapeXml="false" title="Role" sortable="false">
                    <c:forEach items="${row.roles}" var="role">
                        <c:out value="${role}" />
                        <br />
                    </c:forEach>
                </display:column>		        
		        <display:column escapeXml="true" title="City" property="city" sortable="true"/>
		        <display:column escapeXml="true" title="State" property="state" sortable="true"/>
		        <display:column escapeXml="true" title="Status" property="status" sortable="true"/>
		    </display:table>		
		</s:if>        
        </div>        
    </body>
</html>
