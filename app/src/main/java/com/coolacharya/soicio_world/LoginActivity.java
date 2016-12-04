package com.coolacharya.soicio_world;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.coolacharya.soicio_world.facebookSignIn.FacebookHelper;
import com.coolacharya.soicio_world.facebookSignIn.FacebookResponse;
import com.coolacharya.soicio_world.facebookSignIn.FacebookUser;
import com.coolacharya.soicio_world.googleSignIn.GooglePlusSignInHelper;
import com.coolacharya.soicio_world.googleSignIn.GoogleResponseListener;
import com.coolacharya.soicio_world.instagramSignIn.InstagramHelper;
import com.coolacharya.soicio_world.instagramSignIn.InstagramResponse;
import com.coolacharya.soicio_world.instagramSignIn.InstagramUser;
import com.coolacharya.soicio_world.linkedInSiginIn.LinkedinDialog;
import com.coolacharya.soicio_world.twitterSignIn.TwitterHelper;
import com.coolacharya.soicio_world.twitterSignIn.TwitterResponse;
import com.coolacharya.soicio_world.twitterSignIn.TwitterUser;
import com.facebook.FacebookSdk;
import com.google.android.gms.plus.model.people.Person;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kaushal on 27/11/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleResponseListener, FacebookResponse,TwitterResponse,InstagramResponse {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.btn_facebook)
    com.k2infosoft.social_login.FacebookButton _facebook;
    @BindView(R.id.btn_google_plus)
    com.k2infosoft.social_login.GooglePlusButton _google_plus;
    @BindView(R.id.btn_linkedin)
    com.k2infosoft.social_login.LinkedInButton _linkedin;
    @BindView(R.id.btn_twitter)
    com.k2infosoft.social_login.TwitterButton _twitter;
    @BindView(R.id.btn_instagram)
    com.k2infosoft.social_login.InstagramButton _instagram;
    @BindView(R.id.btn_pinterest)
    com.k2infosoft.social_login.PinterestButton _pinterest;

    private PDKClient pdkClient;

    private static final String appID = "4871370872637636072";


    public static String LIN_FIRST_NAME = "";
    public static String LIN_LAST_NAME = "";
    public static LinkedInApiClientFactory factory = LinkedInApiClientFactory
            .newInstance(LinkedinDialog.LINKEDIN_CONSUMER_KEY,
                    LinkedinDialog.LINKEDIN_CONSUMER_SECRET);
    public static LinkedInApiClient client;
    private TwitterHelper mTwitterHelper;
    private GooglePlusSignInHelper mGHelper;
    private FacebookHelper mFbHelper;
    private InstagramHelper mInstagramHelper;
    private PDKUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mFbHelper = new FacebookHelper(this,
                "id,name,email,gender,birthday,picture,cover",
                this);
        mTwitterHelper = new TwitterHelper(R.string.twitter_api_key, R.string.twitter_secrate_key, this, this);

        mInstagramHelper = new InstagramHelper(
                getResources().getString(R.string.instagram_client_id),
                getResources().getString(R.string.instagram_client_secret),
                getResources().getString(R.string.instagram_callback_url), this, this);

        pdkClient = PDKClient.configureInstance(this, appID);
        pdkClient.onConnect(this);

       /* pdkClient.configureInstance(this, appID);
        pdkClient.getInstance().onConnect(this);*/
        pdkClient.setDebugMode(true);

        _google_plus.setOnClickListener(this);
        _linkedin.setOnClickListener(this);
        _facebook.setOnClickListener(this);
        _twitter.setOnClickListener(this);
        _instagram.setOnClickListener(this);
        _pinterest.setOnClickListener(this);

        mGHelper = new GooglePlusSignInHelper(this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_facebook:
                mFbHelper.performSignIn(this);
                break;
            case R.id.btn_google_plus:
                mGHelper.performSignIn();
                break;
            case R.id.btn_linkedin:
                checklinkedinlogin();
                break;
            case R.id.btn_twitter:
                mTwitterHelper.performSignIn();
                break;
            case R.id.btn_instagram:
                mInstagramHelper.performSignIn();
                break;
            case R.id.btn_pinterest:
                onLogin();
                break;
        }
    }

    private void checklinkedinlogin() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ProgressDialog progressDialog = new ProgressDialog(this);//.show(LinkedInSampleActivity.this, null, "Loadong...");

        LinkedinDialog d = new LinkedinDialog(this, progressDialog);
        d.show();


        //set call back listener to get oauth_verifier value
        d.setVerifierListener(new LinkedinDialog.OnVerifyListener() {
            @Override
            public void onVerify(String verifier) {
                try {
                    Log.i("LinkedinSample", "verifier: " + verifier);

                    LinkedInAccessToken accessToken = LinkedinDialog.oAuthService.getOAuthAccessToken(LinkedinDialog.liToken, verifier);
                    LinkedinDialog.factory.createLinkedInApiClient(accessToken);

                    Log.i("LinkedinSample", "ln_access_token: " + accessToken.getToken());
                    Log.i("LinkedinSample", "ln_access_token: " + accessToken.getTokenSecret());


                    LinkedinDialog.factory.createLinkedInApiClient(accessToken);
                    client = factory.createLinkedInApiClient(accessToken);

                    com.google.code.linkedinapi.schema.Person profile = client.getProfileForCurrentUser();

                    Log.d("MyApp", " " + profile.getFirstName() + " " + profile.getLastName() + " " + profile.getId() + " " + profile.getPictureUrl() + " " + profile.getPublicProfileUrl() + " " + profile.getLanguages() + "  " + profile.getLocation());
                    LIN_FIRST_NAME = profile.getLastName();
                    LIN_LAST_NAME = profile.getId();
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } catch (Exception e) {
                    Log.i("LinkedinSample", "error to get verifier");
                    e.printStackTrace();
                }
            }
        });

        //set progress dialog
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGHelper.disconnectApiClient();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        //handle permissions
        mGHelper.onPermissionResult(requestCode, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGHelper.onActivityResult(requestCode, resultCode, data);
        mFbHelper.onActivityResult(requestCode, resultCode, data);
        mTwitterHelper.onActivityResult(requestCode, resultCode, data);
        pdkClient.onOauthResponse(requestCode, resultCode,
                data);
    }


    private void onLogin() {
        List scopes = new ArrayList<String>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_RELATIONSHIPS);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_RELATIONSHIPS);

        pdkClient.login(this, scopes, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                onLoginSuccess();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
            }
        });
    }

    @Override
    public void onGSignInFail() {
        Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGSignInSuccess(Person personData) {
        Toast.makeText(this, "Google+ user data: full name=" + personData, Toast.LENGTH_SHORT).show();
       /* Log.d("Person display name: ", personData.getDisplayName() + "");
        Log.d("Person birth date: ", personData.getBirthday() + "");
        Log.d("Person gender: ", personData.getGender() + "");
        Log.d("Person name: ", personData.getName() + "");
        Log.d("Person id: ", personData.getImage() + "");*/
    }

    @Override
    public void onFbSignInFail() {
        Toast.makeText(this, "Facebook sign in failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInSuccess() {
        Toast.makeText(this, "Facebook sign in success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbProfileReceived(FacebookUser facebookUser) {
        Toast.makeText(this, "Facebook user data: name= " + facebookUser.name + " email= " + facebookUser.email + "facebookID=" + facebookUser.facebookID + "profilePic=" + facebookUser.profilePic + "about=" + facebookUser.about + "gender=" + facebookUser.gender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTwitterError() {
        Toast.makeText(this, "Twitter sign in failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTwitterSignIn(@NonNull String userId, @NonNull String userName) {
        Toast.makeText(this, " User id: " + userId + "\n user name" + userName, Toast.LENGTH_SHORT).show();
        Log.d("Person display id: ", userId + "");
        Log.d("Person birth username: ", userName + "");
    }

    @Override
    public void onTwitterProfileReceived(TwitterUser user) {
        Toast.makeText(this, "Twitter user data: name= " + user.name + " email= " + user.email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInstagramSignInSuccess(InstagramUser user) {
        Toast.makeText(this, "Instagram user data: full name name=" + user.getFull_name() + " user name=" + user.getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInstagramSignInFail(String error) {
        Toast.makeText(this, "Instagram sign in failed", Toast.LENGTH_SHORT).show();
    }

    private void onLoginSuccess() {
       Toast.makeText(this, " User id: " + user.getUid() + "\n user name" +user.getFirstName()+""+user.getLastName(), Toast.LENGTH_SHORT).show();
        Log.d("Person display id: ", user.getUid() + "");
        Log.d("Person birth username: ",user.getFirstName() + "");
       /* Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();*/
    }
}
