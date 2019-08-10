package com.grin.appforuniver.data.model.dto;

public class AuthenticationRequestDto {

    private String username;
    private String password;

    public AuthenticationRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
