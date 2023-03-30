package com.example.photobombproject.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import com.example.photobombproject.OnBoardScreen.OnBoardScreen;
import com.example.photobombproject.R;
import com.example.photobombproject.signupndlogin.EntryActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {


                    SharedPreferences loginpref = getSharedPreferences("installed", MODE_PRIVATE);
                    Boolean logincheck = loginpref.getBoolean("flag", false);
                    if (logincheck) {
                        startActivity(new Intent(Splash.this, EntryActivity.class));

                    } else {
                        startActivity(new Intent(Splash.this, OnBoardScreen.class));

                    }
                }
                catch (Exception e)
                {e.printStackTrace();}

                finally {
                    finishAffinity();
                }

            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}