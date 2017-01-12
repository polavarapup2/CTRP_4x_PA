<%@ tag display-name="unRejectTrialComment" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
   <div id="comment-dialog" style="display: none">
       <div class="body" style=""> 
          <div>
              <label for="comments">Enter Un-Reject Trial comment:</label></br>
              <s:textarea id="comments" name="comments" value="" rows="5" maxlength="4000" cssClass="charcounter" cssStyle="width: 100%;" />
		</div>
		<br />
		<div align="center">
			<button id="save" type="button" class="btn btn-icon btn-primary"
				data-dismiss="modal" value="Save"
				onclick="$('comment-dialog').hide();saveUnrejectReason();">
				<i class="fa fa-save"></i>Ok</button>
			<button id="cancel" type="button" class="btn btn-icon btn-default"
				data-dismiss="modal" value="Cancel"
				onclick="$('comment-dialog').hide();">
				<i class="fa fa-times-circle"></i>Cancel</button>
		</div>
	</div>
</div> 