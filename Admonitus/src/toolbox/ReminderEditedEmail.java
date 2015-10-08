package toolbox;

import java.util.ArrayList;

import models.Reminder;

public class ReminderEditedEmail implements IContentProvider
{

    private Reminder oldReminder;
    private Reminder newReminder;

    public void setOldReminder(Reminder oldReminder)
    {
        this.oldReminder = oldReminder;
    }

    public void setNewReminder(Reminder newReminder)
    {
        this.newReminder = newReminder;
    }

    @Override
    public ArrayList<String> getLines()
    {
        ArrayList<String> lines = new ArrayList<String>();
        
        lines.add("<h2>Reminder edited</h2>");
        
        lines.add(String.format("Your reminder %s has been modified", this.newReminder.getText()));
        
        return lines;
    }
}
