<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/dataTables.colVis.min.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.colVis.min.js'/>"></script>
<script type="text/javascript" language="javascript">
	jQuery(function() {
		jQuery('#row').dataTable( {
			"sDom": 'pCrfltip',
			"pagingType": "full_numbers",
			"bAutoWidth" :false,
	        "oColVis": {
	            "buttonText": "Choose columns"
	        }
		});
	});
	function handleAction() {
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
    	var width = 950;
    	var height = 620;
    	/*if (Prototype.Browser.IE) {
    		width = 670;
            height = 500;            		
    	}*/
    	showPopWin('personsSearchshowDetailspopup.action?personID='+personID, width, height, '', 'Person Details');            	
    }
    
</script>

<!-- main content begins-->
<div class="container">
  <ul class="nav nav-tabs">
    <li><a href="<s:url action='searchTrial.action' />"><i class="fa-flask"></i><fmt:message key="search.trial.page.header"/></a></li>
    <li <s:if test="results == null">class="active"</s:if>><a href="#search-persons" data-toggle="tab"><i class="fa-user"></i><fmt:message key="person.search.header"/></a></li>
    <li><a href="<s:url action='organizationsSearch.action' />"><i class="fa-sitemap"></i><fmt:message key="organization.search.header"/></a></li>
    <s:if test="results != null">
    	<li class="active"><a href="#search-results" data-toggle="tab"><i class="fa-search"></i><fmt:message key="search.results"/></a></li>
    </s:if>
  </ul>
  
  <!-- main content begins-->
<div class="tab-content">
 <div class="tab-pane fade  <s:if test="results == null">active in</s:if> " id="search-persons">
        <c:set var="topic" scope="request" value="searchperson"/>
            <s:form id="personSearchForm"  action="personsSearchquery.action" cssClass="form-horizontal" role="form">
            	 <reg-web:failureMessage/> 
                 <reg-web:sucessMessage/>
                <p class="mb20"><fmt:message key="search.instructions"/></p>                   
                 
           		<div class="form-group">
                       <label for="poID" class="col-xs-2 control-label"><fmt:message key="person.search.poID"/></label>
                       <div class="col-xs-4">
                           <s:textfield id="poID" name="criteria.id" maxlength="10"  cssClass="form-control"  />
                       </div>
                        <label for="functionalRole" class="col-xs-2 control-label"> <fmt:message key="person.search.role"/></label>
                      <div class="col-xs-4">
                           <s:select name="criteria.functionalRole" id="functionalRole"  cssClass="form-control" 
                              list="#{'Healthcare Provider':'Healthcare Provider',
                              'Clinical Research Staff':'Clinical Research Staff',
                              'Organizational Contact':'Organizational Contact'}" 
                              headerKey="" headerValue="Any" value="criteria.functionalRole" />
                      </div>
               </div>
              	<div class="form-group">
                	<label for="ctepID" class="col-xs-2 control-label" ><fmt:message key="person.search.ctepID"/></label>
					<div class="col-xs-4">
					   	<s:textfield id="ctepID" name="criteria.ctepId" maxlength="75"  cssClass="form-control" />
					</div> <label for="affiliation" class="col-xs-2 control-label"><fmt:message key="person.search.affiliation"/></label>
					<div class="col-xs-4">
						<s:textfield id="affiliation" name="criteria.affiliation" maxlength="200"  cssClass="form-control"   />
					</div>                 
                  </div>
              	<div class="form-group">
					<label for="firstName" class="col-xs-2 control-label"><fmt:message key="person.search.firstName"/></label>
                    <div class="col-xs-4">
                    	<s:textfield id="firstName" name="criteria.firstName" maxlength="10"  cssClass="form-control"   />	
					</div>
					<label for="lastName" class="col-xs-2 control-label"><fmt:message key="person.search.lastName"/></label>
					<div class="col-xs-4">
						<s:textfield id="lastName" name="criteria.lastName" maxlength="200"  cssClass="form-control"   />
					</div>       
                </div>
                <div class="bottom">
            		<button type="button" class="btn btn-icon btn-primary" onclick="handleAction()"> <i class="fa-search"></i>Search </button>
            		<button type="button" class="btn btn-icon btn-default" onclick="resetValues();return false"><i class="fa-repeat"></i>Reset</button>
          		</div>
            </s:form>
        </div>
        <s:if test="results!=null">
        <div id="search-results" class="tab-pane fade active in">     
        <div class="tab-inside">
	    <div class="mb20 control-bar">   
	        <s:if test="results!=null && results.empty">
	            <div class="alert alert-warning">
	            No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria.
	            </div>
	        </s:if>        
			<s:if test="results!=null && !results.empty">
			    <h2><fmt:message key="person.search.results"/></h2>		
			    <s:set name="persons" value="results" scope="request" />
			    <div class="table-wrapper">
            	<div class="table-responsive">
			    <display:table class="table table-striped table-bordered" uid="row" name="persons" export="false"
			        requestURI="personsSearchquery.action">
			        <display:setProperty name="basic.msg.empty_list"
			            value="No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
			        <display:column escapeXml="false" title="PO-ID" >
			              <a href="javascript:void(0);" onclick="displayPersonDetails(<c:out value="${row.id}"/>)"><c:out value="${row.id}"/></a>
			        </display:column>
			        <display:column escapeXml="true" title="CTEP ID" property="ctepId"/>
				    <display:column decorator="gov.nih.nci.registry.decorator.HtmlEscapeDecorator" escapeXml="false" title="First Name" property="firstName" />			    		    
				    <display:column decorator="gov.nih.nci.registry.decorator.HtmlEscapeDecorator" escapeXml="false" title="Last Name" property="lastName"  />			    
				    <display:column escapeXml="true" title="Email" property="email"/>
			        <display:column escapeXml="false" title="Organization Affiliation" >
			            <c:forEach items="${row.organizations}" var="org">
			                <c:out value="${org.name.part[0].value}" />
			                <br />
			            </c:forEach>
			        </display:column>
	                <display:column escapeXml="false" title="Role" >
	                    <c:forEach items="${row.roles}" var="role">
	                        <c:out value="${role}" />
	                        <br />
	                    </c:forEach>
	                </display:column>		        
			        <display:column escapeXml="true" title="City" property="city" />
			        <display:column escapeXml="true" title="State" property="state" />
			    </display:table>
			    </div>
			    </div>	
			    </div>
			    </div>		
			</s:if>        
        </div>     
        </s:if>
        
        </div></div>   
