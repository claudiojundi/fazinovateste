package fazinova.com.testefazinova.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import fazinova.com.testefazinova.Core.FlickrConsumer;
import fazinova.com.testefazinova.Core.FlickrInterface;
import fazinova.com.testefazinova.Entities.Flickr.FlickrSearchServer;
import fazinova.com.testefazinova.Entities.MyProfile;
import fazinova.com.testefazinova.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MoviesList extends Fragment {

    private ImageView imgUserThumb;
    private TextView txtUserName;
    private TextView txtUserEmail;

    private DisplayImageOptions displayImageOptions;

    private ListView listViewMovies;

    public MoviesList() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movies_list, container, false);

        txtUserName = (TextView) v.findViewById(R.id.movieslist_txt_name);
        txtUserEmail = (TextView) v.findViewById(R.id.movieslist_txt_email);
        imgUserThumb = (ImageView) v.findViewById(R.id.movieslist_img_thumb);

        listViewMovies = (ListView) v.findViewById(R.id.movieslist_listview);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).build();

        populateUserInfo();

        flickrConsume();
    }


//    https://api.flickr.com/services/rest/?method=flickr.photos.search&per_page=10&nojsoncallback=1&format=json&tags=movies&api_key=a01700f5f7a6ab1876845e9e54c6360c&sort=relevance&privacy_filter=1&content_type=7&safe_search=1
    //https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=a01700f5f7a6ab1876845e9e54c6360c&photo_id=19086614072


    //pegar thumb do perfil http://farm4.staticflickr.com/3287/buddyicons/92452487@N00.jpg
    //http://farm{icon-farm}.staticflickr.com/{icon-server}/buddyicons/{nsid}.jpg


    private void populateUserInfo() {

        MyProfile myProfile = new Select().from(MyProfile.class).executeSingle();

        txtUserName.setText(myProfile.getName());
        txtUserEmail.setText(myProfile.getEmail());

        ImageLoader.getInstance().displayImage(myProfile.getThumbUrl(), imgUserThumb, displayImageOptions);

    }


    private void flickrConsume() {


        FlickrConsumer flickrConsumer = new FlickrConsumer();
        flickrConsumer.start(getActivity());

        flickrConsumer.flickrInterface.getPhotoSearch(new Callback<FlickrSearchServer>() {
            @Override
            public void success(FlickrSearchServer flickrSearchServer, Response response) {

                Log.d("", "flickrSearchServer = " + flickrSearchServer);

            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("", "flickrSearchServer failure = " + error.getMessage());

            }
        });


    }

    private void createMovieList() {


    }

}
