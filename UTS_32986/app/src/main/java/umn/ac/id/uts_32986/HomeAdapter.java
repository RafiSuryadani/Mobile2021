package umn.ac.id.uts_32986;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<ModMusic> listMusic;
    private Context context;

    public HomeAdapter(Context context, List<ModMusic> data){
        this.context = context;
        this.listMusic = data;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        ViewHolder vh = new ViewHolder((LinearLayout)v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        ModMusic model = listMusic.get(position);
        holder.titleMusic.setText(model.getTitleSong());
        Log.d("asdasd", "asdasdasd " + model.getTitleSong());
        byte[] image = getThumbnail(model.getUriSong());
        if(image != null) {
            Glide.with(context).asBitmap().load(image).into(holder.thumbnailMusic);
        }else{
            Glide.with(context).load(R.drawable.musikplayer).into(holder.thumbnailMusic);
        }

        holder.llMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start music player when song name is clicked
                Intent play = new Intent(context, ActDetailMusic.class);
                play.putExtra("songName", model.getTitleSong());
                play.putExtra("songArtist", model.getArtistSong());
                play.putExtra("songAlbum", model.getAlbumSong());
                play.putExtra("songUrl", model.getUriSong());
                context.startActivity(play);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMusic.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleMusic;
        public ImageView thumbnailMusic;
        public LinearLayout llMusic;

        public ViewHolder(View view) {
            super(view);
            titleMusic = (TextView) view.findViewById(R.id.titleMusic);
            thumbnailMusic = (ImageView) view.findViewById(R.id.thumbnailMusic);
            llMusic = view.findViewById(R.id.llMusic);
        }
    }

    private byte[] getThumbnail(String uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] album = retriever.getEmbeddedPicture();
        retriever.release();
        return album;
    }
}
