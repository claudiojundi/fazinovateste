package fazinova.com.testefazinova.Entities.Flickr.PhotoComments;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/25/15.
 */

@Data
public class FlickrPhotoCommentsServer {

    @SerializedName("comments")
    private FlickrPhotoComments photoComments;

    @SerializedName("stat")
    private String status;

}
