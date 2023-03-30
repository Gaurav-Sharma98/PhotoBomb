package com.example.photobombproject.signupndlogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.photobombproject.R;
import com.example.photobombproject.dashboard.Home;
import com.example.photobombproject.retrofit.AppController;
import com.example.photobombproject.retrofit.WebInterface;
import com.example.photobombproject.utility.Const;
import com.example.photobombproject.utility.PreferencesHelper;
import com.example.photobombproject.utility.Progress;
import com.example.photobombproject.utility.UserData__1;
import com.example.photobombproject.utility.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    public Progress progress;
    Context ctx;
    View view;
    TextView forgetpswd,singin_up_txt;
    TextInputEditText login_phone_tv, login_pass_tv;
    Button signin;
    UserData__1 userData__1 = new UserData__1();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login2, container, false);
        initView(view);

        Spannable spannable = new SpannableString("Don't have an account ? Sign Up");
        String str = spannable.toString();
        int iStart = str.indexOf("Sign Up");
        int iEnd = iStart + 7;/*10 characters = in-network. */

        SpannableString ssText = new SpannableString(spannable);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //your code at here.
                singin_up_txt.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_login_to_signup));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.white));
            }
        };
        ssText.setSpan(clickableSpan, iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        singin_up_txt.setText(ssText);
        singin_up_txt.setMovementMethod(LinkMovementMethod.getInstance());
        singin_up_txt.setHighlightColor(Color.TRANSPARENT);
        singin_up_txt.setEnabled(true);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        progress = new Progress(ctx);
        progress.setCancelable(false);

        singin_up_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_signup);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if ((login_phone_tv. getText().toString()).isEmpty()) {
                    login_phone_tv.setError("can't be empty");
                    login_phone_tv.requestFocus();
                } else if ((login_pass_tv.getText().toString()).isEmpty()) {
                    login_pass_tv.setError("can't be empty");
                    login_pass_tv.requestFocus();
                } else {
                    userData__1.setMobile(login_phone_tv.getText().toString());
                    userData__1.setPassword(login_pass_tv.getText().toString());
                    callSignInApi();
                }
            }
        });

        forgetpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_forgotPassword);
            }
        });
    }

    private void initView(View view) {
        login_phone_tv = view.findViewById(R.id.login_phone_tv);
        login_pass_tv = view.findViewById(R.id.login_pass_tv);
        signin = view.findViewById(R.id.signin_button);
        singin_up_txt = view.findViewById(R.id.singin_up_txt);
        forgetpswd = view.findViewById(R.id.signin_fp);
    }
    private void callSignInApi() {
        if (Utils.isConnectingToInternet(ctx)) {
            progress.show();
            HashMap<String, String> params = new HashMap<>();

            params.put(Const.UserName, userData__1.getMobile());
            params.put(Const.PASSWORD, userData__1.getPassword());
            params.put(Const.is_social, "0");
            params.put(Const.Country_ID, "+91");
            params.put(Const.SocialType, "0");
            params.put(Const.DEVICE_TYPE, "2");
            params.put(Const.DEVICE_TOKEN, "2");

            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
            Call<JsonObject> call = Service.SIGN_IN(params);
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

                            assert apiData != null;
                            apiData.optString(Const.JWT);
                            Utils.JWT = apiData.optString(Const.JWT);

                            UserData__1 userData__1 = new Gson().fromJson(Objects.requireNonNull(apiData.optJSONObject(Const.USER_DATA)).toString(), UserData__1.class);
                            PreferencesHelper.getInstance(getContext()).setValue(Const.LOGIN_USER_BEAN, userData__1);
                             PreferencesHelper.getInstance(getContext()).setValue(Const.JWT, apiData.optString(Const.JWT));
                            PreferencesHelper.getInstance(getContext()).setValue(Const.NAME, apiData.optString(Const.Name));
                            PreferencesHelper.getInstance(getContext()).setValue(Const.Mobile, apiData.optString(Const.Mobile));
                            PreferencesHelper.getInstance(getContext()).setBoolean(Const.LOGIN_SESSION, true);
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
}