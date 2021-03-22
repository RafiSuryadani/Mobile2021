package umn.ac.id.uts_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ActSplashScreen extends AppCompatActivity {
    private int timeout=2000;
    private Intent goHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_screen);


     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             goHome=new Intent(ActSplashScreen.this,MainActivity.class);
             startActivity(goHome);
             finish();
         }
     },timeout);
    }
}
