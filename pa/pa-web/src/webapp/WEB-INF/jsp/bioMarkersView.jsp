<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="plannedMarker.details.title" /></title>
<s:head />
<script type="text/javascript"
	src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
	<script type="text/javascript" language="javascript">
            function handleAction() {     
                 var extensionValue = document.forms[0].trialId.value;
                 var name = document.forms[0].markerName.value;  
                 document.forms[0].action="bioMarkerssearch.action";
                 document.forms[0].submit();
                 if (extensionValue == "" && name == "") {
                    document.forms[0].action="bioMarkersexecute.action";
                    document.forms[0].submit();
                 }
            }
            
            function accept(selectedId) {
            	var caDsrIdValue = jQuery('#caDsrId_'+selectedId).val();
                var updatedUrl ='/pa/protected/popupPlannedMarkeraccept.action?selectedRowIdentifier='+selectedId+'&caDsrId='+caDsrIdValue;
                showPopWin(updatedUrl, 950, 300, '', 'Marker Search in caDSR');
            }
            function termRequestForm(pId, markerName,markerId) {
            	var markerName1 = markerName.split("(").join("%28");
            	var markerName2 = markerName1.split(")").join("%29"); 
            	var replacedMarkerName = markerName2.split(";").join("%3B");
                var url = '/pa/protected/popupPlannedMarkersetupEmailRequest.action?fromNewRequestPage=true&nciIdentifier='+pId+'&name='+replacedMarkerName+'&selectedRowIdentifier='+markerId;
                showPopWin(url, 950, 400, '', 'Create Permissible Value Request');
            }
            
            function resetValues() {
                $("trialId").value="";
                $("markerName").value="";
            }
            
            function viewMarker(pId){
                document.forms[0].target = "marker";
                document.forms[0].action="plannedMarkerviewSelectedProtocolMarker.action?nciIdentifier="+pId;
                document.forms[0].submit();
           }
            function loadDiv(markerId, caDsrId) {
                window.top.hidePopWin(true);
                document.forms[0].action ='/pa/protected/bioMarkersaccept.action?selectedRowIdentifier='+markerId+'&caDsrId='+caDsrId;
                document.forms[0].submit();
            }
            function loadTopDiv() {
                window.top.hidePopWin(true);
                 document.forms[0].action="bioMarkersexecute.action";
                 document.forms[0].submit();
                 var div = $('pending');
                 div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';

            }
            function cadsrLookup(){
                var updatedUrl = 'pa/protected/popupPlannedMarker.action?showActionColumn=false';
                showPopWin(updatedUrl, 1000, 600, '', 'Marker Search in caDSR');
            }
   </script>
</head>
<body>
	<h1>
		<fmt:message key="plannedMarker.pending.markers.report" />
	</h1>
    <c:set var="topic" scope="request" value="biomarkers"/>
	<div id="pending" class="box">
		<s:if test="hasActionErrors()">
			<div class="error_msg">
				<s:actionerror />
			</div>
		</s:if>
		<s:form name="diseaseForm">
		 <h1><fmt:message key="plannedMarker.search.header"/></h1>
		<table  class="form">
		
            <tr>
                <td  scope="row" class="label">
                   <label for="trialId"> <fmt:message key="plannedMarker.protocolId"/></label>
                </td>
                <td>
                    <s:textfield id="trialId" name="trialId" maxlength="200" size="100" cssStyle="width:200px"  />
                </td>
            
                <td  scope="row" class="label">
                   <label for="markerName"> <fmt:message key="plannedMarker.markerName"/></label>
                </td>
                <td>
                    <s:textfield id="markerName" name="markerName" maxlength="200" size="100" cssStyle="width:200px"  />
                </td>
            </tr>
        </table>
        <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction();return false"><span class="btn_img"><span class="search">Search</span></span></s:a>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="cadsrLookup();"><span class="btn_img"><span class="search">caDSR Search</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
                <pa:sucessMessage/>
                 <pa:failureMessage/>
			 <table class="form">
			 <tr>
                    <td colspan="2">
                    <s:set name="plannedMarkerList" value="plannedMarkerList" scope="request" /> 
                    
			<display:table name="plannedMarkerList" htmlId="plannedMarkerTable" id="row"
							class="data" sort="list" pagesize="200"
							requestURI="bioMarkerssearch.action">
				<display:column sortable="true" titleKey="plannedMarker.protocolId" headerClass="sortable" style="width:17%">
				<a href="javascript:void(0)" onclick="viewMarker('${row.nciIdentifier}');">
				<s:property value="%{#attr.row.nciIdentifier}" /></a>
					&nbsp;  &nbsp;  &nbsp; 
					<s:if test="%{#attr.row.protocolDocument != '' }">
                          <s:url id="url" action="bioMarkerssaveFile"><s:param name="id" value="%{#attr.row.id}" />
                        <s:param name="selectedRowDocument" value="%{#attr.row.protocolDocumentID}"/></s:url>
                         <s:a href="%{url}">
                                  <img src="<c:url value='/images/document.gif'/>" alt="view" width="14" height="14" />
                          </s:a>
                          </s:if>
				</display:column>
							<display:column escapeXml="true" property="name" sortable="true" 
								titleKey="plannedMarker.markerName" headerClass="sortable" />
		<s:if test="%{#attr.row.dateEmailSent == null}">
                <display:column titleKey="plannedMarker.termRequest" headerClass="centered"
                                class="action" style="width:25%">
                                <del class="btnwrapper"">
                                    <ul class="btnrow">
                                        <li>
                                        <s:a  escapeHtml="true"  cssClass="btn" href="javascript:void(0)" id="request" onclick="termRequestForm('%{#attr.row.nciIdentifier}','%{@org.apache.commons.lang.StringEscapeUtils@escapeJavaScript(#attr.row.name)}','%{#attr.row.id}');">
                                          <span class="btn_img">Term Request Form</span>
                                         </s:a>
                                        </li>
                                    </ul>
                                </del>
                 </display:column>
        </s:if>
       <s:if test="%{#attr.row.dateEmailSent != null}">
                <display:column sortable="true" titleKey="plannedMarker.termRequest" style="width:25%">
                <s:date name="%{#attr.row.dateEmailSent}"  format="yyyy-MM-dd HH:mm:ss"/>
                </display:column>
         </s:if>
               <display:column titleKey="plannedMarker.caDSR.id" headerClass="sortable" >
                  <label><s:textfield id="caDsrId_%{#attr.row.id}"   name="caDsrId_%{#attr.row.id}" maxlength="200" size="100" cssStyle="width:200px" /></label>
               </display:column>
							<display:column titleKey="plannedMarker.action"
								headerClass="centered" class="action">
								<del class="btnwrapper">
									<ul class="btnrow">
										<li>
                                        <s:a cssClass="btn" href="javascript:void(0)" id="acceptid" onclick="accept('%{#attr.row.id}');">
                                          <span class="btn_img">Accept</span>
                                         </s:a>
										</li>
									</ul>
								</del>
							</display:column>
						</display:table>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>
