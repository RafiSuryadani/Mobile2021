package umn.ac.id.week11_32986;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.DnsResolver;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvPostList;
    PostAdapter adapter;
    Helper helper;

    ArrayList<PostModel> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPostList = findViewById(R.id.rvPostList);
        rvPostList.setLayoutManager(new LinearLayoutManager(this));

        helper = ApiClient.getClient().create(Helper.class);

        getData();
    }

    public void getData(){
        retrofit2.Call<ArrayList<PostModel>> postCallback = helper.getPosts();
        postCallback.enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                posts = response.body();

                adapter = new PostAdapter(posts);
                rvPostList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Internet not available", Toast.LENGTH_LONG).show();
            }
        });
    }
}