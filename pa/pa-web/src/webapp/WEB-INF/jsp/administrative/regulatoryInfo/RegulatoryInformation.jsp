<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="regulatory.title" /></title>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">    
            
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
                checkAllonLoad();
                
            }

            function checkAllonLoad() {
                if ($('fdaindid').value == '' | $('fdaindid').value == 'false') {
                    hideRow($('sec801row'));
                   
                } 
               //FDAAA validations
               if ($('device').value == '' | $('device').value == 'false') {
                   hideRow($('delpostindrow'));
                   $('delpostindid').value = '';
                   hideRow($('survRow'));
                   $('surveillance').value = '';
               }
                  
               if (($('delpostindid').value == 'false' | $('delpostindid').value == '') |
                   ($('device').value == 'false' | $('device').value == '')) {
                   hideRow($('approvalRow'));
                   $('approval').value = '';
               }
            }
            
            function checkFDADropDown() {
                if ($('fdaindid').value == '' | $('fdaindid').value == 'false') {
                    input_box=confirm("Section 801 will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true) {
                        $('sec801id').value ='';
                        hideRow($('sec801row'));
                    } else {
                        $('fdaindid').value = 'true';
                    }
                } else {
                    showRow($('sec801row'));
                }
            }
        
            function checkDeviceDropDown() {
                if ($('device').value == '' | $('device').value == 'false') {    
                    input_box = confirm("Unapproved/Uncleared Device and Pediatric Post-market Surveillance will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true){
                        hideRow($('delpostindrow'));
                        hideRow($('survRow'));
                        hideRow($('approvalRow'));
                        $('delpostindid').value = '';
                        $('surveillance').value ='';
                        $('approval').value = '';
                    } else {
                        $('device').value = 'true';
                    }
                } else {
                    showRow($('delpostindrow'));
                    showRow($('survRow'));
                    if ($('delpostindrow').value == 'true') {
                        showRow($('approvalRow'));
                    }
                }    
            }
            
            function checkDelPostingIndDropDown() {
                if ($('delpostindid').value=='false' | $('delpostindid').value=='') {
                    input_box=confirm("Post Prior to U.S. FDA Approval or Clearance will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                       if (input_box == true) {
                            $('approval').value ='';
                            hideRow($('approvalRow'));
                       } else {
                           $('approval').value = 'true';
                       }
                } else {
                	if ($('device').value == 'true') {
                        showRow($('approvalRow'));
                    }
                }  
            }
            
            function hideRow(row) {            
                row.style.display = 'none';    
            }
            
            function showRow(row) {
                row.style.display = '';
            }
            
            /*
            Ajax calls to populate authority
            */
            function show_details() {
                dojo.event.topic.publish("show_detail");
            }
            
            function handleAction(){
                if (!checkReqFields()) {
                    document.saveRegAuthority.action="regulatoryInfoupdate.action";
                    document.saveRegAuthority.submit();
                }
            }
            
            function checkReqFields() {
                if ($('fdaindid').value == 'true') {
                    if ($('sec801id').value == '') {
                        alert("The Section801 Indicator cannot be empty");
                        return true;
                    }
                   
                }    
                return false;
            }
            function cancel(url){
                $('fdaindid').value ='';
                $('sec801id').value ='';
                $('delpostindid').value ='';
                $('datamonid').value ='';
                document.forms[0].setAttribute("action", url);
                document.forms[0].submit();
            }
        </script>
    </head>
    <body>
        <!-- main content begins-->
        <!-- <div id="contentwide"> -->
        <h1><fmt:message key="regulatory.title" /></h1>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
            <c:set var="topic" scope="request" value="reviewregulatory"/>
        </c:if>
        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
            <c:set var="topic" scope="request" value="abstractregulatory"/>
        </c:if>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
         <c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
          <s:url id="cancelUrl" namespace="/protected" action="regulatoryInfoquery"/>
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form action="regulatoryInfoupdate" name="saveRegAuthority" theme="simple">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:actionerror/>
                <s:hidden id="lastUpdatedDate" name="webDTO.lastUpdatedDate"> </s:hidden>
                <s:hidden id="msId" name="webDTO.id"> </s:hidden>
                <h2><fmt:message key="regulatory.title" /></h2>
              <!--  <c:set var="asterisk1" value="${(webDTO.required)?'*':''}" scope="request"/> -->
                <table class="form">
                    
                    <tr>
                        <td scope="row" class="label">
                            <label for="drug"><fmt:message key="regulatory.drug.product"/></label> 
                        </td>
                        <td style="padding: 1px 5px 5px 0 ">
                            <s:select id="drug" name="webDTO.fdaRegulatedDrug" list="#{'':'', 'false':'No', 'true':'Yes'}" />
                            &nbsp;&nbsp;&nbsp;
                            <span class="info">Studying one or more U.S. FDA-regulated drug or biologic products?</span>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.fdaRegulatedDrug</s:param></s:fielderror>
                            </span>
                        </td>  
                      </tr>
                      <tr>
                        <td scope="row" class="label">
                            <label for="device"><fmt:message key="regulatory.device.product"/></label>
                        </td>
                        <td style="padding: 1px 5px 5px 0 ">
                            <s:select id="device" name="webDTO.fdaRegulatedDevice" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkDeviceDropDown();"/>
                            &nbsp;&nbsp;&nbsp;
                            <span class="info">Studying one or more U.S. FDA-regulated device products?</span>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.fdaRegulatedDevice</s:param></s:fielderror>
                            </span>
                        </td>  
                      </tr>
                      
                      <!--   Delayed Posting Indicator-->
                      <tr id="delpostindrow">
                        <td scope="row" class="label">
                            <label for="delpostindid"><fmt:message key="regulatory.delayed.posting.ind"/></label>
                        </td>
                        <td style="padding: 1px 5px 5px 0 ">
                            <s:select id="delpostindid" name="webDTO.delayedPostingIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkDelPostingIndDropDown();" />
                            &nbsp;&nbsp;&nbsp;
                            <span class="info">When this is set to "Yes" the trial will not be included in the nightly export to cancer.gov</span>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.delayedPostingIndicator</s:param></s:fielderror>
                            </span>
                        </td>        
                     </tr>
                      
                      <tr id="approvalRow">
                        <td scope="row" class="label">
                            <label for="approval"><fmt:message key="regulatory.approval.clearance"/></label>
                        </td>
                        <td style="padding: 1px 5px 5px 0 ">
                            <s:select id="approval" name="webDTO.postPriorToApproval" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkApprovalDropDown();"/>
                            &nbsp;&nbsp;&nbsp;
                            <span  class="info">Authorize posting of study record on ClinicalTrials.gov prior to U.S. FDA approval/clearance of device product?</span>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.postPriorToApproval</s:param></s:fielderror>
                            </span>
                        </td>  
                      </tr>
                       <tr id="survRow">
                        <td scope="row" class="label">
                            <label for="surveillance"><fmt:message key="regulatory.market.surveillance"/></label>
                        </td>
                        <td style="padding: 1px 5px 5px 0 ">
                            <s:select id="surveillance" name="webDTO.pedPostmarketSurv" list="#{'':'', 'false':'No', 'true':'Yes'}" />
                            &nbsp;&nbsp;&nbsp;
                            <span class="info">Required only if this is a pediatric postmarket surveillance of a device product ordered by the U.S. FDA.</span>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.pedPostmarketSurv</s:param></s:fielderror>
                            </span>
                        </td>  
                      </tr>
                       <tr>
                        <td scope="row" class="label">
                            <label for="export"><fmt:message key="regulatory.usa.export"/>
                        </td>
                        <td style="padding: 1px 5px 5px 0 ">
                            <s:select id="export" name="webDTO.exportedFromUs" list="#{'':'', 'false':'No', 'true':'Yes'}" />
                            &nbsp;&nbsp;&nbsp;
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.exportedFromUs</s:param></s:fielderror>
                            </span>
                        </td>  
                      </tr>
                      <!--  Trial Oversight Authority Country -->
                    <!--  
                    <td scope="row" class="label">
                            <label for="countries"><fmt:message key="regulatory.oversight.country.name"/></label><span class="required">${asterisk}</span>
                        </td>
                        <td class="value">
                            <s:select id="countries" headerValue="-Select-" headerKey=""
                                      name="lst" list="countryList" listKey="id" listValue="name"
                                      onchange="loadDiv();" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>lst</s:param>
                                </s:fielderror>
                            </span>
                         </td>
                         -->       
                    
                    <%--  Trial Oversignt Authority Organization Name
                    <tr>
                        <td scope="row" class="label">
                            <label for="auths"><fmt:message key="regulatory.oversight.auth.name"/></label>
                            <span class="required">${asterisk}</span>
                        </td>
                        <td class="value">
                            <div id="loadAuthField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/authorityname.jsp" %>
                            </div>
                        </td>
                    </tr> 
                     --%>   
                    <!--   FDA Regulated Intervention Indicator-->
                    <tr>
                        <td scope="row"  class="label">
                            <label for="fdaindid"><fmt:message key="regulatory.FDA.regulated.interv.ind"/></label><span class="required">${asterisk}</span> 
                        </td>
                        <td class="value">
                            <s:select id="fdaindid" name="webDTO.fdaRegulatedInterventionIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkFDADropDown();"/>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.fdaRegulatedInterventionIndicator</s:param></s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <!--   Section 801 Indicator-->
                    <tr id="sec801row">
                        <td scope="row" class="label">
                            <label for="sec801id"><fmt:message key="regulatory.section801.ind"/></label><span class="required">${asterisk}</span>
                        </td>
                        <td class="value">
                            <s:select id="sec801id" name="webDTO.section801Indicator" list="#{'':'', 'false':'No', 'true':'Yes'}"/>
                            <span class="formErrorMsg">
                                <s:fielderror><s:param>webDTO.section801Indicator</s:param></s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <!--   Data Monitoring Committee Appointed Indicator -->
                    <tr id="datamonrow">
                        <td scope="row" class="label">
                            <label for="datamonid"><fmt:message key="regulatory.data.monitoring.committee.ind"/></label>
                        </td>
                        <td class="value">
                            <s:select id="datamonid" name="webDTO.dataMonitoringIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}"/>        
                        </td>        
                    </tr>
                    <tr>
                        <td colspan="2" class="pad10">
                            <div class="line"></div>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                               <s:a onclick="javascript:cancel('%{cancelUrl}');" href="javascript:void(0)" cssClass="btn">
                                    <span class="btn_img"><span class="cancel">Cancel</span></span>
                                </s:a>
                                </li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>
