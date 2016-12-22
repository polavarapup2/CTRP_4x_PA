<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Generic Contact</h2>
<br><p><b><i>Please provide professional contact information only.</i></b></p>
<table  class="form">  
   <tr>             
        <td scope="row" class="label"><label for="create_title">Title :</label><span class="required">*</span></td>
        <td><s:textfield name="title"  id="create_title" maxlength="50" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>
       <td scope="row" class="label"><label for="email">Email :</label><span class="required">*</span></td>
       <td><s:textfield name="email"  id="email" maxlength="254" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>   
       <td scope="row" class="label"><label for="phone">Phone :</label><span class="required">*</span></td>
       <td><s:textfield name="phone"  id="phone" maxlength="30" size="100"  cssStyle="width:200px" /></td>
   <tr>   
</table>
 <div align="center">
 <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
 </div>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="createTitle()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Search</span></span></s:a>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>          
       </li>
       </ul>   
  </del>
  </div>