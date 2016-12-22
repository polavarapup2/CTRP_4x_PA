<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" style="min-width: auto !important; min-width: initial !important;">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
</head>
<body style="min-width: auto !important; min-width: initial !important;">
    <div class="box">
        <pa:sucessMessage/>
        <pa:failureMessage/>
        <s:form>
            <s:actionerror/>
            <s:set name="nctCtGovImportLogs" value="nctCtGovImportLogs" scope="request"/>
            <table class="form">
                <tr>
                    <td scope="row" class="label" nowrap="nowrap" width="0">
                        <label for="nciIdentifier"> <fmt:message key="ctgov.import.logs.details.nciIdentifier"/></label>
                    </td>
                    <td><c:out value="${nciID}"/></td>
                </tr>
                <tr>
                    <td scope="row" class="label" nowrap="nowrap" width="0">
                        <label for="nctIdentifier"> <fmt:message key="ctgov.import.logs.details.nctIdentifier"/></label>
                    </td>
                    <td><c:out value="${nctID}"/></td>
                </tr>
                <tr>
                    <td scope="row" class="label" nowrap="nowrap" width="0">
                        <label for="trialTitle"> <fmt:message key="ctgov.import.logs.details.trialTitle"/></label>
                    </td>
                    <td><c:out value="${title}"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <display:table class="data" sort="list" pagesize="10" id="row" defaultorder="descending" 
                        defaultsort="3"
                        name="nctCtGovImportLogs" export="true" requestURI="ctGovImportLogshowDetailspopup.action">
                            <display:setProperty name="export.xml" value="false" />
                            <display:setProperty name="export.excel.filename" value="ClinicalTrials.Gov.ImportLog.xls" />
                            <display:setProperty name="export.excel.include_header" value="true" />
                            <display:setProperty name="export.csv.filename" value="ClinicalTrials.Gov.ImportLog.csv" />
                            <display:setProperty name="export.csv.include_header" value="true" />
                            <display:column escapeXml="true" title="Action" property="action" sortable="true" />
                            <display:column escapeXml="true" title="User" property="userCreated" sortable="true" />
                            <display:column title="Date/Time" format="{0,date,MM/dd/yyyy hh:mm aaa}" property="dateCreated" sortable="true" />
                            <display:column escapeXml="true" title="Import Status" property="importStatus" sortable="true" />
                            <display:column escapeXml="true" title="Ack. Required?" property="ackPending" sortable="true"/>
                            <display:column escapeXml="true" title="Ack. Performed?" property="ackPerformed" sortable="true"/>                                               
                        </display:table>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
</body>
</html>
    

