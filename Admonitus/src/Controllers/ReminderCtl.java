package Controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

import toolbox.JSONResponse;
import toolbox.emailConfirmation;
import models.Friend;
import models.Reminder;
import models.User;
import flexjson.JSONSerializer;
import MVC.Controller;

/**
 * @author Alexander
 *
 */
public class ReminderCtl extends Controller {

    @Override
    public void doRequest(String actionName, Integer id) throws Exception {
        
        EntityManager em = this.getServletContext().getEM();
        
        if(actionName.equals("get")) {
            if(!loggedIn())
            {
                return;
            }
            
            JSONSerializer serializer = new JSONSerializer();
            JSONResponse response;
            try
            {
                User u = (User) this.getRequest().getSession().getAttribute("user");
                response = new JSONResponse(u.getReminders());
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }
        
        if(actionName.equals("getFriends"))
        {
            if(!loggedIn())
            {
                return;
            }
            
            JSONSerializer serializer = new JSONSerializer();
            JSONResponse response;
            try
            {
                Reminder r = (Reminder) em.find(Reminder.class, id);
                response = new JSONResponse(r.getFriends());
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }
        
        if(actionName.equals("addFriend"))
        {
            if(!loggedIn())
            {
                return;
            }
            
            JSONResponse response;
            JSONSerializer serializer = new JSONSerializer();
            try
            {
                if(this.getRequest().getParameter("email") == null)
                {
                    throw new Exception("email parameter missing");
                }
                if(!(this.getRequest().getParameter("email").length() > 1))
                {
                    throw new Exception("Email cannot be empty");
                }
                
                Friend newFriend = new Friend();
                newFriend.setEmail(getRequest().getParameter("email"));
                Reminder reminder = (Reminder) em.find(Reminder.class, id);
                reminder.addFriend(newFriend);
                
                em.getTransaction().begin();
                em.merge(reminder);
                em.getTransaction().commit();
                response = new JSONResponse(newFriend);
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage().toString());
            }

            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }
        
        if(actionName.equals("deleteFriend"))
        {
            if(!loggedIn())
            {
                return;
            }
            
            JSONResponse response;
            JSONSerializer serializer = new JSONSerializer();
            try
            {
                Friend f = em.find(Friend.class, id);
                Reminder r = f.getReminder();
                em.getTransaction().begin();
                em.remove(f);
                r.removeFriend(f);
                em.getTransaction().commit();
                response = new JSONResponse(true, null, null);
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
        }

        
        if(actionName.equals("edit")) {
    	    if(!loggedIn())
            {
                return;
            }

            JSONSerializer serializer = new JSONSerializer();
            JSONResponse response;
            try
            {
                Reminder r = em.find(Reminder.class, id);
                r.setText(getRequest().getParameter("text"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                r.setDatestart(formatter.parse(this.getRequest().getParameter("startingat")));
                r.setFrequency(Byte.parseByte(getRequest().getParameter("frequency")));
                
                em.getTransaction().begin();
                em.merge(r);
                em.getTransaction().commit();
                response = new JSONResponse(r);
                
                User u = (User) this.getRequest().getSession().getAttribute("user");
                emailConfirmation conf = new emailConfirmation(u.getEmail(), "Admonitus", "Confirmation Message", "Your reminder has editted");
       		 	conf.sendEmail();
            }
            catch(Exception e)
            {
            	response = new JSONResponse(e.getMessage());
            }

            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
        }

        if(actionName.equals("delete")) {
            if(!loggedIn())
            {
                return;
            }
            
            JSONSerializer serializer = new JSONSerializer();
            JSONResponse response;
            try
            {
                User u = (User) this.getRequest().getSession().getAttribute("user");
                Reminder r = em.find(Reminder.class, id);
                u.removeReminder(r);
                em.getTransaction().begin();
                em.remove(r);
                em.getTransaction().commit();
                response = new JSONResponse(true, null, null);
                
                emailConfirmation conf = new emailConfirmation(u.getEmail(), "Admonitus", "Confirmation Message", "Your reminder has been deleted");
       		 	conf.sendEmail();
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");

        }

        if(actionName.equals("add")) {
            if(!loggedIn())
            {
                return;
            }
            
            JSONResponse response;
            JSONSerializer serializer = new JSONSerializer();
            try
            {
                if(this.getRequest().getParameter("text") == null)
                {
                    throw new Exception("text parameter missing");
                }
                if(this.getRequest().getParameter("frequency") == null)
                {
                    throw new Exception("frequency parameter missing");
                }
                if(!(this.getRequest().getParameter("text").length() > 1))
                {
                    throw new Exception("Text cannot be empty");
                }
                Reminder newReminder = new Reminder();
                newReminder.setText(getRequest().getParameter("text"));
                newReminder.setFrequency(Byte.parseByte(getRequest().getParameter("frequency")));
                newReminder.setCreationDate(new Date());
                
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                newReminder.setDatestart(formatter.parse(this.getRequest().getParameter("startingat")));
                
                User u = (User) this.getRequest().getSession().getAttribute("user");
                u.addReminder(newReminder);
                
                em.getTransaction().begin();
                em.persist(newReminder);
                em.getTransaction().commit();
                response = new JSONResponse(newReminder);
                
                emailConfirmation conf = new emailConfirmation(u.getEmail(), "Admonitus", "Confirmation Message", "Your reminder has been added");
       		 	conf.sendEmail();
                
                
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage().toString());
            }

            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }

    }

}
