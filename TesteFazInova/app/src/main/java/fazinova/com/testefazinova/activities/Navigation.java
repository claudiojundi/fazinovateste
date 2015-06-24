package fazinova.com.testefazinova.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import fazinova.com.testefazinova.R;
import fazinova.com.testefazinova.fragments.MoviesList;


public class Navigation extends Activity {


    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        mainLayout = (RelativeLayout) findViewById(R.id.navigation_mainlayout);

        initImgLoaderConfig();

        addHome();

    }

    private void initImgLoaderConfig() {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

    }


    private void addHome() {

        changeFragment(new MoviesList());

    }


    public void changeFragment(Fragment fragment) {

        if (fragment != null) {

            getFragmentManager().beginTransaction().replace(R.id.navigation_mainlayout, fragment).addToBackStack(null).commit();

        }

    }


}
