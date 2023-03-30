package com.example.photobombproject.signupndlogin.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.photobombproject.R;
import com.example.photobombproject.retrofit.AppController;
import com.example.photobombproject.retrofit.WebInterface;
import com.example.photobombproject.utility.Const;
import com.example.photobombproject.utility.Progress;
import com.example.photobombproject.utility.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends Fragment {
    TextInputEditText mobile_fp;
    Progress progress;
    Context ctx;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        Button reset_button = view.findViewById(R.id.reset_button);
        mobile_fp = view.findViewById(R.id.mobile_fp);
        ctx = getContext();
        progress = new Progress(ctx);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallForgetPasswordAPI();
            }
        });
        return view;
    }
    private void CallForgetPasswordAPI() {
        if (Utils.isConnectingToInternet(ctx)) {
            progress.show();
            HashMap<String, String> params = new HashMap<>();

//            params.put(Const.UserName, mobile.getText().toString().trim());
            params.put(Const.Mobile, mobile_fp.getText().toString().trim());
            params.put(Const.Country_Code, "+91");

            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
            Call<JsonObject> call = Service.FORGET_PASSWORD(params);
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
                            Bundle bundle = new Bundle();
                            bundle.putString(Const.FRAG_TYPE, Const.FORGOT_PASSWORD);
                            bundle.putString(Const.Mobile, mobile_fp.getText().toString());
                            Navigation.findNavController(view).navigate(R.id.action_forgotPassword_to_OTPScreen, bundle);


//                            JSONObject apiData;
//                            String mbno=jsonResponse.optJSONObject(Const.DATA).optJSONObject(Const.USER_DATA).optString(Const.Mobile);
//                            apiData = jsonResponse.optJSONObject(Const.DATA);
                           /* assert apiData != null;
                            apiData.optString(Const.JWT);
                            Utils.JWT = apiData.optString(Const.JWT);

                            ((LoginSignupActivity) ctx).signupResponse = new Gson().fromJson(apiData.optJSONObject(Const.USER_DATA).toString(), SignupResponse.class);
                            PreferencesHelper.getInstance().setValue(Const.LOGIN_USER_BEAN, ((LoginSignupActivity) ctx).signupResponse);
                            PreferencesHelper.getInstance().setValue(Const.JWT, apiData.optString(Const.JWT));
*/
//                            PreferencesHelper.getInstance().setValue(Const.LOGIN_SESSION, true);


                        } else {
                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
//                            Navigation.findNavController(view).navigate(R.id.action_signIn_to_signUp);

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


}

