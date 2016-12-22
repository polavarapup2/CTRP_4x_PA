<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="plannedRadiationDetailsDiv">
<pa:failureMessage/>
<pa:sucessMessage /> 
</div>
<h3>Radiation Information</h3>
<table class="form">    
                    
    <tr>
        <td scope="row" class="label"><label for="minDoseValue">Dose Range:</label></td>
        <td class="value" >
            <s:textfield  id="minDoseValue" name="minDoseValue" maxlength="9" /> 
            <label for="maxDoseValue" style="display:none">Dose Range:</label>
             <s:textfield id="maxDoseValue" name="maxDoseValue" maxlength="9"/>         
              <span class="formErrorMsg"> 
             <s:fielderror>
                 <s:param>minDoseValue</s:param>
               </s:fielderror>  
             <s:fielderror>
          <s:param>maxDoseValue</s:param>
         </s:fielderror> 
            </span>
        </td>
        <td scope="row" class="label"><label for="doseUOM">Dose UOM:</label></td>
        <td class="value" >
        <div id="loadDoseUOMDetails">
           <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseUOM.jsp"%>
        </div>   
       </td>
    </tr>
    <tr>
            
     <td scope="row" class="label"><label for="doseForm">Dose Form:</label></td>
         <td>
         <div id="loadDoseFormDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseForm.jsp"%>
         </div>     
        </td>
    </tr> 
    <tr>
            <td scope="row" class="label"><label for="doseFrequency">Dose Frequency:</label></td>
            <td>
           <div id="loadDoseFreqDetails"> 
             <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseFrequency.jsp"%>
          </div>    
    </td>
    </tr> 
   
    <tr>
        <td scope="row" class="label"><label for="doseDurationValue">Dose Duration:</label></td>
        <td class="value">
            <s:textfield name="doseDurationValue" id="doseDurationValue" maxlength="9"  
                   /> 
                   <span class="formErrorMsg"> 
                     <s:fielderror>
                         <s:param>doseDurationValue</s:param>
                       </s:fielderror>  
        </span>
        </td>
       <td scope="row" class="label"><label for="doseDurationUOM">Dose Duration UOM:</label></td>
        <td class="value" >
        <div id="loadDoseDurationUOMDetails"> 
          <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseDurationUOM.jsp"%>
        </div> 
       </td>
    </tr>
      
      <tr>
        <td scope="row" class="label"><label for="doseRegimen">Dose Regimen:</label></td>
        <td class="value" >
            <s:textarea name="doseRegimen" id="doseRegimen" cssStyle="width:275px" rows="3"
                maxlength="1000" cssClass="charcounter"/> 
               <span class="formErrorMsg"> 
                  <s:fielderror>
                      <s:param>doseRegimen</s:param>
                    </s:fielderror>  
            </span>
        </td>
    </tr>
   <tr>
        <td scope="row" class="label"><label for="minDoseTotalValue">Total Dose Range:</label></td>
        <td class="value" >
            <s:textfield name="minDoseTotalValue" id="minDoseTotalValue" maxlength="9" 
                    /> 
             <s:textfield name="maxDoseTotalValue" id="maxDoseTotalValue" maxlength="9" 
                    />  
                    <label for="maxDoseTotalValue" style="display:none">max Total Dose Range:</label>
                      <span class="formErrorMsg"> 
                             <s:fielderror>
                                 <s:param>minDoseTotalValue</s:param>
                               </s:fielderror>  
                               <s:fielderror>
                    <s:param>maxDoseTotalValue</s:param>
                               </s:fielderror> 
        </span>              
        </td>
       <td scope="row" class="label"><label for="doseTotalUOM">Total Dose UOM:</label></td>
        <td class="value" >
        <div id="loadTotalDoseUOMDetails"> 
           <%@ include file="/WEB-INF/jsp/nodecorate/displayTotalDoseUOM.jsp"%>
         </div>  
       </td>
    </tr>
     <tr>    
         <td scope="row" class="label"><label for="target">Target Site:</label></td>
         <td>
         <div id="loadTargetSiteDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayTargetSite.jsp"%>
          </div>    
    </td>
    </tr> 
    <tr>
            <td scope="row" class="label"><label for="approachSite">Approach Site:</label></td>
            <td>
            <div id="loadApproachSiteDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayApproachSite.jsp"%>
             </div> 
    </td>
    </tr> 
 <tr>
       <td>
       </td>
       <td>
        </td>    
         <td>
            <s:a href="javascript:void(0)" cssClass="btn" onclick="generate();">
              <span class="btn_img"><span class="submit">Generate</span></span>
         </s:a>
    </td>
    </tr> 
            </table>   
    
