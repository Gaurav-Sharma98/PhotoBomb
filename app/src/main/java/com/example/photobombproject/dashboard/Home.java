package com.example.photobombproject.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photobombproject.R;
import com.example.photobombproject.dashboard.fragment.CreateFragment;
import com.example.photobombproject.dashboard.fragment.HomeFragment;
import com.example.photobombproject.dashboard.fragment.JoinFragment;
import com.example.photobombproject.dashboard.fragment.PendingFragment;
import com.example.photobombproject.signupndlogin.EntryActivity;
import com.example.photobombproject.utility.Const;
import com.example.photobombproject.utility.PreferencesHelper;
import com.example.photobombproject.utility.UserData__1;
import com.example.photobombproject.utility.Utils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView name, user_mobile;
    UserData__1 userData1;
    Fragment active;
    BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer = findViewById(R.id.drawerlayout);
        MaterialToolbar toolbar = findViewById(R.id.navtoolbar);
        userData1 = PreferencesHelper.getInstance(getApplicationContext()).getObjectValue(Const.LOGIN_USER_BEAN, UserData__1.class);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);

        bottomNavigationView = findViewById(R.id.app_bottom_nav);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayout,new HomeFragment()).addToBackStack(null).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    active = new HomeFragment();
                    break;
                case R.id.create:
                    active = new CreateFragment();
                    break;
                case R.id.join:
                    active = new JoinFragment();
                    break;
                case R.id.pending:
                    active = new PendingFragment();
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.frameLayout,active).addToBackStack(null).commit();
            return true;
        });

        navigationView = findViewById(R.id.navigation);
       /* navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                // your code
                Toast.makeText(this, "Account Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EntryActivity.class));
                drawer.close();
                return true;
            } else {
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });*/


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        Toast.makeText(Home.this, "Profile", Toast.LENGTH_SHORT).show();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.drawerlayout,new PendingFragment());
                        fragmentTransaction.commit();

                        /*Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.common_dialogue, null);

                        builder.setView(dialogView);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        AppCompatButton yesBtn = dialogView.findViewById(R.id.yes_ad_text);
                        AppCompatButton cancelBtn = dialogView.findViewById(R.id.cancel_ad_text);

                        yesBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), EntryActivity.class));
                                PreferencesHelper.getInstance().setValue(Const.LOGIN_USER_BEAN, new UserData());
                                PreferencesHelper.getInstance().setValue(Const.LOGIN_SESSION,false);
                                finishAffinity();
                                dialog.dismiss();
                            }
                        });

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        Utils.openLoginLogoutAlertDialog(Home.this,null,"");*/
                        break;

                    case R.id.settings:
                        Toast.makeText(Home.this, "Account Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.storage:
                        Toast.makeText(Home.this, "Manage Storage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        Toast.makeText(Home.this, "About us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.terms:
                        Toast.makeText(Home.this, "Terms & Conditions", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.privacy:
                        Toast.makeText(Home.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contact:
                        Toast.makeText(Home.this, "Contact us ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.share:
                        Toast.makeText(Home.this, "Share", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(Home.this, "Logout", Toast.LENGTH_SHORT).show();
                        /*if (item.getItemId() == R.id.logout) {
                            Toast.makeText(this, "Account Logged out", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, EntryActivity.class));
                            drawer.close();
                            return true;
                        } else {
                            Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                            return true;
                        }*/
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}