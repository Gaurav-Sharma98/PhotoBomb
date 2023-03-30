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
import android.widget.ImageView;
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
public class CreatePassword extends Fragment {
    TextInputEditText npassword,ncpassword;
    ImageView backimg;
    Progress progress;
    Context ctx;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_create_password, container, false);
        Button save=view.findViewById(R.id.savePassword);
        npassword=view.findViewById(R.id.npassword);
        View view1=inflater.inflate(R.layout.toolbar,container,false);
       // backimg=view1.findViewById(R.id.back);
        ncpassword=view.findViewById(R.id.ncpassword);
        ctx=getContext();
        progress=new Progress(ctx);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((npassword.getText().toString()).isEmpty()) {
                    npassword.setError("can't be empty");

                }
                else if((ncpassword.getText().toString()).isEmpty())
                {
                    ncpassword.setError("can't be empty");
                }
                else
                    ChangePasswordApi();
            }
        });
        return view;
    }

    private void ChangePasswordApi(){
        if (Utils.isConnectingToInternet(ctx)) {
            progress.show();
            HashMap<String, String> params = new HashMap<>();
            params.put(Const.PASSWORD, npassword.getText().toString());
            params.put(Const.USER_ID, "1");

            WebInterface Service = AppController.getRetrofitInstance(WebInterface.class);
            Call<JsonObject> call = Service.CREATE_PASSWORD(params);
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
                            Toast.makeText(getActivity(), jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_createPassword_to_contrats_Screen);
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