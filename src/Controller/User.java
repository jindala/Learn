package Controller;

import java.net.UnknownHostException;

import java.util.Map;

import javax.servlet.http.*;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import Modal.DBUtil;

public class User {
	final static String USER_COLLECTION = "user";
	public String saveUserInfo(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map<String, String[]> reqParamMap = req.getParameterMap();
		
		BasicDBObject userDoc = new BasicDBObject();
		BasicDBObject useraddrDoc = new BasicDBObject();
		
		/*useraddrDoc.put("street1", reqParamMap.get("street1"));
		useraddrDoc.put("street2", reqParamMap.get("street2"));
		useraddrDoc.put("city", reqParamMap.get("city"));
		useraddrDoc.put("state", reqParamMap.get("state"));
		useraddrDoc.put("zip", reqParamMap.get("zip"));*/
		
		userDoc.put("email", reqParamMap.get("email")[0]);
		userDoc.put("name", reqParamMap.get("name")[0]);
		userDoc.put("password", reqParamMap.get("password")[0]);
		//userDoc.put("phone", reqParamMap.get("phone"));
		//userDoc.put("address", useraddrDoc);
		
		String id = null;
		try {
			id = dbUtil.insertintoDB(USER_COLLECTION, userDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return id;
	}
	
	public JSONObject getUserInfo(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("email", email);
		DBUtil dbUtil = new DBUtil();
		
		return dbUtil.queryDocs(USER_COLLECTION, queryObj);
	}
	
	public JSONObject getUserInfoFromId(String id) throws UnknownHostException {
		BasicDBObject queryObj = new BasicDBObject();
		ObjectId objId = ObjectId.massageToObjectId(id);
		System.out.println("user objectId=" + objId);
		queryObj.put("_id", objId);
		DBUtil dbUtil = new DBUtil();
		JSONObject userResult = dbUtil.queryDocs(USER_COLLECTION, queryObj);
		return userResult;
	}
	
	public JSONObject verifyUserInfo(String email, String password) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("email", email);
		queryObj.put("password", password);
		DBUtil dbUtil = new DBUtil();
		JSONObject returnUser = dbUtil.queryDocs(USER_COLLECTION, queryObj);
		
		if(returnUser.isEmpty()) {
			System.out.println("user Not Found");
			return null;
		}
		System.out.println("user Found" + returnUser);
		return returnUser;
	}
	
	public String addFeedback(HttpServletRequest req) throws UnknownHostException {
		Map<String, String[]> reqParamMap = req.getParameterMap();		
		
		String reviewee = reqParamMap.get("reviewee")[0];
		
		BasicDBObject queryObj = new BasicDBObject();
		ObjectId objId = ObjectId.massageToObjectId(reviewee);
		System.out.println("user objectId=" + objId);
		queryObj.put("_id", objId);
		DBUtil dbUtil = new DBUtil();
		JSONObject userResult = dbUtil.queryDocs(USER_COLLECTION, queryObj);
		JSONArray userArray = ((JSONArray)userResult.get("result"));
		Map user = (Map) userArray.get(0); 
		
		BasicDBList feedbackList = new BasicDBList();
		if(user.containsKey("feedback")) {
			System.out.println("feedback: " +user.get("feedback"));
			feedbackList = (BasicDBList)user.get("feedback");
		}
		
		// Form Json object for new feedback
		BasicDBObject feedbackDoc = new BasicDBObject();
		feedbackDoc.put("reviewee", reviewee); 		//reviewee: organizer/customer
		feedbackDoc.put("reviewer", reqParamMap.get("reviewer")[0]); 		//reviewer
		feedbackDoc.put("reviewerName", reqParamMap.get("reviewerName")[0]); 
		feedbackDoc.put("comment", reqParamMap.get("feedback")[0]);
		feedbackDoc.put("rating", reqParamMap.get("rating")[0]);
		feedbackList.add(feedbackDoc);
		
		BasicDBObject updateQuery = new BasicDBObject();
		System.out.println("new feedback list: " + feedbackList);
		updateQuery.append("$set", 
				new BasicDBObject().append("feedback", feedbackList));
			
		dbUtil.updateDoc(USER_COLLECTION, queryObj, updateQuery);
		return reqParamMap.get("reviewer")[0];
	}

}
