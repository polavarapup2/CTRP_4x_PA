<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="asterisk" value="${!sessionScope.trialSummary.proprietaryTrial?'*':''}" scope="request"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl"/>
<style>

	table.sponsor_respparty * td.label {
	    vertical-align: middle !important;
	}

</style>

<table class="sponsor_respparty">
    <tr>
         <th colspan="2">Sponsor/Responsible Party</th>
    </tr>          
    <tr>
        <td scope="row" class="label">
            <label for="gtdDTO.sponsorName"> Sponsor:<span class="required">${asterisk}</span></label> 
        </td>
        <td class="value">
            <div id="loadSponsorField">
           <%@ include file="/WEB-INF/jsp/nodecorate/displaySponsor.jsp" %>
            </div>      
        </td>
    </tr>   
    <tr>
        <td scope="row" class="label"><label for="gtdDTO.responsiblePartyType">Responsible Party:</label><span class="required">${asterisk}</span></td>
        <td>            
            <s:select   onchange="respPartyTypeChanged()"
                        id = "gtdDTO.responsiblePartyType"
                        headerKey=""
                        headerValue="--Select--"
                        name="gtdDTO.responsiblePartyType"
                        list="#{'sponsor':'Sponsor','pi':'Principal Investigator','si':'Sponsor-Investigator'}"
                        value="gtdDTO.responsiblePartyType"
                        cssStyle="width:206px"
                        />
            <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.responsiblePartyType</s:param>
                    </s:fielderror>                            
            </span>
        </td>
    </tr>
    
    <tr style="display:none" class="resppartysection">
	    <td scope="row" class="label">
	        <label for="gtdDTO.responsiblePersonName">Investigator:<span class="required">*</span></label> 
	    </td>               
	    <td class="value">
	        <div id="loadResponsibleContactField">
		        <s:hidden name="gtdDTO.responsiblePersonIdentifier" id="gtdDTO.responsiblePersonIdentifier"/>
				<table>
				    <tr>
				        <td>
				            <s:textfield name="gtdDTO.responsiblePersonName" id="gtdDTO.responsiblePersonName" size="30"  
				                cssStyle="width:200px; top: -5px; position:relative;" readonly="true" cssClass="readonly"/>
				            <s:if test="gtdDTO.responsiblePersonIdentifier != null">            
					            <a href="javascript:void(0)" onclick="displayPersonDetails($('gtdDTO.responsiblePersonIdentifier').value);">
					                <img src="<%=request.getContextPath()%>/images/details.gif" alt="details"/>
					            </a>
                            </s:if>
				        </td>
				        <td id="investigatorlookupcell"> 
		                  <ul style="margin-top:-1px;">             
		                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupInvestigator();"/>
		                        <span class="btn_img"><span class="person">Look Up Person</span></span></a></li>
		                  </ul>
			            </td>
    			    </tr>
				</table>
				<span class="formErrorMsg" id="responsiblePersonNameErr"> 
					<s:fielderror>
					<s:param>responsiblePersonName</s:param>
					</s:fielderror>                            
				</span>
	        </div>                                    
	    </td>
    </tr>
        
    <tr style="display:none" class="resppartysection">
        <td scope="row" class="label">
           <label for="gtdDTO.responsiblePersonTitle">Investigator Title:<span class="required">*</span></label> 
        </td>
        <td class="value">
            <s:textfield name="gtdDTO.responsiblePersonTitle"  id="gtdDTO.responsiblePersonTitle" maxlength="200" cssStyle="width:200px; top: -5px; position:relative;" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                <s:param>responsiblePersonTitle</s:param>
               </s:fielderror>                            
             </span>
        </td>
    </tr>
    
    <tr style="display:none" class="resppartysection">
        <td scope="row" class="label">
            <label for="gtdDTO.responsiblePersonAffiliationOrgName">Investigator Affiliation:<span class="required">*</span></label> 
        </td>               
        <td class="value">
            <div id="investigatorAffiliationDiv">
                <s:hidden name="gtdDTO.responsiblePersonAffiliationOrgId" id="gtdDTO.responsiblePersonAffiliationOrgId"/>
                <table>
                    <tr>
                        <td>
                            <s:textfield name="gtdDTO.responsiblePersonAffiliationOrgName" id="gtdDTO.responsiblePersonAffiliationOrgName" size="30"  
                                cssStyle="width:200px; top: -5px; position:relative;" readonly="true" cssClass="readonly"/>
                            <s:if test="gtdDTO.responsiblePersonAffiliationOrgId != null && gtdDTO.responsiblePersonAffiliationOrgId != ''">
				                <a href="javascript:void(0)" onclick="displayOrgDetails($('gtdDTO.responsiblePersonAffiliationOrgId').value);">
				                    <img src="<%=request.getContextPath()%>/images/details.gif" alt="details"/>
				                </a>
                            </s:if>
                        </td>
                        <td id="affiliationLookupcell"> 
	                          <ul style="margin-top:-1px;">
	                            <li style="padding-left:0">
				                    <a href="javascript:void(0)" class="btn" onclick="lookupAffiliation();">
				                        <span class="btn_img"><span class="organization">Look Up Organization</span></span>
				                    </a>
	                            </li>
	                          </ul>
                        </td>
                    </tr>
                </table>
                <span class="formErrorMsg" id="affiliationOrgErr"> 
                    <s:fielderror>
                    <s:param>responsiblePersonAffiliationOrgName</s:param>
                    </s:fielderror>                            
                </span>
            </div>                                    
        </td>
    </tr>                    
