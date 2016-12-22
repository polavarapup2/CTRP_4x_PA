<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/orgPoplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/registry/ajaxUsersviewAdminUsers.action" var="displayUrl"/>

<script type="text/javascript" language="javascript">
    var orgid;
    var originalorgid = -1;
    var chosenname;
    
    function setorgid(orgIdentifier, oname) {
        orgid = orgIdentifier;
        chosenname = oname.replace(/&apos;/g,"'");
    }
    
    function handleRegisterUserAction(){
        var form = document.myAccountForm;
        form.page.value = "Submit";
        form.action="registerUserupdateAccount.action";
        form.submit();
    }

    function lookup4AffiliateOrg(orgid, name) {
        if (originalorgid == -1) {
          originalorgid = $('registryUserWebDTO.affiliatedOrganizationId').value;
        }
        if (originalorgid) {
          var r = confirm("Warning: You will lose any existing Site Admin, report viewing access and Accrual Submission privileges if you change your organizaiton affiliation.\n\nSystem will automatically log you out and you will have to re-login.");
          if (r == false) {
            return;
          }
        }
        if(orgid == -1) {
        	//The user has selected to seach.
	        showPopWin('${lookupOrgUrl}', 850, 550, loadAffliatedOrgDiv, 'Select Affiliated Organization');
        } else {
        	setorgid(orgid, name);
        	loadAffliatedOrgDiv();
        }
        $('dropdown-affiliateOrganization').hide();
    }

    function loadAffliatedOrgDiv() {
        $('registryUserWebDTO.affiliatedOrganizationId').value = orgid;
        $('registryUserWebDTO.affiliateOrg').value = chosenname;
        $('registryUserWebDTO.affiliateOrgField').innerHTML = chosenname;
        var  url = '/registry/ajaxUsersloadAdminUsers.action';
        var params = {
            "registryUserWebDTO.affiliatedOrganizationId": orgid
        };
        var div = $('adminAccessDiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        var aj = callAjaxPost(div, url, params);
        return false;
    }
    
    function viewAdmin() {
        var orgId = $('registryUserWebDTO.affiliatedOrganizationId').value ;
        showPopWin('${displayUrl}?registryUserWebDTO.affiliatedOrganizationId='+orgId, 900, 400, loadnothing, 'Affiliated Organization Admin');
    }
    
    function loadnothing() {
        return false;
    }
    
    jQuery(function() {
    	chosenname = $('registryUserWebDTO.affiliateOrg').value;
    	if(chosenname) {
    		$('registryUserWebDTO.affiliateOrgField').innerHTML = chosenname;
    	}
    });
</script>
