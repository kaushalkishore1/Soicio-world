package com.coolacharya.soicio_world.googleSignIn;

import com.google.android.gms.plus.model.people.Person;

/**
 * Created by kaushal on 27/11/2016.
 */
public interface GoogleResponseListener {
    void onGSignInFail();

    void onGSignInSuccess(Person personData);
}
