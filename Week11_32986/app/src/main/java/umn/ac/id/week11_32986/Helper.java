package umn.ac.id.week11_32986;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Helper {
    @GET("posts")
    Call<ArrayList<PostModel>> getPosts();
}
