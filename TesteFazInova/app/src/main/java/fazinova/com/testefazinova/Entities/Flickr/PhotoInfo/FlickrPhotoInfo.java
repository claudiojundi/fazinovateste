package fazinova.com.testefazinova.Entities.Flickr.PhotoInfo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/24/15.
 */

@Data
public class FlickrPhotoInfo {


    @SerializedName("id")
    private String id;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private String farm;

    @SerializedName("owner")
    private FlickrPhotoInfoOwner flickrPhotoInfoOwner;

    @SerializedName("comments")
    private FlickrPhotoInfoComments flickrPhotoInfoComments;


}
