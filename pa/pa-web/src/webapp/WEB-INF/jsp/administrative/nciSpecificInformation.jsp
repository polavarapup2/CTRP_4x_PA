<!DOCTYPE html PUBLIC  
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="nciSpecificInformation.title"/></title>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl"/>
        <script type="text/javascript" language="javascript">
            var ts = 0;
            
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();        
            }
            
            function handleAction() {
                 document.nciSpecificInformationupdate.action="nciSpecificInformationupdate.action";
                 document.nciSpecificInformationupdate.submit();     
            }
             
            function lookup() {
                showPopup('${lookupUrl}', null, 'Organization');
            }    
            
            function loadDiv(orgid) {
                var url = '/pa/protected/ajaxorgdisplayOrg.action';
                var params = { orgId: orgid };
                var div = document.getElementById('loadOrgField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function deleteSummary4SponsorRow(rowid) {
                var  url = '/pa/protected/ajaxorgdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadOrgField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn"); 
            }
            
            // do not remove these two callback methods!
            function setpersid(persid) {
            }
            
            function setorgid(orgid) {
            }
            
            var allowableConsortiaCategories = ${allowableConsortiaCategoriesJSON};
            var allConsortiaCategories = ${consortiaTrialCategoryValueMapJSON};
            
            function filterConsortiaCategoryList() {
            	var listEl = $('nciSpecificInformationWebDTO.consortiaTrialCategoryCode');
            	var curSelection = $F(listEl);
            	var sum4Code = $F('summary4TypeCode');
            	            	
            	listEl.options.length = 0;
            	var opt = document.createElement("option");
            	opt.value= '';
            	opt.innerHTML = 'Yes';            	
            	listEl.options.add(opt);
            	
            	jQuery.each(allConsortiaCategories, function(key, val) {
            		if (sum4Code == '' || $A(allowableConsortiaCategories[sum4Code]).indexOf(key)>-1) {
	            		opt = document.createElement("option");
	                    opt.value= key;
	                    opt.innerHTML = val;              
	                    listEl.options.add(opt);
            		}
            	});
            	
            	listEl.setValue(curSelection);
            	
            	if (curSelection!=$F(listEl)) {
            		$('consortiaTrialCategoryCodeErr').innerHTML = 'Please select a new value';
            	} 
            	
            }
            
            Event.observe(window, "load", function() {
            	if ($('nciSpecificInformationWebDTO.consortiaTrialCategoryCode')!=null) {
            	    filterConsortiaCategoryList();
            	    Event.observe($('summary4TypeCode'), "change", 
                        filterConsortiaCategoryList);
            	}


                jQuery("#programCodeIds").select2({
                    templateResult : function(pg){
                        return pg.title;
                    },
                    width: '200px'
                });


                jQuery(".select2-hidden-accessible").on("select2:unselect",function (e) {
                    ts = e.timeStamp;
                }).on("select2:opening", function (e) {
                    if (e.timeStamp - ts < 100) {
                        e.preventDefault();
                    }
                });

            });
                
        </script>
        <style type="text/css">
            li.select2-results__option {
                text-overflow: ellipsis;
                overflow: hidden;
                max-width: 35em;
                white-space: nowrap;
            }

            li.select2-selection__choice > span.select2-selection__choice__remove {
                right: 3px !important;
                left: inherit !important;
                color:#d03b39 !important;
                padding-left:2px;
                float:right;
            }
        </style>
    </head>
    <body>
        <h1><fmt:message key="nciSpecificInformation.title" /></h1>
        <c:set var="topic" scope="request" value="abstractnci"/>
        <c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <s:url id="cancelUrl" namespace="/protected" action="nciSpecificInformationquery"/>
        <div class="box">  
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form action="nciSpecificInformationupdate">
                <s:token/>
                <pa:studyUniqueToken/>
                <h2><fmt:message key="nciSpecificInformation.title" /></h2>
                <table class="form" >
                    
                        <tr>
                            <td scope="row" class="label">
                                <label for="accrualReportingMethodCode"><fmt:message key="studyProtocol.accrualReportingMethodCode"/><span class="required">${asterisk}</span></label>
                            </td>
                            <s:set name="accrualReportingMethodCodeValues" value="@gov.nih.nci.pa.enums.AccrualReportingMethodCode@getDisplayNames()" />
                            <td class="value">
                                <s:select id="accrualReportingMethodCode" headerKey="" headerValue="" 
                                   name="nciSpecificInformationWebDTO.accrualReportingMethodCode" 
                                   list="#accrualReportingMethodCodeValues"  
                                   value="nciSpecificInformationWebDTO.accrualReportingMethodCode" 
                                   cssStyle="width:206px" />
                                <span class="formErrorMsg"> 
                                      <s:fielderror>
                                          <s:param>nciSpecificInformationWebDTO.accrualReportingMethodCode</s:param>
                                      </s:fielderror>                            
                                </span>
                            </td>                           
                        </tr> 
                                  
                    <tr>
                        <td scope="row" class="label">
                            <label for="summary4TypeCode"><fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></label>
                        </td>
                        
                        <td class="value">
                            <s:select id="summary4TypeCode" headerKey="" headerValue="" 
                                      name="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                                      list="#{'National':'National', 'Externally Peer-Reviewed':'Externally Peer-Reviewed','Institutional':'Institutional','Industrial':'Industrial/Other'}"  
                                      value="nciSpecificInformationWebDTO.summaryFourFundingCategoryCode" 
                                      cssStyle="width:206px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.summaryFourFundingCategoryCode</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>                                  
                    </tr>      
                    <tr>
                        <td scope="row" class="label">
                            <label for="summary4FundingSponsor">Data Table 4 Funding Sponsor/Source:</label>
                        </td>
                        <td class="value">
                            <div id="loadOrgField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/orgField.jsp" %>
                            </div>        
                        </td>
                        
                    </tr>
                    
                    <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
                    <tr>
                        <td scope="row" class="label">
                            <label for="nciSpecificInformationWebDTO.consortiaTrialCategoryCode"><fmt:message key="studyProtocol.consortiaTrialCategoryCode"/></label>
                        </td>                        
                        <td class="value">                           
                            <s:select headerKey="" headerValue="Yes"
                                  id="nciSpecificInformationWebDTO.consortiaTrialCategoryCode"
                                  name="nciSpecificInformationWebDTO.consortiaTrialCategoryCode"
                                  list="consortiaTrialCategoryValueMap"
                                  cssStyle="width:206px" />                           
                            <span class="formErrorMsg" id="consortiaTrialCategoryCodeErr"> 
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.consortiaTrialCategoryCode</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>                                  
                    </tr>      
                    </c:if>

                    <s:if test="cancerTrial">
                        <tr>
                            <td scope="row" class="label">
                                <label for="programCodeIds"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                            </td>
                            <td class="value">
                               <table><tr><td>
                                    <s:select id="programCodeIds" name="programCodeIds"
                                          value="programCodeIds"
                                          list="programCodeList"
                                          listKey="id"
                                          listValue="programCode"
                                          listTitle="displayName"
                                          multiple="true" />
                                    <span class="formErrorMsg">
                                        <s:fielderror>
                                            <s:param>programCodeIds</s:param>
                                        </s:fielderror>
                                    </span>
                                    </td>
                                    <td class="info">
                                   
                            Only Program Codes of the Lead Organization Family are displayed. Use the Registration Application to view or modify a
                             different Family's program codes, if it participates on this trial.
                           </td>
                            </tr></table> 
                            </td>
                            
                        </tr>
                    </s:if>


                    
                    <s:if test="%{#request.displayXmlFlag}"> 
                    <tr>
                        <td scope="row" class="label">
                            <label for="ctroOverride"><fmt:message key="studyProtocol.ctroOverride"/></label>
                        </td>
                        <td class="value">
                            <!--<s:checkbox id="ctroOverride" name="nciSpecificInformationWebDTO.ctroOverride" />-->
                            <s:radio id="ctroOverride" name="nciSpecificInformationWebDTO.ctroOverride" list="#{'true':'Yes','false':'No'}"  />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.ctroOverride</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>     
                    <tr><td scope="row" class="label">
                            <label for="ctroOverride">Comments</label>
                        </td>
                        <td class="value">
                        <s:textarea id="ctroOverideFlagComments" name="nciSpecificInformationWebDTO.ctroOverideFlagComments" cols="40" rows="10"/>
                        <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>nciSpecificInformationWebDTO.ctroOverideFlagComments</s:param>
                                </s:fielderror>                            
                            </span>
                        </td>
                    </tr>  
                    </s:if>             
                </table> 
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                                <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                </li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>    
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>