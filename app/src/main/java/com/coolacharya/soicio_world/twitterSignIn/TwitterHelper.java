package com.coolacharya.soicio_world.twitterSignIn;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

/**
 * Created by multidots on 6/17/2016.<p>
 * This class will handle all the twitter sign in related functionality.
 */
public class TwitterHelper {
    private TwitterAuthClient mAuthClient;

    @NonNull
    private final Activity mActivity;

    @NonNull
    private final TwitterResponse mListener;

    /**
     * Public constructor. This will initialize twitter sdk.
     *
     * @param twitterApiKey     twitter api key
     * @param twitterSecreteKey twitter secrete key
     * @param response          {@link TwitterResponse} response listener.
     * @param context           instance of the caller.
     */
    public TwitterHelper(@StringRes final int twitterApiKey,
                         @StringRes final int twitterSecreteKey,
                         @NonNull TwitterResponse response,
                         @NonNull Activity context) {

        //noinspection ConstantConditions
        if (response == null) throw new IllegalArgumentException("TwitterResponse cannot be null.");

        mActivity = context;
        mListener = response;

        //initialize sdk
        TwitterAuthConfig authConfig = new TwitterAuthConfig(context.getResources().getString(twitterApiKey),
                context.getResources().getString(twitterSecreteKey));
        Fabric.with(context, new Twitter(authConfig));

        mAuthClient = new TwitterAuthClient();
    }

    /**
     * Result callback.
     */
    private Callback<TwitterSession> mCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            TwitterSession session = result.data;
            mListener.onTwitterSignIn(session.getUserName(), session.getUserId() + " ");

            //load user data.
            getUserData();
        }

        @Override
        public void failure(TwitterException exception) {
            mListener.onTwitterError();
        }
    };

    /**
     * Perform twitter sign in. Call this method when user clicks on "Login with Twitter" button.
     */
    public void performSignIn() {
        mAuthClient.authorize(mActivity, mCallback);
    }

    /**
     * This method handles onActivityResult callbacks from fragment or activity.
     *
     * @param requestCode request code received.
     * @param resultCode  result code received.
     * @param data        Data intent.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mAuthClient != null)
            mAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Load twitter user profile.
     */
    private void getUserData() {
        Twitter.getApiClient().getAccountService().verifyCredentials(true, false, new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                //parse the response
                TwitterUser user = new TwitterUser();
                user.name = userResult.data.name;
                user.email = userResult.data.email;
                user.description = userResult.data.description;
                user.pictureUrl = userResult.data.profileImageUrl;
                user.bannerUrl = userResult.data.profileBannerUrl;
                user.language = userResult.data.lang;
                user.id = userResult.data.id;

                mListener.onTwitterProfileReceived(user);
            }

            @Override
            public void failure(TwitterException e) {
                mListener.onTwitterError();
            }
        });
    }
}
