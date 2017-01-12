<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><fmt:message key="studyProtocol.general.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js?7b391da0-c0d3-11e3-8a33-0800200c9a66'/>"></script>

        <c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
            var orgid;
            var persid;
            var contactMail;
            var contactPhone;
            var selectedName;
            var orgname;

            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
            }

            function handleAction() {
                document.forms[0].action="generalTrialDesignupdate.action";
                document.forms[0].submit();
            }

            //function which handles the remove of the Central contact.
            function handleRemove() {
                document.forms[0].action="generalTrialDesignremoveCentralContact.action";
                document.forms[0].submit();
            }

            function lookup4loadresponsibleparty() {
               var orgid = $('sponsorIdentifier').value;
               showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
            }
        
            function setorgid(orgIdentifier, name) {
                orgid = orgIdentifier;
                orgname = name;
            }

            function setpersid(persIdentifier,name,email,phone) {
                persid = persIdentifier;
                selectedName = name;
                contactMail = email;
                contactPhone = phone;
            }

            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }

            function lookupCentralContact() {
                showPopup('${lookupPersUrl}', loadCentralContactDiv, 'Select Central Contact');
            }

            function loadCentralContactDiv() {
                var url = 'ajaxTrialValidationdisplayCentralContact.action';
                var params = { persId: persid };
                $('gtdDTO.centralContactTitle').value = '';
                $('gtdDTO.centralContactIdentifier').value =  persid;
                var div = $('loadCentralContactDiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function lookupGenericCentralContact() {
                var orgid = $('gtdDTO.leadOrganizationIdentifier').value;
                showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid+'&type=Site', createGenericCentralContactDiv, 'Select Generic Contact');
            }

            function createGenericCentralContactDiv() {
               $('gtdDTO.centralContactName').value = '';
               $('gtdDTO.centralContactTitle').value = selectedName;
               $('gtdDTO.centralContactIdentifier').value =  persid;
               $("gtdDTO.centralContactEmail").value = contactMail;
               $("gtdDTO.centralContactPhone").value = extractPhoneNumberNoExt(contactPhone);
               $("gtdDTO.centralContactPhoneExtn").value = extractPhoneNumberExt(contactPhone);
            }

            function loadDiv(orgid) {
            	clearValue('gtdDTO.centralContactName');
            	clearValue('gtdDTO.centralContactTitle');
            	clearValue('gtdDTO.centralContactIdentifier');
            	clearValue("gtdDTO.centralContactEmail");
            	clearValue("gtdDTO.centralContactPhone");
            	clearValue("gtdDTO.centralContactPhoneExtn");
            }
            
            function clearValue(elID) {
            	if ($(elID)!=null) {
            		$(elID).value = "";
            	}
            }

            function loadPersDiv(persid, func) {
            }
            
            // Other Identifiers handling code.
            Event.observe(window, 'load', initializeOtherIdentifiersSection);            
            
            function initializeOtherIdentifiersSection() {
            	 var  url = 'ajaxManageOtherIdentifiersActionquery.action';
                 var params = {};
                 var div = $('otherIdentifierdiv');
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                 var aj = callAjaxPost(div, url, params, {evalScripts:true});  
            }

            function deleteOtherIdentifierRow(rowid){ 
                var  url = 'ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params, {evalScripts:true});
            }            
            
            function saveIdentifierRow(rowid){
            	var orgValue = $("identifier_"+rowid).value;            	
            	if (orgValue != null && orgValue != '') {
            		var  url = 'ajaxManageOtherIdentifiersActionsaveOtherIdentifierRow.action';
            		var params = { uuid: rowid, otherIdentifier : orgValue };
            		var div = $('otherIdentifierdiv');
            		div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Saving...</div>';
            		var aj = callAjaxPost(div, url, params, {evalScripts:true}); 
            	} else {
            		alert("Please enter a valid Other Identifier.");
            	}
            }            
            
             function editIdentifierRow(rowid){
             	jQuery("#identifierDiv_"+rowid).hide();
             	jQuery("#identifierInputDiv_"+rowid).show();
             	jQuery("#actionEdit_"+rowid).hide();
             	jQuery("#actionSave_"+rowid).show();            	            	
             }
             
             function addOtherIdentifier() {
                var orgValue = $("otherIdentifierOrg").value;
                var otherIdentifierTypeValue = $("otherIdentifierType").value;
                if (orgValue != null && orgValue != '') {
                    var  url = 'ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue, otherIdentifierType: otherIdentifierTypeValue };  
                                      
                    var div = $('otherIdentifierdiv');   
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params, {evalScripts:true});
                    $("otherIdentifierOrg").value="";
                } else {
                    alert("Please enter a valid Other Identifier.");
                }
            }
             
             function deleteStudyAlternateTitle(rowid){ 
                 var  url = 'ajaxManageStudyAlternateTitlesActiondeleteStudyAlternateTitle.action';
                 var params = { uuid: rowid };
                 var div = $('studyAlternateTitleDiv');
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                 var aj = callAjaxPost(div, url, params);
             }
             
            function saveStudyAlternateTitle(rowid){
                var alternateTitleValue = $("studyAlternateTitle_"+rowid).value;
                var alternateTitleTypeValue = $("studyAlternateTitleType_"+rowid).value;                
                if (alternateTitleValue != null && alternateTitleValue != '') {                	
                    var  url = 'ajaxManageStudyAlternateTitlesActionsaveStudyAlternateTitle.action';
                    var params = { uuid: rowid, alternateTitle: alternateTitleValue, 
                                   alternateTitleType: alternateTitleTypeValue };
                    var div = $('studyAlternateTitleDiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Saving...</div>';
                    var aj = callAjaxPost(div, url, params); 
                } else {
                    alert("Please enter a valid Alternate Title.");
                }
            }
            
            function editStudyAlternateTitle(rowid) {
                jQuery("#studyAlternateTitleTypeDiv_"+rowid).hide();
                jQuery("#studyAlternateTitleTypeInputDiv_"+rowid).show(); 
                jQuery("#studyAlternateTitleDiv_"+rowid).hide();
                jQuery("#studyAlternateTitleInputDiv_"+rowid).show();
                jQuery("#studyAlternateTitleActionEdit_"+rowid).hide();
                jQuery("#studyAlternateTitleActionSave_"+rowid).show();
            }
            
            function addStudyAlternateTitle() {
            	var alternateTitleValue = $("studyAlternateTitle").value;
            	var alternateTitleTypeValue = $("studyAlternateTitleType").value;
            	if (alternateTitleValue != null && alternateTitleValue != '') {
            		var url = 'ajaxManageStudyAlternateTitlesActionaddStudyAlternateTitle.action';
            		var params = { alternateTitle: alternateTitleValue, alternateTitleType: alternateTitleTypeValue };
            		
            		var div = $('studyAlternateTitleDiv');
            		div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("studyAlternateTitle").value="";
            	} else {
                    alert("Please enter a valid Alternate Title.");
                }
            }
            
        </script>
    </head>
    
    <body>
        <h1><fmt:message key="studyProtocol.general.title" /></h1>
        <c:set var="topic" scope="request" value="abstractgeneral"/>
        <c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
                <s:url id="cancelUrl" namespace="/protected" action="generalTrialDesignquery"/>
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form>
                <s:token/>
                <pa:studyUniqueToken/>
                <s:actionerror/>                
                <h2>General Trial Details</h2>
                <div class="actionstoprow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><a href="javascript:void(0)" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a>
                                <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                </li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
                <table class="form">
                    <s:hidden name="gtdDTO.submissionNumber" id="gtdDTO.submissionNumber"/>
                    <s:hidden name="gtdDTO.nonOtherIdentifiers.extension" id="gtdDTO.nonOtherIdentifiers.extension"/>
                    <s:hidden name="gtdDTO.nonOtherIdentifiers.root" id="gtdDTO.nonOtherIdentifiers.root"/>
                    <s:hidden name="gtdDTO.nonOtherIdentifiers.identifierName" id="gtdDTO.nonOtherIdentifiers.identifierName"/>
                    <s:hidden name="gtdDTO.programCodeText" id="gtdDTO.programCodeText"/>
                    <s:hidden name="gtdDTO.phaseCode" id= "gtdDTO.phaseCode"></s:hidden>
                    <s:hidden name="gtdDTO.phaseAdditionalQualifierCode" id= "gtdDTO.phaseAdditionalQualifierCode"></s:hidden>
                    <s:hidden name="gtdDTO.primaryPurposeCode" id= "gtdDTO.primaryPurposeCode"></s:hidden>
                    <s:hidden name="gtdDTO.primaryPurposeAdditionalQualifierCode" id= "gtdDTO.primaryPurposeAdditionalQualifierCode"></s:hidden>
                    <s:hidden name="gtdDTO.primaryPurposeOtherText" id= "gtdDTO.primaryPurposeOtherText"></s:hidden>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <td scope="row" class="label">
                                <a href="http://ClinicalTrials.gov" target="_blank">ClinicalTrials.gov</a> XML required?
                            </td>
                            <td>
                                <s:radio name="gtdDTO.ctGovXmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="toggledisplayDivs(this);"/>
                           </td>
                       </tr>
                    </c:if>
                    <tr><td>&nbsp;</td></tr>
                   <tr>
                        <th colspan="2">Other Trial Identifiers</th>
                    </tr>
                    <c:remove var="otherIdentifiersEditable"/>
                    <pa:adminAbstractorDisplayWhenCheckedOut>
                    <tr>
                        <td scope="row" class="label">
                            <label for="otherIdentifierType">Other Identifier</label>
                        </td>
                        <td>                          
                             <table>
                                <tr>                                
                                    <td><s:select id="otherIdentifierType" name="otherIdentifierType" 
                                          list="#{}"  cssStyle="margin-top: 0px;" /></td>
                                    <td>
                                    <label for="otherIdentifierOrg" style="display:none">orgid</label>
                                    <input type="text" name="otherIdentifierOrg"
                                        id="otherIdentifierOrg" value="" />&nbsp;</td>
                                    <td><input type="button" id="otherIdbtnid"
                                        value="Add Other Identifier" onclick="addOtherIdentifier();" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <c:set var="otherIdentifiersEditable" value="${true}" scope="session"/>
                    </pa:adminAbstractorDisplayWhenCheckedOut>
                    <tr>
                        <td colspan="2" class="space">
                            <div id="otherIdentifierdiv"></div>
                        </td>
                    </tr>                       
                    
                    <tr>
                        <th colspan="2"> Title</th>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="acronym"><fmt:message key="studyProtocol.acronym"/></label>
                        </td>
                        <td class="value">
                            <s:textfield id="acronym" name="gtdDTO.acronym" cssStyle="width:86px" maxlength="14"/>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="officialTitle">
                                <fmt:message key="studyProtocol.officialTitle"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:textarea id="officialTitle" name="gtdDTO.officialTitle" cssStyle="width:606px" rows="4" 
                                maxlength="4000" cssClass="charcounter" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>gtdDTO.officialTitle</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr>                    
                    <tr>
                        <th colspan="2">Alternate Titles</th>
                    </tr>
                    <tr>                       
                        <td>
                            <label for="studyAlternateTitleType" style="width:80px;">Category</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <s:set name="studyAlternateTitleTypeCodeValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getStudyAlternateTitleTypes()" />                             
                            <s:select id="studyAlternateTitleType" headerKey="" name="studyAlternateTitleType" 
                                list="#studyAlternateTitleTypeCodeValues" cssStyle="width:110px;"/>&nbsp;&nbsp;
                        </td>       
                        <td>
                            <s:textarea id="studyAlternateTitle" name="studyAlternateTitle" cssStyle="width:400px" rows="2" 
                                maxlength="4000" cssClass="charcounter" />&nbsp;
                            <input type="button" id="studyAlternateTitleBtnId" 
                                value="Add Alternate Title" onclick="addStudyAlternateTitle();" style="width:130px;"/>
                        </td>                                                                                
                    </tr>
                    <tr>
                        <td colspan="3" class="space">
                            <div id="studyAlternateTitleDiv">
                                <%@ include file="/WEB-INF/jsp/nodecorate/displayStudyAlternateTitles.jsp"%>
                            </div>
                        </td>
                    </tr>                    
                    <tr>
                        <th colspan="2"> <fmt:message key="studyProtocol.trialDescription"/></th>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                           <label for=keywordText><fmt:message key="studyProtocol.keywordText"/></label>
                        </td>
                        <td class="value">
                            <s:textarea id="keywordText" name="gtdDTO.keywordText" cssStyle="width:606px" rows="4"
                                maxlength="4000" cssClass="charcounter" />
                        </td>
                    </tr>
                    
                    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>
                    
                        <tr>
                            <th colspan="2"> Central Contact</th>
                        </tr>
                        <tr>
                            <td/>
                            <td class="info" colspan="2">Assign values to all fields below if central contact information is recorded; otherwise leave these fields empty.</td>
                        </tr>
                        <tr>
                            <td scope="row" class="label" >
                                <label for="gtdDTO.centralContactName"> Personal Contact </label>
                            </td>
                            <td class="value">
                                <div id="loadCentralContactDiv">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/centralContact.jsp" %>
                                </div>
                            </td>
                        </tr>
                      <tr>
                          <td> OR    </td>
                      </tr>
                      <tr>
                          <td scope="row" class="label"><label for="gtdDTO.centralContactTitle">Generic Contact:</label></td>
                          <td>
                              <table>
                                  <tr>
                                      <td>
                                          <s:textfield label="Central Contact title" name="gtdDTO.centralContactTitle" id="gtdDTO.centralContactTitle" size="30"
                                                       readonly="true" cssClass="readonly" cssStyle="width:200px"/>
                                      </td>
                                      <td>
                                          <ul style="margin-top:-1px;">
                                              <li style="padding-left:0">
                                                  <a href="javascript:void(0)" class="btn" onclick="lookupGenericCentralContact();" title="Opens a popup form to select Central Contact">
                                                      <span class="btn_img"><span class="person">Look Up Generic Contact</span></span>
                                                  </a>
                                              </li>
                                          </ul>
                                      </td>
                                  </tr>
                              </table>
                          </td>
                      </tr>
                      <tr>
                          <td scope="row" class="label">
                          <label for="gtdDTO.centralContactEmail">Email Address:</label>
                               
                          </td>
                          <td class="value">
                              <s:textfield name="gtdDTO.centralContactEmail" id="gtdDTO.centralContactEmail" maxlength="200" size="100"  cssStyle="width:200px" />
                              <span class="formErrorMsg">
                                  <s:fielderror>
                                      <s:param>gtdDTO.centralContactEmail</s:param>
                                  </s:fielderror>
                              </span>
                          </td>
                      </tr>
                      <tr>
                          <td scope="row" class="label">
                          <label for="gtdDTO.centralContactPhone">Phone Number:</label></td>
                          <td class="value">
                              <s:textfield name="gtdDTO.centralContactPhone" id="gtdDTO.centralContactPhone" maxlength="200" size="15"  cssStyle="width:100px" />
                                Extn:
                                <label for="gtdDTO.centralContactPhoneExtn" style="display:none">ext</label>
                                <s:textfield name="gtdDTO.centralContactPhoneExtn" id="gtdDTO.centralContactPhoneExtn" maxlength="15" size="10"  cssStyle="width:60px" />
                              <span class="formErrorMsg">
                                  <s:fielderror>
                                      <s:param>gtdDTO.centralContactPhone</s:param>
                                  </s:fielderror>
                              </span>
                          </td>
                      </tr>
                   
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><a href="javascript:void(0)" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a>
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