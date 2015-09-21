package toolbox;

/**
 * Standardized JSON response. Use this class to construct a JSON response to a
 * client.
 * @author Glenn
 *
 */
public class JSONResponse
{
    /**
     * Indicate whether or not the requested operation has succeeded
     */
    public boolean success;
    /**
     * If the operation failed, this any error messages should be contained
     * here. 
     */
    public String error;
    /**
     * Data the operation returns, if any
     */
    public Object data;

    public JSONResponse(boolean success, String error, Object data)
    {
        super();
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public JSONResponse(String error)
    {
        super();
        this.success = false;
        this.error = error;
    }
}
