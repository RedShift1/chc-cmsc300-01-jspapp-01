package Controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;








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
		    System.out.println(this.getRequest().getParameterMap());
			Reminder r = new Reminder();
			r.setText(getRequest().getParameter("text"));
			System.out.println(getRequest().getParameter("data"));
			r.setCreationDate(new Date());
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			r.setDatestart(formatter.parse(this.getRequest().getParameter("startingat")));

			r.setUser(em.find(User.class, 1));
			
			em.getTransaction().begin();
			em.persist(r);
			em.getTransaction().commit();
		}
		

	}
	
	

}
