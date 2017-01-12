<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="participatingOrganizations.subtitle" /></title>
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/control.tabs.js"/>"> </script>
        <script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
        <c:url value="/protected/popuplookupcontactpersons.action" var="lookupContactPersonsUrl"/>
        <c:url value="/protected/ajaxGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <c:url value="/protected/siteStatusHistorypopupexecute.action" var="lookupUrlstatusHistory" scope="request" />

        <script language="javascript" type="text/javascript">

            addCalendar("Cal1", "Select Date", "recStatusDate", "facility");                 
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");

            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
            }

            function facilitySave() {
                document.facility.action="participatingOrganizationsfacilitySave.action";
                document.facility.submit();
            }

            function facilityUpdate() {
                var url = '/pa/protected/ajaxptpOrgfacilityUpdate.action';
                var form = document.facility;
                var params = {
                    localProtocolIdenfier: form.siteLocalTrialIdentifier.value,
                    programCode: form.programCode.value,
                    recStatus: form.recStatus.value,
                    recStatusDate: form.recStatusDate.value,
                    recStatusComments: form.recStatusComments.value,
                    targetAccrualNumber: form.targetAccrualNumber.value,
                    dateOpenedForAccrual: form.dateOpenedForAccrual.value,
                    dateClosedForAccrual: form.dateClosedForAccrual.value
                };
                var div = $('loadOrgDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function lookup() {
                showPopup('${lookupUrl}', null, 'Organization');
            }

            function lookupperson() {
                showPopup('${lookupPersonsUrl}', null, 'Persons');
            }

            function lookupcontactperson() {
                var tel = $('personContactWebDTO.telephone').value;
                var email = $('personContactWebDTO.email').value;
                var url = '${lookupContactPersonsUrl}?tel='+tel+'&email='+email;
                showPopup(url, null, 'Persons');
            }
            
            function lookupStatusHistory() {
                showPopup('${lookupUrlstatusHistory}?studySiteId=<s:property value="cbValue"/>', null, 'Status History');
            } 

            function loadDiv(orgid) {
                var url = '/pa/protected/ajaxptpOrgdisplayOrg.action';
                var params = { orgId: orgid };
                var div = $('orgDetailsDiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function loadPersDiv(persid, rolecode, func) {
                if (func != "add") {
                   input_box=confirm("Click OK to un-link the Investigator from the Study.  Cancel to Abort.");
                   if (input_box!=true) {
                       return;
                   }
                }
                var div = $('saveAndShowContacts');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                if (func == "add") {
                    var url = '/pa/protected/ajaxptpOrgsaveStudyParticipationContact.action';
                    var params = { 
                        persid: persid, 
                        rolecode: rolecode 
                    };
                    var aj = callAjaxPost(div, url, params);
                } else {
                    var url = '/pa/protected/ajaxptpOrgdeleteStudyPartContact.action';
                    var params = { persid: persid };
                    var aj = callAjaxPost(div, url, params);
                }
                return false;
            }

            /**
             * This function is called when a primary contact is chosen from the person search pop up.
             */
            function loadContactPersDiv(persid) {
                var url = '/pa/protected/ajaxptpOrgdisplayStudyParticipationPrimContact.action';
                var params = { contactpersid: persid };
                var div = $('showPrimaryContacts');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            /**
             * Not sure if this one below is called ever!
             */
            function setAsPrimaryContact(persId) {
                var url = '/pa/protected/ajaxptpOrgdisplayStudyParticipationPrimContact.action';
                var params = { contactpersid: persId }; 
                var div = $('saveAndShowPersons');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function loadContactPersDivEditMode(persid) {
                var url = '/pa/protected/ajaxptpOrgdisplayStudyParticipationPrimContact.action';
                var params = { 
                    contactpersid: persid, 
                    editmode: 'yes' 
                }; 
                var div = $('showPrimaryContacts');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function refreshPrimaryContact() {
                var url = '/pa/protected/ajaxptpOrgrefreshPrimaryContact.action';
                var params = { contactpersid: $('personContactWebDTO_selectedPersId').value }; 
                var div = $('showPrimaryContacts');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function savePrimaryContact() {
                var url = '/pa/protected/ajaxptpOrgsaveStudyParticipationPrimContact.action';
                var params = {
                        contactpersid: $('personContactWebDTO.selectedPersId').value,
                        email: $('personContactWebDTO.email').value,
                        tel: $('personContactWebDTO.telephone').value
                };
                var div = $('showPrimaryContacts');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            // do not remove these two callback methods!
            function setpersid(persIdentifier, name, email, phone) {
                persid = persIdentifier;
                selectedName = name;
                contactMail = email;
                contactPhone = phone;
            }

            function setorgid(orgid) {
            }

            function lookup4genericcontact() {
                var orgid = $('editOrg.identifier').value;
                showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid+'&type=Site', createOrgGenericContactDiv, 'Select Generic Contact');
            }

            function createOrgGenericContactDiv() {
                $('personContactWebDTO.firstName').value = '';
                $('personContactWebDTO.middleName').value = '';
                $('personContactWebDTO.lastName').value = '';
                $("personContactWebDTO.email").value = contactMail;
                $("personContactWebDTO.telephone").value = contactPhone;
                $("personContactWebDTO.title").value = selectedName;
                $('personContactWebDTO.selectedPersId').value =  persid;
            }
        </script>
        
        <style type="text/css">
            div.error_msg {
			    white-space: pre-wrap;
			}
			span.warning {
			    color: blue;
			    white-space: pre-wrap;
			}
			span.error {
			    color: red;
			    white-space: pre-wrap;
			}
        </style>
    </head>
    <body>
        <h1><fmt:message key="participatingOrganizations.subtitle" /></h1>
        <c:set var="topic" scope="request" value="abstractsite"/>
        <c:set var="asterisk" value="${sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <s:url id="cancelUrl" namespace="/protected" action="participatingOrganizations"/>
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:if test="hasActionErrors()">
                <div class="action_error_msg"><s:actionerror escape="false"/></div>
            </s:if>
            <h2><fmt:message key="participatingOrganizations.subtitle" /></h2>
            <table class="form" id="formTable">
                <tr>
                    <td colspan="2"><!--Tabs -->
                        <ul id="maintabs" class="tabs">
                            <li><a href="#facility"><fmt:message key="participatingOrganizations.subtitle2" /></a></li>
                            <s:if test="%{currentAction == 'edit'}">
                                <li><a href="#investigators">Investigators</a></li>
                                <li><a href="#contacts">Contact</a></li>
                            </s:if>
                            <s:else>
                                <li><a class="disabled">Investigators</a></li>
                                <li><a class="disabled">Contact</a></li>
                            </s:else>
                        </ul>
                    <!--/Tabs --> <!--
                            directions on http://livepipe.net/control/tabs
                            - make sure you add control.tabs.js to your scripts directory!
                            - Matt
                        --> <script type="text/javascript">
                            //<![CDATA[
                            Event.observe(window,'load',function(){
                                $$('.tabs').each(function(tabs){
                                    new Control.Tabs(tabs);
                                });
                            });
                            //]]>
                        </script>
                        <div id="tabboxwrapper"><!--Facility-->
                            <div id="facility" class="box">
                                <s:form name="facility">
                                    <s:token/>
                                    <pa:studyUniqueToken/>
                                    <div id="loadOrgDetails">
                                         <%@ include file="/WEB-INF/jsp/nodecorate/nodecororgdetails.jsp" %>
                                    </div>
                                    <div class="actionsrow">
                                        <del class="btnwrapper">
                                            <ul class="btnrow">
                                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                                <li>
                                                    <s:if test="%{currentAction == 'edit'}">
                                                        <s:a href="javascript:void(0)" cssClass="btn" onclick="facilityUpdate();">
                                                            <span class="btn_img"><span class="save">Save</span></span>
                                                        </s:a>
                                                    </s:if>
                                                    <s:else>
                                                        <s:a href="javascript:void(0)" cssClass="btn" onclick="facilitySave();">
                                                            <span class="btn_img"><span class="save">Save</span></span>
                                                        </s:a>
                                                    </s:else>
                                                   <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                                </li>
                                             </pa:adminAbstractorDisplayWhenCheckedOut>
                                            </ul>
                                        </del>
                                    </div>
                                </s:form>
                            </div>
                    <!--/Facility-->
                    <!-----------------------------------------------Begin Investigators Tab------------------->
                               <div id="investigators" class="box" style="display:none;">
                                <h3>Participating Site Investigators <s:property value="organizationName"/></h3>
                                <div id="saveAndShowContacts">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/displaySPContactsTable.jsp" %>
                                </div>
                                <div class="actionsrow">
                                    <del class="btnwrapper">
                                        <ul class="btnrow">
                                        <pa:adminAbstractorDisplayWhenCheckedOut>
                                            <li>
                                                <a href="javascript:void(0)" class="btn" onclick="lookupperson();">
                                                    <span class="btn_img"><span class="add">Add</span></span>
                                                </a>
                                            </li>
                                            </pa:adminAbstractorDisplayWhenCheckedOut>
                                        </ul>
                                    </del>
                                </div>
                            </div>
              <!-----------------------------------------------End Investigators Tab------------------->
              <!-----------------------------------------------Begin Contact Tab------------------->
                             <div id="contacts" class="box" style="display:none;">
                                <h3>Primary Contact <s:property value="organizationName"/></h3>
                                <div id="showPrimaryContacts">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/displayPrimaryContact.jsp" %>
                                </div>
                                <div class="actionsrow">
                                    <del class="btnwrapper">
                                        <ul class="btnrow">
                                        <pa:adminAbstractorDisplayWhenCheckedOut>
                                            <li>
                                                 <s:a href="javascript:void(0)" cssClass="btn" onclick="savePrimaryContact();">
                                                    <span class="btn_img"><span class="save">Save</span></span>
                                                </s:a>
                                                <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                                            </li>   
                                        </pa:adminAbstractorDisplayWhenCheckedOut> 
                                        </ul>
                                    </del>
                                </div>
                            </div>
              <!-----------------------------------------------End Contact Tab------------------->
                        </div>
                    </td>
                </tr>
                <s:hidden name="proprietaryTrialIndicator" id="proprietaryTrialIndicator"></s:hidden>
            </table>
        </div>
    </body>
</html>