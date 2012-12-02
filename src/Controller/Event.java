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
		System.out.println("event incoming param map: " + reqParamMap);
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
			System.out.println("saving event");
			insertSuccessful = dbUtil.insertintoDB(EVENT_COLLECTION, eventDoc);
			System.out.println("event saved");
		} catch (UnknownHostException e) {
			throw e;
		}
		return insertSuccessful;
	}
	
	public String getEvent(String eventId) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("_id", eventId);
		DBUtil dbUtil = new DBUtil();
		String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}
	
	public String getEventsByZip(String zip) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("address.zip", zip);
		DBUtil dbUtil = new DBUtil();
		String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}
	
	public String getEventsByCuisine(String cuisine) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("cuisine", cuisine);
		DBUtil dbUtil = new DBUtil();
		String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}
	
	public String getEventsByCuisineAndZip(String cuisine, String zip) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("cuisine", cuisine);
		queryObj.put("address.zip", zip);
		DBUtil dbUtil = new DBUtil();
		String events = dbUtil.queryDocs(EVENT_COLLECTION, queryObj);
		
		return events;
	}

}
