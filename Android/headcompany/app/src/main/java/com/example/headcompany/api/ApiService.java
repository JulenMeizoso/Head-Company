package com.example.headcompany.api;

import com.example.headcompany.model.CameraResponse;
import com.example.headcompany.model.IncidenceResponse;
import com.example.headcompany.model.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<UsersResponse> getUsers();

    @GET("cameras")
    Call<CameraResponse> getCameras();

    @GET("incidences")
    Call<IncidenceResponse> getIncidences(@Query("page") int page);



}
