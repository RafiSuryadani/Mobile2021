package com.umn.ac.id.datakaryawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.umn.ac.id.datakaryawan.ui.ActLogin;
import com.umn.ac.id.datakaryawan.ui.FrgHome;
import com.umn.ac.id.datakaryawan.ui.FrgProfile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_container, new FrgHome()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.bottom_menu_home:
                            selectedFragment = new FrgHome();
                            break;

                        case R.id.bottom_menu_account:
                            selectedFragment = new FrgProfile();
                            break;

                        case R.id.bottom_menu_logout:
                            SharedPreferences prefLogin = MainActivity.this.getSharedPreferences("prefLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefLogin.edit();
                            editor.clear();
                            editor.commit();
                            Intent intent = new Intent(MainActivity.this, ActLogin.class);
                            startActivity(intent);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottom_nav_container, selectedFragment).commit();
                    return true;
                }
            };
}