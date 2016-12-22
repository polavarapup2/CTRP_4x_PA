<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trial.description" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<c:url value="/protected/studyOverallStatushistorypopup.action" var="lookupUrl" />

<script type="text/javascript">
   
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();        
    }
    
    function handleAction() {
        document.trialDescription.action="trialDescriptionupdate.action";
        document.trialDescription.submit();
    }  
</script>
     
</head>
<body>
<h1><fmt:message key="trial.description" /></h1>
<c:set var="topic" scope="request" value="abstractdescription"/>
<c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
<c:set var="mandatoryText" value="${!sessionScope.trialSummary.proprietaryTrial?'Mandatory at Abstraction Validation':''}" scope="request"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <s:url id="cancelUrl" namespace="/protected" action="trialDescriptionquery"/>
<div class="box">
<pa:sucessMessage/>
<pa:failureMessage/>
<s:form name="trialDescription" validate="true" method="POST">
    <s:token/>
    <pa:studyUniqueToken/>
    <s:hidden name="primary" id="primary"/>
    <s:hidden name="secondary" id="secondary"/>
    <s:hidden name="ternary" id="ternary"/>
    <s:hidden name="studyObjectiveIip" id="studyObjectiveIip"/>
    <s:hidden name="studyObjectiveIis" id="studyObjectiveIis"/>
    <s:hidden name="studyObjectiveIit" id="studyObjectiveIit"/>
     <s:if test="hasActionErrors()">
            <div class="error_msg"><s:actionerror/></div>
        </s:if>
    <h2><fmt:message key="trial.description" /></h2>
    <table class="form">
         <tr> 
                <td/>
                <td class="info" colspan="2">${mandatoryText}</td>
       </tr>
        <tr>
            <td class="label">
            <label for="trialBriefTitle">
                <fmt:message key="trial.briefTitle"/> <span class="notRequired">${asterisk}</span>
            </label>
            </td>
            <td colspan="2" class="value"><s:textarea id="trialBriefTitle" name="trialBriefTitle" maxlength="300" cssClass="charcounter" 
                cssStyle="width:606px" rows="4" /></td>
        </tr> 
        <tr> 
                <td/>
                <td class="info" colspan="2">${mandatoryText}</td>
       </tr>
          <tr>
            <td class="label">
            <label for="trialBriefSummary">
                <fmt:message key="trial.briefSummary"/><span class="notRequired">${asterisk}</span>
            </label>
            </td>
            <td colspan="2" class="value">
            <s:textarea id="trialBriefSummary" maxlength="5000" cssClass="charcounter" name="trialBriefSummary" cssStyle="width:606px" rows="20"/></td>
        </tr>        
        <tr>
        <th colspan="2"><fmt:message key="trial.detailedDescription"/></th>
        </tr>
     <tr>
        <td class="label">
           <label for="scientificDescription">
                    <fmt:message key="trial.scientificDescription"/>
           </label>
         </td>
         <td class="value">
        <s:textarea id="scientificDescription" name="outline" cssStyle="width:606px" rows="20" maxlength="32000" cssClass="charcounter"/> 
        <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>outline</s:param>
                </s:fielderror>                            
            </span> 
        </td>
    </tr>
    </table>
   
<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <pa:scientificAbstractorDisplayWhenCheckedOut>
                        <li><a href="javascript:void(0)" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a>
                      <pa:cancelBtn cancelUrl="${cancelUrl}"/>
                        </li>
                    </pa:scientificAbstractorDisplayWhenCheckedOut>
                </ul>   
            </del>

        </div>          
</s:form>
</div>
</body>
</html>
