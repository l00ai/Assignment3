package com.example.assignment3.Network;

import com.example.assignment3.Model.ApiResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @POST("login")
    Single<ApiResponse> login(@Body Map<Object, Object> params);

    @POST("add_new_user")
    Single<ApiResponse> addNewUser(@Body Map<Object, Object> params);

    @PUT("add_reg_token")
    Single<ApiResponse> addToken(@Body Map<Object, Object> params);
}
