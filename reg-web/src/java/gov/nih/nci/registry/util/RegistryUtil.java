/**
 *
 */
package gov.nih.nci.registry.util;


import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.FileDataSource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * Utility Class for Registry.
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegistryUtil {
    private static final String VALIDATION_EXCEPTION_STRING = "Validation Exception";
    private static final String PA_EXCEPTION_STRING = "gov.nih.nci.pa.service.PAException:";
    private static final Logger LOG = Logger.getLogger(RegistryUtil.class);
    private static final String[] VALID_DATE_FORMATS = {"MM/dd/yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "yyyy-MM-dd"};    
    private static final String FROMADDRESS = "fromaddress";
    private static final String TABLESTART = "<table><tr><td>";
    private static final String TABLEEND = "</td></tr></table>";
    /**
     * check if the email address is valid.
     * @param emailAddress emailAddress
     * @return boolean
     */
    public  static boolean isValidEmailAddress(String emailAddress)  {
        boolean isvalidEmailAddr = false;
        Pattern email = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");

        Matcher fit = email.matcher(emailAddress);
        if (fit.matches()) {
            isvalidEmailAddr = true;
        }
        return isvalidEmailAddr;
    }

    /**
     * check if the date is of valid format.
     * @param dateString dateString
     * @return boolean
     */
    public static boolean isValidDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        if (dateString.trim().length() != dateFormat.toPattern().length())  {
            return false;
        }
        dateFormat.setLenient(false);
        try {
            //parse the date
            dateFormat.parse(dateString.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
   }
   /**
    * validates the phone number format.
    * @param phoneNumber phoneNumber
    * @return boolean
    */
   public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean isValidPhoneNumber = false;
        if (phoneNumber != null && phoneNumber.trim().length() >= Constants.MIN_PHONE_NUM_LENGTH) {
            Pattern numberPattern = Pattern.compile("^([\\w\\s\\-\\.\\+\\(\\)])*$");
            Matcher fit = numberPattern.matcher(phoneNumber);
            if (fit.matches()) {
                isValidPhoneNumber = true;
            }
        }
        return isValidPhoneNumber;
   }

   /**
    * Generate batch upload report email.
    *
    * @param action the action
    * @param userName the user name
    * @param successCount the success count
    * @param failedCount the failed count
    * @param totalCount the total count
    * @param attachFileName the attach file name
    * @param errorMessage the error message
    * @param createList createList
    * @param amendList amendList
    */
    // CHECKSTYLE:OFF More than 7 Parameters
   @SuppressWarnings({ "PMD.ExcessiveParameterList", "PMD.ExcessiveMethodLength" })
    public static void generateMail(String action, String userName, String successCount, String failedCount,
            String totalCount, String attachFileName, String errorMessage, 
            List<String> createList, List<String> amendList) {
       
        try {
            StringBuffer submissionMailBody = new StringBuffer();
            Calendar calendar = new GregorianCalendar();
            Date date = calendar.getTime();
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            // get the values of email's subject and email body from the database
            String emailSubject = PaRegistry.getLookUpTableService().getPropertyValue("trial.batchUpload.subject");
            LOG.debug("emailSubject is: " + emailSubject);

            // add the mail header
            String submissionMailBodyHeader = PaRegistry.getLookUpTableService()
                .getPropertyValue("trial.batchUpload.bodyHeader");

            RegistryUser registryUser = PaRegistry.getRegistryUserService().getUser(userName);

            submissionMailBodyHeader = submissionMailBodyHeader.replace("${SubmitterName}", registryUser.getFirstName()
                    + " " + registryUser.getLastName());
            submissionMailBodyHeader = submissionMailBodyHeader.replace("${CurrentDate}", format.format(date));
            submissionMailBody.append(submissionMailBodyHeader);
            // append the body text for processed or error
            if (Constants.PROCESSED.equals(action)) {
                prepareProcessedMessage(successCount, failedCount, totalCount, 
                   submissionMailBody, createList, amendList);
            } else {
                prepareErrorMessage(errorMessage, submissionMailBody);
            }
            // append the footer
            String submissionMailBodyFooter = PaRegistry.getLookUpTableService()
                .getPropertyValue("trial.batchUpload.bodyFooter");
            submissionMailBody.append(submissionMailBodyFooter);

            String emailBody = submissionMailBody.toString();
            String emailTo = registryUser.getEmailAddress();
            if (!StringUtils.isEmpty(attachFileName)) {
                FileDataSource fds = new FileDataSource(attachFileName);
                File[] attachments = new File[1];
                attachments[0] = fds.getFile();
                String fromAddress = PaRegistry.getLookUpTableService().getPropertyValue(FROMADDRESS);
                // Send the batch upload report to the submitter
                PaRegistry.getMailManagerService().sendMailWithHtmlBodyAndAttachment(emailTo, fromAddress
                        , null, emailSubject, emailBody, attachments, false);
                 
              
            } else {
                // Send the batch upload Error to the submitter
                PaRegistry.getMailManagerService().sendMailWithHtmlBody(emailTo, emailSubject, emailBody);
              
            }

        } catch (PAException e) {
            LOG.error("Error occured while generating the batch upload email", e);
        }
    }  
    
    
    
    private static void prepareErrorMessage(String errorMessage, StringBuffer submissionMailBody) throws PAException {
        submissionMailBody.append("Error: ").append(errorMessage).append('\n');
        String submissionMailErrorBody = PaRegistry.getLookUpTableService()
            .getPropertyValue("trial.batchUpload.errorMsg");
        String currentReleaseNumber = PaRegistry.getLookUpTableService().getPropertyValue("current.release.no");
        submissionMailErrorBody = submissionMailErrorBody.replace("${ReleaseNumber}", currentReleaseNumber);
        submissionMailBody.append(submissionMailErrorBody);
    }
    
    /**
     * 
     * @param userName userName
     * @param warningMap warningMap
     * @param action action
     * @param successCount successCount
     * @param failedCount failedCount
     * @param totalCount totalCount
     * @param attachFileName attachFileName
     * @param errorMessage errorMessage
     */
 // CHECKSTYLE:OFF More than 7 Parameters
    @SuppressWarnings({ "PMD.ExcessiveParameterList", "PMD.ExcessiveMethodLength"})
    public static void sendEmail(String action, String userName, String successCount, String failedCount, 
            String totalCount, String attachFileName, String errorMessage, Map<String, String> warningMap) {
        int createCount = 0;
        int amendCount = 0;
        List<String> createTrialIDS = new ArrayList<String>();
        List<String> amendTrialIDS = new ArrayList<String>();
        if (!MapUtils.isEmpty(warningMap)) {
            Set<String> s = warningMap.keySet();
            Iterator<String> iter = s.iterator();
            while (iter.hasNext()) {
                String trialId = iter.next();
                String warning = warningMap.get(trialId); 
                if (StringUtils.equalsIgnoreCase("CreateWarning", warning)) {
                    createCount++;
                    createTrialIDS.add(trialId);
                } else if (StringUtils.equalsIgnoreCase("AmendWarning", warning)) {
                     amendCount++;
                     amendTrialIDS.add(trialId);
                }
            }
        } else {
            generateMail(action, userName, successCount, failedCount, 
                 totalCount, attachFileName, errorMessage, null, null);
        }
        if (createCount > 0 || amendCount > 0) {
            generateMail(action, userName, successCount, failedCount, totalCount, 
                  attachFileName, errorMessage, createTrialIDS, amendTrialIDS);
        }
    }
    
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })  
    private static void prepareProcessedMessage(String successCount, String failedCount, String totalCount, 
            StringBuffer submissionMailBody, List<String>  createList, List<String> amendList) throws PAException {
        String submissionMailBodyText = PaRegistry.getLookUpTableService().getPropertyValue("trial.batchUpload.body");
        submissionMailBodyText = submissionMailBodyText.replace("${totalCount}", totalCount);
        submissionMailBodyText = submissionMailBodyText.replace("${successCount}", successCount);
        submissionMailBodyText = submissionMailBodyText.replace("${failedCount}", failedCount);
        String submissionMailReportBody = null;
        submissionMailBody.append(submissionMailBodyText);
      if ((createList != null && !createList.isEmpty()) || (amendList != null && !amendList.isEmpty())) {
          String changeDate = PaRegistry.getLookUpTableService().getPropertyValue("delayed.posting.change.date");
          if (!createList.isEmpty() && !amendList.isEmpty()) {
              submissionMailReportBody = PaRegistry.getLookUpTableService()
                       .getPropertyValue("trial.batchUpload.reporBothtMsg");
              StringBuffer innerCreateTable = new StringBuffer();
              StringBuffer innerAmendTable = new StringBuffer();
                for (String trialID : createList) {
                    innerCreateTable.append(TABLESTART + trialID
                          + TABLEEND);
                }
                if (innerCreateTable.length() > 0) {
                  submissionMailReportBody = submissionMailReportBody.
                         replace("${createtableRows}", innerCreateTable.toString());
                }
                
                for (String trialID : amendList) {
                   innerAmendTable.append("TABLESTART" + trialID
                          + TABLEEND);
                }
                if (innerAmendTable.length() > 0) {
                  submissionMailReportBody = submissionMailReportBody.
                        replace("${amendtableRows}", innerAmendTable.toString());
                }
           } else if (!createList.isEmpty()) {
             submissionMailReportBody = PaRegistry.getLookUpTableService()
                   .getPropertyValue("trial.batchUpload.reportCreateMsg");
             StringBuffer innerTable = new StringBuffer();
             for (String trialID : createList) {
                 innerTable.append(TABLESTART + trialID
                         + TABLEEND);
             }
             if (innerTable.length() > 0) {
                 submissionMailReportBody = submissionMailReportBody.replace("${tableRows}", innerTable.toString());
             }
          } else if (!amendList.isEmpty()) {
           submissionMailReportBody = PaRegistry.getLookUpTableService()
                  .getPropertyValue("trial.batchUpload.reporAmendtMsg");
           StringBuffer innerTable = new StringBuffer();
           for (String trialID : createList) {
             innerTable.append(TABLESTART + trialID
                     + TABLEEND);
           }
           if (innerTable.length() > 0) {
             submissionMailReportBody = submissionMailReportBody.replace("${tableRows}", innerTable.toString());
           }
         }
        submissionMailReportBody = submissionMailReportBody.replace("${changeDate}", changeDate);
      } else {
           submissionMailReportBody = PaRegistry.getLookUpTableService()
            .getPropertyValue("trial.batchUpload.reportMsg");
        }
        submissionMailBody.append('\n').append(submissionMailReportBody);
    }

  /**
   * Get Registry user web dto.
   * @param loginName the login name.
   * @return registryUserWebDto the registry user web dto.
   */
  public static RegistryUserWebDTO getRegistryUserWebDto(String loginName) {
      RegistryUserWebDTO regUserWebDto = null;
      RegistryUser registryUser = null;
      User csmUser = null;
      try {
          registryUser = PaRegistry.getRegistryUserService().getUser(loginName);
          csmUser = CSMUserService.getInstance().getCSMUser(loginName);
          if (registryUser != null && csmUser != null) {
              regUserWebDto = new RegistryUserWebDTO(registryUser);
          }
      } catch (Exception ex) {
          LOG.error("Error getting the csm user for login name = " + loginName);
      }
      return regUserWebDto;
  }

  /**
   * @param e Exception used for the message
   * @return boolean if Failure Message was set or not
   */
  public static boolean setFailureMessage(Exception e) {
       if (e != null && e.getMessage() != null) {
           final String msg = e.getLocalizedMessage();
           String exceptionStr = !msg.startsWith(VALIDATION_EXCEPTION_STRING) ? msg
                    .substring(msg.indexOf(':') + 1) : msg;
           ServletActionContext.getRequest().setAttribute("failureMessage", removeExceptionFromErrMsg(exceptionStr));
           return true;
       }
       return false;
  }
  /**
   *
   * @param errMsg errMsg
   * @return errorMsg
   */
  public static String removeExceptionFromErrMsg(String errMsg) {
     String removePAException = StringUtils.remove(errMsg, PA_EXCEPTION_STRING);
     return  StringUtils.remove(removePAException, VALIDATION_EXCEPTION_STRING).trim();
  }
  
  /**
   * This method takes in a date string and checks if it is in one of the 4 formats allowed. 
   * MM/dd/yyyy, MM-dd-yyyy, yyyy/MM/dd, yyyy-MM-dd
   * @param s date string to be validated.
   * @return true or false
   */
  public static boolean isDateValid(String s) {
      SimpleDateFormat sdf = null;
      for (String eachDateFormat : VALID_DATE_FORMATS) {
          sdf = new SimpleDateFormat(eachDateFormat, Locale.US);
          sdf.setLenient(false);
          if (sdf.parse(s, new ParsePosition(0)) != null) {
              return true;
          }
      }
      return false;
  }

}
