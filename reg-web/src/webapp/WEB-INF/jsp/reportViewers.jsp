<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="reportviewers.page.title"/></title>
        <s:head/>
        
        <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
        
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js?534785924'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
        
        
        <script type="text/javascript" language="javascript">
        
            function submitForm() {
                var form = document.forms[0];
                form.action="viewReportViewerssearch.action";
                form.submit();
            }
            
            function save() {
                var form = document.forms[0];
                form.action="viewReportViewerssave.action";
                form.submit();
            }
            
            function resetSearch() {
                $("#firstName").val("");
                $("#lastName").val("");
                $("#emailAddress").val("");
                submitForm();
            }
            
            jQuery(function() {
                jQuery('#row').dataTable( {
                	"bPaginate" : false,
                    "bLengthChange" : false,
                    "bFilter" : false,
                    "bSort" : true,
                    "bInfo" : false,
                    "bAutoWidth" : false,
                    "order": [[ 1, "asc" ]],
                    "aoColumnDefs" : [ {
                        'bSortable' : false,
                        'aTargets' : [ 3 ]
                    } ]
                });
            });
            
        </script>
    </head>
    <body>
        <!-- main content begins-->
      <div class="container">
	    <div class="filter-checkbox">
	      <input type="checkbox" onclick="toggledisplay('filters', this)" id="filtercheckbox" name="checkbox">
	      <label for="filtercheckbox">Hide Search Criteria</label>
	    </div>
        <c:set var="topic" scope="request" value="managereports"/>
        	<h1 class="heading"><span><fmt:message key="reportviewers.page.header"/></span></h1>
            <reg-web:failureMessage/>
            <reg-web:sucessMessage/>
            <s:form name="formReportViewer" action="viewReportViewersview.action" cssClass="form-horizontal" role="form">
                <s:token/>
                <div  id="filters">
                 <div class="form-group">
                 	<label for="firstName" class="col-xs-4 control-label"> <fmt:message key="reportviewers.criteria.firstname"/></label>
                    <div class="col-xs-4">    
                    	<s:textfield id="firstName" name="criteria.firstName" maxlength="200" size="100"  cssClass="form-control"/>
                    </div>
                 </div>
				 <div class="form-group">	
                 	<label for="lastName"  class="col-xs-4 control-label"> <fmt:message key="reportviewers.criteria.lastname"/></label>
                    <div class="col-xs-4">
                        <s:textfield id="lastName" name="criteria.lastName"   maxlength="200" size="100"  cssClass="form-control" />
                    </div>
                 </div>
				 <div class="form-group">
					<label for="emailAddress" class="col-xs-4 control-label"> <fmt:message key="reportviewers.criteria.email"/></label>
                    <div class="col-xs-4">
                        <s:textfield id="emailAddress" name="criteria.emailAddress"  maxlength="200" size="100"  cssClass="form-control" />
                    </div>
                 </div>
				<div class="bottom no-border">
					<button id="searchreportuser" type="button" class="btn btn-icon btn-primary" onclick="submitForm();"><i class="fa-search"></i><fmt:message key="reportviewers.buttons.search"/> </button>
        			<button id="resetreportuser" type="button" class="btn btn-icon btn-default" onclick="resetSearch();"><i class="fa-repeat"></i><fmt:message key="reportviewers.buttons.reset"/></button>	                        
                </div>
                </div>
                <div class="line"></div>
                <jsp:include page="/WEB-INF/jsp/reportViewersResults.jsp"/>
                <div class="mt10 mb20 align-center scrollable">
                	<button id="savereportuser" type="button" class="btn btn-icon btn-primary" onclick="save();"><i class="fa-floppy-o"></i><fmt:message key="reportviewers.buttons.save"/></button>
                </div>
            </s:form>
          </div>
        </div>
    </body>
</html>
