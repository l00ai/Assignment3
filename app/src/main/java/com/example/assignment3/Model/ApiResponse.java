package com.example.assignment3.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("msg")
    @Expose
    public String msg;
}
