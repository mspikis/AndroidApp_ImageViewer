package uk.ac.kent.mss37.imageviewer;

/**
 * Created by Lifeowner on 16/11/2017.
 */

public class ImageInfo {
    public String title;
    public int imageResource;
    public String id;
    public String owner;
    public String description;
    public String url_m;
    public String url_l;
    public String url_o;

    public String url()
    {
        if (url_l != null) {

            return url_l;
        }
        else if (url_o != null)
        {
            return url_o;
        }
        else if (url_m != null)
        {
            return url_m;
        }
        else
        {
            return null;
        }
    }

}
