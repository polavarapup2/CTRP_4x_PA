<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
    <c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise> 
     </c:choose>
     </title>   
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery-1.11.1.min.js"/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery.tablednd.js"/>"></script>
    <script type="text/javascript">
		$(document).ready(function() {
		    jQuery('#table-1').tableDnD({
		    	onDragClass: "myDragClass",
		    	onDrop: function(table, row){
		    		var rows = table.tBodies[0].rows;		    		
		    		for (var i=1; i<rows.length; i++) {
		    			var inputId = '#eligibilityList_' + rows[i].id + '_displayOrder';
		                jQuery(inputId).val(i);
		            }
		    		 $('ajaxIndicator').show();
		    		 handleAction();
			    }
	    	});
		    jQuery('#table-1 tr:even').addClass('alt');
		});
	</script>
	<style type="text/css">
		div#page {
			background-color: white;
			padding: 1em;
		}
		
		.category td {
			background-color: #E4EBF3;
		}
		
		.tDnD_whileDrag {
			background-color: #eee;
		}
		
		tr.alt td {
			background-color: #ecf6fc;
		}
		
		tr.myDragClass td {
			color: yellow;
			background-color: #A8B8CE;
		}
		
		td.dragHandle {
			
		}
		
		td.showDragHandle {
			background-image: url(images/updown2.gif);
			background-repeat: no-repeat;
			background-position: center center;
			cursor: move;
		}
	</style>
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();         
}

function handleAction(){
    document.forms[0].action="eligibilityCriteriasave.action";
    document.forms[0].submit(); 
} 
function handleReOrderAction(){
    document.forms[0].action="eligibilityCriteriareOrder.action";
    document.forms[0].submit(); 
} 



function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}

</SCRIPT>
<body>
<c:set var="asterisk" value="${(!sessionScope.trialSummary.proprietaryTrial)||(not empty fieldErrors)?'*':''}" scope="request"/>
 <c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
     <c:set var="topic" scope="request" value="abstracteligibilitynoninterventional"/>
     </c:when>
     <c:otherwise><c:set var="topic" scope="request" value="abstracteligibility"/></c:otherwise>
