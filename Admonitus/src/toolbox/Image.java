package toolbox;

import java.io.InputStream;

public class Image
{
    private String filename;
    private int size;
    private InputStream content;

    public Image(String filename, InputStream content, int size)
    {
        super();
        this.filename   = filename;
        this.content    = content;
        this.size       = size;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public int getSize()
    {
        return size;
    }

    public void getSize(int size)
    {
        this.size = size;
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
