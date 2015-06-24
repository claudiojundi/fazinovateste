package fazinova.com.testefazinova.Entities.Flickr;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jundisassaki on 6/23/15.
 */
public class FlickrSearchPhoto {


    @SerializedName("id")
    private String photoId;

    @SerializedName("owner")
    private String owner;

    @SerializedName("secret")
    private String secret;

    @SerializedName("farm")
    private String farm;

    @SerializedName("title")
    private String title;

    @SerializedName("ispublic")
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;

}
