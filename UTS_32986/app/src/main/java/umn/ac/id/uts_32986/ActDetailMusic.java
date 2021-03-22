package umn.ac.id.uts_32986;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActDetailMusic extends AppCompatActivity {

    private ImageView Back;
    private Intent goHome;

    private SeekBar seekBar;
    private TextView titleSong;
    private TextView artistSong;
    private ImageView albumSong;

    private ImageView prevSong;
    private ImageView nextSong;
    private ImageView playSong;

    MediaPlayer mediaPlayer;
    Intent playerData;
    Bundle bundle;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_music);

        Back=findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome = new Intent(ActDetailMusic.this,ActHome.class);
                startActivity(goHome);
                finish();

            }
        });

        seekBar = findViewById(R.id.seekbar);
        titleSong = findViewById(R.id.titleMusic);
        artistSong = findViewById(R.id.artistMusic);
        albumSong = findViewById(R.id.thumbnailMusic);

        prevSong = findViewById(R.id.btnPrev);
        nextSong = findViewById(R.id.btnNext);
        playSong = findViewById(R.id.btnPlay);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        initPlayer();

        playerData = getIntent();
        bundle = playerData.getExtras();

        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        prevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActDetailMusic.this, "Hehehe masih tahap pengembangan", Toast.LENGTH_LONG).show();
            }
        });
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActDetailMusic.this, "Hehehe masih tahap pengembangan", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initPlayer() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }

        String titleSongIntent = getIntent().getStringExtra("songName").replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        titleSong.setText(titleSongIntent);

        String uriSOngIntent = getIntent().getStringExtra("songUrl");
        Uri songResourceUri = Uri.parse(uriSOngIntent);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), songResourceUri); // create and load mediaplayer with song resources
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                playSong.setImageResource(R.drawable.ic_baseline_pause);

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playSong.setImageResource(R.drawable.ic_baseline_play_arrow);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (mediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            seekBar.setProgress(current_position);
        }
    };

    private void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            playSong.setImageResource(R.drawable.ic_baseline_pause);
        } else {
            pause();
        }
    }

    private void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playSong.setImageResource(R.drawable.ic_baseline_play_arrow);
        }
    }

}