package fazinova.com.testefazinova.Core;

import fazinova.com.testefazinova.Entities.Flickr.PhotoInfo.FlickrPhotoInfoServer;
import fazinova.com.testefazinova.Entities.Flickr.SearchPhoto.FlickrSearchServer;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jundisassaki on 6/23/15.
 */

public interface FlickrInterface {

    @GET("/?method=flickr.photos.search&per_page=10&nojsoncallback=1&format=json&tags=movies&api_key=a01700f5f7a6ab1876845e9e54c6360c&sort=relevance&privacy_filter=1&content_type=7&safe_search=1")
    void getPhotoSearch(Callback<FlickrSearchServer> FlickrSearchServerCallback);


    @GET("/?method=flickr.photos.getInfo&api_key=a01700f5f7a6ab1876845e9e54c6360c&format=json")//&photo_id={photoId}
    void getPhotoInfo(@Query("photo_id") String photoId, Callback<FlickrPhotoInfoServer> FlickrPhotoInfoServerCallback);

}