</table>

<script type="text/javascript">
      
      Event.observe(window, "load", prepareResponsiblePartySection);
      
      function prepareResponsiblePartySection() {
    	  var partyType = $F('gtdDTO.responsiblePartyType');
    	  if (partyType=='' || partyType=='sponsor') {
    		  $$('tr.resppartysection').each(function(el) {el.hide()});
    	  } else {
    		  $$('tr.resppartysection').each(function(el) {el.show()});
    	  }
    	  if (partyType=='pi') {                
              $('investigatorlookupcell').hide();
              $('affiliationLookupcell').show();
          }
    	  if (partyType=='si') {                
              $('investigatorlookupcell').show();
              $('affiliationLookupcell').hide();
          }
      }
      
      function respPartyTypeChanged() {
    	  
    	  $('responsiblePersonNameErr').innerHTML='';
    	  $('affiliationOrgErr').innerHTML='';
    	  
    	  var partyType = $F('gtdDTO.responsiblePartyType');
    	  if (partyType=='' || partyType=='sponsor') {
              $('gtdDTO.responsiblePersonIdentifier').value='';
              $('gtdDTO.responsiblePersonName').value='';
              $('gtdDTO.responsiblePersonTitle').value='';
              $('gtdDTO.responsiblePersonAffiliationOrgId').value='';
              $('gtdDTO.responsiblePersonAffiliationOrgName').value='';
          } else if (partyType=='pi') {    
        	  if ($F('gtdDTO.piIdentifier')=='') {
        		  $('responsiblePersonNameErr').innerHTML='This field cannot be edited. Please select a Principal Investigator in the section above.';
        		  $('piIdentifierErr').innerHTML='Please select a Principal Investigator.';
        	  } 
              copyPiAsRespParty();        	  
              
              if ($F('gtdDTO.responsiblePersonAffiliationOrgId')=='') {
            	  copySponsorAsAffiliation();
              }
              if ($F('gtdDTO.responsiblePersonTitle')=='') {
            	  $('gtdDTO.responsiblePersonTitle').value='Principal Investigator';
              }
          } else if (partyType=='si') {    
        	  if ($F('gtdDTO.responsiblePersonIdentifier')=='') {
        		  copyPiAsRespParty();
        	  }
        	  
        	  if ($F('sponsorIdentifier')=='') {
                  $('affiliationOrgErr').innerHTML='The affiliation cannot be edited. Please select a Sponsor above.';
                  $('sponsorErr').innerHTML='Please select a Sponsor.';
              }
              copySponsorAsAffiliation();
              
              if ($F('gtdDTO.responsiblePersonTitle')=='') {
                  $('gtdDTO.responsiblePersonTitle').value='Principal Investigator';
              }
          }
    	  prepareResponsiblePartySection();
      }
      
      function copyPiAsRespParty() {
    	  $('gtdDTO.responsiblePersonIdentifier').value=$F('gtdDTO.piIdentifier');
          $('gtdDTO.responsiblePersonName').value=$F('principalInvestigator');
      }
      
      function copySponsorAsAffiliation() {
          $('gtdDTO.responsiblePersonAffiliationOrgId').value=$F('sponsorIdentifier');
          $('gtdDTO.responsiblePersonAffiliationOrgName').value=$F('gtdDTO.sponsorName');
      }
      
      function lookupInvestigator() {
          showPopup('${lookupPersUrl}', updateInvestigatorFieldsWithSelection, 'Select Investigator');
      }
      
      function updateInvestigatorFieldsWithSelection() {         
          $('gtdDTO.responsiblePersonIdentifier').value = persid;
          $('gtdDTO.responsiblePersonName').value = selectedName;
      }
      
      function lookupAffiliation() {
    	  showPopup('${lookupOrgUrl}', updateAffiliationWithSelection, 'Select Affiliation Organization');
      }
      
      function updateAffiliationWithSelection() {         
          $('gtdDTO.responsiblePersonAffiliationOrgId').value = orgid;
          $('gtdDTO.responsiblePersonAffiliationOrgName').value=orgname;
      }
      
      
</script>
