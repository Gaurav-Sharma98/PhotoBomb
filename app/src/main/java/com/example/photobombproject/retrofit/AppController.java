package com.example.photobombproject.retrofit;

import androidx.multidex.MultiDexApplication;

import com.example.photobombproject.utility.Const;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends MultiDexApplication {
    static String userId="";
    // static SignupResponse signupResponse;


    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.MINUTES);

    public static <S> S getRetrofitInstance(Class<S> serviceClass) {
       /* signupResponse = PreferencesHelper.getInstance().getObjectValue(Const.LOGIN_USER_BEAN, SignupResponse.class);

        if (signupResponse != null) {
            if (signupResponse.getId() != null) {
                userId = signupResponse.getId();
            } else {
                userId = "0";
            }
        } else {
            userId = "0";
        }*/

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(60, TimeUnit.MINUTES).readTimeout(60, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).build();

        final Gson gson0 = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson0))
                .client(client)
                .build();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    // .header(Const.JWT, PreferencesHelper.getInstance().getStringValue(Const.JWT, ""))
                    //.header(Const.VERSION, String.valueOf(BuildConfig.VERSION_CODE))
                    //  .header(Const.DEVICE_TOKEN, SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID))
                    //.header(Const.DEVICE_TOKEN, Objects.requireNonNull(FirebaseInstanceId.getInstance().getToken()))
                    .header(Const.USER_ID, userId)
                    //.header(Const.DEVICE_ID, SharedPreference.getInstance().getString("android_id"))
                    .header(Const.DEVICE_TYPE, Const.ANDROID_DEVICE);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return retrofit.create(serviceClass);
    }
}
