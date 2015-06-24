package fazinova.com.testefazinova.Entities.Flickr.SearchPhoto;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/23/15.
 */

@Data
@Table(name = "FlickrSearchPhoto")
public class FlickrSearchPhoto extends Model {


    @SerializedName("id")
    @Column(name = "PhotoId")
    private String photoId;

    @SerializedName("owner")
    @Column(name = "Owner")
    private String owner;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;


    @SerializedName("farm")
    private String farm;

    @SerializedName("title")
    @Column(name = "Title")
    private String title;

    @SerializedName("ispublic")
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;


}
