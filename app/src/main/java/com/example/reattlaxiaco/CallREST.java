package com.example.reattlaxiaco;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallREST {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("Coordinates")
    @Expose
    private String coordinates;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
   public CallREST(String Coordinates,String Response){
        this.response=Response;
        this.coordinates=Coordinates;

   }
}