<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${sessionScope.adminUsers != null && fn:length(sessionScope.adminUsers) > 0 && registryUserWebDTO.affiliatedOrganizationId != null}">
       <div class="form-group">
       		<div class="col-xs-8"> 
       			<button type="button" class="btn btn-icon btn-primary" onclick="viewAdmin();"><i class="fa-users"></i>View Admins</button>
       		</div>
        </div>         
 </c:if>
 <c:if test="${requestScope.orgSelected != null && fn:length(sessionScope.adminUsers) == 0}">
     <div class="form-group"> 
     	<label class="col-xs-4 control-label">Request for Admin Access</label>
     	<div class="col-xs-4">
        <s:checkbox name="registryUserWebDTO.requestAdminAccess" fieldValue="true"/>
        </div>
    </div>
 </c:if>
 
