package com.coolacharya.soicio_world.instagramSignIn;

/**
 * Callback operation to receive status of signin with instagram operation
 * Created by kaushal on 27/11/2016.
 */

public interface InstagramResponse {

    /**
     * If instagram successfully login
     *
     * @param user user data from instagram
     */
    void onInstagramSignInSuccess(InstagramUser user);

    /**
     * If error occurs while login to instagram
     *
     * @param error error message from {@link InstagramHelper}
     */
    void onInstagramSignInFail(String error);
}
