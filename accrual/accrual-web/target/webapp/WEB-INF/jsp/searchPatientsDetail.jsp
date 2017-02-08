<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
    <c:url value="/protected/popup.action" var="lookupUrl" />
    <%@ include file="/WEB-INF/jsp/nodecorate/tableTagParameters.jsp" %>
    <head>
        <s:if test="%{currentAction == 'create'}">
            <c:set var="topic" scope="request" value="subjectsadding"/> 
            <fmt:message key="patient.create.title" var="pageTitle" />
        </s:if>
        <s:elseif test="%{currentAction == 'update'}">
            <c:set var="topic" scope="request" value="subjectsupdate"/> 
            <fmt:message key="patient.update.title" var="pageTitle" />
        </s:elseif>
        <s:elseif test="%{currentAction == 'retrieve'}">
            <c:set var="topic" scope="request" value="subjectsintro"/> 
            <fmt:message key="patient.retrieve.title" var="pageTitle" />
        </s:elseif>
        <title>${pageTitle}</title>        
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript">
            
            var urlParameters = '<%=StringEscapeUtils.escapeJavaScript(urlParams)%>';
            function handleCancelAction() {
                submitForm("patients.action" + urlParameters);
            }
            
            function handleAddAction() {
                submitForm("patientsadd.action");
            }
            
            function handleEditAction() {
                submitForm("patientsedit.action" + urlParameters);
            }
            
            function handleUpdateAction() {
                submitForm("patientsupdate.action" +  urlParameters);
            }
            
            function submitForm(action) {
                var form = document.forms[0];
                form.action = action;
                form.submit();
            }
            
            function lookup(siteLookUp){
            	var selectedSite = "false";
            	var sitePN = document.getElementById('sitedisease');
            	if (sitePN != null && (sitePN.value != null || sitePN.value != 'undefined')) {
	            	var selectedSite;
	            	if (sitePN.value.length == 0) {
	            		selectedSite = "false";
	                } else {
	                	selectedSite = "true";
	                }
            	}
            	var result = '${lookupUrl}' + "?siteLookUp=" + siteLookUp + "&selectedSite=" + selectedSite;
            	if(siteLookUp == 'true') {
            		showPopWin(result, 900, 400, '', 'Site');
            	} else {
            		showPopWin(result, 900, 400, '', 'Disease');
            	}            	
            }
            
            function loadDiv(intid, type, siteLookUp) {
                 var url = '/accrual/protected/ajaxpatientsgetDisplayDisease.action';
                 var params = { diseaseId: intid, dType: type, siteLookUp: siteLookUp };
                 var div; 
                 if (siteLookUp == 'true') {
                	 $('#loadSiteDetails').load(url, params);
                 } else {
                	 $('#loadDetails').load(url, params);
                 } 
            } 
        </script>
    </head>
    <body>
    <script>
$('#viewTrials').addClass("active");
$(function() {
    $('input[type="text"]').keypress(function (e) {
        if (e.which == 13) {
          $('#mainActionBtn').click();
          return false;   
        }
      });
});

