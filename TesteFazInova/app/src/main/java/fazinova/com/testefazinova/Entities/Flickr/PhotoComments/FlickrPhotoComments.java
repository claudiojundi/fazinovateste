package fazinova.com.testefazinova.Entities.Flickr.PhotoComments;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/25/15.
 */

@Data
public class FlickrPhotoComments {

    @SerializedName("photo_id")
    private String photoId;

    @SerializedName("comment")
    private FlickrPhotoComment[] photoComments;

}
