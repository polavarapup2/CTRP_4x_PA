<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        
        <script language="javascript" type="text/javascript">
            function submitform(persid, name) {   
                var email = $(persid + 'email');
                var phone = $(persid + 'phone');
                if (email == undefined || email.value == '') {
                    email = '';
                } else {
                    email = email[$(persid + 'email').selectedIndex].value;
                }
                if(phone == undefined || phone.value == '') {
                    phone = '';
                } else {
                    phone = phone[$(persid + 'phone').selectedIndex].value;
                }
                top.window.setpersid(persid, name,email,phone);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {
                var url = '/pa/protected/ajaxGenericContactdisplayTitleList.action';
                var params = {
                    orgGenericContactIdentifier: $("orgContactId").value,
                    title: $("search_title").value,
                    type: $("type").value
                };
                var div = $('getTitles');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
            }
            
            function setSearchFormVisible(){
                $("searchTitleJsp").style.display="";
                $("createTitleJsp").style.display="none";
            }
        
            function setCreateFormVisible(){
                $("searchTitleJsp").style.display="none";
                $("createTitleJsp").style.display="";
            }
            
            function createTitle() {
                var url = '/pa/protected/ajaxGenericContactcreate.action';
                var params = {
                    email: $("email").value,     
                    orgGenericContactIdentifier: $("orgContactId").value,
                    phone: $("phone").value,
                    title: $("create_title").value,
                    type: $("type").value
                };
                var div = $('getTitles');        
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
                var aj = callAjaxPost(div, url, params);   
            }
            
            function setFocusToFirstControl() {
                for (var f=0; f < document.forms.length; f++) {
                    for(var i=0; i < document.forms[f].length; i++) {
                        var elt = document.forms[f][i];
                        if (elt.type != "hidden" && elt.disabled != true && elt.id != 'enableEnterSubmit') {
                            try {
                                elt.focus();
                                return;
                            } catch(er) {
                            }
                        }
                    }
                }
            }
        </script> 
    </head> 
    <body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
        <div class="box">
            <s:form id="titles" name="titles" >
                <s:hidden name="orgContactId" id="orgContactId"></s:hidden>
                <s:hidden name="type" id="type"></s:hidden>
                <div id="searchTitleJsp">
                    <jsp:include page="/WEB-INF/jsp/nodecorate/searchTitle.jsp"/>
                </div>
                <div id="createTitleJsp" style="display:none">
                    <jsp:include page="/WEB-INF/jsp/nodecorate/addTitle.jsp"/>
                </div>
                <div class="line"></div>
                <div id="getTitles" align="center">    
                     <jsp:include page="/WEB-INF/jsp/nodecorate/displayTitleList.jsp"/>
                </div>
            </s:form>
        </div>
    </body>
</html>