<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="participatingOrganizations.title" /></title>
<s:head />
<script type="text/javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/prototype.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/coppa.js"/>"></script>
<script type="text/javascript">
            var siteRecruitmentStatusDate = new CalendarPopup();
</script>
<link
    href="<c:url value='/scripts/js/jquery-ui-1.11.4.custom/jquery-ui.css'/>"
    rel="stylesheet" media="all" type="text/css" />
<link rel="stylesheet" type="text/css"
    href="<c:url value='/scripts/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css'/>">
</head>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleEdit(studyResourcingId){

    document.partOrgs.cbValue.value = studyResourcingId;
    document.partOrgs.action="participatingOrganizationsedit.action";
    document.partOrgs.submit();
}

function handleDelete() {
    var deleteObj = document.getElementsByName("objectsToDelete");
    var deleteObjFinal ='';
    var atLeastOne = false;
    for (var i=0; i < deleteObj.length; i++) {
        if (deleteObj[i].checked == true) { 
        	atLeastOne = true;
        	deleteObjFinal = (deleteObj[i].value) + ", " + deleteObjFinal;     
        } 
    }
    
    
   if (atLeastOne == true) {
    var url = '/pa/protected/popupParticipatingOrganizationsaccrualDeleteWarning.action?objectsToDelete=' +deleteObjFinal;
    showPopWin(url, 453, 300, '', 'Participating Sites Accrual Data');
   }
   else {
       document.partOrgs.action="participatingOrganizationsdelete.action";
       document.partOrgs.submit();
   }
}

function loadTopPage(objectsToDelete) {
       document.partOrgs.action="participatingOrganizationsdelete.action";
       document.partOrgs.submit();
       return true;
}

function loadDiv() {
    document.partOrgs.action="participatingOrganizations.action";
    document.partOrgs.submit();
    return true;
}

(function ($) {
    $(function() {
        $( document ).tooltip();
        
        var table = $('#row').DataTable({
            "paging":   false,
            bFilter: false,
            "columnDefs" : [ {
                "targets" : 5,
                "orderable" : false
            }]
        });
        
    });
})(jQuery);

</SCRIPT>

<body>
<h1><fmt:message key="participatingOrganizations.title" /></h1>
<c:set var="topic" scope="request" value="reviewparticipatingsites"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
    <pa:sucessMessage/>
    <pa:failureMessage/>
    <s:actionerror />
    <s:form name="partOrgs">
        <pa:studyUniqueToken/>
        <h2><fmt:message key="participatingOrganizations.title" /></h2> 
        <c:if test="${fn:length(requestScope.organizationList) > 5}">       
        <div class="actionstoprow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                        <li><a href="participatingOrganizationscreate.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="add" >Add</span></span></a></li>
                        <s:if test="%{organizationList != null && !organizationList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleDelete();" onkeypress="handleDelete();" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>                
                    </pa:adminAbstractorDisplayWhenCheckedOut>
                </ul>
            </del>
        </div>
        </c:if>
    <table class="form">
        <tr>
            <td colspan="2">
                <s:hidden name="cbValue" />
                <s:set name="organizationList" value="organizationList" scope="request" />
                <display:table name="organizationList" id="row" class="data" pagesize="200" sort="list" requestURI="participatingOrganizations.action">
                    <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true"  sortProperty="nciNumberAsLong">
                        <a href="javascript:void(0);" onclick="displayOrgDetails(<c:out value="${row.nciNumber}"/>)"><c:out value="${row.nciNumber}"/></a>
                    </display:column>
                    <display:column escapeXml="true" property="name" titleKey="participatingOrganizations.name" sortable="true" />
                    <display:column escapeXml="true" property="recruitmentStatus" titleKey="participatingOrganizations.recruitmentStatus" sortable="true" />
                    <display:column escapeXml="false" titleKey="participatingOrganizations.recruitmentStatusDate" sortable="false">
                    <span style="display: none;"><fmt:parseDate value="${row.recruitmentStatusDate}" pattern="MM/dd/yyyy"/></span>
                    <c:out value="${row.recruitmentStatusDate}" />
                    </display:column>
                    <display:column titleKey="participatingOrganizations.investigators">
                        <c:forEach var="item" items="${row.investigators}">
                                <a href="javascript:void(0);" onclick="displayPersonDetails(<c:out value="${item.key}"/>)"><c:out value="${item.value}"/></a>
                                <br>
                        </c:forEach>
                    </display:column>
                    <display:column property="primarycontact" titleKey="participatingOrganizations.primarycontacts"/>
                    <pa:displayWhenCheckedOut>
                        <display:column titleKey="participatingOrganizations.edit" headerClass="centered" class="action">
                            <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.id})"><img src='<c:url value="/images/ico_edit.gif"/>' alt="Edit" width="16" height="16"/></s:a>
                        </display:column>
                    </pa:displayWhenCheckedOut>
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                        <display:column titleKey="participatingOrganizations.unlink" headerClass="centered" class="action" >
                            <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
                            <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
                        </display:column>
                   </pa:adminAbstractorDisplayWhenCheckedOut>
              
                
                </display:table>
            </td>
        </tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <pa:adminAbstractorDisplayWhenCheckedOut>
                <li><a href="participatingOrganizationscreate.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="add" >Add</span></span></a></li>
                <s:if test="%{organizationList != null && !organizationList.isEmpty()}">
                    <li><s:a href="javascript:void(0);" onclick="handleDelete();" onkeypress="handleDelete();" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                    <li><pa:toggleDeleteBtn/></li>
                </s:if>                
            </pa:adminAbstractorDisplayWhenCheckedOut>
        </ul>
    </del>
</div>
</s:form>
</div>
</body>
</html>
