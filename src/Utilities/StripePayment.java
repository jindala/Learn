package Utilities;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.*;

import Controller.Event;


public class StripePayment {

	final static String API_KEY = "sk_test_T8oA8Sl7loqlyKgeKlf3xCQG";
	
	static Map<String, Double> getStripeTokenList() throws UnknownHostException {
		Map<String, Double> tokenList = new HashMap<String, Double>();
		
		Event event = new Event();
		JSONObject allEventsObj = event.getAllEvents();
		JSONArray allEvents = (JSONArray)allEventsObj.get("result");
		
		for(int i=0; i<allEvents.size(); i++) {
			Map eachEvent = (Map)allEvents.get(i);
			
			Float pricePerSeat = Float.parseFloat(eachEvent.get("price").toString());
			BasicDBList attendeeList = new BasicDBList();
			if(eachEvent.containsKey("attendees")) {
				System.out.println("registered attendees: " +eachEvent.get("attendees"));
				attendeeList = (BasicDBList)eachEvent.get("attendees");
			}
			
			for(int j=0;j<attendeeList.size();j++) {
				BasicDBObject eachAttendee = (BasicDBObject) attendeeList.get(j);
				
				//Amount
				int totalGuests = Integer.parseInt(eachAttendee.getString("totalGuests"));
				Double amount = new Double(totalGuests*pricePerSeat);
				tokenList.put(eachAttendee.getString("stripePayment"),amount);
			}
		}
		return tokenList;
	}
	
	static void chargeWithStripe() throws UnknownHostException, AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Map<String, Double> stripeTokenList = getStripeTokenList();
		Stripe.apiKey = "sk_test_T8oA8Sl7loqlyKgeKlf3xCQG";
				
		for(String eachToken: stripeTokenList.keySet()) {
				HashMap<String, Object> customerParams = new HashMap<String, Object>();
				customerParams.put("card", eachToken);
				customerParams.put("description", "socialFood payment");
				
				Customer customer = Customer.create(customerParams);

				HashMap<String, Object> chargeParams = new HashMap<String, Object>();
				chargeParams.put("currency", "usd");
				chargeParams.put("amount", stripeTokenList.get(eachToken)*100);
				chargeParams.put("customer", customer.getId());
				
				Charge.create(chargeParams);
				System.out.println("payment complete for " + eachToken);
		}
	}
	
	public static void main(String[] string) {
		try {
			chargeWithStripe();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
