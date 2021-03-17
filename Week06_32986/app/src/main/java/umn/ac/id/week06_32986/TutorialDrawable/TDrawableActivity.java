package umn.ac.id.week06_32986.TutorialDrawable;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import umn.ac.id.week06_32986.R;

public class TDrawableActivity extends AppCompatActivity {
    AnimationDrawable animasiKuda;
    ImageView gambarKuda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_drawable);
        gambarKuda = (ImageView) findViewById(R.id.gambarKuda);
        gambarKuda.setBackgroundResource(R.drawable.kuda_lari);
        animasiKuda = (AnimationDrawable) gambarKuda.getBackground();
        gambarKuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animasiKuda.start();
            }
        });
    }
}