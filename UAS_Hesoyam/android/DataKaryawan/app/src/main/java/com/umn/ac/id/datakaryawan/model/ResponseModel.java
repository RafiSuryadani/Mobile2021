package com.umn.ac.id.datakaryawan.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("status")
    private String status;

    @SerializedName("response")
    private String response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
