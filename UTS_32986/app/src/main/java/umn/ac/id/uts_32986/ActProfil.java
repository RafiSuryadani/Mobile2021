package umn.ac.id.uts_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ActProfil extends AppCompatActivity {
    private ImageView Back;
    private Intent goHome;
    private String IntentFrom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profil);
        Back=findViewById(R.id.Back);
        IntentFrom = getIntent().getStringExtra("IntentFrom");
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IntentFrom.isEmpty()){
                    Toast.makeText(ActProfil.this, "Maaf ada kesalahan", Toast.LENGTH_LONG).show();
                }else{
                    if (IntentFrom.equals("mainActivity")){
                        goHome=new Intent(ActProfil.this,MainActivity.class);
                        startActivity(goHome);
                    }else{
                        startActivity(new Intent(ActProfil.this,ActHome.class));
                    }
                    finish();
                }
            }
        });
    }
}