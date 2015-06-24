package fazinova.com.testefazinova.Core;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by jundisassaki on 6/23/15.
 */

public class FlickrConsumer {

    private OnLoadListener onLoadListener;

    public interface OnLoadListener {
        public void onComplete();

        public void onError();
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }


    public FlickrInterface flickrInterface;


    private Context context;
    private String url = "https://api.flickr.com/services/rest";


    public void start(final Context context) {

        this.context = context;


        Log.d("", "------------------- WebserviceConsumer START ------------------------");

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(" x-li-format", "json");
                request.addHeader("Content-Type", "application/json");

            }
        };


        RestAdapter restAdapter = null;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();


        flickrInterface = restAdapter.create(FlickrInterface.class);


    }

}
