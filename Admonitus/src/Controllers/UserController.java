package Controllers;

import javax.persistence.EntityManager;

import models.User;
import MVC.Controller;

/**
 * @author Alexander
 *
 */
public class UserController extends Controller {

	@Override
	public void doRequest(String actionName, Integer id) throws Exception {

		EntityManager em = this.getServletContext().getEM();
		
		if(actionName.equals("create")) {
			
		User u = new User();
		
		u.setEmail(getRequest().getParameter("email"));
		
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		
		if(actionName.equals("get")) {
			
			em.createNamedQuery("User.findAll").getResultList();	
			
		}
		
		}
		
		
		
		
		
	}

}
