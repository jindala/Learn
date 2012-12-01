package Controller;

import java.net.UnknownHostException;

import java.util.Map;

import javax.servlet.http.*;

import com.mongodb.BasicDBObject;

import Modal.DBUtil;

public class User {
	final static String USER_COLLECTION = "user";
	public boolean saveUserInfo(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map reqParamMap = req.getParameterMap();
		
		BasicDBObject userDoc = new BasicDBObject();
		BasicDBObject useraddrDoc = new BasicDBObject();
		
		useraddrDoc.put("street1", reqParamMap.get("street1"));
		useraddrDoc.put("street2", reqParamMap.get("street2"));
		useraddrDoc.put("city", reqParamMap.get("city"));
		useraddrDoc.put("state", reqParamMap.get("state"));
		useraddrDoc.put("zip", reqParamMap.get("zip"));
		
		userDoc.put("email", reqParamMap.get("email"));
		userDoc.put("name", reqParamMap.get("name"));
		userDoc.put("password", reqParamMap.get("password"));
		userDoc.put("phone", reqParamMap.get("phone"));
		userDoc.put("address", useraddrDoc);
		
		boolean insertSuccessful = false;
		try {
			insertSuccessful = dbUtil.insertintoDB(USER_COLLECTION, userDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return insertSuccessful;
	}
	
	public Map getUserInfo(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("email", email);
		DBUtil dbUtil = new DBUtil();
		Map userInfo = dbUtil.queryDocs(USER_COLLECTION, queryObj);
		
		return userInfo;
	}

}
