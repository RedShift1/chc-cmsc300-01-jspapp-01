package Controllers;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.gson.Gson;

import MVC.Controller;



/**
 * @author Alexander
 *
 */
public class Reminder extends Controller {

	@Override
	public void doRequest(String actionName, Integer id) throws Exception {
		
		EntityManager em = this.getServletContext().getEM();
		
		if(actionName.equals("get")) {
			
			Gson gson = new Gson();
			List reminderList = em.createNamedQuery("Reminder.findAll").getResultList();
			gson.toJson(reminderList);
			
			this.getRequest().setAttribute("json", gson.toString());			
			
			forward("/json.jsp");
			
		}
		
		if(actionName.equals("edit")) {
			
		}
		if(actionName.equals("delete")) {
			
		}
		
		if(actionName.equals("create")) {
			
		}
		
		
		
		
	}
	
	

}
