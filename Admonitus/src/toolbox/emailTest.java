package toolbox;

public class emailTest {

	public static void main(String[] args) {
		 emailConfirmation test = new emailConfirmation("a.m.brandstrup@gmail.com", "Admonitus", "Confirmation Message", "added");
		 test.sendEmail();
	}

}
