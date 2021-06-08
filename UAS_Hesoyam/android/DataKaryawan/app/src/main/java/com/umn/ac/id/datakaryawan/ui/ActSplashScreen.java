package com.umn.ac.id.datakaryawan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.umn.ac.id.datakaryawan.MainActivity;
import com.umn.ac.id.datakaryawan.R;
import com.umn.ac.id.datakaryawan.session.UserSession;

public class ActSplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private Intent redirect;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_screen);

        userSession = new UserSession(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userSession.getIsUserLogin()){
                    redirect = new Intent(ActSplashScreen.this,  MainActivity.class);
                }else{
                    redirect = new Intent(ActSplashScreen.this,  ActLogin.class);
                }
                startActivity(redirect);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        },SPLASH_TIME_OUT);

    }


}