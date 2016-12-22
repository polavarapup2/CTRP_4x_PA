<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="displaytrialownership.page.title"/></title>
    <s:head/>
       <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
        
        
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

function submitForm() {
    document.forms[0].action="displayTrialOwnershipsearch.action";
    document.forms[0].submit();
}

function resetSearch() {
    document.getElementById("firstName").value="";
    document.getElementById("lastName").value="";
    document.getElementById("emailAddress").value="";
    document.getElementById("nciIdentifier").value="";
}

function setEmailPref(trialId, uid, selected) {
    if(selected){
        $("yes_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny active"
        $("no_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny"
    }else{
        $("yes_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny "
        $("no_"+trialId+"_"+uid).className  = "btn btn-default btn-tiny active"
    }
    var  url = '${pageContext.request.contextPath}/siteadmin/manageTrialOwnershipupdateEmailPref.action';
    var params = {
        selected: selected,
        trialId: trialId,
        regUserId: uid
    };
    var aj = callAjaxPost(null, url, params);
    return false
}

jQuery(function() {
    jQuery('#row').dataTable( {
    	"sDom": 'pCrfltip',                 
        "aoColumnDefs" : [ {
            'bSortable' : false,
            'aTargets' : [ 5 ]
         }, {
             'bSortable' : false,
             'aTargets' : [ 6 ]
          }],
         "pagingType": "full_numbers",
         "order": [[ 3, "desc" ]],           
         "oLanguage": {
            "sInfo": "Showing _START_ to _END_ of _TOTAL_",
            "sLengthMenu": "Show _MENU_",
            "oPaginate": {
                "sFirst": "<<",
                "sPrevious": "<",
                "sNext": ">",
                "sLast": ">>"
              }
        }
    });
});

</SCRIPT>
<body>
<!-- for hiding the page contents while loading-->
<div style="display: block" id="hideAll"><img src="${pageContext.request.contextPath}/images/loading.gif"/></div>
<script type="text/javascript">
 document.getElementById("hideAll").style.display = "block";
</script>
 
<!-- main content begins-->
 <div class="container">
    
	<c:set var="topic" scope="request" value="displayownership"/>
	<h1 class="heading"><span><fmt:message key="displaytrialownership.page.header"/></span></h1>
    <reg-web:failureMessage/>
    <reg-web:sucessMessage/>
      <div id="ajaxIndicator" class="info" style="display: none;">
            <img alt="Indicator" align="middle" src="../images/loading.gif"/>&nbsp;<fmt:message key="displaytrialownership.saving"/>
      </div>
      <div class="alert alert-success confirm_msg" style="display: none;" id="prefSaveConfirmation">
          <strong>Message.</strong>&nbsp;<fmt:message key="displaytrialownership.saved"/>
      </div>
      <div class="alert alert-danger" style="display: none;" id="prefSaveError">
          <strong>Message.</strong>&nbsp;<fmt:message key="displaytrialownership.error"/>
      </div>         
      <div class="filter-checkbox">
	      <input type="checkbox" onclick="toggledisplay('filters', this)" id="filtercheckbox" name="checkbox">
	      <label for="filtercheckbox">Hide Search Criteria</label>
      </div>
      <s:form name="formDisplayTrialOwnership" action="displayTrialOwnershipview.action"  cssClass="form-horizontal" role="form">
      	 <div id="filters">
		 <div class="form-group">
        	<label for="firstName" class="col-xs-4 control-label"> <fmt:message key="displaytrialownership.criteria.firstname"/></label>
            <div class="col-xs-4">    
                <s:textfield id="firstName" name="criteria.firstName" maxlength="200" size="100" cssClass="form-control" />
           </div>
	     </div>
	     <div class="form-group">
            <label for="lastName" class="col-xs-4 control-label"> <fmt:message key="displaytrialownership.criteria.lastname"/></label>
            <div class="col-xs-4">
                <s:textfield id="lastName" name="criteria.lastName"   maxlength="200" size="100"  cssClass="form-control" />
            </div>
     	 </div>
	     <div class="form-group">
            <label for="emailAddress" class="col-xs-4 control-label"> <fmt:message key="displaytrialownership.criteria.email"/></label>
            <div class="col-xs-4">
         	   <s:textfield id="emailAddress" name="criteria.emailAddress"  maxlength="200" size="100"  cssClass="form-control" />
            </div>
      	 </div>
         <div class="form-group">
            <label for="nciIdentifier" class="col-xs-4 control-label"> <fmt:message key="displaytrialownership.criteria.nciidentifier"/></label>
            <div class="col-xs-4">
               <s:textfield id="nciIdentifier" name="criteria.nciIdentifier"  maxlength="200" size="100"  cssClass="form-control" />
            </div>
      	 </div>
		<div class="bottom no-border">
	        <button type="button" class="btn btn-icon btn-primary" onclick="submitForm();"> <i class="fa-search"></i><fmt:message key="displaytrialownership.buttons.search"/> </button>
	        <button type="button" class="btn btn-icon btn-default" onclick="resetSearch();"><i class="fa-repeat"></i><fmt:message key="displaytrialownership.buttons.reset"/></button>
      	</div>                      
        </div>
        <div class="line"></div>
        <s:set name="records" value="trialOwnershipInfo" scope="request"/>
        <h3 class="heading mt20"><span>Search Results</span></h3>
        <div id="viewAll">           
           <display:table class="data table table-striped sortable" summary="This table contains your search results."
                      id="row" name="records" requestURI="displayTrialOwnershipview.action" export="false">
            <display:column escapeXml="true" titleKey="displaytrialownership.results.firstname" property="firstName" maxLength= "200" sortable="false" headerClass="sortable" headerScope="col"/>
            <display:column escapeXml="true" titleKey="displaytrialownership.results.lastname" property="lastName" sortable="false" headerClass="sortable" headerScope="col"/>
            <display:column escapeXml="true" titleKey="displaytrialownership.results.email" property="emailAddress" sortable="false" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.nciidentifier" property="nciIdentifier"  sortable="false" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.leadOrgId" property="leadOrgId"  sortable="false" headerClass="sortable" headerScope="col"/>
            <display:column titleKey="displaytrialownership.results.emails" headerScope="col">
               <!-- <label for="enableEmailNotifications_${row.userId}_${row.trialId}" class="hidden-label"><fmt:message key="displaytrialownership.results.emails"/></label>
                 <s:select id="enableEmailNotifications_%{#attr.row.userId}_%{#attr.row.trialId}" name="enableEmailNotifications_%{#attr.row.userId}_%{#attr.row.trialId}" 
                    onchange="setEmailNotificationsPreference(%{#attr.row.userId}, %{#attr.row.trialId}, $F(this));"
                    list="#{'true':'Yes','false':'No'}"  value="%{#attr.row.emailsEnabled}"/> -->
                    
                <div class="btn-group" data-toggle="buttons">
                   <label id="yes_${row.trialId}_${row.userId}" name="emailYes" onclick="setEmailPref('${row.trialId}','${row.userId}', true)" class="btn btn-default btn-tiny <s:if test='%{#attr.row.emailsEnabled}'>active</s:if> ">
                     <input type="radio" name="options">
                     Yes </label>
                   <label id="no_${row.trialId}_${row.userId}" name="emailNo" onclick="setEmailPref('${row.trialId}','${row.userId}', false)" class="btn btn-default btn-tiny <s:if test='%{!#attr.row.emailsEnabled}'>active</s:if>" >
                     <input type="radio" name="options">
                     No </label>
                 </div>    
            </display:column>
            <display:column titleKey="displaytrialownership.results.action">
                <c:url var="removeUrl" value="displayTrialOwnershipremoveOwnership.action">
                    <c:param name="userId" value="${row.userId}" />
                    <c:param name="trialId" value="${row.trialId}" />
                </c:url>
                <a href="<c:out value="${removeUrl}"/>"><button type="button" class="btn btn-icon btn-primary" onclick="resetSearch();"><i class="fa-minus"></i><fmt:message key="displaytrialownership.buttons.remove"/></button></a>
            </display:column>
        </display:table>
        </div>        
    </s:form>
</div>
<script type="text/javascript">
document.getElementById("hideAll").style.display = "none";
</script> 
</body>
</html>
