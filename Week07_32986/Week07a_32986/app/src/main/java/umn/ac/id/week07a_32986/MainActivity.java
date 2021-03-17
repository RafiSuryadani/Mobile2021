package umn.ac.id.week07a_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private Button btnFoto;
    private Button btnVideo;
    private ImageView imgKotakFoto;
    private VideoView imgKotakVideo;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFoto = findViewById(R.id.button);
        btnVideo = findViewById(R.id.button2);
        imgKotakFoto = findViewById(R.id.imageView);
        imgKotakVideo = findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(imgKotakVideo);
        imgKotakVideo.setMediaController(controller);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager())
                        != null) {
                    startActivityForResult(takePictureIntent,
                            REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new
                        Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager())
                        != null) {
                    startActivityForResult(takeVideoIntent,
                            REQUEST_VIDEO_CAPTURE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgKotakFoto.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_VIDEO_CAPTURE &&
                resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            imgKotakVideo.setVideoURI(videoUri);
            imgKotakVideo.seekTo(100);
            imgKotakVideo.start();
        }
    }
}