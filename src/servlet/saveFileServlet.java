package servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Utilities.FileUtilities;

public class saveFileServlet extends HttpServlet{

	/**
     * @see HttpServlet#HttpServlet()
     */
    public saveFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("save new event: " + request.getParameterMap());
		
		String output = request.getParameterMap().toString();
		ServletOutputStream op = null;
		
		//FileUtils.writeStringToFile(new File("FileNameToWrite.txt"), output);
		/*PrintWriter out = new PrintWriter("test.txt");
		out.println(output);
		out.close();*/
		
		File temp = File.createTempFile("aabra", ".txt");
		
		// Delete temp file when program exits.
	    temp.deleteOnExit();

	    // Write to temp file
	    BufferedWriter oute = new BufferedWriter(new FileWriter(temp));
	    oute.write(output);
	    oute.close();
	    
	    System.out.println("loc da file: " + temp.getAbsolutePath());
		System.out.println("dat file: " + output);
		
		response.setContentType("application/file"); 
	    response.setHeader("Content-Disposition","attachment;filename=\"" + temp.getAbsolutePath()); 
	    response.setContentLength((int) temp.length()); 
	    
	    op = response.getOutputStream();
	    op.write(output.getBytes());
	    
		//PrintWriter resp = response.getWriter();
		//resp.print("success");
	}
}
