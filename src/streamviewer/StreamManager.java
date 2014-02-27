/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamviewer;

import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class StreamManager
{
    private int numFavorites;
    private ArrayList favChannels;
    private StreamChannel current;
    
    public StreamManager()
    {
        numFavorites = 0;
        favChannels = new ArrayList();
    }
    
    public void createChannel(String url)
    {
        favChannels.trimToSize();
        boolean alreadyExists = false;
        for (int i = 0; i < favChannels.size(); i++)
        {
            StreamChannel temp = (StreamChannel) favChannels.get(i);
            if ((temp.getURL()).matches(url))
            {
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists)
            current = new StreamChannel(url);
    }
    
    public void createChannel(String url, boolean fav)
    {
        createChannel(url);
        if (fav)
            setFavorite(fav);
    }
    
    public boolean setFavorite(boolean setFav)
    {
        boolean changed = false;
        if (setFav && !current.isFavorite())
        {
            favChannels.add(current);
            numFavorites++;
            changed = true;
        }
        else if (!setFav && current.isFavorite())
        {
            favChannels.remove(current);
            numFavorites--;
            changed = true;
        }
        current.setFavorite(setFav);
        return changed;
    }
    
    public void removeChannel(StreamChannel channel)
    {
        favChannels.remove(channel);
        numFavorites--;
    }
    
    public void setCurrentChannel(StreamChannel channel)
    {
        current = channel;
    }
    
    public void setCurrentChannel(String channel)
    {
        favChannels.trimToSize();
        for (int i = 0; i < favChannels.size(); i++)
        {
            StreamChannel temp = (StreamChannel) favChannels.get(i);
            if ((temp.getURL()).matches(channel))
            {
                current = temp;
            }
        }
    }
    
    public StreamChannel getCurrentChannel()
    {
        return current;
    }
    
    public int getNumFavorites()
    {
        return numFavorites;
    }
    
    public ArrayList getFavChannels()
    {
        return favChannels;
    }
}
