<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="planned ProcedureDetailsDiv">
<pa:failureMessage/>
<pa:sucessMessage /> 
<h3>Procedure Information</h3>
</div>

<table class="form">	
               		
		<tr>
        
        <tr>    
         <td scope="row" class="label"><label for="target">Target Site:</label></td>
         <td>
         <div id="loadTargetSiteDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayTargetSite.jsp"%>
          </div>    
         </td>
        </tr>
        <tr>    
         <td scope="row" class="label"><s:label>Method:</s:label></td>
         <td>
         <div id="loadMethodCodeDetails">
             <%@ include file="/WEB-INF/jsp/nodecorate/displayMethodCode.jsp"%>
          </div>    
    </td>
    </tr>  
    <tr>
       <td>
       </td>    
         <td>
            <s:a href="javascript:void(0)" cssClass="btn" onclick="generate();">
              <span class="btn_img"><span class="submit">Generate</span></span>
         </s:a>
    </td>
    </tr> 
</table>

