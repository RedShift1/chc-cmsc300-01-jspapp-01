package toolbox;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class TEmailSender extends Thread implements Runnable
{
    private AdmonitusEmail email;
    
    public TEmailSender(AdmonitusEmail email)
    {
        this.email = email;
    }
    
    @Override
    public void run()
    {
        try
        {
            email.sendEmail();
        }
        catch (AddressException e)
        {
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }
    
}
