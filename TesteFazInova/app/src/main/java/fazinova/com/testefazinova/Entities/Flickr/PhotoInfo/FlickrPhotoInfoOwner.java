package fazinova.com.testefazinova.Entities.Flickr.PhotoInfo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by jundisassaki on 6/24/15.
 */

@Data
public class FlickrPhotoInfoOwner {

    @SerializedName("nsid")
    private String nsId;

    @SerializedName("username")
    private String userName;


    @SerializedName("realname")
    private String realName;

    @SerializedName("iconserver")
    private String iconServer;

    @SerializedName("iconfarm")
    private String iconFarm;


}
