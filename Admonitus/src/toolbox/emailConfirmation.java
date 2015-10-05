package toolbox;

import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailConfirmation {
	public static void main(String[] args) {

		String to = "a.m.brandstrup@gmail.com";
		String from = "a.m.brandstrup@gmail.com";
		String subject = "Test Mail";
		String text = "Yo, tester";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.user", "admonitusconfirmation@gmail.com");
		properties.put("mail.smtp.password", "anotherchancetonight");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(properties, 
			    new javax.mail.Authenticator(){
			        protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(
			                "admonitusconfirmation@gmail.com", "anotherchancetonight");
			        }
			});
		
		try{
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			message.setSubject(subject);
			
			message.setText(text);
			
			Transport.send(message);
			System.out.println("Email sent");
		}catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
