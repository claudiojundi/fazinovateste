package fazinova.com.testefazinova.Entities.Flickr.PhotoInfo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/24/15.
 */

@Data
public class FlickrPhotoInfoServer {

    @SerializedName("photo")
    private FlickrPhotoInfo flickrPhotoInfo;

    @SerializedName("stat")
    private String status;

}
