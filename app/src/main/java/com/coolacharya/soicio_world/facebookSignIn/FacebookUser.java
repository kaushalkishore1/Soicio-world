package com.coolacharya.soicio_world.facebookSignIn;

import org.json.JSONObject;

/**
 * Created by kaushal on 27/11/2016.
 * This class represents facebook user profile.
 */
public class FacebookUser {
    public String name;

    public String email;

    public String facebookID;

    public String gender;

    public String about;

    public String bio;

    public String coverPicUrl;

    public String profilePic;

    /**
     * JSON response received. If you want to parse more fields.
     */
    public JSONObject response;

}
