<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>
	<c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose>
</title>
	<s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery.tablednd.js"/>"></script>
    <pa:scientificAbstractorDisplayWhenCheckedOut>
    <script type="text/javascript">
         Event.observe(window, "load", function() {
            jQuery('#row').tableDnD({
                onDragClass: "myDragClass",
                onDrop: function(table, draggedRow) {                	
                    var rows = table.tBodies[0].rows;
                    var orderString = '';
                    for (var i=0; i<rows.length; i++) {
                        var row = rows[i]; 
                        var outcomeID = row.id.substring(6);
                        orderString = orderString + outcomeID + ';';
                        $(row).className = (i % 2 == 0?'odd':'even');
                    }
                    $('ajaxIndicator').show();
                    $('orderSaveConfirmation').hide(); 
                    $('orderSaveError').hide(); 
                    var ajaxReq = new Ajax.Request('interventionalStudyDesignoutcomeOrder.action', {
                        method: 'post',
                        parameters: 'orderString='+orderString,
                        onSuccess: function(transport) {
                        	$('ajaxIndicator').hide();
                        	$('orderSaveConfirmation').show();
                        },
                        onFailure: function(transport) {
                        	$('ajaxIndicator').hide();   
                        	$('orderSaveError').show();
                        },
                        onException: function(requesterObj, exceptionObj) {
                            ajaxReq.options.onFailure(null);
                        },
                        on0: function(transport) {
                            ajaxReq.options.onFailure(transport);
                        }
                    });                    
                }
            });            
        });
    </script>
    </pa:scientificAbstractorDisplayWhenCheckedOut>
    <style type="text/css">        
        tr.myDragClass td {
            color: yellow;
            background-color: #A8B8CE;
            font-weight: bold;
        }
    </style>
</head>

<SCRIPT LANGUAGE="JavaScript">
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        // there are no onload functions to call for this jsp
        // leave this function to prevent 'error on page'
    }
    
</SCRIPT>

<body>
<c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
     <c:set var="topic" scope="request" value="abstractoutcomenoninterventional"/>
     </c:when>
     <c:otherwise><c:set var="topic" scope="request" value="abstractoutcome"/></c:otherwise>
</c:choose>
 <h1><c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
     <fmt:message key="osdesign.outcome.title"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.outcome.title"/></c:otherwise></c:choose></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">
  <pa:sucessMessage/>
  <pa:failureMessage/>
  
  <div id="ajaxIndicator" class="info" style="display: none;">
        <img alt="Indicator" align="middle" src="../images/loading.gif"/>&nbsp;<fmt:message key="osdesign.outcome.order.saving"/>
  </div>
  <div class="confirm_msg" style="display: none;" id="orderSaveConfirmation">
      <strong>Message.</strong>&nbsp;<fmt:message key="osdesign.outcome.order.saved"/>
  </div>
  <div class="error_msg" style="display: none;" id="orderSaveError">
      <strong>Message.</strong>&nbsp;<fmt:message key="osdesign.outcome.order.error"/>
  </div>    
    <s:form>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2>Outcome Measures</h2>
    <c:if test="${fn:length(requestScope.outcomeList) > 5}">
    <div class="actionstoprow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:scientificAbstractorDisplayWhenCheckedOut>
                       <li><s:a href="interventionalStudyDesignoutcomeinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{outcomeList != null && !outcomeList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected outcome measure(s) from the study. Cancel to abort.', 'interventionalStudyDesignoutcomedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected outcome measure(s) from the study. Cancel to abort.', 'interventionalStudyDesignoutcomedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>                    
                    </pa:scientificAbstractorDisplayWhenCheckedOut>
                </ul>
            </del>
        </div>
    </c:if>
    <s:if test="outcomeList != null">
    <s:hidden name="page" />
    <s:hidden name="id" />
	<s:set name="outcomeList" value="outcomeList" scope="request"/>
	
	<display:table name="outcomeList" id="row" class="data" sort="list"  pagesize="200"
	   decorator="gov.nih.nci.pa.decorator.OutcomeMeasureTableDecorator" 
	   requestURI="interventionalStudyDesignoutcomeQuery.action" export="false">
	    <display:column escapeXml="true" titleKey="osdesign.outcome.type" property="outcomeMeasure.typeCode" sortable="true" headerClass="sortable nodnd"/>
        <display:column escapeXml="true" titleKey="osdesign.outcome.name" property="outcomeMeasure.name" sortable="true" headerClass="sortable nodnd" />
	    <display:column escapeXml="true" titleKey="osdesign.outcome.timeFrame" property="outcomeMeasure.timeFrame"  sortable="true" headerClass="sortable nodnd" />
        <display:column escapeXml="true" titleKey="osdesign.outcome.description" property="outcomeMeasure.description"  sortable="true" headerClass="sortable nodnd" />
	    <display:column escapeXml="true" titleKey="osdesign.outcome.safety" sortable="true" headerClass="sortable nodnd">
            <pa:displayBoolean value="${row.outcomeMeasure.safetyIndicator}"/>
        </display:column>
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <display:column title="Edit" class="action nodnd" headerClass="nodnd">
                <s:url id="url" action="interventionalStudyDesignoutcomeedit"><s:param name="id" value="%{#attr.row.outcomeMeasure.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
                <s:a href="%{url}"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
            </display:column>
            <display:column title="Copy" class="action nodnd" headerClass="nodnd">
                <s:url id="url" action="interventionalStudyDesignoutcomeCopy"><s:param name="id" value="%{#attr.row.outcomeMeasure.id}" /> <s:param name="page" value="%{'Add'}"/></s:url>
                <s:a href="%{url}"><img src="<c:url value='/images/ico_copy.gif'/>" alt="Copy" width="16" height="16"/></s:a>
            </display:column>            
            <display:column title="Delete" class="action nodnd" headerClass="nodnd">
                <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}" fieldValue="%{#attr.row.outcomeMeasure.id}" value="%{#attr.row.outcomeMeasure.id in objectsToDelete}"/>
                 <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
            </display:column>
        </pa:scientificAbstractorDisplayWhenCheckedOut>        
	</display:table>
  </s:if>
  
    <pa:scientificAbstractorDisplayWhenCheckedOut>  
	    <s:if test="outcomeList.size > 1">
	       <p class="info" align="center">
	           You can re-order outcomes by dragging and dropping individual table rows.       
	       </p>       
	    </s:if>  
    </pa:scientificAbstractorDisplayWhenCheckedOut>
  
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
                    <pa:scientificAbstractorDisplayWhenCheckedOut>
					   <li><s:a href="interventionalStudyDesignoutcomeinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{outcomeList != null && !outcomeList.isEmpty()}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected outcome measure(s) from the study. Cancel to abort.', 'interventionalStudyDesignoutcomedelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected outcome measure(s) from the study. Cancel to abort.', 'interventionalStudyDesignoutcomedelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>					   
                    </pa:scientificAbstractorDisplayWhenCheckedOut>
				</ul>
			</del>
		</div>
  	</s:form>
   </div>
 </body>
 </html>