package umn.ac.id.uts_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button BtnProfil;
    private Button BtnLogin;
    private Intent goProfil;
    private Intent goLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnProfil=findViewById(R.id.BtnProfil);
        BtnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfil=new Intent(MainActivity.this,ActProfil.class);
                goProfil.putExtra("IntentFrom", "mainActivity");
                startActivity(goProfil);
                finish();
            }
        });
        BtnLogin=findViewById(R.id.BtnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin=new Intent(MainActivity.this,ActLogin.class);
                startActivity(goLogin);
                finish();
            }
        });
    }
}