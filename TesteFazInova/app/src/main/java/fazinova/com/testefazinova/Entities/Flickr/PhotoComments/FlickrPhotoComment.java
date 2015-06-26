package fazinova.com.testefazinova.Entities.Flickr.PhotoComments;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/25/15.
 */

@Data
public class FlickrPhotoComment {

    @SerializedName("id")
    private String photoId;

    @SerializedName("author")
    private String author;

    @SerializedName("authorname")
    private String authorName;

    @SerializedName("iconserver")
    private String iconServer;

    @SerializedName("iconfarm")
    private String iconfarm;

    @SerializedName("datecreate")
    private String datecreate;

    @SerializedName("_content")
    private String comment;


}
