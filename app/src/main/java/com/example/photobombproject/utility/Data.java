package com.example.photobombproject.utility;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable
{

    @SerializedName("jwt")
    @Expose
    private String jwt;
    @SerializedName("user_data")
    @Expose
    private UserData__1 userData;
    private final static long serialVersionUID = -5308883742002702769L;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserData__1 getUserData() {
        return userData;
    }

    public void setUserData(UserData__1 userData) {
        this.userData = userData;
    }

}
