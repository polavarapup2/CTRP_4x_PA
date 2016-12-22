<%@ taglib prefix="s" uri="/struts-tags"%> 
 <div >   
            <s:textfield id="target" readonly="true" name="targetSite" maxlength="80" size="80" 
                    cssStyle="width:120px;float:left" cssClass="readonly"/> 
        
             <ul  style="margin-top: -2px;">
                <li style="padding-left: 0"><a href="javascript:void(0)" class="btn"
                    onclick="lookupTargetSite();" /><span class="btn_img"><span
                    class="search">Look Up</span></span></a></li>
            </ul>
  </div>