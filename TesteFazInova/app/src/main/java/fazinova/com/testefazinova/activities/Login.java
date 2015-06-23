package fazinova.com.testefazinova.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import fazinova.com.testefazinova.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class Login extends RoboActivity {

    @InjectView(R.id.login_btn_facebook_login)
    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            Log.d("","package name = " + getPackageName());



            PackageInfo info = getPackageManager().getPackageInfo("fazinova.com.testefazinova",PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

            Log.d("","NameNotFoundException e = " + e.getMessage());

        } catch (NoSuchAlgorithmException e) {
            Log.d("","NoSuchAlgorithmException e = " + e.getMessage());
        }


        initFacebook();

        setContentView(R.layout.activity_login);



        setFacebook();

    }

    private void initFacebook() {

        FacebookSdk.sdkInitialize(getApplicationContext());

    }


    private void setFacebook() {

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_about_me"));

        callbackManager = CallbackManager.Factory.create();

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

//                        Log.d("", "getUserInformation onCompleted object = " + object);
//                        Log.d("", "getUserInformation onCompleted response = " + response);


                        try {

                            String name = object.getString("name");
                            String email = object.getString("email");

                            Log.d("","name = " + name);
                            Log.d("","email = " + email);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");

        request.setParameters(parameters);
        request.executeAsync();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
