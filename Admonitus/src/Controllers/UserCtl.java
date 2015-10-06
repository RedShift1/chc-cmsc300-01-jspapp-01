package Controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import toolbox.JSONResponse;
import toolbox.Sha;
import toolbox.emailConfirmation;
import models.User;
import flexjson.JSONSerializer;
import MVC.Controller;

/**
 * @author Alexander
 *
 */
public class UserCtl extends Controller {

    @Override
    public void doRequest(String actionName, Integer id) throws Exception {

        EntityManager em = this.getServletContext().getEM();
        JSONSerializer serializer = new JSONSerializer();

        // if(actionName.equals("create")) {
        //
        // User u = new User();
        //
        // // u.setEmail(getRequest().getParameter("email"));
        //
        // em.getTransaction().begin();
        // em.persist(u);
        // em.getTransaction().commit();

        //
        if (actionName.equals("get")) {
            List<?> list = em.createNamedQuery("User.findAll").getResultList();

            
            this.getRequest().setAttribute("json",
                    serializer.serialize(list));

            forward("/json.jsp");

        }
        
        if(actionName.equals("isLoggedIn"))
        {
            JSONResponse response;
            if(this.getRequest().getSession().getAttribute("user") == null)
            {
                response = new JSONResponse(true, null, false);
            }
            else
            {
                response = new JSONResponse(true, null, this.getRequest().getSession().getAttribute("user"));
            }
            
            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
        }
        
        if(actionName.equals("logout"))
        {
            JSONResponse response;
            try
            {
                this.getRequest().getSession().removeAttribute("user");
                response = new JSONResponse(true, null, null);
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            
            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
        }
        
        if(actionName.equals("register"))
        {
            JSONResponse response;
            
            if(this.getRequest().getParameter("password1") == null || this.getRequest().getParameter("password2") == null)
            {
                response = new JSONResponse("No password1 or password2 supplied");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            
            if(!(this.getRequest().getParameter("password1").length() > 0))
            {
                response = new JSONResponse("Password cannot be empty");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            
            if(!this.getRequest().getParameter("password1").equals(this.getRequest().getParameter("password2")))
            {
                response = new JSONResponse("Passwords do not match");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            if(this.getRequest().getParameter("email") == null)
            {
                response = new JSONResponse("No email address supplied");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            if(!(this.getRequest().getParameter("email").length() > 0))
            {
                response = new JSONResponse("No email address supplied");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }

            try
            {

                User newUser = new User();
                newUser.setEmail(this.getRequest().getParameter("email"));
                newUser.setPassword(Sha.hash256(this.getRequest().getParameter("password1")));
                newUser.setCreationDate(new Date());
                
                em.getTransaction().begin();
                em.persist(newUser);
                em.getTransaction().commit();
                
                response = new JSONResponse(true, null, null);
                
                User u = (User) this.getRequest().getSession().getAttribute("user");
                emailConfirmation conf = new emailConfirmation(u.getEmail(), "Admonitus", "Confirmation Message", "Your account has been succesfully created, thank you");
       		 	conf.sendEmail();
            }
            catch (Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }

            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
        }
        
        if(actionName.equals("login"))
        {
            JSONResponse response = null;
            try
            {
                Query qry = em.createNamedQuery("User.findByEmailAndPassword");
                qry.setParameter("email", this.getRequest().getParameter("email"));
                qry.setParameter("password", Sha.hash256(this.getRequest().getParameter("password")));
                User user = (User) qry.getSingleResult();
                this.getRequest().getSession().setAttribute("user", user);
                response = new JSONResponse(user);
            }
            catch (NoResultException ex)
            {
                response = new JSONResponse("Invalid email address or password");
            }
            catch (Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            
            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }

    }

}
