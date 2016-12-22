<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="interventions.details.title" /></title>
<s:head />

<pa:scientificAbstractorDisplayWhenCheckedOut>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery.tablednd.js"/>"></script>
    <script type="text/javascript">
	    jQuery(document).ready(function() {
	        
	        jQuery('#nav li').hover(function() {
	            //show its submenu
	            jQuery('ul', this).slideDown(100);
	
	        }, function() {
	            //hide its submenu
	            jQuery('ul', this).slideUp(100);
	        });
	
	    });
    
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
                    var ajaxReq = new Ajax.Request('trialInterventionsorder.action', {
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
    <style type="text/css">        
        tr.myDragClass td {
            color: yellow;
            background-color: #A8B8CE;
            font-weight: bold;
        }
    </style>
    </pa:scientificAbstractorDisplayWhenCheckedOut>
    
</head>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}
function handleEdit(rowId, type){
    document.interventionForm.selectedRowIdentifier.value = rowId;
    document.interventionForm.selectedType.value = type;
    document.interventionForm.action="trialInterventionsedit.action";
    document.interventionForm.submit();
}

function handleCreate(){
    document.interventionForm.action="trialInterventionscreate.action";
    document.interventionForm.submit();
}
</SCRIPT>

<body>
<h1><fmt:message key="interventions.details.title" /></h1>
<c:set var="topic" scope="request" value="abstractinterventions"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box"><pa:sucessMessage /><pa:failureMessage/> <s:if
    test="hasActionErrors()">
    <div class="error_msg"><s:actionerror /></div>
</s:if> 

  <div id="ajaxIndicator" class="info" style="display: none;">
        <img alt="Indicator" align="middle" src="../images/loading.gif"/>&nbsp;<fmt:message key="interventions.order.saving"/>
  </div>
  <div class="confirm_msg" style="display: none;" id="orderSaveConfirmation">
      <strong>Message.</strong>&nbsp;<fmt:message key="interventions.order.saved"/>
  </div>
  <div class="error_msg" style="display: none;" id="orderSaveError">
      <strong>Message.</strong>&nbsp;<fmt:message key="interventions.order.error"/>
  </div> 
  
<s:form name="interventionForm">
    <pa:studyUniqueToken/>
    <s:hidden name="selectedRowIdentifier"/>
    <s:hidden name="selectedType"/>
    <h2><fmt:message
        key="interventions.details.title" /></h2>
    <c:if test="${fn:length(requestScope.interventionsList) > 5}">   
    <div class="actionstoprow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
            <s:if test="%{interventionsList != null && !interventionsList.isEmpty()}">
                <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                <li><pa:toggleDeleteBtn/></li>
            </s:if>            
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
    </c:if>
    <table class="form">
        <tr>
            <td colspan="2"><s:hidden name="cbValue" />
            <s:set name="interventionsList" value="interventionsList" scope="request"/>
            <display:table name="interventionsList" id="row" class="data" sort="list" pagesize="200"
                    decorator="gov.nih.nci.pa.decorator.InterventionTableDecorator"
                    requestURI="trialInterventions.action" export="false">
                <display:column escapeXml="true" property="name" sortable="true" titleKey="interventions.name" headerClass="sortable nodnd"  />
                <display:column escapeXml="true" property="otherNames" sortable="true" titleKey="interventions.otherNames" headerClass="sortable nodnd" />
                <display:column escapeXml="true" property="description" sortable="true" titleKey="interventions.description" headerClass="sortable nodnd" />
                <display:column escapeXml="true" property="type" sortable="true" titleKey="interventions.type" headerClass="sortable nodnd"  />
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <display:column titleKey="interventions.edit" headerClass="centered nodnd" class="action nodnd">
                        <s:a href="javascript:void(0)" onclick="handleEdit(%{#attr.row.plannedActivityIdentifier},'%{#attr.row.type}')">
                            <img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16" />
                        </s:a>
                    </display:column>
                    <display:column titleKey="interventions.delete" headerClass="centered nodnd" class="action nodnd">
                        <s:checkbox name="objectsToDelete"  id="objectsToDelete_%{#attr.row.plannedActivityIdentifier}" fieldValue="%{#attr.row.plannedActivityIdentifier}" value="%{#attr.row.plannedActivityIdentifier in objectsToDelete}"/>
                        <label style="display: none;" for="objectsToDelete_${row.plannedActivityIdentifier}">Check this box to mark row for deletion.</label>
                    </display:column>
                </pa:scientificAbstractorDisplayWhenCheckedOut>
            </display:table>
           </td>
        </tr>
    </table>
    
    <pa:scientificAbstractorDisplayWhenCheckedOut>
	    <s:if test="interventionsList.size > 1">
	       <p class="info" align="center">
	           You can re-order interventions by dragging and dropping individual table rows.       
	       </p>       
	    </s:if> 
    </pa:scientificAbstractorDisplayWhenCheckedOut>
    
    <div class="actionsrow"><del class="btnwrapper">
    <ul class="btnrow">
        <pa:scientificAbstractorDisplayWhenCheckedOut>
            <li><a href="javascript:void(0)" class="btn" onclick="this.blur();handleCreate();"><span class="btn_img"><span class="add">Add </span></span></a></li>
            <s:if test="%{interventionsList != null && !interventionsList.isEmpty()}">
                <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected intervention(s) from the study. Cancel to abort.', 'trialInterventionsdelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                <li><pa:toggleDeleteBtn/></li>
            </s:if>            
            <li><ul id="nav">
                    <li class='root'><span class="btn_img">&nbsp;Manage
                            NCIt Terms &nabla;</span>
                        <ul>
                            <li class="action"><a
                                href="manageTermscreateIntervention.action?pageDiscriminator=intervention"> <fmt:message
                                        key="manageTerms.button.create" />
                            </a></li>
                            <li  class="action" ><a
                                href="manageTermssearchIntervention.action?searchStart=true&pageDiscriminator=intervention">
                                    <fmt:message key="manageTerms.button.import" />
                            </a></li>
                            <li  class="action"><a
                                href="http://ncitermform.nci.nih.gov/ncitermform/"
                                target="_blank"><fmt:message
                                        key="manageTerms.button.requestForm" /> </a></li>
                        </ul>
                    </li>
                </ul></li>
        </pa:scientificAbstractorDisplayWhenCheckedOut>
    </ul>
    </del></div>
</s:form></div>
</body>
</html>
