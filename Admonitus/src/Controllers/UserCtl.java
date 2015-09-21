package Controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import toolbox.JSONResponse;
import toolbox.Sha;
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
