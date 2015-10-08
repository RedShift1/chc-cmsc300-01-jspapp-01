package Controllers;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.User;
import MVC.Controller;

public class ActivateCtl extends Controller
{

    @Override
    public void doRequest(String actionName, Integer id) throws Exception
    {
        
        try
        {
            EntityManager em = this.getServletContext().getEM();
            Query qry = em.createNamedQuery("User.findByActivationKey");
            qry.setParameter("activationKey", actionName);
            User user = (User) qry.getSingleResult();
            user.setActive(true);
            user.setActivationKey("");
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            this.getRequest().setAttribute("activationCompleted", true);
        }
        catch (NoResultException ex)
        {
            
        }
        catch (Exception ex)
        {
            
        }
        
        this.forward("/accountActivated.jsp");
    }

}
