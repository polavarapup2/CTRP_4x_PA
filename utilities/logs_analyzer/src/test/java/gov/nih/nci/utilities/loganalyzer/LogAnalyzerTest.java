package gov.nih.nci.utilities.loganalyzer;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

//NOTE :- This is not a junit test - done on purpose to make things bit simple
public class LogAnalyzerTest {

    public static void main(String args[]) throws Exception {
        LogAnalyzer analyzer = new LogAnalyzer();
        File logDir = makeLogFolder();
        File reportFile1 = File.createTempFile("reportone", "txt");
        File reportFile2 = File.createTempFile("reporttwo", "txt");
        analyzer.processLogFiles(new String[]{logDir.getAbsolutePath(),
                reportFile1.getAbsolutePath(),
                "*"});
        analyzer.processLogFiles(new String[]{logDir.getAbsolutePath(),
                reportFile2.getAbsolutePath(),
                "*",
                "AX88;AX99"});

        List<String> rpOneLines = Files.readAllLines(reportFile1.toPath(), Charset.defaultCharset());
        List<String> rpTwoLines = Files.readAllLines(reportFile2.toPath(), Charset.defaultCharset());



        deleteFolder(logDir);
        reportFile1.delete();
        reportFile2.delete();

        //verify   - report one
        contains(rpOneLines, 1, "ARJ44: good");
        contains(rpOneLines, 4, "a4");
        contains(rpOneLines, 7, "AX88");
        contains(rpOneLines, 10, "b4");
        contains(rpOneLines, 11, "b5");
        contains(rpOneLines, 12, "b6");
        contains(rpOneLines, 13, "AX99");
        contains(rpOneLines, 17, "c3");
        contains(rpOneLines, 18, "c4");
        contains(rpOneLines, 19, "c5");
        contains(rpOneLines, 20, "WEB07");
        contains(rpOneLines, 23, "WEB07");
        contains(rpOneLines, 26, "d1");
        contains(rpOneLines, 27, "d2");
        contains(rpOneLines, 28, "WEB09");

        // verify  - report two (should not contain AX99 or AX88
        for(String l : rpTwoLines) {
           if(l.contains("AX99") || l.contains("AX88")) {
               throw new RuntimeException("AX99 and AX88 where excluded but it is present in report");
           }
        }

        //verify with empty log folder
        File emtpyDir = mkdir("emptyfolder");
        File emptyReportFile = File.createTempFile("emptyreport", "txt");
        analyzer.processLogFiles(new String[]{emtpyDir.getAbsolutePath(),
                emptyReportFile.getAbsolutePath(),
                "*"});

        List<String> emptyReportLines = Files.readAllLines(emptyReportFile.toPath(), Charset.defaultCharset());
        deleteFolder(emtpyDir);
        emptyReportFile.delete();

        if(emptyReportLines.size() > 1) {
            throw new RuntimeException("There should not be any error detected when log folder is empty");
        }

    }

    private static File mkdir(String prefix) throws Exception {
        File dir1 = File.createTempFile(prefix, "");
        dir1.delete();
        dir1.mkdir();
        return dir1;
    }
    private static File makeLogFolder() throws Exception {
        File dir1 = mkdir("testlogs") ;
        new File(dir1, "log1.log").createNewFile();

        //file starts and ends with exception
        Files.write(new File(dir1, "log1.log").toPath(), Arrays.asList(
                "00:00:52,548 ERROR  [z] ARJ44: good\n at bad\n at ugly",
                "00:00:52,548 WARN  [x] ARJ33: a1",
                "00:00:52,548 ERROR  [y] ARJ33: a2",  //not considered as error
                "00:00:52,548 WARN  [x] ARJ33: a3",
                "00:00:52,548 WARN  [x] ARJ33: a4",   //context 3
                "00:00:52,548 WARN  [x] ARJ33: a5",   //context 2
                "00:00:52,548 WARN  [x] ARJ33: a6",   //context 1
                "00:00:52,548 ERROR  [z] AX88: the\n at time\n at machine",   //error
                "00:00:52,548 INFO  [x] ARJ33: b1",
                "00:00:52,548 INFO  [x] ARJ33: b2",
                "00:00:52,548 INFO  [x] ARJ33: b3",
                "00:00:52,548 INFO  [x] ARJ33: b4",   //context 3
                "00:00:52,548 INFO  [x] ARJ33: b5",   //context 2
                "00:00:52,548 INFO  [x] ARJ33: b6",   //context 1
                "00:00:52,548 ERROR  [z] AX99: in\n at to\n at the\n at wild"   //error
        ), Charset.defaultCharset(), StandardOpenOption.APPEND);

        new File(dir1, "log2.log").createNewFile();
        Files.write(new File(dir1, "log2.log").toPath(), Arrays.asList(
                "00:00:52,548 WARN  [x] WEB04: c1",
                "00:00:52,548 WARN  [x] WEB04: c2",
                "00:00:52,548 WARN  [x] WEB04: c3",    //context 3
                "00:00:52,548 ERROR  [y] WEB05: c4",   //context 2
                "00:00:52,548 WARN  [x] WEB06: c5",    //context 3
                "00:00:52,548 ERROR  [z] WEB07: Last\n at man\n at standing",       //error - context 3
                "00:00:52,548 INFO  [x] WEB08: d1",                                 //context 2
                "00:00:52,548 INFO  [x] WEB08: d2",                                 //context 1
                "00:00:52,548 ERROR  [z] WEB09: gone\n at baby\n at gone",           //error
                "00:00:52,548 INFO  [x] WEB08: e1",
                "00:00:52,548 INFO  [x] WEB08: e2",
                "00:00:52,548 INFO  [x] WEB08: e3",
                "00:00:52,548 INFO  [x] WEB08: e4"
        ), Charset.defaultCharset(), StandardOpenOption.APPEND);
        return dir1;
    }

    private static void deleteFolder(File folder) {
        for (File f : folder.listFiles()) {
            f.delete();
        }
        folder.delete();
    }

    private static boolean contains(List<String> rpOneLines, int i, String line) {

        if (!rpOneLines.get(i).contains(line)) {
            throw new RuntimeException(String.format("Line # %d do not contain"
                    + " %s. actual value :%s", i, line, rpOneLines.get(i)));
        }

        return true;
    }
}
