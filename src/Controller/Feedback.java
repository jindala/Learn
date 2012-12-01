package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBObject;

import Modal.DBUtil;


public class Feedback {
	
	final static String FEEDBACK_COLLECTION = "feedback";
	boolean saveFeedback(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map reqParamMap = req.getParameterMap();
		
		BasicDBObject feedbackDoc = new BasicDBObject();
		
		feedbackDoc.put("email", reqParamMap.get("email"));
		feedbackDoc.put("feedback_by_email", reqParamMap.get("feedback_by_email"));
		feedbackDoc.put("comment", reqParamMap.get("comment"));
		feedbackDoc.put("rating", reqParamMap.get("rating"));
		feedbackDoc.put("oneLiner", reqParamMap.get("oneLiner"));
		feedbackDoc.put("feedback_type", reqParamMap.get("feedback_type"));
		
		boolean insertSuccessful = false;
		try {
			insertSuccessful = dbUtil.insertintoDB(FEEDBACK_COLLECTION, feedbackDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return insertSuccessful;
	}
	
	Map getFeedback(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("email", email);
		DBUtil dbUtil = new DBUtil();
		Map feedback = dbUtil.queryDocs(FEEDBACK_COLLECTION, queryObj);
		
		return feedback;
	}

}
