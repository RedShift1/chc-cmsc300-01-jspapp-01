package toolbox;

import java.util.ArrayList;

import models.Reminder;

public class ReminderAddedEmail implements IContentProvider
{

    private Reminder reminder;

    public ReminderAddedEmail(Reminder reminder)
    {
        this.reminder = reminder;
    }
    
    @Override
    public ArrayList<String> getLines()
    {
        ArrayList<String> lines = new ArrayList<String>();
        
        lines.add("<h2>Reminder added</h2>");
        
        lines.add(String.format("Your reminder %s has been added", this.reminder.getText()));
        
        return lines;
    }

    
}
