<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fixed navbar -->
  <div class="navbar navbar-custom navbar-inverse navbar-static-top" id="nav">
    <div class="container">
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li id="viewTrials"><a href="viewTrials.action">Trial Search</a></li>
          <li id="batchUpload"><a href="batchUpload.action">Batch Upload</a></li>
          <li id="priorSubmissions"><a href="priorSubmissions.action">Prior Submissions</a></li>
          <li id="accrualCounts"><a href="accrualCounts.action">Accrual Counts</a></li>
          <li id="diseaseSearch"><a href="diseaseSearch.action">Disease Search</a></li>
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Quick Links <b class="caret"></b></a>
            <ul class="dropdown-menu external-links">
            <li><a href="https://wiki.nci.nih.gov/display/CTRPdoc/NCI+Clinical+Trials+Reporting+Program+%28CTRP%29+User%27s+Guides" class="external" target="new0">Accrual User's Guide</a></li>
              <li><a href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/main" target="new0">Clinical Trials Reporting Program (CTRP)</a></li>
              <li><a href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/resources" target="new1">Useful Templates and Documentation</a></li>
              <li><a target="_blank" href="/registry/home.action"> NCI CTRP Registration Application</a></li>
              <li><a href="http://www.cancer.gov/" target="new2">National Cancer Institute (NCI)</a></li>
              <li><a href="http://cbiit.nci.nih.gov/" target="new3">NCI Center for Biomedical Informatics and Information Technology (CBIIT)</a></li>
            </ul>
          </li>
          <li><a href="#" data-toggle="modal" data-target="#contactUs">Contact Us</a></li>
        </ul>
        <div class="pull-right text-sizer"><a href="#" class="help" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a></div>
      </div>
      <!--/.nav-collapse --> 
    </div>
    <!--/.container --> 
  </div>
        <div id="stickyalias"></div>

  <!--/.navbar --> 
