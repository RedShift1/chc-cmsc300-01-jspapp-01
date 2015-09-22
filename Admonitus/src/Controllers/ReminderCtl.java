package Controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

import toolbox.JSONResponse;
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
        
        if(actionName.equals("edit")) {
            Reminder r = em.find(Reminder.class, id);
            r.setText(getRequest().getParameter("text"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            r.setDatestart(formatter.parse(this.getRequest().getParameter("startingat")));
            r.setFrequency(Byte.parseByte(getRequest().getParameter("frequency")));
            
            
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
                System.out.println(this.getRequest().getParameterMap());
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
                response = new JSONResponse(true, null, null);
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }

            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }
        

    }
    
    public boolean loggedIn() throws ServletException, IOException
    {
        if(this.getRequest().getSession().getAttribute("user") != null)
        {
            return true;
        }
        
        JSONSerializer serializer = new JSONSerializer();
        this.getRequest().setAttribute("json", serializer.serialize(new JSONResponse("User must be logged in for this operation")));

        this.forward("/json.jsp");
        
        return false;
    }
    
    

}
