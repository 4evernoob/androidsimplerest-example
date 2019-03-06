package com.example.reattlaxiaco;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APISErvice {
    @Headers("Content-Type: application/json")
    @POST("calls")
     Call<CallREST> sendCall(@Body CallREST call
                           );
}
