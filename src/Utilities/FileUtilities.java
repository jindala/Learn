package Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;

public class FileUtilities {

	final static String FILE_LOCATION = "/Users/veersingh/Desktop/Projects/socialFood_New/Learn/files";
	public boolean saveFile(FileItem item) throws IOException, ServletException {
		if(item == null)
			return false;
		
		InputStream filecontent = null;
		OutputStream outputStream = null;
		try {
			System.out.println("file item name" + item.getName());
            String filename = item.getName();
			
			filecontent = item.getInputStream();
	        
	        // write the inputStream to a FileOutputStream
			outputStream = 
	                    new FileOutputStream(new File(FILE_LOCATION + filename));
	 
			int read = 0;
			byte[] bytes = new byte[1024];
	 
			while ((read = filecontent.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
	 
			System.out.println("Done!");
			return true;
		} catch (IOException e) {
	        throw new ServletException("Cannot parse multipart request.", e);
	    } finally {
	    	if (filecontent != null) {
				try {
					filecontent.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 
			}
	    }
	}
}
