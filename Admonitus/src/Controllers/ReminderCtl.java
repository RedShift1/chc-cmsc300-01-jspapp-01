package Controllers;

import java.util.List;

import javax.persistence.EntityManager;



import models.Reminder;
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
			List<?> list = em.createNamedQuery("Reminder.findAll").getResultList();
			
			JSONSerializer serializer = new JSONSerializer();
			this.getRequest().setAttribute("json", serializer.serialize(list));

			forward("/json.jsp");
			
		}
		
		if(actionName.equals("edit")) {
			
		}
		if(actionName.equals("delete")) {
			
		}
		if(actionName.equals("add")) {
			
			Reminder r = new Reminder();
			r.set
			
			
			
			
		}
		
		
		
		
	}
	
	

}