</c:choose>
 <h1>
 <c:choose>
     <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
     <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
     </c:when>
     <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise></c:choose> </h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <s:url id="cancelUrl" namespace="/protected" action="eligibilityCriteriaquery"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
   <div id="ajaxIndicator" class="info" style="display: none;">
        <img alt="Indicator" align="middle" src="../images/loading.gif"/>&nbsp;<fmt:message key="osdesign.outcome.order.saving"/>
   </div>
    <s:form>
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>    
    <h2><fmt:message key="isdesign.eligibilitycriteria.title"/></h2>
    <c:if test="${fn:length(requestScope.eligibilityList) > 5}">
    <div class="actionstoprow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                  <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                    </li>
                    <s:if test="list != null">
                        <li><s:a href="eligibilityCriteriainput.action" cssClass="btn"><span class="btn_img"><span class="add">Add Another Criterion</span></span></s:a></li>
                    </s:if>
                    <s:if test="%{eligibilityList != null && !eligibilityList.isEmpty()}">
                        <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected criteria from the study. Cancel to abort.', 'eligibilityCriteriadelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected criteria from the study. Cancel to abort.', 'eligibilityCriteriadelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete Other Criterion</span></span></s:a></li>
                        <li><pa:toggleDeleteBtn/></li>
                    </s:if> 
                                       
                </pa:scientificAbstractorDisplayWhenCheckedOut>
            </ul>   
        </del>
    </div>
    </c:if> 
    <table class="form">
    <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
	    <tr>
	       <td colspan="5" align="center">
	           <span class="info" >
		           For abbreviated trials Eligibility Criteria is optional. However, if provided, the criteria must be
		           complete.<br/><br/>
	           </span>
	       </td>
	    </tr>
    </c:if>
    <tr>
        <td scope="row"  class="label"><label for="acceptHealthy">
            <fmt:message key="isdesign.eligibilitycriteria.ahv"/><span class="required">${asterisk}</span></label>
         </td> <td class="value">   
            <s:select id="acceptHealthy" name="acceptHealthyVolunteersIndicator" list="#{'':'','false':'No', 'true':'Yes'}" />
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>acceptHealthyVolunteersIndicator</s:param>
                   </s:fielderror>                            
             </span>
        </td>
        <td style="width: 5%;">&nbsp;</td>
        <c:if test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
         <td scope="row"  class="label"><label>
                <fmt:message key="osdesign.eligibilitycriteria.samplingMethod"/><span class="required">${asterisk}</span></label>
            </td>   
               <s:set name="samplingMethodValues" value="@gov.nih.nci.pa.enums.SamplingMethodCode@getDisplayNames()" />
              <td class="value">
                <s:select headerKey="" headerValue="" 
                        name="samplingMethodCode" 
                        list="#samplingMethodValues"  
                        cssStyle="width:185px" />
                <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>samplingMethodCode</s:param>
                       </s:fielderror>                            
                 </span>
            </td>        
            </c:if>
    </tr>
    <tr>
         <td scope="row" class="label">
         <label for="gender">
                <fmt:message key="isdesign.eligibilitycriteria.eligibleGender"/><span class="required">${asterisk}</span>
         </label>
        </td> 
        <s:set name="genderValues" value="@gov.nih.nci.pa.enums.EligibleGenderCode@getDisplayNames()" />
       <td class="value">   
            <s:select id="gender" headerKey="" headerValue="" 
                    name="eligibleGenderCode" 
                    list="#genderValues"  
                    cssStyle="width:75px" /> 
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>eligibleGenderCode</s:param>
                   </s:fielderror>                            
             </span>
             <s:hidden name="eligibleGenderCodeId"/>
          </td>
          <td></td>
          <c:if test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
        <td scope="row"  class="label">
            <label>
                <fmt:message key="osdesign.eligibilitycriteria.trialPopulationDescription"/><span class="required">${asterisk}</span>
             </label>
             </td> <td class="value">               
                <s:textarea name="studyPopulationDescription" rows="3" cssStyle="width:250px" maxlength="800" cssClass="charcounter" />
                <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>studyPopulationDescription</s:param>
                       </s:fielderror>                            
                 </span>
            </td>            
            </c:if>
    </tr> 
    <tr>
         <td scope="row" class="label">
         <label for="minage">
                <fmt:message key="isdesign.eligibilitycriteria.minimumAge"/><span class="required">${asterisk}</span>
         </label>
         </td> <td class="value" colspan="1">    
                <s:textfield id="minage" name="minimumValue" maxlength="12" cssStyle="width:85px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>minimumValue</s:param>
                   </s:fielderror>                            
                </span> 
          </td>
          <td></td>
           <td scope="row" class="label" >
         <label for="minageunit">
                <fmt:message key="isdesign.eligibilitycriteria.unit"/><span class="required">${asterisk}</span>
         </label>
         </td> 
        <s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
       <td class="value" colspan="1">   
            <s:select id="minageunit" headerKey="" headerValue="" 
                    name="minValueUnit" 
                    list="#unitsValues"  
                    cssStyle="width:76px" /> 
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>minValueUnit</s:param>
                   </s:fielderror>                            
             </span></td>
          </tr>
          <tr>
          <td scope="row" class="label" >
         <label for="maxage">
                <fmt:message key="isdesign.eligibilitycriteria.maximumAge"/><span class="required">${asterisk}</span>
         </label>
         </td> <td class="value" colspan="1">   
                <s:textfield id="maxage" name="maximumValue" maxlength="12" cssStyle="width:85px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>maximumValue</s:param>
                   </s:fielderror>                            
                </span> 
          </td>
          <td></td>
         <td scope="row" class="label" >
         <label for="maxageunit">
                <fmt:message key="isdesign.eligibilitycriteria.unit"/><span class="required">${asterisk}</span>
         </label>
         </td> 
        <s:set name="unitsValues" value="@gov.nih.nci.pa.enums.UnitsCode@getDisplayNames()" />
       <td class="value" colspan="1">   
            <s:select id="maxageunit" headerKey="" headerValue="" 
                    name="maxValueUnit" 
                    list="#unitsValues"  
                    cssStyle="width:76px" /> 
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>maxValueUnit</s:param>
                   </s:fielderror>                            
             </span>
             <s:hidden name="valueId"/>
          </td>           
    </tr> 
    <tr> 
        <td/>
        <td class="info" colspan="2">Write 0 if no min age is indicated</td>             
        <td class="info" colspan="2">Write 999 if no max age is indicated and select 'Years' as Unit</td>
        
    </tr>                                            
    </table>
    <h2><fmt:message key="eligibilitycriteria.other" /></h2>
    <s:if test="eligibilityList != null" >
      <s:hidden name="page" />
      <s:hidden name="id" /> 
       <table class="data">
                <tr><td>
                 <table class="form" id="table-1">
                 <tbody> 
                 <tr class="nodrag nodrop">
                 <th><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriatype"/></th>
                 <th><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/></th>
                  <th><fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/></th>
                  <th><fmt:message key="isdesign.eligibilitycriteria.operator"/></th>
                  <th><fmt:message key="isdesign.eligibilitycriteria.value"/></th>
                  <th><fmt:message key="isdesign.eligibilitycriteria.unit"/></th>
                  
                  <th>Edit</th>
                  <th>Delete</th>
                  </tr>
      
                      <s:iterator value="eligibilityList" id="eligibilityList" status="stat" >
                      <tr id="<s:property value='#stat.index'/>">
                       <td class="tdBoxed">
                       <label for="eligibilityList[${stat.index}].inclusionIndicator" style="display: none;">Inclusion</label>
                       <s:textfield id="eligibilityList[%{#stat.index}].inclusionIndicator" name="eligibilityList[%{#stat.index}].inclusionIndicator" value="%{inclusionIndicator}" cssStyle="width:55px;border: 1px solid #FFFFFF" readonly="true"/>
                       </td>
                       <td class="tdBoxed">
                        <label for="eligibilityList[${stat.index}].textDescription" style="display: none;">TextDescription</label>
                        <s:textarea id="eligibilityList[%{#stat.index}].textDescription"  name="eligibilityList[%{#stat.index}].textDescription" value="%{textDescription}" cssStyle="width:250px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
                       </td>
                       <td class="tdBoxed">
                        <label for="eligibilityList[${stat.index}].criterionName" style="display: none;">CriterionName</label>
                        <s:textarea id="eligibilityList[%{#stat.index}].criterionName" name="eligibilityList[%{#stat.index}].criterionName" value="%{criterionName}" cssStyle="width:250px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
                       </td>
                       <td class="tdBoxed">
                        <label for="eligibilityList[${stat.index}].operator" style="display: none;">Operator</label>
                        <s:textfield id="eligibilityList[%{#stat.index}].operator" name="eligibilityList[%{#stat.index}].operator" value="%{operator}" cssStyle="width:40px;border: 1px solid #FFFFFF" readonly="true" rows="2"/>
                       </td>
                       <td class="tdBoxed">
                        <s:if test="%{eligibilityList[#stat.index].valueText != null}">
                        <label for="eligibilityList[${stat.index}].valueText" style="display: none;">ValueText</label>
                       <s:textfield id="eligibilityList[%{#stat.index}].valueText" name="eligibilityList[%{#stat.index}].valueText" value="%{valueText}" cssStyle="width:50px;border: 1px solid #FFFFFF" readonly="true"/>
                       </s:if>
                       <s:elseif test="%{eligibilityList[#stat.index].valueIntegerMin != null || eligibilityList[#stat.index].valueIntegerMax != null}">
                        <label for="eligibilityList[${stat.index}].valueIntegerMin" style="display: none;">ValueIntegerMin</label>
                        <s:textfield id="eligibilityList[%{#stat.index}].valueIntegerMin" name="eligibilityList[%{#stat.index}].valueIntegerMin" value="%{valueIntegerMin}" cssStyle="width:35px;border: 1px solid #FFFFFF" readonly="true"/> 
                        <s:if test="%{eligibilityList[#stat.index].valueIntegerMax != null}">
                        <label for="eligibilityList[${stat.index}].valueIntegerMax" style="display: none;">ValueIntegerMax</label>
                        -<s:textfield id="eligibilityList[%{#stat.index}].valueIntegerMax" name="eligibilityList[%{#stat.index}].valueIntegerMax" value="%{valueIntegerMax}" cssStyle="width:35px;border: 1px solid #FFFFFF" readonly="true"/>
                        </s:if>
                       </s:elseif>
                       </td>
                       <td class="tdBoxed">
                       		<label for="eligibilityList[${stat.index}].unit" style="display: none;">Unit</label>
                        	<s:textfield id="eligibilityList[%{#stat.index}].unit" name="eligibilityList[%{#stat.index}].unit" value="%{unit}" cssStyle="width:55px;border: 1px solid #FFFFFF" readonly="true"/>
                        	<s:hidden  name="eligibilityList[%{#stat.index}].id" value="%{id}" />
                        	<s:hidden  name="eligibilityList[%{#stat.index}].structuredType" value="%{structuredType}" />
                        	<s:hidden  id="eligibilityList_%{#stat.index}_displayOrder" name="eligibilityList[%{#stat.index}].displayOrder" value="%{displayOrder}" cssStyle="width:50px" />                       
                       </td>                       
                         <td class="nodnd">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                                <s:url id="url" action="eligibilityCriteriaedit">
                                    <s:param name="id" value="%{id}" />
                                    <s:param name="page" value="%{'Edit'}"/>
                                </s:url>
                                <s:a href="%{url}"><img src="<c:url value="/images/ico_edit.gif"/>" alt="Edit" width="16" height="16"/></s:a>
                            </pa:scientificAbstractorDisplayWhenCheckedOut>
                         </td>
                         <td class="nodnd">
                            <pa:scientificAbstractorDisplayWhenCheckedOut>
                            	<label for="delete${stat.index}" style="display: none;">Delete</label>
                                <s:checkbox id="delete%{#stat.index}" name="objectsToDelete" fieldValue="%{id}" value="%{id in objectsToDelete}"/>                            
                            </pa:scientificAbstractorDisplayWhenCheckedOut>   
                         </td>
                     </tr>
                    </s:iterator>
            </tbody>   
              
           </table>
            <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>reOrder</s:param>
                       </s:fielderror>                            
                 </span>
          
           </td></tr></table>
       </s:if>
   
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <pa:scientificAbstractorDisplayWhenCheckedOut>
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                  <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                    </li>
                    <s:if test="list != null">
                        <li><s:a href="eligibilityCriteriainput.action" cssClass="btn"><span class="btn_img"><span class="add">Add Another Criterion</span></span></s:a></li>
                    </s:if>
                    <s:if test="%{eligibilityList != null && !eligibilityList.isEmpty()}">
                        <li><s:a href="javascript:void(0);" onclick="handleMultiDelete('Click OK to remove selected criteria from the study. Cancel to abort.', 'eligibilityCriteriadelete.action');" onkeypress="handleMultiDelete('Click OK to remove selected criteria from the study. Cancel to abort.', 'eligibilityCriteriadelete.action');" cssClass="btn"><span class="btn_img"><span class="delete">Delete Other Criterion</span></span></s:a></li>
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
