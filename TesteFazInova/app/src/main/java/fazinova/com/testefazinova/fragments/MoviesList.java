package fazinova.com.testefazinova.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import fazinova.com.testefazinova.Core.FlickrConsumer;
import fazinova.com.testefazinova.Entities.Flickr.PhotoInfo.FlickrPhotoInfoOwner;
import fazinova.com.testefazinova.Entities.Flickr.PhotoInfo.FlickrPhotoInfoServer;
import fazinova.com.testefazinova.Entities.Flickr.SearchPhoto.FlickrSearchPhoto;
import fazinova.com.testefazinova.Entities.Flickr.SearchPhoto.FlickrSearchServer;
import fazinova.com.testefazinova.Entities.Flickr.FlickrUser;
import fazinova.com.testefazinova.Entities.MyProfile;
import fazinova.com.testefazinova.R;
import fazinova.com.testefazinova.fazinova.com.testefazinova.adapters.MovieListAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MoviesList extends Fragment {

    private ImageView imgUserThumb;
    private TextView txtUserName;
    private TextView txtUserEmail;

    private DisplayImageOptions displayImageOptions;

    private FlickrConsumer flickrConsumer;

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


    //http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg

    //pegar thumb do perfil http://farm4.staticflickr.com/3287/buddyicons/92452487@N00.jpg
    //http://farm{icon-farm}.staticflickr.com/{icon-server}/buddyicons/{nsid}.jpg


    private void populateUserInfo() {

        MyProfile myProfile = new Select().from(MyProfile.class).executeSingle();

        txtUserName.setText(myProfile.getName());
        txtUserEmail.setText(myProfile.getEmail());

        ImageLoader.getInstance().displayImage(myProfile.getThumbUrl(), imgUserThumb, displayImageOptions);

    }


    private void flickrConsume() {


        flickrConsumer = new FlickrConsumer();
        flickrConsumer.start(getActivity());

        flickrConsumer.flickrInterface.getPhotoSearch(new Callback<FlickrSearchServer>() {
            @Override
            public void success(FlickrSearchServer flickrSearchServer, Response response) {

                Log.d("", "flickrSearchServer = " + flickrSearchServer);

                flickrConsumeSave(flickrSearchServer);
            }


            private void flickrConsumeSave(FlickrSearchServer flickrSearchServer) {


                for (FlickrSearchPhoto flickrSearchPhoto : flickrSearchServer.getFlickrSearchPhotos().getFlickrSearchPhoto()) {


                    FlickrUser flickrUserSelect = new Select().from(FlickrUser.class).where("PhotoId = ?", flickrSearchPhoto.getPhotoId()).executeSingle();

                    if (flickrUserSelect == null) {

                        FlickrUser flickrUser = new FlickrUser();
                        flickrUser.setPhotoId(flickrSearchPhoto.getPhotoId());
                        flickrUser.setPhotoTitle(flickrSearchPhoto.getTitle());
                        flickrUser.setPhotoUrl("http://farm" + flickrSearchPhoto.getFarm() + ".staticflickr.com/" + flickrSearchPhoto.getServer() + "/" + flickrSearchPhoto.getPhotoId() + "_" + flickrSearchPhoto.getSecret() + ".jpg");

                        flickrConsumePhotoInfo(flickrUser, flickrUser.getPhotoId());

                        flickrUser.save();
                    }


                }

                createMovieList();

            }

            private void flickrConsumePhotoInfo(final FlickrUser flickrUser, String photoId) {

                flickrConsumer.flickrInterface.getPhotoInfo(photoId, new Callback<FlickrPhotoInfoServer>() {
                    @Override
                    public void success(FlickrPhotoInfoServer flickrPhotoInfoServer, Response response) {

                        Log.d("", "---------- FlickrPhotoInfoServer = " + flickrPhotoInfoServer);

                        FlickrPhotoInfoOwner current = flickrPhotoInfoServer.getFlickrPhotoInfo().getFlickrPhotoInfoOwner();

                        flickrUser.setAuthorName(current.getUserName());
                        flickrUser.setAuthorThumbUrl("http://farm" + current.getIconFarm() + ".staticflickr.com/" + current.getIconServer() + "/buddyicons/" + current.getNsId() + ".jpg");


                        flickrUser.save();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("", "flickrSearchServer failure = " + error.getMessage());

            }
        });


    }


    private void createMovieList() {

        List<FlickrUser> listFlickUsers = new Select().from(FlickrUser.class).execute();

        MovieListAdapter movieListAdapter = new MovieListAdapter(getActivity(), listFlickUsers);
//        listViewMovies.setAdapter(movieListAdapter);

        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(movieListAdapter);
        scaleInAnimationAdapter.setAbsListView(listViewMovies);
        listViewMovies.setAdapter(scaleInAnimationAdapter);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

}
