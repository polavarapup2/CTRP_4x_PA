package gov.nih.nci.pa.pdq;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DownloadTerminology {
    private static final Logger LOG = Logger.getLogger(DownloadTerminology.class);

    private static String filename = "Terminology.tar.gz";
    private static final int BUFFER = 2048;

    public void process() throws IOException {
        preCheck();
        LOG.info("Completed Pre Check ...........");
        cleanUp();
        LOG.info("Completed Clean up ...........");
        download();
        LOG.info("Completed Download ...........");
        extract();
        LOG.info("Completed Extraction ...........");
    }


    private void download() throws IOException {
      final JSch jsch = new JSch();
      Session session = null;
      try {    	  
    	  session = jsch.getSession("ctrppdq", "cancerinfo.nci.nih.gov", 22);          
          session.setConfig("StrictHostKeyChecking", "no");               
          session.setPassword("Df0211$$");
          session.connect();
          
          if(!session.isConnected()) {
              LOG.error("Unable to connect to PDQ");
              throw new IOException("Unable to connect to SFTP Server.");        	  
          }
                      
          Channel channel = session.openChannel("sftp");
          channel.connect();
          ChannelSftp sftpChannel = (ChannelSftp) channel;
    	  
          FileOutputStream fos = null;
          fos = new FileOutputStream(filename);
          sftpChannel.get("/ncicb/" + filename, fos);
          
          fos.close();
          sftpChannel.exit();  
          sftpChannel.disconnect();
          channel.disconnect();
          session.disconnect();
      } catch (Exception e) {
          LOG.error(e);
      }

    }
    private void extract() throws IOException {
        final TarArchiveInputStream tais = new TarArchiveInputStream(getInputStream(filename));
        TarArchiveEntry tea = tais.getNextTarEntry() ;
        BufferedOutputStream dest = null;
        FileOutputStream fos = null;
        byte data[] = null;
        while (tea != null) {
            if (tea.getName().endsWith(".xml")) {
                data = new byte[BUFFER];
                fos = new FileOutputStream(tea.getName());
                dest = new BufferedOutputStream(fos, BUFFER);
                int count = tais.read(data, 0, BUFFER);
                while (count != -1) {
                   dest.write(data, 0, count);
                   count = tais.read(data, 0, BUFFER);
                }
                dest.flush();
                dest.close();
            }
            tea = tais.getNextTarEntry();
        }
    }

    private void preCheck() {
        new File("disease.sql").delete();
        new File("intervention.sql").delete();
    }

    private void cleanUp() {
        new File(filename).delete();
        final File[] files = new File("./Terminology").listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".xml")) {
                file.delete();
            }

        }

    }

    private InputStream getInputStream(final String tarFileName) throws IOException {
        return new GZIPInputStream(new FileInputStream(new File(tarFileName)));
    }

}
