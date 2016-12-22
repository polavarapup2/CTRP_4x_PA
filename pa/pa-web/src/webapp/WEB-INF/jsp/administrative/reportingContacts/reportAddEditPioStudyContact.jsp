<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

 <h2><fmt:message key="reportStudyContacts.pio.title" /></h2>
 
<div class="box" id="pioContact">
        <s:hidden id="pscToEdit" name="pscToEdit"/>
        <s:hidden id="pscId" name="editedPioSCWebDTO.id"/>
        <s:hidden id="edtdPscSpId" name="editedPioSCWebDTO.studyProtocolId"/>
        <s:hidden id="pscPrsnId" name="editedPioSCWebDTO.selPoPrsnId"/>
        <s:hidden id="edtdPscPrsnId" name="editedPioSCWebDTO.editedPoPrsnId"/>
        <table class="form">
            <tr>
                <td scope="row" class="label">
                    <label for="pscPrsn"><fmt:message key="reportStudyContacts.pio.person"/></label><span class="required">*</span> 
                </td>
                <td nowrap="nowrap">
                    <s:textfield id="pscPrsn" name="editedPioSCWebDTO.editedPrsnNm" maxlength="200" size="200" 
                    cssStyle="width:200px" readonly="true" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedPioSCWebDTO.contactPerson</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td>
                    <ul style="margin-top:-6px;">           
                           <li style="padding-left:0"><a id="lookuppioperson" href="javascript:void(0)" class="btn" onclick="lookuppioperson();"><span class="btn_img"><span class="search">Search</span></span></a></li>
                       </ul>
                    
                </td>
                <td>
                </td>
                <td scope="row" class="label" >
                </td>
            </tr>
            <tr>
                <td  scope="row" class="label">
                    <label for="pscEmail"> <fmt:message key="reportStudyContacts.pio.email"/></label><span class="required">*</span> 
                </td>
                <td>
                    <s:textfield id="pscEmail" name="editedPioSCWebDTO.email" maxlength="200" size="200" cssStyle="width:200px" type="email" placeholder="example@example.com" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedPioSCWebDTO.email</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td  colspan="2" scope="row" class="label" >
                </td>
            </tr>
            <tr>
                <td  scope="row" class="label">
                    <label for="pscPhone"> <fmt:message key="reportStudyContacts.pio.phone"/></label>
                </td>
                <td>
                    <s:textfield id="pscPhone" name="editedPioSCWebDTO.phone" maxlength="200" size="200" cssStyle="width:200px" type="tel" placeholder="703-111-1111" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedPioSCWebDTO.phone</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td>
                </td>
                <td  scope="row" class="label">
                    <label for="pscExt"> <fmt:message key="reportStudyContacts.pio.ext"/></label>
                </td>
                <td>
                    <s:textfield id="pscExt" name="editedPioSCWebDTO.ext" maxlength="200" size="200" cssStyle="width:200px" placeholder="123" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedPioSCWebDTO.ext</s:param>
                       </s:fielderror>
                    </span>
                </td>
            </tr>
        </table>
        
        <jsp:include page="reportPioStudyContacts.jsp"/>
        
          <div class="actionsrow">
           <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0);" onclick="addOrUpdatePioContact();"
                          cssClass="btn" id="addEditPSC">
                          <span class="btn_img"><span class="save">Add/Update PIO</span></span>
                      </s:a> </li>
                </ul>
           </del>
       </div>
         
</div>
