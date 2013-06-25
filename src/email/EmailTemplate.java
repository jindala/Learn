package email;

public class EmailTemplate {
	
	public static String getEmailTemplate(String emailType) {
		String emailTemplate = "";
		if(emailType.equals("register_email")) {
			emailTemplate = "<Hello, <br><br>Congratulations! Welcome to The SocialFood!" + 
			" The revolutionary new way to eating!<br><br>Your account has been created - " + 
			"now it will be easier than ever to eat, share, make money with hosting meals, " + 
			"all this while making new friends.<br><br>To complete the sign-up process, please click here " + 
			"<div style='background-color:#00C90D;color:white;font-size: 20px;border:2px gray;padding:10px;border-radius: 6px;border-bottom-style:  solid;border-right-style:  solid;text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);box-shadow: 0 -20px #05af0a inset;max-width: 125px;'>Verify Account</div>" + 
			"<br><br>Happy Eat/Host-ing!<br>The SocialFood Team";
		} else if (emailType.equals("payment_email")) {
			// TODO need to change hard coding for number of guests and date
			emailTemplate = "Hello,<br>Thank you for registering for the event. " + 
			"Your chef, is expecting you along with 2 guests on 06/25/2013. <br> " + 
			"If you need to make changes to the event, you can: " + 
			"<div style='max-width: 125px;background-color:#00C90D;color:white;font-size: 20px;border:2px gray;padding:10px;border-radius: 6px;border-bottom-style:  solid;border-right-style:  solid;text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);box-shadow: 0 -20px #05af0a inset;'>Update RSVP</div>." + 
			"<br><br> To follow the chef, please click: <div style='max-width: 125px;background-color:#00C90D;color:white;font-size: 20px;border:2px gray;padding:10px;border-radius: 6px;border-bottom-style:  solid;border-right-style:  solid;text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);box-shadow: 0 -20px #05af0a inset;'>Follow Chef</div>.<br> " + 
			"To rate this chef, please click: <div style='max-width: 125px;background-color:#00C90D;color:white;font-size: 20px;border:2px gray;padding:10px;border-radius: 6px;border-bottom-style:  solid;border-right-style:  solid;text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);box-shadow: 0 -20px #05af0a inset;'>Chef Feedback</div>.<br><br>" + 
			" If for some reason you are not able to make it to the event, please cancel at least 48 hours before the event. <br><br> " + 
			"If you like the service, please show us some love by inviting friends and spreading the word. <br><br> " + 
			"Happy Eating <br> The SocialFood Team";
		}
		
		return emailTemplate;
	}
}