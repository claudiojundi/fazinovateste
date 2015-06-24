package fazinova.com.testefazinova.Entities.Flickr;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.Data;

/**
 * Created by jundisassaki on 6/23/15.
 */

@Data
@Table(name = "FlickrUser")
public class FlickrUser extends Model {

    @Column(name = "AuthorName")
    private String authorName;

    @Column(name = "PhotoId")
    private String photoId;

    @Column(name = "PhotoTitle")
    private String photoTitle;

    @Column(name = "PhotoUrl")
    private String photoUrl;

    @Column(name = "AuthorThumbUrl")
    private String authorThumbUrl;

}
