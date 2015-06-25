package fazinova.com.testefazinova.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import fazinova.com.testefazinova.Entities.Flickr.FlickrUser;
import fazinova.com.testefazinova.R;


public class MovieDetail extends Fragment {

    private DisplayImageOptions displayImageOptions;


    private ImageView imgPhoto;
    private TextView txtPhotoTitle;

    private ImageView imgOwnerThumb;
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

        imgPhoto = (ImageView) v.findViewById(R.id.moviedetail_img_photo);
        txtPhotoTitle = (TextView) v.findViewById(R.id.moviedetail_txt_title);

        imgOwnerThumb = (ImageView) v.findViewById(R.id.moviedetail_img_owner_profile);
        txtOwnerName = (TextView) v.findViewById(R.id.moviedetail_txt_owner_name);

        txtCommentsSize = (TextView) v.findViewById(R.id.moviedetail_txt_comments);

        listViewComments = (ListView) v.findViewById(R.id.moviedetail_listview_comments);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).build();

        String photoId = getArguments().getString("PhotoId");


        FlickrUser flickrUser = new Select().from(FlickrUser.class).where("PhotoId = ?", photoId).executeSingle();

        ImageLoader.getInstance().displayImage(flickrUser.getPhotoUrl(), imgPhoto, displayImageOptions);
        ImageLoader.getInstance().displayImage(flickrUser.getAuthorThumbUrl(), imgOwnerThumb, displayImageOptions);

        txtPhotoTitle.setText(flickrUser.getPhotoTitle());
        txtOwnerName.setText(flickrUser.getAuthorName());
        txtCommentsSize.setText(flickrUser.getCommentsSize()+ " Comments");

    }
}
