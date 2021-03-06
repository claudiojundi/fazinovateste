package fazinova.com.testefazinova.Core;

import fazinova.com.testefazinova.Entities.Flickr.PhotoComments.FlickrPhotoCommentsServer;
import fazinova.com.testefazinova.Entities.Flickr.PhotoInfo.FlickrPhotoInfoServer;
import fazinova.com.testefazinova.Entities.Flickr.SearchPhoto.FlickrSearchServer;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jundisassaki on 6/23/15.
 */

public interface FlickrInterface {

    @GET("/?method=flickr.photos.search&per_page=10&nojsoncallback=1&format=json&api_key=a01700f5f7a6ab1876845e9e54c6360c&sort=relevance&privacy_filter=1&content_type=7&safe_search=1")
    void getPhotoSearch(@Query("tags")String tags,Callback<FlickrSearchServer> flickrSearchServerCallback);


    @GET("/?method=flickr.photos.getInfo&api_key=a01700f5f7a6ab1876845e9e54c6360c&format=json&nojsoncallback=1")//&photo_id={photoId}
    void getPhotoInfo(@Query("photo_id") String photoId, Callback<FlickrPhotoInfoServer> flickrPhotoInfoServerCallback);


    @GET("/?method=flickr.photos.comments.getList&api_key=a01700f5f7a6ab1876845e9e54c6360c&format=json&nojsoncallback=1")
    void getPhotoComments(@Query("photo_id") String photoId, Callback<FlickrPhotoCommentsServer> flickrPhotoCommentsServer);
}
