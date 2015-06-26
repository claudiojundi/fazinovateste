package fazinova.com.testefazinova.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.facebook.login.LoginManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import fazinova.com.testefazinova.R;
import fazinova.com.testefazinova.fragments.MoviesList;


public class Navigation extends Activity implements SearchView.OnQueryTextListener {


    private RelativeLayout mainLayout;
    private SearchView mSearchView;

    private Fragment currentFragment;
    private Fragment oldFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        mainLayout = (RelativeLayout) findViewById(R.id.navigation_mainlayout);

//

        initImgLoaderConfig();

        currentFragment = new MoviesList();

        changeFragment(currentFragment);


    }

//    public ActionBar getActionbarActivity() {
//        return getSupportActionBar();
//    }


    private void initImgLoaderConfig() {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

    }

    public void changeFragment(Fragment fragment) {

        if (fragment != null) {

            oldFragment = currentFragment;
            this.currentFragment = fragment;

            getFragmentManager().beginTransaction().add(R.id.navigation_mainlayout, fragment).addToBackStack(null).commit();


        }

    }


    @Override
    public void onBackPressed() {
//

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
            currentFragment = oldFragment;
        } else {
            super.onBackPressed();
        }

        Log.d("", "getFragmentManager().getBackStackEntryCount() = " + getFragmentManager().getBackStackEntryCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_navigation, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

//        mSearchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);

//        mSearchView = (SearchView) getA

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(this);

        MenuItem logOff = menu.findItem(R.id.menu_logout);

        logOff.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                logOut();

                return false;
            }


        });

        return super.onCreateOptionsMenu(menu);
    }


    private void logOut() {

        LoginManager.getInstance().logOut();

        Intent i = new Intent(this, Login.class);
        startActivity(i);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_search) {
            mSearchView.setIconified(false);
            return true;
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Log.d("", "----- onQueryTextSubmit -----");


        if (currentFragment.getClass().equals(MoviesList.class)) {

            Log.d("", "pesquisa isso = " + query);

            MoviesList fragment = (MoviesList) currentFragment;

            fragment.flickrConsume(query);

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
