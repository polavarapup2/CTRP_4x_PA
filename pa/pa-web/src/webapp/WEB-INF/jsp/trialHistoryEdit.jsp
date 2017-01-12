<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialHistory.edittitle"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
    <script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
    addCalendar("Cal1", "Select Date", "trialHistoryWbDto.amendmentDate", "editForm");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");
    
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();        
}

function handleAction(){
 document.forms[0].action="trialHistoryupdate.action";
 document.forms[0].submit(); 
  
} 
</SCRIPT>

<body>
<c:set var="topic" scope="request" value="trialhistory"/>
 <h1><fmt:message key="trialHistory.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form name="editForm">
        <s:token/>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialHistory.edittitle" /></h2>
    <s:set name="reasonCodeValues" value="@gov.nih.nci.pa.enums.AmendmentReasonCode@getDisplayNames()" />   
     <s:hidden name="trialHistoryWbDto.identifier"/> 
    <table class="form">  
            <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="trialHistory.submissionNumber"/></label>
                        </td>
                        <td class="value">      
                          <c:out value="${trialHistoryWbDto.submissionNumber}"/>
                          
                          </td>
                    </tr>
                    <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="trialHistory.submissionDate"/></label>
                        </td>
                        <td class="value">      
                           <c:out value="${trialHistoryWbDto.submissionDate}"/>
                          </td>
                    </tr> 
        <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="trialHistory.amendmentReasonCode"/>:<span class="required">*</span></label>
                        </td>
                        <td class="value">      
                          <s:select headerKey="" headerValue="--Select--" name="trialHistoryWbDto.amendmentReasonCode" list="#reasonCodeValues"/>
                          <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialHistoryWbDTO.amendmentReasonCode</s:param>
                               </s:fielderror>                            
                         </span>
                          </td>
                    </tr>
                    <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="trialHistory.amendmentNumber"/></label>
                        </td>
                        <td class="value">
                            <s:textfield  name="trialHistoryWbDto.amendmentNumber" maxlength="50" cssStyle="width:150px"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialHistoryWbDTO.amendmentNumber</s:param>
                               </s:fielderror>                            
                         </span>
                        </td>
                    </tr>
                     <tr>
                      <td class="label"><s:label><fmt:message key="trialHistory.amendmentDate"/></s:label><span class="required">*</span></td>
                      <td class="value">
                        <s:textfield name="trialHistoryWbDto.amendmentDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                          <a href="javascript:showCal('Cal1')">
                              <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                          </a>
                        <span class="formErrorMsg"><s:fielderror>
                            <s:param>trialHistoryWbDto.amendmentDate</s:param>
                        </s:fielderror></span>
                      </td> 
                  </tr>
    </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>                
                    <li><s:a href="trialHistory.action" cssClass="btn"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a></li>
                </ul>   
            </del>
        </div>             
    </s:form>
   </div>
 </body>
 </html>