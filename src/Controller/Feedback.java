package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBObject;

import Modal.DBUtil;


public class Feedback {
	
	final static String FEEDBACK_COLLECTION = "feedback";
	public boolean saveFeedback(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map reqParamMap = req.getParameterMap();
		
		BasicDBObject feedbackDoc = new BasicDBObject();
		
		feedbackDoc.put("revieweeEmail", reqParamMap.get("revieweeEmail")); 		//reviewee: organizer/customer
		feedbackDoc.put("reviewerEmail", reqParamMap.get("reviewerEmail")); 		//reviewer
		feedbackDoc.put("comment", reqParamMap.get("comment"));
		//To-DO: verify 'rating' is integer
		feedbackDoc.put("rating", reqParamMap.get("rating"));						
		feedbackDoc.put("oneLiner", reqParamMap.get("oneLiner"));					//title?
		feedbackDoc.put("feedback_type", reqParamMap.get("feedback_type")); 		//customer or organizer
		feedbackDoc.put("feedback_cuisine", reqParamMap.get("feedback_cuisine")); 	//feedback for a particular cuisine in general
		
		boolean insertSuccessful = false;
		try {
			insertSuccessful = dbUtil.insertintoDB(FEEDBACK_COLLECTION, feedbackDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return insertSuccessful;
	}
	
	public String getOrganizerFeedback(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("revieweeEmail", email);
		
		DBUtil dbUtil = new DBUtil();
		String feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return feedback;
	}

	public String getCustomerFeedback(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("reviewerEmail", email);
		
		DBUtil dbUtil = new DBUtil();
		String feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return feedback;
	}
	
	public String getOrganizerFeedbackByCuisine(String email, String cuisine) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("reviewerEmail", email);
		queryObj.put("feedback_cuisine", cuisine);
		
		DBUtil dbUtil = new DBUtil();
		String feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return feedback;
	}
}
