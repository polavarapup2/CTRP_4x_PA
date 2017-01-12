<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <body>
   <s:actionerror/>
   <s:actionmessage/>
   <pa:sucessMessage />
    <s:form action="pdqTrialLoad" method="POST" enctype="multipart/form-data">
        <tr>
            <td colspan="2">
            <h1>PDQ XML Import</h1>
            </td>
        </tr>

        <s:file name="upload" label="File" /> <br/>
        <s:label>Email address to send report to: </s:label><s:textfield name="mailDestination"></s:textfield><br/>
        <s:property value="uploadContentType" />
        <s:property value="uploadFileName" />
        <s:submit />
    </s:form>
  </body>
 </html>