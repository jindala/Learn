package Controller;

import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBObject;

import Modal.DBUtil;

public class PaymentInfo {

	final static String PAYMENT_COLLECTION = "payment_info";
	String savePaymentInfo(HttpServletRequest req) throws UnknownHostException
	{
		DBUtil dbUtil = new DBUtil();
		
		Map reqParamMap = req.getParameterMap();
		
		BasicDBObject paymentInfoDoc = new BasicDBObject();
		
		paymentInfoDoc.put("email", reqParamMap.get("email"));
		paymentInfoDoc.put("ccname", reqParamMap.get("ccname"));
		paymentInfoDoc.put("ccnumber", reqParamMap.get("ccnumber"));
		paymentInfoDoc.put("expiration", reqParamMap.get("expiration"));
		paymentInfoDoc.put("cvv", reqParamMap.get("cvv"));
		paymentInfoDoc.put("zip", reqParamMap.get("zip"));
		
		String id = null;
		try {
			id = dbUtil.insertintoDB(PAYMENT_COLLECTION, paymentInfoDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return id;
	}
	
	String getPaymentInfo(String email) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("email", email);
		DBUtil dbUtil = new DBUtil();
		String paymentInfo = dbUtil.queryDocs(PAYMENT_COLLECTION, queryObj);
		
		return paymentInfo;
	}
}
