<%@ tag display-name="saveAndCloseBtn"
	description="Renders Save and Close buttons" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="align-center button-row">
    <button type="button" class="btn btn-icon btn-primary review" onclick="doSave()" onkeypress="doSave()"><i class="fa-floppy-o"></i>Save</button>
    <button type="button" class="btn btn-icon btn-default" onkeypress="window.top.hidePopWin();" onclick="window.top.hidePopWin();"><i class="fa-times-circle"></i>Cancel</button>
</div>