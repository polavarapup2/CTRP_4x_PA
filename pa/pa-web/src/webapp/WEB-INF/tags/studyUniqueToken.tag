<%@ tag display-name="uniqueStudyToken"  description="Handles creation of study's unique token"  body-content="empty" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:hidden name="studyProtocolToken" value="%{#session.studyProtocolToken}" id="studyProtocolToken"/>