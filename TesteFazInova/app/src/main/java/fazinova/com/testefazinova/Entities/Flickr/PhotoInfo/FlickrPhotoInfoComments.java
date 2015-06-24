package fazinova.com.testefazinova.Entities.Flickr.PhotoInfo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/24/15.
 */

@Data
public class FlickrPhotoInfoComments {

    @SerializedName("_content")
    private String commentSize;

}
