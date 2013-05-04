package Controller;

import java.net.UnknownHostException;

import java.util.Map;

import javax.servlet.http.*;

import org.json.simple.JSONObject;

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
		//String userInfo = dbUtil.queryDocs(USER_COLLECTION, queryObj);
		
		return dbUtil.queryDocs(USER_COLLECTION, queryObj);
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

}
