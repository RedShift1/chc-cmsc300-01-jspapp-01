package toolbox;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class emailTest {

	public static void main(String[] args) throws AddressException, MessagingException {
            System.out.println("Creating email");
            AdmonitusEmail email = new AdmonitusEmail("matthysg@chc.edu", "Account activation", null);
            System.out.println("Adding cp");
            email.setContentProvider(new ActivationEmail("1234"));
            System.out.println("Sending");
            email.send();
            System.out.println("Sent email");

	}

}
