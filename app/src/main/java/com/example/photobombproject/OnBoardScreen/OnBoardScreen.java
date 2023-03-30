package com.example.photobombproject.OnBoardScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.photobombproject.OnBoardScreen.adaptor.OnBoardAdaptor;
import com.example.photobombproject.R;
import com.example.photobombproject.signupndlogin.EntryActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class OnBoardScreen extends AppCompatActivity {
    DotsIndicator dotsIndicator;
    Button skip;
    Button button;
    int b = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_screen);

       // getSupportActionBar().hide();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        OnBoardAdaptor adapter = new OnBoardAdaptor(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount()-3);
        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        dotsIndicator.attachTo(viewPager);
        button=findViewById(R.id.button2);
        skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentskip=new Intent(OnBoardScreen.this, EntryActivity.class);
//                startActivity(intentskip);
                SharedPreferences pref = getSharedPreferences("installed",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",true);
                editor.apply();
                Intent intent = new Intent(OnBoardScreen.this, EntryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(position==2)
                {button.setText("Get Started");}

                else
                {button.setText("Next");}
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }
                    @Override
                    public void onPageSelected(int position) {
                        if(position==2)
                        {button.setText("Get Started");}
                        else
                        {button.setText("Next");}
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(viewPager.getCurrentItem()+1 <viewPager.getAdapter().getCount()) {
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                }
                                else {


                                    SharedPreferences pref = getSharedPreferences("installed",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean("flag",true);
                                    editor.apply();
                                    Intent intent = new Intent(OnBoardScreen.this, EntryActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                    }
                });
            }
        });

    }
}