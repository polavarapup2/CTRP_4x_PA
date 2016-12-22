<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <head>
    	<%-- <link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css" media="all" /> --%>
    	    
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
        <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
    
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/orgSearch.js'/>"></script>
        <script type="text/javascript" language="javascript">
        		
        	function createOrg() {
                var url = '/registry/protected/popupcreateOrganization.action';
                var country = $("orgCountry").value;
                var params = {
                    cityName: $("orgCity").value,
                    countryName: country,
                    email: $("orgEmail").value,
                    fax: $("orgFax").value,
                    orgName: $("orgName").value,
                    orgStAddress: $("orgAddress").value,
                    phoneNumber: $("orgPhone").value,
                    stateName: ((country == 'USA') ? $("orgStateSelect").value : $("orgStateText").value),
                    tty: $("orgTty").value,
                    url: $("orgUrl").value,
                    zipCode: $("orgZip").value
                };
            
                var div = $('getOrgs'); 
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
        	function loadDiv() {
        		resetUnusedSearchCriteria();
        		var url = '/registry/protected/popupdisplayOrgList.action';
        		var params = {
                    cityName: $("orgCitySearch").value,
                    countryName: $("orgCountrySearch").value,
                    ctepId: $("orgCtepIdSearch").value,
                    orgName: $("orgNameSearch").value,
                    familyName: $("orgFamilyNameSearch").value,
                    stateName: $("orgStateSearch").value,
                    zipCode: $("orgZipSearch").value,
                    poId: $("orgPOIdSearch").value
                };
        	    var div = $('getOrgs');   	   
        	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
        	    var aj = callAjaxPost(div, url, params);   
        	}
        	
            function resetUnusedSearchCriteria() {
                var orgCtepid = $('orgCtepIdSearch').value;
                var orgPoid = $('orgPOIdSearch').value;
                if(orgPoid.length > 0){
                    $("orgNameSearch").value = '';
                    $("orgFamilyNameSearch").value = '';
                    $("orgCitySearch").value = '';
                    $("orgStateSearch").value = '';
                    $("orgCountrySearch").value = '';
                    $("orgZipSearch").value = '';
                    $('orgCtepIdSearch').value = '';
                }
                if(orgPoid.length == 0 && orgCtepid.length > 0){
                    $("orgNameSearch").value = '';
                    $("orgFamilyNameSearch").value = '';
                    $("orgCitySearch").value = '';
                    $("orgStateSearch").value = '';
                    $("orgCountrySearch").value = '';
                    $("orgZipSearch").value = '';
                }
            }        	
        	
        </script>
    </head> 
   <body>
          <s:form id="poOrganizations" name="poOrganizations" cssClass="form-horizontal" role="form">
              <s:label name="orgErrorMessage"/>
              <div id="searchOrgJsp">
              	<jsp:include page="/WEB-INF/jsp/nodecorate/searchOrgForm.jsp"/>
              </div>
              <div id="createOrgJsp" style="display:none">
              	<jsp:include page="/WEB-INF/jsp/nodecorate/createOrg.jsp"/>
              </div>
              <div id="getOrgs" align="center">	
              	<jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>        	      
              </div>
          </s:form>
	</body>