package toolbox;

import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class AdmonitusEmail extends emailConfirmation
{
    
    private ArrayList<String> lines;
    private ArrayList<String> bodyLines;
    private IContentProvider contentProvider;
    
    public AdmonitusEmail(String to, String subject, String content)
    {
        super(to, "admonitusconfirmation@gmail.com", subject, content);
        this.lines = new ArrayList<String>();
        this.bodyLines = new ArrayList<String>();
    }

    public void send() throws AddressException, MessagingException
    {
        this.addHeader();
        this.addLine(String.join("", this.bodyLines));
        this.addLine(String.join("", this.contentProvider.getLines()));
        this.addFooter();
        this.setContent(String.join("", this.lines));
        
        TEmailSender sender = new TEmailSender(this);
        
        sender.start();
    }
    
    public void setContentProvider(IContentProvider cp)
    {
        this.contentProvider = cp;
    }
    
    public void addBodyLine(String line)
    {
        this.bodyLines.add(line);
    }
    
    private void addLine(String line)
    {
        this.lines.add(line);
    }
    
    private void addHeader()
    {
        this.addLine("<!DOCTYPE html>");
        this.addLine("<html>");
        this.addLine("<head>");
        this.addLine("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        this.addLine("<style type=\"text/css\">");
        this.addLine("body { font-family: Helvetica Neue, Helvetica, Arial, sans-serif; }");
        this.addLine("</style>");
        this.addLine("<body>");
        this.addLine("<h1>Admonitus</h1>");
    }
    
    private void addFooter()
    {
        this.addLine("</body>");
        this.addLine("</html>");
    }
}
