<%@taglib prefix="s" uri="/struts-tags" %>
<%@ tag display-name="toggleDeleteBtn" description="Select All/Deselect All button"  body-content="empty"%>

<s:a href="javascript:void(0);" onclick="toggleDeleteCheckboxes()" onkeypress="toggleDeleteCheckboxes()" cssClass="btn"><span class="btn_img"><span class="delete" name="multiDeleteBtnText">Select All</span></span></s:a>