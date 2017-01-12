<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<c:url value="/protected/popupCadsr.action" var="lookupUrl" />
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
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
    
        <c:url value="/protected/popupTypeInterventiontype.action?className=UnitOfMeasurement&divName=loadUOMDetails" var="lookupUOMUrl" />
        <script language="javascript" type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
                var len = document.eligibilityCriteraiAdd.group3.length;
                var selElement;
                for (i = 0; i < len; i++)
                 {
                 if(document.eligibilityCriteraiAdd.group3[i].checked){
                   selElement = document.eligibilityCriteraiAdd.group3[i];
                   }
                 }  
                activate(selElement);
                //activateMax();    
            }

            function lookup() {
                showPopWin('${lookupUrl}', 900, 400, '', 'caDSR Lookup');
            } 
            
            function handleAction() {
                var page = document.forms[0].page.value;
                var form = document.forms["eligibilityCriteraiAdd"];
                if (page == "Edit") {
                    form.action="eligibilityCriteriaupdate.action";
                } else {
                    form.action="eligibilityCriteriacreate.action";
                } 
                form.submit();
            } 

            function activate(selected) {
                var input = "webDTO.textDescription";
                var form = document.forms["eligibilityCriteraiAdd"];
                var inputElement = form.elements[input];
                var criterionName = "webDTO.criterionName";
                var cnElement = document.forms["eligibilityCriteraiAdd"].elements[criterionName];
                var operator = "webDTO.operator";
                var opElement = document.forms["eligibilityCriteraiAdd"].elements[operator];
                var min = "webDTO.valueIntegerMin";
                var minElement = "";
                var max = "webDTO.valueIntegerMax";
                var maxElement = "";
                var txt = "webDTO.valueText";
                var txtElement = "";

                if (form.elements[min] != 'undefined' && form.elements[min] != null) {
                    minElement = form.elements[min];
                }
                if (form.elements[max] != 'undefined' && form.elements[max] != null) {
                    maxElement = form.elements[max];
                }
                if (form.elements[txt] !='undefined' &&  form.elements[txt] != null) {
                    txtElement = form.elements[txt];
                }
                var unit="webDTO.unit";
                var uElement = form.elements[unit];
                if (selected.value == 'Unstructured')
                    {
                	 	//Uncomment this if we decide to display Build Criterion Description fields again 
                        /*cnElement.disabled = true;
                        opElement.disabled = true;
                        if (minElement != "") {
                         minElement.disabled = true;
                        } 
                        if (maxElement != "") {
                         maxElement.disabled = true;
                        } 
                        if (txtElement != "") {
                         txtElement.disabled = true;
                        } 
                        if (uElement != "") {
                         uElement.disabled = true;
                        }
                        $("loadUOMDetails").disabled=true;
                        $("criteriaNameLookup").disabled=true;
                        $("generateTextButton").disabled=true;*/
                        inputElement.disabled=false;
                    } else {
                        cnElement.disabled = false;
                        opElement.disabled = false;
                        if (minElement != 'undefined'|| minElement != null) {
                            minElement.disabled = false;
                        } 
                        if (maxElement != 'undefined'|| maxElement != null) {
                           maxElement.disabled = false;
                        } 
                        if (txtElement != 'undefined'|| txtElement != null) {
                            txtElement.disabled = false;
                        } 
                        if (uElement != 'undefined'|| uElement != null) {
                           uElement.disabled = false;
                        } 
                        inputElement.disabled=false;
                        $("loadUOMDetails").disabled=false;
                        $("criteriaNameLookup").disabled=false;
                        $("generateTextButton").disabled=false;
                    }   
                }

            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn"); 
            }

            function lookupUOM() {
                showPopWin('${lookupUOMUrl}', 900, 400, '', 'Unit Of Measure');
            }

            function loadDetails(id, divName,className) {
                var url = '/pa/protected/ajaxEligibilityCriteriadisplaySelectedType.action';
                var params = {
                    className: className,
                    divName: divName,
                    id: id
                };
                var div = $(divName);   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif" alt="loading..."/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
               
            function handleGenerateCriteriaText() {
                document.forms[0].action="eligibilityCriteriagenerate.action";
                document.forms[0].submit();
            }   
            
            function cancel() {
            	document.eligibilityCriteraiAdd.action="eligibilityCriteriaquery.action";
            	document.eligibilityCriteraiAdd.submit();
            }            
            
            function loadDiv(deid) {
                 window.top.hidePopWin(true);
                 var url = '/pa/protected/ajaxEligibilityCriteriadisplaycde.action';
                 var params = { cdeid: deid };
                 var div = $('eligibility.build.criterion');   
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif" alt="loading..."/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, params);
            }
             
            function activateMax() {
                var operate = "webDTO.operator";
                var operateElement = document.forms["eligibilityCriteraiAdd"].elements[operate];
                var intMaxName = "webDTO.valueIntegerMax";
                var intMaxElement = document.forms["eligibilityCriteraiAdd"].elements[intMaxName];
                intMaxElement.disabled = operateElement.value != 'in';
            }  
            
        </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="abstracteligibility"/>
        <h1>
            <c:choose>
                <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'NonInterventionalStudyProtocol'}">
                    <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
                </c:when>
                <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise>
            </c:choose>
        </h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form id="eligibilityCriteraiAdd" name="eligibilityCriteraiAdd">
                <s:token/>
                <s:actionerror/>
                <pa:studyUniqueToken/>
                <h2><fmt:message key="isdesign.eligibilitycriteria.subtitle"/></h2>
                <s:hidden name="page"/>
                <s:hidden name="id"/>
                <table class="form">
                    <tr>
                        <td scope="row"  class="label"><label for="webDTO.inclusionIndicator">
                            <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriatype"/></label>
                        </td>
                        <td class="value">
                            <s:select id="webDTO.inclusionIndicator" name="webDTO.inclusionIndicator" list="#{' ':' ', 'Exclusion':'Exclusion', 'Inclusion':'Inclusion'}" cssStyle="width:106px"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>webDTO.inclusionIndicator</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row"  class="label"><label for="group3">
                            <fmt:message key="isdesign.eligibilitycriteria.structuredOrUnstructured"/><span class="required">*</span>:</label>
                        </td>
                        <td class="value">                            
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  disabled onclick='activate(this)'/>Structured<br/>
                            <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" checked="checked" onclick='activate(this)'/>Unstructured
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>webDTO.structuredType</s:param>
                               </s:fielderror>
                            </span>                      
                            <s:hidden name="webDTO.displayOrder" />
                            <s:hidden name="webDTO.id" />

                        </td>
                    </tr>                               
                </table>
                <!-- Adding eligibility description field here. -->
                <table class="form">
                	<tr>
                	   <td/>
                	   <td class="info" colspan="2"><fmt:message key="isdesign.eligibilitycriteria.description"/></td>
                	</tr>
                	<tr>
                		<span class="formErrorMsg"> 
                			<s:fielderror>
                			<s:param>webDTO.mandatory</s:param>
                			</s:fielderror>                            
                		</span>
                		<td scope="row"  class="label"><label for="webDTO.textDescription">
                		    <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/><span class="required">*</span></label>
                		</td>
                		<td class="value">
                			<s:textarea  id="webDTO.textDescription" name="webDTO.textDescription" rows="6" cssStyle="width:600px" onblur='activate();' 
                				maxlength="5000" cssClass="charcounter"/>
                			<span class="formErrorMsg"> 
                				<s:fielderror>
                					<s:param>webDTO.TextDescription</s:param>
                               </s:fielderror>                            
                           </span>
                       </td>                       
                   </tr>                     
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                            <li><s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>                
                        </ul>   
                    </del>
                </div> 
            </s:form>
        </div>
    </body>
</html>
