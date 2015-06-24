package fazinova.com.testefazinova.Entities.Flickr.SearchPhoto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/23/15.
 */

@Data
public class FlickrSearchServer {

    @SerializedName("photos")
    private FlickrSearchPhotos flickrSearchPhotos;

    @SerializedName("stat")
    private String status;
}
