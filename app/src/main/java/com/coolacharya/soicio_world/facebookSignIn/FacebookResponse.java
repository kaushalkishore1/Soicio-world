package com.coolacharya.soicio_world.facebookSignIn;

/**
 * Created by kaushal on 27/11/2016.
 */
public interface FacebookResponse {
    void onFbSignInFail();

    void onFbSignInSuccess();

    void onFbProfileReceived(FacebookUser facebookUser);
}
