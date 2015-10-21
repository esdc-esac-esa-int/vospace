package esavo.vospace.service.utils.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esavo.vospace.service.utils.VOSConstants;

public class CompressionUtils {

    /** Logging variable. */
    private static Log Log = LogFactory.getLog(CompressionUtils.class);    
    private static final int BUFFER_SIZE = 67108864; //64MB    
    private static final String ENCODING = "UTF-8";
    
    /*
     * Compress a unique file in the given ZipOutputStream.
     * ZIP format.
     */
    public static void targzFile (File fileItem, TarArchiveOutputStream taos) 
            throws IOException {
        // TAR has an 8 gig file limit by default, this gets around that
        taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR); // to get past the 8 gig limit
        // TAR originally didn't support long file names, so enable the support for it
        taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        
        FileInputStream fis = new FileInputStream(fileItem);
        try {
            String fileName = URLDecoder.decode(fileItem.getName(), ENCODING);
            TarArchiveEntry te = new TarArchiveEntry(fileName);
            te.setSize(fileItem.length());
            taos.putArchiveEntry(te);
            
            int i;
            byte[] data = new byte[BUFFER_SIZE]; //64MB
            while ((i = fis.read(data)) != -1) {
                taos.write(data, 0, i);
                taos.flush(); // only streaming
            }
        } catch (ClientAbortException cae) {
            Log.info("TARGZ Downloading cancelled by user. ");
            cae.printStackTrace();
        } finally {
            fis.close();
            taos.closeArchiveEntry();
        }
    }
    
    /*
     * Compress a unique file in the given ZipOutputStream.
     * ZIP format.
     */
    public static void zipFile (File fileItem, ZipOutputStream out)
            throws IOException {
        FileInputStream fis = new FileInputStream(fileItem);
        try {
            //logger.log(Level.INFO, "ZipFile------------");            
            String fileName = URLDecoder.decode(fileItem.getName(), ENCODING);
            out.putNextEntry(new ZipEntry(fileName));
            int i;
            byte[] data = new byte[BUFFER_SIZE]; //64MB
            while ((i = fis.read(data)) != -1) {
                out.write(data, 0, i);
                out.flush(); // only streaming
            }            
        } catch (ClientAbortException cae) {
            Log.info("ZIP Downloading cancelled by user. ");
            cae.printStackTrace();
        } finally {
            out.closeEntry();
            fis.close();
        }
    }

    /*
     * Compress a folder in the given TarArchiveOutputStream.
     * TarGZ format
     */
    public static void targzFolder (File folder, String relPath, TarArchiveOutputStream taos) 
            throws IOException {
        // TAR has an 8 gig file limit by default, this gets around that
        taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR); // to get past the 8 gig limit
        // TAR originally didn't support long file names, so enable the support for it
        taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        
        try {
            if (relPath.compareTo("") != 0)
                relPath += VOSConstants.BAR;
            relPath += URLDecoder.decode(folder.getName(), ENCODING);
            
            TarArchiveEntry tarEntry = new TarArchiveEntry(relPath + VOSConstants.BAR);
            //Log.debug("FOLDER LENGTH: " + folder.length());
            taos.putArchiveEntry(tarEntry);
            tarEntry.setSize(folder.length()); //very important, otherwise it fails!
            taos.closeArchiveEntry();

            String[] list = folder.list();
            for (String item : list) {
                File fileItem = new File(folder, item);
                if (fileItem.isFile()) {
                    FileInputStream fis = new FileInputStream(fileItem);
                    try {                        
                        String entry = relPath + VOSConstants.BAR +
                                    URLDecoder.decode(fileItem.getName(), ENCODING);
                        TarArchiveEntry te = new TarArchiveEntry(entry);
                        //Log.debug("FOLDER LENGTH: " + fileItem.length());
                        te.setSize(fileItem.length()); //very important, otherwise it fails!
                        taos.putArchiveEntry(te);
                        
                        int i;
                        byte[] data = new byte[BUFFER_SIZE]; //64MB
                        while ((i = fis.read(data)) != -1) {
                            taos.write(data, 0, i);
                            taos.flush(); // only streaming
                        }
                    } finally {
                        fis.close();
                        taos.closeArchiveEntry(); 
                        // Close the entry! 
                        // Thanks to Beatriz for helping me spotting this!
                    }
                } else if (fileItem.isDirectory()) {
                    targzFolder(fileItem, relPath, taos);
                }
            }
        } catch (ClientAbortException cae) {
            Log.info("Downloading cancelled by user.");
            cae.printStackTrace();
        }
    }
    
    /*
     * Compress a folder in the given ZipOutputStream.
     * ZIP format.
     */
    public static void zipFolder (File folder, String relPath, ZipOutputStream out)
            throws IOException {
        try {
            if (relPath.compareTo("") != 0)
                relPath += VOSConstants.BAR;
            relPath += URLDecoder.decode(folder.getName(), ENCODING);

            out.putNextEntry(new ZipEntry(relPath + VOSConstants.BAR));
            out.closeEntry();

            String[] list = folder.list();
            for (String item : list) {
                File fileItem = new File(folder, item);
                if (fileItem.isFile()) {
                    FileInputStream fis = new FileInputStream(fileItem);
                    try {                        
                        String entry = relPath + VOSConstants.BAR +
                                    URLDecoder.decode(fileItem.getName(), ENCODING);
                        out.putNextEntry(new ZipEntry(entry));
                        int i;
                        byte[] data = new byte[BUFFER_SIZE]; //64MB
                        while ((i = fis.read(data)) != -1) {
                            out.write(data, 0, i);
                            out.flush(); // only streaming
                        }
                    } finally {
                        fis.close();
                        out.closeEntry();
                    }
                } else if (fileItem.isDirectory()) {
                    zipFolder(fileItem, relPath, out);
                }
            }
        } catch (ClientAbortException cae) {
            Log.info("Downloading cancelled by user.");
            cae.printStackTrace();
        } finally {
            out.closeEntry();
        }
    }
}
