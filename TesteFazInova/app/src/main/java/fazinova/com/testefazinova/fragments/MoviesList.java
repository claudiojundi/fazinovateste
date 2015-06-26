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
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import fazinova.com.testefazinova.Core.FlickrConsumer;
import fazinova.com.testefazinova.Entities.Flickr.FlickrUser;
import fazinova.com.testefazinova.Entities.Flickr.PhotoInfo.FlickrPhotoInfoOwner;
import fazinova.com.testefazinova.Entities.Flickr.PhotoInfo.FlickrPhotoInfoServer;
import fazinova.com.testefazinova.Entities.Flickr.SearchPhoto.FlickrSearchPhoto;
import fazinova.com.testefazinova.Entities.Flickr.SearchPhoto.FlickrSearchServer;
import fazinova.com.testefazinova.Entities.MyProfile;
import fazinova.com.testefazinova.R;
import fazinova.com.testefazinova.Utils.InternetCheck;
import fazinova.com.testefazinova.activities.Navigation;
import fazinova.com.testefazinova.fazinova.com.testefazinova.adapters.MovieListAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MoviesList extends Fragment {

    private CircularImageView imgUserThumb;
    private TextView txtUserName;
    private TextView txtUserEmail;

    private DisplayImageOptions displayImageOptions;

    private FlickrConsumer flickrConsumer;

    private ListView listViewMovies;
    private MovieListAdapter movieListAdapter;
    private ArrayList<FlickrUser> flickrUserArrayList;


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
        imgUserThumb = (CircularImageView) v.findViewById(R.id.movieslist_img_thumb);

        listViewMovies = (ListView) v.findViewById(R.id.movieslist_listview);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).build();

        populateUserInfo();


        if (InternetCheck.isNetworkAvailable(getActivity())) {

            flickrConsume("movies");

        } else {

            Toast.makeText(getActivity(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();

            createMovieList();

            List<FlickrUser> flickrUserList = new Select().from(FlickrUser.class).where("Tag = ?", "movies").execute();

            if (flickrUserList != null) {
                movieListAdapter.setFlickrUsersList(flickrUserList);
                movieListAdapter.notifyDataSetChanged();
            }
        }


    }


    private void populateUserInfo() {

        MyProfile myProfile = new Select().from(MyProfile.class).executeSingle();

        txtUserName.setText(myProfile.getName());
        txtUserEmail.setText(myProfile.getEmail());

        ImageLoader.getInstance().displayImage(myProfile.getThumbUrl(), imgUserThumb, displayImageOptions);

    }


    public void flickrConsume(final String tag) {

        flickrUserArrayList = new ArrayList<FlickrUser>();

        if (movieListAdapter != null) {

            movieListAdapter.getFlickrUsersList().clear();
//            movieListAdapter.notifyDataSetChanged();

        }

        flickrConsumer = new FlickrConsumer();
        flickrConsumer.start(getActivity());

        flickrConsumer.flickrInterface.getPhotoSearch(tag, new Callback<FlickrSearchServer>() {
            @Override
            public void success(FlickrSearchServer flickrSearchServer, Response response) {

                Log.d("", "------ flickrSearchServer = " + flickrSearchServer);

                flickrConsumeSave(flickrSearchServer);
            }


            private void flickrConsumeSave(FlickrSearchServer flickrSearchServer) {


                for (FlickrSearchPhoto flickrSearchPhoto : flickrSearchServer.getFlickrSearchPhotos().getFlickrSearchPhoto()) {


                    FlickrUser flickrUserSelect = new Select().from(FlickrUser.class).where("PhotoId = ?", flickrSearchPhoto.getPhotoId()).executeSingle();

//                    if (flickrUserSelect == null) {

                    FlickrUser flickrUser = new FlickrUser();
                    flickrUser.setTag(tag);
                    flickrUser.setPhotoId(flickrSearchPhoto.getPhotoId());
                    flickrUser.setPhotoTitle(flickrSearchPhoto.getTitle());
                    flickrUser.setPhotoUrl("http://farm" + flickrSearchPhoto.getFarm() + ".staticflickr.com/" + flickrSearchPhoto.getServer() + "/" + flickrSearchPhoto.getPhotoId() + "_" + flickrSearchPhoto.getSecret() + ".jpg");

                    flickrConsumePhotoInfo(flickrUser, flickrUser.getPhotoId());

                    flickrUser.save();
//                    }


                }

            }

            private void flickrConsumePhotoInfo(final FlickrUser flickrUser, String photoId) {


                flickrConsumer.flickrInterface.getPhotoInfo(photoId, new Callback<FlickrPhotoInfoServer>() {


                    @Override
                    public void success(FlickrPhotoInfoServer flickrPhotoInfoServer, Response response) {

                        Log.d("", "---------- FlickrPhotoInfoServer = " + flickrPhotoInfoServer);

                        FlickrPhotoInfoOwner current = flickrPhotoInfoServer.getFlickrPhotoInfo().getFlickrPhotoInfoOwner();


                        Log.d("", "---------- FlickrPhotoInfoServer current getUserName = " + current.getUserName());

                        flickrUser.setAuthorName(current.getUserName());
                        flickrUser.setAuthorThumbUrl("http://farm" + current.getIconFarm() + ".staticflickr.com/" + current.getIconServer() + "/buddyicons/" + current.getNsId() + ".jpg");
                        flickrUser.setCommentsSize(flickrPhotoInfoServer.getFlickrPhotoInfo().getFlickrPhotoInfoComments().getCommentSize());


                        flickrUser.save();


//                        flickrUserArrayList.add(flickrUser);

                        if (movieListAdapter != null) {
                            movieListAdapter.getFlickrUsersList().add(flickrUser);
                            movieListAdapter.notifyDataSetChanged();
                        } else {
                            createMovieList();

                            movieListAdapter.getFlickrUsersList().add(flickrUser);
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d("", "-------- flickrConsumePhotoInfo failure error = " + error.getMessage());

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("", "------- flickrSearchServer failure = " + error.getMessage());

            }
        });


    }


    private void createMovieList() {

        movieListAdapter = new MovieListAdapter(getActivity());


        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(movieListAdapter);
        scaleInAnimationAdapter.setAbsListView(listViewMovies);
        listViewMovies.setAdapter(scaleInAnimationAdapter);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                MovieDetail movieDetail = new MovieDetail();

                Bundle bundle = new Bundle();
                bundle.putString("PhotoId", movieListAdapter.getFlickrUsersList().get(position).getPhotoId());

                movieDetail.setArguments(bundle);


                ((Navigation) getActivity()).changeFragment(movieDetail);


            }
        });

    }

}
