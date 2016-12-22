<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <h2><fmt:message key="reportStudyContacts.designee.title" /></h2>
    
    <div class="box" id="designeeContact">
           <s:hidden id="dscToEdit" name="dscToEdit"/>
	       <s:hidden id="dscId" name="editedDesigneeSCWebDTO.id"/>
	       <s:hidden id="edtdDscSpId" name="editedDesigneeSCWebDTO.studyProtocolId"/>
	       <s:hidden id="dscOrgId" name="editedDesigneeSCWebDTO.selPoOrgId"/>
	       <s:hidden id="dscPrsnId" name="editedDesigneeSCWebDTO.selPoPrsnId"/>
	       <s:hidden id="edtdDscOrgId" name="editedDesigneeSCWebDTO.editedPoOrgId"/>
           <s:hidden id="edtdPscPrsnId" name="editedPioSCWebDTO.editedPoPrsnId"/>
	       <table class="form">
             <tr>
                <td  scope="row" class="label">
                    <label for="dscOrg"> <fmt:message key="reportStudyContacts.designee.org"/></label><span class="required">*</span> 
                </td>
                <td nowrap="nowrap">
                    <s:textfield id="dscOrg" name="editedDesigneeSCWebDTO.editedOrgNm" maxlength="200" 
                    size="200" cssStyle="width:200px" readonly="true"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedDesigneeSCWebDTO.contactOrg</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td>
                    <ul style="margin-top:-6px;"><li style="padding-left:0"><a id="lookupdesigneeorg" href="javascript:void(0)" 
                    class="btn" onclick="lookupdesigneeorg();"><span class="btn_img"><span class="search">Search</span></span></a></li></ul>
                </td>
                <td>
                </td>
                <td scope="row" class="label" >
                </td>
             </tr>
             <tr>
                <td scope="row" class="label">
                    <label for="dscPrsn"><fmt:message key="reportStudyContacts.designee.person"/></label><span class="required">*</span> 
                </td>
                <td>
                    <s:textfield id="dscPrsn" name="editedDesigneeSCWebDTO.editedPrsnNm" 
                    maxlength="200" size="200" cssStyle="width:200px" readonly="true" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedDesigneeSCWebDTO.contactPerson</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td nowrap="nowrap">
                    <ul style="margin-top:-6px;"><li style="padding-left:0"><a id="lookupdesigneeperson" href="javascript:void(0)" 
                    class="btn" onclick="lookupdesigneeperson();"><span class="btn_img"><span class="search">Search</span></span></a></li></ul>
                </td>
                <td scope="row" class="label">
                    <label for="dscComments"><fmt:message key="reportStudyContacts.designee.comments"/></label>
                    <br><span class="info">(CTRP Specific):</span>
                </td>
                <td>
                    <s:textarea id="dscComments" name="editedDesigneeSCWebDTO.comments" rows="3" cssStyle="width:200px;" maxlength="500"  cssClass="charcounter" />
                </td>
            </tr>
            <tr>
                <td  scope="row" class="label">
                    <label for="dscPrsUserNm"> <fmt:message key="reportStudyContacts.designee.prsUserName"/></label>
                </td>
                <td>
                    <s:textfield id="dscPrsUserNm" name="editedDesigneeSCWebDTO.prsUserName" maxlength="200" size="200" cssStyle="width:200px"  />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedDesigneeSCWebDTO.prsUserName</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td  colspan="2" scope="row" class="label" >
                </td>
            </tr>
            <tr>
                <td  scope="row" class="label">
                    <label for="dscEmail"> <fmt:message key="reportStudyContacts.designee.email"/></label><span class="required">*</span> 
                </td>
                <td>
                    <s:textfield id="dscEmail" name="editedDesigneeSCWebDTO.email" maxlength="200" size="200" cssStyle="width:200px"  type="email" placeholder="example@example.com" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedDesigneeSCWebDTO.email</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td  colspan="2" scope="row" class="label" >
                </td>
            </tr>
            <tr>
                <td  scope="row" class="label">
                    <label for="dscPhone"> <fmt:message key="reportStudyContacts.designee.phone"/></label>
                </td>
                <td>
                    <s:textfield id="dscPhone" name="editedDesigneeSCWebDTO.phone" maxlength="200" size="200" cssStyle="width:200px" type="tel" placeholder="703-111-1111"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedDesigneeSCWebDTO.phone</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td>
                </td>
                <td  scope="row" class="label">
                    <label for="dscExt"> <fmt:message key="reportStudyContacts.designee.ext"/></label>
                </td>
                <td>
                    <s:textfield id="dscExt" name="editedDesigneeSCWebDTO.ext" maxlength="200" size="200" cssStyle="width:200px" placeholder="123" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>editedDesigneeSCWebDTO.ext</s:param>
                       </s:fielderror>
                    </span>
                </td>
            </tr>
            </table>
            
            <jsp:include page="reportDesigneeStudyContacts.jsp"/>
            
            
            <div class="actionsrow">
             <del class="btnwrapper">
                  <ul class="btnrow">
                      <li><s:a href="javascript:void(0);" onclick="addOrUpdateDesigneeContact();"
                              cssClass="btn" id="addEditDSC">
                              <span class="btn_img"><span class="save">Add/Update Designee</span></span>
                          </s:a></li>
                  </ul>
             </del>
         </div>
         
    </div>
    