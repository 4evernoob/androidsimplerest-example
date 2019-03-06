package com.example.reattlaxiaco;

public class APIUtils {
    private APIUtils() {}


    public static APISErvice getAPIService() {

        return RetrofitClient.getClient(ServiceConstans.service).create(APISErvice.class);
    }
}
