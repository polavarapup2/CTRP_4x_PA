/**
 * 
 */
package gov.nih.nci.pa.external;

import gov.nih.nci.pa.service.status.BaseStatusTransitionService;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.ErrorType;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author vinodh
 *
 */
public class TrialStatusReportCSV {
    
    private static final Logger LOG = Logger.getLogger(TrialStatusReport.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static final String NEWLINE = System.getProperty("line.separator");
    
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
    
    private static String TRIAL_SQL = 
            "select ds.nci_id, ds.nct_id, category as trial_type, ds.lead_org_org_family as lead_org_family, "
            + "ds.lead_org, ds.submitter_organization as submitting_org, ds.submitter_name, ds.summary_4_funding_category, "
            + "ds.ctep_id, ds.dcp_id, ds.internal_system_id as study_protocol_identifier, ds.processing_status as study_processing_status, "
            + "now() as study_processing_status_date, "
            /* + " ds.processing_status_date as study_processing_status_date," */
            + "dsos.status as study_status, dsos.status_date as study_status_date, dsos.why_study_stopped as comments_text, "
            + "'addl comments' as addl_comments,"
            /* + "dsos.addl_comments," */
            + "dsos.internal_system_id as study_status_record_identifier,"
            + "dsos.date_created as added_on, dsos.date_last_updated as last_updated_on,"
            + "dsos.user_created as added_by_name, dsos.user_last_updated as last_updated_by_name "
            + " from dw_study ds join dw_study_overall_status dsos on ds.nci_id=dsos.nci_id "
            + " where ds.processing_status not in ('Rejected', 'Submission Terminated') "
            + " order by ds.nci_id, dsos.status_date, dsos.internal_system_id";
    
    BaseStatusTransitionService service = new BaseStatusTransitionService();
    
    
    /**
     * @param outputDir
     * @param dbUrl
     * @param dbUser
     * @param dbPasswd
     * 
     * @throws Exception - if there are any
     */
    public TrialStatusReportCSV(String outputDir, String dbUrl, String dbUser, String dbPasswd) throws Exception {
        super();
        this.outputDir = outputDir;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPasswd = dbPasswd;
        
        service.getStatusRules();
    }

    private Connection connect() {
        Connection postGresConn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Couldn't find Db driver class." + cnfe.getMessage());
            System.exit(1);
        }
        try {
            postGresConn = DriverManager.getConnection(dbUrl, dbUser, dbPasswd);
        } catch (SQLException se) {
            System.out.println("Couldn't connect to database." + se.getMessage());
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
        try (
           Connection postGresConn = connect();
           Statement stmt = postGresConn.createStatement();
           ResultSet rs = stmt.executeQuery(TRIAL_SQL);
           )
        {
            if (rs == null) {
                throw new Exception("ResultSet is null");
            }
            String prevNciId = "";
            String currNciId = null;
            
            TrialAndSiteInfo trialInfo = null;
            StatusDto statusDto = null;
            
            List<StatusDto> currTrialStatusDtos = null;
//            int rowNum = 0;
            
            while(rs.next()) {
//                rowNum ++;
                currNciId = rs.getString(TrialAndSiteInfo.NCI_ID);
//                Thread.sleep(1000);
//                System.out.println(rowNum + " : " + currNciId );
                if(!StringUtils.equals(prevNciId, currNciId)) {
                    if(trialInfo != null && currTrialStatusDtos != null && !currTrialStatusDtos.isEmpty() ) {
                        service.validateStatusHistory(
                                AppName.REGISTRATION, 
                                TrialType.valueOf(trialInfo.getColValue(TrialAndSiteInfo.TRIAL_TYPE).toString().toUpperCase()), 
                                TransitionFor.TRIAL_STATUS, 
                                currTrialStatusDtos);
                    }
                    
                    trialInfo = new TrialAndSiteInfo(false);
                    trialInfo.map(rs);
                    
                    statusDto = new StatusDto();
                    mapStatusDto(statusDto, rs);
                    currTrialStatusDtos = new ArrayList<StatusDto>();
                    
                    trialInfoMap.put(currNciId, trialInfo);
                    trialStatusDtosMap.put(currNciId, currTrialStatusDtos);
                } else {
                    statusDto = new StatusDto();
                    mapStatusDto(statusDto, rs);
                }
                trialStatusDtosMap.get(currNciId).add(statusDto);
            }
            
        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }
        
    }

    /**
     * @param statusDto
     * @param rs
     */
    private void mapStatusDto(StatusDto statusDto, ResultSet rs) throws Exception {
        if (rs == null) {
            throw new Exception("Error encountered: ResultSet is Null");
        }
        
        if (statusDto == null) {
            throw new Exception("Error encountered: Status record holder is Null");
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
    
    public void writeToCSV() {
        try (   
                FileWriter fw = new FileWriter(new File(outputDir + "/InvalidTrialStatusTransitions-" + SDF.format(new Date()) + ".csv"));
             )
        {
            fw.write(getColumnHeaders());
            Set<String> nciIds = trialInfoMap.keySet();
            fw.flush();
            for (String nciId : nciIds) {
                writeRow(fw, nciId);
                fw.flush();
            }
        }catch(Exception e) {
            LOG.error("Error writing to output file: " + e.getMessage(), e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @return Column Headers String
     */
    private String getColumnHeaders() {
       StringBuffer sb = new StringBuffer();
       append(sb, TrialAndSiteInfo.NCI_ID);
       append(sb, TrialAndSiteInfo.NCT_ID);
       append(sb, TrialAndSiteInfo.TRIAL_TYPE);
       append(sb, TrialAndSiteInfo.LEAD_ORG_FAM);
       append(sb, TrialAndSiteInfo.LEAD_ORG);
       append(sb, TrialAndSiteInfo.SUB_ORG);
       append(sb, TrialAndSiteInfo.SUB_NM);
       append(sb, TrialAndSiteInfo.SUM_4_FUND_CAT);
       //
       append(sb, STATUS);
       append(sb, STATUS_DT);
       append(sb, "Status Transition Errors/Warnings");
       append(sb, COMMENTS);
       append(sb, ADDL_COMMENTS);
       append(sb, SS_REC_ID);
       append(sb, ADD_ON);
       append(sb, UPD_ON);
       append(sb, ADD_BY);
       append(sb, UPD_BY);
       //
       append(sb, TrialAndSiteInfo.CTEP_ID);
       append(sb, TrialAndSiteInfo.DCP_ID);
       append(sb, TrialAndSiteInfo.SP_ID);
       append(sb, TrialAndSiteInfo.PROC_STS);
       append(sb, TrialAndSiteInfo.PROC_STS_DT);
       
       sb.append(NEWLINE);
       return sb.toString();
    }
    
    private void writeRow(FileWriter fw, String nciId) throws IOException {
        List<StatusDto> sdtos = trialStatusDtosMap.get(nciId);
        boolean hasErrors = false;
        for (StatusDto statusDto : sdtos) {
            if(statusDto.hasErrorOfType(ErrorType.ERROR)) {
                hasErrors = true;
                break;
            }
        }
        if(!hasErrors) return;
        for (int i = 0; i < sdtos.size(); i++) {
            writeRow(fw, nciId, i);
        }
    }
    
    /**
     * @return Row String
     * @throws IOException 
     */
    private void writeRow(FileWriter fw, String nciId, int sIndex) throws IOException {
        TrialAndSiteInfo ti = trialInfoMap.get(nciId);
        StatusDto sdto = trialStatusDtosMap.get(nciId).get(sIndex);
        
       StringBuffer sb = new StringBuffer();
       append(sb, ti.getColValue(TrialAndSiteInfo.NCI_ID));
       append(sb, ti.getColValue(TrialAndSiteInfo.NCT_ID));
       append(sb, ti.getColValue(TrialAndSiteInfo.TRIAL_TYPE));
       append(sb, ti.getColValue(TrialAndSiteInfo.LEAD_ORG_FAM));
       append(sb, ti.getColValue(TrialAndSiteInfo.LEAD_ORG));
       append(sb, ti.getColValue(TrialAndSiteInfo.SUB_ORG));
       append(sb, ti.getColValue(TrialAndSiteInfo.SUB_NM));
       append(sb, ti.getColValue(TrialAndSiteInfo.SUM_4_FUND_CAT));
       //
       append(sb, sdto.getStatusCode());
       appendDate(sb, sdto.getStatusDate());
       appendConsolidatedMessage(sb, sdto);
       append(sb, sdto.getReason());
       append(sb, sdto.getComments());
       append(sb, sdto.getId());
       appendDate(sb, sdto.getCreatedOn());
       appendDate(sb, sdto.getUpdatedOn());
       append(sb, sdto.getCreatedBy());
       append(sb, sdto.getUpdatedBy());
       //
       append(sb, ti.getColValue(TrialAndSiteInfo.CTEP_ID));
       append(sb, ti.getColValue(TrialAndSiteInfo.DCP_ID));
       append(sb, ti.getColValue(TrialAndSiteInfo.SP_ID));
       append(sb, ti.getColValue(TrialAndSiteInfo.PROC_STS));
       appendDate(sb, (Date)ti.getColValue(TrialAndSiteInfo.PROC_STS_DT), true);
       
       sb.append(NEWLINE);
       
       fw.write(sb.toString());
    }
    
    private void appendConsolidatedMessage(StringBuffer sb, StatusDto sdto) {
        sb.append("\"");
        sb.append("WARNING(S)").append(":").append(sdto.getConsolidatedWarningMessage());
        sb.append(" ");
        sb.append("ERROR(S)").append(":").append(sdto.getConsolidatedErrorMessage());
        sb.append("\",");
    }
    
    private void append(StringBuffer sb, Object value) {
        append(sb, value, false);
    }
    private void append(StringBuffer sb, Object value, boolean isLastCol) {
        if(value == null) {
           sb.append("\"\"");
        } else {
            sb.append("\"").append(value).append("\"");
        }
        if(!isLastCol) {
            sb.append(",");
        }
    }
    
    private void appendDate(StringBuffer sb, Date value) {
        appendDate(sb, value, false);
    }
    
    private void appendDate(StringBuffer sb, Date value, boolean isLastCol) {
        if(value == null) {
           sb.append("\"\",");
        } else {
            sb.append("\"").append(SDF.format(value)).append("\"");
        }
        if(!isLastCol) {
            sb.append(",");
        }
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
         String dbUrl = "jdbc:postgresql://localhost:5432/dev_pa";
         String dbUser = "padev";
         String dbPasswd = "pa3076dev";
        TrialStatusReportCSV tsr = new TrialStatusReportCSV(".", dbUrl, dbUser, dbPasswd);
        tsr.fetchTrialStatusInfo();
        tsr.writeToCSV();
        
    }

}
