package com.coolacharya.soicio_world.linkedInSiginIn;

import org.json.JSONObject;

/**
 * Created by kaushal on 27/11/2016.
 * This class represents facebook user profile.
 */
public class LinkedInUser {
    public String firstName;

    public String lastName;

    public String tokenID;

    public String gender;

    public String email;

    public String about;

    public String bio;

    public String coverPicUrl;

    public String profilePic;

    /**
     * JSON response received. If you want to parse more fields.
     */
    public JSONObject response;

}
