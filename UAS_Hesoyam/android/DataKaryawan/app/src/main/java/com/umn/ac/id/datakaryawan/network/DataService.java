package com.umn.ac.id.datakaryawan.network;

import com.umn.ac.id.datakaryawan.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    /* Api untuk login */
    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseModel> login(
            @Field("username") String username,
            @Field("password") String password
    );

    /* Api untuk mendapatkan total karyawan */
    @GET("api/total-user")
    Call<ResponseModel> getTotalKaryawan();

    /* Api untuk mendapatkan list karyawan */
    @GET("api/list-user")
    Call<ResponseModel> getListKaryawan();

    /* Api untuk detail karyawan */
    @FormUrlEncoded
    @POST("api/detail-user")
    Call<ResponseModel> detail(
            @Field("userId") String userId
    );

    /* Api untuk update karyawan */
    @FormUrlEncoded
    @POST("api/update-user")
    Call<ResponseModel> update(
            @Field("userId") String userId,
            @Field("nik") String nik,
            @Field("username") String username,
            @Field("name") String nameUser,
            @Field("email") String email,
            @Field("photo") String photo,
            @Field("address") String address
    );

    /* Api untuk insert karyawan */
    @FormUrlEncoded
    @POST("api/insert-user")
    Call<ResponseModel> insert(
            @Field("nik") String nik,
            @Field("username") String username,
            @Field("name") String nameUser,
            @Field("email") String email,
            @Field("photo") String photo,
            @Field("address") String address
    );

    /* Api untuk delete karyawan */
    @FormUrlEncoded
    @POST("api/delete-user")
    Call<ResponseModel> delete(
            @Field("userId") String userId
    );
}
