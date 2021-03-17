package umn.ac.id.week06_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import umn.ac.id.week06_32986.TutorialDrawable.TDrawableActivity;
import umn.ac.id.week06_32986.TutorialFisika.TFisikaActivity;
import umn.ac.id.week06_32986.TutorialProperty.TPropertyActivity;

public class MainActivity extends AppCompatActivity {
    Button btnTutorialProperty, btnTutorialDrawable, btnTutorialFisika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Week06_32986 (Tutorial Animasi)");

        btnTutorialProperty = findViewById(R.id.btnTutorialProperty);
        btnTutorialDrawable = findViewById(R.id.btnTutorialDrawable);
        btnTutorialFisika = findViewById(R.id.btnTutorialFisika);

        btnTutorialProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TPropertyActivity.class));
            }
        });

        btnTutorialDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TDrawableActivity.class));
            }
        });

        btnTutorialFisika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TFisikaActivity.class));
            }
        });
    }
}