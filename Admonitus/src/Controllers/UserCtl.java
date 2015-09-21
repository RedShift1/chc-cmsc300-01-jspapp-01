package Controllers;

import java.util.List;

import javax.persistence.EntityManager;

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

            JSONSerializer serializer = new JSONSerializer();
            this.getRequest().setAttribute("json",
                    serializer.serialize(list));

            forward("/json.jsp");

        }

    }

}
