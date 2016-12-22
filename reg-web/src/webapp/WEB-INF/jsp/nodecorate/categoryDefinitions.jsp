<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<div class="modal fade bs-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myLargeModalLabel">Category Definitions</h4>
      </div>
      <div class="modal-body">
        <table class="table">
          <tbody>
            <tr>
              <td align="right"><strong>National:</strong></td>
              <td><s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.National','Registry')"/> 
              </td>
            </tr>
            <tr>
              <td align="right" width="200"><strong>Externally Peer-Reviewed:</strong></td>
              <td><s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.ExternallyPeerReviewed','Registry')"/></td>
            </tr>
            <tr>
              <td align="right"><strong>Institutional:</strong></td>
           <td><s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.Institutional','Registry')"/></td>
            </tr>
            <tr>
              <td align="right"><strong>Industrial/Other:</strong></td>
              <td><s:property escapeHtml="false" escapeXml="false" 
                                value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.Industrial','Registry')"/></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-icon btn-default" data-dismiss="modal"><i class="fa-times"></i>Close</button>
      </div>
    </div>
  </div>
</div>