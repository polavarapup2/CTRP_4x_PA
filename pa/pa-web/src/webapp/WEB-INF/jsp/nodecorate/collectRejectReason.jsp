<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">
    function submitform() {
        var rejectReason = document.getElementById("rejectReason").value;
        top.window.setRejectReason(rejectReason);
        window.top.hidePopWin(true); 
    }
</SCRIPT> 
</head> 
<body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
<div class="box">
<s:form id="collectRejectReason" name="collectRejectReason" >
    <div class="box" align="center">
        <table>
            <tr>
            <td colspan="3">
            Reject Reason:  
            </td>
           <td colspan="3"><s:textarea name="rejectReason"  id ="rejectReason" cols="75" rows="4" /></td>
           </tr>
           <tr>
           <td colspan="3">&nbsp; </td>
           </tr>
       </table>
       <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="submitform();"><span class="btn_img"><span class="save">Submit</span></span></s:a>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="close">Close</span></span></s:a>
                </li>
            </ul>   
        </del>
       </div>
    </div>
</s:form>
</div>
</body>
</html>