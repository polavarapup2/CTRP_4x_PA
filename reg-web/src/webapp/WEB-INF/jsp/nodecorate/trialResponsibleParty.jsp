<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<div class="accordion">
    <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section5"><fmt:message key="submit.trial.sponsorResParty"/><span class="required">*</span></a></div>
        <div id="section5" class="accordion-body in">
            <div class="container"> 
                <div class="form-group">
                    <label for="trialDTO.sponsorName" class="col-xs-4 control-label">Sponsor: <span class="required">*</span></label>
                    <div id="loadSponsorField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialSponsor.jsp"%>
                    </div>                
                </div>
                <div class="form-group">
                    <label for="trialDTO.responsiblePartyType" class="col-xs-4 control-label"><fmt:message key="submit.trial.responsibleParty"/><span class="required">*</span></label>
                    <div class="col-xs-4">
                     <s:select onchange="respPartyTypeChanged()" headerKey="" headerValue="--Select--" id="trialDTO.responsiblePartyType" 
                         name="trialDTO.responsiblePartyType" list="#{'sponsor':'Sponsor','pi':'Principal Investigator','si':'Sponsor-Investigator'}" 
                         value="trialDTO.responsiblePartyType" cssClass="form-control"/>
                     <span class="alert-danger">
                         <s:fielderror>
                             <s:param>trialDTO.responsiblePartyType</s:param>
                         </s:fielderror>
                     </span>
                    </div>    
                </div>   
                <div class="form-group resppartysection" style="display: none;">
                    <label for="trialDTO.responsiblePersonName" class="col-xs-4 control-label">Investigator:<span class="required">*</span></label>
                    <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
                    <div id="loadResponsibleContactField">
                        <div class="col-xs-4">                                    
                            <s:textfield name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName" size="30" 
                                 readonly="true" cssClass="form-control"/>
                            <span class="alert-danger" id="responsiblePersonNameErr"> 
                                <s:fielderror>
                                    <s:param>responsiblePersonName</s:param>
                                </s:fielderror>                            
                            </span>
                        </div>
                        <div class="col-xs-4" id="investigatorlookupcell">
                            <button type="button" class="btn btn-icon btn-default" onclick="lookupInvestigator();"><i class="fa-user"></i>Look Up Person</button>
                        </div>
                    </div>                    
                </div> 
            <div class="form-group resppartysection" style="display: none;">
                <label for="trialDTO.responsiblePersonTitle" class="col-xs-4 control-label">Investigator Title:<span class="required">*</span></label>
                <div class="col-xs-4">
                    <s:textfield name="trialDTO.responsiblePersonTitle"  id="trialDTO.responsiblePersonTitle" maxlength="200" cssClass="form-control" />
                    <span class="alert-danger"> 
                        <s:fielderror>
                            <s:param>responsiblePersonTitle</s:param>
                        </s:fielderror>                            
                    </span>
                </div>
            </div>
            <div class="form-group resppartysection" style="display: none;">
                <label for="trialDTO.responsiblePersonAffiliationOrgName" class="col-xs-4 control-label">Investigator Affiliation:<span class="required">*</span></label>         
                <div id="investigatorAffiliationDiv">
                    <s:hidden name="trialDTO.responsiblePersonAffiliationOrgId" id="trialDTO.responsiblePersonAffiliationOrgId"/>
                    <div class="col-xs-4">
                        <s:textfield name="trialDTO.responsiblePersonAffiliationOrgName" id="trialDTO.responsiblePersonAffiliationOrgName" size="30" 
                             readonly="true" cssClass="form-control"/>
                            <span class="alert-danger" id="affiliationOrgErr"> 
                                <s:fielderror>
                                   <s:param>responsiblePersonAffiliationOrgName</s:param>
                                </s:fielderror>                            
                            </span>
                    </div>
                    <div class="col-xs-4" id="affiliationLookupcell">
                        <button type="button" onclick="lookupAffiliation();" class="btn btn-icon btn-default"><i class="fa-sitemap"></i>Look Up Organization</button>
                    </div>
                </div>    
        </div>
        </div>
    </div>
