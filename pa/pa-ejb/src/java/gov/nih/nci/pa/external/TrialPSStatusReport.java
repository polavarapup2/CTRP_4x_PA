/**
 * 
 */
package gov.nih.nci.pa.external;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.BaseStatusTransitionService;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author vinodh
 * 
 */
public class TrialPSStatusReport {

    private static final Logger LOG = Logger.getLogger(TrialStatusReport.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static String STATUS = "participating_site_status";
    public static String STATUS_DT = "participating_site_status_date";
    public static String COMMENTS = "comments_text";
    public static String SS_REC_ID = "study_site_accrual_status_record_identifier";
    public static String ADD_ON = "added_on";
    public static String ADD_BY = "added_by_name";
    public static String UPD_ON = "last_updated_on";
    public static String UPD_BY = "last_updated_by_name";

    private String dbUrl;
    private String dbUser;
    private String dbPasswd;
    private String outputDir;

    private Map<String, TrialAndSiteInfo> trialAndPSInfoMap = new LinkedHashMap<String, TrialAndSiteInfo>();
    private Map<String, List<StatusDto>> psStatusDtosMap = new LinkedHashMap<String, List<StatusDto>>();

    private static String TRIAL_PS_SQL = 
            "select ds.nci_id, ds.nct_id, category as trial_type, ds.lead_org_org_family as lead_org_family, "
            + " exists(select nci_id from dw_study_record_owner where name ='CTEPRSS RSS'and nci_id=ds.nci_id) as is_rss_trial, "
            + "ds.lead_org, ds.submitter_organization as submitting_org, ds.submitter_name, ds.summary_4_funding_category, "
            + "ds.ctep_id, ds.dcp_id, ds.internal_system_id as study_protocol_identifier, ds.processing_status as study_processing_status, "
            + " ds.processing_status_date as study_processing_status_date, " 
            + "ds.current_trial_status as most_recent_study_status, ds.current_trial_status_date as most_recent_study_status_date, "
            + "dsps.internal_system_id as participating_site_identifier, dsps.org_org_family as participating_site_org_family, "
            + "dsps.org_name as participating_site_org_name, dspsaa.comments as comments_text, "
            + "dspsaa.status as participating_site_status, dspsaa.status_date as participating_site_status_date, "
            + "dspsaa.internal_system_id as study_site_accrual_status_record_identifier, "
            + "dspsaa.date_created as added_on, dspsaa.date_last_updated as last_updated_on, "
            + "dspsaa.user_created as added_by_name, dspsaa.user_last_updated as last_updated_by_name "
            + "from dw_study ds join DW_STUDY_PARTICIPATING_SITE dsps on ds.nci_id=dsps.nci_id "
            + "join DW_STUDY_PARTICIPATING_SITE_ACCRUAL_STATUS dspsaa on dsps.internal_system_id=dspsaa.study_site_id "
            + "where ds.processing_status not in ('Rejected', 'Submission Terminated') "
            + "order by ds.category, ds.nci_id, dsps.internal_system_id, dspsaa.status_date, dspsaa.internal_system_id";

    BaseStatusTransitionService service = new BaseStatusTransitionService();
    
    TrialStatusReport tsReport = null;
    
    Set<String> trialsWithSSTrxErrs = new HashSet<String>(); 

    SXSSFWorkbook workbook = null;
    SXSSFSheet invalidPSSTrxSheet = null;
    SXSSFSheet tsWithInvalidPSSTrxSheet = null;

    CellStyle hdrStyle = null;
    CellStyle statusHdrStyle = null;
    CellStyle dtCellStyle = null;
    CellStyle wCellStyle = null;
    CellStyle vCellStyle = null;

    int hdrRowIndex = 1;
    int rowIndex = hdrRowIndex;

    /**
     * @param outputDir
     * @param dbUrl
     * @param dbUser
     * @param dbPasswd
     * 
     * @throws Exception
     *             - if there are any
     */
    public TrialPSStatusReport(String outputDir, String dbUrl, String dbUser,
            String dbPasswd) throws Exception {
        this(outputDir, dbUrl, dbUser, dbPasswd, null, null, null);
    }
    
    /**
     * 
     * @param outputDir
     * @param dbUrl
     * @param dbUser
     * @param dbPasswd
     * @param workbook
     * @param sheet
     * @throws Exception if there are any
     */
    public TrialPSStatusReport(String outputDir, String dbUrl, String dbUser,
            String dbPasswd, SXSSFWorkbook workbook, SXSSFSheet pssheet, SXSSFSheet tssheet) throws Exception {
        super();
        this.outputDir = outputDir;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPasswd = dbPasswd;

        service.getStatusRules();
        
        if(workbook == null) {
//            this.workbook = new XSSFWorkbook();
//            FileInputStream inputStream = new FileInputStream(new File(outputDir,
//                    "TrialsWithInvalidSiteStatusTransitions-" + SDF.format(new Date())
//                    + ".xlsx"));
//            XSSFWorkbook xssfWbk = new XSSFWorkbook(inputStream);
//            inputStream.close();

//            this.workbook = new SXSSFWorkbook(xssfWbk); 
//            this.workbook.setCompressTempFiles(true);
            
            this.workbook = new SXSSFWorkbook(100); 
            this.workbook.setCompressTempFiles(true);
        } else {
            this.workbook = (SXSSFWorkbook) workbook;
        }
        
        if(pssheet == null) {
            this.invalidPSSTrxSheet = (SXSSFSheet) this.workbook
                    .createSheet("InvalidSiteStatusTrx");
            this.invalidPSSTrxSheet.setRandomAccessWindowSize(100);
        } else {
            this.invalidPSSTrxSheet = pssheet;
        }
        
        if(tssheet == null) {
            this.tsWithInvalidPSSTrxSheet = (SXSSFSheet) this.workbook
                    .createSheet("TrialsStatusWithInvalidSiteStatusTrx");
            this.tsWithInvalidPSSTrxSheet.setRandomAccessWindowSize(100);
        } else {
            this.tsWithInvalidPSSTrxSheet = tssheet;
        }
        
        //set col widths
        invalidPSSTrxSheet.setDefaultColumnWidth(3 * invalidPSSTrxSheet.getDefaultColumnWidth());
        tsWithInvalidPSSTrxSheet.setDefaultColumnWidth(3 * tsWithInvalidPSSTrxSheet.getDefaultColumnWidth());
        
        tsReport = new TrialStatusReport(outputDir, dbUrl, dbUser, dbPasswd, this.workbook, this.tsWithInvalidPSSTrxSheet);
        
        hdrStyle = getHeaderStyle();
        statusHdrStyle = getStatusHeaderStyle();
        dtCellStyle = getDateCellStyle();
        wCellStyle = getWrapCellStyle();
        vCellStyle = getValueCellStyle();
    }

    private Connection connect() {
        Connection postGresConn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Couldn't find Db driver class."
                    + cnfe.getMessage());
            System.exit(1);
        }
        try {
            postGresConn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
        } catch (SQLException se) {
            System.out.println("Couldn't connect to database."
                    + se.getMessage());
            System.exit(1);
        }

