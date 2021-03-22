package umn.ac.id.uts_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActLogin extends AppCompatActivity {
    private ImageView Back;
    private Intent goHome;
    private EditText username;
    private EditText password;
    private Button bntLogin;
    private Intent goPlayMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        Back=findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome=new Intent(ActLogin.this,MainActivity.class);
                startActivity(goHome);
                finish();
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        bntLogin = findViewById(R.id.btnLogin);
        bntLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("uasmobile") && password.getText().toString().equals("uasmobilegenap")){
                    goPlayMusic = new Intent(ActLogin.this,ActHome.class);
                    startActivity(goPlayMusic);
                    finish();
                }else{
                    Toast.makeText(ActLogin.this, "Username / Password Salah", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}