package com.example.newsapp;
import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("name")
    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}