<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <tr>
        <td scope="row" class="label"><label for="gtdDTO.phaseCode">
                 <fmt:message key="studyProtocol.studyPhase"/><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
            <s:select headerKey="" headerValue="" name="gtdDTO.phaseCode" id="gtdDTO.phaseCode" list="#phaseCodeValues" 
                value="gtdDTO.phaseCode" cssStyle="width:120px" onchange="displayPhaseAdditionalCode()"/>
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id ="phaseOtherDiv" style="display:'none'">
        <td   scope="row" class="label"><label for="gtdDTO.phaseAdditionalQualifierCode"><fmt:message key="isdesign.details.phase.comment"/></label></td>
        <td>
            <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
            <s:select headerKey="" headerValue="No" name="gtdDTO.phaseAdditionalQualifierCode"  id="gtdDTO.phaseAdditionalQualifierCode" list="#phaseAdditionlQualiefierCodeValues" 
                value="gtdDTO.phaseAdditionalQualifierCode" cssStyle="width:120px" />        	
        </td>
    </tr>
    <tr>
        <td  scope="row" class="label"><label for="gtdDTO.primaryPurposeCode">
            <fmt:message key="isdesign.details.primary.purpose"/><span class="required">*</span></label></td>
        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.lov.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="gtdDTO.primaryPurposeCode" id="gtdDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
               value="gtdDTO.primaryPurposeCode" cssStyle="width:150px"  onchange="displayPrimaryPurposeOtherText();" />
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
      <tr id="purposeOtherTextDiv" style="display:'none'">
         <td scope="row" class="label">
            <label for="gtdDTO.primaryPurposeOtherText"> <fmt:message key="isdesign.details.primary.purpose.otherText"/></label>
         </td>
         <td>
               <s:textarea id="gtdDTO.primaryPurposeOtherText"  name="gtdDTO.primaryPurposeOtherText"  cols="50" rows="2" />
               <span class="info">Required if Purpose equals &#39;Other&#39;</span>
               <span class="formErrorMsg"> 
               <s:fielderror>
               <s:param>gtdDTO.primaryPurposeOtherText</s:param>
               </s:fielderror>                            
               </span>
               <s:hidden name="gtdDTO.primaryPurposeAdditionalQualifierCode" id= "gtdDTO.primaryPurposeAdditionalQualifierCode"></s:hidden>
         </td>
      </tr>
<SCRIPT LANGUAGE="JavaScript">
initialize();
function initialize() {
    displayPhaseAdditionalCode();
    displayPrimaryPurposeOtherText();
}
function displayPhaseAdditionalCode(){
    if ($('gtdDTO.phaseCode').value == 'NA') {
        $('phaseOtherDiv').show();
    } else {
        $('phaseOtherDiv').hide();
    }
}

function displayPrimaryPurposeOtherText() {
   if ($('gtdDTO.primaryPurposeCode').value == 'Other') {
       $('purposeOtherTextDiv').show();
       document.getElementById('gtdDTO.primaryPurposeOtherText').disabled = false;
       document.getElementById('gtdDTO.primaryPurposeAdditionalQualifierCode').value = 'Other';
   } else {
       $('purposeOtherTextDiv').hide();
       document.getElementById('gtdDTO.primaryPurposeOtherText').disabled = true;
       document.getElementById('gtdDTO.primaryPurposeAdditionalQualifierCode').value = null;
  }
}
</SCRIPT>
      