package Controllers;

import javax.persistence.EntityManager;

import MVC.Controller;



/**
 * @author Alexander
 *
 */
public class ReminderController extends Controller {

	@Override
	public void doRequest(String actionName, Integer id) throws Exception {
		
		EntityManager em = this.getServletContext().getEM();
		
		if(actionName.equals("create")) {
			
		}
		
		
		
		
	}
	
	

}
