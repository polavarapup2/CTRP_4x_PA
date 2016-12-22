<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="plannedDrugDetailsDiv">
<pa:failureMessage/>
<pa:sucessMessage /> 
<h3>Drug Information</h3>

</div>
<table class="form">    
                    
    <tr>
        <td scope="row" class="label"><s:label>Dose Range:</s:label></td>
        <td class="value" >
            <s:textfield  name="minDoseValue" maxlength="9" />
            <s:textfield name="maxDoseValue" maxlength="9"/>         
             <span class="formErrorMsg"> 
             <s:fielderror>
                 <s:param>minDoseValue</s:param>
               </s:fielderror>  
             <s:fielderror>
          <s:param>maxDoseValue</s:param>
         </s:fielderror> 
            </span>
        </td>
        <td scope="row" class="label"><s:label>Dose UOM:</s:label></td>
        <td class="value" >
        <div id="loadDoseUOMDetails">
           <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseUOM.jsp"%>
        </div>   
       </td>
    </tr>
    <tr>
            
     <td scope="row" class="label"><s:label>Dose Form:</s:label></td>
         <td>
         <div id="loadDoseFormDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseForm.jsp"%>
         </div>     
        </td>
    </tr> 
   <tr>
         
         <td scope="row" class="label"><s:label>Route Of Administration:</s:label></td>
         <td>
         <div id="loadDoseROADetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayRouteOfAdmin.jsp"%>
          </div>    
    </td>
    </tr> 
   <tr>
            <td scope="row" class="label"><s:label>Dose Frequency:</s:label></td>
            <td>
           <div id="loadDoseFreqDetails"> 
             <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseFrequency.jsp"%>
          </div>    
    </td>
    </tr> 
   
    <tr>
        <td scope="row" class="label"><s:label>Dose Duration:</s:label></td>
        <td class="value">
            <s:textfield name="doseDurationValue" maxlength="9"/> 
             <span class="formErrorMsg"> 
                     <s:fielderror>
                         <s:param>doseDurationValue</s:param>
                       </s:fielderror>  
        </span>
        </td>
       <td scope="row" class="label"><s:label>Dose Duration UOM:</s:label></td>
        <td class="value" >
        <div id="loadDoseDurationUOMDetails"> 
          <%@ include file="/WEB-INF/jsp/nodecorate/displayDoseDurationUOM.jsp"%>
        </div> 
       </td>
    </tr>
        <tr>
        <td/>
        <td class="value">
            <s:label name="doseLimit" ></s:label>
         </td>
    </tr>
    
      <tr>
        <td scope="row" class="label"><s:label>Dose Regimen:</s:label></td>
        <td class="value" >
            <s:textarea name="doseRegimen" cssStyle="width:275px" rows="3"
                maxlength="1000" cssClass="charcounter"/> 
            <span class="formErrorMsg"> 
               <s:fielderror>
                   <s:param>doseRegimen</s:param>
                 </s:fielderror>  
            </span>
        </td>
    </tr>
   <tr>
        <td scope="row" class="label"><s:label>Total Dose Range:</s:label></td>
        <td class="value" >
            <s:textfield name="minDoseTotalValue" maxlength="9" 
                    /> 
             <s:textfield name="maxDoseTotalValue" maxlength="9" 
                    />    
             <span class="formErrorMsg"> 
                             <s:fielderror>
                                 <s:param>minDoseTotalValue</s:param>
                               </s:fielderror>  
                               <s:fielderror>
                    <s:param>maxDoseTotalValue</s:param>
                               </s:fielderror> 
        </span>       
        </td>
       <td scope="row" class="label"><s:label>Total Dose UOM:</s:label></td>
        <td class="value" >
        <div id="loadTotalDoseUOMDetails"> 
           <%@ include file="/WEB-INF/jsp/nodecorate/displayTotalDoseUOM.jsp"%>
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


