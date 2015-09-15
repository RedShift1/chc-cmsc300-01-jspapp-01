package MVC;

import Controllers.Default;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Glenn
 */
public class TServlet extends HttpServlet
{

    public EntityManager getEM()
    {
        return (EntityManager)this.getServletContext().getAttribute("em");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        
        if(this.getServletContext().getAttribute("emf") == null)
        {
            this.getServletContext().setAttribute("emf", Persistence.createEntityManagerFactory("Admonitus"));
        }
        if(this.getServletContext().getAttribute("em") == null)
        {
            this.getServletContext().setAttribute("em", ((EntityManagerFactory)this.getServletContext().getAttribute("emf")).createEntityManager());
        }
        
        System.out.println("Path: " + req.getPathInfo());
        String rawPath = req.getPathInfo();
        String controllerName = "";
        String action = "";
        Integer id = null;
        if(rawPath != null)
        {
            String[] path = rawPath.split("/");

            if(path.length >= 2)
            {
                controllerName = "Controllers." + path[1];
                if(path.length >= 3)
                {
                    action = path[2];
                    if(path.length >= 4)
                    {
                        id = Integer.decode(path[3]);
                    }
                }
            }
        }
        
        Controller ctl = null;
        
        System.out.println("Controller: " + controllerName);
        
        if(controllerName.equals(""))
        {
            ctl = new Default();
        }
        else
        {
            try
            {
                ctl = this.makeController(controllerName);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            ctl.init(req, resp, this);
            ctl.doRequest(action, id);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private Controller makeController(String controllerName) throws Exception
    {
        Class<?> ctl = Class.forName(controllerName);
        return (Controller) ctl.newInstance();
    }

}
