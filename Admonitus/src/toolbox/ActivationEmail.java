package toolbox;

import java.util.ArrayList;

public class ActivationEmail implements IContentProvider
{

    private String activationKey;

    public ActivationEmail(String activationKey)
    {
        this.activationKey = activationKey;
    }
    
    @Override
    public ArrayList<String> getLines()
    {
        ArrayList<String> lines = new ArrayList<String>();
        
        lines.add("<h2>Activation</h2>");
        
        lines.add(String.format("<a href=\"http://localhost:8080/Admonitus/activate/%s\">Activate now</a>", this.activationKey));
        
        return lines;
    }

    
}
