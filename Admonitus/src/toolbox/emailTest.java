package toolbox;

public class emailTest {

	public static void main(String[] args) {
		 emailConfirmation test = new emailConfirmation("matthysg@chc.edu", "Admonitus", "Confirmation Message", "added");
		 test.sendEmail();
	}

}
