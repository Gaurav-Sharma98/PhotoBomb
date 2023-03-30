package com.example.photobombproject.signupndlogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.example.photobombproject.R;
import com.example.photobombproject.dashboard.Home;
import com.example.photobombproject.retrofit.AppController;
import com.example.photobombproject.retrofit.WebInterface;
import com.example.photobombproject.utility.Const;
import com.example.photobombproject.utility.Progress;
import com.example.photobombproject.utility.UserData__1;
import com.example.photobombproject.utility.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class OTPScreen extends Fragment {

    Progress progress;
    Context ctx;
    PinView otp;
    String mobile, otpText;
    View view;
    UserData__1 userdata;
    String OTP = "1234";
    Button verify_button;
    String fragType = "";
    String mobileNo = "", password, email, name = "";
    TextView resendotp;
    UserData__1 userData__1 = new UserData__1();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_o_t_p_screen, container, false);
        verify_button = view.findViewById(R.id.verify_button);
        ctx = getContext();
        progress = new Progress(ctx);
        // otp = view.findViewById(R.id.firstPinView);
//        assert getArguments() != null;
        fragType = getArguments().getString(Const.FRAG_TYPE);
        resendotp = view.findViewById(R.id.resendotp);
//        userdata = (UserData__1) getArguments().getSerializable(Const.DATA);
        mobile = getArguments().getString(Const.Mobile);
        password = getArguments().getString(Const.PASSWORD);
        email = getArguments().getString(Const.Email);
        name = getArguments().getString(Const.Name);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                otpText = Objects.requireNonNull(otp.getText()).toString().trim();
