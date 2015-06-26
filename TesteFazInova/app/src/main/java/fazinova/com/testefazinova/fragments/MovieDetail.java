package fazinova.com.testefazinova.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import fazinova.com.testefazinova.Core.FlickrConsumer;
import fazinova.com.testefazinova.Entities.Flickr.FlickrUser;
import fazinova.com.testefazinova.Entities.Flickr.PhotoComments.FlickrPhotoComment;
import fazinova.com.testefazinova.Entities.Flickr.PhotoComments.FlickrPhotoCommentsServer;
import fazinova.com.testefazinova.R;
import fazinova.com.testefazinova.activities.Navigation;
import fazinova.com.testefazinova.fazinova.com.testefazinova.adapters.CommentsListAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MovieDetail extends Fragment {

    private DisplayImageOptions displayImageOptions;


    private ImageView imgPhoto;
    private TextView txtPhotoTitle;

    private CircularImageView imgOwnerThumb;
    private TextView txtOwnerName;

    private TextView txtCommentsSize;

    private ListView listViewComments;


    public MovieDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


//        ((Navigation) getActivity()).getActionbarActivity().hide();

        getActivity().getActionBar().hide();

        imgPhoto = (ImageView) v.findViewById(R.id.moviedetail_img_photo);
        txtPhotoTitle = (TextView) v.findViewById(R.id.moviedetail_txt_title);

        imgOwnerThumb = (CircularImageView) v.findViewById(R.id.moviedetail_img_owner_profile);
        txtOwnerName = (TextView) v.findViewById(R.id.moviedetail_txt_owner_name);

        txtCommentsSize = (TextView) v.findViewById(R.id.moviedetail_txt_comments);

        listViewComments = (ListView) v.findViewById(R.id.moviedetail_listview_comments);

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().getActionBar().show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).build();

        String photoId = getArguments().getString("PhotoId");

        FlickrUser flickrUser = new Select().from(FlickrUser.class).where("PhotoId = ?", photoId).executeSingle();

        populateInfo(flickrUser);

        consumeCommentsService(photoId);

    }


    private void populateInfo(FlickrUser flickrUser) {

        ImageLoader.getInstance().displayImage(flickrUser.getPhotoUrl(), imgPhoto, displayImageOptions);
        ImageLoader.getInstance().displayImage(flickrUser.getAuthorThumbUrl(), imgOwnerThumb, displayImageOptions);

        txtPhotoTitle.setText(flickrUser.getPhotoTitle());
        txtOwnerName.setText(flickrUser.getAuthorName());
        txtCommentsSize.setText(flickrUser.getCommentsSize() + " Comments");

    }


    private void consumeCommentsService(String photoId) {

        FlickrConsumer flickrConsumer = new FlickrConsumer();
        flickrConsumer.start(getActivity());

        flickrConsumer.flickrInterface.getPhotoComments(photoId, new Callback<FlickrPhotoCommentsServer>() {
            @Override
            public void success(FlickrPhotoCommentsServer flickrPhotoCommentsServer, Response response) {

                populateCommentsList(flickrPhotoCommentsServer.getPhotoComments().getPhotoComments());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void populateCommentsList(FlickrPhotoComment[] flickrPhotoComments) {

        if (flickrPhotoComments != null) {
            CommentsListAdapter commentsListAdapter = new CommentsListAdapter(getActivity(), flickrPhotoComments);

            ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(commentsListAdapter);
            scaleInAnimationAdapter.setAbsListView(listViewComments);
            listViewComments.setAdapter(scaleInAnimationAdapter);
        }

    }

}
