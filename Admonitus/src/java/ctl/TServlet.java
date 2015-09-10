/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctl;

import Controllers.Default;
import MVC.Controller;
import java.io.IOException;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("Path: " + req.getPathInfo());
        String rawPath = req.getPathInfo();
        String[] path = rawPath.split("/");
        String controllerName = "";
        String action = "";
        Integer id = null;
        
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
