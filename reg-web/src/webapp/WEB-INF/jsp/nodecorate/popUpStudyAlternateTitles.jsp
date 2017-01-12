<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
    </head>
    <body>        
        <div class="box">            
            <display:table name="${requestScope.studyAlternateTitles}" id="row" class="data" sort="list"  pagesize="10" requestURI="" export="false">
                <display:column escapeXml="true" sortable="false">
                    <c:out value="${row.alternateTitle.value}"/>
                </display:column>                
            </display:table>
        </div>
    </body>
</html>     
        