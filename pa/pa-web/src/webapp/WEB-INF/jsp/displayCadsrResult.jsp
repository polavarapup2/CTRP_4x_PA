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
            
              function loadTopDiv(value) {
                var caDsrId = $('caDsrId').value;
                var selectedRowIdentifier = $('selectedRowIdentifier').value;
                top.window.loadDiv(selectedRowIdentifier, caDsrId);
             }
        </script>
    </head>
    <body>
        <s:form id="cadsrLookup" name="cadsrLookup">
        <pa:failureMessage />
        <s:if test="hasActionErrors()">
            <div class="error_msg">
                <s:actionerror />
            </div>
        </s:if>
        <s:hidden id="selectedRowIdentifier" name="selectedRowIdentifier"/>
        <s:hidden id="caDsrId" name="caDsrId"/>
<s:if test="%{!markers.isEmpty()}">
    <s:set name="markers" value="markers" scope="request" />
    <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" name="markers" export="false">
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.permissibleValue" property="vmName" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.meaning" property="vmMeaning" headerClass="sortable" />
        <display:column escapeXml="false" titleKey="plannedMarker.lookup.synonym">
             <c:forEach var="item" items="${row.altNames}">
                  <c:out escapeXml="false" value="${item}"/>
             <br>
             </c:forEach>
        </display:column>
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.description" property="vmDescription" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.publicId" property="publicId" headerClass="sortable" />
    </display:table>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                 <li>
                  <s:a href="javascript:void(0)" cssClass="btn" onclick="loadTopDiv()">
                  <span class="btn_img">Proceed with the change</span>
                  </s:a>
                  <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                     <span class="btn_img"><span class="cancel">Cancel</span></span>
                  </s:a>
                  </li>
             </ul>
        </del>
   </div>
</s:if>
<s:if test="%{markers.isEmpty()}">
    <br></br>
    <br></br>
    <p align="center">No match found in caDSR with the Public ID entered.</p>
     <div class="actionsrow">
         <del class="btnwrapper">
               <ul class="btnrow">
                    <li>
                      <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                         <span class="btn_img">OK</span>
                      </s:a>
                    </li>
               </ul>
          </del>
       </div>
</s:if>
        </s:form>
    </body>
</html>