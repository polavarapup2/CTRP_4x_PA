<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	style="min-width: 0px !important;">
<head>
<title>Person Details</title>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>

<script type="text/javascript" language="javascript">            
            function displayOrgDetails(orgID) {
                var width = 650;
                var height = 500;
                /*if (Prototype.Browser.IE) {
                    width = 670;
                    height = 500;                   
                }*/
                showPopWin('organizationsSearchshowDetailspopup.action?orgID='+orgID, width, height, '', 'Organization Details');               
            }            
        </script>
</head>
<body>
      <div class="modal-body">
        <div class="row form-horizontal">
        	<div class="col-xs-6">
	            <div class="form-group m0">
	              <label class="col-xs-4 ">Prefix</label>
	              <div class="col-xs-8"><s:property value="person.preFix!=null?person.preFix:'N/A'" /></div>
	            </div>
	            <div class="form-group m0">
	              <label class="col-xs-4 ">First Name</label>
	              <div class="col-xs-8"><s:property value="person.firstName" /></div>
	            </div>
	            <div class="form-group m0">
	              <label class="col-xs-4 ">Middle Name</label>
	              <div class="col-xs-8"><s:property value="person.middleName!=null?person.middleName:'N/A'" /></div>
	            </div>
	            <div class="form-group m0">
	              <label class="col-xs-4 ">Last Name</label>
	              <div class="col-xs-8"><s:property	value="person.lastName" /></div>
	            </div>
          	</div>
          	 
          	<div class="col-xs-6">
	            <div class="form-group m0">
	              <label class="col-xs-4 ">PO ID</label>
	              <div class="col-xs-8"><s:property value="person.id" /></div>
	            </div>
	            <div class="form-group m0">
	              <label class="col-xs-4 ">CTEP ID</label>
	              <div class="col-xs-8"><s:property value="person.ctepId!=null?person.ctepId:'N/A'" /></div>
	            </div>
	            <div class="form-group m0">
	              <label class="col-xs-4 "></label>
	              <div class="col-xs-8"></div>
	            </div>
	            <div class="form-group m0">
	              <label class="col-xs-4 ">Role</label>
	              <div class="col-xs-8"><c:forEach
						items="${person.roles}" var="role">
						<c:out value="${role}" />
						<br />
					</c:forEach> <c:if test="${empty person.roles}">N/A</c:if></div>
	            </div>
           </div>
           </div>
           <div class="row form-horizontal">
          	<div class="col-xs-6">
            <div class="form-group m0">
              <label class="col-xs-4 ">Address</label>
              <div class="col-xs-8"><s:property
					value="person.streetAddress" /> <s:if
					test="person.streetAddress2!=null">
					<br />
					<s:property value="person.streetAddress2" />
				</s:if></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4 ">City</label>
              <div class="col-xs-8"><s:property value="person.city" /></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4 ">Postal Code</label>
              <div class="col-xs-8"><s:property	value="person.zip" /></div>
            </div>
            <div class="form-group m0">
              <label class="col-xs-4 ">Country</label>
              <div class="col-xs-8"><s:property	value="person.country" /></div>
            </div>
          </div>
          </div>
        <div class="row form-horizontal">
		<s:if test="!person.organizations.empty">
			<h3 class="heading mt20"><span>Organization Affiliation</span></h3>
			<s:set name="orgs"
					value="person.organizations" scope="request" />
			 <display:table
				class="data table table-striped" sort="list" pagesize="3" uid="row" name="orgs"
				export="false"
				requestURI="personsSearchshowDetailspopup.action">
				
				<display:column escapeXml="false" title="PO ID"
					headerClass="sortable">
					<a href="javascript:void(0);" onclick="displayOrgDetails(${row.identifier.extension});"><c:out
						value="${row.identifier.extension}" />
					</a>
				</display:column>
				<display:column escapeXml="true" title="Name" property="name.part[0].value"
					headerClass="sortable" />
			</display:table>
		</s:if>
		<hr class="spacer"/>
      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-icon btn-default" data-dismiss="modal" onclick="window.top.hidePopWin(true);"><i class="fa-times"></i>Close</button>
      </div>
</body>
</html>