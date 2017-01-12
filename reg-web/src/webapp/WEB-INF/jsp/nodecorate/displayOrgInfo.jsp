<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>Organization Details</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includeextrajs.jsp"%>
</head>
      <div class="modal-body">
      <div class="row form-horizontal">
          <div class="col-xs-6">
            <div class="form-group m0">
              <label class="col-xs-4">Name</label>
              <div class="col-xs-8"><s:property	value="organization.name" /></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">Family Name</label>
              <div class="col-xs-8"><c:forEach
					items="${organization.families}" var="family">
					<c:out value="${family.value}" />
					<br />
				</c:forEach> <c:if test="${empty organization.families}">N/A</c:if></div>
            </div>
          <div class="form-group m0">
              <label class="col-xs-4"></label>
              <div class="col-xs-8"></div>
            </div>
            
          </div>
          <div class="col-xs-6">
            <div class="form-group m0">
              <label class="col-xs-4">PO ID</label>
              <div class="col-xs-8"><s:property
					value="organization.id" /></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">CTEP ID</label>
              <div class="col-xs-8"><s:property
					value="organization.ctepId" /> <c:if
					test="${empty organization.ctepId}">N/A</c:if></div>
            </div>
            
          </div>
        </div>
        <div class="row form-horizontal">
          <div class="col-xs-6">
            <div class="form-group m0">
              <label class="col-xs-4">Address</label>
              <div class="col-xs-8"><s:property
					value="organization.address1" /> <s:if
					test="organization.address2!=null">
					<br />
					<s:property value="organization.address2" />
				</s:if></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">City</label>
              <div class="col-xs-8"><s:property
					value="organization.city" /></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">State</label>
              <div class="col-xs-8"><s:property
					value="organization.state" /></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">Postal Code</label>
              <div class="col-xs-8"><s:property
					value="organization.zip" /></div>
            </div>
            <div class="form-group m0">
                <label class="col-xs-4">Country</label>
                <div class="col-xs-8"><s:property
					value="organization.country" /></div>
              </div>
            </div>
          <div class="col-xs-6">
              <div class="form-group m0">
                <label class="col-xs-4">Type</label>
                <div class="col-xs-8"><c:forEach
					items="${organization.organizationTypes}" var="orgType">
					<c:out value="${orgType}" />
					<br />
				</c:forEach> <c:if test="${empty organization.organizationTypes}">N/A</c:if></div>
              </div>
            </div>
          </div>
          <div class="row form-horizontal">
          <div class="col-xs-6">
            <div class="form-group m0">
              <label class="col-xs-4">Phone</label>
              <div class="col-xs-8"><c:forEach
					items="${organization.contactInfo.phones}" var="val">
					<c:out value="${val}" />
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.phones}">N/A</c:if></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">Fax</label>
              <div class="col-xs-8"><c:forEach
					items="${organization.contactInfo.faxes}" var="val">
					<c:out value="${val}" />
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.faxes}">N/A</c:if></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">Email</label>
              <div class="col-xs-8"><c:forEach
					items="${organization.contactInfo.emails}" var="val">
					<a href="mailto:<c:out value="${val}" />"><c:out value="${val}" /></a>
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.emails}">N/A</c:if></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4">Website</label>
              <div class="col-xs-8"><c:forEach
					items="${organization.contactInfo.websites}" var="val">
					<a href="<c:out value="${val}" />"><c:out value="${val}" /></a>
					<br />
				</c:forEach> <c:if test="${empty organization.contactInfo.websites}">N/A</c:if></div>
            </div>
           
          </div>
          <div class="col-xs-6">
              
           </div>
         </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-icon btn-default" data-dismiss="modal" onclick="window.parent.hidePopWin(true);"><i class="fa-times"></i>Close</button>
    </div>
</body>
</html>