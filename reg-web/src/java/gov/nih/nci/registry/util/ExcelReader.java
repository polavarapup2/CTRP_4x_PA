/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.enums.BatchStringConstants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @author Vrushali
 *
 */
public class ExcelReader { // NOPMD
    private static final Logger LOG = Logger.getLogger(ExcelReader.class);

    /**
     *
     * @param wb wb
     * @param orgName orgName
     * @throws PAException PAException
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<StudyProtocolBatchDTO> convertToDTOFromExcelWorkbook(HSSFWorkbook wb, String orgName) // NOPMD
            throws PAException { 
        List<StudyProtocolBatchDTO> batchDtoList = new ArrayList<StudyProtocolBatchDTO>();
        StudyProtocolBatchDTO batchDto = null;
        if (wb == null) {
            throw new PAException("HSSFWorkbook cannot be null");
        }
        int numOfSheets = wb.getNumberOfSheets();
        if (numOfSheets == 0) {
            LOG.error("There are no workbook to process");
            throw new PAException("There are no workbook to process");
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            LOG.error("There are no work sheets to process");
            throw new PAException(" There are no work sheets to process");
        }
        
        //Eliminate blank rows
        boolean stop = false;
        boolean nonBlankRowFound;
        int c;
        HSSFRow lastRow = null;
        HSSFCell cell = null;

        while (!stop) {
            nonBlankRowFound = false;
            lastRow = sheet.getRow(sheet.getLastRowNum());
            for (c = lastRow.getFirstCellNum(); c <= lastRow.getLastCellNum(); c++) {
                cell = lastRow.getCell(c);
                if (cell != null && lastRow.getCell(c).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
                    nonBlankRowFound = true;
                }
            }
            if (nonBlankRowFound) {
                stop = true;
            } else {
                sheet.removeRow(lastRow);
            }
        }        

        // create a dynamic hashmap of the headers items
        Map<Integer, String> headerMap = new HashMap<Integer, String>();
        getHeaders(sheet, headerMap);

        boolean flag = true; // loop every row in the work sheet
        for (Iterator<HSSFRow> rows = sheet.rowIterator(); rows.hasNext();) {
            HSSFRow row = rows.next();
            if (flag) {
                flag = false; // skip the first row, since its a header row
                continue;
            }
            batchDto = new StudyProtocolBatchDTO();
            createDto(row, batchDto, orgName, headerMap);
            batchDtoList.add(batchDto);
        }
        return batchDtoList;
    }

    private void getHeaders(HSSFSheet sheet, Map<Integer, String> headerMap) {
        if (sheet.getLastRowNum() > 0) {
            HSSFRow headerRow = sheet.getRow(0);
            short c1 = headerRow.getFirstCellNum();
            short c2 = headerRow.getLastCellNum();

            // populate hashmap of the headers items dynamically
            for (int c = c1; c < c2; c++) {
                HSSFCell cell = headerRow.getCell(c); // loop for every cell in each row
                String cellValue = null;
                if (cell != null) {
                    cellValue = getCellValue(cell);
                    headerMap.put(c, (cellValue == null) ? cellValue : cellValue.toUpperCase(Locale.US));
                }
            }
        }
    }

    private void createDto(HSSFRow row, StudyProtocolBatchDTO batchDto, String orgName, Map<Integer, String> headerMap)
            throws PAException {
        short c1 = row.getFirstCellNum();
        short c2 = row.getLastCellNum();
        for (int c = c1; c < c2; c++) {
            HSSFCell cell = row.getCell(c); // loop for every cell in each row
            String cellValue = null;
            if (cell != null) {
                cellValue = getCellValue(cell);
                setDto(batchDto, cellValue, c, headerMap); // set corresponding values
                if (StringUtils.equalsIgnoreCase("ctep", orgName)) {
                    batchDto.setCtepIdentifier(batchDto.getUniqueTrialId());
                } else if (StringUtils.equalsIgnoreCase("dcp", orgName)) {
                    batchDto.setDcpIdentifier(batchDto.getUniqueTrialId());
                }
            }
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    private String getCellValue(HSSFCell cell) {
        String result = null;
        int cellType = cell.getCellType();
        switch (cellType) {
        case HSSFCell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                result = convertDateToString(cell.getDateCellValue(), "MM/dd/yyyy");
            } else {
                long x = (long) cell.getNumericCellValue();
                result = Long.toString(x); 
            }
            break;
        case HSSFCell.CELL_TYPE_STRING:
            result = cell.getRichStringCellValue().getString();
            if (null != result) {
                result = result.trim();
            }
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        default:
            break;
        }
        return result;
    }

    /**
     *
     * @param is is
     * @return wb
     * @throws PAException exception
     * @throws IOException i
     */
    public HSSFWorkbook parseExcel(InputStream is) throws PAException, IOException {
        if (is == null) {
            throw new PAException(" Input stream cannot be null ");
        }
        POIFSFileSystem fs = new POIFSFileSystem(is);
        return new HSSFWorkbook(fs);
    }

