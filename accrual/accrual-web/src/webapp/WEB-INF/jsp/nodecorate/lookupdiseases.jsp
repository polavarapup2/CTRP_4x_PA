<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html lang="en">
    <head>
        <s:if test="%{siteLookUp}">
            <fmt:message key="site.title" var="pageTitle" />
        </s:if>
        <s:else>
            <fmt:message key="disease.title" var="pageTitle" />
        </s:else>
        <title>${pageTitle}</title>        
        <s:head/>
        <%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery-1.10.2.min.js'/>"></script>
        
        <script type="text/javascript">	        
            function submitform(disid, type) {
                top.window.loadDiv(disid, type, $("#siteLookUp").val());
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {     
                var url = '/accrual/protected/popupdisplayList.action?siteLookUp=' + $("#siteLookUp").val();
                var params = { searchName: $("#searchName").val(),
                		searchCode: $("#searchCode").val(),
                        page: "searchLookup",
                        searchCodeSystem: $("#searchCodeSystem").val()
                		};
                $('#getDiseases').load(url, params);  
            }
            
            $(function() {
                $('input[type="text"]').keypress(function (e) {
                    if (e.which == 13) {
                    	loadDiv();
                      return false;    //<---- Add this line
                    }
                  });
            });
            
        </script>
    </head> 
    <body>
        
    <s:form id="diseases" name="diseases" cssClass="form-horizontal" role="form">
      <div class="modal-header">            
                <s:hidden id="siteLookUp" name="siteLookUp"/>
                <h4 class="modal-title" id="myModalLabel">
	                <s:if test="%{siteLookUp}">
	                     <fmt:message key="site.search"/>
	                </s:if>
	                <s:else>
	                    <fmt:message key="disease.search"/>
	                </s:else>
                </h4></div>
      <div class="modal-body">
                    <div class="form-group"><label for="searchName" class="col-xs-3 control-label">
                        <s:if test="%{siteLookUp}">
                            <fmt:message key="site.name"/>
                        </s:if>
                        <s:else>
                            <fmt:message key="disease.name"/>
                        </s:else>
                        </label>
                        <div class="col-xs-4">
                        <s:textfield id="searchName" name="searchName" cssClass="form-control" /></div>
      
                        <label for="searchCode" class="col-xs-1 control-label">
	                        <s:if test="%{siteLookUp}">
	                            <fmt:message key="site.code"/>
	                        </s:if>
	                        <s:else>
	                            <fmt:message key="disease.code"/>
	                        </s:else>
                        </label>
                        <div class="col-xs-4"><s:textfield id="searchCode" name="searchCode"  cssClass="form-control" /></div>
          </div>
                     <div class="form-group"><label for="includeSDC" class="col-xs-3 control-label">
	                        <s:if test="%{siteLookUp}">
	                            <fmt:message key="site.codeSystem"/>
	                        </s:if>
	                        <s:else>
	                            <fmt:message key="disease.codeSystem"/>
	                        </s:else>    
                        </label><div class="col-xs-4">
                            <s:select id ="searchCodeSystem" name="searchCodeSystem" list="listOfDiseaseCodeSystems"  cssClass="form-control"/>
                       </div>
          </div>
          </div>
                <div class="form-group">
        <div class="col-xs-4 col-xs-offset-4 mt20">
                                <button type="button" class="btn btn-icon btn-default" onclick="loadDiv()"><i class="fa-search"></i>Search</button>
                                <button type="button" class="btn btn-icon btn-default" onclick="window.top.hidePopWin();"><i class="fa-times"></i>Cancel</button>                           
                </div>
                </div>
                <div id="getDiseases" align="center">   
                    <jsp:include page="/WEB-INF/jsp/nodecorate/lookupdiseasesdisplayList.jsp"/>
                </div>
            </s:form>
    </body>
</html>