/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import toolbox.JSONResponse;
import flexjson.JSONSerializer;

/**
 *
 * @author Glenn
 */
public abstract class Controller
{
    private HttpServletRequest request;
    private HttpServletResponse response;
    private TServlet servletContext;
    
    public HttpServletRequest getRequest()
    {
        return request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public TServlet getServletContext()
    {
        return servletContext;
    }

    public void init(HttpServletRequest request, HttpServletResponse response, TServlet servletContext) throws Exception
    {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.response.setCharacterEncoding("UTF-8");
    }
    
    public void forward(String jsp) throws ServletException, IOException
    {
        System.out.println("Forwarding to " + jsp);
        RequestDispatcher rq = this.request.getRequestDispatcher(jsp);
        rq.forward(this.request, this.response);
    }
    
    
    public boolean loggedIn() throws ServletException, IOException
    {
        if(this.getRequest().getSession().getAttribute("user") != null)
        {
            return true;
        }
        
        JSONSerializer serializer = new JSONSerializer();
        this.getRequest().setAttribute("json", serializer.serialize(new JSONResponse("User must be logged in for this operation")));

        this.forward("/json.jsp");
        
        return false;
    }
    
    public User getLoggedInUser()
    {
        return (User) this.getRequest().getSession().getAttribute("user");
    }
    
    public void logIn(User user)
    {
        this.getRequest().getSession().setAttribute("user", user);
    }
    
    public void logOut()
    {
        this.getRequest().getSession().removeAttribute("user");
    }
    
    abstract public void doRequest(String actionName, Integer id) throws Exception;

}
