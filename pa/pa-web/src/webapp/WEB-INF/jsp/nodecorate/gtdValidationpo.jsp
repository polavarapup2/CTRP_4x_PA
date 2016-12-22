<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/ajaxGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>

<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>

<script language="javascript" type="text/javascript">

    function lookup4loadresponsibleparty1() {
       try{     
            var orgid = document.getElementById('trialValidationquery_gtdDTO_sponsorIdentifier').value;
        } catch(err) {
            orgid = document.getElementById('generalTrialDesignquery_gtdDTO_sponsorIdentifier').value;
        }
        showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
    }
    
    function lookup4loadleadorg() {
        showPopup('${lookupOrgUrl}', loadLeadOrgDiv, 'Select Organization');
    }
    
    function lookup4loadleadpers() {
        showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
    }
    
    function lookup4sponsor() {
        showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
    } 
    
    function lookup4loadSummary4Sponsor(){
        showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
    }   
    
    function loadLeadOrgDiv() { 
        var url = 'ajaxTrialValidationdisplayLeadOrganization.action';
        var params = { orgId: orgid };
        var div = $('loadOrgField');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        var aj = callAjaxPost(div, url, params);
    }
    
    function loadLeadPersDiv() {
        var url = 'ajaxTrialValidationdisplayLeadPrincipalInvestigator.action';
        var params = { persId: persid };
        var div = $('loadPersField');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        var aj = callAjaxPost(div, url, params);
        
        var partyType = $F('gtdDTO.responsiblePartyType');
        if (partyType=='pi') {                
        	$('gtdDTO.responsiblePersonIdentifier').value=persid;
            $('gtdDTO.responsiblePersonName').value=selectedName;
        }
    }
    
    function loadSponsorDiv() {
        var url = 'ajaxTrialValidationdisplaySelectedSponsor.action';
        var params = { orgId: orgid };               
        var div = $('loadSponsorField');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Sponsor...</div>';
        var aj = callAjaxPost(div, url, params);
        
        var partyType = $F('gtdDTO.responsiblePartyType');
        if (partyType=='si') {                
        	 $('gtdDTO.responsiblePersonAffiliationOrgId').value=orgid;
             $('gtdDTO.responsiblePersonAffiliationOrgName').value=orgname;
        }
    }
    
    
    function lookup4loadresponsiblepartygenericcontact() {
        var orgid = $('sponsorIdentifier').value;
        showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid+'&type=Responsible Party', createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
    }
    
    
    function toggledisplayDivs(val) {
        var vis = val.value;
        if (vis == 'false') { 
            $('sponsorRespDiv').style.display = 'none'; 
        } else { 
            $('sponsorRespDiv').style.display = ''; 
        }
    }
</script> 

<c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>

<tr>
    <th colspan="2"> Lead Organization/Principal Investigator</th>
</tr>
<s:hidden name="gtdDTO.proprietarytrialindicator" id="gtdDTO.proprietarytrialindicator"/>
<tr>
    <td scope="row" class="label">
       <label for="nciIdentifier">
                Lead Organization
             <span class="required">*</span>
       </label>
     </td>
    <td class="value">
        <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/leadOrg.jsp" %>
        </div>      
    </td>
</tr>


    <tr>
        <td scope="row" class="label">
           <label for="principalInvestigator">
                    Principal Investigator
                 <span class="required">${asterisk}</span>
           </label>
         </td>
        <td class="value">
            <div id="loadPersField">
           <%@ include file="/WEB-INF/jsp/nodecorate/displayLeadPrincipalInvestigator.jsp" %>
            </div>      
        </td>
    </tr>
    <tr> 
        <td colspan="2" class="space">  
           <s:if test="%{gtdDTO.ctGovXmlRequired == true || #session.trialSummary.proprietaryTrial}">
               <div id="sponsorRespDiv" style="display:''">
                   <%@ include file="/WEB-INF/jsp/nodecorate/sponsorRespPartyInfo.jsp" %>
               </div>
            </s:if>
            <s:else>
                <div id="sponsorRespDiv" style="display:none">
                   <%@ include file="/WEB-INF/jsp/nodecorate/sponsorRespPartyInfo.jsp" %>
                </div>
            </s:else>
        </td> 
    </tr>  
