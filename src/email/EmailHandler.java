package email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailHandler {
	
	protected static void sendEmail(List<String> recipients, List<String> bccRecipients, String subject, String body) 
		throws MessagingException{
		
		String userName = "socialfoodservices";
		String password = "testaccount";
		
		Properties props = new Properties();
	    props.setProperty("mail.smtps.host", "smtp.gmail.com");
	    // props.setProperty("mail.smtp.auth", "true"); 
	    Session session = Session.getInstance(props, null);
	    Transport transport = session.getTransport("smtps");
	    transport.connect(userName, password);
	
	    Message message = new MimeMessage(session);
	    message.setSubject(subject);
	    message.setContent(body, "text/html");
	    // message.setText(body);
	    message.setFrom(new InternetAddress(userName + "@gmail.com"));
	    if(recipients != null && recipients.size() > 0) {
		    Address[] recipientList = new InternetAddress[recipients.size()];
		    for(int i=0; i<recipients.size(); i++) {
		    	String recipient = recipients.get(i);
		    	recipientList[i] = new InternetAddress(recipient);	    	
		    }	    	
		    message.setRecipients(Message.RecipientType.TO, recipientList);
	    }
	    if(bccRecipients != null && bccRecipients.size() > 0) {
		    Address[] bccRecipientList = new InternetAddress[bccRecipients.size()];
		    for(int i=0; i<bccRecipients.size(); i++) {
		    	String recipient = bccRecipients.get(i);
		    	bccRecipientList[i] = new InternetAddress(recipient);	    	
		    }	    	
		    message.setRecipients(Message.RecipientType.BCC, bccRecipientList);
	    }
	    transport.sendMessage(message, message.getAllRecipients());
	}
	
	public static void send(List<String> recipients, List<String> bccRecipients, String subject, String body) {
		try {
			sendEmail(recipients, bccRecipients, subject, body);
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void send(String recipient, List<String> bccRecipients, String subject, String body) {
		try {
			List<String> recipients = new ArrayList<String>();
			if(recipient != null)
				recipients.add(recipient);
			sendEmail(recipients, bccRecipients, subject, body);
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void send(String recipient, String bccRecipient, String subject, String body) {
		try {
			List<String> recipients = new ArrayList<String>();
			if(recipient != null)
				recipients.add(recipient);
			List<String> bccRecipients = new ArrayList<String>();
			if(bccRecipient != null)
				bccRecipients.add(bccRecipient);
			sendEmail(recipients, bccRecipients, subject, body);
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void send(List<String> recipients, String bccRecipient, String subject, String body) {
		try {
			List<String> bccRecipients = new ArrayList<String>();
			if(bccRecipient != null)
				bccRecipients.add(bccRecipient);
			sendEmail(recipients, bccRecipients, subject, body);
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
