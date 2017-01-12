<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<table class="form">
<tr>
    <th colspan="3"><fmt:message key="isdesign.eligibilitycriteria.buildDescription" /></th>
</tr>
            <tr>
                     <td scope="row" class="label">
                        <label for="webDTO.cdeCategoryCode">
                            <fmt:message key="cadsr.classificationCode"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:textfield id="webDTO.cdeCategoryCode" name="webDTO.cdeCategoryCode" readonly="true" cssClass="readonly" cssStyle="width:100px" />
                      </td>
                 </tr> 
             <tr >
                    <td scope="row"  class="label"><label for="webDTO.criterionName">
                        <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/></label>
                    </td>
                    <td>
                        <s:textfield id="webDTO.criterionName" name="webDTO.criterionName" maxlength="30" size="120" readonly="true" cssClass="readonly" cssStyle="width:200px;float:left" />
                        <s:hidden name="webDTO.cdePublicIdentifier"/>
                        <s:hidden name="webDTO.cdeVersionNumber"/>
                          <ul >              
                    <li >
                     <a href="javascript:void(0)" class="btn" id="criteriaNameLookup" onclick="lookup();" />
                     <span class="btn_img"><span class="search">Look Up</span></span></a>
                    </li>
                </ul>
                     </td>
                  
                </tr>        
               
                <s:if test = "#session.labTestNameValues">  
                  <tr>
                     <td scope="row" class="label">
                        <label for="webDTO.labTestNameValueText">
                            <fmt:message key="isdesign.eligibilitycriteria.labTestNameValueText"/>
                        </label>
                    </td>
                    <td class="value">
                         <s:select headerKey="" headerValue="" id="webDTO.labTestNameValueText" name="webDTO.labTestNameValueText" value="webDTO.labTestNameValueText"
                        list="#session.labTestNameValues"  cssStyle="width:150px" /> 
                      </td>
                 </tr>           
                 </s:if>
                 
                 <tr>
                     <td scope="row" class="label">
                     <label for="webDTO.operator">
                            <fmt:message key="isdesign.eligibilitycriteria.operator"/>
                     </label>
                    </td>
                    <td class="value">                        
                      <s:select id="webDTO.operator" name="webDTO.operator" list="#{'':'-Select-','=':'=', '<':'<', '<=':'<=', '>':'>', '>=':'>=', 'in':'in'}" cssStyle="width:106px" value="webDTO.operator" onchange="activateMax()"/>
                    </td>         
                </tr> 
                 
                <s:if test="permValues == null">
                <s:if test="cdeDatatype != null && cdeDatatype.equals(\"NUMBER\")">
                
                <tr>
                     <td scope="row" class="label">
                        <label for="webDTO.valueIntegerMin">
                            <fmt:message key="isdesign.eligibilitycriteria.valueInteger"/>
                        </label>
                    </td>
                    <td>
                      Min: <s:textfield id="webDTO.valueIntegerMin" name="webDTO.valueIntegerMin" maxlength="12" cssStyle="width:80px" />
                      <label for="webDTO.valueIntegerMax" style="display: none;">Max</label>
                      <s:if test="%{webDTO.operator=='In'}">
                       Max: <s:textfield id="webDTO.valueIntegerMax" name="webDTO.valueIntegerMax" maxlength="12" cssStyle="width:80px" />
                       </s:if>
                       <s:else>
                        Max: <s:textfield id="webDTO.valueIntegerMax" name="webDTO.valueIntegerMax" maxlength="12" cssStyle="width:80px" disabled="true"/>
                       </s:else>
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.valueIntegerMin</s:param>
                                </s:fielderror>                            
                         </span>
                     </td> 
                 </tr>
                 </s:if>
                 <s:if test="cdeDatatype == null || !cdeDatatype.equals(\"NUMBER\")">
                  <tr>
                     <td scope="row" class="label">
                        <label for="webDTO.valueText">
                            <fmt:message key="isdesign.eligibilitycriteria.valueText"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:textfield id="webDTO.valueText" name="webDTO.valueText" maxlength="12" cssStyle="width:100px" />
                      </td>
                 </tr>
                 </s:if>
                 </s:if>
                 <s:if test="permValues != null">
                 <s:if test="cdeDatatype != null && cdeDatatype.equals(\"NUMBER\")">
                  <tr>
                     <td scope="row" class="label">
                        <label for="webDTO.valueIntegerMin">
                            <fmt:message key="isdesign.eligibilitycriteria.valueInteger"/>
                        </label>
                    </td>
                    <td>
                      Min: <s:select headerKey="" headerValue="" id="webDTO.valueIntegerMin" name="webDTO.valueIntegerMin" value="webDTO.valueIntegerMin"
                list="permValues"  cssStyle="width:150px" />
                <label for="webDTO.valueIntegerMax" style="display: none;">Max</label>
                <s:if test="%{webDTO.operator=='In'}">
                      Max: <s:select headerKey="" headerValue="" id="webDTO.valueIntegerMax" name="webDTO.valueIntegerMax" value="webDTO.valueIntegerMax"
                list="permValues" cssStyle="width:150px" /> 
                 </s:if>
                  <s:else>
                    Max: <s:select headerKey="" headerValue="" id="webDTO.valueIntegerMax" name="webDTO.valueIntegerMax" value="webDTO.valueIntegerMax"
                list="permValues" cssStyle="width:150px" disabled="true"/> 
                  </s:else>
                                <s:fielderror>
                                <s:param>webDTO.valueIntegerMin</s:param>
                                </s:fielderror>                            
                         </span>
                     </td> 
                 </tr>
                 </s:if>
                 <s:if test="cdeDatatype == null || !cdeDatatype.equals(\"NUMBER\")">
                  <tr>
                     <td scope="row" class="label">
                        <label for="webDTO.valueText">
                            <fmt:message key="isdesign.eligibilitycriteria.valueText"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:select headerKey="" headerValue="" id="webDTO.valueText" name="webDTO.valueText" value="webDTO.valueText"
                list="permValues"  cssStyle="width:150px" />
                      </td>
                 </tr>
                 </s:if>
                 </s:if>
                 <tr>
                      <td scope="row" class="label">
                     <label for="webDTO.unit">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/>
                     </label>
                   </td>
                    <td>
                    <div id="loadUOMDetails">
                        <%@ include file="/WEB-INF/jsp/nodecorate/displayUOM.jsp"%>
                    </div> 
                    </td> 
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.buldcriterion</s:param>
                               </s:fielderror>                            
                         </span>           
                </tr> 
                  <tr>
                      <td scope="row" class="label">
                     
                   </td>
                    <td class="value">
                    <s:a href="javascript:void(0)" cssClass="btn" id="generateTextButton" onclick="handleGenerateCriteriaText()"><span class="btn_img"><span class="save">Generate Description  Text</span></span></s:a>
                </tr> 
            <tr>
                <th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.description"/></th>              
                </tr>
                <tr>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.mandatory</s:param>
                               </s:fielderror>                            
                         </span>
                    <td scope="row"  class="label"><label for="webDTO.textDescription">
                        <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/><span class="required">*</span></label>
                    </td>
                    <td class="value">
                        <s:textarea id="webDTO.textDescription" name="webDTO.textDescription" rows="6" cssStyle="width:600px" onblur='activate();' 
                            maxlength="5000" cssClass="charcounter"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.TextDescription</s:param>
                               </s:fielderror>                            
                         </span>
                    </td>
                </tr>           
                 
</table>                 