<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="disclaimer.page.title" /></title>
<s:head />
</head>
<div class="row">
	<div class="col-xs-8 col-xs-offset-2">
		<h3 class="heading align-center">
			<span>Incomplete Account Error</span>
		</h3>
	</div>	
</div>

<c:url value="/registerUserexistingLdapAccount.action" var="regUrl" />
<div class="row">
    <div class="col-xs-8 col-xs-offset-2">
        <div class="alert alert-danger">
            It appears you have a valid account in NCI network; however, your account setup in NCI CTRP Registration Site 
            does not appear to have been completed. It is required in order to use NCI CTRP Registration Site
            successfully. Please follow <a href="${regUrl}">this link</a> to set up your account.   
        </div>
    </div>  
</div>

<script>

jQuery(function() {
	jQuery('#nav').hide();
	
})

</script>