</script>
    <div class="container">
    <div class="scroller_anchor"></div>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <h1 class="heading"><span>${pageTitle}</span></h1>
            <s:if test="hasActionErrors()">
                <div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong>
                    <s:actionerror />.
                </div>
            </s:if>
            <s:form name="detailForm" cssClass="form-horizontal" role="form">
                <s:token/>
                <s:hidden name = "selectedRowIdentifier"/>
                <s:hidden name = "patient.patientId" />
                <s:hidden name = "patient.studySubjectId" />
                <s:hidden name = "patient.studyProtocolId" />
                <s:hidden name = "patient.statusCode" />
                <s:hidden name = "patient.performedSubjectMilestoneId" />
                <s:hidden name = "patient.submissionTypeCode" />
                <s:hidden name = "patient.registrationGroupId" />
                <s:hidden name = "showSite" />
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="identifier" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.ID"/>
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                       <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="identifier" name="patient.assignedIdentifier" cssClass="form-control" />
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                 <p class="form-control-static"><c:out value="${patient.assignedIdentifier}"/></p>
                            </s:elseif>
                      </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="birthDate" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.birthDate"/>
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="birthDate" name="patient.birthDate" placeholder="mm/yyyy" cssClass="form-control" />
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <p class="form-control-static"><c:out value="${patient.birthDate}"/></p>
                            </s:elseif>
                      </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="genderCode" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.gender"/>
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="genderCodeValues" value="@gov.nih.nci.pa.enums.PatientGenderCode@getDisplayNames()" />
                                <s:select id ="genderCode" name="patient.genderCode" headerKey="" headerValue="--Select--"
                                          list="#genderCodeValues"  cssClass="form-control"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <p class="form-control-static"><c:out value="${patient.genderCode}" /></p>
                            </s:elseif>
                       </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="raceCode" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.race"/>
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="raceCodeValues" value="@gov.nih.nci.pa.enums.PatientRaceCode@getDisplayMap()" />
                                <s:select id ="raceCode" name="patient.raceCode" multiple="true" size="7" list="#raceCodeValues"  cssClass="form-control" />
                                <p class="form-control-static">To select multiple races, select one race, and then press and hold the CTRL key as you select the other(s).</p>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <s:iterator id="races" value="patient.raceCode" >
                                    <s:set name="racerx" value="%{code}"/>
                                    <p class="form-control-static"><c:out value="${races}"/><br></p>
                                </s:iterator>
                            </s:elseif>
                       </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="ethnicCode" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.ethnicity"/>
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="ethnicCodeValues" value="@gov.nih.nci.pa.enums.PatientEthnicityCode@getDisplayMap()" />
                                <s:select id ="ethnicCode" name="patient.ethnicCode" headerKey="" headerValue="--Select--"
                                          list="#ethnicCodeValues"  cssClass="form-control"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <p class="form-control-static"><c:out value="${patient.ethnicCode}"/></p>
                            </s:elseif>
                       </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="countryIdentifier" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.country"/>
                                <span class="required">*</span>
                           <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:select id ="countryIdentifier" name="patient.countryIdentifier" headerValue="-Select-" headerKey=""
                                          list="listOfCountries"  listKey="id" listValue="name"  cssClass="form-control"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <p class="form-control-static"><c:out value="${patient.countryName}"/></p>
                            </s:elseif>
                       </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="zip" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.zipCode"/>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:textfield id ="zip" name="patient.zip" cssClass="form-control" />
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                               <p class="form-control-static"> <c:out value="${patient.zip}"/></p>
                            </s:elseif>
                       </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="registrationDate" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.registrationDate"/>:
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-2">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                            <div id="datetimepicker" class="datetimepicker input-append">
                                <s:textfield data-format="MM/dd/yyyy" placeholder="mm/dd/yyyy" id ="registrationDate" name="patient.registrationDate" cssClass="form-control"/>
                                <%-- <a href="javascript:showCal('Cal1')"><img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) --%>
                                <span class="add-on btn-default"><i class="fa-calendar"></i></span> </div> 
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                               <p class="form-control-static"> <c:out value="${patient.registrationDate}"/></p>
                            </s:elseif>
                      </div>
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="paymentMethodCode" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.methodOfPayment"/>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                       <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:set name="paymentCodeValues" value="@gov.nih.nci.pa.enums.PaymentMethodCode@getDisplayNames()" />
                                <s:select id ="paymentMethodCode" name="patient.paymentMethodCode" headerKey="" headerValue="--Select--"
                                          list="#paymentCodeValues"  cssClass="form-control"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                                <p class="form-control-static"><c:out value="${patient.paymentMethodCode}"/></p>
                            </s:elseif>
                      </div>
                </div>
                    
                    <s:if test="%{showSite}">
	                   <div class="form-group">
	                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}"><div class="padme5"></div></s:if>
	                            <s:if test="%{currentAction != 'retrieve'}"><label for="sitedisease" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
	                            <fmt:message key="patient.sitedisease"/><span class="required">*</span><span class="required">*</span>
	                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
	                       
	                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
	                                <div id="loadSiteDetails" >
	                                     <%@ include file="/WEB-INF/jsp/nodecorate/displaySiteDisease.jsp" %>
	                                </div>
	                            </s:if>
	                            <s:elseif test="%{currentAction == 'retrieve'}">
	                               <p class="form-control-static"> <c:out value="${patient.siteDiseasePreferredName}"/></p>
	                            </s:elseif>
                                    
                       </div>
                    </s:if>
                    
                   <div class="form-group">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}"><div class="padme5"></div></s:if>
                            <s:if test="%{currentAction != 'retrieve'}"><label for="disease" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                            <fmt:message key="patient.disease"/><span class="required">*</span><s:if test="%{showSite}"><span class="required">*</span></s:if>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <div id="loadDetails" >
                                     <%@ include file="/WEB-INF/jsp/nodecorate/displayDisease.jsp" %>
                                </div>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                               <p class="form-control-static"> <c:out value="${patient.diseasePreferredName}"/></p>
                            </s:elseif>
                                
                </div>
                <div class="form-group">
                            <s:if test="%{currentAction != 'retrieve'}"><label for="organizationName" class="col-xs-4 control-label"></s:if>
                            <s:else><p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"></s:else>
                                <fmt:message key="patient.organizationName"/>:
                                <span class="required">*</span>
                            <s:if test="%{currentAction != 'retrieve'}"></label></s:if>
                            <s:else></p></s:else>
                        <div class="col-xs-3">
                            <s:if test="%{(currentAction == 'create') || (currentAction == 'update')}">
                                <s:select id="organizationName" name="patient.studySiteId" list="listOfStudySites" headerKey="" 
                                          listKey="ssIi" listValue="orgName" headerValue="--Select--"  cssClass="form-control"/>
                            </s:if>
                            <s:elseif test="%{currentAction == 'retrieve'}">
                               <p class="form-control-static"> <c:out value="${patient.organizationName}"/></p>
                            </s:elseif>
                       </div>
                </div>
                    <s:if test="%{currentAction == 'retrieve'}">
                    <div class="form-group">
                            <p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"><fmt:message key="patient.userCreated"/>:</p>
                        <div class="col-xs-3">                          
                            <p class="form-control-static"><c:out value="${patient.userCreated}"/></p>
                       </div>
                    </div>
                    <div class="form-group">
                            <p style = "inline-block;font-weight: 700;" class="col-xs-4 control-label"><fmt:message key="patient.lastUpdateDateTime"/>:</p>
                       <div class="col-xs-3">                        
                            <p class="form-control-static"><c:out value="${patient.dateLastUpdated}"/></p>
                       </div>
                    </div>
                    </s:if>
            </s:form>
            <div class="form-group">
			<div class="col-xs-4 col-xs-offset-4">
				<s:if test="%{currentAction == 'create'}">
					<button id="mainActionBtn" type="button" class="btn btn-icon btn-primary mr20" onclick="handleAddAction()">
						<i class="fa-floppy-o"></i>Save
					</button>
					<button type="button" class="btn btn-icon btn-primary mr20"	onclick="handleCancelAction()">
						<i class="fa-times-circle"></i>Cancel
					</button>
				</s:if>
				<s:elseif test="%{currentAction == 'retrieve'}">
					<button type="button" class="btn btn-icon btn-primary mr20"	onclick="handleCancelAction()">
						<i class="fa-arrow-circle-left"></i>Back
					</button>
					<s:if test="%{#session['notCtepDcpTrial'] || #session['superAbs']}">
						<button type="button" class="btn btn-icon btn-primary mr20"	onclick="handleUpdateAction()">
							<i class="fa-pencil"></i>Edit
						</button>
					</s:if>
				</s:elseif>
				<s:elseif test="%{currentAction == 'update'}">
					<button id="mainActionBtn" type="button" class="btn btn-icon btn-primary mr20"	onclick="handleEditAction()">
						<i class="fa-floppy-o"></i>Save
					</button>
					<button type="button" class="btn btn-icon btn-primary mr20"	onclick="handleCancelAction()">
						<i class="fa-times-circle"></i>Cancel
					</button>
				</s:elseif>
			</div>
		</div>
        </div>
    </body>
