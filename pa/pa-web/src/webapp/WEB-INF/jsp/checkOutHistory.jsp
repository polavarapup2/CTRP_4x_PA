<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="checkOutHistory.title"/></title>
        <s:head />
    </head>
    <body>
        <h1><fmt:message key="checkOutHistory.title"/></h1>
        <c:set var="topic" scope="request" value="checkouthistory"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />

        <div class="box">
            <s:form>
                <h2><fmt:message key="checkOutHistory.list.title"/></h2>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="checkOutList" value="checkOutList" scope="request"/>
                            <display:table name="checkOutList" id="row" class="data" sort="list" pagesize="20" requestURI="checkOutHistory.action"
                                    decorator="gov.nih.nci.pa.decorator.CheckOutHistoryTagDecorator">
                                <display:column escapeXml="true" property="checkOutType" sortable="true"
                                                titleKey="checkOutHistory.checkOutType" headerClass="sortable" />
                                <display:column escapeXml="false" property="checkOutDate" sortable="true"
                                                titleKey="checkOutHistory.checkOutDate" headerClass="sortable" />
                                <display:column escapeXml="true" property="userIdentifier" sortable="true"
                                                titleKey="checkOutHistory.userIdentifier" headerClass="sortable" />
                                <display:column escapeXml="false" property="checkInDate" sortable="true"
                                                titleKey="checkOutHistory.checkInDate" headerClass="sortable" />
                                <display:column escapeXml="true" property="checkInUserIdentifier" sortable="true"
                                                titleKey="checkOutHistory.checkInUserIdentifier" headerClass="sortable" />
                                <display:column escapeXml="true" property="checkInComment" sortable="true"
                                                titleKey="checkOutHistory.checkInComment" headerClass="sortable"/>
                            </display:table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </body>
</html>
