package fazinova.com.testefazinova.Entities.Flickr;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jundisassaki on 6/23/15.
 */
public class FlickrSearchServer {

    @SerializedName("photos")
    private FlickrSearchPhotos flickrSearchPhotos;

    @SerializedName("stat")
    private String status;
}
