<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="trialIndide.addtitle"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    
        <script type="text/javascript" language="javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();        
            }
           
            function checkCode() {
                var input="studyIndldeWebDTO.nihInstHolder";
                var inputElement = document.forms[0].elements[input];
                if (inputElement.options[inputElement.selectedIndex].value == "NCI-National Cancer Institute") {
                    document.getElementById('programcodenciid').style.display = '';
                    document.getElementById('programcodenihid').style.display = 'none';
                } else {
                    document.getElementById('programcodenciid').style.display = 'none';
                    document.getElementById('programcodenihid').style.display = '';
                }
            }
            
            function handleAction() {
                var page = document.forms[0].page.value;
                if (page == "Edit") {
                    document.forms[0].action="trialIndideupdate.action";
                    document.forms[0].submit(); 
                } else {
                    document.forms[0].action="trialIndidecreate.action";
                    document.forms[0].submit();   
                } 
            } 
            
            function setProgramCodes(ref) {    
                if (ref.value == 'NCI') {
                    document.getElementById('programcodenciid').style.display = '';
                    document.getElementById('programcodenihid').style.display = 'none';
                    document.getElementById('programcodeid').style.display = 'none';
                    $('programcoderow').show();
                } else if (ref.value == 'NIH') {
                    document.getElementById('programcodenciid').style.display = 'none';
                    document.getElementById('programcodenihid').style.display = '';
                    document.getElementById('programcodeid').style.display = 'none';
                    $('programcoderow').show();
                } else {
                    document.getElementById('programcodenihid').style.display = 'none';
                    document.getElementById('programcodenciid').style.display = 'none';
                    document.getElementById('programcodeid').style.display = '';
                    $('programcoderow').hide();
                }
            }
            
            function removeAllOptions(selectbox) {
                for(var i = selectbox.options.length-1; i >= 0; i--){
                    selectbox.remove(i);
                }
            }
            
            function addOption(selectbox, value, text ) {
                var optn = document.createElement("OPTION");
                optn.text = text;
                optn.value = value;
                selectbox.options.add(optn);
            }    
            function SelectSubCat(i) {
            	if (!i.checked) {
            		return;
            	}
                removeAllOptions(document.getElementById('SubCat'));
                addOption(document.getElementById('SubCat'), "", "-Select-", "");    
                if (i.value == 'IND') {
                    addOption(document.getElementById('SubCat'),"CDER", "CDER");
                    addOption(document.getElementById('SubCat'),"CBER", "CBER");
                }
                if (i.value == 'IDE') {
                    addOption(document.getElementById('SubCat'),"CDRH", "CDRH");
                    addOption(document.getElementById('SubCat'),"CBER", "CBER");
                }
            }
            
            function selectGrantor(grantor) {
            	if (grantor!='') {
            	   var list = $('SubCat');
            	   Form.Element.setValue(list, grantor);
            	}
            }
            
            function handleExpandedAccessIndicator() {
            	if ($('group4true').checked) {
            		$('expandedStatus').disabled = false;            		            		
            	}
                if ($('group4false').checked) {
                	$('expandedStatus').value = '';
                	$('expandedStatus').disabled = true;           
                }
                if (!($('group4true').checked || $('group4false').checked)) {
                	$('group4false').checked = true;
                }
            }
            
            Event.observe(window,"load", function(e) {
            	SelectSubCat($('group3IND'));	
            	SelectSubCat($('group3IDE'));
            	selectGrantor('<s:property value="studyIndldeWebDTO.grantor"/>');
            	setProgramCodes($('holderType'));
            	handleExpandedAccessIndicator();
            });
            
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
            <c:set var="topic" scope="request" value="reviewind"/>
        </c:if>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
            <c:set var="topic" scope="request" value="abstractind"/>
        </c:if>
        <h1><fmt:message key="trialIndide.title" /></h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">  
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form>
                <s:token/>
                <s:actionerror/>
                <pa:studyUniqueToken/>
                <h2><fmt:message key="trialIndide.addtitle" /></h2>
                <s:hidden name="page" />
                <s:hidden name="cbValue" />
                <s:set name="phaseCodeValuesNIH" value="@gov.nih.nci.pa.enums.NihInstituteCode@getDisplayNames()" />
                <s:set name="phaseCodeValuesNCI" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                <s:set name="expandedAccessStatusCodeValues" value="@gov.nih.nci.pa.enums.ExpandedAccessStatusCode@getDisplayNames()" />
                <table class="form">   
                    <tr>
                        <td scope="row"  class="label">
                            <label for="group3IND">
                                <fmt:message key="trialIndide.indldeType"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">  
                            <s:radio name="studyIndldeWebDTO.indldeType" id="group3" list="#{'IND':'IND', 'IDE':'IDE'}" onclick="SelectSubCat(this);"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.indldeType</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row"  class="label">
                            <label for="indidenumber">
                                <fmt:message key="trialIndide.indideNumber"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:textfield id="indidenumber" name="studyIndldeWebDTO.indldeNumber" size="10"/>                             
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.indldeNumber</s:param>
                               </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="SubCat">
                                <fmt:message key="trialIndide.grantor"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <select id="SubCat" name="studyIndldeWebDTO.grantor" style="width:150px">
                                <option value="">--Select--</option>
                            </select>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.grantor</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>    
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="holderType">
                                <fmt:message key="trialIndide.holderType"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:select id="holderType" name="studyIndldeWebDTO.holderType" headerKey="" headerValue="-Select-" cssStyle="width:150px" onclick="setProgramCodes(this);"
                                      list="#{'Investigator':'Investigator','Organization':'Organization','Industry':'Industry','NIH':'NIH','NCI':'NCI'}"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.holderType</s:param>
                               </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr id="programcoderow" style="display: none;">
                        <td scope="row"  class="label">
                            <label for="programcodenoneselected">
                                <fmt:message key="trialIndide.nihnciDivProgHolderCode"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <div id="programcodeid" style="display:''">
                                <s:select id="programcodenoneselected"  list="#{'-Select-':'-Select-'}"  cssStyle="width:150px"/>
                            </div>
                            <div id="programcodenihid" style="display:none"><s:select id="programcodenihselectedvalue" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.nihInstHolder" list="#phaseCodeValuesNIH" onclick="checkCode();" /></div>
                            <div id="programcodenciid" style="display:none"><s:select id="programcodenciselectedvalue" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.nciDivProgHolder" list="#phaseCodeValuesNCI" /></div>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.nihInstHolder</s:param>
                                </s:fielderror>                            
                            </span>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.nciDivProgHolder</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="group4true">
                                <fmt:message key="trialIndide.expandedAccessIndicator"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:radio name="studyIndldeWebDTO.expandedAccessIndicator" id="group4" list="#{'true':'Yes', 'false':'No'}" onclick="handleExpandedAccessIndicator();"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.expandedAccessIndicator</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="expandedStatus">
                                <fmt:message key="trialIndide.expandedAccessStatusCode"/>:<span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:select id="expandedStatus" headerKey="" headerValue="-Select-" name="studyIndldeWebDTO.expandedAccessStatus" disabled="true" 
                                      list="#expandedAccessStatusCodeValues"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>studyIndldeWebDTO.expandedAccessStatus</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="exemptIndicator">
                                <fmt:message key="trialIndide.exemptIndicator"/>:
                            </label>
                        </td>
                        <td>
                            <s:checkbox name="studyIndldeWebDTO.exemptIndicator" id="exemptIndicator">Yes</s:checkbox>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
                            <li><s:a href="trialIndidequery.action" cssClass="btn"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>
                        </ul>   
                    </del>
                </div>             
            </s:form>
        </div>
    </body>
</html>