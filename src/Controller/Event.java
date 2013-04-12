package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import Modal.DBUtil;

import com.mongodb.BasicDBObject;

public class Event {
	final static String EVENT_COLLECTION = "event";
	public String saveEvent(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map<String, String[]> reqParamMap = req.getParameterMap();
		System.out.println("event incoming param map: " + reqParamMap);
		BasicDBObject eventDoc = new BasicDBObject();
		BasicDBObject dishDoc = new BasicDBObject();
		BasicDBObject eventaddrDoc = new BasicDBObject();
		
		System.out.println("inserting street1: " + reqParamMap.get("street1")[0]);
		if(reqParamMap.get("street1") != null) eventaddrDoc.put("street1", reqParamMap.get("street1")[0]);
		if(reqParamMap.get("street2") != null) eventaddrDoc.put("street2", reqParamMap.get("street2")[0]);
		if(reqParamMap.get("city") != null) eventaddrDoc.put("city", reqParamMap.get("city")[0]);
		if(reqParamMap.get("state") != null) eventaddrDoc.put("state", reqParamMap.get("state")[0]);
		if(reqParamMap.get("zip") != null) eventaddrDoc.put("zip", reqParamMap.get("zip")[0]);
		if(reqParamMap.get("country") != null) eventaddrDoc.put("country", reqParamMap.get("country")[0]);
		
		if(reqParamMap.get("host_id") != null) eventDoc.put("host_id", reqParamMap.get("host_id")[0]);
		if(reqParamMap.get("seats") != null) eventDoc.put("seats", reqParamMap.get("seats")[0]);
		if(reqParamMap.get("cuisine") != null) eventDoc.put("cuisine", reqParamMap.get("cuisine")[0]);
		if(reqParamMap.get("time") != null) eventDoc.put("time", reqParamMap.get("time")[0]);
		if(reqParamMap.get("date") != null)eventDoc.put("date", reqParamMap.get("date")[0]);
		if(reqParamMap.get("cutOffTime") != null)eventDoc.put("cutOffTime", reqParamMap.get("cutOffTime")[0]);
		if(!dishDoc.isEmpty()) eventDoc.put("dish", dishDoc);
		if(!eventaddrDoc.isEmpty()) eventDoc.put("address", eventaddrDoc);
		if(reqParamMap.get("price") != null) eventDoc.put("price", reqParamMap.get("price")[0]);
		if(reqParamMap.get("title") != null) eventDoc.put("title", reqParamMap.get("title")[0]);
		if(reqParamMap.get("description") != null) eventDoc.put("description", reqParamMap.get("description")[0]);
		if(reqParamMap.get("sponsor") != null) eventDoc.put("sponsor", reqParamMap.get("sponsor")[0]);
		
		
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
