package com.example.photobombproject.retrofit;

public interface API {
    String BASE_URL = "https://myphotobomb.com/";
    String SERVER_URL = BASE_URL + "data_model/";
    String SIGN_UP = BASE_URL + "register/signup";
    String SIGN_IN = BASE_URL + "login/login_authentication";
    String LOG_IN = SERVER_URL + "user/registration/login_authentication";
    String SEND_OTP=BASE_URL +"otp/send_otp_registration";
    String FORGET_PASSWORD= BASE_URL +"otp/check_user_and_send_otp";
    String VERIFY_OTP_FORGETPSWRD= BASE_URL +"otp/verify_otp_forgot";
    String CREATE_PASSWORD= BASE_URL +"users/reset_password";


    String API_ABOUT_US = BASE_URL + "about-us-mobile";
    String API_TERMS_AND_CONDITIONS = BASE_URL + "user-agreement-mobile";
    String API_PRIVACY_AND_POLICY = BASE_URL + "privacy-policy-mobile";
    String API_AMAZON_S3_BUCKET_NAME_PROFILE_IMAGES = "profile_image/";

    String API_AMAZON_S3_BUCKET_NAME_FANWALL_IMAGES = "mvfplayer/file_library/videos/original";
    String API_AMAZON_S3_BUCKET_NAME_VIDEO_IMAGES = "mvfplayer/videos/original";
    String API_AMAZON_S3_BUCKET_NAME_DOCUMENT = "mvfplayer/mvfplayer_doc_folder";
    String API_AMAZON_S3_BUCKET_NAME_FEEDBACK = "mvfplayer/feedback_images";

    String SEND_VERIFICATION_OTP =BASE_URL + "otp/verify_otp_registration" ;
}

