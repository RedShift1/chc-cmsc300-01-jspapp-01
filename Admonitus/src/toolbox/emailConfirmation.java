package toolbox;

import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailConfirmation {

	String to;
	String from;
	String subject;
	String action;

	public emailConfirmation(String to, String from, String subject, String action) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.action = action;
	}

	public void sendEmail() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.user", "admonitusconfirmation@gmail.com");
		properties.put("mail.smtp.password", "anotherchancetonight");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"admonitusconfirmation@gmail.com",
								"anotherchancetonight");
					}
				});

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			message.setSubject(subject);

			message.setContent("<body style='font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;'> <h2> Confirmation Message </h2> <br><hr> <center>Your reminder has been " + action + "</center><hr><p>Best Regards</p><p>Admonitus Team</p></body>", "text/html");

			Transport.send(message);
			System.out.println("Email sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
