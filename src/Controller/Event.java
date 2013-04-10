package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import Modal.DBUtil;

import com.mongodb.BasicDBObject;

public class Event {
	final static String EVENT_COLLECTION = "event";
	public String saveEvent(HttpServletRequest req) throws UnknownHostException
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
		eventaddrDoc.put("country", reqParamMap.get("country"));
		
		eventDoc.put("host_id", reqParamMap.get("host_id"));
		eventDoc.put("seats", reqParamMap.get("seats"));
		eventDoc.put("cuisine", reqParamMap.get("cuisine"));
		eventDoc.put("time", reqParamMap.get("time"));
		eventDoc.put("date", reqParamMap.get("date"));
		eventDoc.put("cutOffTime", reqParamMap.get("cutOffTime"));
		eventDoc.put("dish", dishDoc);
		eventDoc.put("address", eventaddrDoc);
		eventDoc.put("price", reqParamMap.get("price"));
		eventDoc.put("title", reqParamMap.get("title"));
		eventDoc.put("description", reqParamMap.get("description"));
		eventDoc.put("sponsor", reqParamMap.get("sponsor"));
		
		
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
	
	public String getEvent(String eventId) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		ObjectId objId = ObjectId.massageToObjectId(eventId);
		System.out.println("event objectId=" + objId);
		queryObj.put("_id", objId);
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
