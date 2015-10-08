package MVC;

import java.io.IOException;

import javax.servlet.ServletException;

import toolbox.JSONResponse;
import flexjson.JSONSerializer;

abstract public class JSONController extends Controller
{
    private JSONSerializer serializer;
    
    public JSONController()
    {
        this.serializer = new JSONSerializer();
    }
    
    protected void respond(JSONResponse response) throws ServletException, IOException
    {
        this.getRequest().setAttribute("json", this.serializer.serialize(response));
        forward("/json.jsp");
    }
}
