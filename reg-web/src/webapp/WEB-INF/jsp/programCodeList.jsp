<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="programcodes.page.title"/></title>
        <s:head/>        
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/programcode.js'/>"></script>        
        <link href="<c:url value='/styles/jquery-datatables/css/jquery.dataTables.min.css'/>"
        rel="stylesheet" type="text/css" media="all" />
        <link href="<c:url value='/styles/jquery-datatables/css/buttons.dataTables.min.css'/>"
        rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.buttons.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jszip.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/buttons.html5.min.js'/>"></script>
        <style>
            #dialog-edit .row,#dialog-confirm .row {
            padding-top: 5px;
            }
        
            #dialog-edit .charcounter,#dialog-confirm .charcounter {
                font-size: 75%;
            }
            
            .table th { font-size:10pt;  }
    
        </style>
    </head>
<body>
<div class="container">
<c:set var="topic" scope="request" value="manageprogramcodesmasterlist"/>
    <div class="alert alert-success confirm_msg" style="display: none;" id="programCodeUpdatedMessageDiv">
          <strong>Message.</strong>&nbsp;<fmt:message key="programcode.updated"/>
    </div>
    
    <div class="alert alert-success confirm_msg" style="display: none;" id="programCodeDeletedMessageDiv">
          <strong>Message.</strong>&nbsp;<fmt:message key="programcode.deleted"/>
    </div>
    
    <div class="alert alert-success confirm_msg" style="display: none;" id="programCodeInactivatedMessageDiv">
          <strong>Message.</strong>&nbsp;<fmt:message key="programcode.inactivated"/>
    </div>
    
    <div class="alert alert-success confirm_msg" style="display: none;" id="programCodeAddedMessageDiv">
          <strong>Message.</strong>&nbsp;<fmt:message key="programcode.added"/>
    </div>
    
    
    <!-- Program Codes Errors Modal -->
    <div class="modal fade" id="programCodeErrorMessageModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Error</h4>
          </div>
          <div class="modal-body">
            <div class="alert alert-danger" role="alert">
              <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" style="display:inline"></span>
               <p id="programCodesErrorList" style="display:inline"></p>
            </div>
           
          </div>
          <div class="modal-footer">
            <button id="cancelButton" type="button" class="btn btn-default" data-dismiss="modal">OK</button>
          </div>
        </div>
      </div>
    </div>
    
    <div id="dialog-edit" title="Edit Program Code" style="display: none;">
        <div class="container-fluid">
            <p>Organization Family of Affiliate Site: <b>${selectedFamilyDTO.name}</b>
            <div class="row">
                <div class="col-xs-4" align="right">
                    <label for="programCode" class="col-sm control-label"><fmt:message key="programcodes.program.code.label"/><span class="required">*</span></label>
                </div>
                <div class="col-xs-8">
                    <s:textfield required="true" name="updatedProgramCode" id="updatedProgramCode" maxlength="64" size="32" cssClass="form-control" />
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4" align="right">
                    <label for="programName" class="col-sm control-label"><fmt:message key="programcodes.program.name.label"/></label>
                </div>
                <div class="col-xs-8">
                     <s:textfield name="updatedProgramName" id="updatedProgramName" maxlength="64" size="32" cssClass="form-control" />
                </div>
            </div>
           <div class="col-md-12">
                <br />
              </div> 
            <p><b>Warning</b>: Modifying the program code will cause <b>all trials</b> currently <br>
               assigned to the old program code to be reassigned to the new <br>
               program code. The old code will be permanently removed.</p>
        </div>
    </div>
    
    <div id="dialog-inactivate-program-code" title="Inactivate Program Code Confirmation" style="display: none;">
        <div class="container-fluid">
            <p>The following program code is assigned to one or more trial(s):
            <p id="programdCodeToBeInactivated"></p>
            
            <p>Inactivating this program code will:
            <ul style="list-style-type:none">
              <li>- Unassign it from all trials  that were active during  the reporting period (see list below), and</li>
              <li>- Make it no longer available to assign to trials</li>
            </ul>
             
            <p>Note that only trials that were active at any time during the reporting period will be affected. 
            The reporting  period for your center is 1/1/2015 to 12/31/2015.
             
             <div class="table-header-wrap">
                <div>
                    <hr/>
                </div>  
                <table id="trialsAssociatedToProgramCodes" class="table table-bordered table-striped">
                   <thead>
                      <tr>
                         <th nowrap="nowrap" width="15%">Trial ID(s)</th>
                         <th nowrap="nowrap" width="30%">Title</th>
                         <th nowrap="nowrap" width="20%">Lead Organization</th>
                         <th nowrap="nowrap" width="13%">Principal Investigator</th>
                         <th nowrap="nowrap" width="12%">Trial Status</th>
                         <th nowrap="nowrap" width="10%">Program Code(s)</th>
                      </tr>
                   </thead>
                   <tbody />
                </table>
              </div>
              <div>
                  <hr/>
              </div>  
             
            <p>Are you sure you would like to proceed with this action?</p>
        </div>
    </div>
    
    <div id="dialog-confirm" title="Confirm Program Code Modification" style="display: none;">
        <div class="container-fluid">
            <p>You are about to modify a program code value. All trials currently <br>
               assigned to the old program code will be reassigned to the new value.
               The old code value  will be permanently removed.</p>
              
            <p>Are you sure you would like to proceed with this change?</p>
        </div>
    </div>
    
    <div id="dialog-confirm-delete" title="Confirm Delete" style="display: none;">
        <div>
            <p>The following program code is not assigned to any trial,<br> and will be permanently deleted: </p>
            <p id="programdCodeToBeDeleted"></p>
           <p>Please confirm.</p>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-12 ">
            <h1 class="heading"><span><fmt:message key="programcodes.master.list"/></span></h1>
        </div>      
        <div class="col-md-12">
            
            <s:form cssClass="form-horizontal" name="programCodeHeader" action="programCodes.action" id="programCodesHeaderForm">       
              <div class="form-group">
                
                 <c:choose>
                    <c:when test="${sessionScope.programCodeAdmin}">
                        <label class=" col-sm-2 control-label" for="selectedFamily"><fmt:message key="programcodes.organization.family.label"/></label>
                        <div class="col-sm-6">
                             <s:select name="selectedDTOId"
                                id="selectedDTOId"
                                cssClass="form-control" 
                                style="width:auto;max-width:90%;"
                                list="familyDTOs" 
                                listKey="id"
                                listValue="name"
                                value="%{#session['family_dto'].id}"
                                onchange="document.getElementById('programCodesHeaderForm').submit()"/>
                        </div>     
                    </c:when>
                    <c:otherwise>
                        <label class="control-label" style="width: 15.6667%;" for="selectedFamily"><fmt:message key="programcodes.organization.family.label"/></label> &nbsp;&nbsp;${selectedFamilyDTO.name}
                    </c:otherwise>
                </c:choose>
                <s:hidden id="poID" value="%{selectedFamilyDTO.poId}" />
              </div>              
              <div>
                 <table id="newProgramCodesRowTable" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                         <th nowrap="nowrap" width="25%">Program Code <span class="required"> * </span></th>
                         <th nowrap="nowrap" width="50%">Program Name</th>
                         <th nowrap="nowrap" width="25%" />
                      </tr>
                   </thead>
                    <tbody>
                        <td nowrap="nowrap" width="25%"><input type="text" name="newProgramCode" id="newProgramCode" value="" class="form-control"/> </td>
                        <td nowrap="nowrap" width="50%"><input type="text" name="newProgramName" id="newProgramName" value="" class="form-control"/> </td>
                        <td nowrap="nowrap" width="25%"><button type="button" class="btn btn-icon btn-primary" id="addProgramCodeButton" onclick="addProgramCode();"> 
                            <i class="fa-plus"></i> Add Program Code </button> 
                            <div id="program_code_progress_indicator_panel" style="display: none;">
                                <img src="${pageContext.request.contextPath}/images/loading.gif" alt="Progress Indicator" width="18" height="18" />
                            </div>
                        </td>
                    </tbody>
                </table>
              </div> 
              <div class="table-header-wrap">
                <div>
                    <hr/>
                </div>  
                <table id="programCodesTable" class="table table-bordered table-striped">
                   <thead>
                      <tr>
                         <th nowrap="nowrap" width="25%">Program Code</th>
                         <th nowrap="nowrap" width="50%">Program Name</th>
                         <th nowrap="nowrap" width="25%" />
                      </tr>
                   </thead>
                   <tbody />
                </table>
              </div>        
            </s:form>       
        </div>
    </div>
</div>
</body>
