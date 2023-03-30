package com.example.photobombproject.retrofit;

import com.example.photobombproject.utility.Const;
import com.google.gson.JsonObject;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface WebInterface {

    @FormUrlEncoded
    @POST(API.SIGN_UP)
    Call<JsonObject>API_SIGN_UP(@FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST(API.SEND_OTP)
    Call<JsonObject>SEND_OTP(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(API.SIGN_IN)
    Call<JsonObject>SIGN_IN(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(API.LOG_IN)
    Call<JsonObject>LOG_IN(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(API.FORGET_PASSWORD)
    Call<JsonObject>FORGET_PASSWORD(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(API.VERIFY_OTP_FORGETPSWRD)
    Call<JsonObject>VERIFY_OTP_FORGETPSWRD(@FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST(API.CREATE_PASSWORD)
    Call<JsonObject>CREATE_PASSWORD(@FieldMap HashMap<String, String> map);


    @GET(API.LOG_IN)
    Call<JsonObject> API_GET_APP_VERSION();

    @GET
    Call<JsonObject> API_GET_YOUTUBE_URL(@Url String url);


    @FormUrlEncoded
    @POST(API.LOG_IN)
    Call<JsonObject> SUBMIT_QUERY(@Field(Const.USER_ID) String userid);

    @FormUrlEncoded
    @POST(API.SEND_VERIFICATION_OTP)
    Call<JsonObject> SEND_VERIFICATION_OTP(@FieldMap HashMap<String, String> params);
}
