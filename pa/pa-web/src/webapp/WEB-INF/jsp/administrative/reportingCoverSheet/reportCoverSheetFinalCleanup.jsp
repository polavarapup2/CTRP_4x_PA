 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <h2><fmt:message key="reportCoverSheet.finalRecordCleanup.title" /></h2>
   <table class="form" style="width:100%">                
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.standardlanguage.label"/></label>*
       </td>
       <td>
       <table>
       <tr><td>
         <div class="col-xs-4">
         <s:select id="useStandardLanguage" name="useStandardLanguage"
                    list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control" onchange="submitOnChange('useStandardLanguage');"
                      />
                           
                    <span class="formErrorMsg">
                       <s:fielderror>
                       <s:param>studyProtocolDTO.useStandardLanguage</s:param>
                      </s:fielderror>
                    </span>
        </div>
        </td><td>            
                    
                    <div id="useStandardLanguage_flash" class="flash" align="center">Saved!</div>
        </td></tr>            
         </table>       
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.dateEnteredInPrs.label"/></label>
       </td>
       <td>
       <table><tr><td>
        <s:select id="dateEnteredInPrs"   name="dateEnteredInPrs"
                      list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control" onchange="submitOnChange('dateEnteredInPrs');"
                      />
        </td><td>
        <div id="dateEnteredInPrs_flash" class="flash" align="center">Saved!</div>
        </td></tr>
        </table>              
                  
       </td>
     </tr>
      <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.results.trialinfo.ToCtGov.label"/></label>
       </td>
       <td>
       <table><tr><td>
       <s:select id="sendToCtGovUpdated" name="sendToCtGovUpdated"
                      list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control" onchange="submitOnChange('sendToCtGovUpdated');"
                      />
                    <span class="alert-danger">
                       <s:fielderror>
                       <s:param>studyProtocolDTO.sendToCtGovUpdated</s:param>
                      </s:fielderror>
                    </span>
       </td>
       <td> <div id="sendToCtGovUpdated_flash" class="flash" align="center">Saved!</div></td>
       </tr></table>             
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label><fmt:message key="reportCoverSheet.finalRecordCleanup.results.accessRevoked.label"/></label>
       </td>
       <td>
       <table>
       <tr><td>
         <s:select id="designeeAccessRevoked" name="designeeAccessRevoked" list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control" style="vertical-align:top;" onchange="flipDate('designeeAccessRevoked');"
                      />
                      
               
            
          <s:textfield id="designeeAccessRevokedDate"  name="designeeAccessRevokedDate" readonly="true" maxlength="10" size="10" class="dateField"  style="vertical-align:top;" ></s:textfield>       
                  
       </td>
       <td> <div id="designeeAccessRevokedDate_flash" class="flash" align="center">Saved! </div></td>
       <td> <div id="designeeAccessRevoked_flash" class="flash" align="left">Saved! Please enter date</div></td>
       
       </tr></table>                                        
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.results.changes.ctrpandctgov.label"/></label>
       </td>
       <td>
       <table><tr><td>
      <s:select id="changesInCtrpCtGov" name="changesInCtrpCtGov"  list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control" onchange="flipDate('changesInCtrpCtGov')"
                      />
         
        <s:textfield id="changesInCtrpCtGovDate"  name="changesInCtrpCtGovDate" maxlength="10" size="10" class="dateField" readonly="true"></s:textfield>           
       
         </td>
         <td>                             
         <div id="changesInCtrpCtGovDate_flash" class="flash" align="center">Saved!</div>
         </td>
          <td> <div id="changesInCtrpCtGov_flash" class="flash" align="left">Saved!Please enter date</div></td>
         </tr>
         </table>                                                
       </td>
     </tr>
    
     </table>
      <div class="actionsrow">
         <div style="padding:5px 10px 10px 10px">
         </div>
           
         </div>
         <div style="padding:5px 10px 10px 10px;text-align:center">
         <div style="padding:5px 10px 10px 10px">
         </div>
            <del class="btnwrapper">
                <ul class="btnrow">
                      <li> <s:a href="javascript:void(0)" cssClass="btn" id="sendCoverSheetEmail" onclick="sendCoverSheetEmail()" ><span class="btn_img">Email Trial Cover Sheet</span></s:a>
                    </li>                
                </ul>  
            </del>
        </div>  
     
       
              