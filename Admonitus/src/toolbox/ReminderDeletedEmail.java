package toolbox;

import java.util.ArrayList;

import models.Reminder;

public class ReminderDeletedEmail implements IContentProvider
{

    private Reminder reminder;

    public ReminderDeletedEmail(Reminder reminder)
    {
        this.reminder = reminder;
    }
    
    @Override
    public ArrayList<String> getLines()
    {
        ArrayList<String> lines = new ArrayList<String>();
        
        lines.add("<h2>Reminder deleted</h2>");
        
        lines.add(String.format("Your reminder %s has been deleted", this.reminder.getText()));
        
        return lines;
    }

    
}
