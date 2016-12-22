package gov.nih.nci.utilities.loganalyzer;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LogAnalyzer {

    private final int CONTEXT_LENGTH = 1;
    private final String ERROR_QUALIFIER = " ERROR ";
    private final String EXCEPTION_QUALIFIER = "at ";

    private File reportFile;
    private String[] exclusions;
    private LinkedHashMap<StringBuilder, StringBuilder> lruMap;
    private File[] files;

    private File currentLogFile;
    private int currentLineNumber;
    private int currentLogLineNumber;

    private boolean addContextInReport = false;


    public LogAnalyzer() {

        lruMap = new LinkedHashMap<StringBuilder, StringBuilder>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > CONTEXT_LENGTH;
            }
        };

        exclusions = new String[0];


    }


    private static void showUsage(final String why) {
        System.out.println(why);
        System.out.println("java gov.nih.nci.utilities.loganalyzer.LogAnalyzer "
                + "<log-folder> <filter-pattern> <report-file> <exclusions> \n"
                + "  - log-folder      : required; must point to the folder where the logs are \n"
                + "  - report-file     : required; must point to the file where reports to be written \n"
                + "  - filter-pattern  : required; must specify the filter filter pattern \n"
                + "  - exclusions      : optional; must be a semicolon separated list of exception names to excluded"
                + "Example : "
                + " java gov.nih.nci.utilities.loganalyzer.LogAnalyzer "
                + "/palogs/ "
                + "/usr/pa_log_report.txt "
                + "server*2015-10* "
                + "stderr;CaGridFormAuthenticatorValve -Dcontext=yes");

        System.exit(1);
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            showUsage("Incorrect input");
            return;
        }
        new LogAnalyzer().processLogFiles(args);

    }

    public final void processLogFiles(String[] params) throws IOException {

        //Add context ?
        addContextInReport = System.getenv("context") != null &&
                "yes".equals(System.getenv("context"));

        //parse the arguments
        String inLogFolder = params[0].trim();
        String outReportFile = params[1].trim();
        String filterPattern = params[2].trim();

        final String escapedFilterPattern = filterPattern.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");

        //semicolon is the separator so join and then split
        StringBuilder stopWords = new StringBuilder("");
        if (params.length > 3) {
            for (int i = 3; i < params.length; i++) {
                stopWords.append(params[i]);
            }
        }

        if (stopWords.length() > 0) {
            exclusions = stopWords.toString().split(";");
        }
        
        System.out.println("Exclusions: "+Arrays.asList(exclusions));

        //verify that the folder exists
        File logFolder = new File(inLogFolder);
        if (!logFolder.exists()) {
            showUsage("Input log folder do not exist");
        }

        //generate the reprot file
        reportFile = new File(outReportFile);
        if (reportFile.exists()) {
            reportFile.delete();
        }
        String disclaimer = "Report Generated on :" + new Date().toString() + "\n";
        Files.write(reportFile.toPath(), disclaimer.getBytes(), StandardOpenOption.CREATE);

        //sort files on modified date
        files = logFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return false;
                }
                String name = f.getName();
                return name.matches(escapedFilterPattern);
            }
        });
        Arrays.sort(files, new Comparator<File>() {
            public int compare(final File o1, final File o2) {
                return -Long.compare(o1.lastModified(), o2.lastModified());
            }
        });

        //process each file
        for (File file : files) {
            currentLogFile = file;
            System.out.println(" Processing : " + currentLogFile.getName());
            processLogFile();
        }

        System.out.println("Report available in : " + reportFile.getAbsolutePath());

    }


    private void processLogFile() throws IOException {
        currentLineNumber = 0;
        currentLogLineNumber = 0;
        int n = 0;

        Files.write(reportFile.toPath(),
                ("\n********************* " + currentLogFile.getName()+"\n")
                        .getBytes(), StandardOpenOption.APPEND);

        BufferedReader reader =
                Files.newBufferedReader(currentLogFile.toPath(),
                        Charset.defaultCharset());

        StringBuilder logLine = new StringBuilder();
        StringBuilder previousLogLine = null;

        //keep reading the line and check for valid exceptions (but on the last but one read)
        String line = null;
        while ((line = reader.readLine()) != null) {
            currentLineNumber++;
            if (isLogLine(line)) {

                previousLogLine = logLine;
                // previously read line reportable ?
                if (isReportableException(previousLogLine.toString())) {
                    appendToReport();
                }

                logLine = new StringBuilder(line);
                lruMap.put(logLine, logLine);


                n = currentLineNumber;
                currentLogLineNumber = n;

            } else {
                logLine.append("\n").append(line);
            }


        }

        //once again - check for last line read
        if (isReportableException(logLine.toString())) {
            appendToReport();
        }

        reader.close();

    }

    //make sure that the line in fact is a line added by log4j
    private boolean isLogLine(String line) {
        return line.contains(" WARN ") ||
                line.contains(" INFO ") ||
                line.contains(" ERROR ") ||
                line.contains(" FATAL ") ||
                line.contains(" TRACE ") ||
                line.contains("DEBUG");
    }

    /**
     * @param line - The line to be appended
     * @return - true if it is a valid exception
     * @throws IOException
     */
    private boolean isReportableException(final String line) throws IOException {
        //has ERROR
        if (!line.contains(ERROR_QUALIFIER)) {
            return false;
        }

        //has more than one at
        int at = line.indexOf(EXCEPTION_QUALIFIER);
        if (at < 1) {
            return false;
        }

        at = line.indexOf(EXCEPTION_QUALIFIER, at);
        if (at < 1) {
            return false;
        }

        for (String exclusion : exclusions) {
            if (line.contains(exclusion)) {
                return false;
            }
        }

        return true;

    }

    private void appendToReport() throws IOException {
        List<String> contextLines = new ArrayList<String>();
        if (addContextInReport) {
            contextLines.add(String.format("Line : %s File : %s", currentLogLineNumber,
                    currentLogFile.getName()));
        }
        for (StringBuilder sb : lruMap.values()) {
            contextLines.add(sb.toString());
        }
        Files.write(reportFile.toPath(), contextLines,
                Charset.defaultCharset(), StandardOpenOption.APPEND);
    }


}
