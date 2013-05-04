package Controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import Modal.DBUtil;
import Utilities.FileUtilities;

import com.mongodb.BasicDBObject;

public class Event {
	final static String EVENT_COLLECTION = "event";
	public String saveEvent(HttpServletRequest req) throws IOException, ServletException
	{
		DBUtil dbUtil = new DBUtil();
		
		System.out.println("incoming content type: " + req.getContentType());
		//Map<String, String[]> reqParamMap = req.getParameterMap();
		BasicDBObject eventDoc = new BasicDBObject();
		BasicDBObject dishDoc = new BasicDBObject();
		BasicDBObject eventaddrDoc = new BasicDBObject();
	 
		 try {
		        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
		        for (FileItem item : items) {
		            if (item.isFormField()) {
		                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
		                String fieldname = item.getFieldName();
		                String fieldvalue = item.getString();
		                System.out.println("save fieldname =" + fieldname);
		        		if(fieldname.equals("street1")) eventaddrDoc.put("street1", fieldvalue);
		        		else if(fieldname.equals("street2")) eventaddrDoc.put("street2", fieldvalue);
		        		else if(fieldname.equals("city")) eventaddrDoc.put("city", fieldvalue);
		        		else if(fieldname.equals("state")) eventaddrDoc.put("state", fieldvalue);
		        		else if(fieldname.equals("zip")) eventaddrDoc.put("zip", fieldvalue);
		        		else if(fieldname.equals("country")) eventaddrDoc.put("country", fieldvalue);
		        		else {
		        			eventDoc.put(fieldname, fieldvalue);
		        		}
		                
		            } else {
		                // Process form file field (input type="file").
		                String fieldname = item.getFieldName();
		                String filename = item.getName();
		                eventDoc.put(fieldname, "/" + filename);
		                
		                FileUtilities fileUtility = new FileUtilities();
		                fileUtility.saveFile(item);
		            }
		        }
		    } catch (FileUploadException e) {
		        throw new ServletException("Cannot parse multipart request.", e);
		    } 
		
		if(!dishDoc.isEmpty()) eventDoc.put("dish", dishDoc);
		if(!eventaddrDoc.isEmpty()) eventDoc.put("address", eventaddrDoc);
		
		String id = null;
		try {
			System.out.println("saving event");
			id = dbUtil.insertintoDB(EVENT_COLLECTION, eventDoc);
			System.out.println("event saved");
			
			
		} catch (UnknownHostException e) {
			throw e;
		}
		return id;
	}
	
	public JSONObject getEvent(String eventId) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		ObjectId objId = ObjectId.massageToObjectId(eventId);
		System.out.println("event objectId=" + objId);
		queryObj.put("_id", objId);
		DBUtil dbUtil = new DBUtil();
		//String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
	}
	
	public JSONObject getEventsByZip(String zip) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("address.zip", zip);
		DBUtil dbUtil = new DBUtil();
		//String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
	}
	
	public JSONObject getEventsByCuisine(String cuisine) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("cuisine", cuisine);
		DBUtil dbUtil = new DBUtil();
		//String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
	}
	
	public JSONObject getEventsByCuisineAndZip(String cuisine, String zip) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("cuisine", cuisine);
		queryObj.put("address.zip", zip);
		DBUtil dbUtil = new DBUtil();
		//String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
	}

}
