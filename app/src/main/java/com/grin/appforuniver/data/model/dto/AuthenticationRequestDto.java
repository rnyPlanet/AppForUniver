package com.grin.appforuniver.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class AuthenticationRequestDto {

    @SerializedName("username")
    private String mUsername;
    @SerializedName("password")
    private String mPassword;

    public AuthenticationRequestDto(String username, String password) {
        this.mUsername = username;
        this.mPassword = password;
    }

}
