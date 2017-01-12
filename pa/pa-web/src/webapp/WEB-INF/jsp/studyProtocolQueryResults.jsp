<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:if test="records != null">    
    <h2>Search Results</h2>
    <s:form name="sForm">
        <c:if test="${empty isBare}">  
            <s:token/>
        </c:if>
        <s:actionerror/>
        <s:set name="records" value="records" scope="request"/>
        <c:set var="requestURI" value="studyProtocolquery.action" scope="request"/>
        <c:set scope="request"  var="displayTableUID" value="row"/> 
        <s:hidden name="checkInReason"/>
        <jsp:include page="/WEB-INF/jsp/studyProtocolQueryResultsTable.jsp"/>        
    </s:form>    
</s:if>