package com.ctepdataload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProcessCtepData {
    
public void readAndProcessCtepData(String serverUrl, String userName, 
        String password, String inputFileName, boolean isAddException) throws Exception {
    
     
    FileInputStream file = new FileInputStream(new File(inputFileName));
    String outputFileName = "Result_"+inputFileName;
    File logFile = new File("dataLoad.log");
    File sqlFile = new File("updateContactDetails.sql");
    //delete log file if exists
    FileUtils.deleteQuietly(logFile);
    logFile.createNewFile();
    
    //delete sql file if exists
    FileUtils.deleteQuietly(sqlFile);
    sqlFile.createNewFile();
    
    String lineSeperator = System.getProperty("line.separator");

  
    //Get the workbook instance for XLS file 
    XSSFWorkbook workbook = new XSSFWorkbook(file);
     
    //Get first sheet from the workbook
    XSSFSheet sheet = workbook.getSheetAt(0);
     
    //Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();
    int rowIndex = 0;
    
    String url = serverUrl;
    StringBuffer logInformation = new StringBuffer();
  
    
    
   try {  
    

       FileUtils.writeStringToFile(sqlFile, "BEGIN;"+lineSeperator, true);   
       
    while(rowIterator.hasNext()) {
        
     StringBuffer rowInformation = new StringBuffer();  
     StringBuffer sqlQueryString = new StringBuffer();
        
        rowIndex++;
        Row row = rowIterator.next();
        
      //skip the first row
        if (rowIndex == 1 ) {
            
            XSSFFont defaultFont= workbook.createFont();
            defaultFont.setBold(true);

            XSSFCellStyle defaultStyle = workbook.createCellStyle();
            defaultStyle.setFont(defaultFont);   
            
            
            //create result header rowa
            Cell resultCell = row.createCell(11);
            resultCell.setCellValue("RESULT");
            resultCell.setCellStyle(defaultStyle);
      
            
            //create study site id created 
            Cell studySiteIdCreated = row.createCell(12);
            studySiteIdCreated.setCellValue("NEW SITE ID");
            studySiteIdCreated.setCellStyle(defaultStyle);

          //create failure message created 
            Cell failureMessageCell = row.createCell(13);
            failureMessageCell.setCellValue("FAILURE MESSAGE");
            failureMessageCell.setCellStyle(defaultStyle);
            continue;
        }
        
        rowInformation.append("Processing row "+rowIndex+" ");
        int cellIndexCount = 0;
        
        String studyCtepId =null;
        String localStudyIdentifier = null;
        String studySiteCtepId= null;
        String recruitmentStatus = null;
        String recruitmentStatusDate = null;
        String piCtepId = null;
        String updateType = null;
        String paSiteId = null;
        String contactType = null;
        String contactPhone = null;
        String contactEmail = null;
        
      //For each row, iterate through each columns
        Iterator<Cell> cellIterator = row.cellIterator();
        while(cellIterator.hasNext()) {
             
            cellIndexCount++;
            Cell cell = cellIterator.next();
            
            String value =getCellValue(cell); 
         
            switch(cellIndexCount) {
            
            case 1 : studyCtepId = value;
                    break;
                    
            case 2 : localStudyIdentifier = value;
            break;        
                    
            case 3 : studySiteCtepId = value;
            break;
            
            case 4 : recruitmentStatus = value;
            break;
    
            case 5 : recruitmentStatusDate = value;
            break;
    
            case 6 : piCtepId = value;
            break;
            case 7 : contactPhone = value;
            break;
            case 8 : contactEmail = value;
            break;
            
            case 9 : contactType = value;
            break;
    
            case 10 : updateType = value;
            break;
            
            case 11 : paSiteId = value;
            break;
    
            
            
          }
           
        }
        
        RestClient restClient = initRestClient(userName,password,isAddException);
        boolean skipContactInformation = false;
        //if update type = SITE_PUBLIC_CONTACT then skip this recode
        if(contactType!=null && !contactType.equalsIgnoreCase("PI_PUBLIC_CONTACT")) {
            skipContactInformation = true;
        }
        
        //send insert REST call
         if(updateType.equalsIgnoreCase("new")) {
            
            //send insert call
             System.out.println("Processing insert for trial id "+studyCtepId+" and site id "+studySiteCtepId);
            XMLGenerator xmlGenerator = new XMLGenerator();
           String xml = xmlGenerator.generateNewSiteXML(localStudyIdentifier, studySiteCtepId, 
                   recruitmentStatus, recruitmentStatusDate, piCtepId ,skipContactInformation);
           sendInsertCall(xml,url,studyCtepId,studySiteCtepId, row,restClient,skipContactInformation ,rowInformation,
                   sqlQueryString  ,contactPhone,contactEmail);
          
           
        }
         //send update REST call
        else {
            
            //in case of update if contact type is not PI_PUBLIC_CONTACT then skip update
            if(skipContactInformation) {
                continue;
            }
            
            
            XMLGenerator xmlGenerator = new XMLGenerator();
           
 
            String xml = xmlGenerator.generateUpdateSiteXML(localStudyIdentifier ,recruitmentStatus
                    , recruitmentStatusDate, piCtepId,skipContactInformation);
            System.out.println("Processing update for trial id "+studyCtepId+" and site id "+studySiteCtepId);
            
            sendUpdateCall(xml,url,studyCtepId,studySiteCtepId,row,restClient,skipContactInformation ,rowInformation,
                    sqlQueryString, contactPhone,contactEmail);
          
        }
         
         //log this information to file
         FileUtils.writeStringToFile(logFile, rowInformation.toString()+lineSeperator, true);   
         if(sqlQueryString.length() > 0) {
             FileUtils.writeStringToFile(sqlFile, sqlQueryString.toString()+lineSeperator, true);    
         }
            

       
      
       /* System.out.println ("studyCtepId = "+studyCtepId 
                +" localStudyIdentifier="+localStudyIdentifier
                +" studySiteCtepId="+studySiteCtepId
                +" recruitmentStatus="+recruitmentStatus
                +" recruitmentStatusDate="+recruitmentStatusDate
                +" piCtepId="+piCtepId
                +" updateType="+updateType
                +" paSiteId="+paSiteId
                ); */
         
        // System.out.println("The contactPhone here --->"+contactPhone +" the email here --->"+contactEmail);
    }
    
   } 
 
    
    finally {
        file.close();
    
        //write output file name
        FileOutputStream out = 
        new FileOutputStream(new File(outputFileName));
    workbook.write(out);
    out.close();
    FileUtils.writeStringToFile(sqlFile, "END;"+lineSeperator, true);   
    }
}


private void sendInsertCall(String xml, String url,
            String studyCtepId, String studySiteCtepId, Row row, RestClient restClient , boolean skipContactInformation,
            StringBuffer rowInformation,
            StringBuffer queryInformation,
            String contactPhone,
            String contactEmail)
            throws Exception {

        String resultString = null;
        String failureMessge = null;
        String siteId = null;
        
        CtrpResponse ctrpResponse = new CtrpResponse();
        try {

            ctrpResponse = restClient.addSite(xml, url, studyCtepId);

            if (ctrpResponse.isSucess()) {
                if (skipContactInformation){
                    resultString = "Success and Skipped contact information";    
                    queryInformation.append(generateContactUpdateQuery(contactPhone,
                            contactEmail,ctrpResponse.getSiteIdAdded()));                   
                } else {
                    resultString ="Success";
                }
                siteId = ctrpResponse.getSiteIdAdded();
            } else {
                resultString = "Failure";
                failureMessge = ctrpResponse.getFailureMessage();
            }

        } catch (Exception e) {
            // catch exception in order for other rows to continue processing
            resultString = "Failure";
            failureMessge = e.getMessage();

        }
        
        
        
        Cell resultCell = row.createCell(11);
        resultCell.setCellValue(resultString);
        rowInformation.append (" Result ="+resultString);
        
        
        resultCell = row.createCell(12);
        resultCell.setCellValue(siteId);

        rowInformation.append (" SiteId ="+siteId);
      //create failure message created 
        resultCell = row.createCell(13);
        resultCell.setCellValue(failureMessge);
        
        rowInformation.append (" FailureMessage ="+failureMessge);
        
        
         
}

private void sendUpdateCall(String xml, String url,
        String studyCtepId, String studySiteCtepId, Row row,RestClient restClient,
        boolean skipContactInformation,
         StringBuffer rowInformation,
         StringBuffer queryInformation,
         String contactPhone,
         String contactEmail)
        throws Exception {

    String resultString = null;
    String failureMessge = null;
  
    
    CtrpResponse ctrpResponse = new CtrpResponse();
    try {

        ctrpResponse = restClient.updateSite(xml, url, studyCtepId, studySiteCtepId);

        if (ctrpResponse.isSucess()) {
            if (skipContactInformation){
                resultString = "Success and Skipped contact information"; 
                queryInformation.append(generateContactUpdateQuery(contactPhone,
                        contactEmail,ctrpResponse.getSiteIdAdded()));    
            } else {
                resultString ="Success";
            }
            
        } else {
            resultString = "Failure";
            failureMessge = ctrpResponse.getFailureMessage();
        }

    } catch (Exception e) {
        // catch exception in order for other rows to continue processing\
        resultString = "Failure";
        failureMessge = e.getMessage();

    }

    //create result
    Cell resultCell = row.createCell(11);
    resultCell.setCellValue(resultString);
    rowInformation.append (" Result ="+resultString);
    
    resultCell = row.createCell(12);
    resultCell.setCellValue(ctrpResponse.getSiteIdAdded());
    rowInformation.append (" SiteId ="+ctrpResponse.getSiteIdAdded());

  //create failure message created 
    resultCell = row.createCell(13);
    resultCell.setCellValue(failureMessge);
    rowInformation.append (" FailureMessage ="+failureMessge);
    

    
}

private RestClient initRestClient(String userName , String password, boolean isAddException) throws Exception{
    RestClient restClient = new RestClient();
    restClient.setUserName(userName);
    restClient.setPassword(password);
    restClient.setAddException(isAddException);
    restClient.init();
    
    return restClient;
}

private String getCellValue(Cell cell) {
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

public static String convertDateToString(Date date, String format) {
    SimpleDateFormat dateFormatter = null;
    String formattedDate = null;

    if (date != null && format != null) {
        dateFormatter = new SimpleDateFormat(format, Locale.US);
        formattedDate = dateFormatter.format(date);
    }
    return formattedDate;

}

private String generateContactUpdateQuery(String contactPhone,String contactEmail, String siteId){
    StringBuffer resultString= new StringBuffer ();
    resultString.append("update study_site_contact set telephone='");
    if(contactPhone!=null && !contactPhone.equalsIgnoreCase("null")) {
        resultString.append(contactPhone);
    }
    else {
        resultString.append("");
    }
    resultString.append("',email='");
    if(contactEmail!=null & !contactEmail.equalsIgnoreCase("null")) {
        resultString.append(contactEmail);
    }
    else {
        resultString.append("");
    }
    
    resultString.append("' where study_site_identifier ='");
    resultString.append(siteId);
    resultString.append("' and role_code='PRIMARY_CONTACT' and status_code ='PENDING';");
    return resultString.toString();
}
  
public static void main(String[] args) throws Exception {
    ProcessCtepData processCtepData = new ProcessCtepData();
 
    ProperyValueReader properyValueReader = new ProperyValueReader();
    properyValueReader.initProperties();
    String serverUrl = properyValueReader.getServerUrl();
    String userName = properyValueReader.getUserName();
    String password =  properyValueReader.getPassword();
    String inputFileName = properyValueReader.getInputFileName();
    boolean isAddException = properyValueReader.isAddException();
    processCtepData.readAndProcessCtepData(serverUrl,userName,password,inputFileName,isAddException);
    
   
    System.out.println("Finished CTEP Data Load");
}

}
