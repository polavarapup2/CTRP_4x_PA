<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/popupPlannedMarker.action" var="lookupUrl" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
                toggleAssayTypeOtherText();
                toggleEvalTypeOtherText();
                toggleSpecimenTypeOtherText();
                toggleHugoCode();
            }
            
            function toggleHugoCode() {
                if ($('foundInHugo').value == 'true') {
                    $('hugoCodeRow').show();
                } else {
                    $('hugoCodeRow').hide();
                }
            }
            function toggleEvalTypeOtherText() {
               var element = document.getElementsByName("plannedMarker.evaluationType");                
                for (var i=0; i <= element.length; i++) {
                	if (typeof(element[i]) == 'undefined') {
                		break;	
                	} else {
                		if (element[i].value == 'Other' && element[i].checked == true) {    
                            $('evalTypeOtherTextRow').show();
                            break; 
                       } else {
                           $('evalTypeOtherTextRow').hide(); 
                       } 	
                	}       
                }
                return false;
            }
            
            function toggleAssayTypeOtherText() {
            var element = document.getElementsByName("plannedMarker.assayType");
                for (var i=0; i <= element.length; i++) {
                	if (typeof(element[i]) == 'undefined') {
                		break;	
                	} else {
                		if (element[i].value == 'Other' && element[i].checked == true) {    
                            $('assayTypeOtherTextRow').show();
                            break;
                            
                       } else {
                           $('assayTypeOtherTextRow').hide(); 
                       }	
                	}       
                } 
                return false;  
            }
            
            function toggleAssayPurposeOtherText() {
            var element = document.getElementsByName("plannedMarker.assayPurpose");
                for (var i=0; i <= element.length; i++) {
                	if (typeof(element[i]) == 'undefined') {
                		break;	
                	} else {
                		if (element[i].value == 'Other' && element[i].checked == true) {    
                            $('assayPurposeOtherTextRow').show();
                            break;
                            return false;
                       } else {
                           $('assayPurposeOtherTextRow').hide(); 
                       }   	
                	} 
                } 
            }
            
            function toggleSpecimenTypeOtherText() {
            var element = document.getElementsByName("plannedMarker.tissueSpecimenType");
            
                for (var i=0; i <= element.length; i++) {
                	if (typeof(element[i]) == 'undefined') {
                		break;	
                	} else {
                		if (element[i].value == 'Other' && element[i].checked == true) {  
                            $('specimenTypeOtherTextRow').show();
                            break;
                            return false; 
                       } else {
                           $('specimenTypeOtherTextRow').hide(); 
                       }   	
                	}             
                } 
                return false;
            }
            
            function cadsrLookup(){
                var element = document.getElementsByName("plannedMarker.evaluationType");
                var element1 = document.getElementsByName("plannedMarker.assayType");
                var element2 = document.getElementsByName("plannedMarker.assayUse");
                var element3 = document.getElementsByName("plannedMarker.assayPurpose");
                var element4 = document.getElementsByName("plannedMarker.tissueSpecimenType");
                var element5 =document.getElementsByName("plannedMarker.evaluationTypeOtherText");
                var element6 = document.getElementsByName("plannedMarker.assayTypeOtherText");
                var element7 = document.getElementsByName("plannedMarker.specimenTypeOtherText");
                var evalFinal ='';
                var assayFinal ='';
                var bioUseFinal ='';
                var bioPurposeFinal ='';
                var specimenType ='';
                var evalOther =element5[0].value;
                var assayOther = element6[0].value;
                var specimenOther = element7[0].value;
               
                for (var i=0; i < element.length; i++) {
                    if (element[i].checked == true) { 
                        evalFinal = (element[i].value) + ", " + evalFinal;     
                    } 
                } 
                bioUseFinal = element2[0].value;
                for (var i=0; i < element1.length; i++) {
                    if (element1[i].checked == true) { 
                        assayFinal = (element1[i].value) + ", " + assayFinal;     
                    } 
                } 
                
                for (var i=0; i < element3.length; i++) {
                    if (element3[i].checked == true) { 
                        bioPurposeFinal = (element3[i].value) + ", " + bioPurposeFinal;     
                    } 
                } 
               
                for (var i=0; i < element4.length; i++) {
                    if (element4[i].checked == true) { 
                        specimenType = (element4[i].value) + ", " + specimenType;     
                    } 
                } 
               
                var updatedUrl = '${lookupUrl}?plannedMarker.evaluationType='+evalFinal+ 
                '&plannedMarker.assayType='+assayFinal+'&plannedMarker.assayPurpose='+bioPurposeFinal+'&plannedMarker.tissueSpecimenType='+specimenType
                +'&plannedMarker.assayUse='+bioUseFinal+'&plannedMarker.evaluationTypeOtherText='+evalOther
                +'&plannedMarker.assayTypeOtherText='+assayOther+'&plannedMarker.specimenTypeOtherText='+specimenOther;
               
                showPopWin(updatedUrl, 1000, 600, '', 'Marker Search in caDSR');
            }
            
            function addVariation(status){               
                $('saveResetAttribute').value=status;
                document.forms[0].submit();
            }
            function addMarkerReset(status){               
                $('saveResetMarker').value=status;
                document.forms[0].submit();
            }
            function loadDiv(markerId, evaluationType, assayType, bioUse, bioPurpose, specimenType, evalOther, assayOther, specimenOther) {
                window.top.hidePopWin(true);
                var url = '/pa/protected/ajaxptpPlannedMarkerdisplaySelectedCDE.action';
                var split = location.search.replace('?', '').split('=');
                if (split != null) {
                 var rowValue = split[1];
                }
                var params = { cdeId: markerId, selectedRowIdentifier:rowValue, 
                'plannedMarker.evaluationType':evaluationType,
                'plannedMarker.assayType':assayType, 
                'plannedMarker.assayUse':bioUse, 
                'plannedMarker.assayPurpose':bioPurpose, 
                'plannedMarker.tissueSpecimenType':specimenType,
                'plannedMarker.evaluationTypeOtherText':evalOther, 
                'plannedMarker.assayTypeOtherText':assayOther,
                'plannedMarker.specimenTypeOtherText':specimenOther};
                
                var div = $('plannedMarkerDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var options = {
                        onComplete: function(transport) {
                        	callOnloadFunctions();
                                    }
                     };
                var aj = callAjaxPost(div, url, params,options);
            }
            
            function loadMarkerWithRequestedCDE(markerName, foundInHugo, hugoCode, sentDate) {
                window.top.hidePopWin(true);
                var url = '/pa/protected/ajaxptpPlannedMarkerdisplayRequestedCDE.action';
                var params = {
                    'plannedMarker.foundInHugo': foundInHugo,
                    'plannedMarker.hugoCode': hugoCode,
                    'plannedMarker.name': markerName,
                    'plannedMarker.dateEmailSent': sentDate
                };
                var div = $('plannedMarkerDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var options = {
                   onComplete: function(transport) {
                                   toggleHugoCode();
                               }
                };
                var aj = callAjaxPost(div, url, params, options);
            }

            function cancelAction(url){
            	document.forms[0].setAttribute("action", url);
            	document.forms[0].submit();
            }
            

        </script>
    </head>
    <body>
        <h1><fmt:message key="plannedMarker.details.title" /></h1>
        <c:set var="topic" scope="request" value="abstractmarkers"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <s:url id="cancelUrl" namespace="/protected" action="plannedMarker"/>

        <div class="box">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror/></div>
            </s:if>
            <h2>
                <s:if test="%{currentAction == 'edit'}">
                    <s:set var="submitUrl" value="'plannedMarkerupdate'"/>
                    <fmt:message key="plannedMarker.edit.title"/>
                </s:if>
                <s:elseif test="%{currentAction == 'create'}">
                    <s:set var="submitUrl" value="'plannedMarkeradd'"/>
                    <fmt:message key="plannedMarker.add.title"/>
                </s:elseif>
            </h2>
            <table class="form">

                <tr>
                    <td colspan="2">
                        <s:form id="plannedMarkerForm" action="%{#submitUrl}">
                            <s:token/>
                            <pa:studyUniqueToken/>
                            <div class="actionstoprow">
                                <del class="btnwrapper">
                                    <ul class="btnrow">
                                        <li>
                                            <s:a cssClass="btn" href="javascript:void(0)" onclick="document.forms[0].submit();">
                                                <span class="btn_img"><span class="add">Save</span></span>
                                            </s:a>
                                          <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                              
                                                <s:a cssClass="btn" href="javascript:void(0)" id="addVariation" onclick="addVariation('true');">
                                                    <span class="btn_img"><fmt:message key="plannedMarker.addVariation"/></span>
                                                </s:a>
                                                <s:a cssClass="btn" href="javascript:void(0)" id="addMarkerReset" onclick="addMarkerReset('true');">
                                                    <span class="btn_img"><fmt:message key="plannedMarker.addMarkerReset"/></span>
                                                </s:a>                                                 
                                           
                                        </li>
                                    </ul>
                                </del>
                            </div>
                            <div id="plannedMarkerDetails">
                                <%@ include file="/WEB-INF/jsp/scientific/plannedMarker/selectedPlannedMarker.jsp"%>
                            </div>
                            <div class="actionsrow">
                                <del class="btnwrapper">
                                    <ul class="btnrow">
                                        <li>
                                            <s:a cssClass="btn" href="javascript:void(0)" onclick="document.forms[0].submit();">
                                                <span class="btn_img"><span class="add">Save</span></span>
                                            </s:a>
                                            <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                              
                                                    <s:a cssClass="btn" href="javascript:void(0)" id="addVariation" onclick="addVariation('true');">
                                                        <span class="btn_img"><fmt:message key="plannedMarker.addVariation"/></span>
                                                    </s:a>
                                                    <s:a cssClass="btn" href="javascript:void(0)" id="addMarkerReset" onclick="addMarkerReset('true');">
                                                        <span class="btn_img"><fmt:message key="plannedMarker.addMarkerReset"/></span>
                                                    </s:a>                                                 
                                               
                                        </li>
                                    </ul>
                                </del>
                            </div>
                        </s:form>
                    </td>
                </tr>
            </table>
         </div>
    </body>
</html>
