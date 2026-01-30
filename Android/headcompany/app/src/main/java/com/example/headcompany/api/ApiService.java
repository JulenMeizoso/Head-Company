package com.example.headcompany.api;

import com.example.headcompany.model.CameraResponse;
import com.example.headcompany.model.IncidenceResponse;
import com.example.headcompany.model.UsersResponse;
import com.example.headcompany.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<UsersResponse> getUsers(@Query("page") int page);
    @POST("users/create")
    Call<Void> createUser(@Body Usuario usuario);

    @POST("users/login")
    Call<Boolean> loginUser(@Body Usuario usuario);



    @GET("cameras")
    Call<CameraResponse> getCameras(@Query("page") int page);

    @GET("incidences")
    Call<IncidenceResponse> getIncidences(@Query("page") int page);



}
