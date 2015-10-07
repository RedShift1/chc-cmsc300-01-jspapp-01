/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import MVC.Controller;

/**
 *
 * @author Glenn
 */
public class Default extends Controller
{

    @Override
    public void doRequest(String actionName, Integer id) throws Exception
    {        
        this.forward("/default.jsp");
    }

}
