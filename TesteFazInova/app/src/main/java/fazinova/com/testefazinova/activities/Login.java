package fazinova.com.testefazinova.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import fazinova.com.testefazinova.Entities.MyProfile;
import fazinova.com.testefazinova.R;


public class Login extends Activity {


    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initFacebook();

        setContentView(R.layout.activity_login);
        setFacebook();

        if (isFacebookLoggedIn()) {

            changeActivity();

        } else {
            getActionBar().hide();
        }


    }

    private boolean isFacebookLoggedIn() {


        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken == null) {
            return false;
        } else {
            return true;
        }
    }

    private void initFacebook() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }


    private void setFacebook() {

        loginButton = (LoginButton) findViewById(R.id.login_btn_facebook_login);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_about_me"));


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.d("", "LoginManager onSuccess loginResult = " + loginResult);
                        Log.d("", "LoginManager onSuccess loginResult.getRecentlyDeniedPermissions() = " + loginResult.getRecentlyDeniedPermissions());
                        Log.d("", "LoginManager onSuccess loginResult.getRecentlyGrantedPermissions() = " + loginResult.getRecentlyGrantedPermissions());


                        Log.d("", "LoginManager onSuccess loginResult.getAccessToken() = " + loginResult.getAccessToken());

                        getUserInformation();

                    }


                    @Override
                    public void onCancel() {
                        Log.d("", "LoginManager onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("", "LoginManager onError exception = " + exception.getMessage());
                    }
                });

    }

    private void getUserInformation() {


        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {


                        try {


                            String userId = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");


                            Log.d("", "JSONObject = " + object.toString());


                            JSONObject picture = object.getJSONObject("picture");

                            JSONObject data = picture.getJSONObject("data");

                            String imgThumbUrl = data.getString("url");


                            MyProfile myProfile = new MyProfile();
                            myProfile.setUserId(userId);
                            myProfile.setName(name);
                            myProfile.setEmail(email);
                            myProfile.setThumbUrl(imgThumbUrl);
                            myProfile.save();

                            Log.d("", "name = " + name);
                            Log.d("", "email = " + email);
                            Log.d("", "imgThumbUrl = " + imgThumbUrl);

                            changeActivity();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture{url}");

        request.setParameters(parameters);
        request.executeAsync();

    }


    private void changeActivity() {

        Intent i = new Intent(this, Navigation.class);
        startActivity(i);

        this.finish();
        this.overridePendingTransition(0, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