    /**
     *
     * @param cellValue cellValue
     * @param col col
     * @throws PAException
     * @return dto dto
     */
    private StudyProtocolBatchDTO setDto(StudyProtocolBatchDTO batchDto, String cellValue, int col,
            Map<Integer, String> headerMap) throws PAException {
        String colHeader = headerMap.get(col);
        validateHeader(colHeader);
        switch (BatchStringConstants.getByCode(colHeader)) {
        case IND_HAS_EXPANDED_ACCESS:
            setDtoIndExpandedAccess(batchDto, cellValue);
            break;
        case CTGOV_XML_INDICATOR:
            setDtoCtGovXmlIndicator(batchDto, cellValue);
            break;
        case NCI_FUNDED_INDICATOR:
            setNciFunded(batchDto, cellValue);
            break;
        case OTHER_TRIAL_IDENTIFIER:
            String otherIdentifiersValues = cellValue;
            List<Ii> otherIdentifiers = null;
            if (StringUtils.isNotEmpty(otherIdentifiersValues)) {
                otherIdentifiers = new ArrayList<Ii>();
                StringTokenizer st = new StringTokenizer(otherIdentifiersValues, ";");
                while (st.hasMoreTokens()) {
                    otherIdentifiers.add(IiConverter.convertToOtherIdentifierIi(st.nextToken()));
                }
            }
            batchDto.setOtherTrialIdentifiers(otherIdentifiers);
            break;
        default:
            setDtoAttributes(colHeader, cellValue, batchDto);
        }
        return batchDto;
    }

    private void validateHeader(String colHeader) throws PAException {
        if (colHeader == null || BatchStringConstants.getByCode(colHeader) == null) {
            throw new PAException(" Unknown column name " + colHeader + ". Please check the specification");
        }
    }

    private void setDtoCtGovXmlIndicator(StudyProtocolBatchDTO batchDto, String cellValue) {
        if (cellValue != null && cellValue.equalsIgnoreCase("No")) {
            batchDto.setCtGovXmlIndicator(false);
        } else {
            batchDto.setCtGovXmlIndicator(true);
        }
    }

    private void setNciFunded(StudyProtocolBatchDTO batchDto, String cellValue) {
        if (cellValue.equalsIgnoreCase("No")) {
            batchDto.setNciGrant(false);
        } else if (cellValue.equalsIgnoreCase("Yes")) {
            batchDto.setNciGrant(true);
        }
    }

    private void setDtoIndExpandedAccess(StudyProtocolBatchDTO batchDto, String cellValue) {
        if (cellValue != null && cellValue.equalsIgnoreCase("Yes")) {
            batchDto.setIndHasExpandedAccess("True");
        } else {
            batchDto.setIndHasExpandedAccess(cellValue);
        }
    }

    /**
     * Sets the dto attributes.
     *
     * @param colHeader the col header
     * @param cellValue the cell value
     *
     * @throws PAException the PA exception
     */
    private void setDtoAttributes(String colHeader, String cellValue, StudyProtocolBatchDTO batchDto)
            throws PAException {
        try {
            Class<?> clazz = Class.forName("gov.nih.nci.registry.dto.StudyProtocolBatchDTO");
            Class<?>[] paramTypes = new Class<?>[] {String.class };
            final String methodName = BatchStringConstants.getByCode(colHeader).getMethodName();
            Method meth = clazz.getDeclaredMethod(methodName, paramTypes);
            meth.invoke(batchDto, cellValue);
        } catch (Exception e) {
            throw new PAException("Error setting the cell value to Dto", e);
        }

    }

    /**
     *
     * @param date date
     * @param format format
     * @return Str
     */
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat dateFormatter = null;
        String formattedDate = null;

        if (date != null && format != null) {
            dateFormatter = new SimpleDateFormat(format, Locale.US);
            formattedDate = dateFormatter.format(date);
        }
        return formattedDate;

    }
}
