package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import Modal.DBUtil;

import com.mongodb.BasicDBObject;

public class Event {
	final static String EVENT_COLLECTION = "event";
	public boolean saveEvent(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map reqParamMap = req.getParameterMap();
		
		BasicDBObject eventDoc = new BasicDBObject();
		BasicDBObject dishDoc = new BasicDBObject();
		BasicDBObject eventaddrDoc = new BasicDBObject();
		
		eventaddrDoc.put("street1", reqParamMap.get("street1"));
		eventaddrDoc.put("street2", reqParamMap.get("street2"));
		eventaddrDoc.put("city", reqParamMap.get("city"));
		eventaddrDoc.put("state", reqParamMap.get("state"));
		eventaddrDoc.put("zip", reqParamMap.get("zip"));
		
		eventDoc.put("host_id", reqParamMap.get("email"));
		eventDoc.put("capacity", reqParamMap.get("capacity"));
		eventDoc.put("cuisine", reqParamMap.get("cuisine"));
		eventDoc.put("timming", reqParamMap.get("timming"));
		eventDoc.put("cutOffTime", reqParamMap.get("cutOffTime"));
		eventDoc.put("dish", dishDoc);
		eventDoc.put("address", eventaddrDoc);
		eventDoc.put("cost", reqParamMap.get("cost"));
		eventDoc.put("sponsor", reqParamMap.get("sponsor"));
		eventDoc.put("description", reqParamMap.get("description"));
		
		
		boolean insertSuccessful = false;
		try {
			insertSuccessful = dbUtil.insertintoDB(EVENT_COLLECTION, eventDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return insertSuccessful;
	}
	
	public Map getEvent(String eventId) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("_id", eventId);
		DBUtil dbUtil = new DBUtil();
		Map events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}
	
	public Map getEventsByZip(String zip) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("address.zip", zip);
		DBUtil dbUtil = new DBUtil();
		Map events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}
	
	public Map getEventsByCuisine(String cuisine) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("cuisine", cuisine);
		DBUtil dbUtil = new DBUtil();
		Map events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}
	
	public Map getEventsByCuisineAndZip(String cuisine, String zip) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("cuisine", cuisine);
		queryObj.put("address.zip", zip);
		DBUtil dbUtil = new DBUtil();
		Map events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}

}
