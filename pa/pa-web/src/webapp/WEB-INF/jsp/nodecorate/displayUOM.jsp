<%@ taglib prefix="s" uri="/struts-tags"%> 
<div>
            <s:if test="#session.labTestNameValues == null">
                <s:textfield readonly="true" id="webDTO.unit" name="webDTO.unit" maxlength="80" size="80" 
                        cssStyle="width:120px;float:left" cssClass="readonly"/> 
           
                 <ul>
                    <li>
                        <a href="javascript:void(0)" class="btn" onclick="lookupUOM();">
                            <span class="btn_img">
                                <span class="search">Look Up</span>
                            </span>
                        </a>
                    </li>
                 </ul>
             </s:if>
             <s:if test="#session.labTestNameValues != null && #session.labTestUoMValues != null">
                <s:select headerKey="" headerValue="" id="webDTO.unit" name="webDTO.unit" value="webDTO.unit"
                list="#session.labTestUoMValues"  cssStyle="width:150px" />
             </s:if>
</div>