//                if (otpText.isEmpty()) {
//                    otp.requestFocus();
//                    otp.setError("Enter OTP");
//                } else {
                if (fragType.equalsIgnoreCase(Const.SIGNUP))
                    callOPTVerificationApi();
                else if (fragType.equalsIgnoreCase(Const.FORGOT_PASSWORD))
                    callVerifyOtpForget();
            }

            private void callOPTVerificationApi() {
                if (Utils.isConnectingToInternet(ctx)) {
                    progress.show();
                    HashMap<String, String> params = new HashMap<>();
                    params.put(Const.Mobile, mobile);
                    params.put(Const.Country_ID, "+91");
                    params.put(Const.OTP, OTP);

//            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
//            Call<JsonObject> call = Service.SEND_VERIFICATION_OTP(params);
//            call.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    progress.dismiss();
//                    if (response.body() != null) {
//                        JsonObject jsonObject = response.body();
//                        JSONObject jsonResponse = null;
//                        try {
//                            jsonResponse = new JSONObject(jsonObject.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        assert jsonResponse != null;
//                        if (jsonResponse.optString(Const.STATUS).equals(Const.TRUE)) {
//
//                            if (fragType.equalsIgnoreCase(Const.SIGNUP))
//                                callSignUpApi();
//                            else if (fragType.equalsIgnoreCase(Const.FORGOT_PASSWORD))
                    callSignUpApi();
                    Navigation.findNavController(view).navigate(R.id.action_OTPScreen_to_signupCongrates);

//                            else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.Mobile, mobile);
                    bundle.putString(Const.OTP, OTP);
//                                Fragment fragment = new ChangePasswordFragment();
//                                fragment.setArguments(bundle);
//                                assert getFragmentManager() != null;
//                                getFragmentManager().beginTransaction().addToBackStack("ChangePasswordFragment").replace(R.id.container_layout_login, fragment).commit();
//                            }


//                        } else {
//                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
//                            progress.dismiss();
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    progress.dismiss();
//                    Toast.makeText(getActivity(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
//
//                }
//            });
                } else {
                    progress.dismiss();
                    Toast.makeText(getActivity(), ctx.getResources().getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                }
            }

            private void callSignUpApi() {
                if (Utils.isConnectingToInternet(ctx)) {
                    progress.show();
                    HashMap<String, String> params = new HashMap<>();

                    params.put(Const.Name, name);
                    params.put(Const.Mobile, mobile);
                    params.put(Const.Email, email);
                    params.put(Const.PASSWORD, password);
                    params.put(Const.Country_ID, "+91");
                    params.put(Const.is_social, "0");
                    params.put(Const.device_type, "2");
                    params.put(Const.device_token, "2");


                    WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
                    Call<JsonObject> call = Service.API_SIGN_UP(params);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                            progress.dismiss();
                            if (response.body() != null) {
                                JsonObject jsonObject = response.body();
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                assert jsonResponse != null;
                                if (jsonResponse.optString(Const.STATUS).equals(Const.TRUE)) {
                                    JSONObject apiData;
                                    apiData = jsonResponse.optJSONObject(Const.DATA);
                                    JSONObject json_userdata = apiData.optJSONObject(Const.USER_DATA);
                                    UserData__1 userData = new Gson().fromJson((json_userdata).toString(), UserData__1.class);
//                            bundle = new Bundle();
//                            bundle.putString(Const.Mobile, mobile.getText().toString().trim());


//                            if((jsonResponse.optString(Const.STATUS)).equals("true"))
//                            {
//                                Navigation.findNavController(view).navigate(R.id.action_signUp_to_createPassword);
//
//                            }
//                            userData.getName();
//                            userData.getMobile();
//                            userData.getEmail();
//                            userData.getPassword();


                                    assert apiData != null;
                                    apiData.optString(Const.JWT);
                                    Utils.JWT = apiData.optString(Const.JWT);

                                    userData__1 = new Gson().fromJson(apiData.optJSONObject(Const.USER_DATA).toString(), UserData__1.class);
//                            PreferencesHelper.getInstance().setValue(Const.LOGIN_USER_BEAN, (userData__1));
//                            PreferencesHelper.getInstance().setValue(Const.JWT, apiData.optString(Const.JWT));
//
//                            PreferencesHelper.getInstance().setValue(Const.LOGIN_SESSION, true);
                                    startActivity(new Intent(getContext(), Home.class));


                                } else {
                                    Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                                    progress.dismiss();
                                }
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                            progress.dismiss();
                            Toast.makeText(getActivity(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    progress.dismiss();
                    Toast.makeText(getActivity(), ctx.getResources().getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                }
            }
        });


        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ResendotpApi();
            }
        });
        return view;
    }


    private void callVerifyOtpForget() {
        if (Utils.isConnectingToInternet(ctx)) {
            progress.show();
            HashMap<String, String> params = new HashMap<>();
            params.put(Const.Country_Code, "+91");
            params.put(Const.Mobile, mobile);
            params.put(Const.OTP, OTP);

//            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
//            Call<JsonObject> call = Service.VERIFY_OTP_FORGETPSWRD(params);
//            call.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    progress.dismiss();
//                    if (response.body() != null) {
//                        JsonObject jsonObject = response.body();
//                        JSONObject jsonResponse = null;
//                        try {
//                            jsonResponse = new JSONObject(jsonObject.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        assert jsonResponse != null;
//                        if (jsonResponse.optString(Const.STATUS).equals(Const.TRUE)) {
//                            Bundle bundle=new Bundle();
//                            bundle.putString(Const.Mobile,mobile);
            Navigation.findNavController(view).navigate(R.id.action_OTPScreen_to_createPassword);
//                        } else {
//                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
//                            progress.dismiss();
//                        }
//                    }

//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    progress.dismiss();
//                    Toast.makeText(getActivity(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        } else {
//            progress.dismiss();
//            Toast.makeText(getActivity(), ctx.getResources().getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
//        }


        }
    }
}


//    private void ResendotpApi() {
//
//        if (Utils.isConnectingToInternet(ctx)) {
//            progress.show();
//            HashMap<String, String> params = new HashMap<>();
//            params.put(Const.Mobile, mobile);
//            params.put(Const.Country_ID, "+91");
//
//            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
//            Call<JsonObject> call = Service.SEND_VERIFICATION_OTP(params);
//            call.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
//                    progress.dismiss();
//                    if (response.body() != null) {
//                        JsonObject jsonObject = response.body();
//                        JSONObject jsonResponse = null;
//                        try {
//                            jsonResponse = new JSONObject(jsonObject.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        assert jsonResponse != null;
//                        if (jsonResponse.optString(Const.STATUS).equals(Const.TRUE)) {
//                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
//
//
//
//
//                        } else {
//                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
//                            progress.dismiss();
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    progress.dismiss();
//                    Toast.makeText(getActivity(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        } else {
//            progress.dismiss();
//            Toast.makeText(getActivity(), ctx.getResources().getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
//        }
//
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.verifybtn:
//                otpText = otp.getText().toString().trim();
//                if (otpText.isEmpty()) {
//                    otp.requestFocus();
//                    otp.setError("Enter OTP");
//                }
//                else callOPTVerificationApi();
//                break;
//
////            case R.id.backBtn:
////                ((LoginSignupActivity) ctx).onBackPressed();
////                break;
//        }
//    }


