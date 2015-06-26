package fazinova.com.testefazinova.fazinova.com.testefazinova.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

import fazinova.com.testefazinova.Entities.Flickr.FlickrUser;
import fazinova.com.testefazinova.Entities.Flickr.PhotoComments.FlickrPhotoComment;
import fazinova.com.testefazinova.R;
import lombok.Getter;

/**
 * Created by jundisassaki on 6/24/15.
 */

public class CommentsListAdapter extends BaseAdapter {

    private Context context;

    @Getter
    private FlickrPhotoComment[] flickrPhotoCommentsArray;

    private DisplayImageOptions displayImageOptions;

    public CommentsListAdapter(Context context, FlickrPhotoComment[] flickrPhotoCommentsArray) {
        this.flickrPhotoCommentsArray = flickrPhotoCommentsArray;
        this.context = context;

        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).build();
    }


    @Override
    public int getCount() {
        return flickrPhotoCommentsArray.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;


        View view = convertView;


        if (view == null) {

            view = (View) LayoutInflater.from(context).inflate(R.layout.row_comments, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imgThumb = (CircularImageView) view.findViewById(R.id.row_comments_img_thumb);
            viewHolder.txtName = (TextView) view.findViewById(R.id.row_comments_txt_name);
            viewHolder.txtComment = (TextView) view.findViewById(R.id.row_comments_txt_comment);


            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        viewHolder.txtName.setText(flickrPhotoCommentsArray[position].getAuthorName());
        viewHolder.txtComment.setText(Html.fromHtml(flickrPhotoCommentsArray[position].getComment()));


        ImageLoader.getInstance().displayImage("http://farm" + flickrPhotoCommentsArray[position].getIconfarm() + ".staticflickr.com/" + flickrPhotoCommentsArray[position].getIconServer() + "/buddyicons/" + flickrPhotoCommentsArray[position].getAuthor() + ".jpg", viewHolder.imgThumb, displayImageOptions);


        return view;


    }

    private static class ViewHolder {

        private CircularImageView imgThumb;
        private TextView txtName;
        private TextView txtComment;

    }

}
