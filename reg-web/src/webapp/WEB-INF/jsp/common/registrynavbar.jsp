 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <script type="text/javascript" language="javascript">
	function displayBatchUpload() {
		var width = 700;
		var height = 450;
		if (Prototype.Browser.IE) {
			width = 720;
	        height = 470;            		
		}
		showPopWin('/registry/admin/batchUpload.action', width, height, donothing, 'Batch Trial Upload');
	}
	function donothing() {
		//does nothing
	}
</script>
  <!-- Fixed navbar -->
  <div class="navbar navbar-custom navbar-inverse navbar-static-top" id="nav">
    <div class="container">
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li class="active dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Search <b class="caret"></b></a>  
            <ul class="dropdown-menu">
              <li><a id="searchTrialsMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/searchTrial.action');" >Clinical Trials</a></li>
              <li><a id="personSearchMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/personsSearchexecute.action');">Persons</a></li>
              <li><a id="organizationSearchMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/protected/organizationsSearchexecute.action');">Organizations</a></li>
            </ul>
          </li>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Register Trial <b class="caret"></b></a>
            <ul class="dropdown-menu register-trial">
              <li><a href="${pageContext.request.contextPath}/protected/submitTrial.action?sum4FundingCatCode=National">National<i class="fa-question-circle help-text" id="popover" rel="popoverwithhtml" 
              data-content="<s:property escapeHtml="true" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.National','Registry')"/>" data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="${pageContext.request.contextPath}/protected/submitTrial.action?sum4FundingCatCode=Externally Peer-Reviewed">Externally Peer-Reviewed<i class="fa-question-circle help-text" id="popover" rel="popoverwithhtml" 
              data-content="<s:property escapeHtml="true" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.ExternallyPeerReviewed','Registry')"/>" data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="${pageContext.request.contextPath}/protected/submitTrial.action?sum4FundingCatCode=Institutional">Institutional<i class="fa-question-circle help-text" id="popover" rel="popoverwithhtml"
               data-content="<s:property escapeHtml="true" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.Institutional','Registry')"/>" data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="${pageContext.request.contextPath}/protected/submitProprietaryTrialinputNct.action">Industrial/Other<i class="fa-question-circle help-text" id="popover" rel="popoverwithhtml" 
              data-content="<s:property escapeHtml="true" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.Industrial','Registry')"/>" data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg"></i></a></li>
              <li><a href="#" data-toggle="modal" data-target="#myModal">View Category Definitions</a></li>
              <li><a href="${pageContext.request.contextPath}/protected/addSites.action">Add Sites</a></li>
              <s:set name="isUserRegAdmin" value="@gov.nih.nci.pa.service.util.CSMUserService@isCurrentUserRegAdmin()" scope="page" />
              <c:if test="${isUserRegAdmin}">
              <li class="batch">
                <button type="button" class="btn btn-icon btn-sm btn-default" onclick="displayBatchUpload();"><i class="fa-upload"></i> Batch Upload</button>
              </li>
              </c:if>
            </ul>
          </li>
          <s:set name="isUserRegAdmin" value="@gov.nih.nci.pa.service.util.CSMUserService@isCurrentUserRegAdmin()" scope="page" />
          <c:if test="${sessionScope.isSiteAdmin}">
	          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Administration <b class="caret"></b></a>
	            <ul class="dropdown-menu">
		           <li><a id="siteAdministrationMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/siteAdministrationsearch.action');">Site Administration</a></li>
	              	<li class="dropdown-submenu"><a id="showTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/displayTrialOwnershipsearch.action');">Trial Ownership</a>
	                <ul class="dropdown-menu">
	                  <li><a id="showTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/displayTrialOwnershipsearch.action');">View</a></li>
	                  <li><a id="manageTrialOwnershipMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageTrialOwnershipsearch.action');">Manage</a></li>
	                </ul>
	              </li>
	              <li class="dropdown-submenu"><a href="#">Accrual Access</a>
	                <ul class="dropdown-menu">
	                  <li><a id="viewAccrualAccessAssignmentByTrialMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageAccrualAccessassignmentByTrial.action');">View</a></li>
	                  <li><a id="manageAccrualAccessMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageAccrualAccess.action');">Manage</a></li>
	                  <li><a id="viewAccrualAccessAssignmentHistoryMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/manageAccrualAccessassignmentHistory.action');" >Assignment History</a></li>
	                </ul>
	              </li>
	              <!-- Added for PO-7595 #Start -->
                  <c:if test='${sessionScope.isReportsAllowed.equals("true") }'>
                  <li>
                    <a id="viewReportViewersMenuOption" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/viewReportViewerssearch.action');">Report Viewers</a>
                  </li>
                  </c:if>
                  <!-- Added for PO-7595 #End -->
                  
                  <!-- Added for PO-9192 #Start -->
                  <c:if test='${sessionScope.isProgramCodesAllowed}'>
                  <li class="dropdown-submenu"><a href="#">Program Codes</a>
	                <ul class="dropdown-menu">
	                  <li><a id="programCodesMasterList" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/programCodesexecute.action');">Manage Master List</a></li>
	                  <li><a id="programCodesMasterList" href="javascript:void(0)" onclick="submitXsrfForm('${pageContext.request.contextPath}/siteadmin/managePCAssignmentexecute.action');">Manage Code Assignments</a></li>
	                </ul>
	              </li>
                  </c:if>
                  <!-- Added for PO-9192 #End -->
	            </ul>
	          </li>
          </c:if>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Quick Links <b class="caret"></b></a>
            <ul class="dropdown-menu external-links">
            <li><a href="https://wiki.nci.nih.gov/display/CTRPdoc/NCI+Clinical+Trials+Reporting+Program+%28CTRP%29+User%27s+Guides" class="external" target="new0">Registration User's Guide</a></li>
              <li><a target="_blank" href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main">Clinical Trials Reporting Program (CTRP)</a></li>
              <li><a target="_blank" href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/resources">Useful Templates and Documentation</a></li>
              <li><a target="_blank" href="/accrual/home.action">NCI CTRP Accrual Application</a></li>
              <li><a target="_blank" href="http://www.cancer.gov/">National Cancer Institute (NCI)</a></li>
              <li><a target="_blank" href="http://cbiit.nci.nih.gov/">NCI Center for Biomedical Informatics and Information Technology (CBIIT)</a></li>
              <c:if test='${sessionScope.showExtReportLink}'>
                  <li>
                    <a target="_blank" href="${sessionScope.reportExtLinkUrl}">${sessionScope.reportExtLinkName}</a>
                  </li>
              </c:if>
            </ul>
          </li>
          <li><a href="#" data-toggle="modal" data-target="#contactUs">Contact Us</a></li>
        </ul>
        <div class="pull-right text-sizer"><a id="helpMenuOption" href="javascript:void(0)" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></div>
      </div>
      <!--/.nav-collapse --> 
    </div>
    <!--/.container --> 
  </div>
  <!--/.navbar --> 
  <div id="stickyalias"></div>