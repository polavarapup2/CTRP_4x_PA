<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<body>
<s:form name="adminUser" method="POST" >
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" pagesize="10" id="row"
             name="${sessionScope.adminUsers}" export="false">
            <display:column titleKey="admin.userFirstName" property="firstName" headerClass="sortable"/>
            <display:column titleKey="admin.userLastName" property="lastName" headerClass="sortable"/>
</display:table>
<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li>       
                        <s:a href="javascript:void(0)" cssClass="btn" onclick="parent.hidePopWin(false);"><span class="btn_img"><span class="close">Close</span></span></s:a>
                    </li> 
                </ul>   
            </del>
</div>
</s:form>
</body>  