        if (postGresConn == null) {
            System.out.println("Error connecting to Postgres database.");
            System.exit(1);
        } else {
            System.out.println("Successfully connected to Postgres database.");
        }
        return postGresConn;
    }

    public void fetchTrialStatusInfo() throws Exception {
        try (Connection postGresConn = connect();
                Statement stmt = postGresConn.createStatement();
                ResultSet rs = stmt.executeQuery(TRIAL_PS_SQL);) {
            if (rs == null) {
                throw new Exception("ResultSet is null");
            }
            String prevPsId = "";
            String currPsId = null;

            TrialAndSiteInfo trialAndSiteInfo = null;
            StatusDto statusDto = null;

            List<StatusDto> currPSStatusDtos = null;

            while (rs.next()) {
                currPsId = rs.getString(TrialAndSiteInfo.PS_ID);
                
                if (!StringUtils.equals(prevPsId, currPsId)) {
                    runStatusTransitionsValidation(trialAndSiteInfo,
                            currPSStatusDtos);
                    prevPsId = currPsId;
                    currPSStatusDtos = new ArrayList<StatusDto>();
                    trialAndSiteInfo = new TrialAndSiteInfo(true);
                    trialAndSiteInfo.map(rs);

                    statusDto = new StatusDto();
                    mapStatusDto(statusDto, rs);

                    trialAndPSInfoMap.put(currPsId, trialAndSiteInfo);
                    psStatusDtosMap.put(currPsId, currPSStatusDtos);
                } else {
                    statusDto = new StatusDto();
                    mapStatusDto(statusDto, rs);
                }
                psStatusDtosMap.get(currPsId).add(statusDto);
            }
            //run for last trial & PS
            runStatusTransitionsValidation(trialAndSiteInfo,
                    currPSStatusDtos);
        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * @param trialAndSiteInfo
     * @param currPSStatusDtos
     * @throws PAException
     */
    private void runStatusTransitionsValidation(
            TrialAndSiteInfo trialAndSiteInfo, List<StatusDto> currPSStatusDtos)
            throws PAException {
        if (trialAndSiteInfo != null && currPSStatusDtos != null
                && !currPSStatusDtos.isEmpty()) {
            service.validateStatusHistory(
                    AppName.REGISTRATION,
                    TrialType.valueOf(trialAndSiteInfo
                            .getColValue(TrialAndSiteInfo.TRIAL_TYPE)
                            .toString().toUpperCase()),
                    TransitionFor.SITE_STATUS, 
                    currPSStatusDtos);
        }
    }

    /**
     * @param statusDto
     * @param rs
     */
    private void mapStatusDto(StatusDto statusDto, ResultSet rs)
            throws Exception {
        if (rs == null) {
            throw new Exception("Error encountered: ResultSet is Null");
        }

        if (statusDto == null) {
            throw new Exception(
                    "Error encountered: Status record holder is Null");
        }

        statusDto.setId(rs.getLong(SS_REC_ID));
        statusDto.setStatusCode(rs.getString(STATUS));
        statusDto.setStatusDate(rs.getDate(STATUS_DT));
        statusDto.setReason(rs.getString(COMMENTS));
        statusDto.setCreatedOn(rs.getDate(ADD_ON));
        statusDto.setCreatedBy(rs.getString(ADD_BY));
        statusDto.setUpdatedOn(rs.getDate(UPD_ON));
        statusDto.setUpdatedBy(rs.getString(UPD_BY));
    }

    public void writeToFile() {
        try 
        (FileOutputStream fout = new FileOutputStream(new File(outputDir,
                "TrialsWithInvalidSiteStatusTransitions-" + SDF.format(new Date())
                + ".xlsx"));) 
                {
            addHeaders();
            Set<String> psIds = trialAndPSInfoMap.keySet();
            for (String psId : psIds) {
                writeRow(psId);
            }
            tsReport.setFltrByNciIds(trialsWithSSTrxErrs);
            tsReport.fetchTrialStatusInfo();
            tsReport.writeToWorkbook();
            
            workbook.write(fout);
            workbook.dispose();
        } catch (Exception e) {
            LOG.error("Error writing to output file: " + e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @return add Headers
     */
    private void addHeaders() {
        invalidPSSTrxSheet.createRow(hdrRowIndex-1);
        // hdrRow
        Row hdrRow = invalidPSSTrxSheet.createRow(hdrRowIndex);
        short hdrIdx = 0;
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.NCI_ID);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.NCT_ID);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.IS_RSS_TRIAL);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.TRIAL_TYPE);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.LEAD_ORG_FAM);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.LEAD_ORG);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.SUB_ORG);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.SUB_NM);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.SUM_4_FUND_CAT);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.STUDY_STS);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.STUDY_STS_DT);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.PS_ORG_FAM);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.PS_ORG_NM);
        //
        addStatusHeader(hdrRow, hdrIdx++, STATUS);
        addStatusHeader(hdrRow, hdrIdx++, STATUS_DT);
        addStatusHeader(hdrRow, hdrIdx++, "Status Transition Warnings");
        addStatusHeader(hdrRow, hdrIdx++, "Status Transition Errors");
        addStatusHeader(hdrRow, hdrIdx++, COMMENTS);
        addStatusHeader(hdrRow, hdrIdx++, SS_REC_ID);
        addStatusHeader(hdrRow, hdrIdx++, ADD_ON);
        addStatusHeader(hdrRow, hdrIdx++, UPD_ON);
        addStatusHeader(hdrRow, hdrIdx++, ADD_BY);
        addStatusHeader(hdrRow, hdrIdx++, UPD_BY);
        //
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.CTEP_ID);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.DCP_ID);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.SP_ID);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.PROC_STS);
        addHeader(hdrRow, hdrIdx++, TrialAndSiteInfo.PROC_STS_DT);
    }

    /**
     * @param hdrRow
     * @param s
     * @param nCI_ID
     */
    private void addHeader(Row hdrRow, short s, String value) {
        Cell c = hdrRow.createCell(s);
        c.setCellValue(value);
        c.setCellStyle(hdrStyle);
    }

    /**
     * @param hdrRow
     * @param s
     * @param nCI_ID
     */
    private void addStatusHeader(Row hdrRow, short s, String value) {
        Cell c = hdrRow.createCell(s);
        c.setCellValue(value);
        c.setCellStyle(statusHdrStyle);
    }

    private void writeRow(String psId) throws IOException {
        List<StatusDto> sdtos = psStatusDtosMap.get(psId);
        boolean hasErrors = false;
        for (StatusDto statusDto : sdtos) {
            if (statusDto.hasErrorOfType(ErrorType.ERROR)) {
                hasErrors = true;
                break;
            }
        }
        if (!hasErrors)
            return;
        
        TrialAndSiteInfo ti = trialAndPSInfoMap.get(psId);
        trialsWithSSTrxErrs.add((String) ti.getColValue(TrialAndSiteInfo.NCI_ID));
        
        for (int i = 0; i < sdtos.size(); i++) {
            writeRow(psId, i);
        }
    }

    /**
     * @throws IOException
     */
    private void writeRow(String psId, int sIndex) throws IOException {
        TrialAndSiteInfo ti = trialAndPSInfoMap.get(psId);
        StatusDto sdto = psStatusDtosMap.get(psId).get(sIndex);

        rowIndex++;
        Row valueRow = invalidPSSTrxSheet.createRow(rowIndex);
        valueRow.setHeightInPoints(4 * invalidPSSTrxSheet.getDefaultRowHeightInPoints());
        
        short valueIdx = 0;
        
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.NCI_ID));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.NCT_ID));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.IS_RSS_TRIAL));
        addStringValue(valueRow, valueIdx++,
                ti.getColValue(TrialAndSiteInfo.TRIAL_TYPE));
        addStringValue(valueRow, valueIdx++,
                ti.getColValue(TrialAndSiteInfo.LEAD_ORG_FAM));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.LEAD_ORG));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.SUB_ORG));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.SUB_NM));
        addStringValue(valueRow, valueIdx++,
                ti.getColValue(TrialAndSiteInfo.SUM_4_FUND_CAT));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.STUDY_STS));
        addDateValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.STUDY_STS_DT));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.PS_ORG_FAM));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.PS_ORG_NM));
        //
        addStringValue(valueRow, valueIdx++, sdto.getStatusCode());
        addDateValue(valueRow, valueIdx++, sdto.getStatusDate());
        
        Cell c = addStringValue(valueRow, valueIdx++, getConsolidatedWarningMessage(sdto));
        c.setCellStyle(wCellStyle);
        
        c = addStringValue(valueRow, valueIdx++, getConsolidatedErrorMessage(sdto));
        c.setCellStyle(wCellStyle);
        
        addStringValue(valueRow, valueIdx++, sdto.getReason());
        addStringValue(valueRow, valueIdx++, sdto.getId().toString());
        addDateValue(valueRow, valueIdx++, sdto.getCreatedOn());
        addDateValue(valueRow, valueIdx++, sdto.getUpdatedOn());
        addStringValue(valueRow, valueIdx++, sdto.getCreatedBy());
        addStringValue(valueRow, valueIdx++, sdto.getUpdatedBy());
        //
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.CTEP_ID));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.DCP_ID));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.SP_ID));
        addStringValue(valueRow, valueIdx++, ti.getColValue(TrialAndSiteInfo.PROC_STS));
        addDateValue(valueRow, valueIdx++,
                ti.getColValue(TrialAndSiteInfo.PROC_STS_DT));

    }

    private String getConsolidatedWarningMessage(StatusDto sdto) {
        StringBuffer sb = new StringBuffer();
        if(sdto.hasErrorOfType(ErrorType.WARNING)) {
            sb.append("WARNING(S) : ")
                    .append(sdto.getConsolidatedWarningMessage());
        }
        return sb.toString();
    }
    
    private String getConsolidatedErrorMessage(StatusDto sdto) {
        StringBuffer sb = new StringBuffer();
        if(sdto.hasErrorOfType(ErrorType.ERROR)) {
            sb.append("ERROR(S) : ")
                    .append(sdto.getConsolidatedErrorMessage());
        }
        return sb.toString();
    }

    /**
     * @param valueRow
     * @param s
     * @param nCI_ID
     */
    private Cell addStringValue(Row valueRow, short s, Object value) {
        Cell c = valueRow.createCell(s);
        if(value !=null) {
            c.setCellValue((String) value);
        }
        c.setCellStyle(vCellStyle);
        return c;
    }

    /**
     * @param valueRow
     * @param s
     * @param nCI_ID
     */
    private Cell addDateValue(Row valueRow, short s, Object value) {
        Cell c = valueRow.createCell(s);
        c.setCellStyle(dtCellStyle);
        if(value !=null) {
            c.setCellValue((Date) value);
        }
        return c;
    }

    private Font getHeaderFont() {
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setFontName("Courier New");
        headerFont.setItalic(false);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        return headerFont;
    }

    private CellStyle getHeaderStyle() {
        CellStyle hdrStyle = workbook.createCellStyle();
        hdrStyle.setFont(getHeaderFont());
        hdrStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT
                .getIndex());
        hdrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        hdrStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        hdrStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        hdrStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        hdrStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        hdrStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        return hdrStyle;
    }

    private CellStyle getStatusHeaderStyle() {
        CellStyle statsHdrStyle = workbook.createCellStyle();
        statsHdrStyle.setFont(getHeaderFont());
        statsHdrStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
        statsHdrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        statsHdrStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        statsHdrStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        statsHdrStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        statsHdrStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        statsHdrStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        return statsHdrStyle;
    }
    
    private CellStyle getDateCellStyle() {
        CellStyle dtCellStyle = workbook.createCellStyle();
        dtCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        return dtCellStyle;
    }
    
    private CellStyle getWrapCellStyle() {
        CellStyle wCellStyle = workbook.createCellStyle();
        wCellStyle.setWrapText(true);
        wCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        wCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        return wCellStyle;
    }
    
    private CellStyle getValueCellStyle() {
        CellStyle vCellStyle = workbook.createCellStyle();
        vCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        return vCellStyle;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//      String dbUrl = "jdbc:postgresql://localhost:5432/dev_pa";
//      String dbUser = "padev";
//      String dbPasswd = "pa3076dev";
        System.out.println("USAGE: gov.nih.nci.pa.external.TrialPSStatusReport <db_url> <db_username> <db_password>");
        if(args.length !=3 ) {
            System.out.println("Error: All 3 arguments are required.");
        }
        String dbUrl = args[0];
        String dbUser = args[1];
        String dbPasswd = args[2];
        TrialPSStatusReport tsr = new TrialPSStatusReport(".", dbUrl, dbUser,
                dbPasswd);
        tsr.fetchTrialStatusInfo();
        tsr.writeToFile();
    }
    
    
}
