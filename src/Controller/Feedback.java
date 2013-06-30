package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;

import Modal.DBUtil;


public class Feedback {
	
	final static String FEEDBACK_COLLECTION = "feedback";
	public String saveFeedback(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map<String, String[]> reqParamMap = req.getParameterMap();		
		BasicDBObject feedbackDoc = new BasicDBObject();
		
		feedbackDoc.put("reviewee", reqParamMap.get("reviewee")[0]); 		//reviewee: organizer/customer
		feedbackDoc.put("reviewer", reqParamMap.get("reviewer")[0]); 		//reviewer
		feedbackDoc.put("feedback", reqParamMap.get("feedback")[0]);
		feedbackDoc.put("rating", reqParamMap.get("rating")[0]);						
		
		String id=null;
		try {
			id = dbUtil.insertintoDB(FEEDBACK_COLLECTION, feedbackDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return id;
	}
	
	public JSONObject getOrganizerFeedback(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("revieweeEmail", email);
		
		DBUtil dbUtil = new DBUtil();
		//String feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
	}

	public JSONObject getCustomerFeedback(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("reviewerEmail", email);
		
		DBUtil dbUtil = new DBUtil();
		//String feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
	}
	
	public JSONObject getOrganizerFeedbackByCuisine(String email, String cuisine) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("reviewerEmail", email);
		queryObj.put("feedback_cuisine", cuisine);
		
		DBUtil dbUtil = new DBUtil();
		//String feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
	}
}
