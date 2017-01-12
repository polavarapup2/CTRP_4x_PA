<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery-1.11.1.min.js"/>"></script>        
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
  
        <script type="text/javascript" language="javascript">
        function cancelValues() {
           top.window.loadDiv();           
        }
        
        function deleteSites() {
            var deleteStatus = $('objectsToDelete').value;
            top.window.loadTopPage(deleteStatus);
        }
        
       </script>
    
    </head>
    <body>
    <s:hidden id="objectsToDelete" name="objectsToDelete" />
    <s:form name="partOrgs">
 <c:if test="${fn:length(requestScope.subjects) > 0}">
    <div style="width:450px">
    <p></p>
    <p>
     Warning: The following site(s) have patients accrued against them. If you delete the site(s), the accrual data will be deleted.
    </p>
    </div>
    <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" name="subjects" export="false" style="width:450px">
        <display:column escapeXml="false" titleKey="accrual.site.poid" property="poID" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="accrual.site.sitename" property="siteName" headerClass="sortable"/>
        <display:column escapeXml="false" titleKey="accrual.subject.count" property="accuralCount" headerClass="sortable"/>
    </display:table>
</c:if>
    <p></p>
     <div style="width:450px;" align="center">
         <p>Are you sure you want to delete the site(s)?</p>
     </span>
    </div>
    <div class="actionsrow" style="width:450px">
          <del class="btnwrapper">
              <ul class="btnrow">
                  <li>
                     <s:a cssClass="btn" href="javascript:void(0)" onclick="deleteSites();">
                         <span class="btn_img">OK</span>
                     </s:a>
                     <s:a onclick="cancelValues();" href="javascript:void(0)" cssClass="btn">
                          <span class="btn_img"><span class="cancel">Cancel</span></span>
                     </s:a>
                      </li>
               </ul>
          </del>
    </div>
    </s:form>
    </body>
</html>