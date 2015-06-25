package fazinova.com.testefazinova.fazinova.com.testefazinova.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import fazinova.com.testefazinova.Entities.Flickr.FlickrUser;
import fazinova.com.testefazinova.R;
import lombok.Getter;

/**
 * Created by jundisassaki on 6/23/15.
 */

public class MovieListAdapter extends BaseAdapter {

    private Context context;

    @Getter
    private List<FlickrUser> flickrUsersList;

    private DisplayImageOptions displayImageOptions;

    public MovieListAdapter(Context context, List<FlickrUser> flickrUserList) {
        this.flickrUsersList = flickrUserList;
        this.context = context;

        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).build();
    }


    @Override
    public int getCount() {
        return flickrUsersList.size();
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

            view = (View) LayoutInflater.from(context).inflate(R.layout.row_movielist, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imgThumb = (ImageView) view.findViewById(R.id.row_movielist_img_thumb);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.row_movielist_txt_title);
            viewHolder.txtAuthor = (TextView) view.findViewById(R.id.row_movielist_txt_author);


            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }


        viewHolder.txtTitle.setText(flickrUsersList.get(position).getPhotoTitle());
        viewHolder.txtAuthor.setText(flickrUsersList.get(position).getAuthorName());

        ImageLoader.getInstance().displayImage(flickrUsersList.get(position).getPhotoUrl(), viewHolder.imgThumb, displayImageOptions);


        return view;


    }

    private static class ViewHolder {

        private ImageView imgThumb;
        private TextView txtTitle;
        private TextView txtAuthor;

    }

}
