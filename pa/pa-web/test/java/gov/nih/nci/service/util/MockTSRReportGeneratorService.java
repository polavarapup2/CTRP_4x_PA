/**
 *
 */
package gov.nih.nci.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.service.MockStudyProtocolService;

import java.io.ByteArrayOutputStream;

/**
 * @author Vrushali
 *
 */
public class MockTSRReportGeneratorService implements TSRReportGeneratorServiceRemote {


	/** The Constant FONT_TITLE. */
	  private static final String FONT_TITLE = "<FONT SIZE='5' FACE='calibiri'>";

	  /** The Constant FONT_SECTION. */
	  private static final String FONT_SECTION = "<FONT SIZE='4' color='brown' FACE='calibiri'>";

	  /** The Constant FONT_LABEL. */
	  private static final String FONT_LABEL = "<FONT SIZE='3' FACE='calibiri'>";

	  /** The Constant FONT_TEXT. */
	  private static final String FONT_TEXT = "<FONT SIZE='3' FACE='calibiri'>";


	  /** The Constant FONT_END. */
	  private static final String FONT_END = "</FONT>";

	  /** The Constant CENTER_B. */
	  private static final String CENTER_B = "<CENTER>";

	  /** The Constant CENTER_E. */
	  private static final String CENTER_E = "</CENTER>";
	  /** The Constant BR. */
	  private static final String BR = "<BR>";
	  /** The Constant BLD_B. */
	  private static final String BLD_B = "<B>";

	  /** The Constant BLD_E. */
	  private static final String BLD_E = "</B>";

	  /** The Constant NO_INFO. */
	  private static final String NO_INFO = " <I> No Data Available </I>";

	  /** The Constant EMPTY. */
	  private static final String EMPTY = "";


	  StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();

	 public String generateTSRHtml(Ii studyProtocolIi) throws PAException {
		if (studyProtocolIi == null) {
	          throw new PAException("Study Protocol Identifier is null");
	      }

	      StringBuffer htmldata = new StringBuffer();
	      htmldata.append(FONT_TITLE).append(CENTER_B).append(appendBoldData("Trial Summary Report"))
	              .append(CENTER_E).append(FONT_END);
	      htmldata.append(BR);
	      htmldata.append(CENTER_B);
	      htmldata.append(appendData("Date", PAUtil.today(), false, true)).append(CENTER_E);
	      StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
	      htmldata.append(spDTO.getOfficialTitle());
	      return htmldata.toString();
	}

	 private String appendBoldData(String data) {
	      return BLD_B + data + BLD_E;
	  }
	 private String appendData(String column , String data, boolean newLine , boolean bold) {
	      return (newLine ? BR : EMPTY) + (bold ? BLD_B : EMPTY) + FONT_LABEL
	          + column + ": " + FONT_END + (bold ? BLD_E : EMPTY)
	          + FONT_TEXT + getInfo(data, true) + FONT_END;
	  }
	 private String getInfo(String st , boolean appendNoData) {
	      if (st == null || st.equalsIgnoreCase("")  && appendNoData) {
	          return NO_INFO;
	      }
	      return st;
	  }

	 /**
	  * {@inheritDoc}
	  */
	 public ByteArrayOutputStream generatePdfTsrReport(Ii studyProtocolIi) throws PAException {
	     // TODO Auto-generated method stub
	     return null;
	 }

	 /**
      * {@inheritDoc}
      */
	 public ByteArrayOutputStream generateRtfTsrReport(Ii studyProtocolIi) throws PAException {
	     // TODO Auto-generated method stub
	     return null;
	 }

	 /**
      * {@inheritDoc}
      */
	 public ByteArrayOutputStream generateHtmlTsrReport(Ii studyProtocolIi) throws PAException {
	     // TODO Auto-generated method stub
	     return null;
	 }
}
