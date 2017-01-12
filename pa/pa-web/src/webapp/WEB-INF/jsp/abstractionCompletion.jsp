<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Abstraction Validation</title>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <s:head />
        <script type="text/javascript" language="javascript">
            function generateReport(pid) {
                document.aForm.target = "CLinical Trial XML Generation";
                document.aForm.action = "${pageContext.request.contextPath}/protected/ajaxCTGovgenerateXML.action?studyProtocolId=" + pid;
                document.aForm.submit();
            }
            
            function generateTSR() {
                document.aForm.target = "TSR";
                document.aForm.action = "${pageContext.request.contextPath}/protected/ajaxAbstractionCompletionviewTSR.action";
                document.aForm.submit();
            }
            
            function generateTSRWord() {
                document.aForm.target = "TSR";
                document.aForm.action = "${pageContext.request.contextPath}/protected/ajaxAbstractionCompletionviewTSRWord.action";
                document.aForm.submit();
            }
    </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="validateabstract"/>
        <h1>Abstraction Validation</h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <c:set var="abstractionSuccess" value="${empty requestScope.failureMessage}"/>
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <c:if test="${abstractionSuccess}">
                <s:form name="aForm">
                    <s:actionerror/>
                    <pa:studyUniqueToken/>
                   <s:if test="%{absAdminError==true}">
                            <h2>
                                Abstraction validation failed. Please check Admin Data Menu error(s).
                            </h2>
                        <s:if test="abstractionAdminList != null">
                            <s:set name="abstractionAdminList" value="abstractionAdminList" scope="request"/>
                            <display:table name="abstractionAdminList" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="false">
                                <display:column decorator="gov.nih.nci.pa.decorator.HtmlEscapeDecorator" escapeXml="false" title="Description" property="errorDescription" sortable="true" headerClass="sortable" />
                                <display:column decorator="gov.nih.nci.pa.decorator.HtmlEscapeDecorator" escapeXml="false" title="Comment" property="comment" sortable="true" headerClass="sortable" />
                            </display:table>
			    <br/>
                        </s:if>   
                    </s:if>
                   
                    <s:if test="%{absScientificError==true}">
                       <h2>
                           Abstraction validation failed. Please check Scientific Data Menu error(s).
                       </h2>
                        <s:if test="abstractionScientificList != null">
                            <s:set name="abstractionScientificList" value="abstractionScientificList" scope="request"/>
                            <display:table name="abstractionScientificList" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="false">
                                <display:column decorator="gov.nih.nci.pa.decorator.HtmlEscapeDecorator" escapeXml="false" title="Description" property="errorDescription" sortable="true" headerClass="sortable" />
                                <display:column decorator="gov.nih.nci.pa.decorator.HtmlEscapeDecorator" escapeXml="false" title="Comment" property="comment" sortable="true" headerClass="sortable" />
                            </display:table>
			   <br/>
                        </s:if>   
                    </s:if>
                     
                    <s:if test="absWarningList != null and absWarningList.size > 0"> 
                        <h2>                           
                            Abstraction Validation Warning(s).
                        </h2>                    
                         <s:if test="absWarningList != null">                                              
                            <s:set name="absWarningList" value="absWarningList" scope="request"/>          
                            <display:table name="absWarningList" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="false">                   
                                <display:column decorator="gov.nih.nci.pa.decorator.HtmlEscapeDecorator" escapeXml="false" title="Description" property="errorDescription" sortable="true" headerClass="sortable" />
                                <display:column decorator="gov.nih.nci.pa.decorator.HtmlEscapeDecorator" escapeXml="false" title="Comment" property="comment" sortable="true" headerClass="sortable" />
                            </display:table>   
                        </s:if> 
                    </s:if>
                  <s:if test="%{abstractionError==false}">
                    <h2>
                        Abstraction is valid.
                    </h2>
                  </s:if>
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <s:if test="abstractionError == false">
                                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                                        <li><a href="javascript:void(0)" class="btn" onclick="generateReport('${sessionScope.trialSummary.studyProtocolId}');"><span class="btn_img"><span class="save">View XML</span></span></a></li>
                                    </c:if>
                                    <li><a href="javascript:void(0)"  class="btn" onclick="generateTSR();"><span class="btn_img"><span class="save">View TSR</span></span></a></li>
                                </s:if>
                            </ul>
                        </del>
                    </div>
                </s:form>
            </c:if>    
        </div>
    </body>
</html>
