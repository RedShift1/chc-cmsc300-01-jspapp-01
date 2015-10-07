package Controllers;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Image
{
    private String filename;
    private int length;
    private InputStream content;
    
    public Image(String filename) throws FileNotFoundException
    {
        super();
        this.filename = filename;
    }
    public String getFilename()
    {
        return filename;
    }
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    public int getLength()
    {
        return length;
    }
    public void setLength(int length)
    {
        this.length = length;
    }
    public InputStream getContent()
    {
        return content;
    }
    public void setContent(InputStream content)
    {
        this.content = content;
    }
    
    
}