</div> 
<script type="text/javascript">
      
      Event.observe(window, "load", prepareResponsiblePartySection);
      
      function prepareResponsiblePartySection() {
          var partyType = $F('trialDTO.responsiblePartyType');
          if (partyType=='' || partyType=='sponsor') {
              $$('div.resppartysection').each(function(el) {el.hide()});              
          } else {
              $$('div.resppartysection').each(function(el) {el.show()});
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
          
          var partyType = $F('trialDTO.responsiblePartyType');
          if (partyType=='' || partyType=='sponsor') {
              $('trialDTO.responsiblePersonIdentifier').value='';
              $('trialDTO.responsiblePersonName').value='';
              $('trialDTO.responsiblePersonTitle').value='';
              $('trialDTO.responsiblePersonAffiliationOrgId').value='';
              $('trialDTO.responsiblePersonAffiliationOrgName').value='';
              $('responsiblePersonNameErr').innerHTML='';
              $('affiliationOrgErr').innerHTML='';
          } else if (partyType=='pi') {   
        	  $('affiliationOrgErr').innerHTML='';
              if ($F('trialDTO.piIdentifier')=='') {
                  $('responsiblePersonNameErr').innerHTML='This field cannot be edited. Please select a Principal Investigator in the section above.';
                  $('piIdentifierErr').innerHTML='Please select a Principal Investigator.';
              } 
              copyPiAsRespParty();            
              
              if ($F('trialDTO.responsiblePersonAffiliationOrgId')=='') {
                  copySponsorAsAffiliation();
              }
              if ($F('trialDTO.responsiblePersonTitle')=='') {
                  $('trialDTO.responsiblePersonTitle').value='Principal Investigator';
              }
          } else if (partyType=='si') {    
              if ($F('trialDTO.responsiblePersonIdentifier')=='') {
                  copyPiAsRespParty();
              }
              
              if ($F('trialDTO.sponsorIdentifier')=='') {
                  $('affiliationOrgErr').innerHTML='The affiliation cannot be edited. Please select a Sponsor above.';
                  $('sponsorErr').innerHTML='Please select a Sponsor.';
              }
              copySponsorAsAffiliation();
              
              if ($F('trialDTO.responsiblePersonTitle')=='') {
                  $('trialDTO.responsiblePersonTitle').value='Principal Investigator';
              }
          }
          prepareResponsiblePartySection();
      }
      
      function copyPiAsRespParty() {
          $('trialDTO.responsiblePersonIdentifier').value=$F('trialDTO.piIdentifier');
          $('trialDTO.responsiblePersonName').value=$F('trialDTO.piName');
      }
      
      function copySponsorAsAffiliation() {
          $('trialDTO.responsiblePersonAffiliationOrgId').value=$F('trialDTO.sponsorIdentifier');
          $('trialDTO.responsiblePersonAffiliationOrgName').value=$F('trialDTO.sponsorName');
      }
      
      function lookupInvestigator() {
          showPopup('${lookupPersUrl}', updateInvestigatorFieldsWithSelection, 'Select Investigator');
      }
      
      function updateInvestigatorFieldsWithSelection() {         
          $('trialDTO.responsiblePersonIdentifier').value = persid;
          $('trialDTO.responsiblePersonName').value = chosenname;
      }
      
      function lookupAffiliation() {
          showPopup('${lookupOrgUrl}', updateAffiliationWithSelection, 'Select Affiliation Organization');
      }
      
      function updateAffiliationWithSelection() {         
          $('trialDTO.responsiblePersonAffiliationOrgId').value = orgid;
          $('trialDTO.responsiblePersonAffiliationOrgName').value=chosenname;
      }
      
      
</script>
