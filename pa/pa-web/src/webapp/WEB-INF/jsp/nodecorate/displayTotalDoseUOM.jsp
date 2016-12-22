<%@ taglib prefix="s" uri="/struts-tags"%> 
<div>

            <s:textfield readonly="true" name="doseTotalUOM" id="doseTotalUOM" maxlength="80" size="80" 
                    cssStyle="width:120px;float:left" cssClass="readonly"/> 
       
             <ul style="margin-top: -7px;">
                <li style="padding-left: 0"><a href="javascript:void(0)" class="btn"
                    onclick="lookupTotalDoseUOM();" /><span class="btn_img"><span
                    class="search">Look Up</span></span></a></li>
            </ul>
    </div>
