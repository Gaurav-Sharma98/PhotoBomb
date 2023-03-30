package com.example.photobombproject.signupndlogin.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photobombproject.R;
import com.example.photobombproject.retrofit.AppController;
import com.example.photobombproject.retrofit.WebInterface;
import com.example.photobombproject.utility.Const;
import com.example.photobombproject.utility.Progress;
import com.example.photobombproject.utility.UserData__1;
import com.example.photobombproject.utility.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signup extends Fragment  {
    TextInputEditText name, mobile, email, password;
    public Progress progress;
    UserData__1 userdata;
    Context ctx;
    View view, view1;
    Button signup_button;
    ImageView back;
    Bundle bundle;
    TextView signup_txt;

    private void initView(View view) {
        signup_button = view.findViewById(R.id.signup_button);
        name = view.findViewById(R.id.name);
        mobile = view.findViewById(R.id.mobile);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        signup_txt=view.findViewById(R.id.signup_txt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        view1 = inflater.inflate(R.layout.toolbar, container, false);
        ctx = getActivity();
        userdata = new UserData__1();
        progress = new Progress(ctx);
        progress.setCancelable(false);
        initView(view);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       /* String txt="Already have an account ? Sign In";
        SpannableString ss=new SpannableString(txt);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                signup_txt.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_signup_to_login));
            }
        };
        ss.setSpan(clickableSpan,26,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup_txt.setText(ss);
        signup_txt.setMovementMethod(LinkMovementMethod.getInstance());*/

        Spannable spannable = new SpannableString("Already have an account ? Sign In");
        String str = spannable.toString();
        int iStart = str.indexOf("Sign In");
        int iEnd = iStart + 7;/*10 characters = in-network. */

        SpannableString ssText = new SpannableString(spannable);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //your code at here.
                signup_txt.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_signup_to_login));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.white));
            }
        };
        ssText.setSpan(clickableSpan, iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup_txt.setText(ssText);
        signup_txt.setMovementMethod(LinkMovementMethod.getInstance());
        signup_txt.setHighlightColor(Color.TRANSPARENT);
        signup_txt.setEnabled(true);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((name.getText().toString()).isEmpty()) {
                    name.setError("can't be empty");
                    name.requestFocus();
                } else if ((mobile.getText().toString()).isEmpty()) {
                    mobile.setError("can't be empty");
                    mobile.requestFocus();
                } else if ((email.getText().toString()).isEmpty()) {
                    email.setError("can't be empty");
                    email.requestFocus();
                } else if ((password.getText().toString()).isEmpty()) {
                    password.setError("can't be empty");
                }
                else {
                    userdata.setName(name.getText().toString());
                    userdata.setMobile(mobile.getText().toString());
                    userdata.setEmail(email.getText().toString());
                    userdata.setPassword(password.getText().toString());
                    CallOnOtpApi();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void CallOnOtpApi() {
        if (Utils.isConnectingToInternet(ctx)) {
            progress.show();
            HashMap<String, String> params = new HashMap<>();
            params.put(Const.Country_ID, "+91");
            params.put(Const.Mobile, mobile.getText().toString());
            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
            Call<JsonObject> call = Service.SEND_OTP(params);
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
                            bundle = new Bundle();
                            bundle.putString(Const.FRAG_TYPE, Const.SIGNUP);
                            bundle.putString(Const.PASSWORD, password.getText().toString());
                            bundle.putString(Const.Mobile, mobile.getText().toString());
                            bundle.putString(Const.Email, email.getText().toString().trim());
                            bundle.putString(Const.Name, name.getText().toString().trim());
                            JSONObject apiData;
                            apiData = jsonResponse.optJSONObject(Const.DATA);
                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_signup_to_OTPScreen,bundle);
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
        }

    }
}













