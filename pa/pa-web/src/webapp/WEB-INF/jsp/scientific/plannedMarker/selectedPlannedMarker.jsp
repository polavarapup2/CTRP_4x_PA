<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<table class="form">
    <s:hidden name="currentAction" />
    <s:hidden name="plannedMarker.id" />
    <s:hidden id="foundInHugo" name="plannedMarker.foundInHugo" />
    <s:hidden id="saveResetAttribute" name="saveResetAttribute"/>
    <s:hidden id="saveResetMarker" name="saveResetMarker"/>
    <s:hidden name="plannedMarker.meaning" id="meaning"/>
    <s:hidden name="plannedMarker.description" id="description"/>
    <s:hidden name="plannedMarker.dateEmailSent" id="dateEmailSent"/>
    <s:hidden name="plannedMarker.synonymNames" id="synonymNames"/>
    <s:hidden name="plannedMarker.pvValue" id="pvValue"/>
    <tr>
        <td class="label">
            <label for="name"><fmt:message key="plannedMarker.name" />:</label><span class="required">*</span>
        </td>
        <td class="value" style="width: 250px">
            <s:textfield name="plannedMarker.name" id="name" maxlength="200" size="200" cssStyle="width:250px" />
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.name</s:param>
                </s:fielderror>
            </span>
        </td>
        <td class="value">
            <ul style="margin-top: -6px;">
                <li style="padding-left: 0">
                    <s:a href="javascript:void(0)" cssClass="btn" id="cadsrLookup" onclick="cadsrLookup();">
                        <span class="btn_img"><span class="search"><fmt:message key="plannedMarker.caDSR"/></span></span>
                    </s:a>
                </li>
            </ul>
        </td>
    </tr>
    
    <tr>
        <td class="label"><span class="labelspan"><fmt:message key="plannedMarker.evaluationType" />:</span><span class="required">*</span></td>
        <td style="border:1px solid lavender">
            <s:set name="evaluationTypeValues" value="@gov.nih.nci.pa.service.MarkerAttributesBeanLocal@getTypeValues(@gov.nih.nci.pa.enums.BioMarkerAttributesCode@EVALUATION_TYPE)" />
            <s:set name="selectedEvalValues" value="plannedMarker.selectedEvaluationType"/>
            <s:checkboxlist id="plannedMarker.evaluationType" list="#evaluationTypeValues" name="plannedMarker.evaluationType" value="#selectedEvalValues"  onclick="toggleEvalTypeOtherText();" theme="vertical-checkbox"/> 
            
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.evaluationType</s:param>
                </s:fielderror>
            </span>
        </td>
        <td>  
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>               
            <p> Note: Users have the option to multi-select attribute values for Evaluation Type </p>
        </td>
    </tr>
    <tr id="evalTypeOtherTextRow" style="display: none;">
        <td class="label">
            <label for="evaluationTypeOtherText"><fmt:message key="plannedMarker.evaluationTypeOtherText" />:</label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:textfield name="plannedMarker.evaluationTypeOtherText" id="evaluationTypeOtherText" maxlength="200" size="200" cssStyle="width:280px" /> 
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.evaluationTypeOtherText</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    
    <tr id="hugoCodeRow" style="display:none">
        <td class="label"><span class="labelspan"><fmt:message key="plannedMarker.hugoCode"/>:</span></td>
        <td>
            <s:hidden name="plannedMarker.hugoCode" id="hugoCode" />
            <s:property value="plannedMarker.hugoCode"/>
        </td>
    </tr>
    <tr id="cadsrID" style="display:none">
        <td class="label"><fmt:message key="plannedMarker.cadsrId"/>:</td>
        <td>
            <s:hidden name="plannedMarker.cadsrId" id="cadsrID" />
            <s:property value="plannedMarker.cadsrId"/>
        </td>
    </tr>
    <tr> <td>  </td> </tr>
    <tr>
        <td class="label"><span class="labelspan"><fmt:message key="plannedMarker.assayType" />:</span><span class="required">*</span></td>
        <td style="border:1px solid lavender">
            <s:set name="assayTypeValues" value="@gov.nih.nci.pa.service.MarkerAttributesBeanLocal@getTypeValues(@gov.nih.nci.pa.enums.BioMarkerAttributesCode@ASSAY_TYPE)" />
            <s:set name="selectedValues" value="plannedMarker.selectedAssayType"/>
            <s:checkboxlist list="#assayTypeValues" name="plannedMarker.assayType" value="#selectedValues" id="assayType" onclick="toggleAssayTypeOtherText();" theme="vertical-checkbox"/> 
            
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.assayType</s:param>
                </s:fielderror>
            </span>
        </td>
        <td>  
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>               
            <p> Note: Users have the option to multi-select attribute values for Assay Type </p>
        </td>
    </tr>
    <tr id="assayTypeOtherTextRow" style="display: none;">
        <td class="label">
            <label for="assayTypeOtherText"><fmt:message key="plannedMarker.assayTypeOtherText" />:</label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:textfield name="plannedMarker.assayTypeOtherText" id="assayTypeOtherText" maxlength="200" size="200" cssStyle="width:280px" /> 
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayTypeOtherText</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td class="label">
            <label for="assayUse"><fmt:message key="plannedMarker.assayUse" />:</label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:set name="assayUseValues" value="@gov.nih.nci.pa.service.MarkerAttributesBeanLocal@getTypeValues(@gov.nih.nci.pa.enums.BioMarkerAttributesCode@BIOMARKER_USE)" />
            <s:select headerKey="" headerValue="" name="plannedMarker.assayUse" id="assayUse" list="#assayUseValues" /> 
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayUse</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td class="label"><span class="labelspan"><fmt:message key="plannedMarker.assayPurpose" />:</span><span class="required">*</span>
        </td>
        <td style="width: 250px;border:1px solid lavender">
            <s:set name="assayPurposeValues" value="@gov.nih.nci.pa.service.MarkerAttributesBeanLocal@getTypeValues(@gov.nih.nci.pa.enums.BioMarkerAttributesCode@BIOMARKER_PURPOSE)" /> 
            <s:set name="selectedPurposeValues" value="plannedMarker.selectedAssayPurpose"/>
          
            <s:checkboxlist list="#assayPurposeValues" name="plannedMarker.assayPurpose" value="#selectedPurposeValues" id="assayPurpose" onclick="toggleAssayPurposeOtherText();" theme="vertical-checkbox"/>
            
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayPurpose</s:param>
                </s:fielderror>
            </span>
        </td>
        
             
        <td class="value">
           
            <br>
            <br>
            <br>      
           <p> Note: Users have the option to multi-select attribute values for Biomarker Purpose</p>
            
    </tr>
        </td>
        
    </tr>
     <tr> <td>  </td> </tr>
    <tr id="assayPurposeOtherTextRow" style="display: none;">
        <td class="label">
            <label for="assayPurposeOtherText"><fmt:message key="plannedMarker.assayPurposeOtherText" />:</label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:textfield name="plannedMarker.assayPurposeOtherText" id="assayPurposeOtherText" maxlength="200" size="200" cssStyle="width:280px" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.assayPurposeOtherText</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td class="label"><span class="labelspan"><fmt:message key="plannedMarker.tissueSpecimenType" />:</span><span class="required">*</span>
        </td>
        <td style="width: 250px;border:1px solid lavender">
            <s:set name="tissueSpecimenTypeValues" value="@gov.nih.nci.pa.service.MarkerAttributesBeanLocal@getTypeValues(@gov.nih.nci.pa.enums.BioMarkerAttributesCode@SPECIMEN_TYPE)" />
            <s:set name="selectedTissueValues" value="plannedMarker.selectedTissueSpecType"/>
          
            <s:checkboxlist list="#tissueSpecimenTypeValues" name="plannedMarker.tissueSpecimenType" value="#selectedTissueValues" id="tissueSpecimenType" onclick="toggleSpecimenTypeOtherText();" theme="vertical-checkbox" />
           
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.tissueSpecimenType</s:param>
                </s:fielderror>
            </span>
        </td>
        <td style="width:350px">
            <br>
            <br>
            <br>
            <p> Note: Users have the option to multi-select attribute values for Specimen Type </p> 
            </td>
    </tr>
     <tr id="specimenTypeOtherTextRow" style="display: none;">
        <td class="label">
            <label for="specimenTypeOtherText"><fmt:message key="plannedMarker.specimenTypeOtherText" />:</label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:textfield name="plannedMarker.specimenTypeOtherText" id="specimenTypeOtherText" maxlength="200" size="200" cssStyle="width:280px" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.specimenTypeOtherText</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    
    <tr>
        <td class="label"><span class="labelspan"><fmt:message key="plannedMarker.status" />:</span></td>
        <td>
            <s:hidden name="plannedMarker.status" id="status"/>
            <s:property value="plannedMarker.status"/>
        </td>
    </tr>
</table>