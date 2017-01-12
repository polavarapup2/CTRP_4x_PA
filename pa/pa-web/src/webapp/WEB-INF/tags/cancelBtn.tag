<%@ tag display-name="cancelBtn"  description="Generates a cancel button" body-content="empty"%>
<%@ attribute name="cancelUrl" required="true" type="java.lang.String" description="Cancel url"%>

<a onclick="cancelAction('${cancelUrl}');" href="javascript:void(0)" class="btn">
  <span class="btn_img"><span class="cancel">Cancel</span></span>
</a>