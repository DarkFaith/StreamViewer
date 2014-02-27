/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamviewer;

import java.io.Serializable;

/**
 *
 * @author Kevin
 */
public class StreamChannel implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String name;
    private String url;
    private boolean favorite;
    
    public StreamChannel(String url)
    {
        this.url = url;
        favorite = false;
    }
    
    public void setURL(String newURL)
    {
        url = newURL;
    }
    
    public String getURL()
    {
        return url;
    }
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String toString()
    {
        return url;
    }
    
    protected void setFavorite(boolean b)
    {
        favorite = b;
    }
    
    public boolean isFavorite()
    {
        return favorite;
    }
}
