package umn.ac.id.uts_32986;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActHome extends AppCompatActivity {

    private Toolbar toolbar;

    private static final int MY_PERMISSION_REQUEST = 1;
    private RecyclerView rvMusic;
    List<ModMusic> modMusics = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        new AlertDialog.Builder(ActHome.this)
                .setTitle("Selamat Datang")
                .setMessage("Rafi Suryadani \n 00000032986")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActHome.this, "Selamat Mendengarkan", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(ActHome.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActHome.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(ActHome.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(ActHome.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }else{
            renderMusic();
        }

    }

    public void getMusic(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                ModMusic modMusic = new ModMusic();
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentPath = songCursor.getString(songPath);
                modMusic.setTitleSong(currentTitle);
                modMusic.setArtistSong(currentArtist);
                modMusic.setAlbumSong(currentAlbum);
                modMusic.setUriSong(currentPath);
                modMusics.add(modMusic);
            }while (songCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(ActHome.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted!", Toast.LENGTH_LONG).show();
                        renderMusic();
                    }else{
                        Toast.makeText(this, "No Permission Granted!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        }
    }

    public void renderMusic(){
        rvMusic = findViewById(R.id.recyclerView);
        rvMusic.setLayoutManager(new LinearLayoutManager(ActHome.this));
        getMusic();
        HomeAdapter adapter = new HomeAdapter(ActHome.this, modMusics);
        rvMusic.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.goToProfile){
            Intent goProfile = new Intent(this, ActProfil.class);
            goProfile.putExtra("IntentFrom", "homeActivity");
            startActivity(goProfile);
            finish();
        } else if (item.getItemId() == R.id.goLogOut) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return true;
    }
}