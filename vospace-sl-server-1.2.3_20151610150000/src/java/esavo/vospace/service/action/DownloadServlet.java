/**
 *
 */
package esavo.vospace.service.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import esavo.vospace.service.cmd.service.TransferService;
import esavo.vospace.service.utils.ConfigProperties;

/**
 * Download service to retrieve files and/or folders from VOSpace
 * in compressed format (ZIP). Given a requested node URI the service
 * compress and retrieve a zip file with the nodes requested by the user.
 * If the request is only for a unique node, it's a GET HTTP request.
 * IF the request is for more than one node, it's a POST request.
 *
 * @author Sara Nieto - ESAC/ESA - Madrid, Spain Copyright (c) 2013
 *
 */
public class DownloadServlet extends HttpServlet {  

	private static final long serialVersionUID = 1L;
	
	private static final String downloadPath = ConfigProperties.getProperty("vospace.download");
	
	private final String FILE_SEP = System.getProperty("file.separator");

	//protected final int BUFFER_SIZE = 67108864; //64MB
    
    /*
     * Return the list of files requested in the POST HTTP call
     * which are comma separated.
     */
	private String[] getFiles (String fileName) {
		String[] files = fileName.split(",");
		return files;
	}

	/*
	 * Create an inventory file with the sources to be compressed
	 * in the following Get request.
	 * The inventory file is saved in localfs/tmp folder and the deleted.
	 */
	@Override
    public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException{

		BufferedReader br = new BufferedReader(
				new InputStreamReader(request.getInputStream()));

		String[] files = null;
		String line = br.readLine();
		if (line != null) {
			files = getFiles (line);
		}

		if (files != null && files.length > 0) {

		    String tmpFolder = downloadPath + FILE_SEP + "tmp";
			// String tmpFolder = /home/snieto/Desktop/wrk/localfs/tmp
			//hash date creation
			Date d = new Date();
			byte[] resultByte = DigestUtils.md5(d.toString());
			String hash = new String(Hex.encodeHex(resultByte));

			String exportFile = "documents-export-"+ hash; //file inventory without extension
			File inventory = new File(tmpFolder, exportFile);
			FileUtils.writeStringToFile(inventory, line);

			PrintWriter pw = response.getWriter();
			String urlToExportFile = exportFile;
			pw.write(urlToExportFile);
		}
	}
	
	/*
     * Start creating the Zip file and download in streaming mode.
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException{

        String name = request.getParameter("archived");
        String path = downloadPath + FILE_SEP + "tmp" + FILE_SEP + name;
        TransferService transferService = new TransferService();
        try {
            transferService.downloadNode(response, path, name, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
