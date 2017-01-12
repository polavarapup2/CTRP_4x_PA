<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trial.data.verification.page.title"/></title>
    <s:head/>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
     <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js?534785924'/>"></script>
     <script type="text/javascript">
        function addAction(url){
                if (confirm("<fmt:message key='trial.data.verification.Confirm' />")) {
                    var pid = getUrlVars()["studyProtocolId"];
                    document.forms[0].setAttribute("action", url+"?studyProtocolId="+pid);
                    document.forms[0].submit();    
                }
            }
            
            function getUrlVars() {
                var vars = {};
                var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
                    vars[key] = value;
                });
                return vars;
            }
            
            function cancelAction(url){                
                    document.forms[0].setAttribute("action", url);
                    document.forms[0].submit();      
            }
            </script>
</head>
    <body>
        <h1 class="heading"><span><fmt:message key="trial.data.verification.title" /></span></h1> 
        <c:set var="topic" scope="request" value="dataverification"/>
        <fmt:message key="studyAlternateTitles.text" var="title" />
        <reg-web:sucessMessage/>
        <div class="row form-horizontal details">
                <s:token/>                
                <reg-web:valueRowDiv labelKey="view.trial.identifier" strong="true" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.assignedIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.assignedIdentifier}"/>
                            </c:if>
                </reg-web:valueRowDiv>
                 <reg-web:valueRowDiv labelKey="view.trial.nctNumber" noLabelTag="true">
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                    <c:out value="${requestScope.trialDTO.nctIdentifier }"/>
                                </c:if>
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                    <c:out value="${requestScope.nctIdentifier }"/>
                                </c:if>
                 </reg-web:valueRowDiv>
                <reg-web:valueRowDiv labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.leadOrgTrialIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.leadOrgTrialIdentifier}"/>
                            </c:if>
                </reg-web:valueRowDiv>

                 <reg-web:valueRowDiv labelKey="view.trial.title" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.officialTitle.value}"/>
                            <c:if test="${not empty trialSummary.studyAlternateTitles}">
                               <a href="javascript:void(0)" title="${title}" onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>                                                   
                            </c:if>
                 </reg-web:valueRowDiv>
                 <s:set name="webDTOList" value="webDTOList" scope="request"/>
               	<display:table name="webDTOList" htmlId="webDTOList" id="row" class="data table table-striped table-bordered sortable" sort="list" pagesize="200" requestURI="trialDataVerification.action">
                      <display:column escapeXml="true" property="updatedDate" sortable="false" titleKey="trial.data.verification.date" headerClass="sortable"/>
                      <display:column escapeXml="true" property="verificationMethod" sortable="false" titleKey="trial.data.verification.verificationMethod" headerClass="sortable"/>
                      <display:column escapeXml="true" property="userLastUpdated" sortable="false" titleKey="trial.data.verification.verifiedBy" headerClass="sortable"/>        
               </display:table>
               <br/>
               <br/>
               <h3 class="heading"><span>Add Data Verification Record</span></h3>
               <br/>
               <br/>
               <p class="info"><strong><fmt:message key="trial.data.verification.review" /></strong></p>                     
               
               <div class="align-center button-row">
               	  <s:url id="addUrl" namespace="/protected" action="trialDataVerificationsave"/>
			      <s:a onclick="javascript:addAction('%{addUrl}');" href="javascript:void(0)"><button type="button" class="btn btn-icon btn-primary"><i class="fa-floppy-o"></i>Save Verification Record</button></s:a>
			      <s:url id="cancelUrl" namespace="/protected" action="searchTrial"/>
			      <s:a onclick="javascript:cancelAction('%{cancelUrl}');" href="javascript:void(0)"><button type="button" class="btn btn-icon btn-default" ><i class="fa-times-circle"></i>Cancel</button></s:a>
		    </div>
        </div> 
    </body>
</html>
