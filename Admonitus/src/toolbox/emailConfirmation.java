package toolbox;
import java.util.*;
import javax.activation.*;

public class emailConfirmation {
	
	private String to;
	private String from;
	private String subject;
	private String text;
	private String senderMail;
	private String senderPassword;
	private String recipientMail;
	
	public emailConfirmation() {
		
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
