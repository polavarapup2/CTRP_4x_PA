<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>

</head>
<p align="center" class="info">
    Enter the Title and click on Search.
</p>
<table  class="form">  
   	<tr>    
        <td scope="row" class="label"><label for="title">Title:</label></td>
        <td><s:textfield name="title" id="search_title" maxlength="200" size="100"  cssStyle="width:200px" /></td>
     </tr>   
   	</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="javascript:void(0)" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                   <s:a href="javascript:void(0)" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Generic Contact</span></span></s:a>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                   </li>
               </ul>   
          </del>
     </div> 