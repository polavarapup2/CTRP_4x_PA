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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author vinodh
 * 
 */
public class TrialStatusReport {

    private static final Logger LOG = Logger.getLogger(TrialStatusReport.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static String STATUS = "study_status";
    public static String STATUS_DT = "study_status_date";
    public static String COMMENTS = "comments_text";
    public static String ADDL_COMMENTS = "addl_comments";
    public static String SS_REC_ID = "study_status_record_identifier";
    public static String ADD_ON = "added_on";
    public static String ADD_BY = "added_by_name";
    public static String UPD_ON = "last_updated_on";
    public static String UPD_BY = "last_updated_by_name";

    private String dbUrl;
    private String dbUser;
    private String dbPasswd;
    private String outputDir;

    private Map<String, TrialAndSiteInfo> trialInfoMap = new LinkedHashMap<String, TrialAndSiteInfo>();
    private Map<String, List<StatusDto>> trialStatusDtosMap = new LinkedHashMap<String, List<StatusDto>>();
    
    private Set<String> fltrByNciIds = new HashSet<String>();

    private static String TRIAL_SQL = "select ds.nci_id, ds.nct_id, category as trial_type, ds.lead_org_org_family as lead_org_family, "
            + " exists(select nci_id from dw_study_record_owner where name ='CTEPRSS RSS'and nci_id=ds.nci_id) as is_rss_trial, "
            + "ds.lead_org, ds.submitter_organization as submitting_org, ds.submitter_name, ds.summary_4_funding_category, "
            + "ds.ctep_id, ds.dcp_id, ds.internal_system_id as study_protocol_identifier, ds.processing_status as study_processing_status, "
            + " ds.processing_status_date as study_processing_status_date, " 
            + "dsos.status as study_status, dsos.status_date as study_status_date, dsos.why_study_stopped as comments_text, "
            + "dsos.addl_comments," 
            + "dsos.internal_system_id as study_status_record_identifier,"
            + "dsos.date_created as added_on, dsos.date_last_updated as last_updated_on,"
            + "dsos.user_created as added_by_name, dsos.user_last_updated as last_updated_by_name "
            + " from dw_study ds join dw_study_overall_status dsos on ds.nci_id=dsos.nci_id "
            + " where ds.processing_status not in ('Rejected', 'Submission Terminated') "
            + "FLTR_BY_NCI_IDS"
            + " order by ds.nci_id, dsos.status_date, dsos.internal_system_id";

    BaseStatusTransitionService service = new BaseStatusTransitionService();

    Workbook workbook = null;
    Sheet invalidTSTrxSheet = null;

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
    public TrialStatusReport(String outputDir, String dbUrl, String dbUser,
            String dbPasswd) throws Exception {
        this(outputDir, dbUrl, dbUser, dbPasswd, null, null);
    }
    
    /**
     * @param outputDir
     * @param dbUrl
     * @param dbUser
     * @param dbPasswd
     * 
     * @throws Exception
     *             - if there are any
     */
    public TrialStatusReport(String outputDir, String dbUrl, String dbUser,
            String dbPasswd, Workbook workbook, Sheet sheet) throws Exception {
        super();
        this.outputDir = outputDir;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPasswd = dbPasswd;

        service.getStatusRules();
        
        if(workbook == null) {
            this.workbook = new XSSFWorkbook();
        } else {
            this.workbook = workbook;
        }
        
        if(sheet == null) {
            this.invalidTSTrxSheet = this.workbook
                    .createSheet("InvalidTrialStatusTransitions");
          //set col widths
          invalidTSTrxSheet.setDefaultColumnWidth(3 * invalidTSTrxSheet.getDefaultColumnWidth());
        } else {
            this.invalidTSTrxSheet = sheet;
        }
        
        hdrStyle = getHeaderStyle();
        statusHdrStyle = getStatusHeaderStyle();
        dtCellStyle = getDateCellStyle();
        wCellStyle = getWrapCellStyle();
        vCellStyle = getValueCellStyle();
    }

    /**
     * @return the fltrByNciIds
     */
    public Set<String> getFltrByNciIds() {
        return fltrByNciIds;
    }

    /**
     * @param fltrByNciIds the fltrByNciIds to set
     */
    public void setFltrByNciIds(Set<String> fltrByNciIds) {
        this.fltrByNciIds = fltrByNciIds;
        String fltByNciIds = " and 1=1 ";
        
        if(this.fltrByNciIds != null && !this.fltrByNciIds.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            sb.append(" and ds.nci_id in (");
            for (String nciId : fltrByNciIds) {
                sb.append("'").append(nciId).append("',");
            }
            sb.append("'1') ");
            fltByNciIds = sb.toString();
        } 
        TRIAL_SQL = TRIAL_SQL.replace("FLTR_BY_NCI_IDS", fltByNciIds);
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
                ResultSet rs = stmt.executeQuery(TRIAL_SQL);) {
            if (rs == null) {
                throw new Exception("ResultSet is null");
            }
            String prevNciId = "";
            String currNciId = null;

            TrialAndSiteInfo trialInfo = null;
            StatusDto statusDto = null;

            List<StatusDto> currTrialStatusDtos = null;

            while (rs.next()) {
                currNciId = rs.getString(TrialAndSiteInfo.NCI_ID);
                if (!StringUtils.equals(prevNciId, currNciId)) {
                    runStatusTransitionsValidation(trialInfo,
                            currTrialStatusDtos);
                    prevNciId = currNciId;
                    currTrialStatusDtos = new ArrayList<StatusDto>();
                    trialInfo = new TrialAndSiteInfo(false);
                    trialInfo.map(rs);

                    statusDto = new StatusDto();
                    mapStatusDto(statusDto, rs);

                    trialInfoMap.put(currNciId, trialInfo);
                    trialStatusDtosMap.put(currNciId, currTrialStatusDtos);
                } else {
                    statusDto = new StatusDto();
                    mapStatusDto(statusDto, rs);
                }
                trialStatusDtosMap.get(currNciId).add(statusDto);
            }
            runStatusTransitionsValidation(trialInfo, currTrialStatusDtos);

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * @param trialInfo
     * @param currTrialStatusDtos
     * @throws PAException
     */
    private void runStatusTransitionsValidation(TrialAndSiteInfo trialInfo,
            List<StatusDto> currTrialStatusDtos) throws PAException {
        if (trialInfo != null && currTrialStatusDtos != null
                && !currTrialStatusDtos.isEmpty()) {
            service.validateStatusHistory(
                    AppName.REGISTRATION,
                    TrialType.valueOf(trialInfo
                            .getColValue(TrialAndSiteInfo.TRIAL_TYPE)
                            .toString().toUpperCase()),
                    TransitionFor.TRIAL_STATUS, currTrialStatusDtos);
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
        statusDto.setComments(rs.getString(ADDL_COMMENTS));
        statusDto.setCreatedOn(rs.getDate(ADD_ON));
        statusDto.setCreatedBy(rs.getString(ADD_BY));
        statusDto.setUpdatedOn(rs.getDate(UPD_ON));
        statusDto.setUpdatedBy(rs.getString(UPD_BY));
    }

    public void writeToWorkbook() {
        try  {
            addHeaders();
            Set<String> nciIds = trialInfoMap.keySet();
            for (String nciId : nciIds) {
                writeRow(nciId);
            }
            
        } catch (Exception e) {
            LOG.error("Error writing to excel workbook: " + e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void writeToFile() {
        try (FileOutputStream fout = new FileOutputStream(new File(outputDir
                + "/InvalidTrialStatusTransitions-" + SDF.format(new Date())
                + ".xlsx"));) {
            
            workbook.write(fout);
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
        invalidTSTrxSheet.createRow(hdrRowIndex-1);
        // hdrRow
        Row hdrRow = invalidTSTrxSheet.createRow(hdrRowIndex);
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
        //
        addStatusHeader(hdrRow, hdrIdx++, STATUS);
        addStatusHeader(hdrRow, hdrIdx++, STATUS_DT);
        addStatusHeader(hdrRow, hdrIdx++, "Status Transition Warnings");
        addStatusHeader(hdrRow, hdrIdx++, "Status Transition Errors");
        addStatusHeader(hdrRow, hdrIdx++, COMMENTS);
        addStatusHeader(hdrRow, hdrIdx++, ADDL_COMMENTS);
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

    private void writeRow(String nciId) throws IOException {
        List<StatusDto> sdtos = trialStatusDtosMap.get(nciId);
        boolean hasErrors = false;
        for (StatusDto statusDto : sdtos) {
            if (statusDto.hasErrorOfType(ErrorType.ERROR)) {
                hasErrors = true;
                break;
            }
        }
        if ((getFltrByNciIds() == null || getFltrByNciIds().isEmpty()) && !hasErrors)
            return;
        
        for (int i = 0; i < sdtos.size(); i++) {
            writeRow(nciId, i);
        }
    }

    /**
     * @return Row String
     * @throws IOException
     */
    private void writeRow(String nciId, int sIndex) throws IOException {
        TrialAndSiteInfo ti = trialInfoMap.get(nciId);
        StatusDto sdto = trialStatusDtosMap.get(nciId).get(sIndex);

        rowIndex++;
        Row valueRow = invalidTSTrxSheet.createRow(rowIndex);
        valueRow.setHeightInPoints(4 * invalidTSTrxSheet.getDefaultRowHeightInPoints());
        
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
        //
        addStringValue(valueRow, valueIdx++, sdto.getStatusCode());
        addDateValue(valueRow, valueIdx++, sdto.getStatusDate());
        
        Cell c = addStringValue(valueRow, valueIdx++, getConsolidatedWarningMessage(sdto));
        c.setCellStyle(wCellStyle);
        
        c = addStringValue(valueRow, valueIdx++, getConsolidatedErrorMessage(sdto));
        c.setCellStyle(wCellStyle);
        
        addStringValue(valueRow, valueIdx++, sdto.getReason());
        addStringValue(valueRow, valueIdx++, sdto.getComments());
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
//        String dbUrl = "jdbc:postgresql://localhost:5432/dev_pa";
//        String dbUser = "padev";
//        String dbPasswd = "pa3076dev";
        System.out.println("USAGE: gov.nih.nci.pa.external.TrialStatusReport <db_url> <db_username> <db_password>");
        if(args.length !=3 ) {
            System.out.println("Error: All 3 arguments are required.");
        }
        String dbUrl = args[0];
        String dbUser = args[1];
        String dbPasswd = args[2];
        TrialStatusReport tsr = new TrialStatusReport(".", dbUrl, dbUser,
                dbPasswd);
        Set<String> nciIDs = new HashSet<String>();
//        nciIDs.add("NCI-2009-00107");
        tsr.setFltrByNciIds(nciIDs);
        tsr.fetchTrialStatusInfo();
        tsr.writeToWorkbook();
        tsr.writeToFile();
    }
    
    
}
