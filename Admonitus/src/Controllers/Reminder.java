package Controllers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
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
			List list = em.createNamedQuery("Reminder.findAll").getResultList();
			Gson reminderList = new Gson();
			reminderList.toJson(list);
			this.getRequest().setAttribute("reminderList", reminderList);		
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
